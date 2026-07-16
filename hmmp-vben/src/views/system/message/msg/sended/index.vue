<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import * as messageApi from '#/api/biz/message';

const loading = ref(false);
const queryParams = reactive({
  phone: '',
  sendStatus: undefined as string | undefined,
});
const dataSource = ref<messageApi.MessageApi.Sms[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const sendStatusOptions = [
  { label: '发送成功', value: '1' },
  { label: '发送失败', value: '2' },
];

const columns = [
  { title: '手机号', dataIndex: 'phone', key: 'phone', width: 150 },
  { title: '短信内容', dataIndex: 'content', key: 'content', width: 300, ellipsis: true },
  { title: '发送时间', dataIndex: 'sendTime', key: 'sendTime', width: 160 },
  { title: '发送状态', dataIndex: 'sendStatus', key: 'sendStatus', width: 100 },
  { title: '发送人', dataIndex: 'sendBy', key: 'sendBy', width: 120 },
  { title: '短信提供商', dataIndex: 'smsProvider', key: 'smsProvider', width: 120 },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await messageApi.getSmsSendedList({
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
  queryParams.phone = '';
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
    <a-card :bordered="false" title="已发送短信">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="手机号">
          <a-input v-model:value="queryParams.phone" placeholder="请输入手机号" allow-clear style="width: 200px" />
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
        row-key="smsId"
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