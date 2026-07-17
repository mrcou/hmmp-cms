<script lang="ts" setup>
/**
 * 审稿统计 —— 按审稿人聚合审回次数、审回率、审稿周期等
 * 布局：标题 + 兄弟统计页导航 + 提示 + 筛选行 + 表格 + 底部（下载/重新统计/显示设置）
 */
import type { EditorApi } from '#/api/biz/editor';
import type { StatisticsApi } from '#/api/biz/statistics';

import { computed, onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';
import { exportReviewerAuditList, getReviewerAuditList } from '#/api/biz/statistics';

defineOptions({ name: 'StatisticsIntegrativeReviewerAudit' });

const route = useRoute();
const router = useRouter();

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

/** 筛选行 */
const filters = reactive({
  searchField: 'userName' as
    | 'affiliation'
    | 'email'
    | 'expertiseFields'
    | 'mobile'
    | 'realName'
    | 'searchKeywords'
    | 'userName',
  searchValue: '',
  dateRange: null as [string, string] | null,
  permissionLevel: '' as string,
});

const searchFieldOptions = [
  { value: 'userName', label: '用户名' },
  { value: 'realName', label: '姓名' },
  { value: 'affiliation', label: '单位' },
  { value: 'mobile', label: '手机' },
  { value: 'email', label: 'E-mail' },
  { value: 'expertiseFields', label: '审稿领域' },
  { value: 'searchKeywords', label: '查询关键词' },
];

const permissionLevelOptions = [
  { value: '', label: '全部' },
  { value: '0', label: '0' },
  { value: '1', label: '1' },
];

/** 表格 */
const tableData = ref<StatisticsApi.ReviewerAuditRow[]>([]);
const tableLoading = ref(false);
const exporting = ref(false);
const selectedKeys = ref<number[]>([]);

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  pageSizeOptions: ['10', '20', '50', '100'],
  showTotal: (total: number, range: [number, number]) =>
    `当前显示 ${range[0]} - ${range[1]} 条，共 ${total} 条记录`,
});

const allColumns = [
  { key: 'userName', title: '用户名', dataIndex: 'userName', width: 160, ellipsis: true },
  { key: 'realName', title: '姓名', dataIndex: 'realName', width: 100 },
  { key: 'affiliation', title: '单位', dataIndex: 'affiliation', ellipsis: true, width: 160 },
  { key: 'mobile', title: '手机', dataIndex: 'mobile', width: 120 },
  { key: 'email', title: 'E-mail', dataIndex: 'email', ellipsis: true, width: 180 },
  { key: 'idCard', title: '身份证号', dataIndex: 'idCard', width: 160, ellipsis: true },
  { key: 'expertiseFields', title: '审稿领域', dataIndex: 'expertiseFields', ellipsis: true, width: 140 },
  { key: 'searchKeywords', title: '查询关键词', dataIndex: 'searchKeywords', ellipsis: true, width: 140 },
  { key: 'permissionLevel', title: '权限级别', dataIndex: 'permissionLevel', width: 90, align: 'center' as const },
  { key: 'reputation', title: '信誉度', dataIndex: 'reputation', width: 90, align: 'right' as const },
  { key: 'returnedCount', title: '审回次数', dataIndex: 'returnedCount', width: 100, align: 'right' as const },
  {
    key: 'reReviewReturnedCount',
    title: '复审回次数',
    dataIndex: 'reReviewReturnedCount',
    width: 110,
    align: 'right' as const,
  },
  {
    key: 'returnedManuscriptCount',
    title: '审回篇数',
    dataIndex: 'returnedManuscriptCount',
    width: 100,
    align: 'right' as const,
  },
  { key: 'reviewCycleDays', title: '审稿周期', dataIndex: 'reviewCycleDays', width: 100, align: 'right' as const },
  { key: 'reviewScoreAvg', title: '审稿得分', dataIndex: 'reviewScoreAvg', width: 100, align: 'right' as const },
  { key: 'returnRate', title: '审回率', dataIndex: 'returnRate', width: 90, align: 'right' as const },
  { key: 'reviewFee', title: '审稿费', dataIndex: 'reviewFee', width: 90, align: 'right' as const },
  { key: 'detail', title: '审稿明细', dataIndex: 'detail', width: 100, fixed: 'right' as const },
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

function num(v?: number | null) {
  return v ?? 0;
}

function buildSearchParams(): StatisticsApi.ReviewerAuditListParams {
  const params: StatisticsApi.ReviewerAuditListParams = {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  };
  if (filters.searchValue.trim()) {
    params[filters.searchField] = filters.searchValue.trim();
  }
  if (filters.permissionLevel) {
    params.permissionLevel = filters.permissionLevel;
  }
  if (filters.dateRange?.[0] && filters.dateRange?.[1]) {
    params.beginTime = filters.dateRange[0];
    params.endTime = filters.dateRange[1];
  }
  return params;
}

async function loadTable() {
  tableLoading.value = true;
  try {
    const res = (await getReviewerAuditList(buildSearchParams())) as any;
    tableData.value = res?.rows ?? [];
    pagination.total = res?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载审稿统计失败');
    tableData.value = [];
    pagination.total = 0;
  } finally {
    tableLoading.value = false;
  }
}

/** 重新统计：按当前筛选条件重新查询 */
function onRestat() {
  pagination.current = 1;
  loadTable();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  loadTable();
}

async function onExport() {
  exporting.value = true;
  try {
    const params = buildSearchParams() as Record<string, any>;
    delete params.pageNum;
    delete params.pageSize;
    await exportReviewerAuditList(params);
  } catch {
    message.error('导出失败');
  } finally {
    exporting.value = false;
  }
}

/** 审稿明细弹窗 */
const detailOpen = ref(false);
const detailLoading = ref(false);
const detailTitle = ref('审稿明细');
const detailRows = ref<EditorApi.Audit[]>([]);

const detailColumns = [
  { title: '审稿ID', dataIndex: 'auditId', key: 'auditId', width: 80 },
  { title: '稿件ID', dataIndex: 'manuscriptId', key: 'manuscriptId', width: 80 },
  { title: '审稿人', dataIndex: 'reviewerName', key: 'reviewerName', width: 100 },
  { title: '轮次', dataIndex: 'reviewRound', key: 'reviewRound', width: 70 },
  { title: '状态', dataIndex: 'auditStatus', key: 'auditStatus', width: 100 },
  { title: '结论', dataIndex: 'auditResult', key: 'auditResult', width: 90 },
  { title: '评分', dataIndex: 'auditScore', key: 'auditScore', width: 70 },
  { title: '邀请时间', dataIndex: 'inviteTime', key: 'inviteTime', width: 160 },
  { title: '审回时间', dataIndex: 'auditTime', key: 'auditTime', width: 160 },
];

const auditStatusLabel: Record<string, string> = {
  '0': '未审回',
  '1': '已审回',
  '2': '已撤审',
  '3': '已接受退审',
};

async function onOpenDetail(record: StatisticsApi.ReviewerAuditRow) {
  if (!record.reviewerId) {
    message.warning('缺少审稿人ID，无法打开明细');
    return;
  }
  detailTitle.value = `审稿明细 — ${record.realName || record.userName || record.reviewerId}`;
  detailOpen.value = true;
  detailLoading.value = true;
  detailRows.value = [];
  try {
    const res = (await editorApi.getAuditList({
      reviewerId: record.reviewerId,
      pageNum: 1,
      pageSize: 200,
    })) as any;
    const data = res?.data ?? res;
    detailRows.value = data?.rows ?? data?.list ?? [];
  } catch {
    message.error('加载审稿明细失败');
  } finally {
    detailLoading.value = false;
  }
}

onMounted(() => {
  loadTable();
});
</script>

<template>
  <div class="p-4">
    <!-- 标题 + 兄弟统计页导航 -->
    <div class="mb-3 flex flex-wrap items-center gap-x-4 gap-y-2">
      <a-button type="primary" class="pointer-events-none shrink-0">
        审稿统计({{ pagination.total }})
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

    <a-alert
      class="mb-3"
      type="info"
      show-icon
      message="提示：如需根据条件对审稿统计结果进行筛选，请点击最底下【重新统计】按钮！"
    />

    <a-card :bordered="false" class="shadow-sm">
      <!-- 筛选行 -->
      <div class="mb-3 flex flex-wrap items-center gap-2">
        <span class="filter-label">审稿时间</span>
        <a-range-picker
          v-model:value="filters.dateRange"
          value-format="YYYY-MM-DD"
          style="width: 260px"
        />
        <span class="filter-label">权限级别</span>
        <a-select
          v-model:value="filters.permissionLevel"
          style="width: 100px"
          :options="permissionLevelOptions"
        />
        <div class="ml-auto flex items-center">
          <a-select v-model:value="filters.searchField" style="width: 120px">
            <a-select-option
              v-for="f in searchFieldOptions"
              :key="f.value"
              :value="f.value"
            >
              {{ f.label }}
            </a-select-option>
          </a-select>
          <a-input
            v-model:value="filters.searchValue"
            allow-clear
            placeholder="请输入搜索词..."
            style="width: 280px"
            @press-enter="onRestat"
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
        :scroll="{ x: 2200 }"
        row-key="reviewerId"
        size="middle"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #emptyText>
          <a-empty description="未查询到相关信息！" />
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'userName'">
            <span class="text-primary">{{ record.userName || '-' }}</span>
          </template>
          <template v-else-if="column.key === 'realName'">
            <a class="text-primary" href="javascript:;" @click.prevent="onOpenDetail(record)">
              {{ record.realName || '-' }}
            </a>
          </template>
          <template v-else-if="column.key === 'detail'">
            <a class="text-primary" href="javascript:;" @click.prevent="onOpenDetail(record)">
              审稿明细
            </a>
          </template>
          <template v-else-if="column.key === 'returnRate'">
            <span class="tabular-nums">{{ num(record.returnRate) }}%</span>
          </template>
          <template
            v-else-if="[
              'reputation',
              'returnedCount',
              'reReviewReturnedCount',
              'returnedManuscriptCount',
              'reviewCycleDays',
              'reviewScoreAvg',
              'reviewFee',
            ].includes(column.key)"
          >
            <span class="tabular-nums">{{ num(record[column.dataIndex]) }}</span>
          </template>
          <template
            v-else-if="[
              'affiliation',
              'mobile',
              'email',
              'idCard',
              'expertiseFields',
              'searchKeywords',
              'permissionLevel',
            ].includes(column.key)"
          >
            {{ record[column.dataIndex] || '-' }}
          </template>
        </template>
      </a-table>

      <!-- 表格下方按钮行 -->
      <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
        <a-button type="primary" :loading="exporting" @click="onExport">
          下载审稿统计表
        </a-button>
        <a-button type="primary" :loading="tableLoading" @click="onRestat">
          重新统计
        </a-button>
        <a-button type="primary" ghost @click="columnDrawerOpen = true">
          显示设置
        </a-button>
      </div>
    </a-card>

    <!-- 审稿明细 -->
    <a-modal
      v-model:open="detailOpen"
      :title="detailTitle"
      :footer="null"
      width="960px"
      destroy-on-close
    >
      <a-table
        :columns="detailColumns"
        :data-source="detailRows"
        :loading="detailLoading"
        :pagination="false"
        :scroll="{ y: 420 }"
        row-key="auditId"
        size="small"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'auditStatus'">
            {{ auditStatusLabel[record.auditStatus] || record.auditStatus || '-' }}
          </template>
        </template>
      </a-table>
    </a-modal>

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

.filter-label {
  color: hsl(var(--foreground) / 80%);
  font-size: 0.8125rem;
  white-space: nowrap;
}

.batch-actions :deep(.ant-btn) {
  min-width: 7rem;
}

.text-primary {
  color: hsl(var(--primary));
}
</style>
