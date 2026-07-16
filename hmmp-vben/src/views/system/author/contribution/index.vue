<script lang="ts" setup>
import { reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Form,
  FormItem,
  Input,
  TextArea,
  Upload,
  Button,
  Space,
} from 'antdv-next';
import type { UploadChangeParam } from 'antdv-next';
import * as authorApi from '#/api/biz/author';
import AuthorPageShell from '../_components/author-page-shell.vue';

const formRef = ref();
const loading = ref(false);
const fileList = ref<any[]>([]);

const formState = reactive({
  title: '',
  abstract: '',
  keywords: '',
});

const rules: Record<string, any> = {
  title: [{ required: true, message: '请输入稿件标题', trigger: 'blur' }],
  abstract: [{ required: true, message: '请输入稿件摘要', trigger: 'blur' }],
  keywords: [{ required: true, message: '请输入关键词', trigger: 'blur' }],
};

function onUploadChange(info: UploadChangeParam) {
  fileList.value = info.fileList;
}

function beforeUpload(file: File) {
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
  return false; // 阻止自动上传，手动提交
}

async function onSubmit() {
  try {
    await formRef.value?.validate();
  } catch {
    return;
  }

  loading.value = true;
  try {
    const formData = new FormData();
    formData.append('title', formState.title);
    formData.append('abstract', formState.abstract);
    formData.append('keywords', formState.keywords);
    if (fileList.value.length > 0 && fileList.value[0]?.originFileObj) {
      formData.append('file', fileList.value[0].originFileObj);
    }
    await authorApi.submitNewArticle(formData as any);
    message.success('投稿成功');
    formState.title = '';
    formState.abstract = '';
    formState.keywords = '';
    fileList.value = [];
    formRef.value?.resetFields();
  } catch {
    message.error('投稿失败');
  } finally {
    loading.value = false;
  }
}

function onReset() {
  formState.title = '';
  formState.abstract = '';
  formState.keywords = '';
  fileList.value = [];
  formRef.value?.resetFields();
}
</script>

<template>
  <AuthorPageShell
    title="提交新稿"
    description="填写稿件信息并上传正文文件，提交后可在「稿件查询」中跟踪进度。"
  >
    <Card :bordered="false" class="shadow-sm">
      <Form
        ref="formRef"
        :model="formState"
        :rules="rules"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 18 }"
      >
        <FormItem label="稿件标题" name="title">
          <Input
            v-model:value="formState.title"
            placeholder="请输入稿件标题"
            :maxlength="200"
          />
        </FormItem>

        <FormItem label="摘要" name="abstract">
          <TextArea
            v-model:value="formState.abstract"
            placeholder="请输入稿件摘要"
            :rows="6"
            :maxlength="2000"
            show-count
          />
        </FormItem>

        <FormItem label="关键词" name="keywords">
          <Input
            v-model:value="formState.keywords"
            placeholder="请输入关键词，多个关键词用逗号分隔"
            :maxlength="200"
          />
        </FormItem>

        <FormItem label="上传稿件" name="file">
          <Upload
            v-model:file-list="fileList"
            :before-upload="beforeUpload"
            :max-count="1"
            @change="onUploadChange"
          >
            <Button>选择文件</Button>
          </Upload>
          <span class="text-muted-foreground ml-2 text-sm">
            支持 PDF、DOC、DOCX，单个文件不超过 10MB
          </span>
        </FormItem>

        <FormItem :wrapper-col="{ offset: 4, span: 18 }">
          <Space>
            <Button type="primary" :loading="loading" @click="onSubmit">
              提交投稿
            </Button>
            <Button @click="onReset">重置</Button>
          </Space>
        </FormItem>
      </Form>
    </Card>
  </AuthorPageShell>
</template>