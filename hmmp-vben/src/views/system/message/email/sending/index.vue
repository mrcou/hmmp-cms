<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as messageApi from '#/api/biz/message';

const loading = ref(false);
const queryParams = reactive({
  toEmail: '',
  subject: '',
});
const dataSource = ref<messageApi.MessageApi.Email[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const sendModalVisible = ref(false);
const sendFormData = reactive<messageApi.MessageApi.Email>({
  toEmail: '',
  subject: '',
  content: '',
});

const columns = [
  { title: '收件人', dataIndex: 'toEmail', key: 'toEmail', width: 200, ellipsis: true },
  { title: '主题', dataIndex: 'subject', key: 'subject', width: 250, ellipsis: true },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 160 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await messageApi.getEmailSendingList({
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
    toEmail: '',
    subject: '',
    content: '',
  });
  sendModalVisible.value = true;
}

async function handleSend() {
  if (!sendFormData.toEmail) {
    message.warning('请输入收件人邮箱');
    return;
  }
  if (!sendFormData.subject) {
    message.warning('请输入邮件主题');
    return;
  }
  try {
    await messageApi.sendEmail(sendFormData);
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
    <a-card :bordered="false" title="待发送邮件">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="收件人">
          <a-input v-model:value="queryParams.toEmail" placeholder="请输入收件人邮箱" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item label="主题">
          <a-input v-model:value="queryParams.subject" placeholder="请输入主题" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-space class="mb-4">
        <a-button type="primary" @click="openSendModal">发送邮件</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="emailId"
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
      title="发送邮件"
      ok-text="发送"
      cancel-text="取消"
      @ok="handleSend"
      destroy-on-close
      :width="600"
    >
      <a-form layout="vertical">
        <a-form-item label="收件人" required>
          <a-input v-model:value="sendFormData.toEmail" placeholder="请输入收件人邮箱" />
        </a-form-item>
        <a-form-item label="主题" required>
          <a-input v-model:value="sendFormData.subject" placeholder="请输入邮件主题" />
        </a-form-item>
        <a-form-item label="内容">
          <a-textarea v-model:value="sendFormData.content" placeholder="请输入邮件内容" :rows="6" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>