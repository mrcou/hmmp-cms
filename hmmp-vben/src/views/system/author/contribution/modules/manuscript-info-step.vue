<script lang="ts" setup>
import {
  Button,
  Card,
  Form,
  FormItem,
  Input,
  InputNumber,
  Select,
  TextArea,
  message,
} from 'antdv-next';
import { computed, reactive, ref } from 'vue';

import { VbenTiptap } from '@vben/plugins/tiptap';

import * as authorApi from '#/api/biz/author';

import type { ManuscriptInfoForm } from './manuscript-info';

type AutoFetchField =
  | 'title'
  | 'enTitle'
  | 'keywords'
  | 'enKeywords'
  | 'abstractText';

const props = defineProps<{
  manuscriptPath?: string;
}>();

const formState = defineModel<ManuscriptInfoForm>({ required: true });
const formRef = ref();
const parsing = ref(false);
const fieldLoading = reactive<Record<AutoFetchField, boolean>>({
  title: false,
  enTitle: false,
  keywords: false,
  enKeywords: false,
  abstractText: false,
});

let cachedMeta: authorApi.ManuscriptMeta | null = null;
let cachedPath = '';

const keywordsJoined = computed(() => formState.value.keywords.join(''));
const enKeywordsJoined = computed(() => formState.value.enKeywords.join(''));

function stripHtmlTags(html: string) {
  return html.replace(/<[^>]+>/g, '');
}

function isEmptyRichText(html?: string) {
  if (!html?.trim() || html === '<p></p>') {
    return true;
  }
  return !stripHtmlTags(html).trim();
}

function richTextLength(html?: string) {
  return stripHtmlTags(html || '').trim().length;
}

const rules: Record<string, any> = {
  title: [
    { required: true, message: '标题不能为空', trigger: 'blur' },
    { max: 350, message: '标题最多 350 个字符', trigger: 'blur' },
  ],
  enTitle: [{ max: 350, message: '英文标题最多 350 个字符', trigger: 'blur' }],
  keywords: [
    {
      required: true,
      type: 'array',
      min: 1,
      message: '关键词不能为空',
      trigger: 'change',
    },
    {
      validator: async () => {
        if (keywordsJoined.value.length > 125) {
          throw new Error('中文关键词合计不能超过 125 个汉字');
        }
      },
      trigger: 'change',
    },
  ],
  enKeywords: [
    {
      validator: async () => {
        if (enKeywordsJoined.value.length > 250) {
          throw new Error('英文关键词合计不能超过 250 个英文字母');
        }
      },
      trigger: 'change',
    },
  ],
  // 富文本用单条 validator，避免 required 默认文案「请输入摘要」与自定义文案叠成两行
  abstractText: [
    {
      validator: async (_rule: unknown, value: string) => {
        if (isEmptyRichText(value)) {
          throw new Error('摘要不能为空');
        }
        if (richTextLength(value) > 8000) {
          throw new Error('摘要最多 8000 个字符');
        }
      },
      trigger: ['blur', 'change'],
    },
  ],
  enAbstract: [
    {
      validator: async (_rule: unknown, value: string) => {
        if (!isEmptyRichText(value) && richTextLength(value) > 8000) {
          throw new Error('英文摘要最多 8000 个字符');
        }
      },
      trigger: ['blur', 'change'],
    },
  ],
  fundProject: [
    { max: 1000, message: '基金项目最多 1000 个字符', trigger: 'blur' },
  ],
  enFundProject: [
    { max: 1000, message: '基金项目英文名最多 1000 个字符', trigger: 'blur' },
  ],
  studyClassCode: [
    { max: 150, message: '学科分类号最多 150 个字符', trigger: 'blur' },
  ],
  clcNumber: [
    { max: 250, message: '中图分类号最多 250 个字符', trigger: 'blur' },
  ],
  pageNum: [{ required: true, message: '请填写页数', trigger: 'change' }],
  remark: [
    { required: true, message: '备注不能为空', trigger: 'blur' },
    { max: 1000, message: '备注最多 1000 个字符', trigger: 'blur' },
  ],
};

function normalizeKeywordTags(values: string[]) {
  return values
    .map((item) => item.trim())
    .filter(Boolean)
    .filter((item, index, list) => list.indexOf(item) === index);
}

function onKeywordsChange(values: string[]) {
  formState.value.keywords = normalizeKeywordTags(values);
}

function onEnKeywordsChange(values: string[]) {
  formState.value.enKeywords = normalizeKeywordTags(values);
}

function toPlainParagraphHtml(text?: string) {
  const value = (text || '').trim();
  if (!value) return '';
  return value
    .split(/\n+/)
    .map((line) => `<p>${line}</p>`)
    .join('');
}

async function loadMeta() {
  const filePath = props.manuscriptPath?.trim();
  if (!filePath) {
    message.warning('请先在上一步上传稿件正文');
    return null;
  }

  parsing.value = true;
  try {
    const res = (await authorApi.parseManuscriptMeta(filePath)) as any;
    const meta = (res?.data ?? res) as authorApi.ManuscriptMeta;
    cachedMeta = meta || {};
    cachedPath = filePath;
    return cachedMeta;
  } finally {
    parsing.value = false;
  }
}

function applyField(field: AutoFetchField, meta: authorApi.ManuscriptMeta) {
  if (field === 'title') {
    if (!meta.title) {
      message.warning('未识别到标题，请手工填写');
      return false;
    }
    formState.value.title = meta.title;
    return true;
  }
  if (field === 'enTitle') {
    if (!meta.enTitle?.trim()) {
      message.warning('未识别到英文标题，请手工填写');
      return false;
    }
    formState.value.enTitle = meta.enTitle.trim();
    return true;
  }
  if (field === 'keywords') {
    const list = normalizeKeywordTags(meta.keywords || []);
    if (!list.length) {
      message.warning('未识别到关键词，请手工填写');
      return false;
    }
    formState.value.keywords = list;
    return true;
  }
  if (field === 'enKeywords') {
    const list = normalizeKeywordTags(meta.enKeywords || []);
    if (!list.length) {
      message.warning('未识别到英文关键词，请手工填写');
      return false;
    }
    formState.value.enKeywords = list;
    return true;
  }
  const html = toPlainParagraphHtml(meta.abstractText);
  if (!html) {
    message.warning('未识别到摘要，请手工填写');
    return false;
  }
  formState.value.abstractText = html;
  return true;
}

async function autoFetch(field: AutoFetchField) {
  fieldLoading[field] = true;
  try {
    // 每次点击都重新扫描，支持后期再次获取
    const meta = await loadMeta();
    if (!meta) return;
    if (applyField(field, meta)) {
      message.success('已自动获取');
    }
  } catch (error: any) {
    message.error(error?.message || '自动获取失败');
  } finally {
    fieldLoading[field] = false;
  }
}

async function validate() {
  await formRef.value?.validate();
}

function resetFields() {
  formRef.value?.resetFields?.();
  cachedMeta = null;
  cachedPath = '';
}

defineExpose({ validate, resetFields });
</script>

<template>
  <div class="manuscript-info-step">
    <Form
      ref="formRef"
      layout="vertical"
      :model="formState"
      :rules="rules"
      class="info-form"
    >
      <Card :bordered="false" class="section-card mb-4">
        <template #title>
          <span class="text-sm font-semibold">基本信息</span>
        </template>

        <div class="form-grid">
          <div class="span-full field-block">
            <div class="field-label-row">
              <span class="field-label is-required">标题</span>
              <Button
                type="link"
                size="small"
                class="auto-fetch-btn"
                :loading="fieldLoading.title || parsing"
                @click="autoFetch('title')"
              >
                自动获取
              </Button>
            </div>
            <FormItem name="title">
              <Input
                v-model:value="formState.title"
                placeholder="中文标题最多不能超过 125 个汉字，空格按一个汉字计算"
                :maxlength="350"
                show-count
              />
            </FormItem>
          </div>

          <div class="span-full field-block">
            <div class="field-label-row">
              <span class="field-label">英文标题</span>
              <Button
                type="link"
                size="small"
                class="auto-fetch-btn"
                :loading="fieldLoading.enTitle || parsing"
                @click="autoFetch('enTitle')"
              >
                自动获取
              </Button>
            </div>
            <FormItem name="enTitle">
              <Input
                v-model:value="formState.enTitle"
                placeholder="英文标题最多只能输入 250 个英文字母，空格按一个英文字母计算"
                :maxlength="350"
                show-count
              />
            </FormItem>
          </div>

          <div class="span-full field-block">
            <div class="field-label-row">
              <span class="field-label is-required">关键词</span>
              <Button
                type="link"
                size="small"
                class="auto-fetch-btn"
                :loading="fieldLoading.keywords || parsing"
                @click="autoFetch('keywords')"
              >
                自动获取
              </Button>
            </div>
            <FormItem name="keywords">
              <Select
                :value="formState.keywords"
                mode="tags"
                class="w-full"
                :open="false"
                :token-separators="[]"
                placeholder="输入中文关键词后按回车添加"
                @change="(values: string[]) => onKeywordsChange(values)"
              />
            </FormItem>
          </div>

          <div class="span-full field-block">
            <div class="field-label-row">
              <span class="field-label">英文关键词</span>
              <Button
                type="link"
                size="small"
                class="auto-fetch-btn"
                :loading="fieldLoading.enKeywords || parsing"
                @click="autoFetch('enKeywords')"
              >
                自动获取
              </Button>
            </div>
            <FormItem name="enKeywords">
              <Select
                :value="formState.enKeywords"
                mode="tags"
                class="w-full"
                :open="false"
                :token-separators="[]"
                placeholder="输入英文关键词后按回车添加"
                @change="(values: string[]) => onEnKeywordsChange(values)"
              />
            </FormItem>
          </div>

          <div class="span-full field-block">
            <div class="field-label-row">
              <span class="field-label is-required">摘要</span>
              <Button
                type="link"
                size="small"
                class="auto-fetch-btn"
                :loading="fieldLoading.abstractText || parsing"
                @click="autoFetch('abstractText')"
              >
                自动获取
              </Button>
            </div>
            <FormItem name="abstractText" class="rich-form-item" :validate-first="true">
              <VbenTiptap
                v-model="formState.abstractText"
                placeholder="请输入中文摘要"
                :min-height="180"
                :max-height="360"
                :previewable="false"
              />
            </FormItem>
          </div>

          <FormItem
            label="英文摘要"
            name="enAbstract"
            class="span-full rich-form-item"
            :validate-first="true"
          >
            <VbenTiptap
              v-model="formState.enAbstract"
              placeholder="请输入英文摘要"
              :min-height="180"
              :max-height="360"
              :previewable="false"
            />
          </FormItem>

          <FormItem label="基金项目" name="fundProject">
            <Input
              v-model:value="formState.fundProject"
              placeholder="可直接输入基金项目，非基金资助类论文可为空"
              :maxlength="1000"
            />
          </FormItem>

          <FormItem label="基金项目英文名" name="enFundProject">
            <Input
              v-model:value="formState.enFundProject"
              placeholder="可直接输入基金项目英文名，非基金资助类论文可为空"
              :maxlength="1000"
            />
          </FormItem>

          <FormItem label="学科分类号" name="studyClassCode">
            <Input
              v-model:value="formState.studyClassCode"
              placeholder="请输入学科分类号"
              :maxlength="150"
            />
          </FormItem>

          <FormItem label="中图分类号" name="clcNumber">
            <Input
              v-model:value="formState.clcNumber"
              placeholder="请输入中图分类号"
              :maxlength="250"
            />
          </FormItem>

          <FormItem label="页数" name="pageNum" class="page-num-item">
            <InputNumber
              v-model:value="formState.pageNum"
              class="page-num-input"
              :min="1"
              :max="9999"
              placeholder="请输入页数"
            />
          </FormItem>

          <FormItem label="备注" name="remark" class="span-full remark-item">
            <TextArea
              v-model:value="formState.remark"
              class="remark-textarea"
              placeholder="请输入备注信息"
              :rows="4"
              :maxlength="1000"
              show-count
              allow-clear
            />
          </FormItem>
        </div>
      </Card>
    </Form>
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

.field-block {
  margin-bottom: 24px;
}

.field-block :deep(.ant-form-item) {
  margin-bottom: 0;
}

.field-label-row {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  gap: 0.5rem;
  margin-bottom: 8px;
}

.field-label {
  color: hsl(var(--foreground));
  font-size: 14px;
  line-height: 1.5;
}

.field-label.is-required::before {
  display: inline-block;
  margin-inline-end: 4px;
  color: #ff4d4f;
  font-size: 14px;
  font-family: SimSun, sans-serif;
  line-height: 1;
  content: '*';
}

.auto-fetch-btn {
  height: auto;
  padding: 0;
  font-size: 12px;
}

.remark-item :deep(.ant-form-item-control-input-content) {
  width: 100%;
}

.remark-textarea {
  width: 100%;
}

.remark-item :deep(textarea) {
  min-height: 96px;
  resize: vertical;
}

.page-num-item :deep(.ant-form-item-control-input-content) {
  width: 100%;
}

.page-num-input,
.page-num-item :deep(.ant-input-number) {
  width: 100%;
}

.info-form :deep(.ant-form-item-explain) {
  white-space: nowrap;
}

.info-form :deep(.ant-form-item-explain-error) {
  white-space: nowrap;
}

/* 富文本偶发叠两条错误时，只保留一行 */
.rich-form-item :deep(.ant-form-item-explain-error + .ant-form-item-explain-error) {
  display: none;
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
