<script lang="ts" setup>
import type { SystemNoticeApi } from '#/api/system/notice';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createNotice, getNotice, updateNotice } from '#/api/system/notice';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemNoticeApi.SysNotice>>();
const isEdit = computed(() => !!formData.value?.noticeId);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemNoticeApi.SysNotice>();
    modalApi.lock();
    try {
      if (isEdit.value) {
        await updateNotice({ ...values, noticeId: formData.value?.noticeId });
      } else {
        await createNotice(values);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemNoticeApi.SysNotice>>();
    formData.value = data;
    formApi.resetForm();
    if (data?.noticeId) {
      const res = await getNotice(data.noticeId);
      formApi.setValues(res.data);
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑公告' : '新增公告'));
</script>

<template>
  <Modal :title="title" class="w-[900px]">
    <Form />
  </Modal>
</template>
