<script lang="ts" setup>
import type { EditorApi } from '#/api/biz/editor';

import { onMounted, reactive, ref } from 'vue';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';

// ---------- 状态常量 ----------
const manuscriptStatusMap: Record<string, { label: string; color: string }> = {
  '0': { label: '待处理', color: 'blue' },
  '1': { label: '已分配', color: 'cyan' },
  '2': { label: '审稿中', color: 'orange' },
  '3': { label: '退修中', color: 'purple' },
  '4': { label: '已录用', color: 'green' },
  '5': { label: '已退稿', color: 'red' },
  '6': { label: '已发表', color: 'geekblue' },
};

const manuscriptTypeMap: Record<string, string> = {
  '1': '研究论文',
  '2': '综述',
  '3': '简报',
  '4': '评论',
  '5': '其他',
};

// ---------- 查询参数 ----------
const searchForm = reactive({
  fileNo: '',
  title: '',
  authorNames: '',
  status: undefined as string | undefined,
});

// ---------- 表格 ----------
const tableData = ref<EditorApi.Manuscript[]>([]);
const tableLoading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  showTotal: (total: number) => `共 ${total} 条`,
});

const columns = [
  { title: '稿件ID', dataIndex: 'manuscriptId', key: 'manuscriptId', width: 80 },
  { title: '文件编号', dataIndex: 'fileNo', key: 'fileNo', width: 140 },
  { title: '标题', dataIndex: 'title', key: 'title', ellipsis: true, width: 220 },
  { title: '作者', dataIndex: 'authorNames', key: 'authorNames', width: 120 },
  { title: '稿件类型', dataIndex: 'manuscriptType', key: 'manuscriptType', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '删除时间', dataIndex: 'deleteTime', key: 'deleteTime', width: 160 },
  { title: '删除人', dataIndex: 'deleteBy', key: 'deleteBy', width: 110 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' },
];

// ---------- 生命周期 ----------
onMounted(() => {
  loadTable();
});

// ---------- 方法 ----------
async function loadTable() {
  tableLoading.value = true;
  try {
    const params = {
      ...searchForm,
      isDeleted: '1',
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
    };
    const res = await editorApi.getManuscriptList(params);
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载删除稿件列表失败');
  } finally {
    tableLoading.value = false;
  }
}

function onSearch() {
  pagination.current = 1;
  loadTable();
}

function onReset() {
  searchForm.fileNo = '';
  searchForm.title = '';
  searchForm.authorNames = '';
  searchForm.status = undefined;
  pagination.current = 1;
  loadTable();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  loadTable();
}

function getStatusTag(status: string | undefined) {
  return manuscriptStatusMap[status ?? ''] ?? { label: status ?? '未知', color: 'default' };
}

function getTypeLabel(type: string | undefined) {
  return manuscriptTypeMap[type ?? ''] ?? type ?? '';
}
</script>

<template>
  <div class="p-4">
    <!-- 搜索区域 -->
    <a-card class="mb-4" size="small">
      <a-form layout="inline">
        <a-form-item label="文件编号">
          <a-input v-model:value="searchForm.fileNo" placeholder="请输入" allow-clear style="width:150px" />
        </a-form-item>
        <a-form-item label="标题">
          <a-input v-model:value="searchForm.title" placeholder="请输入" allow-clear style="width:180px" />
        </a-form-item>
        <a-form-item label="作者">
          <a-input v-model:value="searchForm.authorNames" placeholder="请输入" allow-clear style="width:130px" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear style="width:130px">
            <a-select-option v-for="(val, key) in manuscriptStatusMap" :key="key" :value="key">
              {{ val.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="onSearch">查询</a-button>
            <a-button @click="onReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>
    </a-card>

    <!-- 表格区域 -->
    <a-card>
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        :scroll="{ x: 1200 }"
        row-key="manuscriptId"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'manuscriptType'">
            {{ getTypeLabel(record.manuscriptType) }}
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="getStatusTag(record.status).color">
              {{ getStatusTag(record.status).label }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button size="small" @click="() => {}">查看详情</a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>