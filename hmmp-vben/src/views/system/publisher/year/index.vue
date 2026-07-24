<script lang="ts" setup>
/**
 * 年份管理 —— 对齐旧系统列表：杂志筛选 + 快捷检索 + 表格 + 删除/添加/显示设置
 */
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { VbenTableAction } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';
import { message, Modal, Upload } from 'antdv-next';

import * as meetingApi from '#/api/biz/meeting';
import * as publisherApi from '#/api/biz/publisher';
import {
  JOURNAL_CODE_LABEL,
  JOURNAL_NAME_LABEL,
  useJournalMagazine,
} from '#/composables/use-journal-magazine';

defineOptions({ name: 'PublisherIssueYear' });

type UploadField = 'catalogFile';

const currentYear = new Date().getFullYear();
const loading = ref(false);
const dataSource = ref<PublisherApi.Year[]>([]);
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

/** 顶栏筛选 */
const filters = reactive({
  journalCode: undefined as string | undefined,
  searchField: 'journalCode' as
    | 'journalCode'
    | 'year'
    | 'period'
    | 'volume'
    | 'nameCn'
    | 'nameEn',
  searchValue: '',
});

const { magazineOptions } = useJournalMagazine();

const searchFieldOptions = [
  { value: 'journalCode', label: JOURNAL_CODE_LABEL },
  { value: 'year', label: '年份' },
  { value: 'volume', label: '卷号' },
  { value: 'period', label: '期数' },
  { value: 'nameCn', label: '中文名' },
  { value: 'nameEn', label: '英文名' },
];

const statusOptions = [
  { value: '0', label: '正常' },
  { value: '1', label: '停用' },
];

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

const allColumns = [
  { key: 'journalCode', title: JOURNAL_CODE_LABEL, dataIndex: 'journalCode', width: 120 },
  { key: 'year', title: '年份', dataIndex: 'year', width: 90 },
  { key: 'volume', title: '卷号', dataIndex: 'volume', width: 90 },
  { key: 'period', title: '期数', dataIndex: 'period', width: 90 },
  { key: 'nameCn', title: '中文名', dataIndex: 'nameCn', width: 140 },
  { key: 'nameEn', title: '英文名', dataIndex: 'nameEn', width: 140 },
  { key: 'catalogFile', title: '目录文件', dataIndex: 'catalogFile', ellipsis: true, width: 180 },
  { key: 'isFree', title: '免费访问', dataIndex: 'isFree', width: 100 },
  { key: 'status', title: '状态', dataIndex: 'status', width: 90 },
  { key: 'remark', title: '备注', dataIndex: 'remark', ellipsis: true, width: 180 },
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
    if (filters.searchField === 'year' || filters.searchField === 'volume') {
      const num = Number(keyword);
      if (!Number.isNaN(num)) {
        params[filters.searchField] = num;
      }
    } else {
      params[filters.searchField] = keyword;
    }
  }
  return params;
}

async function fetchList() {
  loading.value = true;
  try {
    const res = (await publisherApi.getYearList(buildSearchParams())) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载年份列表失败');
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

/** 新增 / 编辑 */
const drawerOpen = ref(false);
const drawerTitle = ref('添加年份');
const submitting = ref(false);
const isEdit = ref(false);
const formData = reactive<PublisherApi.Year>({
  journalCode: 'ddhl',
  year: currentYear,
  period: '',
  volume: 1,
  nameCn: '',
  nameEn: '',
  catalogFile: '',
  isFree: '0',
  meetingId: undefined,
  status: '0',
  remark: '',
});
const uploadFileLists = reactive<Record<UploadField, any[]>>({
  catalogFile: [],
});

function resetForm() {
  formData.yearId = undefined;
  formData.journalCode = filters.journalCode || 'ddhl';
  formData.year = currentYear;
  formData.period = '';
  formData.volume = 1;
  formData.nameCn = `${formData.year}年`;
  formData.nameEn = '';
  formData.catalogFile = '';
  formData.isFree = '0';
  formData.meetingId = undefined;
  formData.status = '0';
  formData.remark = '';
  uploadFileLists.catalogFile = [];
}

function handleAdd() {
  isEdit.value = false;
  drawerTitle.value = '添加年份';
  resetForm();
  drawerOpen.value = true;
}

function handleEdit(record: PublisherApi.Year) {
  isEdit.value = true;
  drawerTitle.value = '修改年份';
  resetForm();
  Object.assign(formData, {
    yearId: record.yearId,
    journalCode: record.journalCode || 'ddhl',
    year: record.year,
    period: String(record.period ?? ''),
    volume: record.volume,
    nameCn: record.nameCn || '',
    nameEn: record.nameEn || '',
    catalogFile: record.catalogFile || '',
    isFree: record.isFree ?? '0',
    meetingId: record.meetingId,
    status: record.status || '0',
    remark: record.remark || '',
  });
  drawerOpen.value = true;
}

function onYearChange(val: number | null) {
  if (val != null && !isEdit.value) {
    formData.nameCn = `${val}年`;
  }
}

function makeUploadBefore(field: UploadField) {
  return (file: File) => {
    uploadFieldFile(field, file);
    return Upload.LIST_IGNORE;
  };
}

async function uploadFieldFile(field: UploadField, file: File) {
  try {
    const res: any = await publisherApi.uploadPublisherFile(file);
    const filePath =
      res?.fileName ?? res?.data?.fileName ?? res?.url ?? res?.data?.url ?? '';
    if (!filePath) {
      message.warning('上传成功，但未返回文件路径');
      return;
    }
    formData[field] = filePath;
    uploadFileLists[field] = [
      {
        uid: `${field}-${Date.now()}`,
        name: file.name,
        status: 'done',
      },
    ];
    message.success('上传成功');
  } catch {
    message.error('上传失败');
  }
}

function statusLabel(status?: string) {
  return statusOptions.find((item) => item.value === status)?.label || '正常';
}

function buildPayload(): PublisherApi.Year | null {
  const journalCode = formData.journalCode?.trim();
  if (!journalCode) {
    message.warning(`请选择${JOURNAL_NAME_LABEL}`);
    return null;
  }
  if (formData.year == null) {
    message.warning('请填写年份');
    return null;
  }
  return {
    ...formData,
    journalCode,
    year: Number(formData.year),
    period: formData.period?.trim() || undefined,
    volume: formData.volume == null ? undefined : Number(formData.volume),
    nameCn: formData.nameCn?.trim() || `${formData.year}年`,
    nameEn: formData.nameEn?.trim() || '',
    catalogFile: formData.catalogFile?.trim() || '',
    isFree: formData.isFree || '0',
    status: formData.status || '0',
    remark: formData.remark?.trim() || '',
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
      await publisherApi.updateYear(payload);
      message.success('修改成功');
    } else {
      await publisherApi.createYear(payload);
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
    content: `确定删除选中的 ${selectedKeys.value.length} 条年份记录吗？`,
    okType: 'danger',
    async onOk() {
      await publisherApi.deleteYear(selectedKeys.value);
      message.success('删除成功');
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
    <!-- 顶部筛选（统一图示格式：标签 + 控件 + 查询/重置） -->
    <div class="mb-4 flex items-center justify-between">
      <a-form layout="inline" class="flex w-full items-center justify-between">
        <div class="flex items-center">
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
        </div>
      </a-form>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :loading="loading"
      :pagination="pagination"
      :row-selection="rowSelection"
      :scroll="{ x: 1400 }"
      row-key="yearId"
      size="middle"
      @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
    >
      <template #emptyText>
        <a-empty description="未查询到任何数据!!" />
      </template>

      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <VbenTableAction
            :actions="[
              { text: '修改', icon: 'lucide:edit', onClick: () => handleEdit(record) },
            ]"
          />
        </template>
        <template v-else-if="column.key === 'isFree'">
          {{ record.isFree === '1' ? '是' : '否' }}
        </template>
        <template v-else-if="column.key === 'volume'">
          {{ record.volume ?? '' }}
        </template>
        <template v-else-if="column.key === 'period'">
          {{ record.period ?? '' }}
        </template>
        <template v-else-if="column.key === 'catalogFile'">
          {{ record.catalogFile || '' }}
        </template>
        <template v-else-if="column.key === 'status'">
          {{ statusLabel(record.status) }}
        </template>
      </template>
    </a-table>

    <!-- 底部操作 -->
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

    <!-- 新增 / 修改 -->
    <a-drawer
      v-model:open="drawerOpen"
      :title="drawerTitle"
      placement="right"
      :width="680"
      destroy-on-close
      class="year-drawer"
    >
      <a-form layout="vertical" class="year-form">
        <a-divider orientation="left">基础信息</a-divider>
        <div class="form-grid">
          <a-form-item :label="JOURNAL_NAME_LABEL" required>
            <a-select
              v-model:value="formData.journalCode"
              :options="magazineOptions"
              placeholder="请选择杂志"
            />
          </a-form-item>
          <a-form-item label="年份" required>
            <a-input-number
              v-model:value="formData.year"
              :min="1900"
              :max="2100"
              style="width: 100%"
              @change="onYearChange"
            />
          </a-form-item>
          <a-form-item label="卷号">
            <a-input-number
              v-model:value="formData.volume"
              :min="1"
              :max="9999"
              style="width: 100%"
            />
          </a-form-item>
          <a-form-item label="期数">
            <a-input v-model:value="formData.period" allow-clear />
          </a-form-item>
          <a-form-item label="会议">
            <a-select
              v-model:value="formData.meetingId"
              allow-clear
              placeholder="请选择会议"
              style="width: 100%"
              :options="meetingOptions"
            />
          </a-form-item>
        </div>

        <a-divider orientation="left">名称与目录</a-divider>
        <div class="form-grid">
          <a-form-item label="中文名">
            <a-input
              v-model:value="formData.nameCn"
              allow-clear
              placeholder="如 2026年"
            />
          </a-form-item>
          <a-form-item label="英文名">
            <a-input v-model:value="formData.nameEn" allow-clear />
          </a-form-item>
        </div>
        <a-form-item
          label="目录文件"
          extra="如果需要给读者提供目录下载，可在此上传目录文件"
        >
          <div class="file-picker">
            <a-input
              v-model:value="formData.catalogFile"
              allow-clear
              placeholder="目录文件路径或文件名"
            />
            <a-upload
              :before-upload="makeUploadBefore('catalogFile')"
              :file-list="uploadFileLists.catalogFile"
              :max-count="1"
              :show-upload-list="false"
            >
              <a-button>
                <IconifyIcon icon="lucide:folder-open" class="mr-1 size-3.5" />
                浏览...
              </a-button>
            </a-upload>
          </div>
        </a-form-item>

        <a-divider orientation="left">发布设置</a-divider>
        <div class="form-grid form-grid-compact">
          <a-form-item label="免费访问">
            <a-switch
              v-model:checked="formData.isFree"
              checked-value="1"
              un-checked-value="0"
            />
          </a-form-item>
          <a-form-item label="状态">
            <a-select
              v-model:value="formData.status"
              style="width: 100%"
              :options="statusOptions"
            />
          </a-form-item>
        </div>
        <a-form-item label="备注">
          <a-textarea v-model:value="formData.remark" :rows="2" />
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

.year-form {
  padding-bottom: 1rem;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  column-gap: 1rem;
}

.form-grid-compact {
  align-items: start;
}

.file-picker {
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 0.5rem;
  align-items: center;
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

@media (max-width: 768px) {
  .form-grid,
  .file-picker {
    grid-template-columns: 1fr;
  }
}
</style>
