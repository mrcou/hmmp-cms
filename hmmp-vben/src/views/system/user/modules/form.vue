<script lang="ts" setup>
import type { SystemUserApi } from '#/api/system/user';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createUser, getUser, updateUser } from '#/api/system/user';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemUserApi.SystemUser>>();
const isEdit = computed(() => !!formData.value?.userId);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(false),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemUserApi.SystemUser>();
    modalApi.lock();
    try {
      if (isEdit.value) {
        await updateUser({ ...values, userId: formData.value?.userId });
      } else {
        await createUser(values);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemUserApi.SystemUser>>();
    formData.value = data;
    formApi.updateSchema(useFormSchema(!!data?.userId));
    formApi.resetForm();
    if (data?.userId) {
      const res = await getUser(data.userId);
      formApi.setValues({
        ...res.data,
        postIds: res.postIds,
        roleIds: res.roleIds,
      });
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑用户' : '新增用户'));
</script>

<template>
  <Modal :title="title" class="w-[600px]">
    <Form />
  </Modal>
</template>
