<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Article[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ title: '', status: undefined as string | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增文章');
const formData = reactive<PublisherApi.Article>({ title: '', subtitle: '', authorNames: '', abstract: '', keywords: '', issueId: undefined, columnId: undefined, status: '0' });
const isEdit = ref(false);

const columns = [
  { title: '文章ID', dataIndex: 'articleId', key: 'articleId', width: 100 },
  { title: '标题', dataIndex: 'title', key: 'title', width: 240, ellipsis: true },
  { title: '副标题', dataIndex: 'subtitle', key: 'subtitle', width: 180, ellipsis: true },
  { title: '作者', dataIndex: 'authorNames', key: 'authorNames', width: 120 },
  { title: '刊期ID', dataIndex: 'issueId', key: 'issueId', width: 80 },
  { title: '栏目ID', dataIndex: 'columnId', key: 'columnId', width: 80 },
  { title: '起始页', dataIndex: 'pageStart', key: 'pageStart', width: 80 },
  { title: '字数', dataIndex: 'wordCount', key: 'wordCount', width: 80 },
  { title: '浏览量', dataIndex: 'viewCount', key: 'viewCount', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getArticleList({
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
  searchForm.title = '';
  searchForm.status = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增文章';
  formData.title = '';
  formData.subtitle = '';
  formData.authorNames = '';
  formData.abstract = '';
  formData.keywords = '';
  formData.issueId = undefined;
  formData.columnId = undefined;
  formData.status = '0';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Article) {
  isEdit.value = true;
  modalTitle.value = '编辑文章';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Article) {
  await publisherApi.deleteArticle([record.articleId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updateArticle({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createArticle({ ...formData });
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
      <a-form-item label="文章标题">
        <a-input v-model:value="searchForm.title" placeholder="请输入文章标题" style="width: 200px" />
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
      <a-button type="primary" @click="handleAdd">新增文章</a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="articleId"
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
            <a-popconfirm title="确认删除该文章吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="600">
      <a-form layout="vertical">
        <a-form-item label="文章标题" required>
          <a-input v-model:value="formData.title" placeholder="请输入文章标题" />
        </a-form-item>
        <a-form-item label="副标题">
          <a-input v-model:value="formData.subtitle" placeholder="请输入副标题" />
        </a-form-item>
        <a-form-item label="作者">
          <a-input v-model:value="formData.authorNames" placeholder="多个作者用逗号分隔" />
        </a-form-item>
        <a-form-item label="摘要">
          <a-textarea v-model:value="formData.abstract" :rows="3" placeholder="请输入摘要" />
        </a-form-item>
        <a-form-item label="关键词">
          <a-input v-model:value="formData.keywords" placeholder="多个关键词用逗号分隔" />
        </a-form-item>
        <a-form-item label="所属刊期ID">
          <a-input-number v-model:value="formData.issueId" style="width: 100%" />
        </a-form-item>
        <a-form-item label="所属栏目ID">
          <a-input-number v-model:value="formData.columnId" style="width: 100%" />
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