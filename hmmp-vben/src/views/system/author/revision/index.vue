<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Table,
  Tag,
  Space,
  Button,
  Popconfirm,
  Modal,
  Form,
  FormItem,
  Input,
  TextArea,
  Upload,
} from 'antdv-next';
import type { UploadChangeParam } from 'antdv-next';
import * as authorApi from '#/api/biz/author';
import AuthorPageShell from '../_components/author-page-shell.vue';

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
  { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
  { title: '反馈意见', dataIndex: 'feedback', key: 'feedback', ellipsis: true, width: 200 },
  { title: '反馈时间', dataIndex: 'feedbackTime', key: 'feedbackTime', width: 180 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

// 修改稿件弹窗
const editModalVisible = ref(false);
const editLoading = ref(false);
const editFormRef = ref();
const editFileList = ref<any[]>([]);
const editFormState = reactive({
  submissionId: 0,
  title: '',
  abstract: '',
  keywords: '',
});

const editRules: Record<string, any> = {
  title: [{ required: true, message: '请输入稿件标题', trigger: 'blur' }],
  abstract: [{ required: true, message: '请输入稿件摘要', trigger: 'blur' }],
  keywords: [{ required: true, message: '请输入关键词', trigger: 'blur' }],
};

async function loadData(page = 1) {
  loading.value = true;
  try {
    const res = (await authorApi.getRevisionList({
      pageNum: page,
      pageSize: pagination.pageSize,
    })) as any;
    dataSource.value = res?.rows ?? res?.data ?? [];
    pagination.total = res?.total ?? 0;
    pagination.current = page;
  } catch {
    message.error('加载待修改稿列表失败');
  } finally {
    loading.value = false;
  }
}

function onTableChange(pag: any) {
  loadData(pag.current);
}

function onEdit(record: any) {
  editFormState.submissionId = record.submissionId;
  editFormState.title = record.title;
  editFormState.abstract = record.abstract;
  editFormState.keywords = record.keywords;
  editFileList.value = [];
  editModalVisible.value = true;
}

function onEditUploadChange(info: UploadChangeParam) {
  editFileList.value = info.fileList;
}

function beforeEditUpload(file: File) {
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

async function onSubmitEdit() {
  try {
    await editFormRef.value?.validate();
  } catch {
    return;
  }

  editLoading.value = true;
  try {
    const formData = new FormData();
    formData.append('submissionId', String(editFormState.submissionId));
    formData.append('title', editFormState.title);
    formData.append('abstract', editFormState.abstract);
    formData.append('keywords', editFormState.keywords);
    if (editFileList.value.length > 0 && editFileList.value[0]?.originFileObj) {
      formData.append('file', editFileList.value[0].originFileObj);
    }
    await authorApi.updateSubmission(formData as any);
    message.success('修改成功');
    editModalVisible.value = false;
    loadData(pagination.current);
  } catch {
    message.error('修改失败');
  } finally {
    editLoading.value = false;
  }
}

function onDelete(record: any) {
  authorApi.deleteSubmission([record.submissionId]).then(() => {
    message.success('删除成功');
    loadData(pagination.current);
  });
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <AuthorPageShell
    title="待修改稿"
    description="编辑部退回修改的稿件会显示在这里，请按反馈意见修订后重新提交。"
  >
    <Card :bordered="false" class="shadow-sm">
      <Table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="submissionId"
        :scroll="{ x: 1000 }"
        @change="onTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <Tag v-if="record.status === 'revision'" color="orange">待修改</Tag>
            <Tag v-else-if="record.status === 'pending'" color="blue">待审核</Tag>
            <Tag v-else-if="record.status === 'approved'" color="green">已通过</Tag>
            <Tag v-else-if="record.status === 'rejected'" color="red">已拒绝</Tag>
            <Tag v-else>{{ record.status }}</Tag>
          </template>
          <template v-if="column.key === 'action'">
            <Space>
              <Button type="link" size="small" @click="onEdit(record)">修改</Button>
              <Popconfirm
                title="确认删除该稿件吗？"
                ok-text="确认"
                cancel-text="取消"
                @confirm="onDelete(record)"
              >
                <Button type="link" danger size="small">删除</Button>
              </Popconfirm>
            </Space>
          </template>
        </template>
      </Table>
    </Card>

    <Modal
      v-model:open="editModalVisible"
      title="修改稿件"
      width="720px"
      :confirm-loading="editLoading"
      @ok="onSubmitEdit"
    >
      <Form
        ref="editFormRef"
        :model="editFormState"
        :rules="editRules"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 18 }"
      >
        <FormItem label="稿件标题" name="title">
          <Input
            v-model:value="editFormState.title"
            placeholder="请输入稿件标题"
            :maxlength="200"
          />
        </FormItem>

        <FormItem label="摘要" name="abstract">
          <TextArea
            v-model:value="editFormState.abstract"
            placeholder="请输入稿件摘要"
            :rows="6"
            :maxlength="2000"
            show-count
          />
        </FormItem>

        <FormItem label="关键词" name="keywords">
          <Input
            v-model:value="editFormState.keywords"
            placeholder="请输入关键词，多个关键词用逗号分隔"
            :maxlength="200"
          />
        </FormItem>

        <FormItem label="上传稿件" name="file">
          <Upload
            v-model:file-list="editFileList"
            :before-upload="beforeEditUpload"
            :max-count="1"
            @change="onEditUploadChange"
          >
            <Button>选择文件</Button>
          </Upload>
        </FormItem>
      </Form>
    </Modal>
  </AuthorPageShell>
</template>