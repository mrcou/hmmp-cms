<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

import { IconifyIcon } from '@vben/icons';

import * as authorApi from '#/api/biz/author';

defineOptions({ name: 'ReviewerPortalFeed' });

const router = useRouter();
const loading = ref(false);
const messages = ref<any[]>([]);

const tips = [
  {
    title: '请按时完成审稿',
    desc: '收到邀请后请尽快接受或婉拒；接受后请在截止日前提交意见。',
  },
  {
    title: '意见请客观具体',
    desc: '审稿结论需附具体修改建议，便于作者与编辑部处理。',
  },
  {
    title: '注意利益冲突',
    desc: '若与作者存在利益冲突，请及时拒绝邀请并说明原因。',
  },
];

async function loadMessages() {
  loading.value = true;
  try {
    // 暂复用作者消息接口；后续可对接审稿人独立消息源
    const res = (await authorApi.getAuthorMessages({
      pageNum: 1,
      pageSize: 5,
    })) as any;
    messages.value = res?.rows ?? res?.data ?? [];
  } catch {
    messages.value = [];
  } finally {
    loading.value = false;
  }
}

function goMessages() {
  router.push('/reviewer/messages');
}

onMounted(loadMessages);
</script>

<template>
  <aside class="reviewer-portal-feed flex h-full flex-col gap-3 overflow-y-auto p-3">
    <section class="feed-card">
      <div class="feed-card__head">
        <h3>最新消息</h3>
        <button type="button" class="link-btn" @click="goMessages">更多</button>
      </div>
      <div v-if="loading" class="text-muted-foreground px-1 py-6 text-center text-sm">
        加载中…
      </div>
      <ul v-else-if="messages.length" class="space-y-3">
        <li
          v-for="(item, index) in messages"
          :key="item.msgId ?? index"
          class="border-border/50 border-b pb-3 last:border-0 last:pb-0"
        >
          <div class="line-clamp-1 text-sm font-medium">
            {{ item.title || '系统通知' }}
          </div>
          <div class="text-muted-foreground mt-1 line-clamp-2 text-xs leading-relaxed">
            {{ item.content || '暂无内容' }}
          </div>
          <div class="text-muted-foreground mt-1.5 text-xs">
            {{ item.sendTime || '' }}
          </div>
        </li>
      </ul>
      <div v-else class="text-muted-foreground px-1 py-6 text-center text-sm">
        暂无新消息
      </div>
    </section>

    <section class="feed-card">
      <div class="feed-card__head">
        <h3>审稿须知</h3>
      </div>
      <ul class="space-y-3">
        <li v-for="(tip, index) in tips" :key="index" class="flex gap-2">
          <IconifyIcon
            icon="lucide:lightbulb"
            class="text-primary mt-0.5 size-4 shrink-0"
          />
          <div>
            <div class="text-sm font-medium">{{ tip.title }}</div>
            <div class="text-muted-foreground mt-0.5 text-xs leading-relaxed">
              {{ tip.desc }}
            </div>
          </div>
        </li>
      </ul>
    </section>
  </aside>
</template>

<style scoped>
.feed-card {
  border-radius: 0.75rem;
  background: hsl(var(--card));
  padding: 0.875rem 1rem;
  box-shadow: 0 1px 2px hsl(var(--foreground) / 4%);
}

.feed-card__head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.75rem;
}

.feed-card__head h3 {
  margin: 0;
  font-size: 0.875rem;
  font-weight: 600;
}

.link-btn {
  border: 0;
  background: transparent;
  color: hsl(var(--primary));
  font-size: 0.75rem;
  cursor: pointer;
}
</style>
