<script lang="ts" setup>
/**
 * 责编工作量统计 —— 按责任编辑汇总各流程状态稿件数
 * 布局：兄弟页导航 + 筛选行 + 数据表（含合计行）
 */
import type { StatisticsApi } from '#/api/biz/statistics';

import { computed, onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { message } from 'antdv-next';

import { exportEditorWorkload, getEditorWorkload } from '#/api/biz/statistics';
import {
  JOURNAL_NAME_LABEL,
  useJournalMagazine,
} from '#/composables/use-journal-magazine';

defineOptions({ name: 'StatisticsIntegrativeEditWork' });

const route = useRoute();
const router = useRouter();

const siblingPages = [
  { title: '期刊统计', path: '/statistics/integrative/journal' },
  { title: '审理周期统计', path: '/statistics/integrative/audit-cycle' },
  { title: '出版周期统计', path: '/statistics/integrative/publish-cycle' },
  { title: '责编处理周期统计', path: '/statistics/integrative/edit-process-cycle' },
  { title: '加工周期', path: '/statistics/integrative/edit-article-cycle' },
  { title: '责编工作量统计', path: '/statistics/integrative/edit-work' },
  { title: '责编加工工作量', path: '/statistics/integrative/edit-production' },
  { title: '审稿统计', path: '/statistics/integrative/reviewer-audit' },
  { title: '被引文章查询', path: '/statistics/integrative/referenced-article' },
  { title: '审稿周期统计', path: '/statistics/integrative/review-cycle' },
  { title: '用户统计', path: '/statistics/integrative/user' },
];

function onNav(path: string) {
  if (path !== route.path) router.push(path);
}

const currentYear = String(new Date().getFullYear());

const { magazineOptions } = useJournalMagazine({ optionValue: 'default' });

const dateFieldOptions = [
  { value: 'receiveTime', label: '投稿日期' },
  { value: 'assignTime', label: '分配日期' },
  { value: 'reviewStartTime', label: '送审日期' },
  { value: 'conclusionTime', label: '结论日期' },
  { value: 'publishTime', label: '发表日期' },
];

const filters = reactive({
  magazineId: 'default',
  dateField: 'receiveTime',
  beginTime: currentYear,
  endTime: currentYear,
});

const loading = ref(false);
const exporting = ref(false);
const rows = ref<StatisticsApi.EditWorkRow[]>([]);

const DATE_HINT = '可以输入年、年-月、年-月-日三种格式！';

function validateDateInput(value: string, label: string): boolean {
  const v = value.trim();
  if (!v) {
    message.warning(`请填写${label}`);
    return false;
  }
  if (!/^(\d{4}|\d{4}-\d{1,2}|\d{4}-\d{1,2}-\d{1,2})$/.test(v)) {
    message.warning(`${label}格式不正确（${DATE_HINT}）`);
    return false;
  }
  return true;
}

function num(v?: number | null) {
  return v ?? 0;
}

function buildParams(): StatisticsApi.EditWorkParams {
  return {
    beginTime: filters.beginTime.trim(),
    endTime: filters.endTime.trim(),
    dateField: filters.dateField,
    magazineId: filters.magazineId,
  };
}

async function loadData() {
  if (!validateDateInput(filters.beginTime, '起始时间')) return;
  if (!validateDateInput(filters.endTime, '截止时间')) return;

  loading.value = true;
  try {
    const res = (await getEditorWorkload(buildParams())) as {
      data?: StatisticsApi.EditWorkRow[];
    };
    rows.value = Array.isArray(res?.data) ? res.data : [];
  } catch {
    message.error('加载责编工作量统计失败');
    rows.value = [];
  } finally {
    loading.value = false;
  }
}

function onSubmit() {
  loadData();
}

async function onExport() {
  if (!validateDateInput(filters.beginTime, '起始时间')) return;
  if (!validateDateInput(filters.endTime, '截止时间')) return;
  exporting.value = true;
  try {
    await exportEditorWorkload(buildParams());
  } catch {
    message.error('导出失败');
  } finally {
    exporting.value = false;
  }
}

const summaryRow = computed(() => {
  const list = rows.value;
  return {
    editorName: '合计',
    deptName: '',
    totalCount: list.reduce((s, r) => s + num(r.totalCount), 0),
    notSentCount: list.reduce((s, r) => s + num(r.notSentCount), 0),
    reviseCount: list.reduce((s, r) => s + num(r.reviseCount), 0),
    resubmittedCount: list.reduce((s, r) => s + num(r.resubmittedCount), 0),
    reviewingCount: list.reduce((s, r) => s + num(r.reviewingCount), 0),
    returnedCount: list.reduce((s, r) => s + num(r.returnedCount), 0),
    notReturnedCount: list.reduce((s, r) => s + num(r.notReturnedCount), 0),
    adoptCount: list.reduce((s, r) => s + num(r.adoptCount), 0),
    rejectCount: list.reduce((s, r) => s + num(r.rejectCount), 0),
    publishCount: list.reduce((s, r) => s + num(r.publishCount), 0),
  };
});

const tableData = computed(() => {
  if (rows.value.length === 0) return [];
  return [...rows.value, { ...summaryRow.value, __summary: true }];
});

const columns = [
  { title: '责任编辑姓名', dataIndex: 'editorName', key: 'editorName', width: 140 },
  { title: '责任部门', dataIndex: 'deptName', key: 'deptName', width: 140 },
  { title: '总件', dataIndex: 'totalCount', key: 'totalCount', width: 72, align: 'right' as const },
  { title: '未送审', dataIndex: 'notSentCount', key: 'notSentCount', width: 80, align: 'right' as const },
  { title: '退修', dataIndex: 'reviseCount', key: 'reviseCount', width: 72, align: 'right' as const },
  { title: '已修回', dataIndex: 'resubmittedCount', key: 'resubmittedCount', width: 80, align: 'right' as const },
  { title: '审稿中', dataIndex: 'reviewingCount', key: 'reviewingCount', width: 80, align: 'right' as const },
  { title: '已审回', dataIndex: 'returnedCount', key: 'returnedCount', width: 80, align: 'right' as const },
  { title: '未审回', dataIndex: 'notReturnedCount', key: 'notReturnedCount', width: 80, align: 'right' as const },
  { title: '采用', dataIndex: 'adoptCount', key: 'adoptCount', width: 72, align: 'right' as const },
  { title: '退稿', dataIndex: 'rejectCount', key: 'rejectCount', width: 72, align: 'right' as const },
  { title: '发表', dataIndex: 'publishCount', key: 'publishCount', width: 72, align: 'right' as const },
];

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <div class="mb-3 flex flex-wrap items-center gap-x-4 gap-y-2">
      <a-button type="primary" class="pointer-events-none shrink-0">
        责编工作量统计
      </a-button>
      <nav class="flex min-w-0 flex-1 flex-wrap items-center gap-x-3 gap-y-1">
        <a
          v-for="page in siblingPages"
          :key="page.path"
          href="javascript:;"
          class="nav-link"
          :class="{ active: page.path === route.path }"
          @click.prevent="onNav(page.path)"
        >
          {{ page.title }}
        </a>
      </nav>
    </div>

    <a-card :bordered="false" class="shadow-sm">
      <div class="filter-bar mb-4 flex flex-wrap items-center gap-x-3 gap-y-2 border-b pb-4">
        <div class="flex items-center gap-2">
          <span class="filter-label">{{ JOURNAL_NAME_LABEL }}</span>
          <a-select
            v-model:value="filters.magazineId"
            style="width: 160px"
            :options="magazineOptions"
          />
        </div>
        <div class="flex items-center gap-2">
          <span class="filter-label">字段名称</span>
          <a-select
            v-model:value="filters.dateField"
            style="width: 140px"
            :options="dateFieldOptions"
          />
        </div>
        <div class="flex items-center gap-2">
          <span class="filter-label">起始时间</span>
          <a-input
            v-model:value="filters.beginTime"
            allow-clear
            placeholder="年/月/日"
            style="width: 120px"
            @press-enter="onSubmit"
          />
        </div>
        <div class="flex items-center gap-2">
          <span class="filter-label">截止时间</span>
          <a-input
            v-model:value="filters.endTime"
            allow-clear
            placeholder="年/月/日"
            style="width: 120px"
            @press-enter="onSubmit"
          />
        </div>
        <a-button type="primary" :loading="loading" @click="onSubmit">
          检索
        </a-button>
        <a-button :loading="exporting" @click="onExport">
          导出
        </a-button>
        <span class="text-muted-foreground text-xs">({{ DATE_HINT }})</span>
      </div>

      <a-table
        :columns="columns"
        :data-source="tableData"
        :loading="loading"
        :pagination="false"
        :row-class-name="(record: any) => (record.__summary ? 'summary-row' : '')"
        :row-key="(record: any, index: number) =>
          record.__summary ? 'summary' : `${record.editorId ?? 'none'}-${index}`"
        size="middle"
        bordered
      >
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.dataIndex === 'editorName'">
            <span :class="{ 'font-semibold': record.__summary }">{{ text || '—' }}</span>
          </template>
          <template v-else-if="column.dataIndex === 'deptName'">
            {{ text || (record.__summary ? '' : '—') }}
          </template>
          <template v-else>
            <span class="tabular-nums" :class="{ 'font-semibold': record.__summary }">
              {{ num(text) }}
            </span>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<style scoped>
.nav-link {
  color: hsl(var(--foreground) / 75%);
  font-size: 0.8125rem;
  line-height: 1.75;
  text-decoration: none;
  transition: color 0.15s ease;
}

.nav-link:hover {
  color: hsl(var(--primary));
}

.nav-link.active {
  color: hsl(var(--primary));
  font-weight: 600;
}

.filter-label {
  color: hsl(var(--foreground) / 80%);
  font-size: 0.8125rem;
  white-space: nowrap;
}

:deep(.summary-row) td {
  background: hsl(var(--muted) / 45%) !important;
}
</style>
