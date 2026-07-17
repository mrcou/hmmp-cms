<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Inventory[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ year: undefined as number | undefined, period: undefined as number | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增库存');
const formData = reactive<PublisherApi.Inventory>({ year: new Date().getFullYear(), period: 1, totalCount: 0, stockCount: 0, soldCount: 0, giftCount: 0, lossCount: 0, status: '0', remark: '' });
const isEdit = ref(false);

const columns = [
  { title: '库存ID', dataIndex: 'inventoryId', key: 'inventoryId', width: 100 },
  { title: '年份', dataIndex: 'year', key: 'year', width: 100 },
  { title: '期数', dataIndex: 'period', key: 'period', width: 80 },
  { title: '总印量', dataIndex: 'totalCount', key: 'totalCount', width: 100 },
  { title: '库存量', dataIndex: 'stockCount', key: 'stockCount', width: 100 },
  { title: '已售量', dataIndex: 'soldCount', key: 'soldCount', width: 100 },
  { title: '赠送量', dataIndex: 'giftCount', key: 'giftCount', width: 100 },
  { title: '损耗量', dataIndex: 'lossCount', key: 'lossCount', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '备注', dataIndex: 'remark', key: 'remark' },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getInventoryList({
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
  searchForm.period = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增库存';
  formData.year = new Date().getFullYear();
  formData.period = 1;
  formData.totalCount = 0;
  formData.stockCount = 0;
  formData.soldCount = 0;
  formData.giftCount = 0;
  formData.lossCount = 0;
  formData.status = '0';
  formData.remark = '';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Inventory) {
  isEdit.value = true;
  modalTitle.value = '编辑库存';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Inventory) {
  await publisherApi.deleteInventory([record.inventoryId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updateInventory({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createInventory({ ...formData });
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
      <a-button type="primary" @click="handleAdd">新增库存</a-button>
      <a-form layout="inline">
        <a-form-item label="年份">
          <a-input-number v-model:value="searchForm.year" placeholder="请输入年份" :min="2000" :max="2100" style="width: 140px" />
        </a-form-item>
        <a-form-item label="期数">
          <a-input-number v-model:value="searchForm.period" placeholder="请输入期数" :min="1" style="width: 140px" />
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
      row-key="inventoryId"
      :scroll="{ x: 1200 }"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <a-tag :color="record.status === '0' ? 'green' : 'red'">
            {{ record.status === '0' ? '启用' : '停用' }}
          </a-tag>
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
            <a-popconfirm title="确认删除该库存记录吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="560">
      <a-form layout="vertical">
        <a-form-item label="年份" required>
          <a-input-number v-model:value="formData.year" :min="2000" :max="2100" style="width: 100%" />
        </a-form-item>
        <a-form-item label="期数" required>
          <a-input-number v-model:value="formData.period" :min="1" :max="36" style="width: 100%" />
        </a-form-item>
        <a-form-item label="总印量">
          <a-input-number v-model:value="formData.totalCount" :min="0" style="width: 100%" />
        </a-form-item>
        <a-form-item label="库存量">
          <a-input-number v-model:value="formData.stockCount" :min="0" style="width: 100%" />
        </a-form-item>
        <a-form-item label="已售量">
          <a-input-number v-model:value="formData.soldCount" :min="0" style="width: 100%" />
        </a-form-item>
        <a-form-item label="赠送量">
          <a-input-number v-model:value="formData.giftCount" :min="0" style="width: 100%" />
        </a-form-item>
        <a-form-item label="损耗量">
          <a-input-number v-model:value="formData.lossCount" :min="0" style="width: 100%" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="formData.status">
            <a-select-option value="0">启用</a-select-option>
            <a-select-option value="1">停用</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea v-model:value="formData.remark" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>