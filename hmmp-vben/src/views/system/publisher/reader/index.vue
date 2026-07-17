<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Reader[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ realName: '', phone: '', status: undefined as string | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增读者');
const formData = reactive<PublisherApi.Reader>({ realName: '', nickName: '', gender: '', email: '', phone: '', mobile: '', address: '', status: '0' });
const isEdit = ref(false);

const columns = [
  { title: '读者ID', dataIndex: 'readerId', key: 'readerId', width: 100 },
  { title: '姓名', dataIndex: 'realName', key: 'realName', width: 120 },
  { title: '昵称', dataIndex: 'nickName', key: 'nickName', width: 120 },
  { title: '性别', dataIndex: 'gender', key: 'gender', width: 70 },
  { title: '邮箱', dataIndex: 'email', key: 'email', width: 180 },
  { title: '手机号', dataIndex: 'phone', key: 'phone', width: 130 },
  { title: '地址', dataIndex: 'address', key: 'address', ellipsis: true },
  { title: '注册时间', dataIndex: 'registerTime', key: 'registerTime', width: 160 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getReaderList({
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
  searchForm.realName = '';
  searchForm.phone = '';
  searchForm.status = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增读者';
  formData.realName = '';
  formData.nickName = '';
  formData.gender = '';
  formData.email = '';
  formData.phone = '';
  formData.mobile = '';
  formData.address = '';
  formData.status = '0';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Reader) {
  isEdit.value = true;
  modalTitle.value = '编辑读者';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Reader) {
  await publisherApi.deleteReader([record.readerId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updateReader({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createReader({ ...formData });
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
      <a-button type="primary" @click="handleAdd">新增读者</a-button>
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input v-model:value="searchForm.realName" placeholder="请输入姓名" style="width: 160px" />
        </a-form-item>
        <a-form-item label="手机号">
          <a-input v-model:value="searchForm.phone" placeholder="请输入手机号" style="width: 160px" />
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
      row-key="readerId"
      :scroll="{ x: 1200 }"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'gender'">
          <a-tag v-if="record.gender" :color="record.gender === '1' ? 'blue' : 'pink'">
            {{ record.gender === '1' ? '男' : '女' }}
          </a-tag>
        </template>
        <template v-if="column.key === 'status'">
          <a-tag :color="record.status === '0' ? 'green' : 'red'">
            {{ record.status === '0' ? '启用' : '停用' }}
          </a-tag>
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
            <a-popconfirm title="确认删除该读者吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="560">
      <a-form layout="vertical">
        <a-form-item label="姓名" required>
          <a-input v-model:value="formData.realName" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item label="昵称">
          <a-input v-model:value="formData.nickName" placeholder="请输入昵称" />
        </a-form-item>
        <a-form-item label="性别">
          <a-select v-model:value="formData.gender" placeholder="请选择性别">
            <a-select-option value="1">男</a-select-option>
            <a-select-option value="2">女</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="邮箱">
          <a-input v-model:value="formData.email" placeholder="请输入邮箱" />
        </a-form-item>
        <a-form-item label="电话">
          <a-input v-model:value="formData.phone" placeholder="请输入电话" />
        </a-form-item>
        <a-form-item label="手机">
          <a-input v-model:value="formData.mobile" placeholder="请输入手机号" />
        </a-form-item>
        <a-form-item label="地址">
          <a-textarea v-model:value="formData.address" :rows="2" placeholder="请输入地址" />
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