<script lang="ts" setup>
/**
 * 审稿主页 —— 中间操作区：审稿概览 + 待办任务
 */
import { computed, onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';

import { preferences } from '@vben/preferences';
import { useUserStore } from '@vben/stores';

import { Tag } from 'antdv-next';

import * as authorApi from '#/api/biz/author';
import * as editorApi from '#/api/biz/editor';
import * as reviewerApi from '#/api/biz/reviewer';

defineOptions({ name: 'ReviewerPortalHome' });

const router = useRouter();
const userStore = useUserStore();

const loading = ref(false);
const recentList = ref<any[]>([]);
const stats = reactive({
  newReceipt: 0,
  assign: 0,
  resubmitted: 0,
  interact: 0,
});

const displayName = computed(
  () =>
    userStore.userInfo?.realName ||
    userStore.userInfo?.username ||
    '审稿人',
);

const statusMap: Record<string, { color: string; text: string }> = {
  '0': { color: 'blue', text: '待接受' },
  '1': { color: 'cyan', text: '已接受' },
  '2': { color: 'orange', text: '审稿中' },
  '3': { color: 'green', text: '已审完' },
  '4': { color: 'default', text: '已拒绝' },
};

const quickCards = [
  {
    key: 'newReceipt',
    label: '新收稿',
    path: '/reviewer/new',
  },
  {
    key: 'assign',
    label: '新分配',
    path: '/reviewer/assign',
  },
  {
    key: 'resubmitted',
    label: '已修回',
    path: '/reviewer/resubmitted',
  },
  {
    key: 'interact',
    label: '新互动',
    path: '/reviewer/messages',
  },
] as const;

async function loadDashboard() {
  loading.value = true;
  try {
    const [inviteRes, reviewingRes, msgRes, newRes, assignRes, revisedRes] =
      await Promise.all([
        reviewerApi.getNewInviteList({ pageNum: 1, pageSize: 20 }) as Promise<any>,
        reviewerApi.getReviewingList({ pageNum: 1, pageSize: 20 }) as Promise<any>,
        authorApi.getAuthorMessages({ pageNum: 1, pageSize: 5 }) as Promise<any>,
        editorApi.getManuscriptList({ status: '01', pageNum: 1, pageSize: 1 }) as Promise<any>,
        editorApi.getManuscriptList({ status: '02', pageNum: 1, pageSize: 1 }) as Promise<any>,
        editorApi.getManuscriptList({ status: '05', pageNum: 1, pageSize: 1 }) as Promise<any>,
      ]);
    const invites: any[] = inviteRes?.rows ?? inviteRes?.data ?? [];
    const reviewing: any[] = reviewingRes?.rows ?? reviewingRes?.data ?? [];
    const messages: any[] = msgRes?.rows ?? msgRes?.data ?? [];

    const pickTotal = (res: any) =>
      res?.total ?? res?.data?.total ?? 0;

    stats.newReceipt = pickTotal(newRes);
    stats.assign = pickTotal(assignRes);
    stats.resubmitted = pickTotal(revisedRes);
    stats.interact = msgRes?.total ?? messages.length;

    recentList.value = [
      ...invites.map((r) => ({ ...r, _status: r.taskStatus ?? '0' })),
      ...reviewing.map((r) => ({ ...r, _status: r.taskStatus ?? '2' })),
    ].slice(0, 20);
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
  <div class="reviewer-home flex min-h-full flex-col gap-3 p-1 md:p-0">
    <section class="home-card shrink-0">
      <div class="mb-4 flex items-end justify-between gap-3">
        <div>
          <h2 class="m-0 text-lg font-semibold">你好，{{ displayName }}</h2>
          <p class="text-muted-foreground mt-1 mb-0 text-sm">
            欢迎回到审稿工作台，从这里处理邀请与审稿任务。
          </p>
        </div>
        <img
          :src="userStore.userInfo?.avatar ?? preferences.app.defaultAvatar"
          alt=""
          class="hidden size-12 rounded-full object-cover sm:block"
        />
      </div>

      <div class="grid grid-cols-2 gap-3 md:grid-cols-4">
        <div
          v-for="card in quickCards"
          :key="card.key"
          class="stat-tile cursor-pointer"
          @click="go(card.path)"
        >
          <div class="text-muted-foreground text-xs">{{ card.label }}</div>
          <div class="stat-value">{{ stats[card.key] }}</div>
        </div>
      </div>
    </section>

    <section class="home-card recent-card flex min-h-0 flex-1 flex-col">
      <div class="mb-3 flex shrink-0 items-center justify-between">
        <h3 class="m-0 text-sm font-semibold">待办审稿</h3>
        <button
          type="button"
          class="text-primary cursor-pointer border-0 bg-transparent text-xs"
          @click="go('/reviewer/reviewing')"
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
          暂无待办，去「最新邀请审稿」查看是否有新任务。
        </div>
        <ul v-else class="divide-border/60 m-0 divide-y p-0">
          <li
            v-for="item in recentList"
            :key="item.taskId ?? item.manuscriptId"
            class="flex cursor-pointer items-start justify-between gap-3 py-3 first:pt-0 last:pb-0"
            @click="
              go(
                item._status === '0' || item._status === '1'
                  ? '/reviewer/invite'
                  : '/reviewer/reviewing',
              )
            "
          >
            <div class="min-w-0">
              <div class="truncate text-sm font-medium">
                {{ item.title || '未命名稿件' }}
              </div>
              <div class="text-muted-foreground mt-1 text-xs">
                {{ item.deadlineTime || item.acceptTime || '时间未知' }}
              </div>
            </div>
            <Tag :color="statusMeta(item._status).color" class="shrink-0">
              {{ statusMeta(item._status).text }}
            </Tag>
          </li>
        </ul>
      </div>
    </section>
  </div>
</template>

<style scoped>
.reviewer-home {
  min-height: 100%;
  height: 100%;
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
