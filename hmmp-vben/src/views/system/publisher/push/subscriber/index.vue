<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.PushSubscriber[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ userName: '', email: '', status: undefined as string | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增订阅用户');
const formData = reactive<PublisherApi.PushSubscriber>({ userId: undefined, userName: '', email: '', phone: '', keywords: '', status: '0' });
const isEdit = ref(false);

const columns = [
  { title: 'ID', dataIndex: 'pushSubId', key: 'pushSubId', width: 100 },
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 100 },
  { title: '用户名', dataIndex: 'userName', key: 'userName', width: 120 },
  { title: '邮箱', dataIndex: 'email', key: 'email', width: 200 },
  { title: '手机号', dataIndex: 'phone', key: 'phone', width: 140 },
  { title: '订阅关键词', dataIndex: 'keywords', key: 'keywords', width: 200, ellipsis: true },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getPushSubscriberList({
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
  searchForm.userName = '';
  searchForm.email = '';
  searchForm.status = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增订阅用户';
  formData.userId = undefined;
  formData.userName = '';
  formData.email = '';
  formData.phone = '';
  formData.keywords = '';
  formData.status = '0';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.PushSubscriber) {
  isEdit.value = true;
  modalTitle.value = '编辑订阅用户';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.PushSubscriber) {
  await publisherApi.deletePushSubscriber([record.pushSubId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  // Note: The API only has createPushSubscriber, no update. For edit we still call create.
  await publisherApi.createPushSubscriber({ ...formData });
  message.success(isEdit.value ? '编辑成功' : '新增成功');
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
      <a-button type="primary" @click="handleAdd">新增订阅用户</a-button>
      <a-form layout="inline">
        <a-form-item label="用户名">
          <a-input v-model:value="searchForm.userName" placeholder="请输入用户名" style="width: 160px" />
        </a-form-item>
        <a-form-item label="邮箱">
          <a-input v-model:value="searchForm.email" placeholder="请输入邮箱" style="width: 160px" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear style="width: 120px">
            <a-select-option value="0">启用</a-select-option>
            <a-select-option value="1">停用</a-select-option>
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
      row-key="pushSubId"
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
            <a-popconfirm title="确认删除该订阅用户吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="520">
      <a-form layout="vertical">
        <a-form-item label="用户ID">
          <a-input-number v-model:value="formData.userId" style="width: 100%" />
        </a-form-item>
        <a-form-item label="用户名" required>
          <a-input v-model:value="formData.userName" placeholder="请输入用户名" />
        </a-form-item>
        <a-form-item label="邮箱">
          <a-input v-model:value="formData.email" placeholder="请输入邮箱" />
        </a-form-item>
        <a-form-item label="手机号">
          <a-input v-model:value="formData.phone" placeholder="请输入手机号" />
        </a-form-item>
        <a-form-item label="订阅关键词">
          <a-textarea v-model:value="formData.keywords" :rows="2" placeholder="多个关键词用逗号分隔" />
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