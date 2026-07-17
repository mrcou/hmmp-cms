<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { VbenTableAction } from '@vben/common-ui';
import { VbenTiptap } from '@vben/plugins/tiptap';
import { Upload, message } from 'antdv-next';

import * as publisherApi from '#/api/biz/publisher';

interface AttachmentItem {
  cnName: string;
  enName: string;
  path: string;
}

interface ArticleFormData extends PublisherApi.Article {
  abstractText?: string;
  adoptTime?: string;
  allIssueSameMoney?: string;
  attachments: AttachmentItem[];
  authorResume?: string;
  bookPublishedTime?: string;
  clcNumber?: string;
  cnEthicalStatement?: string;
  cstr?: string;
  enEthicalStatement?: string;
  imagePath?: string;
  isHeadlines?: string;
  perArticleMoney?: number;
  requestMoney?: string;
  specialMark?: string;
  studyClassCode?: string;
}

const createEmptyForm = (): ArticleFormData => ({
  title: '',
  titleEn: '',
  subtitle: '',
  fileNo: '',
  fundZh: '',
  fundEn: '',
  clcNumber: '',
  studyClassCode: '',
  keywords: '',
  keywordsEn: '',
  abstractText: '',
  abstractEn: '',
  cnEthicalStatement: '',
  enEthicalStatement: '',
  issueId: undefined,
  columnId: undefined,
  yearNo: new Date().getFullYear(),
  pageStart: undefined,
  pageEnd: undefined,
  submitTime: undefined,
  lastModifyTime: undefined,
  adoptTime: undefined,
  publishTime: undefined,
  bookPublishedTime: undefined,
  specialMark: '',
  doi: '',
  cstr: '',
  imagePath: '',
  requestMoney: '0',
  isHeadlines: '0',
  perArticleMoney: undefined,
  allIssueSameMoney: '0',
  articleUrl: '',
  citationText: '',
  authorNames: '',
  authorNamesEn: '',
  authorOrgZh: '',
  authorOrgEn: '',
  authorResume: '',
  referenceText: '',
  attachments: Array.from({ length: 5 }, () => ({ cnName: '', enName: '', path: '' })),
  status: '0',
});

const loading = ref(false);
const submitting = ref(false);
const drawerOpen = ref(false);
const drawerTitle = ref('新增文章');
const isEdit = ref(false);
const dataSource = ref<PublisherApi.Article[]>([]);
const issueList = ref<PublisherApi.Issue[]>([]);
const columnList = ref<PublisherApi.Column[]>([]);
const formData = reactive<ArticleFormData>(createEmptyForm());
const referenceEditorHtml = ref('');
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ title: '', status: undefined as string | undefined });

const statusOptions = [
  { value: '0', label: '启用' },
  { value: '1', label: '停用' },
];
const yearOptions = computed(() => {
  const years = new Set(issueList.value.map((item) => Number(item.year)).filter(Boolean));
  years.add(new Date().getFullYear());
  return [...years].sort((a, b) => b - a).map((year) => ({ value: year, label: `${year}年` }));
});
const issueOptions = computed(() =>
  issueList.value
    .filter((item) => !formData.yearNo || Number(item.year) === Number(formData.yearNo))
    .map((item) => ({
      value: item.issueId!,
      label: item.issueName || item.nameCn || `第${item.period}期`,
    })),
);
const columnOptions = computed(() =>
  columnList.value.map((item) => ({
    value: item.columnId!,
    label: item.columnName,
  })),
);

const columns = [
  { title: '文章ID', dataIndex: 'articleId', key: 'articleId', width: 100 },
  { title: '标题', dataIndex: 'title', key: 'title', width: 240, ellipsis: true },
  { title: '副标题', dataIndex: 'subtitle', key: 'subtitle', width: 180, ellipsis: true },
  { title: '作者', dataIndex: 'authorNames', key: 'authorNames', width: 120 },
  { title: '刊期ID', dataIndex: 'issueId', key: 'issueId', width: 80 },
  { title: '栏目ID', dataIndex: 'columnId', key: 'columnId', width: 80 },
  { title: '起始页', dataIndex: 'pageStart', key: 'pageStart', width: 80 },
  { title: '字数', dataIndex: 'wordCount', key: 'wordCount', width: 80 },
  { title: '浏览量', dataIndex: 'viewCount', key: 'viewCount', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', dataIndex: 'action', width: 140, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = (await publisherApi.getArticleList({
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
      ...searchForm,
    })) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? res?.data ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
  } finally {
    loading.value = false;
  }
}

async function loadOptions() {
  const [issues, columnsResult] = await Promise.all([
    publisherApi.getIssueList({ pageNum: 1, pageSize: 500 }),
    publisherApi.getColumnList({ pageNum: 1, pageSize: 500 }),
  ]);
  const issueResult = issues as any;
  const columnResult = columnsResult as any;
  issueList.value = issueResult?.rows ?? issueResult?.data?.rows ?? [];
  columnList.value = columnResult?.rows ?? columnResult?.data?.rows ?? [];
}

function escapeHtml(text: string) {
  return text
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

function stripHtmlTags(html: string) {
  return html.replace(/<[^>]+>/g, '');
}

function isEmptyRichText(html?: string) {
  if (!html?.trim() || html === '<p></p>') {
    return true;
  }
  return !stripHtmlTags(html).trim();
}

/** 纯文本或旧数据 -> TipTap HTML */
function textToTiptapHtml(value?: string) {
  if (!value?.trim()) {
    return '';
  }
  if (/<[a-z][\s\S]*>/i.test(value)) {
    return value;
  }
  return value
    .split(/\n+/)
    .map((line) => line.trim())
    .filter(Boolean)
    .map((line) => `<p>${escapeHtml(line)}</p>`)
    .join('');
}

/** 存储格式（<br> 分隔） -> TipTap HTML（每行一个 <p>） */
function referencesToHtml(value?: string) {
  if (!value?.trim()) {
    return '';
  }
  if (/<p[\s>]/i.test(value) && !/<br\s*\/?>/i.test(value)) {
    return value;
  }
  const lines = value
    .replace(/<\/p>\s*<p[^>]*>/gi, '\n')
    .replace(/<br\s*\/?>/gi, '\n')
    .replace(/<\/?p[^>]*>/gi, '\n')
    .split(/\n/)
    .map((line) => stripHtmlTags(line).trim())
    .filter(Boolean);
  if (!lines.length) {
    return '';
  }
  return lines.map((line) => `<p>${escapeHtml(line)}</p>`).join('');
}

/** TipTap HTML -> 存储格式（<br> 分隔，每条一行） */
function referencesFromHtml(html?: string) {
  if (!html?.trim() || html === '<p></p>') {
    return '';
  }
  const lines: string[] = [];
  const paragraphRegex = /<p[^>]*>([\s\S]*?)<\/p>/gi;
  let match = paragraphRegex.exec(html);
  while (match) {
    const line = match[1]
      .replace(/<br\s*\/?>/gi, ' ')
      .replace(/<[^>]+>/g, '')
      .replace(/\s+/g, ' ')
      .trim();
    if (line) {
      lines.push(line);
    }
    match = paragraphRegex.exec(html);
  }
  if (!lines.length) {
    return html
      .replace(/<br\s*\/?>/gi, '\n')
      .replace(/<[^>]+>/g, '')
      .split(/\n/)
      .map((line) => line.trim())
      .filter(Boolean)
      .join('<br>');
  }
  return lines.join('<br>');
}

function normalizeDateField(value?: string) {
  if (!value) {
    return undefined;
  }
  return value.includes(' ') ? value.split(' ')[0] : value;
}

function resetForm() {
  Object.assign(formData, createEmptyForm());
  referenceEditorHtml.value = '';
}

function handleSearch() {
  pagination.current = 1;
  fetchList();
}

function handleReset() {
  searchForm.title = '';
  searchForm.status = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  drawerTitle.value = '新增文章';
  resetForm();
  drawerOpen.value = true;
}

async function handleEdit(record: PublisherApi.Article) {
  isEdit.value = true;
  drawerTitle.value = '编辑文章';
  resetForm();
  let detail = record;
  if (record.articleId) {
    const res = (await publisherApi.getArticle(record.articleId)) as any;
    detail = res?.data ?? res ?? record;
  }
  Object.assign(formData, detail, {
    abstractText: textToTiptapHtml(
      (detail as any).abstractText ?? (detail as any).abstract ?? '',
    ),
    abstractEn: textToTiptapHtml((detail as any).abstractEn ?? ''),
    submitTime: normalizeDateField(detail.submitTime),
    lastModifyTime: normalizeDateField(detail.lastModifyTime),
    adoptTime: normalizeDateField((detail as any).adoptTime),
    publishTime: normalizeDateField(detail.publishTime),
    bookPublishedTime: normalizeDateField((detail as any).bookPublishedTime),
    attachments: Array.from({ length: 5 }, () => ({ cnName: '', enName: '', path: '' })),
  });
  referenceEditorHtml.value = referencesToHtml(detail.referenceText);
  drawerOpen.value = true;
}

async function handleDelete(record: PublisherApi.Article) {
  await publisherApi.deleteArticle([record.articleId!]);
  message.success('删除成功');
  fetchList();
}

function buildPayload(): PublisherApi.Article | null {
  const title = formData.title.trim();
  if (!title) {
    message.warning('请输入标题');
    return null;
  }
  if (!formData.keywords?.trim()) {
    message.warning('请输入关键词');
    return null;
  }
  if (isEmptyRichText(formData.abstractText)) {
    message.warning('请输入摘要');
    return null;
  }
  return {
    articleId: formData.articleId,
    manuscriptId: formData.manuscriptId,
    issueId: formData.issueId,
    columnId: formData.columnId,
    journalCode: formData.journalCode,
    fileNo: formData.fileNo?.trim(),
    title,
    titleEn: formData.titleEn?.trim(),
    subtitle: formData.subtitle?.trim(),
    authorNames: formData.authorNames?.trim(),
    authorNamesEn: formData.authorNamesEn?.trim(),
    authorOrgZh: formData.authorOrgZh?.trim(),
    authorOrgEn: formData.authorOrgEn?.trim(),
    doi: formData.doi?.trim(),
    abstractText: formData.abstractText.trim(),
    abstractEn: formData.abstractEn?.trim(),
    keywords: formData.keywords.trim(),
    keywordsEn: formData.keywordsEn?.trim(),
    fundZh: formData.fundZh?.trim(),
    fundEn: formData.fundEn?.trim(),
    referenceText: referencesFromHtml(referenceEditorHtml.value),
    citationText: formData.citationText,
    pageStart: formData.pageStart,
    pageEnd: formData.pageEnd,
    submitTime: formData.submitTime || undefined,
    lastModifyTime: formData.lastModifyTime || undefined,
    yearNo: formData.yearNo,
    period: issueList.value.find((item) => item.issueId === formData.issueId)?.period
      ? Number(issueList.value.find((item) => item.issueId === formData.issueId)?.period)
      : undefined,
    status: formData.status,
    publishTime: formData.publishTime || undefined,
    articleUrl: formData.articleUrl?.trim(),
  } as PublisherApi.Article;
}

async function handleSubmit() {
  const payload = buildPayload();
  if (!payload) return;
  submitting.value = true;
  try {
    if (isEdit.value) {
      await publisherApi.updateArticle(payload);
      message.success('编辑成功');
    } else {
      await publisherApi.createArticle(payload);
      message.success('新增成功');
    }
    drawerOpen.value = false;
    fetchList();
  } finally {
    submitting.value = false;
  }
}

function uploadFile(target: 'image' | number) {
  return async (file: File) => {
    try {
      const res = (await publisherApi.uploadPublisherFile(file)) as any;
      const path = res?.fileName ?? res?.data?.fileName ?? res?.url ?? res?.data?.url ?? '';
      if (!path) {
        message.warning('上传成功，但未返回文件路径');
        return Upload.LIST_IGNORE;
      }
      if (target === 'image') formData.imagePath = path;
      else formData.attachments[target]!.path = path;
      message.success('上传成功');
    } catch {
      message.error('上传失败');
    }
    return Upload.LIST_IGNORE;
  };
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchList();
}

onMounted(() => {
  fetchList();
  loadOptions();
});
</script>

<template>
  <div class="p-4">
    <div class="mb-4 flex items-center justify-between">
      <a-button type="primary" @click="handleAdd">新增文章</a-button>
      <a-form layout="inline">
        <a-form-item label="文章标题">
          <a-input v-model:value="searchForm.title" placeholder="请输入文章标题" style="width: 200px" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear style="width: 120px" :options="statusOptions" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="articleId"
      :scroll="{ x: 1200 }"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <a-tag :color="record.status === '0' ? 'green' : 'red'">{{ record.status === '0' ? '启用' : '停用' }}</a-tag>
        </template>
        <template v-if="column.key === 'action'">
          <VbenTableAction
            :actions="[
              { text: '修改', icon: 'lucide:edit', onClick: () => handleEdit(record) },
              {
                text: '删除',
                icon: 'lucide:trash-2',
                danger: true,
                popConfirm: {
                  title: '确认删除该文章吗？',
                  confirm: () => handleDelete(record),
                },
              },
            ]"
          />
        </template>
      </template>
    </a-table>

    <a-drawer v-model:open="drawerOpen" :title="drawerTitle" placement="right" :width="1080" destroy-on-close class="article-drawer">
      <a-form layout="vertical" class="article-form">
        <a-form-item label="稿件编号">
          <a-input v-model:value="formData.fileNo" placeholder="请输入稿件编号" />
        </a-form-item>
        <a-form-item label="标题" required>
          <a-input v-model:value="formData.title" placeholder="请输入标题" />
        </a-form-item>
        <a-form-item label="英文标题">
          <a-input v-model:value="formData.titleEn" placeholder="请输入英文标题" />
        </a-form-item>
        <a-form-item label="副标题">
          <a-input v-model:value="formData.subtitle" />
        </a-form-item>
        <a-form-item label="图片">
          <div class="file-picker">
            <a-input v-model:value="formData.imagePath" readonly placeholder="请选择图片" />
            <a-upload :before-upload="uploadFile('image')" :show-upload-list="false" accept="image/*">
              <a-button>浏览...</a-button>
            </a-upload>
            <a-button v-if="formData.imagePath" danger @click="formData.imagePath = ''">删除</a-button>
          </div>
        </a-form-item>
        <div class="form-grid form-grid-3">
          <a-form-item label="基金项目">
            <a-input v-model:value="formData.fundZh" />
          </a-form-item>
          <a-form-item label="基金项目英文名">
            <a-input v-model:value="formData.fundEn" />
          </a-form-item>
          <a-form-item label="中图分类号">
            <a-input v-model:value="formData.clcNumber" />
          </a-form-item>
          <a-form-item label="学科分类号">
            <a-input v-model:value="formData.studyClassCode" />
          </a-form-item>
          <a-form-item label="关键词" required>
            <a-input v-model:value="formData.keywords" />
          </a-form-item>
          <a-form-item label="英文关键词">
            <a-input v-model:value="formData.keywordsEn" />
          </a-form-item>
        </div>
        <a-form-item label="摘要" required class="rich-form-item">
          <VbenTiptap
            v-model="formData.abstractText"
            :min-height="160"
            :max-height="320"
            :previewable="false"
            placeholder="请输入摘要"
          />
        </a-form-item>
        <a-form-item label="英文摘要" class="rich-form-item">
          <VbenTiptap
            v-model="formData.abstractEn"
            :min-height="160"
            :max-height="320"
            :previewable="false"
            placeholder="请输入英文摘要"
          />
        </a-form-item>
        <div class="form-grid">
          <a-form-item label="中文伦理声明">
            <a-textarea v-model:value="formData.cnEthicalStatement" :rows="3" />
          </a-form-item>
          <a-form-item label="英文伦理声明">
            <a-textarea v-model:value="formData.enEthicalStatement" :rows="3" />
          </a-form-item>
        </div>

        <a-divider orientation="left">出版信息</a-divider>
        <div class="form-grid form-grid-3">
          <a-form-item label="年份">
            <a-select v-model:value="formData.yearNo" allow-clear :options="yearOptions" />
          </a-form-item>
          <a-form-item label="刊期">
            <a-select v-model:value="formData.issueId" allow-clear placeholder="请选择刊期" :options="issueOptions" />
          </a-form-item>
          <a-form-item label="栏目名称">
            <a-select v-model:value="formData.columnId" allow-clear show-search option-filter-prop="label" placeholder="请选择栏目" :options="columnOptions" />
          </a-form-item>
          <a-form-item label="起始页码">
            <a-input-number v-model:value="formData.pageStart" :min="0" style="width: 100%" />
          </a-form-item>
          <a-form-item label="截止页码">
            <a-input-number v-model:value="formData.pageEnd" :min="0" style="width: 100%" />
          </a-form-item>
          <a-form-item label="投稿时间">
            <a-date-picker
              v-model:value="formData.submitTime"
              value-format="YYYY-MM-DD"
              style="width: 100%"
              allow-clear
              placeholder="请选择投稿时间"
            />
          </a-form-item>
          <a-form-item label="最后修改时间">
            <a-date-picker
              v-model:value="formData.lastModifyTime"
              value-format="YYYY-MM-DD"
              style="width: 100%"
              allow-clear
              placeholder="请选择最后修改时间"
            />
          </a-form-item>
          <a-form-item label="录用日期">
            <a-date-picker
              v-model:value="formData.adoptTime"
              value-format="YYYY-MM-DD"
              style="width: 100%"
              allow-clear
              placeholder="请选择录用日期"
            />
          </a-form-item>
          <a-form-item label="网刊发布日期">
            <a-date-picker
              v-model:value="formData.publishTime"
              value-format="YYYY-MM-DD"
              style="width: 100%"
              allow-clear
              placeholder="请选择网刊发布日期"
            />
          </a-form-item>
          <a-form-item label="书版出版日期">
            <a-date-picker
              v-model:value="formData.bookPublishedTime"
              value-format="YYYY-MM-DD"
              style="width: 100%"
              allow-clear
              placeholder="请选择书版出版日期"
            />
          </a-form-item>
          <a-form-item label="特殊标记">
            <a-input v-model:value="formData.specialMark" />
          </a-form-item>
          <a-form-item label="状态">
            <a-select v-model:value="formData.status" :options="statusOptions" />
          </a-form-item>
          <a-form-item label="DOI">
            <a-input v-model:value="formData.doi" />
          </a-form-item>
          <a-form-item label="CSTR">
            <a-input v-model:value="formData.cstr" />
          </a-form-item>
        </div>

        <a-divider orientation="left">发布与收费</a-divider>
        <div class="form-grid form-grid-3">
          <a-form-item label="免费下载？">
            <a-switch v-model:checked="formData.requestMoney" checked-value="1" un-checked-value="0" />
          </a-form-item>
          <a-form-item label="推送为头条" extra="一期中最有代表性的文章且只能是最新一期">
            <a-switch v-model:checked="formData.isHeadlines" checked-value="1" un-checked-value="0" />
          </a-form-item>
          <a-form-item label="本期文章收费金额全部相同">
            <a-switch v-model:checked="formData.allIssueSameMoney" checked-value="1" un-checked-value="0" />
          </a-form-item>
          <a-form-item label="收费金额">
            <a-input-number v-model:value="formData.perArticleMoney" :min="0" :precision="2" style="width: 100%" />
          </a-form-item>
          <a-form-item label="网址" class="span-2">
            <a-input v-model:value="formData.articleUrl" />
          </a-form-item>
        </div>

        <a-divider orientation="left">作者与引用</a-divider>
        <div class="form-grid">
          <a-form-item label="文章引用">
            <a-input v-model:value="formData.citationText" />
          </a-form-item>
          <a-form-item label="原始作者中文名">
            <a-input v-model:value="formData.authorNames" />
          </a-form-item>
          <a-form-item label="原始作者中英文名">
            <a-input v-model:value="formData.authorNamesEn" />
          </a-form-item>
          <a-form-item label="原始单位中文名">
            <a-input v-model:value="formData.authorOrgZh" />
          </a-form-item>
          <a-form-item label="原始单位英文名">
            <a-input v-model:value="formData.authorOrgEn" />
          </a-form-item>
          <a-form-item label="作者简介">
            <a-textarea v-model:value="formData.authorResume" :rows="3" />
          </a-form-item>
          <a-form-item label="参考文献" class="span-2" extra="请确保一行只显示一个参考文献">
            <VbenTiptap
              v-model="referenceEditorHtml"
              :min-height="200"
              :max-height="360"
              :previewable="false"
              placeholder="每行一条参考文献，回车新增下一条"
            />
          </a-form-item>
        </div>

        <a-divider orientation="left">添加附件</a-divider>
        <div v-for="(attachment, index) in formData.attachments" :key="index" class="attachment-row">
          <a-input v-model:value="attachment.cnName" placeholder="附件中文名" />
          <a-input v-model:value="attachment.enName" placeholder="附件英文名" />
          <a-input v-model:value="attachment.path" readonly placeholder="附件文件名" />
          <a-upload :before-upload="uploadFile(index)" :show-upload-list="false">
            <a-button>浏览...</a-button>
          </a-upload>
          <a-button v-if="attachment.path" danger @click="attachment.path = ''">删除</a-button>
        </div>
      </a-form>

      <template #footer>
        <div class="drawer-footer">
          <a-button @click="drawerOpen = false">取消</a-button>
          <a-button type="primary" :loading="submitting" @click="handleSubmit">保存</a-button>
        </div>
      </template>
    </a-drawer>
  </div>
</template>

<style scoped>
.article-form {
  --ant-form-label-height: 24px;
  --ant-form-item-margin-bottom: 10px;
  padding-bottom: 1rem;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  column-gap: 1rem;
}

.form-grid-3 {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.span-2 {
  grid-column: span 2;
}

.file-picker {
  display: flex;
  gap: 0.5rem;
}

.file-picker :deep(.ant-input) {
  min-width: 0;
}

.attachment-row {
  display: grid;
  grid-template-columns: 1fr 1fr 1.4fr auto auto;
  gap: 0.5rem;
  margin-bottom: 0.75rem;
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

.rich-form-item :deep(.ant-form-item-control-input-content) {
  min-width: 0;
}

@media (max-width: 900px) {
  .form-grid,
  .form-grid-3,
  .attachment-row {
    grid-template-columns: 1fr;
  }

  .span-2 {
    grid-column: span 1;
  }
}
</style>