<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';

const tableData = ref<any[]>([]);
const tableLoading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showTotal: (total: number) => `共 ${total} 条`,
});

const columns = [
  { title: '组稿ID', dataIndex: 'groupId', key: 'groupId', width: 90 },
  { title: '组稿名称', dataIndex: 'groupName', key: 'groupName' },
  { title: '组稿类型', dataIndex: 'groupType', key: 'groupType', width: 120 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
];

onMounted(() => {
  loadTable();
});

async function loadTable() {
  tableLoading.value = true;
  try {
    const res = await editorApi.getGroupList({
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
      groupType: 'simple',
    });
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载简单组稿列表失败');
  } finally {
    tableLoading.value = false;
  }
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  loadTable();
}
</script>

<template>
  <div class="p-4">
    <a-card title="简单组稿">
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        row-key="groupId"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      />
    </a-card>
  </div>
</template>
