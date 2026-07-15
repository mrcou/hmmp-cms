<script lang="ts" setup>
import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm, z } from '#/adapter/form';
import { resetUserPwd } from '#/api/system/user';

const emit = defineEmits<{ success: [] }>();

const userId = ref<number>();

const [Form, formApi] = useVbenForm({
  schema: [
    {
      component: 'InputPassword',
      fieldName: 'password',
      label: '新密码',
      rules: z.string().min(5, '密码长度不能少于5位'),
    },
  ],
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const { password } = await formApi.getValues<{ password: string }>();
    if (!userId.value) return;
    modalApi.lock();
    try {
      await resetUserPwd(userId.value, password);
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<{ userId: number }>();
    userId.value = data?.userId;
    formApi.resetForm();
  },
});
</script>

<template>
  <Modal title="重置密码" class="w-[400px]">
    <Form />
  </Modal>
</template>
