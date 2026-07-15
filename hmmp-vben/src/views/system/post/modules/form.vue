<script lang="ts" setup>
import type { SystemPostApi } from '#/api/system/post';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createPost, getPost, updatePost } from '#/api/system/post';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemPostApi.SysPost>>();
const isEdit = computed(() => !!formData.value?.postId);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemPostApi.SysPost>();
    modalApi.lock();
    try {
      if (isEdit.value) {
        await updatePost({ ...values, postId: formData.value?.postId });
      } else {
        await createPost(values);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemPostApi.SysPost>>();
    formData.value = data;
    formApi.resetForm();
    if (data?.postId) {
      const res = await getPost(data.postId);
      formApi.setValues(res.data);
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑岗位' : '新增岗位'));
</script>

<template>
  <Modal :title="title" class="w-[600px]">
    <Form />
  </Modal>
</template>
