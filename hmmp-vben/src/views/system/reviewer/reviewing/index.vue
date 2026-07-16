<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Table,
  Button,
  Modal,
  Form,
  FormItem,
  TextArea,
  Select,
  SelectOption,
  Upload,
} from 'antdv-next';
import type { UploadChangeParam } from 'antdv-next';
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
  { title: '接受时间', dataIndex: 'acceptTime', key: 'acceptTime', width: 180 },
  { title: '截止时间', dataIndex: 'deadlineTime', key: 'deadlineTime', width: 180 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' as const },
];

// 提交审稿意见弹窗
const reviewModalVisible = ref(false);
const reviewLoading = ref(false);
const reviewFormRef = ref();
const reviewFileList = ref<any[]>([]);
const currentTaskId = ref(0);
const reviewForm = reactive({
  conclusion: undefined as string | undefined,
  comment: '',
});

const reviewRules: Record<string, any[]> = {
  conclusion: [{ required: true, message: '请选择审稿结论', trigger: 'change' }],
  comment: [{ required: true, message: '请输入审稿意见', trigger: 'blur' }],
};

async function loadData(page = 1) {
  loading.value = true;
  try {
    const res = (await reviewerApi.getReviewingList({
      pageNum: page,
      pageSize: pagination.pageSize,
    })) as any;
    dataSource.value = res?.rows ?? res?.data ?? [];
    pagination.total = res?.total ?? 0;
    pagination.current = page;
  } catch {
    message.error('加载未审完稿件列表失败');
  } finally {
    loading.value = false;
  }
}

function onTableChange(pag: any) {
  loadData(pag.current);
}

function onSubmitReview(record: any) {
  currentTaskId.value = record.taskId;
  reviewForm.conclusion = undefined;
  reviewForm.comment = '';
  reviewFileList.value = [];
  reviewModalVisible.value = true;
}

function onReviewUploadChange(info: UploadChangeParam) {
  reviewFileList.value = info.fileList;
}

function beforeReviewUpload(file: File) {
  const isValidType = [
    'application/pdf',
    'application/msword',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
  ].includes(file.type);
  if (!isValidType) {
    message.error('仅支持 PDF、DOC、DOCX 格式文件');
    return false;
  }
  const isLt10M = file.size / 1024 / 1024 < 10;
  if (!isLt10M) {
    message.error('文件大小不能超过 10MB');
    return false;
  }
  return false;
}

async function onSubmitReviewForm() {
  try {
    await reviewFormRef.value?.validate();
  } catch {
    return;
  }

  reviewLoading.value = true;
  try {
    const formData = new FormData();
    formData.append('taskId', String(currentTaskId.value));
    formData.append('conclusion', reviewForm.conclusion!);
    formData.append('comment', reviewForm.comment);
    if (reviewFileList.value.length > 0 && reviewFileList.value[0]?.originFileObj) {
      formData.append('file', reviewFileList.value[0].originFileObj);
    }
    await reviewerApi.submitReview(formData as any);
    message.success('审稿意见提交成功');
    reviewModalVisible.value = false;
    loadData(pagination.current);
  } catch {
    message.error('提交失败');
  } finally {
    reviewLoading.value = false;
  }
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <Card title="未审完稿件">
      <Table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="taskId"
        :scroll="{ x: 800 }"
        @change="onTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <Button type="primary" size="small" @click="onSubmitReview(record)">
              提交审稿意见
            </Button>
          </template>
        </template>
      </Table>
    </Card>

    <!-- 提交审稿意见弹窗 -->
    <Modal
      v-model:open="reviewModalVisible"
      title="提交审稿意见"
      width="640px"
      :confirm-loading="reviewLoading"
      @ok="onSubmitReviewForm"
    >
      <Form
        ref="reviewFormRef"
        :model="reviewForm"
        :rules="reviewRules"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 18 }"
      >
        <FormItem label="审稿结论" name="conclusion">
          <Select
            v-model:value="reviewForm.conclusion"
            placeholder="请选择审稿结论"
          >
            <SelectOption value="accept">录用</SelectOption>
            <SelectOption value="revision">修改后录用</SelectOption>
            <SelectOption value="reject">退稿</SelectOption>
          </Select>
        </FormItem>

        <FormItem label="审稿意见" name="comment">
          <TextArea
            v-model:value="reviewForm.comment"
            placeholder="请输入审稿意见"
            :rows="6"
            :maxlength="2000"
            show-count
          />
        </FormItem>

        <FormItem label="上传附件" name="file">
          <Upload
            v-model:file-list="reviewFileList"
            :before-upload="beforeReviewUpload"
            :max-count="1"
            @change="onReviewUploadChange"
          >
            <Button>
              <template #icon>
                <span>📎</span>
              </template>
              选择文件
            </Button>
          </Upload>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>