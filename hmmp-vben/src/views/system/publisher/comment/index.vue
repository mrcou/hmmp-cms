<template>
  <div class="publisher-comment-container">
    <a-card title="文章评论管理" :bordered="false">
      <a-form layout="inline" style="margin-bottom: 16px">
        <a-form-item label="文章标题">
          <a-input v-model:value="searchForm.articleTitle" placeholder="请输入文章标题" style="width: 200px" />
        </a-form-item>
        <a-form-item label="审核状态">
          <a-select v-model:value="searchForm.auditStatus" placeholder="请选择" style="width: 150px" allowClear>
            <a-select-option value="0">待审核</a-select-option>
            <a-select-option value="1">已通过</a-select-option>
            <a-select-option value="2">已拒绝</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="fetchData">查询</a-button>
          <a-button style="margin-left: 8px" @click="resetSearch">重置</a-button>
        </a-form-item>
      </a-form>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" row-key="commentId" @change="handleTableChange">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'auditStatus'">
            <a-tag :color="statusMap[record.auditStatus]?.color">{{ statusMap[record.auditStatus]?.label }}</a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a @click="handleAudit(record, '1')">通过</a>
              <a-popconfirm title="确认拒绝?" @confirm="handleAudit(record, '2')">
                <a style="color: red">拒绝</a>
              </a-popconfirm>
              <a-popconfirm title="确认删除?" @confirm="handleDelete(record)">
                <a style="color: red">删除</a>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import { requestClient } from '#/api/request';

const loading = ref(false);
const dataSource = ref([]);
const searchForm = reactive({ articleTitle: '', auditStatus: undefined });
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const statusMap: Record<string, { label: string; color: string }> = {
  '0': { label: '待审核', color: 'orange' },
  '1': { label: '已通过', color: 'green' },
  '2': { label: '已拒绝', color: 'red' },
};

const columns = [
  { title: 'ID', dataIndex: 'commentId', key: 'commentId', width: 80 },
  { title: '文章标题', dataIndex: 'articleTitle', key: 'articleTitle', ellipsis: true },
  { title: '评论人', dataIndex: 'userName', key: 'userName', width: 120 },
  { title: '评论内容', dataIndex: 'content', key: 'content', ellipsis: true },
  { title: '审核状态', key: 'auditStatus', width: 100 },
  { title: '评论时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' },
];

const fetchData = async () => {
  loading.value = true;
  try {
    const params = { ...searchForm, pageNum: pagination.current, pageSize: pagination.pageSize };
    const res = await requestClient.get('/publisher/comment/list', { params });
    dataSource.value = res.rows || [];
    pagination.total = res.total || 0;
  } catch (e) {
    message.error('获取数据失败');
  } finally {
    loading.value = false;
  }
};

const resetSearch = () => {
  searchForm.articleTitle = '';
  searchForm.auditStatus = undefined;
  fetchData();
};

const handleTableChange = (pag: any) => {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchData();
};

const handleAudit = async (record: any, status: string) => {
  try {
    await requestClient.put('/publisher/comment/audit', { commentId: record.commentId, auditStatus: status });
    message.success(status === '1' ? '审核通过' : '已拒绝');
    fetchData();
  } catch (e) {
    message.error('操作失败');
  }
};

const handleDelete = async (record: any) => {
  try {
    await requestClient.delete(`/publisher/comment/${record.commentId}`);
    message.success('删除成功');
    fetchData();
  } catch (e) {
    message.error('删除失败');
  }
};

onMounted(() => fetchData());
</script>