<script lang="ts" setup>
/**
 * 列表展开：稿件概览 + 快捷操作
 * 结构对齐旧系统：稿件信息 / 作者信息 / 附件信息 + 底部操作条
 */
import type { EditorApi } from '#/api/biz/editor';

import { computed } from 'vue';

import { message } from 'antdv-next';
import { IconifyIcon } from '@vben/icons';

defineOptions({ name: 'ReviewerManuscriptExpandPanel' });

const props = defineProps<{
  record: EditorApi.Manuscript;
}>();

const emit = defineEmits<{
  detail: [row: EditorApi.Manuscript];
  revise: [row: EditorApi.Manuscript];
  reject: [row: EditorApi.Manuscript];
  review: [row: EditorApi.Manuscript];
  download: [row: EditorApi.Manuscript];
  editFile: [row: EditorApi.Manuscript];
  upload: [row: EditorApi.Manuscript];
  editInfo: [row: EditorApi.Manuscript];
}>();

interface AuthorItem {
  name: string;
  sort: number;
  isCorresponding: boolean;
}

interface AttachmentItem {
  key: string;
  name: string;
  path?: string;
  time?: string;
  ext: 'pdf' | 'img' | 'doc' | 'file';
}

const keywordTags = computed(() => {
  const raw = props.record.keywords?.trim();
  if (!raw) return [] as string[];
  return raw
    .split(/[,，;；、\s]+/)
    .map((s) => s.trim())
    .filter(Boolean);
});

const authors = computed((): AuthorItem[] => {
  const names = (props.record.authorNames || '')
    .split(/[,，;；、]+/)
    .map((s) => s.trim())
    .filter(Boolean);
  const corresponding = props.record.correspondingAuthor?.trim();
  if (!names.length && corresponding) {
    return [{ name: corresponding, sort: 1, isCorresponding: true }];
  }
  return names.map((name, idx) => ({
    name,
    sort: idx + 1,
    isCorresponding: !!corresponding && name === corresponding,
  }));
});

const attachments = computed((): AttachmentItem[] => {
  const time = props.record.updateTime || props.record.receiveTime || props.record.createTime;
  const main = props.record.filePath;
  const list: AttachmentItem[] = [];

  if (main) {
    list.push({
      key: 'manuscript',
      name: '正文稿件',
      path: main,
      time,
      ext: guessExt(main),
    });
  }

  // 扩展附件位：后端就绪后按真实字段替换；暂以占位展示结构
  const stubs: AttachmentItem[] = [
    { key: 'copyright', name: '版权转让协议', ext: 'img', time },
    { key: 'letter', name: '单位介绍信', ext: 'img', time },
    { key: 'cnki', name: '查重报告', ext: 'pdf', time },
  ];

  for (const stub of stubs) {
    if (!list.some((a) => a.key === stub.key)) {
      list.push(stub);
    }
  }
  return list;
});

const quickActions = [
  { key: 'revise', label: '直接退修', icon: 'lucide:file-output' },
  { key: 'reject', label: '直接退稿', icon: 'lucide:circle-minus' },
  { key: 'review', label: '稿件送审', icon: 'lucide:send' },
  { key: 'download', label: '下载稿件', icon: 'lucide:download' },
  { key: 'editFile', label: '修改稿件', icon: 'lucide:pencil' },
  { key: 'upload', label: '上传稿件', icon: 'lucide:upload' },
  { key: 'editInfo', label: '修改稿件信息', icon: 'lucide:file-pen' },
] as const;

function guessExt(path: string): AttachmentItem['ext'] {
  const lower = path.toLowerCase();
  if (/\.(png|jpe?g|gif|webp|bmp)$/.test(lower)) return 'img';
  if (/\.pdf$/.test(lower)) return 'pdf';
  if (/\.(docx?|wps)$/.test(lower)) return 'doc';
  return 'file';
}

function fileIcon(ext: AttachmentItem['ext']) {
  if (ext === 'pdf') return 'lucide:file-text';
  if (ext === 'img') return 'lucide:image';
  if (ext === 'doc') return 'lucide:file-type';
  return 'lucide:paperclip';
}

function formatDateTime(val?: string) {
  if (!val) return '';
  return String(val).replace('T', ' ').slice(0, 19);
}

function roleLabel(author: AuthorItem) {
  const base = `第${author.sort}作者`;
  return author.isCorresponding ? `${base}(通信)` : base;
}

function onSearchAuthor(author: AuthorItem) {
  message.info(`检索作者「${author.name}」（待对接作者库）`);
}

function onPreview(file: AttachmentItem) {
  if (!file.path) {
    message.warning(`暂无「${file.name}」可预览`);
    return;
  }
  window.open(file.path, '_blank');
}

function onDownloadFile(file: AttachmentItem) {
  if (!file.path) {
    message.warning(`暂无「${file.name}」可下载`);
    return;
  }
  window.open(file.path, '_blank');
}

function onModifyFile(file: AttachmentItem) {
  message.info(`修改「${file.name}」待对接上传接口`);
}

function onDeleteFile(file: AttachmentItem) {
  message.info(`删除「${file.name}」待对接接口`);
}

function onQuick(key: (typeof quickActions)[number]['key']) {
  emit(key, props.record);
}
</script>

<template>
  <div class="expand-panel">
    <!-- 稿件信息 -->
    <section class="expand-section">
      <div class="expand-section__title">
        <IconifyIcon icon="lucide:file-text" class="size-4" />
        <span>稿件信息</span>
      </div>
      <div class="expand-section__body">
        <div class="mb-2">
          <span class="text-muted-foreground">摘要：</span>
          <span class="leading-relaxed">
            {{ record.abstractText || '暂无摘要' }}
          </span>
        </div>
        <div class="flex flex-wrap items-start gap-2">
          <span class="text-muted-foreground shrink-0">关键词：</span>
          <div v-if="keywordTags.length" class="flex flex-wrap gap-1.5">
            <a-tag
              v-for="tag in keywordTags"
              :key="tag"
              color="processing"
              class="!m-0 cursor-default"
            >
              {{ tag }}
            </a-tag>
          </div>
          <span v-else>-</span>
        </div>
      </div>
    </section>

    <!-- 作者信息 -->
    <section class="expand-section">
      <div class="expand-section__title">
        <IconifyIcon icon="lucide:users" class="size-4" />
        <span>作者信息</span>
      </div>
      <div class="expand-section__body">
        <div v-if="authors.length" class="flex flex-wrap gap-6">
          <div
            v-for="author in authors"
            :key="`${author.sort}-${author.name}`"
            class="flex items-start gap-2"
          >
            <div
              class="bg-muted text-muted-foreground flex size-9 shrink-0 items-center justify-center rounded-full"
            >
              <IconifyIcon icon="lucide:user" class="size-4" />
            </div>
            <div class="min-w-0">
              <div class="flex items-center gap-1.5">
                <span class="font-medium">{{ author.name }}</span>
                <button
                  type="button"
                  class="text-primary inline-flex p-0.5"
                  title="检索作者"
                  @click="onSearchAuthor(author)"
                >
                  <IconifyIcon icon="lucide:search" class="size-3.5" />
                </button>
              </div>
              <div class="text-muted-foreground text-xs">
                {{ roleLabel(author) }}
              </div>
            </div>
          </div>
        </div>
        <div v-else class="text-muted-foreground">暂无作者信息</div>
      </div>
    </section>

    <!-- 附件信息 -->
    <section class="expand-section">
      <div class="expand-section__title">
        <IconifyIcon icon="lucide:paperclip" class="size-4" />
        <span>附件信息</span>
      </div>
      <div class="expand-section__body">
        <div class="flex flex-wrap gap-x-8 gap-y-4">
          <div
            v-for="file in attachments"
            :key="file.key"
            class="min-w-[200px] max-w-xs"
          >
            <div class="flex items-start gap-2">
              <IconifyIcon
                :icon="fileIcon(file.ext)"
                class="text-primary mt-0.5 size-5 shrink-0"
              />
              <div class="min-w-0">
                <div class="truncate text-sm" :title="file.name">
                  {{ file.name }}
                  <span v-if="file.time" class="text-muted-foreground">
                    ({{ formatDateTime(file.time) }})
                  </span>
                </div>
                <div class="mt-1 flex flex-wrap gap-x-3 gap-y-0.5 text-xs">
                  <a class="attach-link" href="javascript:;" @click.prevent="onPreview(file)">
                    预览
                  </a>
                  <a
                    class="attach-link"
                    href="javascript:;"
                    @click.prevent="onDownloadFile(file)"
                  >
                    下载
                  </a>
                  <a
                    class="attach-link"
                    href="javascript:;"
                    @click.prevent="onModifyFile(file)"
                  >
                    修改
                  </a>
                  <a
                    class="attach-link"
                    href="javascript:;"
                    @click.prevent="onDeleteFile(file)"
                  >
                    删除
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 快捷操作 -->
    <div class="expand-actions">
      <template v-for="act in quickActions" :key="act.key">
        <a-popconfirm
          v-if="act.key === 'reject'"
          title="确认退稿该稿件？"
          @confirm="onQuick('reject')"
        >
          <a-button size="small">
            <template #icon>
              <IconifyIcon :icon="act.icon" class="size-3.5" />
            </template>
            {{ act.label }}
          </a-button>
        </a-popconfirm>
        <a-popconfirm
          v-else-if="act.key === 'revise'"
          title="确认直接退修该稿件？"
          @confirm="onQuick('revise')"
        >
          <a-button size="small">
            <template #icon>
              <IconifyIcon :icon="act.icon" class="size-3.5" />
            </template>
            {{ act.label }}
          </a-button>
        </a-popconfirm>
        <a-button v-else size="small" @click="onQuick(act.key)">
          <template #icon>
            <IconifyIcon :icon="act.icon" class="size-3.5" />
          </template>
          {{ act.label }}
        </a-button>
      </template>
    </div>
  </div>
</template>

<style scoped>
.expand-panel {
  margin: 0.25rem 0.5rem 0.5rem;
  border: 1px solid hsl(199 89% 70% / 55%);
  border-radius: 0.5rem;
  background: hsl(var(--card));
  overflow: hidden;
}

.expand-section {
  padding: 0.75rem 1rem;
  border-bottom: 1px solid hsl(var(--border) / 70%);
}

.expand-section__title {
  display: inline-flex;
  align-items: center;
  gap: 0.375rem;
  margin-bottom: 0.5rem;
  font-size: 0.875rem;
  font-weight: 600;
  color: hsl(var(--foreground));
}

.expand-section__body {
  font-size: 0.8125rem;
  line-height: 1.65;
  color: hsl(var(--foreground) / 90%);
}

.attach-link {
  color: hsl(var(--primary));
  text-decoration: none;
}

.attach-link:hover {
  text-decoration: underline;
}

.expand-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  padding: 0.75rem 1rem;
  background: hsl(199 89% 97% / 55%);
}
</style>
