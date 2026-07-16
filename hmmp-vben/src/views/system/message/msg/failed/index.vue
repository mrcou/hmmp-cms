<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as messageApi from '#/api/biz/message';

const loading = ref(false);
const queryParams = reactive({
  phone: '',
});
const dataSource = ref<messageApi.MessageApi.Sms[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const columns = [
  { title: '手机号', dataIndex: 'phone', key: 'phone', width: 150 },
  { title: '短信内容', dataIndex: 'content', key: 'content', width: 280, ellipsis: true },
  { title: '失败原因', dataIndex: 'failReason', key: 'failReason', width: 200, ellipsis: true },
  { title: '重试次数', dataIndex: 'retryCount', key: 'retryCount', width: 100 },
  { title: '发送时间', dataIndex: 'sendTime', key: 'sendTime', width: 160 },
  { title: '发送人', dataIndex: 'sendBy', key: 'sendBy', width: 120 },
  { title: '操作', key: 'action', width: 150, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await messageApi.getSmsFailedList({
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
  pagination.current = 1;
  fetchData();
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchData();
}

async function handleResend(record: messageApi.MessageApi.Sms) {
  try {
    await messageApi.sendSms({
      phone: record.phone,
      content: record.content,
    });
    message.success('重发成功');
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: messageApi.MessageApi.Sms) {
  await messageApi.deleteSms([record.smsId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="失败的短信">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="手机号">
          <a-input v-model:value="queryParams.phone" placeholder="请输入手机号" allow-clear style="width: 200px" />
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
        :scroll="{ x: 1200 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'retryCount'">
            <a-tag :color="(record.retryCount ?? 0) > 3 ? 'red' : 'orange'">
              {{ record.retryCount ?? 0 }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="handleResend(record)">重发</a-button>
              <a-popconfirm
                title="确认删除该短信吗？"
                ok-text="确认"
                cancel-text="取消"
                @confirm="handleDelete(record)"
              >
                <a-button type="link" danger size="small">删除</a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>