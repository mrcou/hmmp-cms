<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Post[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ postTrackingNo: '', subscriberName: '', postStatus: undefined as string | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增寄书记录');
const formData = reactive<PublisherApi.Post>({ orderId: undefined, subscriberName: '', shippingName: '', shippingPhone: '', shippingAddress: '', year: new Date().getFullYear(), period: 1, postTrackingNo: '', postCompany: '', postStatus: '0', quantity: 1, remark: '' });
const isEdit = ref(false);

const columns = [
  { title: '寄书ID', dataIndex: 'postId', key: 'postId', width: 100 },
  { title: '订单ID', dataIndex: 'orderId', key: 'orderId', width: 100 },
  { title: '订户', dataIndex: 'subscriberName', key: 'subscriberName', width: 120 },
  { title: '年份', dataIndex: 'year', key: 'year', width: 80 },
  { title: '期数', dataIndex: 'period', key: 'period', width: 80 },
  { title: '数量', dataIndex: 'quantity', key: 'quantity', width: 80 },
  { title: '快递单号', dataIndex: 'postTrackingNo', key: 'postTrackingNo', width: 160 },
  { title: '快递公司', dataIndex: 'postCompany', key: 'postCompany', width: 120 },
  { title: '收货人', dataIndex: 'shippingName', key: 'shippingName', width: 100 },
  { title: '收货电话', dataIndex: 'shippingPhone', key: 'shippingPhone', width: 130 },
  { title: '寄书状态', dataIndex: 'postStatus', key: 'postStatus', width: 100 },
  { title: '寄书时间', dataIndex: 'postTime', key: 'postTime', width: 160 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

const postStatusMap: Record<string, { color: string; text: string }> = {
  '0': { color: 'orange', text: '待寄出' },
  '1': { color: 'blue', text: '已寄出' },
  '2': { color: 'green', text: '已签收' },
  '3': { color: 'red', text: '已退回' },
};

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getPostList({
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
  searchForm.postTrackingNo = '';
  searchForm.subscriberName = '';
  searchForm.postStatus = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增寄书记录';
  formData.orderId = undefined;
  formData.subscriberName = '';
  formData.shippingName = '';
  formData.shippingPhone = '';
  formData.shippingAddress = '';
  formData.year = new Date().getFullYear();
  formData.period = 1;
  formData.postTrackingNo = '';
  formData.postCompany = '';
  formData.postStatus = '0';
  formData.quantity = 1;
  formData.remark = '';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Post) {
  isEdit.value = true;
  modalTitle.value = '编辑寄书记录';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Post) {
  await publisherApi.deletePost([record.postId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updatePost({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createPost({ ...formData });
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
      <a-form-item label="快递单号">
        <a-input v-model:value="searchForm.postTrackingNo" placeholder="请输入快递单号" style="width: 160px" />
      </a-form-item>
      <a-form-item label="订户">
        <a-input v-model:value="searchForm.subscriberName" placeholder="请输入订户名称" style="width: 160px" />
      </a-form-item>
      <a-form-item label="寄书状态">
        <a-select v-model:value="searchForm.postStatus" placeholder="请选择" allow-clear style="width: 120px">
          <a-select-option value="0">待寄出</a-select-option>
          <a-select-option value="1">已寄出</a-select-option>
          <a-select-option value="2">已签收</a-select-option>
          <a-select-option value="3">已退回</a-select-option>
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
      <a-button type="primary" @click="handleAdd">新增寄书记录</a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="postId"
      :scroll="{ x: 1500 }"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'postStatus'">
          <a-tag :color="postStatusMap[record.postStatus!]?.color">
            {{ postStatusMap[record.postStatus!]?.text ?? record.postStatus }}
          </a-tag>
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
            <a-popconfirm title="确认删除该寄书记录吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="560">
      <a-form layout="vertical">
        <a-form-item label="订单ID">
          <a-input-number v-model:value="formData.orderId" style="width: 100%" />
        </a-form-item>
        <a-form-item label="订户名称" required>
          <a-input v-model:value="formData.subscriberName" placeholder="请输入订户名称" />
        </a-form-item>
        <a-form-item label="年份" required>
          <a-input-number v-model:value="formData.year" :min="2000" :max="2100" style="width: 100%" />
        </a-form-item>
        <a-form-item label="期数" required>
          <a-input-number v-model:value="formData.period" :min="1" :max="36" style="width: 100%" />
        </a-form-item>
        <a-form-item label="数量">
          <a-input-number v-model:value="formData.quantity" :min="1" style="width: 100%" />
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
        <a-form-item label="快递单号">
          <a-input v-model:value="formData.postTrackingNo" placeholder="请输入快递单号" />
        </a-form-item>
        <a-form-item label="快递公司">
          <a-input v-model:value="formData.postCompany" placeholder="请输入快递公司" />
        </a-form-item>
        <a-form-item label="寄书状态">
          <a-select v-model:value="formData.postStatus">
            <a-select-option value="0">待寄出</a-select-option>
            <a-select-option value="1">已寄出</a-select-option>
            <a-select-option value="2">已签收</a-select-option>
            <a-select-option value="3">已退回</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea v-model:value="formData.remark" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>