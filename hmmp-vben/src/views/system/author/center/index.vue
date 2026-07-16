<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Table,
  Tag,
} from 'antdv-next';
import * as authorApi from '#/api/biz/author';

const loading = ref(false);
const dataSource = ref<any[]>([]);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const columns = [
  { title: '序号', key: 'index', width: 80, customRender: ({ index }: any) => (pagination.current - 1) * pagination.pageSize + index + 1 },
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
  <div class="p-4">
    <Card title="最新消息">
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
            <Tag v-if="record.status === '0'" color="blue">未读</Tag>
            <Tag v-else-if="record.status === '1'" color="green">已读</Tag>
            <Tag v-else>{{ record.status }}</Tag>
          </template>
          <template v-if="column.key === 'msgType'">
            <Tag v-if="record.msgType === 'system'" color="purple">系统消息</Tag>
            <Tag v-else-if="record.msgType === 'review'" color="orange">审稿通知</Tag>
            <Tag v-else-if="record.msgType === 'publish'" color="green">出版通知</Tag>
            <Tag v-else>{{ record.msgType }}</Tag>
          </template>
        </template>
      </Table>
    </Card>
  </div>
</template>