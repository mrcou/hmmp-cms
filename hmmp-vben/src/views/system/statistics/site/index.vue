<script lang="ts" setup>
/**
 * 网站访问量统计 —— 按年汇总 PV / UV / 登录·匿名访问 / 停留时长
 * 布局对齐参考图：起始年份 + 截止年份 + 统计 + 嵌套表头
 */
import type { StatisticsApi } from '#/api/biz/statistics';

import { computed, onMounted, reactive, ref } from 'vue';

import { message } from 'antdv-next';

import { exportSiteVisitTotal, getSiteVisitTotal } from '#/api/biz/statistics';

defineOptions({ name: 'StatisticsSiteVisit' });

const currentYear = String(new Date().getFullYear());

const filters = reactive({
  beginTime: currentYear,
  endTime: currentYear,
});

const loading = ref(false);
const exporting = ref(false);
const rows = ref<StatisticsApi.SiteVisitRow[]>([]);

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

function rateText(v?: number | null) {
  return `${Number(num(v)).toFixed(1)}%`;
}

function buildParams(): StatisticsApi.SiteVisitParams {
  return {
    beginTime: filters.beginTime.trim(),
    endTime: filters.endTime.trim(),
  };
}

async function loadData() {
  if (!validateDateInput(filters.beginTime, '起始年份')) return;
  if (!validateDateInput(filters.endTime, '截止年份')) return;

  loading.value = true;
  try {
    const res = (await getSiteVisitTotal(buildParams())) as {
      data?: StatisticsApi.SiteVisitRow[];
    };
    rows.value = Array.isArray(res?.data) ? res.data : [];
  } catch {
    message.error('加载网站访问量统计失败');
    rows.value = [];
  } finally {
    loading.value = false;
  }
}

function onSubmit() {
  loadData();
}

async function onExport() {
  if (!validateDateInput(filters.beginTime, '起始年份')) return;
  if (!validateDateInput(filters.endTime, '截止年份')) return;
  exporting.value = true;
  try {
    await exportSiteVisitTotal(buildParams());
  } catch {
    message.error('导出失败');
  } finally {
    exporting.value = false;
  }
}

const summaryRow = computed(() => {
  const list = rows.value;
  const pv = list.reduce((s, r) => s + num(r.pvCount), 0);
  const login = list.reduce((s, r) => s + num(r.loginCount), 0);
  const anon = list.reduce((s, r) => s + num(r.anonCount), 0);
  const staySamples = list.filter((r) => num(r.pvCount) > 0);
  const avgStay =
    staySamples.length === 0
      ? 0
      : staySamples.reduce((s, r) => s + num(r.avgStayTime), 0) / staySamples.length;
  const minStay =
    staySamples.length === 0
      ? 0
      : Math.min(...staySamples.map((r) => num(r.minStayTime)));
  const maxStay =
    staySamples.length === 0
      ? 0
      : Math.max(...staySamples.map((r) => num(r.maxStayTime)));

  const row: StatisticsApi.SiteVisitRow & { __summary?: boolean } = {
    visitYear: undefined,
    pvCount: pv,
    uvCount: list.reduce((s, r) => s + num(r.uvCount), 0),
    loginCount: login,
    loginRate: pv > 0 ? Math.round((login * 1000) / pv) / 10 : 0,
    anonCount: anon,
    anonRate: pv > 0 ? Math.round((anon * 1000) / pv) / 10 : 0,
    pageCount: list.reduce((s, r) => s + num(r.pageCount), 0),
    avgStayTime: Math.round(avgStay * 10) / 10,
    minStayTime: minStay,
    maxStayTime: maxStay,
    __summary: true,
  };
  return row;
});

const tableData = computed(() => {
  if (rows.value.length === 0) return [];
  return [...rows.value, summaryRow.value];
});

function qtyPctColumns(title: string, qtyKey: string, pctKey: string) {
  return {
    title,
    align: 'center' as const,
    children: [
      {
        title: '量',
        dataIndex: qtyKey,
        key: qtyKey,
        width: 90,
        align: 'center' as const,
      },
      {
        title: '%',
        dataIndex: pctKey,
        key: pctKey,
        width: 80,
        align: 'center' as const,
      },
    ],
  };
}

const columns = [
  {
    title: '年份',
    dataIndex: 'visitYear',
    key: 'visitYear',
    width: 100,
    align: 'center' as const,
    fixed: 'left' as const,
  },
  {
    title: '(1) 访问量',
    dataIndex: 'pvCount',
    key: 'pvCount',
    width: 100,
    align: 'center' as const,
  },
  {
    title: '(2) 独立访客',
    dataIndex: 'uvCount',
    key: 'uvCount',
    width: 110,
    align: 'center' as const,
  },
  qtyPctColumns('(3) 登录访问', 'loginCount', 'loginRate'),
  qtyPctColumns('(4) 匿名访问', 'anonCount', 'anonRate'),
  {
    title: '(5) 页面种类',
    dataIndex: 'pageCount',
    key: 'pageCount',
    width: 100,
    align: 'center' as const,
  },
  {
    title: '(6) 停留时长 (秒)',
    align: 'center' as const,
    children: [
      {
        title: '平均',
        dataIndex: 'avgStayTime',
        key: 'avgStayTime',
        width: 90,
        align: 'center' as const,
      },
      {
        title: '最小值',
        dataIndex: 'minStayTime',
        key: 'minStayTime',
        width: 90,
        align: 'center' as const,
      },
      {
        title: '最大值',
        dataIndex: 'maxStayTime',
        key: 'maxStayTime',
        width: 90,
        align: 'center' as const,
      },
    ],
  },
];

const rateKeys = new Set(['loginRate', 'anonRate']);

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" class="shadow-sm">
      <template #title>
        <span class="text-base font-medium">网站访问量统计</span>
      </template>

      <div class="filter-bar mb-4 flex flex-wrap items-center gap-x-3 gap-y-2 border-b pb-4">
        <div class="flex items-center gap-2">
          <span class="filter-label">起始年份:</span>
          <a-input
            v-model:value="filters.beginTime"
            allow-clear
            placeholder="年/月/日"
            style="width: 120px"
            @press-enter="onSubmit"
          />
        </div>
        <div class="flex items-center gap-2">
          <span class="filter-label">截止年份:</span>
          <a-input
            v-model:value="filters.endTime"
            allow-clear
            placeholder="年/月/日"
            style="width: 120px"
            @press-enter="onSubmit"
          />
        </div>
        <a-button type="primary" :loading="loading" @click="onSubmit">
          统计
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
        :row-key="(record: any) =>
          record.__summary ? 'summary' : String(record.visitYear ?? '')"
        size="middle"
        bordered
        :scroll="{ x: 1100 }"
      >
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.dataIndex === 'visitYear'">
            <span :class="{ 'font-semibold': record.__summary }">
              {{ record.__summary ? '合计' : text }}
            </span>
          </template>
          <template v-else-if="rateKeys.has(String(column.dataIndex))">
            <span class="tabular-nums" :class="{ 'font-semibold': record.__summary }">
              {{ rateText(text) }}
            </span>
          </template>
          <template v-else>
            <span class="tabular-nums" :class="{ 'font-semibold': record.__summary }">
              {{ text ?? 0 }}
            </span>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<style scoped>
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
