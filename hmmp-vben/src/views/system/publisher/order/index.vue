<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Order[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ orderNo: '', subscriberName: '', orderStatus: undefined as string | undefined, payStatus: undefined as string | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增订单');
const formData = reactive<PublisherApi.Order>({ orderNo: '', subscriberName: '', totalAmount: 0, orderStatus: '0', payStatus: '0', orderType: '', shippingName: '', shippingPhone: '', shippingAddress: '' });
const isEdit = ref(false);

const columns = [
  { title: '订单ID', dataIndex: 'orderId', key: 'orderId', width: 100 },
  { title: '订单号', dataIndex: 'orderNo', key: 'orderNo', width: 180 },
  { title: '订户', dataIndex: 'subscriberName', key: 'subscriberName', width: 120 },
  { title: '订单金额', dataIndex: 'totalAmount', key: 'totalAmount', width: 120 },
  { title: '支付状态', dataIndex: 'payStatus', key: 'payStatus', width: 100 },
  { title: '订单状态', dataIndex: 'orderStatus', key: 'orderStatus', width: 100 },
  { title: '订单类型', dataIndex: 'orderType', key: 'orderType', width: 100 },
  { title: '收货人', dataIndex: 'shippingName', key: 'shippingName', width: 100 },
  { title: '收货电话', dataIndex: 'shippingPhone', key: 'shippingPhone', width: 130 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 160 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

const payStatusMap: Record<string, { color: string; text: string }> = {
  '0': { color: 'orange', text: '待支付' },
  '1': { color: 'green', text: '已支付' },
  '2': { color: 'red', text: '已退款' },
};

const orderStatusMap: Record<string, { color: string; text: string }> = {
  '0': { color: 'blue', text: '待处理' },
  '1': { color: 'cyan', text: '处理中' },
  '2': { color: 'green', text: '已完成' },
  '3': { color: 'red', text: '已取消' },
};

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getOrderList({
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
  searchForm.orderNo = '';
  searchForm.subscriberName = '';
  searchForm.orderStatus = undefined;
  searchForm.payStatus = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增订单';
  formData.orderNo = '';
  formData.subscriberName = '';
  formData.totalAmount = 0;
  formData.orderStatus = '0';
  formData.payStatus = '0';
  formData.orderType = '';
  formData.shippingName = '';
  formData.shippingPhone = '';
  formData.shippingAddress = '';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Order) {
  isEdit.value = true;
  modalTitle.value = '编辑订单';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Order) {
  await publisherApi.deleteOrder([record.orderId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updateOrder({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createOrder({ ...formData });
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
    <div class="mb-4 flex items-center justify-between">
      <a-button type="primary" @click="handleAdd">新增订单</a-button>
      <a-form layout="inline">
        <a-form-item label="订单号">
          <a-input v-model:value="searchForm.orderNo" placeholder="请输入订单号" style="width: 160px" />
        </a-form-item>
        <a-form-item label="订户">
          <a-input v-model:value="searchForm.subscriberName" placeholder="请输入订户名称" style="width: 160px" />
        </a-form-item>
        <a-form-item label="支付状态">
          <a-select v-model:value="searchForm.payStatus" placeholder="请选择" allow-clear style="width: 120px">
            <a-select-option value="0">待支付</a-select-option>
            <a-select-option value="1">已支付</a-select-option>
            <a-select-option value="2">已退款</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="订单状态">
          <a-select v-model:value="searchForm.orderStatus" placeholder="请选择" allow-clear style="width: 120px">
            <a-select-option value="0">待处理</a-select-option>
            <a-select-option value="1">处理中</a-select-option>
            <a-select-option value="2">已完成</a-select-option>
            <a-select-option value="3">已取消</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="orderId"
      :scroll="{ x: 1400 }"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'payStatus'">
          <a-tag :color="payStatusMap[record.payStatus]?.color">
            {{ payStatusMap[record.payStatus]?.text ?? record.payStatus }}
          </a-tag>
        </template>
        <template v-if="column.key === 'orderStatus'">
          <a-tag :color="orderStatusMap[record.orderStatus]?.color">
            {{ orderStatusMap[record.orderStatus]?.text ?? record.orderStatus }}
          </a-tag>
        </template>
        <template v-if="column.key === 'totalAmount'">
          ¥{{ (record.totalAmount ?? 0).toFixed(2) }}
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
            <a-popconfirm title="确认删除该订单吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="560">
      <a-form layout="vertical">
        <a-form-item label="订单号">
          <a-input v-model:value="formData.orderNo" placeholder="请输入订单号" />
        </a-form-item>
        <a-form-item label="订户名称" required>
          <a-input v-model:value="formData.subscriberName" placeholder="请输入订户名称" />
        </a-form-item>
        <a-form-item label="订单金额">
          <a-input-number v-model:value="formData.totalAmount" :min="0" :step="0.01" :precision="2" style="width: 100%" />
        </a-form-item>
        <a-form-item label="订单类型">
          <a-select v-model:value="formData.orderType" placeholder="请选择订单类型">
            <a-select-option value="subscribe">订阅</a-select-option>
            <a-select-option value="single">单期购买</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="订单状态">
          <a-select v-model:value="formData.orderStatus">
            <a-select-option value="0">待处理</a-select-option>
            <a-select-option value="1">处理中</a-select-option>
            <a-select-option value="2">已完成</a-select-option>
            <a-select-option value="3">已取消</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="支付状态">
          <a-select v-model:value="formData.payStatus">
            <a-select-option value="0">待支付</a-select-option>
            <a-select-option value="1">已支付</a-select-option>
            <a-select-option value="2">已退款</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="收货人">
          <a-input v-model:value="formData.shippingName" placeholder="请输入收货人" />
        </a-form-item>
        <a-form-item label="收货电话">
          <a-input v-model:value="formData.shippingPhone" placeholder="请输入收货电话" />
        </a-form-item>
        <a-form-item label="收货地址">
          <a-textarea v-model:value="formData.shippingAddress" :rows="2" placeholder="请输入收货地址" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>