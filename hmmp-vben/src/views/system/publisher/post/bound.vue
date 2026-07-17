<script lang="ts" setup>
/**
 * 邮寄合订本 —— 对齐旧系统：杂志/会议/字段检索 + 表格 + 邮签/导出/显示设置
 */
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { VbenTableAction } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';
import { preferences } from '@vben/preferences';

import { message, Modal } from 'antdv-next';

import * as meetingApi from '#/api/biz/meeting';
import * as publisherApi from '#/api/biz/publisher';

defineOptions({ name: 'PublisherOrderBound' });

const loading = ref(false);
const submitting = ref(false);
const dataSource = ref<PublisherApi.BoundPost[]>([]);
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
  searchField: 'receiveUser' as
    | 'category'
    | 'distributeType'
    | 'journalCode'
    | 'orderNo'
    | 'receiveAddress'
    | 'receivePostcode'
    | 'receiveUnit'
    | 'receiveUser'
    | 'sendMethodName'
    | 'sendUser'
    | 'unitType'
    | 'yearId',
  searchValue: '',
});

const searchFieldOptions = [
  { value: 'journalCode', label: '杂志编号' },
  { value: 'orderNo', label: '订单编号' },
  { value: 'category', label: '行业分类' },
  { value: 'unitType', label: '企业类型' },
  { value: 'distributeType', label: '发行方式' },
  { value: 'sendMethodName', label: '邮寄方式' },
  { value: 'yearId', label: '订购年份' },
  { value: 'receiveUser', label: '收书人姓名' },
  { value: 'receiveUnit', label: '收书人单位' },
  { value: 'receiveAddress', label: '收书人地址' },
  { value: 'receivePostcode', label: '读者邮编' },
  { value: 'sendUser', label: '寄书人' },
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

const advancedOpen = ref(false);

interface SearchRow {
  field: string;
  id: number;
  value: string;
}

let searchRowSeq = 1;
function createSearchRow(field = 'receiveUser'): SearchRow {
  return { id: searchRowSeq++, field, value: '' };
}
const searchRows = ref<SearchRow[]>([createSearchRow()]);

const advancedFields = [
  ...searchFieldOptions,
  { value: 'orderBookNum', label: '邮寄数量' },
] as const;

function addSearchRow() {
  searchRows.value.push(createSearchRow('receiveAddress'));
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
  { key: 'action', title: '寄书登记', dataIndex: 'action', width: 100, fixed: 'left' as const },
  { key: 'journalCode', title: '杂志编号', dataIndex: 'journalCode', width: 100 },
  { key: 'category', title: '行业分类', dataIndex: 'category', width: 150 },
  { key: 'unitType', title: '企业类型', dataIndex: 'unitType', width: 150 },
  { key: 'distributeType', title: '发行方式', dataIndex: 'distributeType', width: 150 },
  { key: 'sendMethodName', title: '邮寄方式', dataIndex: 'sendMethodName', width: 150 },
  { key: 'yearId', title: '订购年份', dataIndex: 'yearId', width: 100 },
  { key: 'receiveUser', title: '收书人姓名', dataIndex: 'receiveUser', width: 150 },
  { key: 'receiveUnit', title: '收书人单位', dataIndex: 'receiveUnit', ellipsis: true, width: 150 },
  { key: 'receiveAddress', title: '收书人地址', dataIndex: 'receiveAddress', ellipsis: true, width: 250 },
  { key: 'orderTime', title: '订单日期', dataIndex: 'orderTime', width: 150 },
  { key: 'orderBookNum', title: '邮寄数量', dataIndex: 'orderBookNum', width: 100 },
  { key: 'sendBookTime', title: '寄书时间', dataIndex: 'sendBookTime', width: 150 },
  { key: 'sendUser', title: '寄书人', dataIndex: 'sendUser', width: 150 },
  { key: 'receivePostcode', title: '读者邮编', dataIndex: 'receivePostcode', width: 150 },
  { key: 'rmbOrderCost', title: '金额', dataIndex: 'rmbOrderCost', width: 120 },
  { key: 'orderNo', title: '订单编号', dataIndex: 'orderNo', width: 150 },
];

const visibleColumnKeys = ref(
  allColumns
    .filter((c) => !['orderNo', 'receivePostcode', 'rmbOrderCost', 'sendMethodName'].includes(c.key))
    .map((c) => c.key),
);
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

function assignSearchParam(params: Record<string, any>, field: string, value: string) {
  const keyword = value.trim();
  if (!keyword) return;
  if (field === 'yearId' || field === 'orderBookNum') {
    const num = Number(keyword);
    if (!Number.isNaN(num)) {
      params[field] = num;
    }
    return;
  }
  params[field] = keyword;
}

function buildSearchParams(extra: Record<string, any> = {}) {
  const params: Record<string, any> = {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
    ...extra,
  };
  if (filters.journalCode) {
    params.journalCode = filters.journalCode;
  }
  if (filters.meetingId != null) {
    params.meetingId = filters.meetingId;
  }
  assignSearchParam(params, filters.searchField, filters.searchValue);
  for (const row of searchRows.value) {
    assignSearchParam(params, row.field, row.value);
  }
  return params;
}

async function fetchList(extra: Record<string, any> = {}) {
  loading.value = true;
  try {
    const res = (await publisherApi.getBoundPostList(buildSearchParams(extra))) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载邮寄合订本列表失败');
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
  filters.searchField = 'receiveUser';
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
const formData = reactive<PublisherApi.BoundPost>({
  orderId: undefined,
  receiveUser: '',
  receiveUnit: '',
  receiveAddress: '',
  receivePostcode: '',
  sendBookTime: undefined,
  sendUser: '',
  canPost: '1',
  remark: '',
});

function handleEdit(record: PublisherApi.BoundPost) {
  Object.assign(formData, {
    boundId: record.boundId,
    orderId: record.orderId,
    orderNo: record.orderNo,
    journalCode: record.journalCode,
    yearId: record.yearId,
    orderBookNum: record.orderBookNum,
    receiveUser: record.receiveUser || '',
    receiveUnit: record.receiveUnit || '',
    receiveAddress: record.receiveAddress || '',
    receivePostcode: record.receivePostcode || '',
    sendBookTime: record.sendBookTime,
    sendUser: record.sendUser || '',
    canPost: record.canPost || '1',
    remark: record.remark || '',
  });
  drawerOpen.value = true;
}

async function handleSubmit() {
  if (!formData.orderId) {
    message.warning('缺少订单信息');
    return;
  }
  submitting.value = true;
  try {
    await publisherApi.saveBoundPost({ ...formData });
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

function escapeHtml(text: string) {
  return text
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

function buildLabelHtml(rows: PublisherApi.BoundPost[]) {
  const blocks = rows
    .map(
      (item) => `
      <div class="label">
        <div class="name">${escapeHtml(item.receiveUser || '（未填写收书人）')}</div>
        <div>${escapeHtml(item.receiveUnit || '')}</div>
        <div>${escapeHtml(item.receiveAddress || '')}</div>
        <div>邮编：${escapeHtml(item.receivePostcode || '')}</div>
        <div class="meta">订单：${escapeHtml(item.orderNo || '')}　合订本：${item.orderBookNum ?? 1} 册</div>
      </div>`,
    )
    .join('');
  return `<!DOCTYPE html><html><head><meta charset="utf-8"><title>合订本邮签</title>
    <style>
      body { font-family: "Microsoft YaHei", sans-serif; padding: 16px; }
      .label { border: 1px dashed #999; padding: 12px 16px; margin-bottom: 16px; width: 340px; line-height: 1.6; }
      .name { font-size: 16px; font-weight: bold; margin-bottom: 4px; }
      .meta { margin-top: 6px; color: #666; font-size: 12px; }
      @media print { .label { page-break-inside: avoid; } }
    </style></head><body><h2>合订本邮签</h2>${blocks}</body></html>`;
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

function handleGenerateLabels() {
  if (!requireSelection('生成邮签')) return;
  const selected = dataSource.value.filter((row) =>
    selectedKeys.value.includes(row.orderId!),
  );
  if (selected.every((row) => !row.receiveUser?.trim())) {
    message.warning('所选记录缺少收书人信息');
    return;
  }
  openPrintWindow(buildLabelHtml(selected));
}

async function handleClearInvalidAddress() {
  Modal.confirm({
    title: '确认清除',
    content: '确定清除不可邮寄记录中的收书人地址吗？',
    async onOk() {
      await publisherApi.clearInvalidBoundPostAddress();
      message.success('清除成功');
      fetchList();
    },
  });
}

async function handleExport() {
  try {
    await publisherApi.exportBoundPost(buildSearchParams());
  } catch {
    message.error('导出失败');
  }
}

function filterMailed(mailedOnly: boolean) {
  pagination.current = 1;
  fetchList(mailedOnly ? { mailedOnly: true } : { pendingOnly: true });
}

onMounted(() => {
  fetchList();
  loadMeetingOptions();
});
</script>

<template>
  <div class="p-4">
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
          style="width: 140px"
          size="small"
          :options="advancedFields"
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
        <a-button type="primary" size="small" @click="onSearch">检索</a-button>
        <a-button size="small" @click="onResetAdvanced">重置</a-button>
        <a-button size="small" @click="advancedOpen = false">关闭</a-button>
      </div>
    </div>

    <a-card :bordered="false" class="shadow-sm">
      <div class="mb-4 flex items-center justify-between">
        <div class="flex items-center justify-end">
          <a-form layout="inline">
            <a-form-item label="杂志名称">
              <a-select
                v-model:value="filters.journalCode"
                allow-clear
                placeholder="--杂志名称--"
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
                style="width: 130px"
                :options="searchFieldOptions"
              />
            </a-form-item>
            <a-form-item>
              <a-input
                v-model:value="filters.searchValue"
                allow-clear
                placeholder="请输入搜索词"
                style="width: 240px"
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
        row-key="orderId"
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
          <template v-else-if="column.key === 'rmbOrderCost'">
            {{ record.rmbOrderCost == null ? '' : `¥${Number(record.rmbOrderCost).toFixed(2)}` }}
          </template>
        </template>
      </a-table>

      <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
        <a-button type="primary" @click="advancedOpen = !advancedOpen">
          高级检索
        </a-button>
        <a-button type="primary" @click="handleExport">
          导出数据
        </a-button>
        <a-button type="primary" @click="handleClearInvalidAddress">
          清除无效寄书地址
        </a-button>
        <a-button type="primary" @click="filterMailed(true)">
          已寄书
        </a-button>
        <a-button type="primary" @click="filterMailed(false)">
          未寄书
        </a-button>
        <a-button type="primary" @click="handleGenerateLabels">
          显示邮签
        </a-button>
        <a-button type="primary" @click="columnDrawerOpen = true">
          显示设置
        </a-button>
      </div>
    </a-card>

    <a-drawer
      v-model:open="drawerOpen"
      title="邮寄合订本登记"
      placement="right"
      :width="560"
      destroy-on-close
    >
      <a-form layout="vertical">
        <a-descriptions :column="2" size="small" bordered class="mb-4">
          <a-descriptions-item label="订单编号">
            {{ formData.orderNo || '-' }}
          </a-descriptions-item>
          <a-descriptions-item label="订购年份">
            {{ formData.yearId ?? '-' }}
          </a-descriptions-item>
          <a-descriptions-item label="邮寄数量">
            {{ formData.orderBookNum ?? '-' }}
          </a-descriptions-item>
          <a-descriptions-item label="杂志编号">
            {{ formData.journalCode || '-' }}
          </a-descriptions-item>
        </a-descriptions>

        <a-divider orientation="left">收书信息</a-divider>
        <a-form-item label="收书人姓名">
          <a-input v-model:value="formData.receiveUser" allow-clear />
        </a-form-item>
        <a-form-item label="收书人单位">
          <a-input v-model:value="formData.receiveUnit" allow-clear />
        </a-form-item>
        <a-form-item label="收书人地址">
          <a-textarea v-model:value="formData.receiveAddress" :rows="2" />
        </a-form-item>
        <div class="form-grid">
          <a-form-item label="读者邮编">
            <a-input v-model:value="formData.receivePostcode" allow-clear />
          </a-form-item>
          <a-form-item label="是否可邮寄">
            <a-select
              v-model:value="formData.canPost"
              :options="[
                { value: '1', label: '是' },
                { value: '0', label: '否' },
              ]"
            />
          </a-form-item>
        </div>
        <div class="form-grid">
          <a-form-item label="寄书时间">
            <a-date-picker
              v-model:value="formData.sendBookTime"
              show-time
              value-format="YYYY-MM-DD HH:mm:ss"
              style="width: 100%"
            />
          </a-form-item>
          <a-form-item label="寄书人">
            <a-input v-model:value="formData.sendUser" allow-clear />
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
