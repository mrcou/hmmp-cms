<script lang="ts" setup>
/**
 * 邮寄样刊登记 —— 对齐示意图：杂志/会议/稿件编号筛选 + 表格 + 邮签生成/导出/显示设置
 */
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { VbenTableAction } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';

import { message } from 'antdv-next';

import * as meetingApi from '#/api/biz/meeting';
import * as publisherApi from '#/api/biz/publisher';
import {
  JOURNAL_NAME_LABEL,
  useJournalMagazine,
} from '#/composables/use-journal-magazine';

defineOptions({ name: 'PublisherOrderSample' });

type LabelType = 'first' | 'corresponding' | 'submit' | 'payee';

const loading = ref(false);
const submitting = ref(false);
const dataSource = ref<PublisherApi.SamplePost[]>([]);
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
  meetingId: undefined as number | undefined,
  searchField: 'fileNo' as
    | 'fileNo'
    | 'yearNo'
    | 'period'
    | 'submitAuthor'
    | 'firstAuthor'
    | 'payeeName'
    | 'payeeAddress'
    | 'payeeOrg'
    | 'payeeZip'
    | 'payeePhone',
  searchValue: '',
});

const searchFieldOptions = [
  { value: 'fileNo', label: '稿件编号' },
  { value: 'yearNo', label: '年份' },
  { value: 'period', label: '刊期' },
  { value: 'submitAuthor', label: '投稿作者' },
  { value: 'firstAuthor', label: '第一作者' },
  { value: 'payeeName', label: '收款人姓名' },
  { value: 'payeeAddress', label: '收款人地址' },
  { value: 'payeeOrg', label: '收款人单位' },
  { value: 'payeeZip', label: '邮编' },
  { value: 'payeePhone', label: '联系电话' },
];

const { magazineOptions } = useJournalMagazine();

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

/** 高级检索 */
const advancedOpen = ref(false);

interface SearchRow {
  field: string;
  id: number;
  value: string;
}

let searchRowSeq = 1;
function createSearchRow(field = 'fileNo'): SearchRow {
  return { id: searchRowSeq++, field, value: '' };
}
const searchRows = ref<SearchRow[]>([createSearchRow()]);

const advancedFields = [
  { value: 'fileNo', label: '稿件编号' },
  { value: 'yearNo', label: '年份' },
  { value: 'period', label: '刊期' },
  { value: 'submitAuthor', label: '投稿作者' },
  { value: 'firstAuthor', label: '第一作者' },
  { value: 'payeeName', label: '收款人姓名' },
  { value: 'payeeAddress', label: '收款人地址' },
  { value: 'payeeOrg', label: '收款人单位' },
  { value: 'payeeZip', label: '邮编' },
  { value: 'payeePhone', label: '联系电话' },
  { value: 'sampleQty', label: '样刊册数' },
] as const;

const advancedFieldOptions = advancedFields.map((f) => ({
  value: f.value,
  label: f.label,
}));

function addSearchRow() {
  searchRows.value.push(createSearchRow('submitAuthor'));
}

function removeSearchRow(id: number) {
  if (searchRows.value.length <= 1) return;
  searchRows.value = searchRows.value.filter((r) => r.id !== id);
}

function onResetAdvanced() {
  searchRows.value = [createSearchRow()];
  pagination.current = 1;
  fetchList();
}

const allColumns = [
  { key: 'action', title: '操作', dataIndex: 'action', width: 90, fixed: 'left' as const },
  { key: 'fileNo', title: '稿件编号', dataIndex: 'fileNo', width: 130 },
  { key: 'yearNo', title: '年份', dataIndex: 'yearNo', width: 80 },
  { key: 'period', title: '刊期', dataIndex: 'period', width: 80 },
  { key: 'submitAuthor', title: '投稿作者', dataIndex: 'submitAuthor', width: 110 },
  { key: 'firstAuthor', title: '第一作者', dataIndex: 'firstAuthor', width: 110 },
  { key: 'payeeName', title: '收款人姓名', dataIndex: 'payeeName', width: 110 },
  { key: 'payeeAddress', title: '收款人地址', dataIndex: 'payeeAddress', ellipsis: true, width: 200 },
  { key: 'payeeOrg', title: '收款人单位', dataIndex: 'payeeOrg', ellipsis: true, width: 160 },
  { key: 'payeeZip', title: '邮编', dataIndex: 'payeeZip', width: 90 },
  { key: 'payeePhone', title: '联系电话', dataIndex: 'payeePhone', width: 130 },
  { key: 'sampleQty', title: '样刊册数', dataIndex: 'sampleQty', width: 100 },
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
    if (filters.searchField === 'yearNo' || filters.searchField === 'period') {
      const num = Number(keyword);
      if (!Number.isNaN(num)) {
        params[filters.searchField] = num;
      }
    } else {
      params[filters.searchField] = keyword;
    }
  }
  for (const row of searchRows.value) {
    if (!row.value?.trim()) continue;
    if (row.field === 'yearNo' || row.field === 'period' || row.field === 'sampleQty') {
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
    const res = (await publisherApi.getSamplePostList(buildSearchParams())) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载样刊登记列表失败');
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

/** 编辑登记 */
const drawerOpen = ref(false);
const formData = reactive<PublisherApi.SamplePost>({
  articleId: undefined,
  payeeName: '',
  payeeAddress: '',
  payeeOrg: '',
  payeeZip: '',
  payeePhone: '',
  sampleQty: 1,
  remark: '',
});

function handleEdit(record: PublisherApi.SamplePost) {
  Object.assign(formData, {
    sampleId: record.sampleId,
    articleId: record.articleId,
    fileNo: record.fileNo,
    yearNo: record.yearNo,
    period: record.period,
    submitAuthor: record.submitAuthor,
    firstAuthor: record.firstAuthor,
    payeeName: record.payeeName || '',
    payeeAddress: record.payeeAddress || '',
    payeeOrg: record.payeeOrg || '',
    payeeZip: record.payeeZip || '',
    payeePhone: record.payeePhone || '',
    sampleQty: record.sampleQty ?? 1,
    remark: record.remark || '',
  });
  drawerOpen.value = true;
}

async function handleSubmit() {
  if (!formData.articleId) {
    message.warning('缺少文章信息');
    return;
  }
  submitting.value = true;
  try {
    await publisherApi.saveSamplePost({ ...formData });
    message.success('保存成功');
    drawerOpen.value = false;
    fetchList();
  } finally {
    submitting.value = false;
  }
}

function requireSelection(action: string) {
  if (!selectedKeys.value.length) {
    message.warning(`请先勾选要${action}的记录`);
    return false;
  }
  return true;
}

interface LabelInfo {
  name: string;
  address: string;
  org: string;
  zip: string;
  phone: string;
  qty: number;
  fileNo: string;
}

function resolveLabel(record: PublisherApi.SamplePost, type: LabelType): LabelInfo {
  const qty = record.sampleQty ?? 1;
  const base = {
    qty,
    fileNo: record.fileNo || '',
  };
  switch (type) {
    case 'first':
      return {
        ...base,
        name: record.firstAuthor || '',
        address: record.firstAuthorAddress || '',
        org: record.firstAuthorOrg || '',
        zip: record.firstAuthorZip || '',
        phone: record.firstAuthorPhone || '',
      };
    case 'corresponding':
      return {
        ...base,
        name: record.correspondingAuthor || '',
        address: record.correspondingAddress || '',
        org: record.correspondingOrg || '',
        zip: record.correspondingZip || '',
        phone: record.correspondingPhone || '',
      };
    case 'submit':
      return {
        ...base,
        name: record.submitAuthor || '',
        address: record.submitAuthorAddress || '',
        org: record.submitAuthorOrg || '',
        zip: record.submitAuthorZip || '',
        phone: record.submitAuthorPhone || '',
      };
    case 'payee':
    default:
      return {
        ...base,
        name: record.payeeName || '',
        address: record.payeeAddress || '',
        org: record.payeeOrg || '',
        zip: record.payeeZip || '',
        phone: record.payeePhone || '',
      };
  }
}

function escapeHtml(text: string) {
  return text
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

function buildLabelHtml(labels: LabelInfo[], title: string) {
  const blocks = labels
    .map(
      (item) => `
      <div class="label">
        <div class="name">${escapeHtml(item.name || '（未填写姓名）')}</div>
        <div>${escapeHtml(item.org || '')}</div>
        <div>${escapeHtml(item.address || '')}</div>
        <div>邮编：${escapeHtml(item.zip || '')}　电话：${escapeHtml(item.phone || '')}</div>
        <div class="meta">稿件：${escapeHtml(item.fileNo)}　样刊：${item.qty} 册</div>
      </div>`,
    )
    .join('');
  return `<!DOCTYPE html><html><head><meta charset="utf-8"><title>${escapeHtml(title)}</title>
    <style>
      body { font-family: "Microsoft YaHei", sans-serif; padding: 16px; }
      .label { border: 1px dashed #999; padding: 12px 16px; margin-bottom: 16px; width: 320px; line-height: 1.6; }
      .name { font-size: 16px; font-weight: bold; margin-bottom: 4px; }
      .meta { margin-top: 6px; color: #666; font-size: 12px; }
      @media print { .label { page-break-inside: avoid; } }
    </style></head><body><h2>${escapeHtml(title)}</h2>${blocks}</body></html>`;
}

function openPrintWindow(html: string) {
  const win = window.open('', '_blank');
  if (!win) {
    message.error('无法打开打印窗口，请检查浏览器弹窗拦截');
    return;
  }
  win.document.write(html);
  win.document.close();
  win.focus();
  setTimeout(() => win.print(), 300);
}

const labelTitles: Record<LabelType, string> = {
  first: '第一作者邮签',
  corresponding: '通讯作者邮签',
  submit: '投稿作者邮签',
  payee: '稿费收款人邮签',
};

function handleGenerateLabels(type: LabelType) {
  if (!requireSelection('生成邮签')) return;
  const selected = dataSource.value.filter((row) =>
    selectedKeys.value.includes(row.articleId!),
  );
  const labels = selected.map((row) => resolveLabel(row, type));
  const emptyNames = labels.filter((l) => !l.name?.trim());
  if (emptyNames.length === labels.length) {
    message.warning('所选记录缺少对应作者/收款人信息');
    return;
  }
  openPrintWindow(buildLabelHtml(labels, labelTitles[type]));
}

async function handleExport() {
  try {
    await publisherApi.exportSamplePost(buildSearchParams());
  } catch {
    message.error('导出失败');
  }
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
        />

        <a-input
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
      <!-- 顶部筛选 -->
      <div class="mb-4 flex items-center justify-between">
        <div class="flex items-center justify-end">
          <a-form layout="inline">
            <a-form-item :label="JOURNAL_NAME_LABEL">
              <a-select
                v-model:value="filters.journalCode"
                allow-clear
                :placeholder="`--${JOURNAL_NAME_LABEL}--`"
                style="width: 160px"
                :options="magazineOptions"
              />
            </a-form-item>
            <a-form-item label="会议">
              <a-select
                v-model:value="filters.meetingId"
                allow-clear
                placeholder="--会议--"
                style="width: 160px"
                :options="meetingOptions"
              />
            </a-form-item>
            <a-form-item>
              <a-select
                v-model:value="filters.searchField"
                style="width: 120px"
                :options="searchFieldOptions"
              />
            </a-form-item>
            <a-form-item>
              <a-input
                v-model:value="filters.searchValue"
                allow-clear
                placeholder="请输入搜索词"
                style="width: 220px"
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
        :scroll="{ x: 1500 }"
        row-key="articleId"
        size="middle"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #emptyText>
          <span class="text-red-500">未查询到相关数据信息!</span>
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <VbenTableAction
              :actions="[
                { text: '登记', icon: 'lucide:edit', onClick: () => handleEdit(record) },
              ]"
            />
          </template>
          <template v-else-if="column.key === 'yearNo'">
            {{ record.yearNo ?? '' }}
          </template>
          <template v-else-if="column.key === 'period'">
            {{ record.period ?? '' }}
          </template>
          <template v-else-if="column.key === 'sampleQty'">
            {{ record.sampleQty ?? '' }}
          </template>
        </template>
      </a-table>

      <!-- 底部操作（对齐示意图） -->
      <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
        <a-button type="primary" @click="advancedOpen = !advancedOpen">
          高级检索
        </a-button>
        <a-button type="primary" @click="handleGenerateLabels('first')">
          生成第一作者邮签
        </a-button>
        <a-button type="primary" @click="handleGenerateLabels('corresponding')">
          生成通讯作者邮签
        </a-button>
        <a-button type="primary" @click="handleGenerateLabels('submit')">
          生成投稿作者邮签
        </a-button>
        <a-button type="primary" @click="handleGenerateLabels('payee')">
          生成稿费收款人邮签
        </a-button>
        <a-button type="primary" @click="handleExport">
          导出数据
        </a-button>
        <a-button type="primary" @click="columnDrawerOpen = true">
          显示设置
        </a-button>
      </div>
    </a-card>

    <!-- 登记编辑 -->
    <a-drawer
      v-model:open="drawerOpen"
      title="邮寄样刊登记"
      placement="right"
      :width="560"
      destroy-on-close
    >
      <a-form layout="vertical">
        <a-descriptions :column="2" size="small" bordered class="mb-4">
          <a-descriptions-item label="稿件编号">
            {{ formData.fileNo || '-' }}
          </a-descriptions-item>
          <a-descriptions-item label="年份/刊期">
            {{ formData.yearNo ?? '-' }} / {{ formData.period ?? '-' }}
          </a-descriptions-item>
          <a-descriptions-item label="投稿作者">
            {{ formData.submitAuthor || '-' }}
          </a-descriptions-item>
          <a-descriptions-item label="第一作者">
            {{ formData.firstAuthor || '-' }}
          </a-descriptions-item>
        </a-descriptions>

        <a-divider orientation="left">收款人信息</a-divider>
        <a-form-item label="收款人姓名">
          <a-input v-model:value="formData.payeeName" allow-clear />
        </a-form-item>
        <a-form-item label="收款人地址">
          <a-textarea v-model:value="formData.payeeAddress" :rows="2" />
        </a-form-item>
        <a-form-item label="收款人单位">
          <a-input v-model:value="formData.payeeOrg" allow-clear />
        </a-form-item>
        <div class="form-grid">
          <a-form-item label="邮编">
            <a-input v-model:value="formData.payeeZip" allow-clear />
          </a-form-item>
          <a-form-item label="联系电话">
            <a-input v-model:value="formData.payeePhone" allow-clear />
          </a-form-item>
        </div>
        <a-form-item label="样刊册数">
          <a-input-number
            v-model:value="formData.sampleQty"
            :min="1"
            :max="999"
            style="width: 100%"
          />
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

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  column-gap: 1rem;
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}
</style>
