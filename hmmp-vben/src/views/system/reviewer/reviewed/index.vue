<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Table,
  Tag,
  Button,
  Modal,
  Descriptions,
  DescriptionsItem,
} from 'antdv-next';
import * as reviewerApi from '#/api/biz/reviewer';

const loading = ref(false);
const dataSource = ref<any[]>([]);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const columns = [
  { title: '序号', key: 'index', width: 80, customRender: ({ index }: any) => (pagination.current - 1) * pagination.pageSize + index + 1 },
  { title: '稿件标题', dataIndex: 'title', key: 'title', ellipsis: true },
  { title: '提交时间', dataIndex: 'submitTime', key: 'submitTime', width: 180 },
  { title: '审稿结论', dataIndex: 'conclusion', key: 'conclusion', width: 120 },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const },
];

// 详情弹窗
const detailVisible = ref(false);
const detailData = ref<any>({});

async function loadData(page = 1) {
  loading.value = true;
  try {
    const res = (await reviewerApi.getReviewedList({
      pageNum: page,
      pageSize: pagination.pageSize,
    })) as any;
    dataSource.value = res?.rows ?? res?.data ?? [];
    pagination.total = res?.total ?? 0;
    pagination.current = page;
  } catch {
    message.error('加载已审完稿件列表失败');
  } finally {
    loading.value = false;
  }
}

function onTableChange(pag: any) {
  loadData(pag.current);
}

function onView(record: any) {
  detailData.value = record;
  detailVisible.value = true;
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <Card title="已审完稿件">
      <Table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="taskId"
        :scroll="{ x: 700 }"
        @change="onTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'conclusion'">
            <Tag v-if="record.conclusion === 'accept'" color="green">录用</Tag>
            <Tag v-else-if="record.conclusion === 'revision'" color="orange">修改后录用</Tag>
            <Tag v-else-if="record.conclusion === 'reject'" color="red">退稿</Tag>
            <Tag v-else>{{ record.conclusion ?? '-' }}</Tag>
          </template>
          <template v-if="column.key === 'action'">
            <Button type="link" size="small" @click="onView(record)">查看详情</Button>
          </template>
        </template>
      </Table>
    </Card>

    <!-- 详情弹窗 -->
    <Modal
      v-model:open="detailVisible"
      title="审稿详情"
      :footer="null"
      width="640px"
    >
      <Descriptions bordered :column="1">
        <DescriptionsItem label="稿件标题">{{ detailData.title }}</DescriptionsItem>
        <DescriptionsItem label="稿件摘要">{{ detailData.abstract }}</DescriptionsItem>
        <DescriptionsItem label="接受时间">{{ detailData.acceptTime }}</DescriptionsItem>
        <DescriptionsItem label="截止时间">{{ detailData.deadlineTime }}</DescriptionsItem>
        <DescriptionsItem label="提交时间">{{ detailData.submitTime }}</DescriptionsItem>
        <DescriptionsItem label="审稿结论">
          <Tag v-if="detailData.conclusion === 'accept'" color="green">录用</Tag>
          <Tag v-else-if="detailData.conclusion === 'revision'" color="orange">修改后录用</Tag>
          <Tag v-else-if="detailData.conclusion === 'reject'" color="red">退稿</Tag>
          <Tag v-else>{{ detailData.conclusion ?? '-' }}</Tag>
        </DescriptionsItem>
        <DescriptionsItem label="审稿意见">{{ detailData.comment }}</DescriptionsItem>
      </Descriptions>
    </Modal>
  </div>
</template>