<script lang="ts" setup>
import type { NotificationItem } from '@vben/layouts';
import type { SystemNoticeApi } from '#/api/system/notice';

import { computed, onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';

import { AuthenticationLoginExpiredModal, useVbenModal } from '@vben/common-ui';
import { useWatermark } from '@vben/hooks';
import {
  BasicLayout,
  LockScreen,
  Notification,
  UserDropdown,
} from '@vben/layouts';
import { preferences, usePreferences } from '@vben/preferences';
import { useAccessStore, useUserStore } from '@vben/stores';

import {
  getTopNoticeList,
  markAllNoticesRead,
  markNoticeRead,
} from '#/api/system/notice';
import { $t } from '#/locales';
import { useAuthStore } from '#/store';
import LoginForm from '#/views/_core/authentication/login.vue';
import NoticePreview from '#/views/system/notice/modules/preview.vue';

const router = useRouter();
const userStore = useUserStore();
const authStore = useAuthStore();
const accessStore = useAccessStore();
const { destroyWatermark, updateWatermark } = useWatermark();
const { isDark } = usePreferences();
const notifications = ref<NotificationItem[]>([]);
const topNoticeLoading = ref(false);
const unreadNoticeCount = ref(0);
const showDot = computed(() =>
  unreadNoticeCount.value > 0 ||
  notifications.value.some((item) => !item.isRead),
);

const menus = computed(() => [
  {
    handler: () => {
      router.push({ name: 'Profile' });
    },
    icon: 'lucide:user',
    text: $t('page.auth.profile'),
  },
]);

const avatar = computed(() => {
  return userStore.userInfo?.avatar ?? preferences.app.defaultAvatar;
});

async function handleLogout() {
  await authStore.logout(false);
}

function toNotificationItem(
  notice: SystemNoticeApi.SysNotice,
): NotificationItem {
  const id = notice.noticeId ?? notice.noticeTitle;

  return {
    id,
    date: notice.createTime ?? '',
    isRead: notice.isRead,
    message: notice.noticeContent ?? '',
    noticeId: notice.noticeId,
    title: notice.noticeTitle,
  };
}

async function loadTopNotices() {
  if (!accessStore.accessToken || topNoticeLoading.value) {
    return;
  }

  topNoticeLoading.value = true;
  try {
    const res = await getTopNoticeList();
    notifications.value = (res.data ?? []).map(toNotificationItem);
    unreadNoticeCount.value = res.unreadCount ?? 0;
  } finally {
    topNoticeLoading.value = false;
  }
}

async function handleNoticeClear() {
  await handleMakeAll();
  notifications.value = [];
}

async function markRead(id: number | string) {
  const item = notifications.value.find((item) => item.id === id);
  if (!item?.noticeId || item.isRead) {
    return;
  }

  await markNoticeRead(item.noticeId);
  item.isRead = true;
  unreadNoticeCount.value = Math.max(unreadNoticeCount.value - 1, 0);
}

function remove(id: number | string) {
  notifications.value = notifications.value.filter((item) => item.id !== id);
}

async function handleMakeAll() {
  const unreadIds = notifications.value
    .filter((item) => !item.isRead && item.noticeId)
    .map((item) => item.noticeId);

  await markAllNoticesRead(unreadIds);
  notifications.value.forEach((item) => (item.isRead = true));
  unreadNoticeCount.value = 0;
}

function viewAll() {
  router.push('/system/notice');
}

function handleNotificationOpenChange(open: boolean) {
  if (open) {
    loadTopNotices();
  }
}

const [NoticePreviewModal, noticePreviewModalApi] = useVbenModal({
  connectedComponent: NoticePreview,
  destroyOnHidden: true,
});

const handleClick = (item: NotificationItem) => {
  if (item.noticeId) {
    noticePreviewModalApi.setData({ noticeId: item.noticeId }).open();
  }

  if (item.id) {
    markRead(item.id).catch(() => undefined);
  }
};

onMounted(loadTopNotices);

watch(
  () => ({
    enable: preferences.app.watermark,
    content: preferences.app.watermarkContent,
    isDark: isDark.value,
  }),
  async ({ enable, content, isDark: isDarkValue }) => {
    if (enable) {
      const watermarkColor = isDarkValue
        ? 'rgba(255, 255, 255, 0.12)'
        : 'rgba(0, 0, 0, 0.12)';

      await updateWatermark({
        advancedStyle: {
          colorStops: [
            {
              color: watermarkColor,
              offset: 0,
            },
            {
              color: watermarkColor,
              offset: 1,
            },
          ],
          type: 'linear',
        },
        content:
          content ||
          `${userStore.userInfo?.username} - ${userStore.userInfo?.realName}`,
      });
    } else {
      destroyWatermark();
    }
  },
  {
    immediate: true,
  },
);
</script>

<template>
  <BasicLayout @clear-preferences-and-logout="handleLogout">
    <template #user-dropdown>
      <UserDropdown
        :avatar
        :menus
        :text="userStore.userInfo?.realName"
        description="ann.vben@gmail.com"
        tag-text="Pro"
        @logout="handleLogout"
        @clear-preferences-and-logout="handleLogout"
      />
    </template>
    <template #notification>
      <Notification
        :dot="showDot"
        :notifications="notifications"
        @clear="handleNoticeClear"
        @read="(item) => item.id && markRead(item.id)"
        @remove="(item) => item.id && remove(item.id)"
        @make-all="handleMakeAll"
        @on-click="handleClick"
        @open-change="handleNotificationOpenChange"
        @view-all="viewAll"
      />
    </template>
    <template #extra>
      <NoticePreviewModal />
      <AuthenticationLoginExpiredModal
        v-model:open="accessStore.loginExpired"
        :avatar
      >
        <LoginForm />
      </AuthenticationLoginExpiredModal>
    </template>
    <template #lock-screen>
      <LockScreen :avatar @to-login="handleLogout" />
    </template>
  </BasicLayout>
</template>
