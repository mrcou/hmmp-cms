<script lang="ts" setup>
/**
 * 文章评论管理 —— 对齐示意图：杂志/会议/稿件编号筛选 + 表格 + 高级检索/删除/审核/显示设置
 */
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { IconifyIcon } from '@vben/icons';
import { preferences } from '@vben/preferences';

import { message, Modal } from 'antdv-next';

import * as meetingApi from '#/api/biz/meeting';
import * as publisherApi from '#/api/biz/publisher';

defineOptions({ name: 'PublisherIssueComment' });

const loading = ref(false);
const dataSource = ref<PublisherApi.Comment[]>([]);
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

/** 顶栏筛选（对齐示意图） */
const filters = reactive({
  journalCode: undefined as string | undefined,
  meetingId: undefined as number | undefined,
  searchField: 'fileNo' as 'articleTitle' | 'fileNo' | 'userName',
  searchValue: '',
});

const searchFieldOptions = [
  { value: 'fileNo', label: '稿件编号' },
  { value: 'articleTitle', label: '标题' },
  { value: 'userName', label: '评论用户' },
];

const magazineOptions = computed(() => [
  {
    value: 'ddhl',
    label: preferences.app.name || '默认杂志',
  },
]);

const meetingOptions = ref<{ label: string; value: number }[]>([]);

async function loadMeetingOptions() {
  try {
    const res = (await meetingApi.getMeetingList({
      pageNum: 1,
      pageSize: 200,
    })) as any;
    meetingOptions.value = (res?.rows ?? [])
      .map((r: any) => ({
        value: r.meetingId as number,
        label: (r.meetingName || r.title || `会议#${r.meetingId}`) as string,
      }))
      .filter((m: { value: number }) => m.value != null);
  } catch {
    meetingOptions.value = [];
  }
}

const statusMap: Record<string, { color: string; label: string }> = {
  '0': { label: '待审核', color: 'orange' },
  '1': { label: '已通过', color: 'green' },
  '2': { label: '已拒绝', color: 'red' },
};

/** antdv-next Select 不会转发 a-select-option 子节点，必须用 options */
const statusOptions = Object.entries(statusMap).map(([value, item]) => ({
  value,
  label: item.label,
}));

/** 高级检索 */
const advancedOpen = ref(false);

interface SearchRow {
  field: string;
  id: number;
  range: [string, string] | null;
  value: string;
}

let searchRowSeq = 1;
function createSearchRow(field = 'fileNo'): SearchRow {
  return { id: searchRowSeq++, field, value: '', range: null };
}
const searchRows = ref<SearchRow[]>([createSearchRow()]);

const advancedFields = [
  { value: 'fileNo', label: '稿件编号', type: 'text' },
  { value: 'articleTitle', label: '标题', type: 'text' },
  { value: 'userName', label: '评论用户', type: 'text' },
  { value: 'content', label: '评论内容', type: 'text' },
  { value: 'auditStatus', label: '状态', type: 'status' },
  { value: 'yearNo', label: '年份', type: 'text' },
  { value: 'period', label: '刊期', type: 'text' },
  { value: 'commentTime', label: '评论日期', type: 'range' },
] as const;

const advancedFieldOptions = advancedFields.map((f) => ({
  value: f.value,
  label: f.label,
}));

function fieldType(field: string) {
  return advancedFields.find((f) => f.value === field)?.type ?? 'text';
}

function addSearchRow() {
  searchRows.value.push(createSearchRow('articleTitle'));
}

function removeSearchRow(id: number) {
  if (searchRows.value.length <= 1) return;
  searchRows.value = searchRows.value.filter((r) => r.id !== id);
}

function onAdvancedFieldChange(row: SearchRow) {
  row.value = '';
  row.range = null;
}

function onResetAdvanced() {
  searchRows.value = [createSearchRow()];
  pagination.current = 1;
  fetchList();
}

const allColumns = [
  { key: 'fileNo', title: '稿件编号', dataIndex: 'fileNo', width: 130 },
  {
    key: 'articleTitle',
    title: '标题',
    dataIndex: 'articleTitle',
    ellipsis: true,
    width: 240,
  },
  { key: 'yearNo', title: '年份', dataIndex: 'yearNo', width: 80 },
  { key: 'period', title: '刊期', dataIndex: 'period', width: 80 },
  {
    key: 'createTime',
    title: '评论日期',
    dataIndex: 'createTime',
    width: 170,
  },
  { key: 'userName', title: '评论用户', dataIndex: 'userName', width: 110 },
  { key: 'auditStatus', title: '状态', dataIndex: 'auditStatus', width: 100 },
  {
    key: 'content',
    title: '评论内容',
    dataIndex: 'content',
    ellipsis: true,
    width: 260,
  },
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

function buildSearchParams() {
  const params: Record<string, any> = {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  };

  if (filters.journalCode) {
    params.journalCode = filters.journalCode;
  }
  if (filters.meetingId != null) {
    params.meetingId = filters.meetingId;
  }

  const keyword = filters.searchValue.trim();
  if (keyword) {
    params[filters.searchField] = keyword;
  }

  for (const row of searchRows.value) {
    if (fieldType(row.field) === 'range') {
      if (row.range?.[0] && row.range?.[1]) {
        params['params[beginTime]'] = row.range[0];
        params['params[endTime]'] = row.range[1];
      }
      continue;
    }
    if (!row.value?.trim()) continue;
    if (row.field === 'yearNo' || row.field === 'period') {
      const num = Number(row.value.trim());
      if (!Number.isNaN(num)) {
        params[row.field] = num;
      }
      continue;
    }
    params[row.field] = row.value.trim();
  }

  return params;
}

async function fetchList() {
  loading.value = true;
  try {
    const res = (await publisherApi.getCommentList(buildSearchParams())) as any;
    dataSource.value = res?.rows ?? [];
    pagination.total = res?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载评论列表失败');
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
  filters.meetingId = undefined;
  filters.searchField = 'fileNo';
  filters.searchValue = '';
  pagination.current = 1;
  fetchList();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  fetchList();
}

function formatDateTime(val?: string) {
  return val ? String(val).slice(0, 19).replace('T', ' ') : '';
}

function requireSelection(action: string) {
  if (!selectedKeys.value.length) {
    message.warning(`请先勾选要${action}的评论`);
    return false;
  }
  return true;
}

async function handleBatchDelete() {
  if (!requireSelection('删除')) return;
  Modal.confirm({
    title: '确认删除',
    content: `确定删除选中的 ${selectedKeys.value.length} 条评论吗？`,
    okType: 'danger',
    async onOk() {
      await publisherApi.deleteComment(selectedKeys.value);
      message.success('删除成功');
      fetchList();
    },
  });
}

async function handleBatchAudit(auditStatus: '1' | '2') {
  const action = auditStatus === '1' ? '审核通过' : '审核不通过';
  if (!requireSelection(action)) return;
  Modal.confirm({
    title: `确认${action}`,
    content: `确定将选中的 ${selectedKeys.value.length} 条评论标记为「${action}」吗？`,
    async onOk() {
      await publisherApi.auditComment({
        commentIds: selectedKeys.value,
        auditStatus,
      });
      message.success(`${action}成功`);
      fetchList();
    },
  });
}

onMounted(() => {
  fetchList();
  loadMeetingOptions();
});
</script>

<template>
  <div class="p-4">
    <!-- 高级检索面板 -->
    <div v-show="advancedOpen" class="bg-card mb-3 rounded-md border px-3 py-3">
      <div class="mb-2 text-sm font-medium">检索条件设置:</div>
      <div
        v-for="(row, idx) in searchRows"
        :key="row.id"
        class="mb-2 flex flex-wrap items-center gap-2"
      >
        <a-button
          v-if="idx === 0"
          type="primary"
          size="small"
          class="!h-7 !w-7 !min-w-7 !p-0"
          @click="addSearchRow"
        >
          <IconifyIcon icon="lucide:plus" class="size-3.5" />
        </a-button>
        <a-button
          v-else
          type="primary"
          size="small"
          class="!h-7 !w-7 !min-w-7 !p-0"
          style="background: #0d9488; border-color: #0d9488"
          @click="removeSearchRow(row.id)"
        >
          <IconifyIcon icon="lucide:minus" class="size-3.5" />
        </a-button>

        <a-select
          v-model:value="row.field"
          style="width: 130px"
          size="small"
          :options="advancedFieldOptions"
          @change="onAdvancedFieldChange(row)"
        />

        <template v-if="fieldType(row.field) === 'range'">
          <a-range-picker
            v-model:value="row.range"
            value-format="YYYY-MM-DD"
            size="small"
            style="width: 260px"
          />
        </template>
        <a-select
          v-else-if="fieldType(row.field) === 'status'"
          v-model:value="row.value"
          allow-clear
          placeholder="请选择状态"
          size="small"
          style="width: 180px"
          :options="statusOptions"
        />
        <a-input
          v-else
          v-model:value="row.value"
          size="small"
          allow-clear
          placeholder="请输入搜索词..."
          style="width: 220px"
          @press-enter="onSearch"
        />
      </div>

      <div class="mt-3 flex justify-end gap-2">
        <a-button type="primary" size="small" @click="onSearch">
          <template #icon>
            <IconifyIcon icon="lucide:search" class="size-3.5" />
          </template>
          检索
        </a-button>
        <a-button size="small" @click="onResetAdvanced">重置</a-button>
        <a-button size="small" @click="advancedOpen = false">
          <template #icon>
            <IconifyIcon icon="lucide:x" class="size-3.5" />
          </template>
          关闭
        </a-button>
      </div>
    </div>

    <a-card :bordered="false" class="shadow-sm">
      <!-- 顶部筛选（统一图示格式：标签 + 控件 + 查询/重置） -->
      <div class="mb-4 flex items-center justify-between">
        <div class="flex items-center justify-end">
          <a-form layout="inline">
            <a-form-item label="杂志名称">
              <a-select
                v-model:value="filters.journalCode"
                allow-clear
                placeholder="请选择"
                style="width: 160px"
                :options="magazineOptions"
              />
            </a-form-item>
            <a-form-item label="会议">
              <a-select
                v-model:value="filters.meetingId"
                allow-clear
                placeholder="请选择"
                style="width: 160px"
                :options="meetingOptions"
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

      <!-- 表格 -->
      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        :row-selection="rowSelection"
        :scroll="{ x: 1300 }"
        row-key="commentId"
        size="middle"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #emptyText>
          <a-empty description="未查询到任何数据信息!" />
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'articleTitle'">
            <span class="text-primary">{{ record.articleTitle || '' }}</span>
          </template>
          <template v-else-if="column.key === 'yearNo'">
            {{ record.yearNo ?? '' }}
          </template>
          <template v-else-if="column.key === 'period'">
            {{ record.period ?? '' }}
          </template>
          <template v-else-if="column.key === 'createTime'">
            {{ formatDateTime(record.createTime) }}
          </template>
          <template v-else-if="column.key === 'auditStatus'">
            <a-tag :color="statusMap[record.auditStatus ?? '']?.color">
              {{ statusMap[record.auditStatus ?? '']?.label || '-' }}
            </a-tag>
          </template>
        </template>
      </a-table>

      <!-- 底部操作（对齐示意图） -->
      <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
        <a-button type="primary" @click="advancedOpen = !advancedOpen">
          高级检索
        </a-button>
        <a-button type="primary" danger @click="handleBatchDelete">
          删除
        </a-button>
        <a-button type="primary" @click="handleBatchAudit('1')">
          审核通过
        </a-button>
        <a-button type="primary" @click="handleBatchAudit('2')">
          审核不通过
        </a-button>
        <a-button type="primary" ghost @click="columnDrawerOpen = true">
          显示设置
        </a-button>
      </div>
    </a-card>

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
