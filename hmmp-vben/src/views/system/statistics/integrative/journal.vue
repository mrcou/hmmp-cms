<script lang="ts" setup>
/**
 * 期刊统计 —— 按时间区间汇总收稿/发稿/退稿/录用等指标
 * 布局：兄弟页导航 + 筛选行 + 三列错落统计表
 */
import type { StatisticsApi } from '#/api/biz/statistics';

import { computed, onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { message } from 'antdv-next';

import { getJournalTotal } from '#/api/biz/statistics';
import {
  JOURNAL_NAME_LABEL,
  useJournalMagazine,
} from '#/composables/use-journal-magazine';

defineOptions({ name: 'StatisticsIntegrativeJournal' });

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

const filters = reactive({
  magazineId: 'default',
  beginTime: currentYear,
  endTime: currentYear,
});

const loading = ref(false);
const stats = ref<StatisticsApi.JournalTotal>({});

interface StatCell {
  label: string;
  value: number | string;
  unit: string;
  /** 第三列比率：展示进度条 */
  isRate?: boolean;
  /** 进度条语义色：正向指标 / 退稿类指标 */
  tone?: 'positive' | 'reject';
}

interface StatRow {
  key: string;
  cells: StatCell[];
  /** 是否为「次数」类单列行 */
  sparse?: boolean;
}

function num(v?: number) {
  return v ?? 0;
}

function rate(v?: number) {
  return Number(num(v)).toFixed(2);
}

function rateCell(
  label: string,
  value: number | undefined,
  tone: 'positive' | 'reject' = 'positive',
): StatCell {
  return {
    label,
    value: rate(value),
    unit: '%',
    isRate: true,
    tone,
  };
}

/** 进度条百分比（限制 0–100） */
function ratePercent(value: number | string) {
  const n = Number(value);
  if (Number.isNaN(n) || n <= 0) return 0;
  return Math.min(100, Math.round(n * 100) / 100);
}

function rateStatus(tone: StatCell['tone'], percent: number) {
  if (tone === 'reject') {
    if (percent >= 60) return 'exception' as const;
    if (percent >= 30) return 'normal' as const;
    return 'success' as const;
  }
  if (percent >= 80) return 'success' as const;
  if (percent >= 40) return 'active' as const;
  return 'normal' as const;
}

const statRows = computed<StatRow[]>(() => {
  const s = stats.value;
  return [
    {
      key: 'receive',
      cells: [
        { label: '收稿量', value: num(s.receiveCount), unit: '篇' },
        { label: '年平均收稿量', value: num(s.avgReceiveCount), unit: '篇' },
      ],
    },
    {
      key: 'publish',
      cells: [
        { label: '发稿量', value: num(s.publishCount), unit: '篇' },
        { label: '年平均发稿量', value: num(s.avgPublishCount), unit: '篇' },
        rateCell('发稿率', s.publishRate, 'positive'),
      ],
    },
    {
      key: 'reject',
      cells: [
        { label: '退稿量', value: num(s.rejectCount), unit: '篇' },
        { label: '年平均退稿量', value: num(s.avgRejectCount), unit: '篇' },
        rateCell('退稿率', s.rejectRate, 'reject'),
      ],
    },
    {
      key: 'initialReject',
      cells: [
        { label: '初审退稿量', value: num(s.initialRejectCount), unit: '篇' },
        { label: '年平均初审退稿量', value: num(s.avgInitialRejectCount), unit: '篇' },
        rateCell('年初审退稿率', s.initialRejectRate, 'reject'),
      ],
    },
    {
      key: 'reviewReject',
      cells: [
        { label: '评审退稿量', value: num(s.reviewRejectCount), unit: '篇' },
        { label: '年平均评审退稿量', value: num(s.avgReviewRejectCount), unit: '篇' },
        rateCell('年评审退稿率', s.reviewRejectRate, 'reject'),
      ],
    },
    {
      key: 'reReviewReject',
      cells: [
        { label: '复审退稿量', value: num(s.reReviewRejectCount), unit: '篇' },
        { label: '年平均复审退稿量', value: num(s.avgReReviewRejectCount), unit: '篇' },
        rateCell('年复审退稿率', s.reReviewRejectRate, 'reject'),
      ],
    },
    {
      key: 'finalReject',
      cells: [
        { label: '终审退稿量', value: num(s.finalRejectCount), unit: '篇' },
        { label: '年平均终审退稿量', value: num(s.avgFinalRejectCount), unit: '篇' },
        rateCell('年终审退稿率', s.finalRejectRate, 'reject'),
      ],
    },
    {
      key: 'adopt',
      cells: [
        { label: '录用量', value: num(s.adoptCount), unit: '篇' },
        { label: '年平均录用量', value: num(s.avgAdoptCount), unit: '篇' },
        rateCell('录用率', s.adoptRate, 'positive'),
      ],
    },
    {
      key: 'sendReview',
      cells: [
        { label: '送审量', value: num(s.sendReviewCount), unit: '篇' },
        { label: '年平均送审量', value: num(s.avgSendReviewCount), unit: '篇' },
        rateCell('送审率', s.sendReviewRate, 'positive'),
      ],
    },
    {
      key: 'fund',
      cells: [
        { label: '基金量', value: num(s.fundCount), unit: '篇' },
        { label: '年平均基金量', value: num(s.avgFundCount), unit: '篇' },
        rateCell('年平均基金率', s.fundRate, 'positive'),
      ],
    },
    {
      key: 'initialTimes',
      sparse: true,
      cells: [{ label: '初审', value: num(s.initialReviewTimes), unit: '次' }],
    },
    {
      key: 'peerTimes',
      sparse: true,
      cells: [{ label: '评审', value: num(s.peerReviewTimes), unit: '次' }],
    },
    {
      key: 'reReviewTimes',
      sparse: true,
      cells: [{ label: '复审', value: num(s.reReviewTimes), unit: '次' }],
    },
    {
      key: 'finalTimes',
      sparse: true,
      cells: [{ label: '终审', value: num(s.finalReviewTimes), unit: '次' }],
    },
  ];
});

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

async function loadStats() {
  if (!validateDateInput(filters.beginTime, '起始时间')) return;
  if (!validateDateInput(filters.endTime, '截止时间')) return;

  loading.value = true;
  try {
    const res = (await getJournalTotal({
      beginTime: filters.beginTime.trim(),
      endTime: filters.endTime.trim(),
      magazineId: filters.magazineId,
    })) as { data?: StatisticsApi.JournalTotal };
    stats.value = res?.data ?? {};
  } catch {
    message.error('加载期刊统计失败');
    stats.value = {};
  } finally {
    loading.value = false;
  }
}

function onSubmit() {
  loadStats();
}

onMounted(() => {
  loadStats();
});
</script>

<template>
  <div class="p-4">
    <!-- 标题 + 兄弟统计页导航 -->
    <div class="mb-3 flex flex-wrap items-center gap-x-4 gap-y-2">
      <a-button type="primary" class="pointer-events-none shrink-0">
        期刊统计
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
      <!-- 筛选行 -->
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
          <span class="filter-label">起始时间</span>
          <a-input
            v-model:value="filters.beginTime"
            allow-clear
            placeholder="如 2026"
            style="width: 120px"
            @press-enter="onSubmit"
          />
        </div>
        <div class="flex items-center gap-2">
          <span class="filter-label">截止时间</span>
          <a-input
            v-model:value="filters.endTime"
            allow-clear
            placeholder="如 2026"
            style="width: 120px"
            @press-enter="onSubmit"
          />
        </div>
        <a-button type="primary" :loading="loading" @click="onSubmit">
          提交
        </a-button>
        <span class="text-muted-foreground text-xs">({{ DATE_HINT }})</span>
      </div>

      <!-- 统计表：三列错落，紧凑分隔 -->
      <a-spin :spinning="loading">
        <div class="stat-table">
          <div
            v-for="row in statRows"
            :key="row.key"
            class="stat-row"
            :class="{ sparse: row.sparse }"
          >
            <div
              v-for="(cell, idx) in row.cells"
              :key="`${row.key}-${idx}`"
              class="stat-cell"
              :class="{ 'stat-cell-rate': cell.isRate }"
            >
              <template v-if="cell.isRate">
                <div class="rate-meta">
                  <span class="stat-label">{{ cell.label }}</span>
                  <span class="stat-value">{{ cell.value }}</span>
                  <span class="stat-unit">{{ cell.unit }}</span>
                </div>
                <a-progress
                  class="rate-progress"
                  size="small"
                  :percent="ratePercent(cell.value)"
                  :status="rateStatus(cell.tone, ratePercent(cell.value))"
                  :show-info="false"
                />
              </template>
              <template v-else>
                <span class="stat-label">{{ cell.label }}</span>
                <span class="stat-value">{{ cell.value }}</span>
                <span class="stat-unit">{{ cell.unit }}</span>
              </template>
            </div>
            <!-- 不足三列时占位，保持竖向对齐节奏 -->
            <template v-if="!row.sparse && row.cells.length < 3">
              <div
                v-for="n in 3 - row.cells.length"
                :key="`pad-${row.key}-${n}`"
                class="stat-cell pad"
              />
            </template>
          </div>
        </div>
      </a-spin>
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

.stat-table {
  border: 1px solid hsl(var(--border));
  border-radius: 6px;
  overflow: hidden;
}

.stat-row {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0 1.25rem;
  padding: 0.65rem 1rem;
  border-bottom: 1px solid hsl(var(--border));
  background: hsl(var(--card));
  transition: background 0.12s ease;
}

.stat-row:nth-child(even) {
  background: hsl(var(--muted) / 35%);
}

.stat-row:last-child {
  border-bottom: none;
}

.stat-row:hover {
  background: hsl(var(--accent) / 45%);
}

.stat-row.sparse {
  grid-template-columns: minmax(0, 1fr);
}

.stat-cell {
  display: flex;
  align-items: baseline;
  gap: 0.4rem;
  min-width: 0;
  line-height: 1.5;
}

.stat-cell.pad {
  visibility: hidden;
}

.stat-cell-rate {
  flex-direction: column;
  align-items: stretch;
  gap: 0.2rem;
}

.rate-meta {
  display: flex;
  align-items: baseline;
  gap: 0.4rem;
  min-width: 0;
}

.rate-progress {
  margin-inline-end: 0;
  line-height: 1;
}

.rate-progress :deep(.ant-progress-outer) {
  margin-inline-end: 0;
  padding-inline-end: 0;
}

.rate-progress :deep(.ant-progress-inner) {
  background: hsl(var(--muted) / 55%);
}

.stat-label {
  color: hsl(var(--muted-foreground));
  font-size: 0.8125rem;
  flex-shrink: 0;
}

.stat-value {
  color: hsl(var(--foreground));
  font-size: 0.9375rem;
  font-weight: 600;
  font-variant-numeric: tabular-nums;
  letter-spacing: 0.01em;
}

.stat-unit {
  color: hsl(var(--muted-foreground));
  font-size: 0.75rem;
}

@media (max-width: 768px) {
  .stat-row:not(.sparse) {
    grid-template-columns: 1fr;
    gap: 0.35rem;
  }

  .stat-cell.pad {
    display: none;
  }
}
</style>
