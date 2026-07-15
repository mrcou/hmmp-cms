<script lang="ts" setup>
import type { SystemDictTypeApi } from '#/api/system/dict-type';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createDictType, getDictType, updateDictType } from '#/api/system/dict-type';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemDictTypeApi.SysDictType>>();
const isEdit = computed(() => !!formData.value?.dictId);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(false),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemDictTypeApi.SysDictType>();
    modalApi.lock();
    try {
      if (isEdit.value) {
        await updateDictType({ ...values, dictId: formData.value?.dictId });
      } else {
        await createDictType(values);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemDictTypeApi.SysDictType>>();
    formData.value = data;
    formApi.updateSchema(useFormSchema(!!data?.dictId));
    formApi.resetForm();
    if (data?.dictId) {
      const dictType = await getDictType(data.dictId);
      formApi.setValues(dictType);
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑字典类型' : '新增字典类型'));
</script>

<template>
  <Modal :title="title" class="w-[600px]">
    <Form />
  </Modal>
</template>
