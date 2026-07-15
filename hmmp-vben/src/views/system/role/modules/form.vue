<script lang="ts" setup>
import type { SystemRoleApi } from '#/api/system/role';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { Tree } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import {
  createRole,
  getRole,
  getRoleMenuTree,
  updateRole,
} from '#/api/system/role';
import { getMenuTreeSelect } from '#/api/system/menu';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const formData = ref<Partial<SystemRoleApi.SystemRole>>();
const isEdit = computed(() => !!formData.value?.roleId);

const menuTree = ref<SystemRoleApi.MenuTreeNode[]>([]);
const checkedMenuKeys = ref<number[]>([]);
const menuCheckStrictly = ref(true);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;
    const values = await formApi.getValues<SystemRoleApi.SystemRole>();
    modalApi.lock();
    try {
      const payload = {
        ...values,
        roleId: formData.value?.roleId,
        menuIds: checkedMenuKeys.value,
        menuCheckStrictly: menuCheckStrictly.value,
      };
      if (isEdit.value) {
        await updateRole(payload);
      } else {
        await createRole(payload);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<Partial<SystemRoleApi.SystemRole>>();
    formData.value = data;
    formApi.resetForm();
    if (data?.roleId) {
      const role = await getRole(data.roleId);
      formApi.setValues(role);
      menuCheckStrictly.value = !!role.menuCheckStrictly;
      const res = await getRoleMenuTree(data.roleId);
      menuTree.value = res.menus;
      checkedMenuKeys.value = res.checkedKeys ?? [];
    } else {
      menuCheckStrictly.value = true;
      menuTree.value = await getMenuTreeSelect();
      checkedMenuKeys.value = [];
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑角色' : '新增角色'));
</script>

<template>
  <Modal :title="title" class="w-[600px]">
    <Form />
    <div class="mb-2 mt-1 text-sm font-medium">菜单权限</div>
    <Tree
      v-model="checkedMenuKeys"
      :tree-data="menuTree"
      :check-strictly="menuCheckStrictly"
      multiple
      label-field="label"
      value-field="id"
      children-field="children"
      :default-expanded-level="2"
    />
  </Modal>
</template>
