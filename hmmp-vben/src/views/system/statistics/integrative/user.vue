<script lang="ts" setup>
/**
 * 用户统计 —— 按系统用户聚合其投稿（作者）与审稿（审稿人）数据
 * 布局：标题 + 兄弟统计页导航 + 筛选行 + 表格 + 底部按钮（导出/移除/审稿统计/投稿统计/显示设置）
 */
import type { StatisticsApi } from '#/api/biz/statistics';

import { computed, onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { message, Modal } from 'antdv-next';

import { exportUserStatList, getUserStatList } from '#/api/biz/statistics';

defineOptions({ name: 'StatisticsIntegrativeUser' });

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
  searchField: 'userName' as 'email' | 'realName' | 'userName',
  searchValue: '',
  dateRange: null as [string, string] | null,
  role: '' as '' | 'author' | 'reviewer',
});

const searchFieldOptions = [
  { value: 'userName', label: '用户名' },
  { value: 'realName', label: '姓名' },
  { value: 'email', label: 'E-mail' },
];

/** 表格 */
const tableData = ref<StatisticsApi.UserStatRow[]>([]);
const tableLoading = ref(false);
const exporting = ref(false);
const selectedKeys = ref<number[]>([]);
/** 已在当前视图移除的用户ID（前端过滤，不影响后端数据） */
const removedIds = ref<Set<number>>(new Set());

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
  { key: 'userName', title: '用户名', dataIndex: 'userName', width: 130 },
  { key: 'realName', title: '姓名', dataIndex: 'realName', width: 110 },
  { key: 'affiliation', title: '单位', dataIndex: 'affiliation', ellipsis: true, width: 200 },
  { key: 'email', title: 'E-mail', dataIndex: 'email', ellipsis: true, width: 200 },
  { key: 'submissionCount', title: '稿件数', dataIndex: 'submissionCount', width: 90, align: 'right' as const },
  { key: 'publishCount', title: '发表', dataIndex: 'publishCount', width: 80, align: 'right' as const },
  { key: 'reviewManuscriptCount', title: '审稿篇数', dataIndex: 'reviewManuscriptCount', width: 100, align: 'right' as const },
  { key: 'reviewTimes', title: '审稿次数', dataIndex: 'reviewTimes', width: 100, align: 'right' as const },
  { key: 'completedReviewCount', title: '已审回', dataIndex: 'completedReviewCount', width: 90, align: 'right' as const },
  { key: 'reviewScoreAvg', title: '审稿得分', dataIndex: 'reviewScoreAvg', width: 100, align: 'right' as const },
  { key: 'acceptedCount', title: '接收次数', dataIndex: 'acceptedCount', width: 100, align: 'right' as const },
];

const visibleColumnKeys = ref(allColumns.map((c) => c.key));
const columnDrawerOpen = ref(false);

const columns = computed(() => {
  const visible = new Set(visibleColumnKeys.value);
  return allColumns.filter((c) => visible.has(c.key));
});

const displayData = computed(() =>
  tableData.value.filter((r) => !removedIds.value.has(r.userId ?? -1)),
);

const rowSelection = computed(() => ({
  selectedRowKeys: selectedKeys.value,
  onChange: (keys: (number | string)[]) => {
    selectedKeys.value = keys.map(Number);
  },
}));

function num(v?: number | null) {
  return v ?? 0;
}

function buildSearchParams(): StatisticsApi.UserStatListParams {
  const params: StatisticsApi.UserStatListParams = {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  };
  if (filters.searchValue.trim()) {
    params[filters.searchField] = filters.searchValue.trim();
  }
  if (filters.role) params.role = filters.role;
  if (filters.dateRange?.[0] && filters.dateRange?.[1]) {
    params.beginTime = filters.dateRange[0];
    params.endTime = filters.dateRange[1];
  }
  return params;
}

async function loadTable() {
  tableLoading.value = true;
  try {
    const res = (await getUserStatList(buildSearchParams())) as any;
    tableData.value = res?.rows ?? [];
    pagination.total = res?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载用户统计失败');
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

/** 角色快捷统计：再次点击取消 */
function toggleRole(role: 'author' | 'reviewer') {
  filters.role = filters.role === role ? '' : role;
  onSearch();
}

/** 底部按钮 */
async function onExport() {
  exporting.value = true;
  try {
    const params = buildSearchParams() as Record<string, any>;
    delete params.pageNum;
    delete params.pageSize;
    await exportUserStatList(params);
  } catch {
    message.error('导出失败');
  } finally {
    exporting.value = false;
  }
}

/** 从当前视图移除选中行（仅前端隐藏，不删除后端数据） */
function onRemoveSelected() {
  if (selectedKeys.value.length === 0) {
    message.warning('请先勾选要移除的行');
    return;
  }
  Modal.confirm({
    title: '移除确认',
    content: `确认从当前列表移除选中的 ${selectedKeys.value.length} 条记录？（仅隐藏显示，不影响用户数据）`,
    okText: '移除',
    cancelText: '取消',
    onOk() {
      for (const id of selectedKeys.value) removedIds.value.add(id);
      selectedKeys.value = [];
    },
  });
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
        用户统计
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
      <div class="mb-3 flex flex-wrap items-center gap-2">
        <span class="filter-label">投稿/审稿时间</span>
        <a-range-picker
          v-model:value="filters.dateRange"
          value-format="YYYY-MM-DD"
          style="width: 260px"
          @change="onSearch"
        />
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
        :data-source="displayData"
        :loading="tableLoading"
        :pagination="pagination"
        :row-selection="rowSelection"
        :scroll="{ x: 1200 }"
        row-key="userId"
        size="middle"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #emptyText>
          <a-empty description="未查询到相关信息！" />
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'userName'">
            <span class="text-primary">{{ record.userName }}</span>
          </template>
          <template v-else-if="column.key === 'affiliation'">
            {{ record.affiliation || '-' }}
          </template>
          <template v-else-if="column.key === 'email'">
            {{ record.email || '-' }}
          </template>
          <template v-else-if="column.key === 'reviewScoreAvg'">
            <span class="tabular-nums">{{ record.reviewScoreAvg ?? 0 }}</span>
          </template>
          <template
            v-else-if="[
              'acceptedCount',
              'completedReviewCount',
              'publishCount',
              'reviewManuscriptCount',
              'reviewTimes',
              'submissionCount',
            ].includes(column.key)"
          >
            <span class="tabular-nums">{{ num(record[column.dataIndex]) }}</span>
          </template>
        </template>
      </a-table>

      <!-- 表格下方按钮行 -->
      <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
        <a-button type="primary" :loading="exporting" @click="onExport">
          导出数据
        </a-button>
        <a-button danger @click="onRemoveSelected">删除</a-button>
        <a-button
          type="primary"
          :ghost="filters.role !== 'reviewer'"
          @click="toggleRole('reviewer')"
        >
          审稿统计
        </a-button>
        <a-button
          type="primary"
          :ghost="filters.role !== 'author'"
          @click="toggleRole('author')"
        >
          投稿统计
        </a-button>
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

.filter-label {
  color: hsl(var(--foreground) / 80%);
  font-size: 0.8125rem;
  white-space: nowrap;
}

.batch-actions :deep(.ant-btn) {
  min-width: 7rem;
}
</style>
