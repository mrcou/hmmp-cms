<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Table,
  Tag,
  Space,
  Button,
  Form,
  FormItem,
  Input,
  Select,
  SelectOption,
  Modal,
  Descriptions,
  DescriptionsItem,
} from 'antdv-next';
import * as authorApi from '#/api/biz/author';

const loading = ref(false);
const dataSource = ref<any[]>([]);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const searchForm = reactive({
  title: '',
  status: undefined as string | undefined,
});

const columns = [
  { title: '序号', key: 'index', width: 80, customRender: ({ index }: any) => (pagination.current - 1) * pagination.pageSize + index + 1 },
  { title: '稿件标题', dataIndex: 'title', key: 'title', ellipsis: true },
  { title: '投稿时间', dataIndex: 'submissionTime', key: 'submissionTime', width: 180 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '关键词', dataIndex: 'keywords', key: 'keywords', width: 180, ellipsis: true },
  { title: '操作', key: 'action', width: 120, fixed: 'right' as const },
];

// 详情弹窗
const detailVisible = ref(false);
const detailData = ref<any>({});

async function loadData(page = 1) {
  loading.value = true;
  try {
    const params: any = {
      pageNum: page,
      pageSize: pagination.pageSize,
    };
    if (searchForm.title) params.title = searchForm.title;
    if (searchForm.status) params.status = searchForm.status;

    const res = (await authorApi.getSubmissionList(params)) as any;
    dataSource.value = res?.rows ?? res?.data ?? [];
    pagination.total = res?.total ?? 0;
    pagination.current = page;
  } catch {
    message.error('加载稿件列表失败');
  } finally {
    loading.value = false;
  }
}

function onSearch() {
  loadData(1);
}

function onReset() {
  searchForm.title = '';
  searchForm.status = undefined;
  loadData(1);
}

function onTableChange(pag: any) {
  loadData(pag.current);
}

async function onView(record: any) {
  try {
    const res = (await authorApi.getSubmissionList({ submissionId: record.submissionId })) as any;
    detailData.value = res?.data ?? res ?? {};
    detailVisible.value = true;
  } catch {
    message.error('获取稿件详情失败');
  }
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <Card title="稿件查询">
      <!-- 搜索区域 -->
      <Form layout="inline" class="mb-4">
        <FormItem label="稿件标题">
          <Input
            v-model:value="searchForm.title"
            placeholder="请输入稿件标题"
            style="width: 200px"
            allow-clear
          />
        </FormItem>
        <FormItem label="状态">
          <Select
            v-model:value="searchForm.status"
            placeholder="请选择状态"
            style="width: 160px"
            allow-clear
          >
            <SelectOption value="pending">待审核</SelectOption>
            <SelectOption value="revision">待修改</SelectOption>
            <SelectOption value="reviewing">审稿中</SelectOption>
            <SelectOption value="approved">已通过</SelectOption>
            <SelectOption value="rejected">已拒绝</SelectOption>
            <SelectOption value="published">已发表</SelectOption>
          </Select>
        </FormItem>
        <FormItem>
          <Space>
            <Button type="primary" @click="onSearch">查询</Button>
            <Button @click="onReset">重置</Button>
          </Space>
        </FormItem>
      </Form>

      <Table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="submissionId"
        :scroll="{ x: 900 }"
        @change="onTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <Tag v-if="record.status === 'pending'" color="blue">待审核</Tag>
            <Tag v-else-if="record.status === 'revision'" color="orange">待修改</Tag>
            <Tag v-else-if="record.status === 'reviewing'" color="purple">审稿中</Tag>
            <Tag v-else-if="record.status === 'approved'" color="green">已通过</Tag>
            <Tag v-else-if="record.status === 'rejected'" color="red">已拒绝</Tag>
            <Tag v-else-if="record.status === 'published'" color="cyan">已发表</Tag>
            <Tag v-else>{{ record.status }}</Tag>
          </template>
          <template v-if="column.key === 'action'">
            <Button type="link" size="small" @click="onView(record)">查看</Button>
          </template>
        </template>
      </Table>
    </Card>

    <!-- 详情弹窗 -->
    <Modal
      v-model:open="detailVisible"
      title="稿件详情"
      :footer="null"
      width="680px"
    >
      <Descriptions bordered :column="1">
        <DescriptionsItem label="稿件标题">{{ detailData.title }}</DescriptionsItem>
        <DescriptionsItem label="摘要">{{ detailData.abstract }}</DescriptionsItem>
        <DescriptionsItem label="关键词">{{ detailData.keywords }}</DescriptionsItem>
        <DescriptionsItem label="投稿时间">{{ detailData.submissionTime }}</DescriptionsItem>
        <DescriptionsItem label="状态">
          <Tag v-if="detailData.status === 'pending'" color="blue">待审核</Tag>
          <Tag v-else-if="detailData.status === 'revision'" color="orange">待修改</Tag>
          <Tag v-else-if="detailData.status === 'reviewing'" color="purple">审稿中</Tag>
          <Tag v-else-if="detailData.status === 'approved'" color="green">已通过</Tag>
          <Tag v-else-if="detailData.status === 'rejected'" color="red">已拒绝</Tag>
          <Tag v-else-if="detailData.status === 'published'" color="cyan">已发表</Tag>
          <Tag v-else>{{ detailData.status }}</Tag>
        </DescriptionsItem>
        <DescriptionsItem v-if="detailData.feedback" label="反馈意见">{{ detailData.feedback }}</DescriptionsItem>
        <DescriptionsItem v-if="detailData.feedbackTime" label="反馈时间">{{ detailData.feedbackTime }}</DescriptionsItem>
      </Descriptions>
    </Modal>
  </div>
</template>