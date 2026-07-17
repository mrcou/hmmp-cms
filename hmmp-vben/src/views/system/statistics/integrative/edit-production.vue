<script lang="ts" setup>
/**
 * 责编加工工作量 —— 按责任编辑汇总编辑加工 / 排版 / 校对 / 定版确认的未完成与已完成量
 * 支持按篇数 / 按字数切换；嵌套表头；时间筛选（完成日期 / 选件日期 / 创建日期）
 */
import type { StatisticsApi } from '#/api/biz/statistics';

import { computed, onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { message } from 'antdv-next';

import { exportEditProduction, getEditProduction } from '#/api/biz/statistics';

defineOptions({ name: 'StatisticsIntegrativeEditProduction' });

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

const dateFieldOptions = [
  { value: 'editEndTime', label: '完成日期' },
  { value: 'editStartTime', label: '选件日期' },
  { value: 'createTime', label: '创建日期' },
];

/** article=按篇数 | word=按字数 */
type MetricMode = 'article' | 'word';

const filters = reactive({
  dateField: 'editEndTime',
  beginTime: currentYear,
  endTime: currentYear,
});

const metricMode = ref<MetricMode>('word');
const loading = ref(false);
const exporting = ref(false);
const rows = ref<StatisticsApi.EditProductionRow[]>([]);

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

function buildParams(): StatisticsApi.EditProductionParams {
  return {
    beginTime: filters.beginTime.trim(),
    endTime: filters.endTime.trim(),
    dateField: filters.dateField,
  };
}

async function loadData() {
  if (!validateDateInput(filters.beginTime, '起始日期')) return;
  if (!validateDateInput(filters.endTime, '截止日期')) return;

  loading.value = true;
  try {
    const res = (await getEditProduction(buildParams())) as {
      data?: StatisticsApi.EditProductionRow[];
    };
    rows.value = Array.isArray(res?.data) ? res.data : [];
  } catch {
    message.error('加载责编加工工作量失败');
    rows.value = [];
  } finally {
    loading.value = false;
  }
}

function onSubmit() {
  loadData();
}

async function onExport() {
  if (!validateDateInput(filters.beginTime, '起始日期')) return;
  if (!validateDateInput(filters.endTime, '截止日期')) return;
  exporting.value = true;
  try {
    await exportEditProduction(buildParams());
  } catch {
    message.error('导出失败');
  } finally {
    exporting.value = false;
  }
}

const metricKeys = [
  'editUnfinished',
  'editFinished',
  'layoutUnfinished',
  'layoutFinished',
  'proofUnfinished',
  'proofFinished',
  'finalizeUnfinished',
  'finalizeFinished',
] as const;

type MetricKey = (typeof metricKeys)[number];

function cellValue(row: StatisticsApi.EditProductionRow, key: MetricKey): number {
  const byArticle: Record<MetricKey, number> = {
    editUnfinished: num(row.editUnfinishedCount),
    editFinished: num(row.editFinishedCount),
    layoutUnfinished: num(row.layoutUnfinishedCount),
    layoutFinished: num(row.layoutFinishedCount),
    proofUnfinished: num(row.proofUnfinishedCount),
    proofFinished: num(row.proofFinishedCount),
    finalizeUnfinished: num(row.finalizeUnfinishedCount),
    finalizeFinished: num(row.finalizeFinishedCount),
  };
  const byWord: Record<MetricKey, number> = {
    editUnfinished: num(row.editUnfinishedWords),
    editFinished: num(row.editFinishedWords),
    layoutUnfinished: num(row.layoutUnfinishedWords),
    layoutFinished: num(row.layoutFinishedWords),
    proofUnfinished: num(row.proofUnfinishedWords),
    proofFinished: num(row.proofFinishedWords),
    finalizeUnfinished: num(row.finalizeUnfinishedWords),
    finalizeFinished: num(row.finalizeFinishedWords),
  };
  return metricMode.value === 'word' ? byWord[key] : byArticle[key];
}

const summaryRow = computed(() => {
  const list = rows.value;
  const base: StatisticsApi.EditProductionRow = {
    editorName: '合计',
    editUnfinishedCount: list.reduce((s, r) => s + num(r.editUnfinishedCount), 0),
    editFinishedCount: list.reduce((s, r) => s + num(r.editFinishedCount), 0),
    editUnfinishedWords: list.reduce((s, r) => s + num(r.editUnfinishedWords), 0),
    editFinishedWords: list.reduce((s, r) => s + num(r.editFinishedWords), 0),
    layoutUnfinishedCount: list.reduce((s, r) => s + num(r.layoutUnfinishedCount), 0),
    layoutFinishedCount: list.reduce((s, r) => s + num(r.layoutFinishedCount), 0),
    layoutUnfinishedWords: list.reduce((s, r) => s + num(r.layoutUnfinishedWords), 0),
    layoutFinishedWords: list.reduce((s, r) => s + num(r.layoutFinishedWords), 0),
    proofUnfinishedCount: list.reduce((s, r) => s + num(r.proofUnfinishedCount), 0),
    proofFinishedCount: list.reduce((s, r) => s + num(r.proofFinishedCount), 0),
    proofUnfinishedWords: list.reduce((s, r) => s + num(r.proofUnfinishedWords), 0),
    proofFinishedWords: list.reduce((s, r) => s + num(r.proofFinishedWords), 0),
    finalizeUnfinishedCount: list.reduce((s, r) => s + num(r.finalizeUnfinishedCount), 0),
    finalizeFinishedCount: list.reduce((s, r) => s + num(r.finalizeFinishedCount), 0),
    finalizeUnfinishedWords: list.reduce((s, r) => s + num(r.finalizeUnfinishedWords), 0),
    finalizeFinishedWords: list.reduce((s, r) => s + num(r.finalizeFinishedWords), 0),
  };
  return base;
});

const tableData = computed(() => {
  if (rows.value.length === 0) return [];
  return [...rows.value, { ...summaryRow.value, __summary: true }];
});

function stageColumns(stageTitle: string, unfinishedKey: MetricKey, finishedKey: MetricKey) {
  return {
    title: stageTitle,
    align: 'center' as const,
    children: [
      {
        title: '未完成',
        dataIndex: unfinishedKey,
        key: unfinishedKey,
        width: 100,
        align: 'center' as const,
      },
      {
        title: '已完成',
        dataIndex: finishedKey,
        key: finishedKey,
        width: 100,
        align: 'center' as const,
      },
    ],
  };
}

const columns = computed(() => [
  {
    title: '责任编辑',
    dataIndex: 'editorName',
    key: 'editorName',
    width: 140,
    fixed: 'left' as const,
  },
  stageColumns('编辑加工', 'editUnfinished', 'editFinished'),
  stageColumns('排版', 'layoutUnfinished', 'layoutFinished'),
  stageColumns('校对', 'proofUnfinished', 'proofFinished'),
  stageColumns('定版确认', 'finalizeUnfinished', 'finalizeFinished'),
]);

const metricKeysSet = new Set<string>(metricKeys);

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <div class="mb-3 flex flex-wrap items-center gap-x-4 gap-y-2">
      <a-button type="primary" class="pointer-events-none shrink-0">
        责编加工工作量
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
          <span class="filter-label">统计字段</span>
          <a-select
            v-model:value="filters.dateField"
            style="width: 140px"
            :options="dateFieldOptions"
          />
        </div>
        <div class="flex items-center gap-2">
          <span class="filter-label">起始日期</span>
          <a-input
            v-model:value="filters.beginTime"
            allow-clear
            placeholder="年/月/日"
            style="width: 120px"
            @press-enter="onSubmit"
          />
        </div>
        <div class="flex items-center gap-2">
          <span class="filter-label">截止日期</span>
          <a-input
            v-model:value="filters.endTime"
            allow-clear
            placeholder="年/月/日"
            style="width: 120px"
            @press-enter="onSubmit"
          />
        </div>
        <a-button-group>
          <a-button
            :type="metricMode === 'article' ? 'primary' : 'default'"
            @click="metricMode = 'article'"
          >
            按篇数统计
          </a-button>
          <a-button
            :type="metricMode === 'word' ? 'primary' : 'default'"
            @click="metricMode = 'word'"
          >
            按字数统计
          </a-button>
        </a-button-group>
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
        :scroll="{ x: 980 }"
      >
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.dataIndex === 'editorName'">
            <span :class="{ 'font-semibold': record.__summary }">{{ text || '—' }}</span>
          </template>
          <template v-else-if="metricKeysSet.has(String(column.dataIndex))">
            <span class="tabular-nums" :class="{ 'font-semibold': record.__summary }">
              {{ cellValue(record, column.dataIndex as MetricKey) }}
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

:deep(.ant-table-thead > tr > th) {
  text-align: center;
}
</style>
