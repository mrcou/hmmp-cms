<script lang="ts" setup>
import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { message, Tabs } from 'antdv-next';

import { previewGenTable } from '#/api/tool/gen';

const codeMap = ref<Record<string, string>>({});
const activeKey = ref('');

function shortName(path: string) {
  const parts = path.split('/');
  return parts[parts.length - 1]?.replace('.vm', '') ?? path;
}

const tabs = computed(() =>
  Object.keys(codeMap.value).map((key) => ({ key, label: shortName(key) })),
);

const [Modal, modalApi] = useVbenModal({
  footer: false,
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<{ tableId: number }>();
    if (!data?.tableId) return;
    modalApi.lock();
    try {
      const res = await previewGenTable(data.tableId);
      codeMap.value = res.data;
      const keys = Object.keys(res.data);
      activeKey.value = keys[0] ?? '';
    } finally {
      modalApi.unlock();
    }
  },
});

async function copyCode() {
  try {
    await navigator.clipboard.writeText(codeMap.value[activeKey.value] ?? '');
    message.success('已复制到剪贴板');
  } catch {
    message.error('复制失败');
  }
}
</script>

<template>
  <Modal title="代码预览" class="w-[900px]">
    <Tabs v-model:active-key="activeKey" :items="tabs" />
    <div class="relative">
      <button
        class="absolute right-2 top-2 z-10 rounded bg-gray-700 px-2 py-1 text-xs text-white"
        @click="copyCode"
      >
        复制
      </button>
      <pre class="max-h-[500px] overflow-auto rounded bg-gray-900 p-4 text-xs text-gray-100"><code>{{ codeMap[activeKey] }}</code></pre>
    </div>
  </Modal>
</template>
