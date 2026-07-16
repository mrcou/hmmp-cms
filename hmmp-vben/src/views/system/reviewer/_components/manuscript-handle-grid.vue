<script lang="ts" setup>
/**
 * 稿件处理列表 Grid —— 新收稿 / 新分配 / 已修回 共用
 * 布局：标题 + 状态筛选 + 高级检索（单行）+ 可展开表格 + 批量操作
 */
import type { EditorApi } from '#/api/biz/editor';

import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';

import { message } from 'antdv-next';
import { IconifyIcon } from '@vben/icons';

import * as editorApi from '#/api/biz/editor';

import ManuscriptExpandPanel from './manuscript-expand-panel.vue';
import ReviewerPageShell from './reviewer-page-shell.vue';

defineOptions({ name: 'ReviewerManuscriptHandleGrid' });

const props = defineProps<{
  listType: 'new' | 'assign' | 'resubmitted';
}>();

const router = useRouter();

/** 与后端 editor_manuscript.status 一致 */
const statusMap: Record<string, { color: string; label: string }> = {
  '01': { label: '新收稿', color: 'blue' },
  '02': { label: '新分配', color: 'cyan' },
  '03': { label: '审稿中', color: 'orange' },
  '04': { label: '已退修', color: 'purple' },
  '05': { label: '已修回', color: 'lime' },
  '06': { label: '已采用', color: 'green' },
  '07': { label: '已退稿', color: 'red' },
  '08': { label: '已发表', color: 'geekblue' },
  '09': { label: '已归档', color: 'default' },
};

const listTypeConfig = {
  new: {
    title: '新收稿',
    defaultStatus: '01',
    emptyText: '暂无新收稿件',
  },
  assign: {
    title: '新分配',
    defaultStatus: '02',
    emptyText: '暂无新分配稿件',
  },
  resubmitted: {
    title: '已修回',
    defaultStatus: '05',
    emptyText: '暂无已修回稿件',
  },
} as const;

const pageMeta = computed(() => listTypeConfig[props.listType]);

const statusFilters = [
  { key: '01', label: '新收稿', path: '/reviewer/new' },
  { key: '02', label: '新分配', path: '/reviewer/assign' },
  { key: '04', label: '已退修' },
  { key: '05', label: '已修回', path: '/reviewer/resubmitted' },
  { key: '03', label: '审稿中' },
  { key: '06', label: '已采用' },
  { key: '07', label: '已退稿' },
  { key: '08', label: '已发表' },
  { key: '', label: '稿件查询' },
  { key: '09', label: '归档查询' },
];

const activeStatus = ref(pageMeta.value.defaultStatus);
const advancedOpen = ref(false);

interface SearchRow {
  id: number;
  logic: 'and' | 'or' | 'not';
  field: string;
  match: 'like' | 'eq' | 'range';
  value: string;
  range: [string, string] | null;
}

let searchRowSeq = 1;
const searchRows = ref<SearchRow[]>([
  {
    id: searchRowSeq++,
    logic: 'and',
    field: 'real_file_no',
    match: 'like',
    value: '',
    range: null,
  },
]);

/** 高级检索字段：与旧系统选项对齐 */
const searchFields = [
  { value: 'real_file_no', label: '稿件编号', match: 'like' as const },
  { value: 'title', label: '标题', match: 'like' as const },
  { value: 'post_real_name', label: '投稿作者', match: 'like' as const },
  { value: 'conclusion_name', label: '稿件状态', match: 'like' as const },
  { value: 'column_name', label: '栏目名称', match: 'like' as const },
  { value: 'edit_real_name', label: '责任编辑', match: 'like' as const },
  { value: 'upload_copyright_status', label: '版权协议', match: 'eq' as const },
  { value: 'receive_time', label: '投稿时间', match: 'range' as const },
  { value: 'magazine_name', label: '期刊名称', match: 'like' as const },
  { value: 'urgency', label: '加急程度', match: 'eq' as const },
];

const copyrightOptions = [
  { label: '已上传', value: '1' },
  { label: '未上传', value: '0' },
];

const urgencyOptions = [
  { label: '普通', value: '0' },
  { label: '加急', value: '1' },
  { label: '特急', value: '2' },
];

function addSearchRow() {
  searchRows.value.push({
    id: searchRowSeq++,
    logic: 'and',
    field: 'title',
    match: 'like',
    value: '',
    range: null,
  });
}

function removeSearchRow(id: number) {
  if (searchRows.value.length <= 1) return;
  searchRows.value = searchRows.value.filter((r) => r.id !== id);
}

function onFieldChange(row: SearchRow) {
  const meta = searchFields.find((f) => f.value === row.field);
  row.match = meta?.match === 'range' ? 'range' : meta?.match === 'eq' ? 'eq' : 'like';
  row.value = '';
  row.range = null;
}

function isSelectValueField(field: string) {
  return (
    field === 'conclusion_name' ||
    field === 'upload_copyright_status' ||
    field === 'urgency'
  );
}

const tableData = ref<EditorApi.Manuscript[]>([]);
const tableLoading = ref(false);
const selectedKeys = ref<number[]>([]);
const selectedRows = ref<EditorApi.Manuscript[]>([]);

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  showTotal: (total: number, range: [number, number]) =>
    `当前显示 ${range[0]}-${range[1]} 条，共 ${total} 条记录`,
});

const allColumns = [
  { key: 'fileNo', title: '稿件编号', dataIndex: 'fileNo', width: 150 },
  { key: 'title', title: '标题', dataIndex: 'title', ellipsis: true, width: 260 },
  { key: 'authorNames', title: '投稿作者', dataIndex: 'authorNames', width: 140 },
  { key: 'receiveTime', title: '投稿时间', dataIndex: 'receiveTime', width: 120 },
  { key: 'status', title: '稿件状态', dataIndex: 'status', width: 100 },
  { key: 'columnName', title: '栏目名称', dataIndex: 'columnName', width: 120 },
  { key: 'urgency', title: '加急程度', dataIndex: 'urgency', width: 100 },
  { key: 'action', title: '操作', width: 200, fixed: 'right' as const },
];

const visibleColumnKeys = ref(
  allColumns.filter((c) => c.key !== 'action').map((c) => c.key),
);

const columns = computed(() => {
  const visible = new Set([...visibleColumnKeys.value, 'action']);
  return allColumns.filter((c) => visible.has(c.key));
});

const rowSelection = computed(() => ({
  selectedRowKeys: selectedKeys.value,
  onChange: (keys: (string | number)[], rows: EditorApi.Manuscript[]) => {
    selectedKeys.value = keys.map(Number);
    selectedRows.value = rows;
  },
}));

const reviewModalOpen = ref(false);
const reviewLoading = ref(false);
const reviewForm = reactive({
  manuscriptIds: [] as number[],
  reviewerId: undefined as number | undefined,
  reviewRound: 1,
  deadlineTime: '',
  inviteMessage: '',
});

const rejectLoading = ref(false);
const cnkiModalOpen = ref(false);
const cnkiFileList = ref<any[]>([]);
const columnDrawerOpen = ref(false);
const detailOpen = ref(false);
const detailLoading = ref(false);
const detailData = ref<EditorApi.Manuscript | null>(null);

const uploadModalOpen = ref(false);
const uploadFileList = ref<any[]>([]);
const uploadTarget = ref<EditorApi.Manuscript | null>(null);

const editInfoOpen = ref(false);
const editInfoSaving = ref(false);
const editInfoForm = reactive({
  manuscriptId: undefined as number | undefined,
  title: '',
  keywords: '',
  abstractText: '',
  correspondingAuthor: '',
  correspondingEmail: '',
});

watch(
  () => props.listType,
  () => {
    activeStatus.value = listTypeConfig[props.listType].defaultStatus;
    pagination.current = 1;
    selectedKeys.value = [];
    selectedRows.value = [];
    loadTable();
  },
);

onMounted(() => {
  loadTable();
});

function buildSearchParams(): EditorApi.ManuscriptListParams {
  const params: EditorApi.ManuscriptListParams = {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  };

  if (activeStatus.value) {
    params.status = activeStatus.value;
  }

  for (const row of searchRows.value) {
    // 「非」暂在前端标记；后端目前按 AND 合并条件
    if (row.field === 'receive_time') {
      if (row.range?.[0] && row.range?.[1]) {
        params.beginTime = row.range[0];
        params.endTime = row.range[1];
      }
      continue;
    }
    if (!row.value?.trim()) continue;
    const val = row.value.trim();
    switch (row.field) {
      case 'real_file_no':
        params.fileNo = val;
        break;
      case 'title':
        params.title = val;
        break;
      case 'post_real_name':
        params.authorNames = val;
        break;
      case 'conclusion_name': {
        // 支持按状态码或状态名检索
        const byLabel = Object.entries(statusMap).find(([, m]) => m.label === val);
        params.status = byLabel?.[0] ?? val;
        break;
      }
      default:
        break;
    }
  }

  return params;
}

async function loadTable() {
  tableLoading.value = true;
  try {
    const res = (await editorApi.getManuscriptList(buildSearchParams())) as any;
    const data = res?.data ?? res;
    tableData.value = data?.rows ?? data?.list ?? [];
    pagination.total = data?.total ?? 0;
  } catch {
    message.error('加载稿件列表失败');
    tableData.value = [];
    pagination.total = 0;
  } finally {
    tableLoading.value = false;
  }
}

function onStatusFilter(item: (typeof statusFilters)[number]) {
  if (item.path && item.path !== router.currentRoute.value.path) {
    router.push(item.path);
    return;
  }
  activeStatus.value = item.key;
  pagination.current = 1;
  selectedKeys.value = [];
  selectedRows.value = [];
  loadTable();
}

function onSearch() {
  pagination.current = 1;
  loadTable();
}

function onCloseAdvanced() {
  advancedOpen.value = false;
}

function onResetSearch() {
  searchRows.value = [
    {
      id: searchRowSeq++,
      logic: 'and',
      field: 'real_file_no',
      match: 'like',
      value: '',
      range: null,
    },
  ];
  pagination.current = 1;
  loadTable();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  loadTable();
}

function getStatusTag(status?: string) {
  return statusMap[status ?? ''] ?? { label: status ?? '未知', color: 'default' };
}

function requireSelection(): EditorApi.Manuscript[] | null {
  if (!selectedRows.value.length) {
    message.warning('请先勾选稿件');
    return null;
  }
  return selectedRows.value;
}

function onBatchReview() {
  const rows = requireSelection();
  if (!rows) return;
  reviewForm.manuscriptIds = rows.map((r) => r.manuscriptId!);
  reviewForm.reviewerId = undefined;
  reviewForm.reviewRound = 1;
  reviewForm.deadlineTime = '';
  reviewForm.inviteMessage = '';
  reviewModalOpen.value = true;
}

async function onReviewSubmit() {
  if (!reviewForm.reviewerId) {
    message.warning('请填写审稿人ID');
    return;
  }
  reviewLoading.value = true;
  try {
    for (const id of reviewForm.manuscriptIds) {
      await editorApi.sendForReview({
        manuscriptId: id,
        reviewerId: reviewForm.reviewerId,
      } as any);
    }
    message.success(`已送审 ${reviewForm.manuscriptIds.length} 篇稿件`);
    reviewModalOpen.value = false;
    selectedKeys.value = [];
    selectedRows.value = [];
    loadTable();
  } catch {
    message.error('送审失败');
  } finally {
    reviewLoading.value = false;
  }
}

function onBatchDownload() {
  const rows = requireSelection();
  if (!rows) return;
  const withFile = rows.filter((r) => r.filePath);
  if (!withFile.length) {
    message.warning('所选稿件暂无原文路径');
    return;
  }
  for (const row of withFile) {
    window.open(row.filePath!, '_blank');
  }
  message.success(`已打开 ${withFile.length} 个原文链接`);
}

function onOpenCnki() {
  const rows = requireSelection();
  if (!rows) return;
  if (rows.length !== 1) {
    message.warning('上传 CNKI 报告请只选择一篇稿件');
    return;
  }
  cnkiFileList.value = [];
  cnkiModalOpen.value = true;
}

function onCnkiOk() {
  if (!cnkiFileList.value.length) {
    message.warning('请选择检测报告文件');
    return;
  }
  message.success('CNKI 检测报告已提交（待后端存储接口对接）');
  cnkiModalOpen.value = false;
}

async function onBatchReject() {
  const rows = requireSelection();
  if (!rows) return;
  rejectLoading.value = true;
  try {
    for (const row of rows) {
      await editorApi.rejectManuscript({ manuscriptId: row.manuscriptId! });
    }
    message.success(`已退稿 ${rows.length} 篇`);
    selectedKeys.value = [];
    selectedRows.value = [];
    loadTable();
  } catch {
    message.error('退稿失败');
  } finally {
    rejectLoading.value = false;
  }
}

function onPrintLabels() {
  const rows = requireSelection();
  if (!rows) return;
  const html = rows
    .map(
      (r) =>
        `<div style="border:1px solid #333;padding:12px;margin:8px;width:280px;font-family:sans-serif">
          <div><b>编号：</b>${r.fileNo ?? '-'}</div>
          <div><b>标题：</b>${r.title ?? '-'}</div>
          <div><b>作者：</b>${r.authorNames ?? '-'}</div>
          <div><b>状态：</b>${getStatusTag(r.status).label}</div>
        </div>`,
    )
    .join('');
  const win = window.open('', '_blank');
  if (!win) {
    message.error('浏览器拦截了打印窗口');
    return;
  }
  win.document.write(
    `<html><head><title>打印标签</title></head><body onload="window.print()">${html}</body></html>`,
  );
  win.document.close();
}

async function onExport() {
  try {
    await editorApi.exportManuscriptList(buildSearchParams());
  } catch {
    message.error('导出失败');
  }
}

function onRowReview(row: EditorApi.Manuscript) {
  selectedKeys.value = [row.manuscriptId!];
  selectedRows.value = [row];
  onBatchReview();
}

async function onRowReject(row: EditorApi.Manuscript) {
  try {
    await editorApi.rejectManuscript({ manuscriptId: row.manuscriptId! });
    message.success('退稿成功');
    loadTable();
  } catch {
    message.error('退稿失败');
  }
}

async function onRowRevise(row: EditorApi.Manuscript) {
  try {
    await editorApi.sendForRevision({ manuscriptId: row.manuscriptId! });
    message.success('已退修');
    loadTable();
  } catch {
    message.error('退修失败');
  }
}

function onRowDownload(row: EditorApi.Manuscript) {
  if (!row.filePath) {
    message.warning('该稿件暂无原文路径');
    return;
  }
  window.open(row.filePath, '_blank');
}

function onRowEditFile(row: EditorApi.Manuscript) {
  uploadTarget.value = row;
  uploadFileList.value = [];
  uploadModalOpen.value = true;
  message.info('请上传替换后的稿件文件');
}

function onRowUpload(row: EditorApi.Manuscript) {
  uploadTarget.value = row;
  uploadFileList.value = [];
  uploadModalOpen.value = true;
}

function onUploadOk() {
  if (!uploadFileList.value.length) {
    message.warning('请选择要上传的文件');
    return;
  }
  message.success(
    `稿件「${uploadTarget.value?.fileNo ?? ''}」文件已提交（待后端存储接口对接）`,
  );
  uploadModalOpen.value = false;
}

function onRowEditInfo(row: EditorApi.Manuscript) {
  editInfoForm.manuscriptId = row.manuscriptId;
  editInfoForm.title = row.title ?? '';
  editInfoForm.keywords = row.keywords ?? '';
  editInfoForm.abstractText = row.abstractText ?? '';
  editInfoForm.correspondingAuthor = row.correspondingAuthor ?? '';
  editInfoForm.correspondingEmail = row.correspondingEmail ?? '';
  editInfoOpen.value = true;
}

async function onEditInfoSubmit() {
  if (!editInfoForm.manuscriptId) return;
  if (!editInfoForm.title.trim()) {
    message.warning('请填写标题');
    return;
  }
  editInfoSaving.value = true;
  try {
    await editorApi.updateManuscript({
      manuscriptId: editInfoForm.manuscriptId,
      title: editInfoForm.title.trim(),
      keywords: editInfoForm.keywords,
      abstractText: editInfoForm.abstractText,
      correspondingAuthor: editInfoForm.correspondingAuthor,
      correspondingEmail: editInfoForm.correspondingEmail,
    } as EditorApi.Manuscript);
    message.success('稿件信息已更新');
    editInfoOpen.value = false;
    loadTable();
  } catch {
    message.error('更新失败');
  } finally {
    editInfoSaving.value = false;
  }
}

async function onViewDetail(row: EditorApi.Manuscript) {
  detailLoading.value = true;
  detailOpen.value = true;
  try {
    const res = (await editorApi.getManuscript(row.manuscriptId!)) as any;
    detailData.value = res?.data ?? res ?? row;
  } catch {
    detailData.value = row;
  } finally {
    detailLoading.value = false;
  }
}

function formatDate(val?: string) {
  if (!val) return '-';
  return String(val).slice(0, 10);
}

function beforeUpload() {
  return false;
}
</script>

<template>
  <ReviewerPageShell>
    <template #header>
      <div class="flex flex-wrap items-center gap-x-4 gap-y-2">
        <h1 class="text-foreground m-0 shrink-0 text-lg font-semibold tracking-tight">
          {{ pageMeta.title }}
          <span class="text-muted-foreground ml-1 text-sm font-normal">
            （{{ pagination.total }}）
          </span>
        </h1>
        <nav class="flex min-w-0 flex-1 flex-wrap items-center gap-x-3 gap-y-1">
          <a
            v-for="item in statusFilters"
            :key="item.key || item.label"
            href="javascript:;"
            class="status-link"
            :class="{ active: activeStatus === item.key }"
            @click.prevent="onStatusFilter(item)"
          >
            {{ item.label }}
          </a>
        </nav>
        <a-button
          type="primary"
          size="small"
          class="shrink-0"
          @click="advancedOpen = !advancedOpen"
        >
          <template #icon>
            <IconifyIcon icon="lucide:search" class="size-3.5" />
          </template>
          高级检索
        </a-button>
      </div>
    </template>

    <div
      v-show="advancedOpen"
      class="bg-card mb-3 rounded-md border px-3 py-3"
    >
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

        <a-select v-model:value="row.logic" style="width: 72px" size="small">
          <a-select-option value="and">与</a-select-option>
          <a-select-option value="or">或</a-select-option>
          <a-select-option value="not">非</a-select-option>
        </a-select>

        <a-select
          v-model:value="row.field"
          style="width: 130px"
          size="small"
          @change="onFieldChange(row)"
        >
          <a-select-option
            v-for="f in searchFields"
            :key="f.value"
            :value="f.value"
          >
            {{ f.label }}
          </a-select-option>
        </a-select>

        <template v-if="row.match === 'range'">
          <span class="text-muted-foreground text-xs">从</span>
          <a-range-picker
            v-model:value="row.range"
            value-format="YYYY-MM-DD"
            size="small"
            style="width: 260px"
          />
        </template>
        <template v-else>
          <a-select
            v-if="!isSelectValueField(row.field)"
            v-model:value="row.match"
            style="width: 90px"
            size="small"
          >
            <a-select-option value="like">模糊</a-select-option>
            <a-select-option value="eq">精确</a-select-option>
          </a-select>

          <a-select
            v-if="row.field === 'conclusion_name'"
            v-model:value="row.value"
            allow-clear
            placeholder="请选择状态"
            size="small"
            style="width: 180px"
          >
            <a-select-option
              v-for="(meta, code) in statusMap"
              :key="code"
              :value="code"
            >
              {{ meta.label }}
            </a-select-option>
          </a-select>
          <a-select
            v-else-if="row.field === 'upload_copyright_status'"
            v-model:value="row.value"
            allow-clear
            placeholder="请选择"
            size="small"
            style="width: 180px"
          >
            <a-select-option
              v-for="opt in copyrightOptions"
              :key="opt.value"
              :value="opt.value"
            >
              {{ opt.label }}
            </a-select-option>
          </a-select>
          <a-select
            v-else-if="row.field === 'urgency'"
            v-model:value="row.value"
            allow-clear
            placeholder="请选择"
            size="small"
            style="width: 180px"
          >
            <a-select-option
              v-for="opt in urgencyOptions"
              :key="opt.value"
              :value="opt.value"
            >
              {{ opt.label }}
            </a-select-option>
          </a-select>
          <a-input
            v-else
            v-model:value="row.value"
            size="small"
            allow-clear
            placeholder="请输入搜索词..."
            style="width: 220px"
          />
        </template>
      </div>

      <div class="mt-3 flex justify-end gap-2">
        <a-button type="primary" size="small" @click="onSearch">
          <template #icon>
            <IconifyIcon icon="lucide:search" class="size-3.5" />
          </template>
          检索
        </a-button>
        <a-button size="small" @click="onResetSearch">重置</a-button>
        <a-button size="small" @click="onCloseAdvanced">
          <template #icon>
            <IconifyIcon icon="lucide:x" class="size-3.5" />
          </template>
          关闭
        </a-button>
      </div>
    </div>

    <a-card :bordered="false" class="shadow-sm">
      <a-table
        :columns="columns"
        :data-source="tableData"
        :loading="tableLoading"
        :pagination="pagination"
        :row-selection="rowSelection"
        :scroll="{ x: 1100 }"
        row-key="manuscriptId"
        size="middle"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #emptyText>
          <a-empty :description="pageMeta.emptyText" />
        </template>

        <template #expandIcon="expandProps">
          <button
            type="button"
            class="expand-btn"
            @click="(e: MouseEvent) => expandProps.onExpand(expandProps.record, e)"
          >
            <IconifyIcon
              :icon="
                expandProps.expanded ? 'lucide:chevron-down' : 'lucide:chevron-right'
              "
              class="text-primary size-4"
            />
          </button>
        </template>

        <template #expandedRowRender="{ record }">
          <ManuscriptExpandPanel
            :record="record"
            @detail="onViewDetail"
            @revise="onRowRevise"
            @reject="onRowReject"
            @review="onRowReview"
            @download="onRowDownload"
            @edit-file="onRowEditFile"
            @upload="onRowUpload"
            @edit-info="onRowEditInfo"
          />
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'title'">
            <div class="flex items-start gap-2">
              <a
                class="text-primary min-w-0 flex-1 cursor-pointer"
                @click.prevent="onViewDetail(record)"
              >
                {{ record.title }}
              </a>
              <a
                class="text-primary inline-flex shrink-0 items-center gap-0.5 whitespace-nowrap text-xs"
                href="javascript:;"
                @click.prevent="onViewDetail(record)"
              >
                详情
                <IconifyIcon icon="lucide:circle-arrow-right" class="size-3.5" />
              </a>
            </div>
          </template>
          <template v-else-if="column.key === 'authorNames'">
            <span class="inline-flex items-center gap-1">
              {{ record.authorNames || '-' }}
              <IconifyIcon
                v-if="record.authorNames"
                icon="lucide:circle-arrow-right"
                class="size-3.5 text-green-600"
              />
            </span>
          </template>
          <template v-else-if="column.key === 'receiveTime'">
            {{ formatDate(record.receiveTime) }}
          </template>
          <template v-else-if="column.key === 'status'">
            <a-tag :color="getStatusTag(record.status).color">
              {{ getStatusTag(record.status).label }}
            </a-tag>
          </template>
          <template v-else-if="column.key === 'columnName'">
            {{ record.columnName || '-' }}
          </template>
          <template v-else-if="column.key === 'urgency'">
            {{ record.urgency || '-' }}
          </template>
          <template v-else-if="column.key === 'action'">
            <a-space>
              <a-button size="small" type="link" @click="onViewDetail(record)">
                详情
              </a-button>
              <a-button
                v-if="
                  record.status === '01' ||
                  record.status === '02' ||
                  record.status === '05'
                "
                size="small"
                type="link"
                @click="onRowReview(record)"
              >
                送审
              </a-button>
              <a-popconfirm
                title="确认退稿该稿件？"
                @confirm="onRowReject(record)"
              >
                <a-button
                  v-if="record.status !== '07' && record.status !== '08'"
                  size="small"
                  type="link"
                  danger
                >
                  退稿
                </a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>

      <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
        <a-button type="primary" @click="onBatchReview">批量送审</a-button>
        <a-button type="primary" @click="onBatchDownload">批量下载原文</a-button>
        <a-button type="primary" @click="onOpenCnki">上传CNKI检测报告</a-button>
        <a-popconfirm
          title="确认批量退稿所选稿件？"
          :disabled="!selectedKeys.length"
          @confirm="onBatchReject"
        >
          <a-button type="primary" danger :loading="rejectLoading">
            批量退稿
          </a-button>
        </a-popconfirm>
        <a-button type="primary" @click="onPrintLabels">打印标签</a-button>
        <a-button type="primary" @click="onExport">导出数据</a-button>
        <a-button type="primary" ghost @click="columnDrawerOpen = true">
          显示设置
        </a-button>
      </div>
    </a-card>

    <a-modal
      v-model:open="reviewModalOpen"
      title="批量送审"
      :confirm-loading="reviewLoading"
      destroy-on-close
      @ok="onReviewSubmit"
    >
      <a-form layout="vertical">
        <a-form-item label="已选稿件数">
          <span>{{ reviewForm.manuscriptIds.length }}</span>
        </a-form-item>
        <a-form-item label="审稿人ID" required>
          <a-input-number
            v-model:value="reviewForm.reviewerId"
            placeholder="请输入审稿人ID"
            style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="审稿轮次">
          <a-input-number
            v-model:value="reviewForm.reviewRound"
            :min="1"
            style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="截止日期">
          <a-date-picker
            v-model:value="reviewForm.deadlineTime"
            value-format="YYYY-MM-DD"
            style="width: 100%"
            placeholder="请选择截止日期"
          />
        </a-form-item>
        <a-form-item label="邀请信息">
          <a-textarea
            v-model:value="reviewForm.inviteMessage"
            :rows="3"
            placeholder="请输入邀请信息"
          />
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal
      v-model:open="cnkiModalOpen"
      title="上传CNKI检测报告"
      destroy-on-close
      @ok="onCnkiOk"
    >
      <a-upload
        v-model:file-list="cnkiFileList"
        :before-upload="beforeUpload"
        :max-count="1"
        accept=".pdf,.doc,.docx"
      >
        <a-button>
          <IconifyIcon icon="lucide:upload" class="mr-1 size-3.5" />
          选择文件
        </a-button>
      </a-upload>
      <p class="text-muted-foreground mt-2 text-xs">
        支持 PDF / Word，单次一篇稿件。
      </p>
    </a-modal>

    <a-modal
      v-model:open="uploadModalOpen"
      title="上传稿件"
      destroy-on-close
      @ok="onUploadOk"
    >
      <p v-if="uploadTarget" class="text-muted-foreground mb-3 text-sm">
        稿件：{{ uploadTarget.fileNo }} — {{ uploadTarget.title }}
      </p>
      <a-upload
        v-model:file-list="uploadFileList"
        :before-upload="beforeUpload"
        :max-count="1"
        accept=".pdf,.doc,.docx,.zip"
      >
        <a-button>
          <IconifyIcon icon="lucide:upload" class="mr-1 size-3.5" />
          选择文件
        </a-button>
      </a-upload>
    </a-modal>

    <a-modal
      v-model:open="editInfoOpen"
      title="修改稿件信息"
      :confirm-loading="editInfoSaving"
      destroy-on-close
      @ok="onEditInfoSubmit"
    >
      <a-form layout="vertical">
        <a-form-item label="标题" required>
          <a-input v-model:value="editInfoForm.title" placeholder="请输入标题" />
        </a-form-item>
        <a-form-item label="关键词">
          <a-input
            v-model:value="editInfoForm.keywords"
            placeholder="多个关键词用逗号分隔"
          />
        </a-form-item>
        <a-form-item label="摘要">
          <a-textarea
            v-model:value="editInfoForm.abstractText"
            :rows="4"
            placeholder="请输入摘要"
          />
        </a-form-item>
        <a-form-item label="通讯作者">
          <a-input
            v-model:value="editInfoForm.correspondingAuthor"
            placeholder="通讯作者"
          />
        </a-form-item>
        <a-form-item label="通讯邮箱">
          <a-input
            v-model:value="editInfoForm.correspondingEmail"
            placeholder="通讯邮箱"
          />
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal
      v-model:open="detailOpen"
      title="稿件详情"
      :footer="null"
      width="720px"
      destroy-on-close
    >
      <div v-if="detailLoading" class="py-8 text-center">加载中…</div>
      <div v-else-if="detailData" class="text-sm">
        <div class="grid gap-3 md:grid-cols-2">
          <div>
            <span class="text-muted-foreground">稿件编号：</span>
            {{ detailData.fileNo || '-' }}
          </div>
          <div>
            <span class="text-muted-foreground">状态：</span>
            <a-tag :color="getStatusTag(detailData.status).color">
              {{ getStatusTag(detailData.status).label }}
            </a-tag>
          </div>
          <div class="md:col-span-2">
            <span class="text-muted-foreground">标题：</span>
            {{ detailData.title }}
          </div>
          <div>
            <span class="text-muted-foreground">投稿作者：</span>
            {{ detailData.authorNames || '-' }}
          </div>
          <div>
            <span class="text-muted-foreground">投稿时间：</span>
            {{ formatDate(detailData.receiveTime) }}
          </div>
          <div>
            <span class="text-muted-foreground">通讯作者：</span>
            {{ detailData.correspondingAuthor || '-' }}
          </div>
          <div>
            <span class="text-muted-foreground">通讯邮箱：</span>
            {{ detailData.correspondingEmail || '-' }}
          </div>
          <div>
            <span class="text-muted-foreground">责任编辑：</span>
            {{ detailData.editorName || '-' }}
          </div>
          <div>
            <span class="text-muted-foreground">关键词：</span>
            {{ detailData.keywords || '-' }}
          </div>
        </div>
        <div v-if="detailData.abstractText" class="mt-3">
          <div class="text-muted-foreground mb-1">摘要</div>
          <div class="bg-muted/40 rounded p-3 leading-relaxed">
            {{ detailData.abstractText }}
          </div>
        </div>
      </div>
    </a-modal>

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
        <a-checkbox
          v-for="col in allColumns.filter((c) => c.key !== 'action')"
          :key="col.key"
          :value="col.key"
        >
          {{ col.title }}
        </a-checkbox>
      </a-checkbox-group>
      <div class="mt-6">
        <a-button type="primary" block @click="columnDrawerOpen = false">
          确定
        </a-button>
      </div>
    </a-drawer>
  </ReviewerPageShell>
</template>

<style scoped>
.status-link {
  color: hsl(var(--foreground) / 75%);
  font-size: 0.8125rem;
  line-height: 1.75;
  text-decoration: none;
  transition: color 0.15s ease;
}

.status-link:hover {
  color: hsl(var(--primary));
}

.status-link.active {
  color: hsl(var(--primary));
  font-weight: 600;
}

.expand-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: 0;
  background: transparent;
  padding: 0;
  cursor: pointer;
}

.batch-actions :deep(.ant-btn) {
  min-width: 7rem;
}
</style>
