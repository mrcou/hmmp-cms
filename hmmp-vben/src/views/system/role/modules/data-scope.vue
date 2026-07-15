<script lang="ts" setup>
import type { SystemRoleApi } from '#/api/system/role';

import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { Tree } from '@vben/common-ui';

import { Select } from 'antdv-next';

import { getRoleDeptTree, updateRoleDataScope } from '#/api/system/role';

import { DATA_SCOPE_OPTIONS } from '../data';

const emit = defineEmits<{ success: [] }>();

const roleId = ref<number>();
const dataScope = ref('1');
const deptTree = ref<SystemRoleApi.DeptTreeNode[]>([]);
const checkedDeptKeys = ref<number[]>([]);

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    if (!roleId.value) return;
    modalApi.lock();
    try {
      await updateRoleDataScope({
        roleId: roleId.value,
        dataScope: dataScope.value,
        deptIds: checkedDeptKeys.value,
      });
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;
    const data = modalApi.getData<SystemRoleApi.SystemRole>();
    roleId.value = data?.roleId;
    dataScope.value = data?.dataScope ?? '1';
    if (roleId.value) {
      const res = await getRoleDeptTree(roleId.value);
      deptTree.value = res.depts;
      checkedDeptKeys.value = res.checkedKeys ?? [];
    }
  },
});

</script>

<template>
  <Modal title="分配数据权限" class="w-[500px]">
    <div class="data-scope-picker">
      <div class="data-scope-title">权限范围</div>
      <Select
        v-model:value="dataScope"
        class="data-scope-select"
        :options="DATA_SCOPE_OPTIONS"
      />
    </div>
    <div class="mb-2 mt-1 text-sm font-medium">数据权限</div>
    <Tree
      v-model="checkedDeptKeys"
      :tree-data="deptTree"
      multiple
      label-field="label"
      value-field="id"
      children-field="children"
      :default-expanded-level="2"
    />
  </Modal>
</template>

<style scoped>
.data-scope-picker {
  display: grid;
  grid-template-columns: 88px 1fr;
  gap: 12px;
  align-items: center;
  padding: 12px;
  margin-bottom: 16px;
  background: hsl(var(--muted) / 45%);
  border: 1px solid hsl(var(--border));
  border-radius: 8px;
}

.data-scope-title {
  display: flex;
  align-items: center;
  min-height: 36px;
  font-size: 14px;
  font-weight: 600;
  color: hsl(var(--foreground));
}

.data-scope-select {
  width: 100%;
}
</style>
