<script lang="ts" setup>
/**
 * 联系编审 —— 编辑部联系方式 + 留言表单
 */
import { reactive, ref } from 'vue';

import { Button, Form, FormItem, Input, TextArea, message } from 'antdv-next';

import AuthorPageShell from '../_components/author-page-shell.vue';

defineOptions({ name: 'AuthorContact' });

const formRef = ref();
const loading = ref(false);

const formState = reactive({
  subject: '',
  content: '',
});

const rules: Record<string, any> = {
  subject: [{ required: true, message: '请输入主题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入留言内容', trigger: 'blur' }],
};

const officeInfo = [
  { label: '编辑部邮箱', value: 'editor@example.com' },
  { label: '值班电话', value: '010-0000-0000' },
  { label: '工作时间', value: '周一至周五 9:00–17:00' },
];

async function onSubmit() {
  try {
    await formRef.value?.validate();
  } catch {
    return;
  }
  loading.value = true;
  try {
    // TODO: 对接作者留言 / 站内信接口后在此提交
    await new Promise((r) => setTimeout(r, 300));
    message.success('已提交，编辑部将尽快回复');
    formState.subject = '';
    formState.content = '';
    formRef.value?.resetFields?.();
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <AuthorPageShell
    title="联系编审"
    description="如需咨询稿件进度或审稿意见，可通过下方方式联系编辑部。"
  >
    <div class="space-y-4">
      <section class="contact-card">
        <h3 class="mb-3 mt-0 text-sm font-semibold">编辑部联系方式</h3>
        <dl class="m-0 space-y-2">
          <div
            v-for="item in officeInfo"
            :key="item.label"
            class="flex gap-3 text-sm"
          >
            <dt class="text-muted-foreground w-24 shrink-0">{{ item.label }}</dt>
            <dd class="m-0 font-medium">{{ item.value }}</dd>
          </div>
        </dl>
      </section>

      <section class="contact-card">
        <h3 class="mb-3 mt-0 text-sm font-semibold">在线留言</h3>
        <Form
          ref="formRef"
          :model="formState"
          :rules="rules"
          layout="vertical"
          @finish="onSubmit"
        >
          <FormItem label="主题" name="subject">
            <Input
              v-model:value="formState.subject"
              placeholder="例如：关于稿件 XXX 的审稿进度咨询"
              maxlength="80"
              show-count
            />
          </FormItem>
          <FormItem label="留言内容" name="content">
            <TextArea
              v-model:value="formState.content"
              placeholder="请简要说明问题，并附上稿件编号（如有）"
              :rows="5"
              maxlength="1000"
              show-count
            />
          </FormItem>
          <FormItem>
            <Button type="primary" html-type="submit" :loading="loading">
              提交留言
            </Button>
          </FormItem>
        </Form>
      </section>
    </div>
  </AuthorPageShell>
</template>

<style scoped>
.contact-card {
  border-radius: 0.75rem;
  background: hsl(var(--card));
  padding: 1rem 1.125rem;
  box-shadow: 0 1px 2px hsl(var(--foreground) / 4%);
}
</style>
