<script lang="ts" setup>
import type { MonitorCacheApi } from '#/api/monitor/cache';

import { computed, onMounted, ref } from 'vue';

import { Page } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';

import { Button, Empty, message, Modal, Spin } from 'antdv-next';

import {
  clearCacheAll,
  clearCacheKey,
  clearCacheName,
  getCacheInfo,
  getCacheKeys,
  getCacheNames,
  getCacheValue,
} from '#/api/monitor/cache';

const loading = ref(false);
const cacheInfo = ref<MonitorCacheApi.CacheInfo>();
const cacheNames = ref<MonitorCacheApi.CacheName[]>([]);
const cacheKeys = ref<string[]>([]);
const cacheValue = ref('');

const selectedCacheName = ref<string>();
const selectedCacheKey = ref<string>();

const baseInfoItems = computed(() => {
  const info = cacheInfo.value?.info ?? {};
  return [
    { label: 'Redis版本', value: info.redis_version },
    { label: '运行模式', value: info.redis_mode === 'cluster' ? '集群' : '单机' },
    { label: '端口', value: info.tcp_port },
    { label: '客户端数', value: info.connected_clients },
    { label: '运行时间(天)', value: info.uptime_in_days },
    { label: '使用内存', value: info.used_memory_human },
    { label: '使用CPU', value: info.used_cpu_user_children },
    { label: '内存配置', value: info.maxmemory_human },
    { label: 'AOF是否开启', value: info.aof_enabled === '0' ? '否' : '是' },
    { label: 'RDB是否成功', value: info.rdb_last_bgsave_status },
    { label: 'Key数量', value: cacheInfo.value?.dbSize },
    { label: '网络入口/出口', value: `${info.instantaneous_input_kbps ?? 0}kps/${info.instantaneous_output_kbps ?? 0}kps` },
  ];
});

async function loadCacheInfo() {
  const res = await getCacheInfo();
  cacheInfo.value = res.data;
}

async function loadCacheNames() {
  const res = await getCacheNames();
  cacheNames.value = res.data;
}

async function refreshAll() {
  loading.value = true;
  try {
    await Promise.all([loadCacheInfo(), loadCacheNames()]);
    cacheKeys.value = [];
    cacheValue.value = '';
    selectedCacheName.value = undefined;
    selectedCacheKey.value = undefined;
  } finally {
    loading.value = false;
  }
}

async function onSelectCacheName(item: MonitorCacheApi.CacheName) {
  selectedCacheName.value = item.cacheName;
  selectedCacheKey.value = undefined;
  cacheValue.value = '';
  const res = await getCacheKeys(item.cacheName);
  cacheKeys.value = res.data;
}

async function onSelectCacheKey(key: string) {
  if (!selectedCacheName.value) return;
  selectedCacheKey.value = key;
  const res = await getCacheValue(
    selectedCacheName.value,
    encodeURIComponent(key),
  );
  cacheValue.value = res.data?.cacheValue ?? '';
}

function confirm(content: string, title: string) {
  return new Promise((resolve, reject) => {
    Modal.confirm({
      content,
      onCancel: () => reject(new Error('已取消')),
      onOk: () => resolve(true),
      title,
    });
  });
}

async function onClearCacheName(item: MonitorCacheApi.CacheName) {
  try {
    await confirm(`确认清除"${item.remark}"这一类缓存吗？`, '提示');
    await clearCacheName(item.cacheName);
    message.success('清除成功');
    if (selectedCacheName.value === item.cacheName) {
      cacheKeys.value = [];
      cacheValue.value = '';
      selectedCacheKey.value = undefined;
    }
  } catch {
    // 用户取消
  }
}

async function onClearCacheKey(key: string) {
  try {
    await confirm(`确认清除缓存键"${key}"吗？`, '提示');
    await clearCacheKey(encodeURIComponent(key));
    message.success('清除成功');
    cacheKeys.value = cacheKeys.value.filter((item) => item !== key);
    if (selectedCacheKey.value === key) {
      cacheValue.value = '';
      selectedCacheKey.value = undefined;
    }
  } catch {
    // 用户取消
  }
}

async function onClearCacheAll() {
  try {
    await confirm('确认清空全部缓存吗？', '提示');
    await clearCacheAll();
    message.success('清空成功');
    cacheKeys.value = [];
    cacheValue.value = '';
    selectedCacheName.value = undefined;
    selectedCacheKey.value = undefined;
  } catch {
    // 用户取消
  }
}

onMounted(() => {
  refreshAll();
});
</script>

<template>
  <Page auto-content-height title="缓存监控">
    <Spin :spinning="loading">
      <div class="mb-4 rounded-lg border p-4">
        <div class="mb-3 flex items-center justify-between">
          <div class="text-base font-medium">Redis 基本信息</div>
          <div class="flex gap-2">
            <Button @click="refreshAll">
              <IconifyIcon icon="lucide:refresh-cw" class="size-4" />
              刷新
            </Button>
            <Button danger @click="onClearCacheAll">
              <IconifyIcon icon="lucide:trash-2" class="size-4" />
              清空全部缓存
            </Button>
          </div>
        </div>
        <div class="grid grid-cols-2 gap-x-6 gap-y-2 md:grid-cols-4">
          <div v-for="item in baseInfoItems" :key="item.label" class="flex justify-between border-b border-dashed pb-1 text-sm">
            <span class="text-gray-500">{{ item.label }}</span>
            <span class="font-medium">{{ item.value ?? '-' }}</span>
          </div>
        </div>
      </div>

      <div class="flex gap-4">
        <div class="w-1/4 rounded-lg border p-3">
          <div class="mb-2 text-base font-medium">缓存名称</div>
          <Empty v-if="cacheNames.length === 0" description="暂无缓存" />
          <div v-else class="max-h-[480px] space-y-1 overflow-auto">
            <div
              v-for="item in cacheNames"
              :key="item.cacheName"
              class="flex cursor-pointer items-center justify-between rounded px-2 py-1.5 text-sm hover:bg-gray-100 dark:hover:bg-gray-800"
              :class="{ 'bg-blue-100 dark:bg-blue-900': selectedCacheName === item.cacheName }"
              @click="onSelectCacheName(item)"
            >
              <span>{{ item.remark }}</span>
              <IconifyIcon
                icon="lucide:trash-2"
                class="size-4 shrink-0 text-gray-400 hover:text-red-500"
                @click.stop="onClearCacheName(item)"
              />
            </div>
          </div>
        </div>

        <div class="w-1/4 rounded-lg border p-3">
          <div class="mb-2 text-base font-medium">缓存键名</div>
          <Empty v-if="cacheKeys.length === 0" description="暂无键名" />
          <div v-else class="max-h-[480px] space-y-1 overflow-auto">
            <div
              v-for="key in cacheKeys"
              :key="key"
              class="flex cursor-pointer items-center justify-between rounded px-2 py-1.5 text-sm hover:bg-gray-100 dark:hover:bg-gray-800"
              :class="{ 'bg-blue-100 dark:bg-blue-900': selectedCacheKey === key }"
              @click="onSelectCacheKey(key)"
            >
              <span class="truncate" :title="key">{{ key }}</span>
              <IconifyIcon
                icon="lucide:trash-2"
                class="size-4 shrink-0 text-gray-400 hover:text-red-500"
                @click.stop="onClearCacheKey(key)"
              />
            </div>
          </div>
        </div>

        <div class="w-1/2 rounded-lg border p-3">
          <div class="mb-2 text-base font-medium">缓存内容</div>
          <Empty v-if="!cacheValue" description="暂无内容" />
          <pre v-else class="max-h-[480px] overflow-auto whitespace-pre-wrap break-all text-sm">{{ cacheValue }}</pre>
        </div>
      </div>
    </Spin>
  </Page>
</template>
