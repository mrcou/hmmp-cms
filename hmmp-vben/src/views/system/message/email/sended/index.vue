<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import * as messageApi from '#/api/biz/message';

const loading = ref(false);
const queryParams = reactive({
  toEmail: '',
  subject: '',
  sendStatus: undefined as string | undefined,
});
const dataSource = ref<messageApi.MessageApi.Email[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const sendStatusOptions = [
  { label: '发送成功', value: '1' },
  { label: '发送失败', value: '2' },
];

const columns = [
  { title: '收件人', dataIndex: 'toEmail', key: 'toEmail', width: 200, ellipsis: true },
  { title: '主题', dataIndex: 'subject', key: 'subject', width: 250, ellipsis: true },
  { title: '发送时间', dataIndex: 'sendTime', key: 'sendTime', width: 160 },
  { title: '发送状态', dataIndex: 'sendStatus', key: 'sendStatus', width: 100 },
  { title: '失败原因', dataIndex: 'failReason', key: 'failReason', width: 200, ellipsis: true },
  { title: '发送人', dataIndex: 'sendBy', key: 'sendBy', width: 120 },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await messageApi.getEmailSendedList({
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
      ...queryParams,
    });
    dataSource.value = res.rows ?? [];
    pagination.total = res.total ?? 0;
  } finally {
    loading.value = false;
  }
}

function handleSearch() {
  pagination.current = 1;
  fetchData();
}

function handleReset() {
  queryParams.toEmail = '';
  queryParams.subject = '';
  queryParams.sendStatus = undefined;
  pagination.current = 1;
  fetchData();
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="已发送邮件">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="收件人">
          <a-input v-model:value="queryParams.toEmail" placeholder="请输入收件人邮箱" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item label="主题">
          <a-input v-model:value="queryParams.subject" placeholder="请输入主题" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item label="发送状态">
          <a-select v-model:value="queryParams.sendStatus" placeholder="请选择" allow-clear style="width: 120px">
            <a-select-option v-for="opt in sendStatusOptions" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="emailId"
        :scroll="{ x: 1000 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'sendStatus'">
            <a-tag :color="record.sendStatus === '1' ? 'green' : 'red'">
              {{ record.sendStatus === '1' ? '发送成功' : '发送失败' }}
            </a-tag>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>