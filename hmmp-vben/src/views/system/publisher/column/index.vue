<script lang="ts" setup>
/**
 * 栏目管理 —— 按旧系统字段：列表 + 右侧抽屉新增/修改 + 显示设置
 */
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { VbenTableAction } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';

import { message, Modal } from 'antdv-next';

import * as publisherApi from '#/api/biz/publisher';
import {
  JOURNAL_CODE_LABEL,
  JOURNAL_NAME_LABEL,
  useJournalMagazine,
} from '#/composables/use-journal-magazine';

defineOptions({ name: 'PublisherIssueColumn' });

type SearchField =
  | 'columnCode'
  | 'columnEnName'
  | 'columnName'
  | 'editName'
  | 'fileNoPrefix'
  | 'isPostColumn'
  | 'journalCode'
  | 'needRecommender'
  | 'sequence';

const { magazineOptions } = useJournalMagazine();

const usageScopeOptions = [
  { value: '1', label: '投稿、内部查询、网刊发布' },
  { value: '0', label: '网刊发布' },
  { value: '2', label: '内部查询、网刊发布' },
];

const yesNoOptions = [
  { value: '0', label: '否' },
  { value: '1', label: '是' },
];

const editorOptions = [
  { value: '166787581@qq.com', label: '勤云测试' },
  { value: '577326487@qq.com', label: '勤云测试' },
  { value: 'bianji@qq.com', label: '勤云编辑' },
  { value: 'ddhlfl@163.com', label: 'FL' },
  { value: 'ddhlgwj@163.com', label: 'GWJ' },
  { value: 'ddhllh@163.com', label: 'LH' },
  { value: 'ddhlljq@163.com', label: 'LJQ' },
  { value: 'ddhllys@163.com', label: 'LYS' },
  { value: 'ddhlryy@163.com', label: 'RYY' },
  { value: 'ddhlws@163.com', label: 'ws' },
];

const loading = ref(false);
const submitting = ref(false);
const dataSource = ref<PublisherApi.Column[]>([]);
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
  searchField: 'journalCode' as SearchField,
  searchValue: '',
});

const searchFieldOptions: { label: string; value: SearchField }[] = [
  { value: 'journalCode', label: JOURNAL_CODE_LABEL },
  { value: 'columnCode', label: '栏目ID' },
  { value: 'columnName', label: '中文栏目' },
  { value: 'columnEnName', label: '英文栏目' },
  { value: 'isPostColumn', label: '使用范围' },
  { value: 'sequence', label: '顺序' },
  { value: 'needRecommender', label: '是否需要推荐人' },
  { value: 'fileNoPrefix', label: '稿件编号前缀' },
  { value: 'editName', label: '编辑' },
];

const allColumns = [
  { key: 'journalCode', title: JOURNAL_NAME_LABEL, dataIndex: 'journalCode', width: 120 },
  { key: 'columnCode', title: '栏目ID', dataIndex: 'columnCode', width: 120 },
  { key: 'columnName', title: '中文栏目', dataIndex: 'columnName', width: 150 },
  { key: 'columnEnName', title: '英文栏目', dataIndex: 'columnEnName', width: 150 },
  { key: 'isPostColumn', title: '使用范围', dataIndex: 'isPostColumn', ellipsis: true, width: 210 },
  { key: 'sequence', title: '顺序', dataIndex: 'sequence', width: 80 },
  { key: 'needRecommender', title: '是否需要推荐人', dataIndex: 'needRecommender', width: 170 },
  { key: 'fileNoPrefix', title: '稿件编号前缀', dataIndex: 'fileNoPrefix', width: 130 },
  { key: 'enAbstractMoney', title: '英文摘要加工费', dataIndex: 'enAbstractMoney', width: 140 },
  { key: 'editName', title: '编辑', dataIndex: 'editName', width: 180 },
  { key: 'action', title: '操作', dataIndex: 'action', width: 90, fixed: 'right' as const },
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

function getMagazineLabel(value?: string) {
  if (!value) {
    return '';
  }
  return magazineOptions.value.find((item) => item.value === value)?.label || value;
}

function normalizeIsPostColumn(
  value?: string,
  usageScope?: string,
): '0' | '1' | '2' {
  if (value === '0' || value === '1' || value === '2') {
    return value;
  }
  if (usageScope?.includes('投稿')) {
    return '1';
  }
  if (usageScope?.includes('内部')) {
    return '2';
  }
  return '0';
}

function getUsageScopeLabel(value?: string, usageScope?: string) {
  const normalized = normalizeIsPostColumn(value, usageScope);
  return (
    usageScopeOptions.find((item) => item.value === normalized)?.label || ''
  );
}

function yesNoLabel(value?: string) {
  return yesNoOptions.find((item) => item.value === value)?.label || '否';
}

function getSequence(record: PublisherApi.Column) {
  return record.sequence ?? record.orderNum ?? '';
}

function getColumnEnName(record: PublisherApi.Column) {
  return record.columnEnName || record.nameEn || '';
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
    if (filters.searchField === 'sequence') {
      const num = Number(keyword);
      if (!Number.isNaN(num)) {
        params.sequence = num;
      }
    } else if (filters.searchField === 'isPostColumn') {
      params.isPostColumn = normalizeIsPostColumn(keyword, keyword);
    } else if (filters.searchField === 'needRecommender') {
      params.needRecommender = keyword.includes('是')
        ? '1'
        : keyword.includes('否')
          ? '0'
          : keyword;
    } else {
      params[filters.searchField] = keyword;
    }
  }
  return params;
}

async function fetchList() {
  loading.value = true;
  try {
    const res = (await publisherApi.getColumnList(buildSearchParams())) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载栏目列表失败');
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
  filters.searchField = 'journalCode';
  filters.searchValue = '';
  pagination.current = 1;
  fetchList();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  fetchList();
}

const drawerOpen = ref(false);
const drawerTitle = ref('添加栏目');
const isEdit = ref(false);
const selectedEditor = ref<string | undefined>();
const formData = reactive<PublisherApi.Column>({
  journalCode: 'ddhl',
  columnCode: '',
  columnName: '',
  columnEnName: '',
  nameEn: '',
  isPostColumn: '1',
  usageScope: getUsageScopeLabel('1'),
  sequence: 0,
  orderNum: 0,
  needRecommender: '0',
  fileNoPrefix: '',
  enAbstractMoney: undefined,
  editName: '',
  parentId: 0,
  status: '0',
});

function resetForm() {
  formData.columnId = undefined;
  formData.journalCode = filters.journalCode || 'ddhl';
  formData.columnCode = '';
  formData.columnName = '';
  formData.columnEnName = '';
  formData.nameEn = '';
  formData.isPostColumn = '1';
  formData.usageScope = getUsageScopeLabel('1');
  formData.sequence = 0;
  formData.orderNum = 0;
  formData.needRecommender = '0';
  formData.fileNoPrefix = '';
  formData.enAbstractMoney = undefined;
  formData.editName = '';
  formData.parentId = 0;
  formData.status = '0';
  selectedEditor.value = undefined;
}

function handleAdd() {
  isEdit.value = false;
  drawerTitle.value = '添加栏目';
  resetForm();
  drawerOpen.value = true;
}

function handleEdit(record: PublisherApi.Column) {
  isEdit.value = true;
  drawerTitle.value = '修改栏目';
  resetForm();
  const isPostColumn = normalizeIsPostColumn(
    record.isPostColumn,
    record.usageScope,
  );
  Object.assign(formData, {
    columnId: record.columnId,
    journalCode: record.journalCode || 'ddhl',
    columnCode: record.columnCode || '',
    columnName: record.columnName || '',
    columnEnName: getColumnEnName(record),
    nameEn: getColumnEnName(record),
    isPostColumn,
    usageScope: getUsageScopeLabel(isPostColumn),
    sequence: record.sequence ?? record.orderNum ?? 0,
    orderNum: record.orderNum ?? record.sequence ?? 0,
    needRecommender: record.needRecommender ?? '0',
    fileNoPrefix: record.fileNoPrefix || '',
    enAbstractMoney: record.enAbstractMoney,
    editName: record.editName || '',
    parentId: record.parentId ?? 0,
    status: record.status || '0',
  });
  selectedEditor.value = editorOptions.some(
    (item) => item.value === formData.editName,
  )
    ? formData.editName
    : undefined;
  drawerOpen.value = true;
}

function handleEditorSelect(value?: string) {
  selectedEditor.value = value;
  if (value) {
    formData.editName = value;
  }
}

function buildPayload(): PublisherApi.Column | null {
  const journalCode = formData.journalCode?.trim();
  const columnCode = formData.columnCode?.trim();
  const columnName = formData.columnName?.trim();
  if (!journalCode) {
    message.warning(`请选择${JOURNAL_NAME_LABEL}`);
    return null;
  }
  if (!columnCode) {
    message.warning('请填写栏目ID');
    return null;
  }
  if (!columnName) {
    message.warning('请填写中文栏目');
    return null;
  }
  const isPostColumn = normalizeIsPostColumn(formData.isPostColumn);
  const sequence = Number(formData.sequence ?? formData.orderNum ?? 0);
  const columnEnName = formData.columnEnName?.trim() || '';
  return {
    ...formData,
    journalCode,
    columnCode,
    columnName,
    columnEnName,
    nameEn: columnEnName,
    isPostColumn,
    usageScope: getUsageScopeLabel(isPostColumn),
    sequence,
    orderNum: sequence,
    needRecommender: formData.needRecommender || '0',
    fileNoPrefix: formData.fileNoPrefix?.trim() || '',
    enAbstractMoney:
      formData.enAbstractMoney == null
        ? undefined
        : Number(formData.enAbstractMoney),
    editName: formData.editName?.trim() || '',
    parentId: formData.parentId ?? 0,
    status: formData.status || '0',
  };
}

async function handleSubmit() {
  const payload = buildPayload();
  if (!payload) {
    return;
  }
  submitting.value = true;
  try {
    if (isEdit.value) {
      await publisherApi.updateColumn(payload);
      message.success('修改成功');
    } else {
      await publisherApi.createColumn(payload);
      message.success('添加成功');
    }
    drawerOpen.value = false;
    fetchList();
  } finally {
    submitting.value = false;
  }
}

async function handleBatchDelete() {
  if (!selectedKeys.value.length) {
    message.warning('请先勾选要删除的记录');
    return;
  }
  Modal.confirm({
    title: '确认删除',
    content: `确定删除选中的 ${selectedKeys.value.length} 条栏目记录吗？`,
    okType: 'danger',
    async onOk() {
      await publisherApi.deleteColumn(selectedKeys.value);
      message.success('删除成功');
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
              style="width: 150px"
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
      :scroll="{ x: 1600 }"
      row-key="columnId"
      size="middle"
      @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
    >
      <template #emptyText>
        <a-empty description="未查询到任何数据!!" />
      </template>

      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'journalCode'">
          {{ getMagazineLabel(record.journalCode) }}
        </template>
        <template v-else-if="column.key === 'columnEnName'">
          {{ getColumnEnName(record) }}
        </template>
        <template v-else-if="column.key === 'isPostColumn'">
          {{ getUsageScopeLabel(record.isPostColumn, record.usageScope) }}
        </template>
        <template v-else-if="column.key === 'sequence'">
          {{ getSequence(record) }}
        </template>
        <template v-else-if="column.key === 'needRecommender'">
          {{ yesNoLabel(record.needRecommender) }}
        </template>
        <template v-else-if="column.key === 'enAbstractMoney'">
          <span v-if="record.enAbstractMoney != null">
            {{ record.enAbstractMoney }} 元/每篇
          </span>
        </template>
        <template v-else-if="column.key === 'action'">
          <VbenTableAction
            :actions="[
              { text: '修改', icon: 'lucide:edit', onClick: () => handleEdit(record) },
            ]"
          />
        </template>
      </template>
    </a-table>

    <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
      <a-button type="primary" @click="handleAdd">
        <IconifyIcon icon="lucide:plus" class="mr-1 size-3.5" />
        添加
      </a-button>
      <a-button type="primary" danger @click="handleBatchDelete">
        <IconifyIcon icon="lucide:trash-2" class="mr-1 size-3.5" />
        删除
      </a-button>
      <a-button type="primary" @click="columnDrawerOpen = true">
        <IconifyIcon icon="lucide:settings-2" class="mr-1 size-3.5" />
        显示设置
      </a-button>
    </div>

    <a-drawer
      v-model:open="drawerOpen"
      :title="drawerTitle"
      placement="right"
      :width="720"
      destroy-on-close
      class="column-form-drawer"
    >
      <a-form layout="vertical" class="column-form">
        <a-form-item :label="JOURNAL_NAME_LABEL">
          <a-select
            v-model:value="formData.journalCode"
            :options="magazineOptions"
            placeholder="请选择"
          />
        </a-form-item>
        <a-form-item label="栏目ID" required>
          <a-input
            v-model:value="formData.columnCode"
            allow-clear
            :disabled="isEdit"
          />
        </a-form-item>
        <a-form-item label="中文栏目" required>
          <a-input v-model:value="formData.columnName" allow-clear />
        </a-form-item>
        <a-form-item label="英文栏目">
          <a-input v-model:value="formData.columnEnName" allow-clear />
        </a-form-item>
        <a-form-item label="使用范围">
          <a-select
            v-model:value="formData.isPostColumn"
            :options="usageScopeOptions"
          />
        </a-form-item>
        <a-form-item label="顺序">
          <a-input-number
            v-model:value="formData.sequence"
            :min="0"
            :max="9999"
            style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="本栏目的投稿是否需要作者填写推荐人">
          <a-select
            v-model:value="formData.needRecommender"
            :options="yesNoOptions"
          />
        </a-form-item>
        <a-form-item
          label="稿件编号前缀"
          extra="注意：一般情况下，请不要设置前缀，因为这样会使得按稿件编号排序变得混乱！如果一定要用，请尽量用英文字母，而且每个栏目的前缀长度尽量相同。"
        >
          <a-input v-model:value="formData.fileNoPrefix" allow-clear />
        </a-form-item>
        <a-form-item label="英文摘要加工费" extra="元/每篇">
          <a-input-number
            v-model:value="formData.enAbstractMoney"
            :min="0"
            :precision="2"
            style="width: 100%"
          />
        </a-form-item>
        <a-form-item
          label="编辑"
          extra="提示：如果希望实现系统自动分稿，即根据作者选择的栏目自动把稿件分配给相关栏目的责任编辑，请输入责任编辑的帐号即可"
        >
          <div class="editor-picker">
            <a-input v-model:value="formData.editName" allow-clear />
            <a-select
              v-model:value="selectedEditor"
              allow-clear
              placeholder="请选择 ..."
              :options="editorOptions"
              @change="handleEditorSelect"
            />
          </div>
        </a-form-item>
      </a-form>

      <template #footer>
        <div class="drawer-footer">
          <a-button @click="drawerOpen = false">取消</a-button>
          <a-button type="primary" :loading="submitting" @click="handleSubmit">
            保存
          </a-button>
        </div>
      </template>
    </a-drawer>

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

.column-form {
  padding-bottom: 1rem;
}

.editor-picker {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 14rem;
  gap: 0.75rem;
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
}

.column-form :deep(.ant-form-item-extra) {
  color: #cf1322;
  font-size: 12px;
  line-height: 1.6;
}

@media (max-width: 640px) {
  .editor-picker {
    grid-template-columns: 1fr;
  }
}
</style>
