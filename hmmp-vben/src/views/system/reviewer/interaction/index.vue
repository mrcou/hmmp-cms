<script lang="ts" setup>
/**
 * 新互动 —— 基础列表页（待对接审稿人互动消息接口）
 */
import { reactive, ref } from 'vue';

import { Card, Empty, Table } from 'antdv-next';

import ReviewerPageShell from '../_components/reviewer-page-shell.vue';

defineOptions({ name: 'ReviewerInteraction' });

const loading = ref(false);
const dataSource = ref<any[]>([]);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
});

const columns = [
  {
    title: '序号',
    key: 'index',
    width: 80,
    customRender: ({ index }: any) =>
      (pagination.current - 1) * pagination.pageSize + index + 1,
  },
  { title: '标题', dataIndex: 'title', key: 'title', ellipsis: true },
  { title: '类型', dataIndex: 'msgType', key: 'msgType', width: 120 },
  { title: '发送时间', dataIndex: 'sendTime', key: 'sendTime', width: 180 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
];
</script>

<template>
  <ReviewerPageShell
    title="新互动"
    description="编辑部与作者相关的互动消息将汇总在这里。"
  >
    <Card :bordered="false" class="shadow-sm">
      <Table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="msgId"
        size="middle"
      >
        <template #emptyText>
          <Empty description="暂无新互动" />
        </template>
      </Table>
    </Card>
  </ReviewerPageShell>
</template>
