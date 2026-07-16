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
  { title: '文件ID', dataIndex: 'fileId', key: 'fileId', width: 90 },
  { title: '稿件ID', dataIndex: 'manuscriptId', key: 'manuscriptId', width: 90 },
  { title: '文件名', dataIndex: 'fileName', key: 'fileName' },
  { title: '上传时间', dataIndex: 'uploadTime', key: 'uploadTime', width: 180 },
  { title: '操作', key: 'action', width: 120 },
];

function onDownload(row: any) {
  message.info(`下载排版文件：${row.fileName ?? row.fileId ?? ''}`);
}
</script>

<template>
  <div class="p-4">
    <a-card title="待下载排版文件">
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        row-key="fileId"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <a-button size="small" type="link" @click="onDownload(record)">下载</a-button>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>
