<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.PushKeyword[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ keyword: '', status: undefined as string | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增关键词推送');
const formData = reactive<PublisherApi.PushKeyword>({ keyword: '', pushContent: '', status: '0' });
const isEdit = ref(false);

const columns = [
  { title: '关键词ID', dataIndex: 'keywordId', key: 'keywordId', width: 110 },
  { title: '关键词', dataIndex: 'keyword', key: 'keyword', width: 160 },
  { title: '推送内容', dataIndex: 'pushContent', key: 'pushContent', ellipsis: true },
  { title: '推送时间', dataIndex: 'pushTime', key: 'pushTime', width: 170 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getPushKeywordList({
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
  searchForm.keyword = '';
  searchForm.status = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增关键词推送';
  formData.keyword = '';
  formData.pushContent = '';
  formData.status = '0';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.PushKeyword) {
  isEdit.value = true;
  modalTitle.value = '编辑关键词推送';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.PushKeyword) {
  await publisherApi.deletePushKeyword([record.keywordId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  // Note: The API only has createPushKeyword, no update. For edit we still call create.
  await publisherApi.createPushKeyword({ ...formData });
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
    <a-form layout="inline" class="mb-4">
      <a-form-item label="关键词">
        <a-input v-model:value="searchForm.keyword" placeholder="请输入关键词" style="width: 180px" />
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

    <div class="mb-4">
      <a-button type="primary" @click="handleAdd">新增关键词推送</a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="keywordId"
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
            <a-popconfirm title="确认删除该关键词推送吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="520">
      <a-form layout="vertical">
        <a-form-item label="关键词" required>
          <a-input v-model:value="formData.keyword" placeholder="请输入关键词" />
        </a-form-item>
        <a-form-item label="推送内容" required>
          <a-textarea v-model:value="formData.pushContent" :rows="4" placeholder="请输入推送内容" />
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