<script lang="ts" setup>
import { reactive, ref } from 'vue';

import { message } from 'antdv-next';

const tableData = ref<any[]>([]);
const tableLoading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showTotal: (total: number) => `共 ${total} 条`,
});

const columns = [
  { title: '意见ID', dataIndex: 'opinionId', key: 'opinionId', width: 90 },
  { title: '稿件ID', dataIndex: 'manuscriptId', key: 'manuscriptId', width: 90 },
  { title: '审稿人', dataIndex: 'reviewerName', key: 'reviewerName', width: 120 },
  { title: '审稿意见', dataIndex: 'opinion', key: 'opinion', ellipsis: true },
  { title: '删除时间', dataIndex: 'deleteTime', key: 'deleteTime', width: 180 },
  { title: '操作', key: 'action', width: 120 },
];

function onRecover(row: any) {
  message.success(`已恢复审稿意见：${row.opinionId ?? ''}`);
}
</script>

<template>
  <div class="p-4">
    <a-card title="审稿意见恢复">
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        row-key="opinionId"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <a-button size="small" type="primary" @click="onRecover(record)">恢复</a-button>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>
