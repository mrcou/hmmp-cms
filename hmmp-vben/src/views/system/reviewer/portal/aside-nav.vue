<script lang="ts" setup>
/**
 * 审稿工作台左侧菜单 —— 与作者中心同款布局
 */
import { computed, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { VbenAvatar } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';
import { preferences } from '@vben/preferences';
import { useUserStore } from '@vben/stores';

defineOptions({ name: 'ReviewerPortalNav' });

interface MenuLeaf {
  icon: string;
  label: string;
  path: string;
  value: string;
}

interface MenuGroup {
  children: MenuLeaf[];
  icon: string;
  label: string;
  value: string;
}

type MenuEntry = MenuGroup | MenuLeaf;

function isGroup(item: MenuEntry): item is MenuGroup {
  return 'children' in item;
}

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

const menus: MenuEntry[] = [
  {
    value: 'home',
    label: '审稿首页',
    icon: 'lucide:house',
    path: '/reviewer/home',
  },
  {
    value: 'handle',
    label: '稿件处理',
    icon: 'lucide:files',
    children: [
      {
        value: 'new',
        label: '新收稿',
        icon: 'lucide:file-plus',
        path: '/reviewer/new',
      },
      {
        value: 'assign',
        label: '新分配',
        icon: 'lucide:user-plus',
        path: '/reviewer/assign',
      },
      {
        value: 'resubmitted',
        label: '已修回',
        icon: 'lucide:rotate-ccw',
        path: '/reviewer/resubmitted',
      },
    ],
  },
  {
    value: 'invite',
    label: '最新邀请审稿',
    icon: 'lucide:mail-plus',
    path: '/reviewer/invite',
  },
  {
    value: 'reviewing',
    label: '未审完稿件',
    icon: 'lucide:list-todo',
    path: '/reviewer/reviewing',
  },
  {
    value: 'reviewed',
    label: '已审完稿件',
    icon: 'lucide:list-checks',
    path: '/reviewer/reviewed',
  },
  {
    value: 'member',
    label: '修改资料',
    icon: 'lucide:user-round',
    path: '/reviewer/member',
  },
];

const activeValue = ref('');
const expandedGroups = ref<Record<string, boolean>>({ handle: true });

function allLeaves(): MenuLeaf[] {
  const leaves: MenuLeaf[] = [];
  for (const item of menus) {
    if (isGroup(item)) leaves.push(...item.children);
    else leaves.push(item);
  }
  return leaves;
}

function resolveValueByPath(path: string) {
  if (path === '/reviewer' || path === '/reviewer/home') return 'home';
  let best: MenuLeaf | undefined;
  for (const item of allLeaves()) {
    if (item.value === 'home') continue;
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
    for (const item of menus) {
      if (isGroup(item) && item.children.some((c) => c.value === activeValue.value)) {
        expandedGroups.value[item.value] = true;
      }
    }
  },
  { immediate: true },
);

function onSelect(item: MenuLeaf) {
  activeValue.value = item.value;
  if (route.path !== item.path) {
    router.push(item.path);
  }
}

function toggleGroup(group: MenuGroup) {
  expandedGroups.value[group.value] = !expandedGroups.value[group.value];
}

const avatar = computed(
  () => userStore.userInfo?.avatar ?? preferences.app.defaultAvatar,
);
const displayName = computed(
  () =>
    userStore.userInfo?.realName ||
    userStore.userInfo?.username ||
    '审稿用户',
);
const userName = computed(() => userStore.userInfo?.username ?? '');
</script>

<template>
  <aside class="reviewer-portal-nav flex h-full flex-col">
    <div
      class="border-border/60 flex cursor-pointer items-center gap-3 border-b px-4 py-4"
      @click="router.push('/reviewer/home')"
    >
      <VbenAvatar :src="avatar" class="size-10 shrink-0" />
      <div class="min-w-0">
        <div class="truncate text-sm font-semibold">{{ displayName }}</div>
        <div class="text-muted-foreground truncate text-xs">{{ userName }}</div>
      </div>
    </div>

    <nav class="m-4 flex flex-col gap-1">
      <template v-for="item in menus" :key="item.value">
        <template v-if="isGroup(item)">
          <button
            type="button"
            class="menu-item menu-item--group"
            @click="toggleGroup(item)"
          >
            <IconifyIcon :icon="item.icon" class="menu-item__icon size-4 shrink-0" />
            <span class="truncate">{{ item.label }}</span>
            <IconifyIcon
              icon="lucide:chevron-down"
              class="menu-item__chevron ml-auto size-3.5 shrink-0"
              :class="{ 'menu-item__chevron--open': expandedGroups[item.value] }"
            />
          </button>
          <div v-show="expandedGroups[item.value]" class="menu-sub">
            <button
              v-for="child in item.children"
              :key="child.value"
              type="button"
              class="menu-item menu-item--sub"
              :class="{ active: activeValue === child.value }"
              @click="onSelect(child)"
            >
              <IconifyIcon
                :icon="child.icon"
                class="menu-item__icon size-4 shrink-0"
              />
              <span class="truncate">{{ child.label }}</span>
            </button>
          </div>
        </template>
        <button
          v-else
          type="button"
          class="menu-item"
          :class="{ active: activeValue === item.value }"
          @click="onSelect(item)"
        >
          <IconifyIcon :icon="item.icon" class="menu-item__icon size-4 shrink-0" />
          <span class="truncate">{{ item.label }}</span>
        </button>
      </template>
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

.menu-item--sub {
  height: 2.5rem;
  padding-left: 1.75rem;
  font-size: 0.8125rem;
}

.menu-item__icon {
  opacity: 0.75;
}

.menu-item__chevron {
  opacity: 0.55;
  transition: transform 0.15s ease;
}

.menu-item__chevron--open {
  transform: rotate(180deg);
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

.menu-sub {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  margin-bottom: 0.25rem;
}
</style>
