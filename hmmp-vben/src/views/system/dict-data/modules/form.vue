<script lang="ts" setup>
import type { SystemDictDataApi } from '#/api/system/dict-data';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import {
  createDictData,
  getDictData,
  updateDictData,
} from '#/api/system/dict-data';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemDictDataApi.SysDictData>>();
const isEdit = computed(() => !!formData.value?.dictCode);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemDictDataApi.SysDictData>();
      modalApi.lock();
      try {
      const payload: SystemDictDataApi.SysDictData = {
        ...values,
        dictCode: formData.value?.dictCode,
        dictType: formData.value?.dictType ?? values.dictType ?? '',
      };
      if (isEdit.value) {
        await updateDictData(payload);
      } else {
        await createDictData(payload);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemDictDataApi.SysDictData>>();
    formData.value = data;
    formApi.resetForm();
    if (data?.dictCode) {
      const dictData = await getDictData(data.dictCode);
      formData.value = dictData;
      formApi.setValues(dictData);
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑字典数据' : '新增字典数据'));
</script>

<template>
  <Modal :title="title" class="w-[600px]">
    <Form />
  </Modal>
</template>
