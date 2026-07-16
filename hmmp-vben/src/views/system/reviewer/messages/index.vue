<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';

import { Card, message, Table, Tag } from 'antdv-next';

import * as authorApi from '#/api/biz/author';

import ReviewerPageShell from '../_components/reviewer-page-shell.vue';

defineOptions({ name: 'ReviewerMessages' });

const loading = ref(false);
const dataSource = ref<any[]>([]);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const columns = [
  {
    title: '序号',
    key: 'index',
    width: 80,
    customRender: ({ index }: any) =>
      (pagination.current - 1) * pagination.pageSize + index + 1,
  },
  { title: '消息标题', dataIndex: 'title', key: 'title' },
  { title: '消息内容', dataIndex: 'content', key: 'content', ellipsis: true },
  { title: '消息类型', dataIndex: 'msgType', key: 'msgType', width: 120 },
  { title: '发送时间', dataIndex: 'sendTime', key: 'sendTime', width: 180 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
];

async function loadData(page = 1) {
  loading.value = true;
  try {
    const res = (await authorApi.getAuthorMessages({
      pageNum: page,
      pageSize: pagination.pageSize,
    })) as any;
    dataSource.value = res?.rows ?? res?.data ?? [];
    pagination.total = res?.total ?? 0;
    pagination.current = page;
  } catch {
    message.error('加载消息列表失败');
  } finally {
    loading.value = false;
  }
}

function onTableChange(pag: any) {
  loadData(pag.current);
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <ReviewerPageShell
    title="最新消息"
    description="审稿邀请、截止提醒与系统通知会汇总在这里。"
  >
    <Card :bordered="false" class="shadow-sm">
      <Table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="msgId"
        @change="onTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <Tag :color="record.status === '1' ? 'green' : 'orange'">
              {{ record.status === '1' ? '已读' : '未读' }}
            </Tag>
          </template>
        </template>
      </Table>
    </Card>
  </ReviewerPageShell>
</template>
