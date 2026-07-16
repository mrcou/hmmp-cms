<script lang="ts" setup>
/**
 * 审稿端三栏工作台：左导航 / 中操作区 / 右消息区
 */
import { computed } from 'vue';
import { RouterView, useRoute } from 'vue-router';

import AsideFeed from './aside-feed.vue';
import AsideNav from './aside-nav.vue';

defineOptions({ name: 'ReviewerPortalLayout' });

const route = useRoute();

/** 仅首页展示右侧消息栏（三栏） */
const isHome = computed(
  () => route.path === '/reviewer/home' || route.path === '/reviewer',
);
</script>

<template>
  <div class="reviewer-portal">
    <div
      class="reviewer-portal__frame"
      :class="{ 'reviewer-portal__frame--no-right': !isHome }"
    >
      <div class="reviewer-portal__left">
        <AsideNav />
      </div>
      <div
        class="reviewer-portal__main"
        :class="{ 'reviewer-portal__main--fill': isHome }"
      >
        <RouterView />
      </div>
      <div v-if="isHome" class="reviewer-portal__right">
        <AsideFeed />
      </div>
    </div>
  </div>
</template>

<style scoped>
.reviewer-portal {
  height: 100%;
  min-height: 0;
  background: #f3f4f6;
  padding: 0.75rem;
}

.dark .reviewer-portal {
  background: hsl(var(--background));
}

.reviewer-portal__frame {
  display: grid;
  grid-template-columns: 240px minmax(0, 1fr) 280px;
  gap: 0.75rem;
  height: 100%;
  min-height: calc(100vh - 7rem);
}

.reviewer-portal__frame--no-right {
  grid-template-columns: 240px minmax(0, 1fr);
}

.reviewer-portal__left,
.reviewer-portal__right,
.reviewer-portal__main {
  min-height: 0;
  overflow: hidden;
  border-radius: 0.75rem;
  background: hsl(var(--card));
  box-shadow: 0 1px 2px hsl(var(--foreground) / 4%);
}

.reviewer-portal__main {
  overflow: auto;
  background: transparent;
  box-shadow: none;
}

.reviewer-portal__main--fill {
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.reviewer-portal__main--fill > :deep(*) {
  flex: 1;
  min-height: 0;
  height: 100%;
}

@media (max-width: 1100px) {
  .reviewer-portal__frame {
    grid-template-columns: 200px minmax(0, 1fr);
  }

  .reviewer-portal__right {
    display: none;
  }
}

@media (max-width: 640px) {
  .reviewer-portal__frame {
    grid-template-columns: 1fr;
  }
}
</style>
