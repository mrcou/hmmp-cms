<script lang="ts" setup>
import type { SystemNoticeApi } from '#/api/system/notice';

import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';
import { VbenTiptapPreview } from '@vben/plugins/tiptap';

import { getNotice } from '#/api/system/notice';

const notice = ref<SystemNoticeApi.SysNotice>();

const [Modal, modalApi] = useVbenModal({
  footer: false,
  fullscreenButton: false,
  async onOpenChange(isOpen) {
    if (!isOpen) {
      notice.value = undefined;
      return;
    }

    const data = modalApi.getData<Pick<SystemNoticeApi.SysNotice, 'noticeId'>>();
    if (!data?.noticeId) return;

    modalApi.lock();
    try {
      const res = await getNotice(data.noticeId);
      notice.value = res.data;
    } finally {
      modalApi.unlock();
    }
  },
});
</script>

<template>
  <Modal title="公告详情" class="w-[900px] max-w-[calc(100vw-32px)]">
    <div v-if="notice" class="mx-auto w-[760px] max-w-full space-y-4">
      <div class="border-b border-border pb-3">
        <h2 class="text-lg font-semibold text-foreground">
          {{ notice.noticeTitle }}
        </h2>
        <p class="mt-2 text-sm text-muted-foreground">
          {{ notice.createTime }}
        </p>
      </div>
      <VbenTiptapPreview
        class="max-h-[55vh] overflow-auto"
        :content="notice.noticeContent || '<p>暂无内容</p>'"
        :min-height="180"
      />
    </div>
  </Modal>
</template>
