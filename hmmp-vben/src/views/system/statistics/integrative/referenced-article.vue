<script lang="ts" setup>
/**
 * 被引文章查询 —— 按期刊检索已刊发文章的被引/自引/他引
 * 布局：标题 + 兄弟统计页导航 + 期刊名称筛选 + 表格（参考图字段）
 */
import type { StatisticsApi } from '#/api/biz/statistics';

import { computed, onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { message } from 'antdv-next';

import {
  exportReferencedArticleList,
  getReferencedArticleList,
} from '#/api/biz/statistics';
import {
  JOURNAL_NAME_LABEL,
  useJournalMagazine,
} from '#/composables/use-journal-magazine';

defineOptions({ name: 'StatisticsIntegrativeReferencedArticle' });

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

const { magazineOptions } = useJournalMagazine({ optionValue: 'default' });

/** 筛选行（对齐参考图：期刊名称 + 检索） */
const filters = reactive({
  magazineId: 'default',
});

const tableData = ref<StatisticsApi.ReferencedArticleRow[]>([]);
const tableLoading = ref(false);
const exporting = ref(false);
/** 是否已执行过检索（未检索时不展示空态文案误导） */
const hasSearched = ref(false);

const pagination = reactive({
  current: 1,
  pageSize: 20,
  total: 0,
  showSizeChanger: true,
  pageSizeOptions: ['10', '20', '50', '100'],
  showTotal: (total: number, range: [number, number]) =>
    `当前显示 ${range[0]} - ${range[1]} 条，共 ${total} 条记录`,
});

const columns = [
  { key: 'title', title: '标题', dataIndex: 'title', ellipsis: true, width: 280 },
  {
    key: 'authorNames',
    title: '作者信息',
    dataIndex: 'authorNames',
    ellipsis: true,
    width: 180,
  },
  { key: 'year', title: '年份', dataIndex: 'year', width: 80, align: 'center' as const },
  { key: 'period', title: '刊期', dataIndex: 'period', width: 80, align: 'center' as const },
  {
    key: 'pageStart',
    title: '起始页码',
    dataIndex: 'pageStart',
    width: 100,
    align: 'right' as const,
  },
  {
    key: 'pageEnd',
    title: '截止页码',
    dataIndex: 'pageEnd',
    width: 100,
    align: 'right' as const,
  },
  {
    key: 'citedCount',
    title: '被引次数',
    dataIndex: 'citedCount',
    width: 100,
    align: 'right' as const,
  },
  {
    key: 'selfCitedCount',
    title: '自引次数',
    dataIndex: 'selfCitedCount',
    width: 100,
    align: 'right' as const,
  },
  {
    key: 'otherCitedCount',
    title: '他引次数',
    dataIndex: 'otherCitedCount',
    width: 100,
    align: 'right' as const,
  },
];

function num(v?: number | null) {
  return v ?? 0;
}

function buildSearchParams(): StatisticsApi.ReferencedArticleListParams {
  return {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
    magazineId: filters.magazineId,
  };
}

async function loadTable() {
  tableLoading.value = true;
  hasSearched.value = true;
  try {
    const res = (await getReferencedArticleList(buildSearchParams())) as any;
    tableData.value = res?.rows ?? [];
    pagination.total = res?.total ?? 0;
  } catch {
    message.error('加载被引文章失败');
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

async function onExport() {
  exporting.value = true;
  try {
    const params = buildSearchParams() as Record<string, any>;
    delete params.pageNum;
    delete params.pageSize;
    await exportReferencedArticleList(params);
  } catch {
    message.error('导出失败');
  } finally {
    exporting.value = false;
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
        被引文章查询
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
      <!-- 筛选行：期刊名称 + 检索（对齐参考图） -->
      <div class="mb-3 flex flex-wrap items-center gap-2">
        <span class="filter-label">{{ JOURNAL_NAME_LABEL }}</span>
        <a-select
          v-model:value="filters.magazineId"
          :options="magazineOptions"
          style="width: 220px"
        />
        <a-button type="primary" @click="onSearch">检索</a-button>
      </div>

      <!-- 表格 -->
      <a-table
        :columns="columns"
        :data-source="tableData"
        :loading="tableLoading"
        :pagination="pagination"
        :scroll="{ x: 1100 }"
        row-key="articleId"
        size="middle"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #emptyText>
          <a-empty
            :description="
              hasSearched ? '未查询到任何数据信息！' : '请点击检索查询被引文章'
            "
          />
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'title'">
            <span class="text-primary">{{ record.title || '-' }}</span>
          </template>
          <template v-else-if="column.key === 'authorNames'">
            {{ record.authorNames || '-' }}
          </template>
          <template v-else-if="column.key === 'year' || column.key === 'period'">
            {{ record[column.dataIndex] ?? '-' }}
          </template>
          <template
            v-else-if="
              [
                'citedCount',
                'otherCitedCount',
                'pageEnd',
                'pageStart',
                'selfCitedCount',
              ].includes(column.key)
            "
          >
            <span class="tabular-nums">{{ num(record[column.dataIndex]) }}</span>
          </template>
        </template>
      </a-table>

      <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
        <a-button type="primary" :loading="exporting" @click="onExport">
          导出数据
        </a-button>
      </div>
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

.batch-actions :deep(.ant-btn) {
  min-width: 7rem;
}
</style>
