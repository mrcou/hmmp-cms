<script lang="ts" setup>
/**
 * 知网文章标题核对 —— 对齐旧系统：分类筛选 + 字段检索 + 表格 + 删除/引用同步/显示设置
 */
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { message, Modal } from 'antdv-next';

import * as publisherApi from '#/api/biz/publisher';
import {
  JOURNAL_CODE_LABEL,
  JOURNAL_NAME_LABEL,
  useJournalMagazine,
} from '#/composables/use-journal-magazine';

defineOptions({ name: 'PublisherIssueCnki' });

/** 匹配状态：0 未核对 / 1 已匹配 / 2 不匹配 */
const MATCH_STATUS_OPTIONS = [
  { value: '0', label: '未核对' },
  { value: '1', label: '已匹配' },
  { value: '2', label: '不匹配' },
];

const loading = ref(false);
const dataSource = ref<PublisherApi.Article[]>([]);
const selectedKeys = ref<number[]>([]);

const pagination = reactive({
  current: 1,
  pageSize: 5,
  total: 0,
  showSizeChanger: true,
  pageSizeOptions: ['5', '10', '20', '50'],
  showTotal: (total: number, range: [number, number]) =>
    `当前显示 ${range[0]} - ${range[1]} 条，共 ${total} 条记录`,
});

const filters = reactive({
  journalCode: undefined as string | undefined,
  searchField: 'cnkiTitle' as
    | 'cnkiTitle'
    | 'title'
    | 'journalCode'
    | 'fileNo'
    | 'cnkiMatchStatus',
  searchValue: '',
});

const { magazineOptions } = useJournalMagazine();

const searchFieldOptions = [
  { value: 'cnkiTitle', label: '知网标题' },
  { value: 'title', label: '本系统标题' },
  { value: 'journalCode', label: JOURNAL_CODE_LABEL },
  { value: 'fileNo', label: '稿件编号' },
  { value: 'cnkiMatchStatus', label: '匹配状态' },
];

const allColumns = [
  { key: 'action', title: '操作', dataIndex: 'action', width: 90, fixed: 'left' as const },
  { key: 'journalCode', title: JOURNAL_CODE_LABEL, dataIndex: 'journalCode', width: 110 },
  { key: 'cnkiTitle', title: '知网标题', dataIndex: 'cnkiTitle', ellipsis: true, width: 220 },
  { key: 'title', title: '本系统标题', dataIndex: 'title', ellipsis: true, width: 220 },
  { key: 'cnkiCitedCount', title: '被引次数', dataIndex: 'cnkiCitedCount', width: 100 },
  { key: 'createTime', title: '添加时间', dataIndex: 'createTime', width: 170 },
  { key: 'cnkiMatchStatus', title: '匹配次数', dataIndex: 'cnkiMatchStatus', width: 100 },
];

const visibleColumnKeys = ref(allColumns.map((c) => c.key));
const columnDrawerOpen = ref(false);

const columns = computed(() => {
  const visible = new Set(visibleColumnKeys.value);
  return allColumns.filter((c) => visible.has(c.key));
});

const rowSelection = computed(() => ({
  selectedRowKeys: selectedKeys.value,
  onChange: (keys: (number | string)[]) => {
    selectedKeys.value = keys.map(Number);
  },
}));

function matchStatusLabel(status?: string) {
  if (status == null || status === '') {
    return '0';
  }
  const found = MATCH_STATUS_OPTIONS.find((o) => o.value === status);
  return found ? status : status;
}

function buildSearchParams() {
  const params: Record<string, any> = {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  };
  if (filters.journalCode) {
    params.journalCode = filters.journalCode;
  }
  const keyword = filters.searchValue.trim();
  if (keyword) {
    params[filters.searchField] = keyword;
  }
  return params;
}

async function fetchList() {
  loading.value = true;
  try {
    const res = (await publisherApi.getArticleList(buildSearchParams())) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载知网核对列表失败');
    dataSource.value = [];
    pagination.total = 0;
  } finally {
    loading.value = false;
  }
}

function onSearch() {
  pagination.current = 1;
  fetchList();
}

function onReset() {
  filters.journalCode = undefined;
  filters.searchField = 'cnkiTitle';
  filters.searchValue = '';
  pagination.current = 1;
  fetchList();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  fetchList();
}

/** 核对 / 修改 */
const modalVisible = ref(false);
const formData = reactive({
  articleId: undefined as number | undefined,
  journalCode: '',
  title: '',
  cnkiTitle: '',
  cnkiCitedCount: 0 as number,
  cnkiMatchStatus: '0',
});

function handleEdit(record: PublisherApi.Article) {
  formData.articleId = record.articleId;
  formData.journalCode = record.journalCode || '';
  formData.title = record.title || '';
  formData.cnkiTitle = record.cnkiTitle || '';
  formData.cnkiCitedCount = record.cnkiCitedCount ?? 0;
  formData.cnkiMatchStatus =
    record.cnkiMatchStatus && record.cnkiMatchStatus !== ''
      ? record.cnkiMatchStatus
      : '0';
  modalVisible.value = true;
}

function onCnkiTitleBlur() {
  const sys = formData.title.trim();
  const cnki = formData.cnkiTitle.trim();
  if (sys && cnki && sys === cnki && formData.cnkiMatchStatus === '0') {
    formData.cnkiMatchStatus = '1';
  }
}

async function handleSubmit() {
  if (!formData.articleId) {
    return;
  }
  try {
    await publisherApi.cnkiCheckArticle({
      articleId: formData.articleId,
      title: formData.title,
      cnkiTitle: formData.cnkiTitle.trim(),
      cnkiCitedCount: formData.cnkiCitedCount,
      cnkiMatchStatus: formData.cnkiMatchStatus,
    });
    message.success('核对保存成功');
    modalVisible.value = false;
    fetchList();
  } catch {
    // 错误提示由请求拦截器处理
  }
}

async function handleBatchDelete() {
  if (!selectedKeys.value.length) {
    message.warning('请先勾选要删除的记录');
    return;
  }
  Modal.confirm({
    title: '确认删除',
    content: `确定删除选中的 ${selectedKeys.value.length} 条文章记录吗？`,
    okType: 'danger',
    async onOk() {
      await publisherApi.deleteArticle(selectedKeys.value);
      message.success('删除成功');
      fetchList();
    },
  });
}

async function handleSyncCited() {
  if (!selectedKeys.value.length) {
    message.warning('请先勾选要同步引用次数的记录');
    return;
  }
  Modal.confirm({
    title: '引用次数同步',
    content: `将选中的 ${selectedKeys.value.length} 条记录的知网引用次数同步到被引次数，是否继续？`,
    async onOk() {
      await publisherApi.syncCnkiCited(selectedKeys.value);
      message.success('引用次数同步成功');
      fetchList();
    },
  });
}

onMounted(() => {
  fetchList();
});
</script>

<template>
  <div class="p-4">
    <!-- 顶部筛选（统一图示格式：标签 + 控件 + 查询/重置） -->
    <div class="mb-4 flex items-center justify-between">
      <div class="flex items-center justify-end">
        <a-form layout="inline">
          <a-form-item :label="JOURNAL_NAME_LABEL">
            <a-select
              v-model:value="filters.journalCode"
              allow-clear
              placeholder="请选择"
              style="width: 160px"
              :options="magazineOptions"
            />
          </a-form-item>
          <a-form-item label="检索项">
            <a-select
              v-model:value="filters.searchField"
              style="width: 120px"
              :options="searchFieldOptions"
            />
          </a-form-item>
          <a-form-item label="关键词">
            <a-input
              v-model:value="filters.searchValue"
              allow-clear
              placeholder="请输入搜索词"
              style="width: 200px"
              @press-enter="onSearch"
            />
          </a-form-item>
          <a-form-item>
            <a-space>
              <a-button type="primary" @click="onSearch">查询</a-button>
              <a-button @click="onReset">重置</a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </div>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :loading="loading"
      :pagination="pagination"
      :row-selection="rowSelection"
      :scroll="{ x: 1100 }"
      row-key="articleId"
      size="middle"
      @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
    >
      <template #emptyText>
        <a-empty description="未查询到任何数据信息!" />
      </template>

      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-button size="small" @click="handleEdit(record)">修改</a-button>
        </template>
        <template v-else-if="column.key === 'cnkiTitle'">
          {{ record.cnkiTitle || '' }}
        </template>
        <template v-else-if="column.key === 'cnkiCitedCount'">
          {{ record.cnkiCitedCount ?? 0 }}
        </template>
        <template v-else-if="column.key === 'cnkiMatchStatus'">
          {{ matchStatusLabel(record.cnkiMatchStatus) }}
        </template>
        <template v-else-if="column.key === 'createTime'">
          {{ record.createTime || '' }}
        </template>
      </template>
    </a-table>

    <!-- 底部操作 -->
    <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
      <a-button type="primary" danger @click="handleBatchDelete">
        删除
      </a-button>
      <a-button type="primary" @click="handleSyncCited">
        引用次数同步
      </a-button>
      <a-button type="primary" @click="columnDrawerOpen = true">
        显示设置
      </a-button>
    </div>

    <!-- 核对 / 修改 -->
    <a-modal
      v-model:open="modalVisible"
      title="知网标题核对"
      destroy-on-close
      :width="560"
      @ok="handleSubmit"
    >
      <a-form layout="vertical" class="pt-2">
        <a-form-item :label="JOURNAL_CODE_LABEL">
          <a-input :value="formData.journalCode" disabled />
        </a-form-item>
        <a-form-item label="本系统标题">
          <a-textarea :value="formData.title" :rows="2" disabled />
        </a-form-item>
        <a-form-item label="知网标题" required>
          <a-textarea
            v-model:value="formData.cnkiTitle"
            :rows="2"
            placeholder="请填写知网检索到的文章标题"
            @blur="onCnkiTitleBlur"
          />
        </a-form-item>
        <div class="grid grid-cols-2 gap-x-4">
          <a-form-item label="被引次数（知网）">
            <a-input-number
              v-model:value="formData.cnkiCitedCount"
              :min="0"
              :max="999999"
              style="width: 100%"
            />
          </a-form-item>
          <a-form-item label="匹配状态">
            <a-select
              v-model:value="formData.cnkiMatchStatus"
              style="width: 100%"
              :options="MATCH_STATUS_OPTIONS"
            />
          </a-form-item>
        </div>
      </a-form>
    </a-modal>

    <!-- 显示设置 -->
    <a-drawer
      v-model:open="columnDrawerOpen"
      title="显示设置"
      placement="right"
      :width="320"
    >
      <p class="text-muted-foreground mb-3 text-sm">勾选需要在列表中显示的列：</p>
      <a-checkbox-group
        v-model:value="visibleColumnKeys"
        class="flex flex-col gap-2"
      >
        <a-checkbox v-for="col in allColumns" :key="col.key" :value="col.key">
          {{ col.title }}
        </a-checkbox>
      </a-checkbox-group>
      <div class="mt-6">
        <a-button type="primary" block @click="columnDrawerOpen = false">
          确定
        </a-button>
      </div>
    </a-drawer>
  </div>
</template>

<style scoped>
.batch-actions :deep(.ant-btn) {
  min-width: 7rem;
}
</style>
