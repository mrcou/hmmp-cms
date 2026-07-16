<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Invoice[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ invoiceNo: '', title: '', applyStatus: undefined as string | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增发票');
const formData = reactive<PublisherApi.Invoice>({ orderId: undefined, invoiceNo: '', invoiceType: '', amount: 0, taxAmount: 0, totalAmount: 0, title: '', taxId: '', applyStatus: '0', makeStatus: '0' });
const isEdit = ref(false);

const columns = [
  { title: '发票ID', dataIndex: 'invoiceId', key: 'invoiceId', width: 100 },
  { title: '发票号', dataIndex: 'invoiceNo', key: 'invoiceNo', width: 180 },
  { title: '订单ID', dataIndex: 'orderId', key: 'orderId', width: 100 },
  { title: '发票类型', dataIndex: 'invoiceType', key: 'invoiceType', width: 120 },
  { title: '金额', dataIndex: 'amount', key: 'amount', width: 120 },
  { title: '税额', dataIndex: 'taxAmount', key: 'taxAmount', width: 100 },
  { title: '价税合计', dataIndex: 'totalAmount', key: 'totalAmount', width: 120 },
  { title: '抬头', dataIndex: 'title', key: 'title', width: 160 },
  { title: '税号', dataIndex: 'taxId', key: 'taxId', width: 140 },
  { title: '申请状态', dataIndex: 'applyStatus', key: 'applyStatus', width: 100 },
  { title: '开票状态', dataIndex: 'makeStatus', key: 'makeStatus', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

const applyStatusMap: Record<string, { color: string; text: string }> = {
  '0': { color: 'orange', text: '待审核' },
  '1': { color: 'green', text: '已通过' },
  '2': { color: 'red', text: '已驳回' },
};

const makeStatusMap: Record<string, { color: string; text: string }> = {
  '0': { color: 'orange', text: '未开票' },
  '1': { color: 'green', text: '已开票' },
};

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getInvoiceList({
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
      ...searchForm,
    });
    dataSource.value = res.data?.rows ?? res.data ?? [];
    pagination.total = res.data?.total ?? 0;
  } finally {
    loading.value = false;
  }
}

function handleSearch() {
  pagination.current = 1;
  fetchList();
}

function handleReset() {
  searchForm.invoiceNo = '';
  searchForm.title = '';
  searchForm.applyStatus = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增发票';
  formData.orderId = undefined;
  formData.invoiceNo = '';
  formData.invoiceType = '';
  formData.amount = 0;
  formData.taxAmount = 0;
  formData.totalAmount = 0;
  formData.title = '';
  formData.taxId = '';
  formData.applyStatus = '0';
  formData.makeStatus = '0';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Invoice) {
  isEdit.value = true;
  modalTitle.value = '编辑发票';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Invoice) {
  await publisherApi.deleteInvoice([record.invoiceId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updateInvoice({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createInvoice({ ...formData });
    message.success('新增成功');
  }
  modalVisible.value = false;
  fetchList();
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchList();
}

onMounted(() => {
  fetchList();
});
</script>

<template>
  <div class="p-4">
    <a-form layout="inline" class="mb-4">
      <a-form-item label="发票号">
        <a-input v-model:value="searchForm.invoiceNo" placeholder="请输入发票号" style="width: 160px" />
      </a-form-item>
      <a-form-item label="抬头">
        <a-input v-model:value="searchForm.title" placeholder="请输入抬头" style="width: 160px" />
      </a-form-item>
      <a-form-item label="申请状态">
        <a-select v-model:value="searchForm.applyStatus" placeholder="请选择" allow-clear style="width: 120px">
          <a-select-option value="0">待审核</a-select-option>
          <a-select-option value="1">已通过</a-select-option>
          <a-select-option value="2">已驳回</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleSearch">查询</a-button>
          <a-button @click="handleReset">重置</a-button>
        </a-space>
      </a-form-item>
    </a-form>

    <div class="mb-4">
      <a-button type="primary" @click="handleAdd">新增发票</a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="invoiceId"
      :scroll="{ x: 1500 }"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'applyStatus'">
          <a-tag :color="applyStatusMap[record.applyStatus!]?.color">
            {{ applyStatusMap[record.applyStatus!]?.text ?? record.applyStatus }}
          </a-tag>
        </template>
        <template v-if="column.key === 'makeStatus'">
          <a-tag :color="makeStatusMap[record.makeStatus!]?.color">
            {{ makeStatusMap[record.makeStatus!]?.text ?? record.makeStatus }}
          </a-tag>
        </template>
        <template v-if="column.key === 'amount' || column.key === 'taxAmount' || column.key === 'totalAmount'">
          ¥{{ (record[column.dataIndex] ?? 0).toFixed(2) }}
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
            <a-popconfirm title="确认删除该发票吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="560">
      <a-form layout="vertical">
        <a-form-item label="发票号">
          <a-input v-model:value="formData.invoiceNo" placeholder="请输入发票号" />
        </a-form-item>
        <a-form-item label="订单ID">
          <a-input-number v-model:value="formData.orderId" style="width: 100%" />
        </a-form-item>
        <a-form-item label="发票类型">
          <a-select v-model:value="formData.invoiceType" placeholder="请选择发票类型">
            <a-select-option value="vat">增值税专用发票</a-select-option>
            <a-select-option value="normal">普通发票</a-select-option>
            <a-select-option value="electronic">电子发票</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="抬头" required>
          <a-input v-model:value="formData.title" placeholder="请输入抬头" />
        </a-form-item>
        <a-form-item label="税号">
          <a-input v-model:value="formData.taxId" placeholder="请输入税号" />
        </a-form-item>
        <a-form-item label="金额">
          <a-input-number v-model:value="formData.amount" :min="0" :step="0.01" :precision="2" style="width: 100%" />
        </a-form-item>
        <a-form-item label="税额">
          <a-input-number v-model:value="formData.taxAmount" :min="0" :step="0.01" :precision="2" style="width: 100%" />
        </a-form-item>
        <a-form-item label="价税合计">
          <a-input-number v-model:value="formData.totalAmount" :min="0" :step="0.01" :precision="2" style="width: 100%" />
        </a-form-item>
        <a-form-item label="申请状态">
          <a-select v-model:value="formData.applyStatus">
            <a-select-option value="0">待审核</a-select-option>
            <a-select-option value="1">已通过</a-select-option>
            <a-select-option value="2">已驳回</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="开票状态">
          <a-select v-model:value="formData.makeStatus">
            <a-select-option value="0">未开票</a-select-option>
            <a-select-option value="1">已开票</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>