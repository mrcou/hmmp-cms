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

const sendModalVisible = ref(false);
const sendFormData = reactive<messageApi.MessageApi.Sms>({
  phone: '',
  content: '',
});

const columns = [
  { title: '手机号', dataIndex: 'phone', key: 'phone', width: 150 },
  { title: '短信内容', dataIndex: 'content', key: 'content', width: 300, ellipsis: true },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 160 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await messageApi.getSmsSendingList({
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

function openSendModal() {
  Object.assign(sendFormData, {
    phone: '',
    content: '',
  });
  sendModalVisible.value = true;
}

async function handleSend() {
  if (!sendFormData.phone) {
    message.warning('请输入手机号');
    return;
  }
  if (!sendFormData.content) {
    message.warning('请输入短信内容');
    return;
  }
  try {
    await messageApi.sendSms(sendFormData);
    message.success('发送成功');
    sendModalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="待发送短信">
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

      <a-space class="mb-4">
        <a-button type="primary" @click="openSendModal">发送短信</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="smsId"
        :scroll="{ x: 800 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column }">
          <template v-if="column.key === 'action'">
            <a-button type="link" size="small" @click="openSendModal">发送</a-button>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal
      v-model:open="sendModalVisible"
      title="发送短信"
      ok-text="发送"
      cancel-text="取消"
      @ok="handleSend"
      destroy-on-close
      :width="500"
    >
      <a-form layout="vertical">
        <a-form-item label="手机号" required>
          <a-input v-model:value="sendFormData.phone" placeholder="请输入手机号" />
        </a-form-item>
        <a-form-item label="短信内容" required>
          <a-textarea v-model:value="sendFormData.content" placeholder="请输入短信内容" :rows="5" :maxlength="500" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>