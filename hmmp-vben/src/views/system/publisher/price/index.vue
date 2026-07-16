<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Price[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ year: undefined as number | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增定价');
const formData = reactive<PublisherApi.Price>({ year: new Date().getFullYear(), periodStart: 1, periodEnd: 12, unitPrice: 0, annualPrice: 0, status: '0' });
const isEdit = ref(false);

const columns = [
  { title: '定价ID', dataIndex: 'priceId', key: 'priceId', width: 100 },
  { title: '年份', dataIndex: 'year', key: 'year', width: 100 },
  { title: '起始期数', dataIndex: 'periodStart', key: 'periodStart', width: 100 },
  { title: '结束期数', dataIndex: 'periodEnd', key: 'periodEnd', width: 100 },
  { title: '单价', dataIndex: 'unitPrice', key: 'unitPrice', width: 120 },
  { title: '年价', dataIndex: 'annualPrice', key: 'annualPrice', width: 120 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getPriceList({
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
  searchForm.year = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增定价';
  formData.year = new Date().getFullYear();
  formData.periodStart = 1;
  formData.periodEnd = 12;
  formData.unitPrice = 0;
  formData.annualPrice = 0;
  formData.status = '0';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Price) {
  isEdit.value = true;
  modalTitle.value = '编辑定价';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Price) {
  await publisherApi.deletePrice([record.priceId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updatePrice({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createPrice({ ...formData });
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
      <a-form-item label="年份">
        <a-input-number v-model:value="searchForm.year" placeholder="请输入年份" :min="2000" :max="2100" style="width: 160px" />
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleSearch">查询</a-button>
          <a-button @click="handleReset">重置</a-button>
        </a-space>
      </a-form-item>
    </a-form>

    <div class="mb-4">
      <a-button type="primary" @click="handleAdd">新增定价</a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="priceId"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <a-tag :color="record.status === '0' ? 'green' : 'red'">
            {{ record.status === '0' ? '启用' : '停用' }}
          </a-tag>
        </template>
        <template v-if="column.key === 'unitPrice' || column.key === 'annualPrice'">
          ¥{{ (record[column.dataIndex] ?? 0).toFixed(2) }}
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
            <a-popconfirm title="确认删除该定价吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="500">
      <a-form layout="vertical">
        <a-form-item label="年份" required>
          <a-input-number v-model:value="formData.year" :min="2000" :max="2100" style="width: 100%" />
        </a-form-item>
        <a-form-item label="起始期数" required>
          <a-input-number v-model:value="formData.periodStart" :min="1" :max="36" style="width: 100%" />
        </a-form-item>
        <a-form-item label="结束期数" required>
          <a-input-number v-model:value="formData.periodEnd" :min="1" :max="36" style="width: 100%" />
        </a-form-item>
        <a-form-item label="单价(元)">
          <a-input-number v-model:value="formData.unitPrice" :min="0" :step="0.01" :precision="2" style="width: 100%" />
        </a-form-item>
        <a-form-item label="年价(元)">
          <a-input-number v-model:value="formData.annualPrice" :min="0" :step="0.01" :precision="2" style="width: 100%" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="formData.status">
            <a-select-option value="0">启用</a-select-option>
            <a-select-option value="1">停用</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>