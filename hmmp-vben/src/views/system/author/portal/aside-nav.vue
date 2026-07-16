<script lang="ts" setup>
/**
 * 作者工作台左侧菜单 —— 与 /profile 同款布局与选中样式
 */
import { computed, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { VbenAvatar } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';
import { preferences } from '@vben/preferences';
import { useUserStore } from '@vben/stores';

defineOptions({ name: 'AuthorPortalNav' });

interface MenuItem {
  icon: string;
  label: string;
  path: string;
  value: string;
}

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

const menus: MenuItem[] = [
  {
    value: 'contribution',
    label: '提交新稿',
    icon: 'lucide:file-plus',
    path: '/author/contribution',
  },
  {
    value: 'revision',
    label: '待修改稿',
    icon: 'lucide:file-pen',
    path: '/author/revision',
  },
  {
    value: 'search',
    label: '稿件查询',
    icon: 'lucide:search',
    path: '/author/search',
  },
  {
    value: 'contact',
    label: '联系编审',
    icon: 'lucide:mail',
    path: '/author/contact',
  },
  {
    value: 'member',
    label: '修改资料',
    icon: 'lucide:user-round',
    path: '/author/member',
  },
];

const activeValue = ref('');

function resolveValueByPath(path: string) {
  let best: MenuItem | undefined;
  for (const item of menus) {
    if (path === item.path || path.startsWith(`${item.path}/`)) {
      if (!best || item.path.length > best.path.length) best = item;
    }
  }
  return best?.value ?? '';
}

watch(
  () => route.path,
  (path) => {
    activeValue.value = resolveValueByPath(path);
  },
  { immediate: true },
);

function onSelect(item: MenuItem) {
  activeValue.value = item.value;
  if (route.path !== item.path) {
    router.push(item.path);
  }
}

const avatar = computed(
  () => userStore.userInfo?.avatar ?? preferences.app.defaultAvatar,
);
const displayName = computed(
  () =>
    userStore.userInfo?.realName ||
    userStore.userInfo?.username ||
    '作者用户',
);
const userName = computed(() => userStore.userInfo?.username ?? '');
</script>

<template>
  <aside class="author-portal-nav flex h-full flex-col">
    <div
      class="border-border/60 flex cursor-pointer items-center gap-3 border-b px-4 py-4"
      @click="router.push('/author/home')"
    >
      <VbenAvatar :src="avatar" class="size-10 shrink-0" />
      <div class="min-w-0">
        <div class="truncate text-sm font-semibold">{{ displayName }}</div>
        <div class="text-muted-foreground truncate text-xs">{{ userName }}</div>
      </div>
    </div>

    <nav class="m-4 flex flex-col gap-1">
      <button
        v-for="item in menus"
        :key="item.value"
        type="button"
        class="menu-item"
        :class="{ active: activeValue === item.value }"
        @click="onSelect(item)"
      >
        <IconifyIcon :icon="item.icon" class="menu-item__icon size-4 shrink-0" />
        <span class="truncate">{{ item.label }}</span>
      </button>
    </nav>
  </aside>
</template>

<style scoped>
.menu-item {
  display: flex;
  height: 3rem;
  width: 100%;
  align-items: center;
  justify-content: flex-start;
  gap: 0.625rem;
  border: 0;
  border-radius: 0.375rem;
  background: transparent;
  padding: 0 0.75rem;
  color: hsl(var(--foreground));
  font-size: 0.875rem;
  line-height: 1.25rem;
  text-align: left;
  cursor: pointer;
  transition:
    background-color 0.15s ease,
    color 0.15s ease;
}

.menu-item__icon {
  opacity: 0.75;
}

.menu-item:hover {
  background: hsl(var(--muted) / 60%);
}

.menu-item.active {
  background: hsl(var(--primary));
  color: hsl(var(--primary-foreground));
  font-weight: 500;
}

.menu-item.active .menu-item__icon {
  opacity: 1;
}
</style>
