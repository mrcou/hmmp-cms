<script lang="ts" setup>
import { computed, ref, watch } from 'vue';

import { VbenTiptap } from '@vben/plugins/tiptap';

import {
  Button,
  Card,
  Checkbox,
  Form,
  FormItem,
  Input,
  Select,
  Space,
  TabPane,
  Tabs,
} from 'antdv-next';

import {
  createAuthor,
  type ManuscriptInfoForm,
} from './manuscript-info';

const formState = defineModel<ManuscriptInfoForm>({ required: true });
const activeAuthorId = ref(formState.value.authors[0]?.id ?? '');

const sexOptions = [
  { label: '男', value: '男' },
  { label: '女', value: '女' },
];

const educationOptions = [
  { label: '博士', value: '博士' },
  { label: '硕士', value: '硕士' },
  { label: '本科', value: '本科' },
  { label: '大专', value: '大专' },
  { label: '其他', value: '其他' },
];

const activeAuthor = computed(() =>
  formState.value.authors.find((author) => author.id === activeAuthorId.value),
);

watch(
  () => formState.value.authors,
  (authors) => {
    if (!authors.some((author) => author.id === activeAuthorId.value)) {
      activeAuthorId.value = authors[0]?.id ?? '';
    }
  },
  { deep: true },
);

function setContactAuthor(id: string, checked: boolean) {
  if (!checked) return;
  formState.value.authors.forEach((author) => {
    author.isContactAuthor = author.id === id;
  });
}

function onContactChange(id: string, event: any) {
  setContactAuthor(id, !!event?.target?.checked);
}

function addAuthor() {
  const author = createAuthor();
  formState.value.authors.push(author);
  activeAuthorId.value = author.id;
}

function removeAuthor(id: string) {
  if (formState.value.authors.length <= 1) return;

  const removed = formState.value.authors.find((author) => author.id === id);
  formState.value.authors = formState.value.authors.filter(
    (author) => author.id !== id,
  );
  formState.value.authors.forEach((author, index) => {
    author.isFirstAuthor = index === 0;
  });

  if (removed?.isContactAuthor && formState.value.authors[0]) {
    formState.value.authors[0].isContactAuthor = true;
  }
  activeAuthorId.value = formState.value.authors[0]?.id ?? '';
}

async function validate() {
  for (const [index, author] of formState.value.authors.entries()) {
    const message =
      (!author.realName.trim() && '请填写作者真实名') ||
      (!author.sex && '请选择性别') ||
      (!author.unit.trim() && '请填写单位') ||
      (!author.address.trim() && '请填写办公地址') ||
      (!author.postcode.trim() && '请填写邮编') ||
      (!author.mobile.trim() && '请填写手机号') ||
      (!author.email.trim() && '请填写 E-mail');

    if (message) {
      activeAuthorId.value = author.id;
      throw new Error(`第 ${index + 1} 作者：${message}`);
    }
  }

  if (!formState.value.authors.some((author) => author.isContactAuthor)) {
    throw new Error('请指定一位通讯作者');
  }
}

defineExpose({ validate });
</script>

<template>
  <div class="author-info-step">
    <div class="mb-4 flex flex-wrap items-center justify-end gap-3">
      <Button type="primary" ghost @click="addAuthor">添加作者</Button>
    </div>

    <Card :bordered="false" class="section-card">
      <Tabs v-model:active-key="activeAuthorId" type="card">
        <TabPane
          v-for="(author, index) in formState.authors"
          :key="author.id"
          :tab="`第 ${index + 1} 作者${author.isContactAuthor ? '（通讯）' : ''}`"
        />
      </Tabs>

      <template v-if="activeAuthor">
        <div class="mb-4 flex flex-wrap items-center justify-between gap-3">
          <Space>
            <span class="text-sm font-medium">
              {{ activeAuthor.isFirstAuthor ? '第一作者' : '作者信息' }}
            </span>
            <Checkbox
              :checked="activeAuthor.isContactAuthor"
              @change="(event: any) => onContactChange(activeAuthor!.id, event)"
            >
              通讯作者
            </Checkbox>
          </Space>
          <Button
            v-if="formState.authors.length > 1"
            type="link"
            danger
            @click="removeAuthor(activeAuthor.id)"
          >
            删除当前作者
          </Button>
        </div>

        <Form layout="vertical" :model="activeAuthor">
          <div class="form-grid">
            <FormItem label="作者真实名" required>
              <Input
                v-model:value="activeAuthor.realName"
                placeholder="请输入作者真实名"
              />
            </FormItem>
            <FormItem label="汉语拼音">
              <Input
                v-model:value="activeAuthor.pinYin"
                placeholder="请输入汉语拼音"
              />
            </FormItem>
            <FormItem label="性别" required>
              <Select
                v-model:value="activeAuthor.sex"
                allow-clear
                placeholder="请选择"
                :options="sexOptions"
              />
            </FormItem>

            <FormItem label="出生年月">
              <Input
                v-model:value="activeAuthor.birthday"
                placeholder="如 1990-01"
              />
            </FormItem>
            <FormItem label="单位" class="span-2" required>
              <Input
                v-model:value="activeAuthor.unit"
                placeholder="请输入单位"
              />
            </FormItem>

            <FormItem label="单位英文名" class="span-2">
              <Input
                v-model:value="activeAuthor.enUnit"
                placeholder="请输入单位英文名"
              />
            </FormItem>
            <FormItem label="办公地址" class="span-2" required>
              <Input
                v-model:value="activeAuthor.address"
                placeholder="请输入办公地址"
              />
            </FormItem>
            <FormItem label="邮编" required>
              <Input
                v-model:value="activeAuthor.postcode"
                placeholder="请输入邮编"
              />
            </FormItem>

            <FormItem label="国家">
              <Input
                v-model:value="activeAuthor.country"
                placeholder="请输入国家"
              />
            </FormItem>
            <FormItem label="省份">
              <Input
                v-model:value="activeAuthor.province"
                placeholder="请输入省份"
              />
            </FormItem>
            <FormItem label="专业">
              <Input
                v-model:value="activeAuthor.major"
                placeholder="请输入专业"
              />
            </FormItem>

            <FormItem label="学历">
              <Select
                v-model:value="activeAuthor.education"
                allow-clear
                placeholder="请选择"
                :options="educationOptions"
              />
            </FormItem>
            <FormItem label="职称">
              <Input
                v-model:value="activeAuthor.title"
                placeholder="请输入职称"
              />
            </FormItem>
            <FormItem label="办公室电话">
              <Input
                v-model:value="activeAuthor.tel"
                placeholder="请输入办公室电话"
              />
            </FormItem>

            <FormItem label="手机号" required>
              <Input
                v-model:value="activeAuthor.mobile"
                placeholder="请输入手机号"
              />
            </FormItem>
            <FormItem label="E-mail" class="span-2" required>
              <Input
                v-model:value="activeAuthor.email"
                placeholder="请输入 E-mail"
              />
            </FormItem>

            <FormItem label="个人简历" class="span-full">
              <VbenTiptap
                v-model="activeAuthor.resume"
                placeholder="请输入个人简历"
                :min-height="160"
                :max-height="320"
                :previewable="false"
              />
            </FormItem>
          </div>
        </Form>
      </template>
    </Card>
  </div>
</template>

<style scoped>
.section-card {
  box-shadow: 0 1px 2px hsl(var(--foreground) / 4%);
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  column-gap: 1rem;
  row-gap: 0.25rem;
}

.span-2 {
  grid-column: span 2;
}

.span-full {
  grid-column: 1 / -1;
}

@media (max-width: 1200px) {
  .form-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .span-2 {
    grid-column: span 2;
  }
}

@media (max-width: 768px) {
  .form-grid {
    grid-template-columns: 1fr;
  }

  .span-2,
  .span-full {
    grid-column: 1;
  }
}
</style>
