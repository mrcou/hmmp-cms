<script lang="ts" setup>
/**
 * 刊期管理 —— 对齐旧系统刊期列表：筛选 + 表格 + 右侧抽屉添加/修改 + 显示设置
 */
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { VbenTableAction } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';
import { preferences } from '@vben/preferences';

import { message, Upload } from 'antdv-next';

import * as meetingApi from '#/api/biz/meeting';
import * as publisherApi from '#/api/biz/publisher';

defineOptions({ name: 'PublisherIssuePeriod' });

type SearchField =
  | 'journalCode'
  | 'nameCn'
  | 'nameEn'
  | 'period'
  | 'year';

type UploadField = 'contentsFile' | 'imageUrl' | 'largerCoverUrl';

interface IssueColumnItem {
  articleCount?: number;
  columnId?: number | string;
  columnName?: string;
  sequence?: number;
}

interface IssueColumnRow {
  articleCount?: number;
  columnCode?: string;
  columnId?: number;
  columnName: string;
  key: string;
  selected: boolean;
  sequence?: number;
}

interface ColumnDef {
  dataIndex?: string;
  fixed?: 'left' | 'right';
  key: string;
  title: string;
  width?: number;
}

const currentYear = new Date().getFullYear();
const loading = ref(false);
const dataSource = ref<PublisherApi.Issue[]>([]);
const pagination = reactive({
  current: 1,
  pageSize: 5,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  pageSizeOptions: ['5', '10', '20', '50'],
  showTotal: (total: number, range: [number, number]) =>
    `当前显示 ${range[0]}-${range[1]} 条，共 ${total} 条记录`,
});

const filters = reactive({
  journalCode: undefined as string | undefined,
  meetingId: undefined as number | undefined,
  searchField: 'journalCode' as SearchField,
  searchValue: '',
});

const searchFieldOptions: { label: string; value: SearchField }[] = [
  { value: 'journalCode', label: '杂志编号' },
  { value: 'year', label: '年份' },
  { value: 'nameCn', label: '中文名' },
  { value: 'nameEn', label: '英文名' },
  { value: 'period', label: '刊期' },
];

const yesNoOptions = [
  { value: '0', label: '否' },
  { value: '1', label: '是' },
];

const magazineOptions = computed(() => [
  {
    value: 'ddhl',
    label: preferences.app.name || '默认杂志',
  },
]);

const meetingOptions = ref<{ label: string; value: number }[]>([]);
const yearOptions = ref<
  { label: string; value: number; volume?: number; yearId?: number }[]
>([]);
const columnList = ref<PublisherApi.Column[]>([]);
const issueColumnRows = ref<IssueColumnRow[]>([]);

const allColumns: ColumnDef[] = [
  { title: '杂志编号', dataIndex: 'journalCode', key: 'journalCode', width: 110 },
  { title: '年份', dataIndex: 'year', key: 'year', width: 80 },
  { title: '卷号', dataIndex: 'volume', key: 'volume', width: 80 },
  { title: '刊期', dataIndex: 'period', key: 'period', width: 90 },
  { title: '顺序', dataIndex: 'sequence', key: 'sequence', width: 80 },
  { title: '中文名', dataIndex: 'nameCn', key: 'nameCn', width: 120 },
  { title: '英文名', dataIndex: 'nameEn', key: 'nameEn', width: 120 },
  { title: '免费下载?', dataIndex: 'requestMoney', key: 'requestMoney', width: 100 },
  { title: '是最新一期文章?', dataIndex: 'isLatest', key: 'isLatest', width: 140 },
  { title: '出版日期', dataIndex: 'publishDate', key: 'publishDate', width: 120 },
  { title: '订阅状态', key: 'subscribe', width: 100 },
  { title: 'DOI', key: 'doi', width: 100 },
  { title: 'CSTR', key: 'cstr', width: 100 },
  { title: '百度发布', key: 'baidu', width: 100 },
  { title: '操作', key: 'action', dataIndex: 'action', width: 140, fixed: 'right' as const },
];

const visibleColumnKeys = ref(allColumns.map((c) => c.key));
const columnDrawerOpen = ref(false);

const columns = computed(() =>
  allColumns.filter((c) => visibleColumnKeys.value.includes(c.key)),
);

const drawerOpen = ref(false);
const drawerTitle = ref('添加刊期');
const submitting = ref(false);
const isEdit = ref(false);

const formData = reactive<PublisherApi.Issue>({
  journalCode: 'ddhl',
  year: currentYear,
  period: '1',
  volume: undefined,
  sequence: undefined,
  nameCn: '',
  nameEn: '',
  specialName: '',
  specialEnName: '',
  important: '',
  enImportant: '',
  imageUrl: '',
  largerCoverUrl: '',
  contentsFile: '',
  cnImageExplain: '',
  enImageExplain: '',
  requestMoney: '0',
  isFree: '0',
  isLatest: '0',
  printMoney: undefined,
  publishMoney: undefined,
  postMoney: undefined,
  publishDate: undefined,
  startPage: '',
  endPage: '',
  columnsInfo: '',
  meetingId: undefined,
  status: '0',
  remark: '',
});

const uploadFileLists = reactive<Record<UploadField, any[]>>({
  contentsFile: [],
  imageUrl: [],
  largerCoverUrl: [],
});

function yesNo(v?: string) {
  return v === '1' ? '是' : '否';
}

function buildQueryParams() {
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
    if (filters.searchField === 'year') {
      const num = Number(keyword);
      if (!Number.isNaN(num)) {
        params.year = num;
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
    const res: any = await publisherApi.getIssueList(buildQueryParams());
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
  } finally {
    loading.value = false;
  }
}

function handleSearch() {
  pagination.current = 1;
  fetchList();
}

function handleReset() {
  filters.journalCode = undefined;
  filters.meetingId = undefined;
  filters.searchField = 'journalCode';
  filters.searchValue = '';
  pagination.current = 1;
  fetchList();
}

function resetUploadLists() {
  uploadFileLists.contentsFile = [];
  uploadFileLists.imageUrl = [];
  uploadFileLists.largerCoverUrl = [];
}

function getColumnKey(col: PublisherApi.Column) {
  return col.columnCode || String(col.columnId ?? '');
}

function parseColumnsInfo(raw?: string): IssueColumnItem[] {
  if (!raw?.trim()) {
    return [];
  }
  try {
    const parsed = JSON.parse(raw);
    return Array.isArray(parsed) ? parsed : [];
  } catch {
    return [];
  }
}

async function loadColumnList() {
  try {
    const res: any = await publisherApi.getColumnList({
      pageNum: 1,
      pageSize: 500,
    });
    columnList.value = res?.rows ?? res?.data?.rows ?? [];
  } catch {
    columnList.value = [];
  }
}

function buildIssueColumnRows(columnsInfo?: string) {
  const journalCode = formData.journalCode?.trim();
  const cols = columnList.value.filter(
    (col) =>
      !journalCode || !col.journalCode || col.journalCode === journalCode,
  );
  const saved = parseColumnsInfo(columnsInfo);
  const savedMap = new Map(
    saved.map((item) => [String(item.columnId ?? ''), item]),
  );

  issueColumnRows.value = cols.map((col) => {
    const key = getColumnKey(col);
    const savedItem =
      savedMap.get(key) ?? savedMap.get(String(col.columnId ?? ''));
    return {
      key,
      columnCode: col.columnCode,
      columnId: col.columnId,
      columnName: col.columnName,
      selected: !!savedItem,
      sequence: savedItem?.sequence ?? col.sequence ?? col.orderNum,
      articleCount: savedItem?.articleCount,
    };
  });
}

function serializeIssueColumns() {
  const selected = issueColumnRows.value
    .filter((row) => row.selected)
    .map((row) => ({
      columnId: row.columnCode || row.key,
      columnName: row.columnName,
      sequence: row.sequence,
      ...(row.articleCount == null ? {} : { articleCount: row.articleCount }),
    }));
  return selected.length ? JSON.stringify(selected) : '';
}

function resetForm() {
  formData.issueId = undefined;
  formData.yearId = undefined;
  formData.journalCode = filters.journalCode || 'ddhl';
  formData.year = currentYear;
  formData.volume = undefined;
  formData.period = '1';
  formData.sequence = undefined;
  formData.issueName = '';
  formData.nameCn = '';
  formData.nameEn = '';
  formData.specialName = '';
  formData.specialEnName = '';
  formData.important = '';
  formData.enImportant = '';
  formData.imageUrl = '';
  formData.largerCoverUrl = '';
  formData.contentsFile = '';
  formData.cnImageExplain = '';
  formData.enImageExplain = '';
  formData.requestMoney = '0';
  formData.isFree = '0';
  formData.isLatest = '0';
  formData.printMoney = undefined;
  formData.publishMoney = undefined;
  formData.postMoney = undefined;
  formData.publishDate = undefined;
  formData.startPage = '';
  formData.endPage = '';
  formData.columnsInfo = '';
  formData.meetingId = filters.meetingId;
  formData.status = '0';
  formData.coverImage = '';
  formData.remark = '';
  issueColumnRows.value = [];
  resetUploadLists();
}

async function handleAdd() {
  isEdit.value = false;
  drawerTitle.value = '添加刊期';
  resetForm();
  await loadColumnList();
  buildIssueColumnRows();
  drawerOpen.value = true;
}

async function handleEdit(record: PublisherApi.Issue) {
  isEdit.value = true;
  drawerTitle.value = '修改刊期';
  resetForm();
  Object.assign(formData, {
    issueId: record.issueId,
    yearId: record.yearId,
    journalCode: record.journalCode || 'ddhl',
    year: record.year,
    volume: record.volume,
    period: String(record.period ?? ''),
    sequence: record.sequence,
    issueName: record.issueName || '',
    nameCn: record.nameCn || record.issueName || '',
    nameEn: record.nameEn || '',
    specialName: record.specialName || '',
    specialEnName: record.specialEnName || '',
    important: record.important || '',
    enImportant: record.enImportant || '',
    imageUrl: record.imageUrl || record.coverImage || '',
    largerCoverUrl: record.largerCoverUrl || '',
    contentsFile: record.contentsFile || '',
    cnImageExplain: record.cnImageExplain || '',
    enImageExplain: record.enImageExplain || '',
    requestMoney: record.requestMoney ?? record.isFree ?? '0',
    isFree: record.isFree ?? record.requestMoney ?? '0',
    isLatest: record.isLatest ?? '0',
    printMoney: record.printMoney,
    publishMoney: record.publishMoney,
    postMoney: record.postMoney,
    publishDate: record.publishDate,
    startPage: record.startPage || '',
    endPage: record.endPage || '',
    columnsInfo: record.columnsInfo || '',
    meetingId: record.meetingId,
    status: record.status ?? '0',
    coverImage: record.coverImage || record.imageUrl || '',
    remark: record.remark || '',
  });
  await loadColumnList();
  buildIssueColumnRows(record.columnsInfo || '');
  drawerOpen.value = true;
}

async function handleDelete(record: PublisherApi.Issue) {
  await publisherApi.deleteIssue([record.issueId!]);
  message.success('删除成功');
  fetchList();
}

function onYearChange(value: number) {
  const matched = yearOptions.value.find((item) => item.value === value);
  formData.yearId = matched?.yearId;
  if (matched?.volume != null && formData.volume == null) {
    formData.volume = matched.volume;
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
    (formData as Record<UploadField, string | undefined>)[field] = filePath;
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

function buildPayload(): PublisherApi.Issue | null {
  const journalCode = formData.journalCode?.trim();
  const period = String(formData.period ?? '').trim();
  if (!journalCode) {
    message.warning('请选择杂志名称');
    return null;
  }
  if (!formData.year) {
    message.warning('请选择年份');
    return null;
  }
  if (!period) {
    message.warning('请填写刊期');
    return null;
  }
  const nameCn = formData.nameCn?.trim() || `第${period}期`;
  const nameEn = formData.nameEn?.trim() || `No.${period}`;
  const requestMoney = formData.requestMoney ?? '0';
  const imageUrl = formData.imageUrl?.trim() || '';
  return {
    ...formData,
    journalCode,
    period,
    issueName: formData.issueName?.trim() || nameCn,
    nameCn,
    nameEn,
    specialName: formData.specialName?.trim() || '',
    specialEnName: formData.specialEnName?.trim() || '',
    imageUrl,
    largerCoverUrl: formData.largerCoverUrl?.trim() || '',
    contentsFile: formData.contentsFile?.trim() || '',
    requestMoney,
    isFree: requestMoney,
    coverImage: imageUrl,
    startPage: formData.startPage?.trim() || '',
    endPage: formData.endPage?.trim() || '',
    columnsInfo: serializeIssueColumns(),
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
      await publisherApi.updateIssue(payload);
      message.success('修改成功');
    } else {
      await publisherApi.createIssue(payload);
      message.success('添加成功');
    }
    drawerOpen.value = false;
    fetchList();
  } finally {
    submitting.value = false;
  }
}

async function handleAction(
  action: 'baidu' | 'cstr' | 'doi' | 'subscribe',
  record: PublisherApi.Issue,
) {
  const labels = {
    subscribe: '订阅发送',
    doi: 'DOI注册',
    cstr: 'CSTR注册',
    baidu: '百度发布',
  };
  await publisherApi.executeIssueAction(action, record.issueId!);
  message.success(`${labels[action]}成功`);
  fetchList();
}

function handleTableChange(pag: { current?: number; pageSize?: number }) {
  pagination.current = pag.current ?? 1;
  pagination.pageSize = pag.pageSize ?? pagination.pageSize;
  fetchList();
}

async function loadMeetings() {
  try {
    const res: any = await meetingApi.getMeetingList({
      pageNum: 1,
      pageSize: 200,
    });
    meetingOptions.value = (res?.rows ?? res?.data?.rows ?? [])
      .map((r: any) => ({
        value: r.meetingId as number,
        label: (r.meetingName || r.title || `会议#${r.meetingId}`) as string,
      }))
      .filter((m: { value: number }) => m.value != null);
  } catch {
    meetingOptions.value = [];
  }
}

async function loadYears() {
  try {
    const res: any = await publisherApi.getYearList({
      pageNum: 1,
      pageSize: 200,
    });
    const rows = res?.rows ?? res?.data?.rows ?? [];
    yearOptions.value = rows
      .map((r: PublisherApi.Year) => ({
        value: r.year,
        label: `${r.year}年`,
        yearId: r.yearId,
        volume: r.volume,
      }))
      .filter((item: { value: number }) => item.value != null);
    if (!yearOptions.value.some((item) => item.value === currentYear)) {
      yearOptions.value.unshift({ value: currentYear, label: `${currentYear}年` });
    }
  } catch {
    yearOptions.value = [{ value: currentYear, label: `${currentYear}年` }];
  }
}

onMounted(() => {
  loadMeetings();
  loadYears();
  fetchList();
});
</script>

<template>
  <div class="p-4">
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
              @press-enter="handleSearch"
            />
          </a-form-item>
          <a-form-item>
            <a-space>
              <a-button type="primary" @click="handleSearch">查询</a-button>
              <a-button @click="handleReset">重置</a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </div>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      :scroll="{ x: 1600 }"
      row-key="issueId"
      size="middle"
      @change="handleTableChange"
    >
      <template #emptyText>
        <a-empty description="未查询到任何数据" />
      </template>

      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'requestMoney'">
          {{ yesNo(record.requestMoney ?? record.isFree) }}
        </template>
        <template v-else-if="column.key === 'isLatest'">
          {{ yesNo(record.isLatest) }}
        </template>
        <template v-else-if="column.key === 'nameCn'">
          {{ record.nameCn || record.issueName || '-' }}
        </template>
        <template v-else-if="column.key === 'subscribe'">
          <a-button size="small" class="action-btn" @click="handleAction('subscribe', record)">
            发送
          </a-button>
        </template>
        <template v-else-if="column.key === 'doi'">
          <a-button size="small" class="action-btn" @click="handleAction('doi', record)">
            注册
          </a-button>
        </template>
        <template v-else-if="column.key === 'cstr'">
          <a-button size="small" class="action-btn" @click="handleAction('cstr', record)">
            注册
          </a-button>
        </template>
        <template v-else-if="column.key === 'baidu'">
          <a-button size="small" class="action-btn" @click="handleAction('baidu', record)">
            发送
          </a-button>
        </template>
        <template v-else-if="column.key === 'action'">
          <VbenTableAction
            :actions="[
              { text: '修改', icon: 'lucide:edit', onClick: () => handleEdit(record) },
              {
                text: '删除',
                icon: 'lucide:trash-2',
                danger: true,
                popConfirm: {
                  title: '确认删除该刊期吗？',
                  confirm: () => handleDelete(record),
                },
              },
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
      <a-button type="primary" @click="columnDrawerOpen = true">
        <IconifyIcon icon="lucide:settings-2" class="mr-1 size-3.5" />
        显示设置
      </a-button>
    </div>

    <a-drawer
      v-model:open="drawerOpen"
      :title="drawerTitle"
      placement="right"
      :width="760"
      destroy-on-close
      class="issue-drawer"
    >
      <a-form layout="vertical" class="issue-form">
        <a-divider orientation="left">基础信息</a-divider>
        <div class="form-grid">
          <a-form-item label="杂志名称" required>
            <a-select
              v-model:value="formData.journalCode"
              :options="magazineOptions"
              placeholder="请选择杂志"
            />
          </a-form-item>
          <a-form-item label="年份" required>
            <a-select
              v-model:value="formData.year"
              :options="yearOptions"
              placeholder="请选择年份"
              show-search
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
          <a-form-item
            label="刊期"
            required
            extra="可以是数字，也可以是字符。例：第1期输入 1，增刊可直接输入字符。"
          >
            <a-input v-model:value="formData.period" allow-clear />
          </a-form-item>
          <a-form-item label="顺序">
            <a-input-number
              v-model:value="formData.sequence"
              :min="0"
              :max="9999"
              style="width: 100%"
            />
          </a-form-item>
          <a-form-item label="会议">
            <a-select
              v-model:value="formData.meetingId"
              allow-clear
              placeholder="请选择会议"
              :options="meetingOptions"
            />
          </a-form-item>
        </div>

        <a-divider orientation="left">名称与重点</a-divider>
        <div class="form-grid">
          <a-form-item label="刊期中文名" extra="例：第1期">
            <a-input v-model:value="formData.nameCn" allow-clear />
          </a-form-item>
          <a-form-item label="刊期英文名" extra="Example: No.1">
            <a-input v-model:value="formData.nameEn" allow-clear />
          </a-form-item>
          <a-form-item label="专辑中文名">
            <a-input v-model:value="formData.specialName" allow-clear />
          </a-form-item>
          <a-form-item label="专辑英文名">
            <a-input v-model:value="formData.specialEnName" allow-clear />
          </a-form-item>
        </div>
        <a-form-item label="重点(中文)">
          <a-textarea v-model:value="formData.important" :rows="3" />
        </a-form-item>
        <a-form-item label="重点(英文)">
          <a-textarea v-model:value="formData.enImportant" :rows="3" />
        </a-form-item>

        <a-divider orientation="left">封面与目录</a-divider>
        <a-form-item label="封面(小图)" extra="可上传或填写本期封面地址">
          <div class="file-picker">
            <a-input v-model:value="formData.imageUrl" allow-clear />
            <a-upload
              :before-upload="makeUploadBefore('imageUrl')"
              :file-list="uploadFileLists.imageUrl"
              :max-count="1"
              :show-upload-list="false"
              accept="image/*"
            >
              <a-button>
                <IconifyIcon icon="lucide:folder-open" class="mr-1 size-3.5" />
                浏览...
              </a-button>
            </a-upload>
          </div>
        </a-form-item>
        <a-form-item label="封面(大图)" extra="可上传或填写本期封面地址">
          <div class="file-picker">
            <a-input v-model:value="formData.largerCoverUrl" allow-clear />
            <a-upload
              :before-upload="makeUploadBefore('largerCoverUrl')"
              :file-list="uploadFileLists.largerCoverUrl"
              :max-count="1"
              :show-upload-list="false"
              accept="image/*"
            >
              <a-button>
                <IconifyIcon icon="lucide:folder-open" class="mr-1 size-3.5" />
                浏览...
              </a-button>
            </a-upload>
          </div>
        </a-form-item>
        <a-form-item label="目录文件" extra="如果需要给读者提供目录下载，可在此上传目录文件">
          <div class="file-picker">
            <a-input v-model:value="formData.contentsFile" allow-clear />
            <a-upload
              :before-upload="makeUploadBefore('contentsFile')"
              :file-list="uploadFileLists.contentsFile"
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
        <a-form-item label="封面说明(中文)">
          <a-textarea v-model:value="formData.cnImageExplain" :rows="3" />
        </a-form-item>
        <a-form-item label="封面说明(英文)">
          <a-textarea v-model:value="formData.enImageExplain" :rows="3" />
        </a-form-item>

        <a-divider orientation="left">发布与费用</a-divider>
        <div class="form-grid">
          <a-form-item label="免费下载？">
            <a-switch
              v-model:checked="formData.requestMoney"
              checked-value="1"
              un-checked-value="0"
            />
          </a-form-item>
          <a-form-item label="是最新一期文章？">
            <a-switch
              v-model:checked="formData.isLatest"
              checked-value="1"
              un-checked-value="0"
            />
          </a-form-item>
          <a-form-item label="印刷费">
            <a-input-number
              v-model:value="formData.printMoney"
              :min="0"
              :precision="2"
              style="width: 100%"
            />
          </a-form-item>
          <a-form-item label="发行费">
            <a-input-number
              v-model:value="formData.publishMoney"
              :min="0"
              :precision="2"
              style="width: 100%"
            />
          </a-form-item>
          <a-form-item label="邮费">
            <a-input-number
              v-model:value="formData.postMoney"
              :min="0"
              :precision="2"
              style="width: 100%"
            />
          </a-form-item>
          <a-form-item label="出版日期">
            <a-date-picker
              v-model:value="formData.publishDate"
              value-format="YYYY-MM-DD"
              style="width: 100%"
              allow-clear
            />
          </a-form-item>
        </div>

        <a-divider orientation="left">页码与栏目</a-divider>
        <div class="form-grid">
          <a-form-item
            label="起始页码"
            extra="本期文章第一篇文章的起始页码或者文献号"
          >
            <a-input v-model:value="formData.startPage" allow-clear />
          </a-form-item>
          <a-form-item
            label="截止页码"
            extra="本期文章最后一篇文章的截止页码或者文献号"
          >
            <a-input v-model:value="formData.endPage" allow-clear />
          </a-form-item>
        </div>
        <a-form-item label="本期栏目">
          <div class="issue-columns">
            <div class="issue-columns__header">
              <span>栏目名称</span>
              <span>显示顺序</span>
              <span>文章数</span>
            </div>
            <div
              v-for="row in issueColumnRows"
              :key="row.key"
              class="issue-columns__row"
            >
              <label class="issue-columns__name">
                <a-checkbox v-model:checked="row.selected" />
                <span>{{ row.columnName }}</span>
              </label>
              <a-input-number
                v-model:value="row.sequence"
                :min="0"
                :disabled="!row.selected"
                style="width: 100%"
              />
              <a-input-number
                v-model:value="row.articleCount"
                :min="0"
                :disabled="!row.selected"
                style="width: 100%"
              />
            </div>
            <div
              v-if="!issueColumnRows.length"
              class="issue-columns__empty text-muted-foreground text-sm"
            >
              暂无栏目，请先在栏目管理中添加
            </div>
          </div>
        </a-form-item>
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
.action-btn {
  color: #5b6abf;
  background: #eef0fb;
  border-color: #d5daf3;
}

.action-btn:hover {
  color: #3f51b5 !important;
  background: #e4e8f8 !important;
  border-color: #c5cceb !important;
}

.batch-actions :deep(.ant-btn) {
  min-width: 7rem;
}

.issue-form {
  padding-bottom: 1rem;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  column-gap: 1rem;
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

.issue-columns {
  width: 100%;
  overflow: hidden;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
}

.issue-columns__header,
.issue-columns__row {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 120px 120px;
  gap: 12px;
  align-items: center;
  padding: 8px 12px;
}

.issue-columns__header {
  font-weight: 500;
  color: rgb(0 0 0 / 65%);
  background: #fafafa;
  border-bottom: 1px solid #f0f0f0;
}

.issue-columns__row + .issue-columns__row {
  border-top: 1px solid #f0f0f0;
}

.issue-columns__name {
  display: flex;
  gap: 8px;
  align-items: center;
  margin: 0;
  cursor: pointer;
}

.issue-columns__empty {
  padding: 12px;
}

@media (max-width: 768px) {
  .form-grid,
  .file-picker {
    grid-template-columns: 1fr;
  }
}
</style>
