<script lang="ts" setup>
import type { PublisherNewsApi } from '#/api/biz/publisher-news';

import { computed, onMounted, reactive, ref } from 'vue';

import { Page, VbenTableAction } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';
import { VbenTiptap, VbenTiptapPreview } from '@vben/plugins/tiptap';

import { Upload, message } from 'antdv-next';

import * as newsApi from '#/api/biz/publisher-news';

defineOptions({ name: 'PublisherNewsContent' });

type MediaType = 'attachments' | 'images' | 'videos';
type NewsContentForm = PublisherNewsApi.Content;

const createEmptyForm = (): NewsContentForm => ({
  title: '',
  content: '',
  summary: '',
  status: 'draft',
  sortOrder: 0,
  source: '',
  author: '',
  editor: '',
  sourceUrl: '',
  publishTime: undefined,
});

const loading = ref(false);
const submitting = ref(false);
const savingSort = ref(false);
const drawerOpen = ref(false);
const previewOpen = ref(false);
const isEdit = ref(false);
const dataSource = ref<PublisherNewsApi.Content[]>([]);
const columnList = ref<PublisherNewsApi.Column[]>([]);
const previewData = ref<PublisherNewsApi.Content>();
const formData = reactive<NewsContentForm>(createEmptyForm());
const mediaLists = reactive<Record<MediaType, string[]>>({
  attachments: [],
  images: [],
  videos: [],
});
const searchForm = reactive({
  title: '',
  columnId: undefined as number | undefined,
  status: undefined as 'draft' | 'published' | undefined,
});
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条记录`,
});

const statusOptions = [
  { label: '草稿', value: 'draft' },
  { label: '已发布', value: 'published' },
];
const columnOptions = computed(() =>
  columnList.value.map((item) => ({
    label: item.columnName,
    value: item.columnId!,
  })),
);
const columns = [
  { title: 'ID', dataIndex: 'contentId', key: 'contentId', width: 80 },
  { title: '排序', dataIndex: 'sortOrder', key: 'sortOrder', width: 105 },
  {
    title: '标题',
    dataIndex: 'title',
    key: 'title',
    ellipsis: true,
    width: 260,
  },
  {
    title: '栏目',
    dataIndex: 'columnName',
    key: 'columnName',
    width: 140,
  },
  { title: '发布状态', dataIndex: 'status', key: 'status', width: 110 },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
    width: 180,
  },
  {
    title: '发布时间',
    dataIndex: 'publishTime',
    key: 'publishTime',
    width: 180,
  },
  {
    title: '操作',
    dataIndex: 'action',
    key: 'action',
    fixed: 'right' as const,
    width: 210,
  },
];

function parseMedia(value?: string) {
  return value
    ? value
        .split(',')
        .map((item) => item.trim())
        .filter(Boolean)
    : [];
}

function resetForm() {
  Object.assign(formData, createEmptyForm());
  mediaLists.images.splice(0);
  mediaLists.videos.splice(0);
  mediaLists.attachments.splice(0);
}

async function loadColumns() {
  const res = (await newsApi.getNewsColumnList({
    pageNum: 1,
    pageSize: 500,
  })) as any;
  columnList.value = res?.rows ?? res?.data?.rows ?? [];
}

async function fetchList() {
  loading.value = true;
  try {
    const res = (await newsApi.getNewsContentList({
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
      title: searchForm.title.trim() || undefined,
      columnId: searchForm.columnId,
      status: searchForm.status,
    })) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
  } finally {
    loading.value = false;
  }
}

function handleSearch() {
  pagination.current = 1;
  fetchList();
}

function handleReset() {
  searchForm.title = '';
  searchForm.columnId = undefined;
  searchForm.status = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  if (!columnList.value.length) {
    message.warning('请先在“栏目管理”中添加栏目');
    return;
  }
  isEdit.value = false;
  resetForm();
  formData.columnId = columnList.value[0]?.columnId;
  drawerOpen.value = true;
}

async function handleEdit(record: PublisherNewsApi.Content) {
  isEdit.value = true;
  resetForm();
  let detail = record;
  if (record.contentId) {
    const res = (await newsApi.getNewsContent(record.contentId)) as any;
    detail = res?.data ?? res ?? record;
  }
  Object.assign(formData, detail);
  mediaLists.images.push(...parseMedia(detail.images));
  mediaLists.videos.push(...parseMedia(detail.videos));
  mediaLists.attachments.push(...parseMedia(detail.attachments));
  drawerOpen.value = true;
}

function handlePreview(record: PublisherNewsApi.Content) {
  previewData.value = { ...record };
  previewOpen.value = true;
}

function stripHtml(html: string) {
  return html.replace(/<[^>]+>/g, '').replace(/&nbsp;/g, ' ').trim();
}

async function handleSubmit() {
  if (!formData.columnId) {
    message.warning('请选择栏目');
    return;
  }
  if (!formData.title.trim()) {
    message.warning('请输入标题');
    return;
  }
  if (!stripHtml(formData.content || '')) {
    message.warning('请输入内容');
    return;
  }
  submitting.value = true;
  try {
    const payload: PublisherNewsApi.Content = {
      ...formData,
      title: formData.title.trim(),
      summary: formData.summary?.trim() || '',
      source: formData.source?.trim() || '',
      author: formData.author?.trim() || '',
      editor: formData.editor?.trim() || '',
      sourceUrl: formData.sourceUrl?.trim() || '',
      publishTime:
        formData.status === 'published' ? formData.publishTime : undefined,
      sortOrder: Number(formData.sortOrder || 0),
      images: mediaLists.images.join(','),
      videos: mediaLists.videos.join(','),
      attachments: mediaLists.attachments.join(','),
    };
    if (isEdit.value) {
      await newsApi.updateNewsContent(payload);
      message.success('修改成功');
    } else {
      await newsApi.createNewsContent(payload);
      message.success('添加成功');
    }
    drawerOpen.value = false;
    fetchList();
  } finally {
    submitting.value = false;
  }
}

async function handleDelete(record: PublisherNewsApi.Content) {
  await newsApi.deleteNewsContent([record.contentId!]);
  message.success('删除成功');
  if (dataSource.value.length === 1 && pagination.current > 1) {
    pagination.current -= 1;
  }
  fetchList();
}

async function saveSort() {
  savingSort.value = true;
  try {
    await newsApi.updateNewsContentSort(
      dataSource.value.map((item) => ({
        contentId: item.contentId,
        sortOrder: Number(item.sortOrder || 0),
      })),
    );
    message.success('排序已保存');
    fetchList();
  } finally {
    savingSort.value = false;
  }
}

function uploadMedia(type: MediaType) {
  return async (file: File) => {
    try {
      const res = (await newsApi.uploadNewsFile(file)) as any;
      const path =
        res?.fileName ??
        res?.data?.fileName ??
        res?.url ??
        res?.data?.url ??
        '';
      if (!path) {
        message.warning('上传成功，但未返回文件路径');
        return Upload.LIST_IGNORE;
      }
      mediaLists[type].push(path);
      message.success('上传成功');
    } catch {
      message.error('上传失败');
    }
    return Upload.LIST_IGNORE;
  };
}

function removeMedia(type: MediaType, index: number) {
  mediaLists[type].splice(index, 1);
}

function fileName(path: string) {
  const parts = path.split('/');
  return decodeURIComponent(parts[parts.length - 1] || path);
}

function handleTableChange(page: { current: number; pageSize: number }) {
  pagination.current = page.current;
  pagination.pageSize = page.pageSize;
  fetchList();
}

onMounted(() => {
  Promise.all([loadColumns(), fetchList()]);
});
</script>

<template>
  <Page auto-content-height>
    <div class="mb-4 flex flex-wrap items-center justify-between gap-3">
      <a-space>
        <a-button type="primary" @click="handleAdd">
          <IconifyIcon icon="lucide:plus" class="mr-1 size-4" />
          添加内容
        </a-button>
        <a-button :loading="savingSort" @click="saveSort">
          <IconifyIcon icon="lucide:arrow-up-down" class="mr-1 size-4" />
          保存排序
        </a-button>
      </a-space>
      <a-form layout="inline">
        <a-form-item label="栏目">
          <a-select
            v-model:value="searchForm.columnId"
            allow-clear
            :options="columnOptions"
            placeholder="全部栏目"
            style="width: 150px"
          />
        </a-form-item>
        <a-form-item label="标题">
          <a-input
            v-model:value="searchForm.title"
            allow-clear
            placeholder="搜索标题"
            style="width: 200px"
            @press-enter="handleSearch"
          />
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            v-model:value="searchForm.status"
            allow-clear
            :options="statusOptions"
            placeholder="全部状态"
            style="width: 120px"
          />
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
      :loading="loading"
      :pagination="pagination"
      :scroll="{ x: 1280 }"
      row-key="contentId"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'sortOrder'">
          <a-input-number
            v-model:value="record.sortOrder"
            :min="0"
            :max="999999"
            size="small"
            style="width: 78px"
          />
        </template>
        <template v-else-if="column.key === 'status'">
          <a-tag :color="record.status === 'published' ? 'green' : 'default'">
            {{ record.status === 'published' ? '已发布' : '草稿' }}
          </a-tag>
        </template>
        <template v-else-if="column.key === 'action'">
          <VbenTableAction
            :actions="[
              {
                text: '预览',
                icon: 'lucide:eye',
                onClick: () => handlePreview(record),
              },
              {
                text: '编辑',
                icon: 'lucide:edit',
                onClick: () => handleEdit(record),
              },
              {
                text: '删除',
                icon: 'lucide:trash-2',
                danger: true,
                popConfirm: {
                  title: `确认删除内容“${record.title}”吗？`,
                  confirm: () => handleDelete(record),
                },
              },
            ]"
          />
        </template>
      </template>
    </a-table>

    <a-drawer
      v-model:open="drawerOpen"
      :title="isEdit ? '编辑内容' : '添加内容'"
      placement="right"
      :width="1000"
      destroy-on-close
    >
      <a-form layout="vertical">
        <div class="form-grid form-grid-4">
          <a-form-item label="栏目" required>
            <a-select
              v-model:value="formData.columnId"
              :options="columnOptions"
              placeholder="请选择栏目"
            />
          </a-form-item>
          <a-form-item label="状态">
            <a-select
              v-model:value="formData.status"
              :options="statusOptions"
            />
          </a-form-item>
          <a-form-item label="发布时间">
            <a-date-picker
              v-model:value="formData.publishTime"
              :disabled="formData.status !== 'published'"
              show-time
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="发布时可留空自动生成"
              style="width: 100%"
            />
          </a-form-item>
          <a-form-item label="排序">
            <a-input-number
              v-model:value="formData.sortOrder"
              :min="0"
              :max="999999"
              style="width: 100%"
            />
          </a-form-item>
        </div>

        <a-form-item label="标题" required>
          <a-input
            v-model:value="formData.title"
            :maxlength="200"
            show-count
            placeholder="请输入新闻标题"
          />
        </a-form-item>
        <a-form-item label="摘要">
          <a-textarea
            v-model:value="formData.summary"
            :maxlength="1000"
            :rows="3"
            show-count
            placeholder="请输入摘要"
          />
        </a-form-item>

        <div class="form-grid form-grid-3">
          <a-form-item label="来源">
            <a-input v-model:value="formData.source" placeholder="请输入来源" />
          </a-form-item>
          <a-form-item label="作者">
            <a-input v-model:value="formData.author" placeholder="请输入作者" />
          </a-form-item>
          <a-form-item label="编辑">
            <a-input v-model:value="formData.editor" placeholder="请输入编辑" />
          </a-form-item>
        </div>
        <a-form-item label="来源URL">
          <a-input
            v-model:value="formData.sourceUrl"
            placeholder="https://..."
          />
        </a-form-item>

        <a-form-item label="内容" required class="rich-form-item">
          <VbenTiptap
            v-model="formData.content"
            :min-height="320"
            :max-height="560"
            placeholder="请输入新闻内容"
          />
        </a-form-item>

        <a-divider orientation="left">图片</a-divider>
        <div class="media-list">
          <div
            v-for="(item, index) in mediaLists.images"
            :key="`${item}-${index}`"
            class="media-item"
          >
            <a-input :value="item" readonly />
            <a-button danger @click="removeMedia('images', index)">
              删除
            </a-button>
          </div>
          <a-upload
            accept="image/*"
            :before-upload="uploadMedia('images')"
            :show-upload-list="false"
          >
            <a-button>
              <IconifyIcon icon="lucide:upload" class="mr-1 size-4" />
              上传图片
            </a-button>
          </a-upload>
        </div>

        <a-divider orientation="left">视频</a-divider>
        <div class="media-list">
          <div
            v-for="(item, index) in mediaLists.videos"
            :key="`${item}-${index}`"
            class="media-item"
          >
            <a-input :value="item" readonly />
            <a-button danger @click="removeMedia('videos', index)">
              删除
            </a-button>
          </div>
          <a-upload
            accept="video/*"
            :before-upload="uploadMedia('videos')"
            :show-upload-list="false"
          >
            <a-button>
              <IconifyIcon icon="lucide:upload" class="mr-1 size-4" />
              上传视频
            </a-button>
          </a-upload>
        </div>

        <a-divider orientation="left">附件</a-divider>
        <div class="media-list">
          <div
            v-for="(item, index) in mediaLists.attachments"
            :key="`${item}-${index}`"
            class="media-item"
          >
            <a :href="item" target="_blank">{{ fileName(item) }}</a>
            <a-button danger @click="removeMedia('attachments', index)">
              删除
            </a-button>
          </div>
          <a-upload
            :before-upload="uploadMedia('attachments')"
            :show-upload-list="false"
          >
            <a-button>
              <IconifyIcon icon="lucide:paperclip" class="mr-1 size-4" />
              上传附件
            </a-button>
          </a-upload>
        </div>
      </a-form>

      <template #footer>
        <div class="flex justify-end gap-2">
          <a-button @click="drawerOpen = false">取消</a-button>
          <a-button
            type="primary"
            :loading="submitting"
            @click="handleSubmit"
          >
            保存
          </a-button>
        </div>
      </template>
    </a-drawer>

    <a-modal
      v-model:open="previewOpen"
      :footer="null"
      title="内容预览"
      width="900px"
    >
      <article v-if="previewData" class="news-preview">
        <h1>{{ previewData.title }}</h1>
        <div class="news-preview__meta">
          <span>{{ previewData.columnName || '未分类' }}</span>
          <span>{{ previewData.publishTime || previewData.createTime }}</span>
          <span v-if="previewData.source">来源：{{ previewData.source }}</span>
          <span v-if="previewData.author">作者：{{ previewData.author }}</span>
        </div>
        <p v-if="previewData.summary" class="news-preview__summary">
          {{ previewData.summary }}
        </p>
        <VbenTiptapPreview
          :content="previewData.content || '<p>暂无内容</p>'"
          :min-height="220"
        />
        <div v-if="parseMedia(previewData.attachments).length" class="mt-5">
          <strong>附件：</strong>
          <a-space direction="vertical" class="mt-2 w-full">
            <a
              v-for="item in parseMedia(previewData.attachments)"
              :key="item"
              :href="item"
              target="_blank"
            >
              {{ fileName(item) }}
            </a>
          </a-space>
        </div>
      </article>
    </a-modal>
  </Page>
</template>

<style scoped>
.form-grid {
  display: grid;
  gap: 0 16px;
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.form-grid-3 {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.form-grid-4 {
  grid-template-columns: repeat(4, minmax(0, 1fr));
}

.media-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 18px;
}

.media-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  min-height: 32px;
}

.media-item :deep(.ant-input) {
  flex: 1;
}

.news-preview {
  max-height: 70vh;
  overflow: auto;
  padding: 4px 12px 12px;
}

.news-preview h1 {
  margin-bottom: 12px;
  color: hsl(var(--foreground));
  font-size: 24px;
  font-weight: 600;
  line-height: 1.4;
  text-align: center;
}

.news-preview__meta {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 8px 20px;
  margin-bottom: 18px;
  color: hsl(var(--muted-foreground));
  font-size: 13px;
}

.news-preview__summary {
  margin-bottom: 18px;
  padding: 12px 16px;
  border-left: 3px solid hsl(var(--primary));
  background: hsl(var(--muted));
  color: hsl(var(--muted-foreground));
  line-height: 1.7;
}

@media (max-width: 900px) {
  .form-grid,
  .form-grid-3,
  .form-grid-4 {
    grid-template-columns: 1fr;
  }
}
</style>
