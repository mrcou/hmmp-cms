<script lang="ts" setup>
/**
 * 出版周期统计 —— 按稿件展示排期与纸质/网络出版时间
 * 布局：标题 + 兄弟统计页导航 + 筛选行 + 表格 + 底部按钮（高级检索 / 导出数据 / 显示设置）
 */
import type { StatisticsApi } from '#/api/biz/statistics';

import { computed, onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { IconifyIcon } from '@vben/icons';
import { preferences } from '@vben/preferences';

import { message } from 'antdv-next';

import * as meetingApi from '#/api/biz/meeting';
import * as publisherApi from '#/api/biz/publisher';
import {
  exportPublishCycleList,
  getPublishCycleList,
} from '#/api/biz/statistics';

defineOptions({ name: 'StatisticsIntegrativePublishCycle' });

const route = useRoute();
const router = useRouter();

/** 与后端 editor_manuscript.status 一致 */
const statusMap: Record<string, string> = {
  '01': '新收稿',
  '02': '新分配',
  '03': '审稿中',
  '04': '已退修',
  '05': '已修回',
  '06': '已采用',
  '07': '已退稿',
  '08': '已发表',
  '09': '已归档',
};

/** 加急程度（示意筛选项；库表暂无对应字段，仅前端展示） */
const urgencyOptions = [
  { value: '0', label: '普通' },
  { value: '1', label: '加急' },
  { value: '2', label: '特急' },
];

/** 审理进程状态（示意筛选项；库表暂无对应字段，仅前端展示） */
const processStatusOptions = [
  { value: 'pending', label: '待审理' },
  { value: 'reviewing', label: '审理中' },
  { value: 'adopted', label: '已录用' },
  { value: 'published', label: '已出版' },
];

/** 综合统计兄弟页面导航 */
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

const magazineOptions = computed(() => [
  {
    value: 'default',
    label: preferences.app.name || 'HMMP管理系统',
  },
]);

/** 筛选行（对齐示意图） */
const filters = reactive({
  magazineId: 'default' as string | undefined,
  meetingId: undefined as number | undefined,
  status: undefined as string | undefined,
  columnName: undefined as string | undefined,
  urgency: undefined as string | undefined,
  processStatus: undefined as string | undefined,
  searchField: 'fileNo' as 'authorNames' | 'fileNo' | 'title',
  searchValue: '',
});

const searchFieldOptions = [
  { value: 'fileNo', label: '稿件编号' },
  { value: 'title', label: '标题' },
  { value: 'authorNames', label: '作者' },
];

const meetingOptions = ref<{ label: string; value: number }[]>([]);
const columnOptions = ref<string[]>([]);

async function loadFilterOptions() {
  try {
    const [columnRes, meetingRes] = (await Promise.all([
      publisherApi.getColumnList({ pageNum: 1, pageSize: 200 }),
      meetingApi.getMeetingList({ pageNum: 1, pageSize: 200 }),
    ])) as any[];
    columnOptions.value = [
      ...new Set<string>(
        (columnRes?.rows ?? []).map((r: any) => r.columnName).filter(Boolean),
      ),
    ];
    meetingOptions.value = (meetingRes?.rows ?? [])
      .map((r: any) => ({
        value: r.meetingId as number,
        label: (r.meetingName || r.title || `会议#${r.meetingId}`) as string,
      }))
      .filter((m: { value: number }) => m.value != null);
  } catch {
    // 无相应权限时静默降级，筛选下拉留空
  }
}

/** 高级检索 */
const advancedOpen = ref(false);

interface SearchRow {
  id: number;
  field: string;
  value: string;
  range: [string, string] | null;
}

let searchRowSeq = 1;
function createSearchRow(field = 'fileNo'): SearchRow {
  return { id: searchRowSeq++, field, value: '', range: null };
}
const searchRows = ref<SearchRow[]>([createSearchRow()]);

const advancedFields = [
  { value: 'fileNo', label: '稿件编号', type: 'text' },
  { value: 'title', label: '标题', type: 'text' },
  { value: 'authorNames', label: '作者', type: 'text' },
  { value: 'status', label: '稿件状态', type: 'status' },
  { value: 'columnName', label: '栏目名称', type: 'text' },
  { value: 'year', label: '年份', type: 'text' },
  { value: 'period', label: '刊期', type: 'text' },
  { value: 'onlinePublishTime', label: '网络出版时间', type: 'range' },
] as const;

function fieldType(field: string) {
  return advancedFields.find((f) => f.value === field)?.type ?? 'text';
}

function addSearchRow() {
  searchRows.value.push(createSearchRow('title'));
}

function removeSearchRow(id: number) {
  if (searchRows.value.length <= 1) return;
  searchRows.value = searchRows.value.filter((r) => r.id !== id);
}

function onAdvancedFieldChange(row: SearchRow) {
  row.value = '';
  row.range = null;
}

function onResetAdvanced() {
  searchRows.value = [createSearchRow()];
  pagination.current = 1;
  loadTable();
}

/** 表格 */
const tableData = ref<StatisticsApi.PublishCycleRow[]>([]);
const tableLoading = ref(false);
const selectedKeys = ref<number[]>([]);

const pagination = reactive({
  current: 1,
  pageSize: 5,
  total: 0,
  showSizeChanger: true,
  pageSizeOptions: ['5', '10', '20', '50'],
  showTotal: (total: number, range: [number, number]) =>
    `当前显示 ${range[0]} - ${range[1]} 条，共 ${total} 条记录`,
});

const allColumns = [
  { key: 'fileNo', title: '稿件编号', dataIndex: 'fileNo', width: 130 },
  { key: 'title', title: '标题', dataIndex: 'title', ellipsis: true, width: 260 },
  { key: 'authorNames', title: '作者', dataIndex: 'authorNames', width: 110 },
  { key: 'year', title: '年份', dataIndex: 'year', width: 80 },
  { key: 'period', title: '刊期', dataIndex: 'period', width: 80 },
  { key: 'columnName', title: '栏目名称', dataIndex: 'columnName', width: 120 },
  { key: 'pageStart', title: '起始页码', dataIndex: 'pageStart', width: 100 },
  { key: 'pageEnd', title: '截止页码', dataIndex: 'pageEnd', width: 100 },
  {
    key: 'paperPublishTime',
    title: '纸质出版时间',
    dataIndex: 'paperPublishTime',
    width: 130,
  },
  {
    key: 'onlinePublishTime',
    title: '网络出版时间',
    dataIndex: 'onlinePublishTime',
    width: 160,
  },
];

const visibleColumnKeys = ref(allColumns.map((c) => c.key));
const columnDrawerOpen = ref(false);

const columns = computed(() => {
  const visible = new Set(visibleColumnKeys.value);
  return allColumns.filter((c) => visible.has(c.key));
});

const rowSelection = computed(() => ({
  selectedRowKeys: selectedKeys.value,
  onChange: (keys: (number | string)[]) => {
    selectedKeys.value = keys.map(Number);
  },
}));

function buildSearchParams(): StatisticsApi.PublishCycleListParams {
  const params: StatisticsApi.PublishCycleListParams = {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  };

  if (filters.status) params.status = filters.status;
  if (filters.columnName) params.columnName = filters.columnName;
  if (filters.searchValue.trim()) {
    params[filters.searchField] = filters.searchValue.trim();
  }

  for (const row of searchRows.value) {
    if (fieldType(row.field) === 'range') {
      if (row.range?.[0] && row.range?.[1]) {
        params['params[beginTime]'] = row.range[0];
        params['params[endTime]'] = row.range[1];
      }
      continue;
    }
    if (!row.value?.trim()) continue;
    if (row.field === 'year' || row.field === 'period') {
      const num = Number(row.value.trim());
      if (!Number.isNaN(num)) {
        (params as any)[row.field] = num;
      }
      continue;
    }
    (params as any)[row.field] = row.value.trim();
  }

  return params;
}

async function loadTable() {
  tableLoading.value = true;
  try {
    const res = (await getPublishCycleList(buildSearchParams())) as any;
    tableData.value = res?.rows ?? [];
    pagination.total = res?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载出版周期统计失败');
    tableData.value = [];
    pagination.total = 0;
  } finally {
    tableLoading.value = false;
  }
}

function onSearch() {
  pagination.current = 1;
  loadTable();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  loadTable();
}

function formatDate(val?: string) {
  return val ? String(val).slice(0, 10) : '';
}

function formatDateTime(val?: string) {
  return val ? String(val).slice(0, 19).replace('T', ' ') : '';
}

/** 底部按钮 */
async function onExport() {
  try {
    const params = buildSearchParams() as Record<string, any>;
    delete params.pageNum;
    delete params.pageSize;
    await exportPublishCycleList(params);
  } catch {
    message.error('导出失败');
  }
}

onMounted(() => {
  loadTable();
  loadFilterOptions();
});
</script>

<template>
  <div class="p-4">
    <!-- 标题 + 兄弟统计页导航 + 高级检索入口 -->
    <div class="mb-3 flex flex-wrap items-center gap-x-4 gap-y-2">
      <a-button type="primary" class="pointer-events-none shrink-0">
        出版周期统计
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
      <a
        href="javascript:;"
        class="text-primary shrink-0 text-sm font-medium"
        @click.prevent="advancedOpen = !advancedOpen"
      >
        高级检索
      </a>
    </div>

    <!-- 高级检索面板 -->
    <div v-show="advancedOpen" class="bg-card mb-3 rounded-md border px-3 py-3">
      <div class="mb-2 text-sm font-medium">检索条件设置:</div>
      <div
        v-for="(row, idx) in searchRows"
        :key="row.id"
        class="mb-2 flex flex-wrap items-center gap-2"
      >
        <a-button
          v-if="idx === 0"
          type="primary"
          size="small"
          class="!h-7 !w-7 !min-w-7 !p-0"
          @click="addSearchRow"
        >
          <IconifyIcon icon="lucide:plus" class="size-3.5" />
        </a-button>
        <a-button
          v-else
          type="primary"
          size="small"
          class="!h-7 !w-7 !min-w-7 !p-0"
          style="background: #0d9488; border-color: #0d9488"
          @click="removeSearchRow(row.id)"
        >
          <IconifyIcon icon="lucide:minus" class="size-3.5" />
        </a-button>

        <a-select
          v-model:value="row.field"
          style="width: 130px"
          size="small"
          @change="onAdvancedFieldChange(row)"
        >
          <a-select-option
            v-for="f in advancedFields"
            :key="f.value"
            :value="f.value"
          >
            {{ f.label }}
          </a-select-option>
        </a-select>

        <template v-if="fieldType(row.field) === 'range'">
          <a-range-picker
            v-model:value="row.range"
            value-format="YYYY-MM-DD"
            size="small"
            style="width: 260px"
          />
        </template>
        <a-select
          v-else-if="fieldType(row.field) === 'status'"
          v-model:value="row.value"
          allow-clear
          placeholder="请选择状态"
          size="small"
          style="width: 180px"
        >
          <a-select-option v-for="(label, code) in statusMap" :key="code" :value="code">
            {{ label }}
          </a-select-option>
        </a-select>
        <a-input
          v-else
          v-model:value="row.value"
          size="small"
          allow-clear
          placeholder="请输入搜索词..."
          style="width: 220px"
          @press-enter="onSearch"
        />
      </div>

      <div class="mt-3 flex justify-end gap-2">
        <a-button type="primary" size="small" @click="onSearch">
          <template #icon>
            <IconifyIcon icon="lucide:search" class="size-3.5" />
          </template>
          检索
        </a-button>
        <a-button size="small" @click="onResetAdvanced">重置</a-button>
        <a-button size="small" @click="advancedOpen = false">
          <template #icon>
            <IconifyIcon icon="lucide:x" class="size-3.5" />
          </template>
          关闭
        </a-button>
      </div>
    </div>

    <a-card :bordered="false" class="shadow-sm">
      <!-- 筛选行（对齐示意图） -->
      <div class="mb-3 flex flex-wrap items-center gap-2">
        <a-select
          v-model:value="filters.magazineId"
          allow-clear
          placeholder="杂志名称"
          style="width: 160px"
          @change="onSearch"
        >
          <a-select-option
            v-for="m in magazineOptions"
            :key="m.value"
            :value="m.value"
          >
            {{ m.label }}
          </a-select-option>
        </a-select>
        <a-select
          v-model:value="filters.meetingId"
          allow-clear
          placeholder="--会议--"
          style="width: 160px"
          @change="onSearch"
        >
          <a-select-option
            v-for="m in meetingOptions"
            :key="m.value"
            :value="m.value"
          >
            {{ m.label }}
          </a-select-option>
        </a-select>
        <a-select
          v-model:value="filters.status"
          allow-clear
          placeholder="--稿件状态--"
          style="width: 140px"
          @change="onSearch"
        >
          <a-select-option v-for="(label, code) in statusMap" :key="code" :value="code">
            {{ label }}
          </a-select-option>
        </a-select>
        <a-select
          v-model:value="filters.columnName"
          allow-clear
          placeholder="--栏目名称--"
          style="width: 160px"
          @change="onSearch"
        >
          <a-select-option v-for="c in columnOptions" :key="c" :value="c">
            {{ c }}
          </a-select-option>
        </a-select>
        <a-select
          v-model:value="filters.urgency"
          allow-clear
          placeholder="--加急程度--"
          style="width: 130px"
          @change="onSearch"
        >
          <a-select-option
            v-for="opt in urgencyOptions"
            :key="opt.value"
            :value="opt.value"
          >
            {{ opt.label }}
          </a-select-option>
        </a-select>
        <a-select
          v-model:value="filters.processStatus"
          allow-clear
          placeholder="--审理进程状态--"
          style="width: 150px"
          @change="onSearch"
        >
          <a-select-option
            v-for="opt in processStatusOptions"
            :key="opt.value"
            :value="opt.value"
          >
            {{ opt.label }}
          </a-select-option>
        </a-select>
        <div class="ml-auto flex items-center">
          <a-select v-model:value="filters.searchField" style="width: 110px">
            <a-select-option
              v-for="f in searchFieldOptions"
              :key="f.value"
              :value="f.value"
            >
              {{ f.label }}
            </a-select-option>
          </a-select>
          <a-input-search
            v-model:value="filters.searchValue"
            allow-clear
            placeholder="请输入搜索词..."
            style="width: 320px"
            @search="onSearch"
          />
        </div>
      </div>

      <!-- 表格 -->
      <a-table
        :columns="columns"
        :data-source="tableData"
        :loading="tableLoading"
        :pagination="pagination"
        :row-selection="rowSelection"
        :scroll="{ x: 1400 }"
        row-key="manuscriptId"
        size="middle"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #emptyText>
          <a-empty description="未查询到任何数据!!" />
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'title'">
            <span class="text-primary">{{ record.title }}</span>
          </template>
          <template v-else-if="column.key === 'authorNames'">
            <span class="text-primary">{{ record.authorNames || '' }}</span>
          </template>
          <template v-else-if="column.key === 'year'">
            {{ record.year ?? '' }}
          </template>
          <template v-else-if="column.key === 'period'">
            {{ record.period ?? '' }}
          </template>
          <template v-else-if="column.key === 'columnName'">
            {{ record.columnName ?? '' }}
          </template>
          <template v-else-if="column.key === 'pageStart'">
            {{ record.pageStart ?? '' }}
          </template>
          <template v-else-if="column.key === 'pageEnd'">
            {{ record.pageEnd ?? '' }}
          </template>
          <template v-else-if="column.key === 'paperPublishTime'">
            {{ formatDate(record.paperPublishTime) }}
          </template>
          <template v-else-if="column.key === 'onlinePublishTime'">
            {{ formatDateTime(record.onlinePublishTime) }}
          </template>
        </template>
      </a-table>

      <!-- 表格下方按钮行 -->
      <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
        <a-button type="primary" @click="advancedOpen = !advancedOpen">
          高级检索
        </a-button>
        <a-button type="primary" @click="onExport">导出数据</a-button>
        <a-button type="primary" ghost @click="columnDrawerOpen = true">
          显示设置
        </a-button>
      </div>
    </a-card>

    <!-- 显示设置 -->
    <a-drawer
      v-model:open="columnDrawerOpen"
      title="显示设置"
      placement="right"
      :width="320"
    >
      <p class="text-muted-foreground mb-3 text-sm">勾选需要在列表中显示的列：</p>
      <a-checkbox-group
        v-model:value="visibleColumnKeys"
        class="flex flex-col gap-2"
      >
        <a-checkbox v-for="col in allColumns" :key="col.key" :value="col.key">
          {{ col.title }}
        </a-checkbox>
      </a-checkbox-group>
      <div class="mt-6">
        <a-button type="primary" block @click="columnDrawerOpen = false">
          确定
        </a-button>
      </div>
    </a-drawer>
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

.batch-actions :deep(.ant-btn) {
  min-width: 7rem;
}
</style>
