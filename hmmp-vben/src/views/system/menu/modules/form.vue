<script lang="ts" setup>
import type { SystemMenuApi } from '#/api/system/menu';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createMenu, getMenu, updateMenu } from '#/api/system/menu';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemMenuApi.SystemMenu>>();
const isEdit = computed(() => !!formData.value?.menuId);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemMenuApi.SystemMenu>();
    modalApi.lock();
    try {
      if (isEdit.value) {
        await updateMenu({ ...values, menuId: formData.value?.menuId });
      } else {
        await createMenu(values);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemMenuApi.SystemMenu>>();
    formData.value = data;
    formApi.resetForm();
    if (data?.menuId) {
      const menu = await getMenu(data.menuId);
      formApi.setValues(menu);
    } else if (data?.parentId) {
      formApi.setValues({ parentId: data.parentId });
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑菜单' : '新增菜单'));
</script>

<template>
  <Modal :title="title" class="w-[600px]">
    <Form />
  </Modal>
</template>
