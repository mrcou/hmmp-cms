<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Issue[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ year: undefined as number | undefined, period: undefined as number | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增刊期');
const formData = reactive<PublisherApi.Issue>({ yearId: 0, year: new Date().getFullYear(), period: 1, status: '0', issueName: '', publishDate: '' });
const isEdit = ref(false);

const columns = [
  { title: '刊期ID', dataIndex: 'issueId', key: 'issueId', width: 100 },
  { title: '年份', dataIndex: 'year', key: 'year', width: 100 },
  { title: '期数', dataIndex: 'period', key: 'period', width: 80 },
  { title: '刊期名称', dataIndex: 'issueName', key: 'issueName', width: 160 },
  { title: '出版日期', dataIndex: 'publishDate', key: 'publishDate', width: 120 },
  { title: '文章数', dataIndex: 'articleCount', key: 'articleCount', width: 80 },
  { title: '页数', dataIndex: 'pageCount', key: 'pageCount', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getIssueList({
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
  modalTitle.value = '新增刊期';
  formData.yearId = 0;
  formData.year = new Date().getFullYear();
  formData.period = 1;
  formData.status = '0';
  formData.issueName = '';
  formData.publishDate = '';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Issue) {
  isEdit.value = true;
  modalTitle.value = '编辑刊期';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Issue) {
  await publisherApi.deleteIssue([record.issueId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updateIssue({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createIssue({ ...formData });
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

    <div class="mb-4">
      <a-button type="primary" @click="handleAdd">新增刊期</a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="issueId"
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
            <a-popconfirm title="确认删除该刊期吗？" @confirm="handleDelete(record)">
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
        <a-form-item label="刊期名称">
          <a-input v-model:value="formData.issueName" placeholder="如：2024年第1期" />
        </a-form-item>
        <a-form-item label="出版日期">
          <a-date-picker v-model:value="formData.publishDate" style="width: 100%" />
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