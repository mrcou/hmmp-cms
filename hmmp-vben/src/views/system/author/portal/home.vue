<script lang="ts" setup>
/**
 * 作者主页 —— 中间操作区：数据概览 + 最近稿件（撑满剩余高度）
 */
import { computed, onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';

import { preferences } from '@vben/preferences';
import { useUserStore } from '@vben/stores';

import { Button, Tag } from 'antdv-next';

import * as authorApi from '#/api/biz/author';

defineOptions({ name: 'AuthorPortalHome' });

const router = useRouter();
const userStore = useUserStore();

const loading = ref(false);
const recentList = ref<any[]>([]);
const stats = reactive({
  total: 0,
  revision: 0,
  reviewing: 0,
  published: 0,
});

const displayName = computed(
  () =>
    userStore.userInfo?.realName ||
    userStore.userInfo?.username ||
    '作者',
);

const statusMap: Record<string, { color: string; text: string }> = {
  pending: { color: 'blue', text: '待审核' },
  revision: { color: 'orange', text: '待修改' },
  reviewing: { color: 'purple', text: '审稿中' },
  approved: { color: 'green', text: '已通过' },
  rejected: { color: 'red', text: '已拒绝' },
  published: { color: 'cyan', text: '已发表' },
};

async function loadDashboard() {
  loading.value = true;
  try {
    const res = (await authorApi.getSubmissionList({
      pageNum: 1,
      pageSize: 50,
    })) as any;
    const rows: any[] = res?.rows ?? res?.data ?? [];
    recentList.value = rows.slice(0, 20);
    stats.total = res?.total ?? rows.length;
    stats.revision = rows.filter((r) => r.status === 'revision').length;
    stats.reviewing = rows.filter((r) => r.status === 'reviewing').length;
    stats.published = rows.filter((r) => r.status === 'published').length;
  } catch {
    recentList.value = [];
  } finally {
    loading.value = false;
  }
}

function go(path: string) {
  router.push(path);
}

function statusMeta(status?: string) {
  return statusMap[status || ''] || { color: 'default', text: status || '-' };
}

onMounted(loadDashboard);
</script>

<template>
  <div class="author-home flex min-h-full flex-col gap-3 p-1 md:p-0">
    <section
      v-if="!userStore.userInfo?.realName"
      class="tip-banner flex shrink-0 flex-wrap items-center justify-between gap-3"
    >
      <div class="text-sm">
        建议完善账户资料，便于编辑部与您联系并顺利推进审稿流程。
      </div>
      <Button type="primary" size="small" @click="go('/author/member')">
        立即完善
      </Button>
    </section>

    <section class="home-card shrink-0">
      <div class="mb-4 flex items-end justify-between gap-3">
        <div>
          <h2 class="m-0 text-lg font-semibold">你好，{{ displayName }}</h2>
          <p class="text-muted-foreground mt-1 mb-0 text-sm">
            欢迎回到作者工作台，从这里管理投稿与账户信息。
          </p>
        </div>
        <img
          :src="userStore.userInfo?.avatar ?? preferences.app.defaultAvatar"
          alt=""
          class="hidden size-12 rounded-full object-cover sm:block"
        />
      </div>

      <div class="grid grid-cols-2 gap-3 md:grid-cols-4">
        <div class="stat-tile">
          <div class="text-muted-foreground text-xs">全部稿件</div>
          <div class="stat-value">{{ stats.total }}</div>
        </div>
        <div class="stat-tile">
          <div class="text-muted-foreground text-xs">待修改</div>
          <div class="stat-value">{{ stats.revision }}</div>
        </div>
        <div class="stat-tile">
          <div class="text-muted-foreground text-xs">审稿中</div>
          <div class="stat-value">{{ stats.reviewing }}</div>
        </div>
        <div class="stat-tile">
          <div class="text-muted-foreground text-xs">已发表</div>
          <div class="stat-value">{{ stats.published }}</div>
        </div>
      </div>
    </section>

    <section class="home-card recent-card flex min-h-0 flex-1 flex-col">
      <div class="mb-3 flex shrink-0 items-center justify-between">
        <h3 class="m-0 text-sm font-semibold">最近稿件</h3>
        <button
          type="button"
          class="text-primary cursor-pointer border-0 bg-transparent text-xs"
          @click="go('/author/search')"
        >
          查看全部
        </button>
      </div>

      <div class="recent-body min-h-0 flex-1 overflow-y-auto">
        <div
          v-if="loading"
          class="text-muted-foreground flex h-full items-center justify-center py-8 text-sm"
        >
          加载中…
        </div>
        <div
          v-else-if="!recentList.length"
          class="text-muted-foreground flex h-full items-center justify-center py-8 text-sm"
        >
          暂无稿件，去「提交新稿」开始第一篇投稿吧。
        </div>
        <ul v-else class="divide-border/60 m-0 divide-y p-0">
          <li
            v-for="item in recentList"
            :key="item.submissionId"
            class="flex cursor-pointer items-start justify-between gap-3 py-3 first:pt-0 last:pb-0"
            @click="go('/author/search')"
          >
            <div class="min-w-0">
              <div class="truncate text-sm font-medium">
                {{ item.title || '未命名稿件' }}
              </div>
              <div class="text-muted-foreground mt-1 text-xs">
                {{ item.submissionTime || '时间未知' }}
                <span v-if="item.keywords"> · {{ item.keywords }}</span>
              </div>
            </div>
            <Tag :color="statusMeta(item.status).color" class="shrink-0">
              {{ statusMeta(item.status).text }}
            </Tag>
          </li>
        </ul>
      </div>
    </section>
  </div>
</template>

<style scoped>
.author-home {
  /* 与三栏主区同高，最近稿件 flex-1 吃掉剩余空间 */
  min-height: 100%;
  height: 100%;
}

.tip-banner {
  border-radius: 0.75rem;
  background: hsl(var(--primary) / 8%);
  padding: 0.75rem 1rem;
}

.home-card {
  border-radius: 0.75rem;
  background: hsl(var(--card));
  padding: 1rem 1.125rem;
  box-shadow: 0 1px 2px hsl(var(--foreground) / 4%);
}

.recent-card {
  min-height: 0;
}

.stat-tile {
  border-radius: 0.625rem;
  background: hsl(var(--muted) / 45%);
  padding: 0.75rem 0.875rem;
}

.stat-value {
  margin-top: 0.35rem;
  font-size: 1.5rem;
  font-weight: 700;
  line-height: 1.2;
}
</style>
