<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Table,
  Space,
  Button,
  Popconfirm,
  Modal,
  TextArea,
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
  { title: '摘要', dataIndex: 'abstract', key: 'abstract', ellipsis: true, width: 200 },
  { title: '邀请时间', dataIndex: 'acceptTime', key: 'acceptTime', width: 180 },
  { title: '截止时间', dataIndex: 'deadlineTime', key: 'deadlineTime', width: 180 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

// 拒绝审稿弹窗
const rejectModalVisible = ref(false);
const rejectReason = ref('');
const currentTaskId = ref(0);
const rejectLoading = ref(false);

async function loadData(page = 1) {
  loading.value = true;
  try {
    const res = (await reviewerApi.getNewInviteList({
      pageNum: page,
      pageSize: pagination.pageSize,
    })) as any;
    dataSource.value = res?.rows ?? res?.data ?? [];
    pagination.total = res?.total ?? 0;
    pagination.current = page;
  } catch {
    message.error('加载新邀请列表失败');
  } finally {
    loading.value = false;
  }
}

function onTableChange(pag: any) {
  loadData(pag.current);
}

async function onAccept(taskId: number) {
  try {
    await reviewerApi.acceptReview(taskId);
    message.success('已接受审稿邀请');
    loadData(pagination.current);
  } catch {
    message.error('操作失败');
  }
}

function onReject(taskId: number) {
  currentTaskId.value = taskId;
  rejectReason.value = '';
  rejectModalVisible.value = true;
}

async function onSubmitReject() {
  if (!rejectReason.value.trim()) {
    message.warning('请输入拒绝原因');
    return;
  }
  rejectLoading.value = true;
  try {
    await reviewerApi.rejectReview({ taskId: currentTaskId.value, reason: rejectReason.value });
    message.success('已拒绝审稿邀请');
    rejectModalVisible.value = false;
    loadData(pagination.current);
  } catch {
    message.error('操作失败');
  } finally {
    rejectLoading.value = false;
  }
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <Card title="新邀请审稿">
      <Table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="taskId"
        :scroll="{ x: 1000 }"
        @change="onTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <Space>
              <Popconfirm
                title="确认接受该审稿邀请吗？"
                ok-text="确认"
                cancel-text="取消"
                @confirm="onAccept(record.taskId)"
              >
                <Button type="primary" size="small">接受</Button>
              </Popconfirm>
              <Button danger size="small" @click="onReject(record.taskId)">拒绝</Button>
            </Space>
          </template>
        </template>
      </Table>
    </Card>

    <!-- 拒绝审稿弹窗 -->
    <Modal
      v-model:open="rejectModalVisible"
      title="拒绝审稿"
      :confirm-loading="rejectLoading"
      @ok="onSubmitReject"
    >
      <div class="mb-2">请输入拒绝原因：</div>
      <TextArea
        v-model:value="rejectReason"
        placeholder="请输入拒绝原因"
        :rows="4"
        :maxlength="500"
        show-count
      />
    </Modal>
  </div>
</template>