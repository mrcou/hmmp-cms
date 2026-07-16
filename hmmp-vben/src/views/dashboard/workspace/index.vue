<script lang="ts" setup>
import type {
  WorkbenchProjectItem,
  WorkbenchTodoItem,
  WorkbenchTrendItem,
} from '@vben/common-ui';
import type { EditorApi } from '#/api/biz/editor';

import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

import {
  WorkbenchHeader,
  WorkbenchProject,
  WorkbenchTodo,
  WorkbenchTrends,
} from '@vben/common-ui';
import { preferences } from '@vben/preferences';
import { useUserStore } from '@vben/stores';
import { openWindow } from '@vben/utils';

import * as editorApi from '#/api/biz/editor';

const userStore = useUserStore();
const router = useRouter();

const projectItems: WorkbenchProjectItem[] = [
  {
    color: '#2563eb',
    content: '收稿登记、稿件处理、审稿管理、编辑加工与组稿签发。',
    date: '/editor/manuscript/register',
    group: '采编业务',
    icon: 'lucide:file-edit',
    title: '采编中心',
    url: '/editor/manuscript/register',
  },
  {
    color: '#0f766e',
    content: '维护期年期次、栏目文章、读者订阅、订单发票与寄送。',
    date: '/publisher/issue/year',
    group: '出版发行',
    icon: 'lucide:newspaper',
    title: '发行中心',
    url: '/publisher/issue/year',
  },
  {
    color: '#db2777',
    content: '提交新稿、修回处理、稿件查询、联系编审与资料维护。',
    date: '/author/home',
    group: '作者服务',
    icon: 'lucide:pen-line',
    title: '作者中心',
    url: '/author/home',
  },
  {
    color: '#ea580c',
    content: '处理审稿邀请、待审任务、未审完稿件和已审完记录。',
    date: '/reviewer/home',
    group: '审稿协同',
    icon: 'lucide:clipboard-check',
    title: '审稿中心',
    url: '/reviewer/home',
  },
  {
    color: '#475569',
    content: '维护用户、角色、菜单、字典、参数设置和通知公告。',
    date: '/system/user',
    group: '基础配置',
    icon: 'lucide:settings',
    title: '系统设置',
    url: '/system/user',
  },
  {
    color: '#16a34a',
    content: '查看期刊运营、采编效率、发行数据、审稿周期和站点访问。',
    date: '/statistics/integrative/journal',
    group: '数据看板',
    icon: 'lucide:chart-no-axes-combined',
    title: '统计中心',
    url: '/statistics/integrative/journal',
  },
];

const todoItems = ref<WorkbenchTodoItem[]>([
  // TODO: 待办事项后续作为统一消息系统入口，需要先明确消息来源、推送通道、未读状态和处理回执，再接实时接口。
  {
    completed: false,
    content: '处理作者咨询、审稿沟通和系统消息中的未回复事项。',
    date: '今日',
    title: '待回复',
  },
  {
    completed: false,
    content: '跟进新收稿件、审稿邀请和审稿中的稿件进度。',
    date: '今日',
    title: '待审稿',
  },
  {
    completed: false,
    content: '核对已采用稿件的期号、排版、栏目和发布准备。',
    date: '本周',
    title: '待发布',
  },
]);

const emptyTrend: WorkbenchTrendItem = {
  avatar: 'lucide:file-search',
  content: '当前没有新提交的稿件。',
  date: '实时',
  title: '暂无稿件',
};

const trendItems = ref<WorkbenchTrendItem[]>([emptyTrend]);
const manuscriptStats = ref({
  adopted: 0,
  pending: 0,
  reviewing: 0,
});
const manuscriptStatsLoading = ref(true);

const statItems = computed(() => [
  {
    label: '待审稿件',
    path: '/editor/manuscript/new',
    value: manuscriptStatsLoading.value ? '--' : manuscriptStats.value.pending,
  },
  {
    label: '审稿中',
    path: '/editor/manuscript/reviewing',
    value: manuscriptStatsLoading.value
      ? '--'
      : manuscriptStats.value.reviewing,
  },
  {
    label: '已采用',
    path: '/editor/manuscript/adopted',
    value: manuscriptStatsLoading.value ? '--' : manuscriptStats.value.adopted,
  },
]);

const weatherText = ref('正在获取实时天气...');

const weatherCodeMap: Record<number, string> = {
  0: '晴',
  1: '晴间多云',
  2: '局部多云',
  3: '阴',
  45: '雾',
  48: '雾凇',
  51: '小毛毛雨',
  53: '毛毛雨',
  55: '大毛毛雨',
  56: '冻毛毛雨',
  57: '强冻毛毛雨',
  61: '小雨',
  63: '中雨',
  65: '大雨',
  66: '冻雨',
  67: '强冻雨',
  71: '小雪',
  73: '中雪',
  75: '大雪',
  77: '雪粒',
  80: '阵雨',
  81: '强阵雨',
  82: '暴雨',
  85: '阵雪',
  86: '强阵雪',
  95: '雷暴',
  96: '雷暴伴冰雹',
  99: '强雷暴伴冰雹',
};

interface WeatherLocation {
  label: string;
  latitude: number;
  longitude: number;
}

interface WeatherResponse {
  current?: {
    temperature_2m?: number;
    weather_code?: number;
    wind_speed_10m?: number;
  };
  daily?: {
    temperature_2m_max?: number[];
    temperature_2m_min?: number[];
    weather_code?: number[];
  };
}

interface TableLike<T> {
  data?: TableLike<T>;
  list?: T[];
  rows?: T[];
  total?: number;
}

function navTo(nav: WorkbenchProjectItem) {
  if (nav.url?.startsWith('http')) {
    openWindow(nav.url);
    return;
  }
  if (nav.url?.startsWith('/')) {
    router.push(nav.url).catch((error) => {
      console.error('Navigation failed:', error);
    });
  } else {
    console.warn(`Unknown URL for navigation item: ${nav.title} -> ${nav.url}`);
  }
}

function go(path: string) {
  router.push(path).catch((error) => {
    console.error('Navigation failed:', error);
  });
}

function extractTable<T>(response: TableLike<T>) {
  const data = response?.data ?? response ?? {};
  const rows = data.rows ?? data.list ?? [];
  return {
    rows,
    total: Number(data.total ?? rows.length),
  };
}

function escapeHtml(value: string) {
  return value.replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;')
    .replaceAll("'", '&#39;');
}

function getStatusLabel(status?: string) {
  const labels: Record<string, string> = {
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
  return labels[status ?? ''] ?? '待处理';
}

function formatDateTime(value?: string) {
  if (!value) {
    return '刚刚';
  }
  return value.replace('T', ' ').slice(0, 16);
}

function toTrendItem(item: EditorApi.Manuscript): WorkbenchTrendItem {
  const title = escapeHtml(item.title || '未命名稿件');
  const authors = item.authorNames
    ? `作者：${escapeHtml(item.authorNames)}`
    : '作者：未填写';
  const status = escapeHtml(getStatusLabel(item.status));
  const fileNo = item.fileNo || `稿件 #${item.manuscriptId ?? title}`;

  return {
    avatar: 'lucide:file-text',
    content: `<a>${title}</a> · ${authors} · ${status}`,
    date: formatDateTime(item.receiveTime || item.createTime),
    title: fileNo,
  };
}

async function loadStatusCount(status: string) {
  const response = await editorApi.getManuscriptList({
    pageNum: 1,
    pageSize: 1,
    status,
  });
  return extractTable<EditorApi.Manuscript>(response).total;
}

async function loadManuscriptDashboard() {
  manuscriptStatsLoading.value = true;
  try {
    const [pendingResponse, reviewing, adopted] = await Promise.all([
      editorApi.getManuscriptList({ status: '01' }),
      loadStatusCount('03'),
      loadStatusCount('06'),
    ]);
    const pending = extractTable<EditorApi.Manuscript>(pendingResponse);

    manuscriptStats.value = {
      adopted,
      pending: pending.total,
      reviewing,
    };
    trendItems.value =
      pending.rows.length > 0 ? pending.rows.map(toTrendItem) : [emptyTrend];
  } catch (error) {
    console.error('Failed to load manuscript dashboard:', error);
    trendItems.value = [
      {
        avatar: 'lucide:wifi-off',
        content: '稿件列表暂时无法获取，请检查接口权限或服务状态。',
        date: '实时',
        title: '加载失败',
      },
    ];
  } finally {
    manuscriptStatsLoading.value = false;
  }
}

function resolveBrowserLocation(): Promise<WeatherLocation> {
  const fallback = {
    label: '西安',
    latitude: 34.3416,
    longitude: 108.9398,
  };

  return new Promise((resolve) => {
    if (!navigator.geolocation) {
      resolve(fallback);
      return;
    }

    navigator.geolocation.getCurrentPosition(
      ({ coords }) => {
        resolve({
          label: '当前位置',
          latitude: coords.latitude,
          longitude: coords.longitude,
        });
      },
      () => resolve(fallback),
      {
        maximumAge: 10 * 60 * 1000,
        timeout: 3500,
      },
    );
  });
}

function formatTemp(value?: number) {
  return typeof value === 'number' ? `${Math.round(value)}℃` : '--℃';
}

function getWeatherLabel(code?: number) {
  return typeof code === 'number' ? (weatherCodeMap[code] ?? '天气更新') : '天气更新';
}

async function loadWeather() {
  try {
    const location = await resolveBrowserLocation();
    const params = new URLSearchParams({
      current: 'temperature_2m,weather_code,wind_speed_10m',
      daily: 'weather_code,temperature_2m_max,temperature_2m_min',
      forecast_days: '3',
      latitude: `${location.latitude}`,
      longitude: `${location.longitude}`,
      timezone: 'auto',
    });
    const response = await fetch(
      `https://api.open-meteo.com/v1/forecast?${params.toString()}`,
    );

    if (!response.ok) {
      throw new Error(`Weather request failed: ${response.status}`);
    }

    const data = (await response.json()) as WeatherResponse;
    const currentCode = data.current?.weather_code ?? data.daily?.weather_code?.[0];
    const todayMin = data.daily?.temperature_2m_min?.[0];
    const todayMax = data.daily?.temperature_2m_max?.[0];
    const tomorrowCode = data.daily?.weather_code?.[1];
    const tomorrowMin = data.daily?.temperature_2m_min?.[1];
    const tomorrowMax = data.daily?.temperature_2m_max?.[1];
    const windSpeed = data.current?.wind_speed_10m;

    weatherText.value =
      `${location.label} ${getWeatherLabel(currentCode)}，当前 ${formatTemp(data.current?.temperature_2m)}` +
      `，今日 ${formatTemp(todayMin)} - ${formatTemp(todayMax)}` +
      (typeof windSpeed === 'number' ? `，风速 ${Math.round(windSpeed)}km/h` : '') +
      `；明日 ${getWeatherLabel(tomorrowCode)} ${formatTemp(tomorrowMin)} - ${formatTemp(tomorrowMax)}。`;
  } catch (error) {
    console.error('Failed to load weather:', error);
    weatherText.value = '天气预报暂时无法获取，请检查网络或浏览器定位权限。';
  }
}

onMounted(() => {
  loadWeather();
  loadManuscriptDashboard();
});
</script>

<template>
  <div class="dashboard-page p-5">
    <WorkbenchHeader
      :avatar="userStore.userInfo?.avatar || preferences.app.defaultAvatar"
    >
      <template #title>
        欢迎回来，{{ userStore.userInfo?.realName || '编辑老师' }}，今天从稿件流转开始。
      </template>
      <template #description>
        {{ weatherText }}
      </template>
      <template #actions>
        <button
          v-for="stat in statItems"
          :key="stat.label"
          class="mx-4 flex flex-col justify-center rounded-md px-3 py-2 text-right transition-colors hover:bg-accent md:mx-6"
          type="button"
          @click="go(stat.path)"
        >
          <span class="text-foreground/80">{{ stat.label }}</span>
          <span class="text-2xl">{{ stat.value }}</span>
        </button>
      </template>
    </WorkbenchHeader>

    <div class="dashboard-body mt-5">
      <div class="dashboard-main-grid">
        <div class="dashboard-left-stack">
          <WorkbenchProject
            :items="projectItems"
            class="dashboard-quick-menu"
            title="快捷菜单"
            @click="navTo"
          >
            <template #content="{ item }">
              <div class="text-foreground/80 mt-3 flex min-h-8 text-sm">
                {{ item.content }}
              </div>
            </template>
            <template #footer></template>
          </WorkbenchProject>

          <WorkbenchTrends
            :items="trendItems"
            class="dashboard-message-card dashboard-trends-card"
            title="最新稿件"
          />
        </div>

        <WorkbenchTodo
          :items="todoItems"
          class="dashboard-message-card dashboard-todo-card"
          title="待办事项"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.dashboard-page {
  display: flex;
  min-height: calc(100vh - 112px);
  flex-direction: column;
}

.dashboard-body {
  display: flex;
  flex: 1;
  min-height: 0;
  flex-direction: column;
  gap: 20px;
}

.dashboard-main-grid {
  display: grid;
  flex: 1;
  min-height: 620px;
  grid-template-columns: minmax(0, 3fr) minmax(320px, 2fr);
  gap: 20px;
}

.dashboard-left-stack {
  display: flex;
  min-height: 0;
  flex-direction: column;
  gap: 20px;
}

.dashboard-quick-menu {
  gap: 16px;
  padding-top: 20px;
  padding-bottom: 20px;
}

.dashboard-quick-menu :deep(.border-border.group) {
  padding: 12px;
}

.dashboard-quick-menu :deep([data-slot='card-header']) {
  padding-right: 20px;
  padding-left: 20px;
}

.dashboard-message-card {
  height: 100%;
  min-height: 0;
}

.dashboard-trends-card {
  flex: 1;
}

.dashboard-todo-card {
  align-self: stretch;
}

.dashboard-message-card :deep([data-slot='card-content']) {
  flex: 1;
  min-height: 0;
  overflow: auto;
}

@media (max-width: 1023px) {
  .dashboard-page {
    min-height: auto;
  }

  .dashboard-main-grid {
    min-height: auto;
    grid-template-columns: 1fr;
  }

  .dashboard-message-card {
    min-height: 360px;
  }
}
</style>
