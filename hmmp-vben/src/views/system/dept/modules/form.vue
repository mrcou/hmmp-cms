<script lang="ts" setup>
import type { SystemDeptApi } from '#/api/system/dept';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createDept, getDept, updateDept } from '#/api/system/dept';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemDeptApi.SystemDept>>();
const isEdit = computed(() => !!formData.value?.deptId);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemDeptApi.SystemDept>();
    modalApi.lock();
    try {
      if (isEdit.value) {
        await updateDept({ ...values, deptId: formData.value?.deptId });
      } else {
        await createDept(values);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemDeptApi.SystemDept>>();
    formData.value = data;
    formApi.updateSchema(useFormSchema(data?.deptId));
    formApi.resetForm();
    if (data?.deptId) {
      const dept = await getDept(data.deptId);
      formApi.setValues(dept);
    } else if (data?.parentId) {
      formApi.setValues({ parentId: data.parentId });
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑部门' : '新增部门'));
</script>

<template>
  <Modal :title="title" class="w-[550px]">
    <Form />
  </Modal>
</template>
