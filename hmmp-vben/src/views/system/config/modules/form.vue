<script lang="ts" setup>
import type { SystemConfigApi } from '#/api/system/config';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createConfig, getConfig, updateConfig } from '#/api/system/config';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemConfigApi.SysConfig>>();
const isEdit = computed(() => !!formData.value?.configId);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemConfigApi.SysConfig>();
    modalApi.lock();
    try {
      if (isEdit.value) {
        await updateConfig({ ...values, configId: formData.value?.configId });
      } else {
        await createConfig(values);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemConfigApi.SysConfig>>();
    formData.value = data;
    formApi.resetForm();
    if (data?.configId) {
      const res = await getConfig(data.configId);
      formApi.setValues(res.data);
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑参数' : '新增参数'));
</script>

<template>
  <Modal :title="title" class="w-[600px]">
    <Form />
  </Modal>
</template>
