<script lang="ts" setup>
/**
 * 作者端三栏工作台：左导航 / 中操作区 / 右消息区
 * 待修改稿、稿件查询隐藏右侧消息栏，中间区铺满
 */
import { computed } from 'vue';
import { RouterView, useRoute } from 'vue-router';

import AsideFeed from './aside-feed.vue';
import AsideNav from './aside-nav.vue';

defineOptions({ name: 'AuthorPortalLayout' });

const route = useRoute();

/** 这些页面不展示右侧消息栏 */
const HIDE_RIGHT_PATHS = ['/author/revision', '/author/search'];

const hideRight = computed(() =>
  HIDE_RIGHT_PATHS.some(
    (p) => route.path === p || route.path.startsWith(`${p}/`),
  ),
);

const isHome = computed(
  () => route.path === '/author/home' || route.path === '/author',
);
</script>

<template>
  <div class="author-portal">
    <div
      class="author-portal__frame"
      :class="{ 'author-portal__frame--no-right': hideRight }"
    >
      <div class="author-portal__left">
        <AsideNav />
      </div>
      <div
        class="author-portal__main"
        :class="{ 'author-portal__main--fill': isHome }"
      >
        <RouterView />
      </div>
      <div v-if="!hideRight" class="author-portal__right">
        <AsideFeed />
      </div>
    </div>
  </div>
</template>

<style scoped>
.author-portal {
  height: 100%;
  min-height: 0;
  background: #f3f4f6;
  padding: 0.75rem;
}

.dark .author-portal {
  background: hsl(var(--background));
}

.author-portal__frame {
  display: grid;
  grid-template-columns: 240px minmax(0, 1fr) 280px;
  gap: 0.75rem;
  height: 100%;
  min-height: calc(100vh - 7rem);
}

.author-portal__frame--no-right {
  grid-template-columns: 240px minmax(0, 1fr);
}

.author-portal__left,
.author-portal__right,
.author-portal__main {
  min-height: 0;
  overflow: hidden;
  border-radius: 0.75rem;
  background: hsl(var(--card));
  box-shadow: 0 1px 2px hsl(var(--foreground) / 4%);
}

.author-portal__main {
  overflow: auto;
  background: transparent;
  box-shadow: none;
}

.author-portal__main--fill {
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.author-portal__main--fill > :deep(*) {
  flex: 1;
  min-height: 0;
  height: 100%;
}

@media (max-width: 1100px) {
  .author-portal__frame {
    grid-template-columns: 200px minmax(0, 1fr);
  }

  .author-portal__right {
    display: none;
  }
}

@media (max-width: 640px) {
  .author-portal__frame {
    grid-template-columns: 1fr;
  }
}
</style>
