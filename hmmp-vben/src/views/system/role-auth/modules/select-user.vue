<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { RoleUserApi } from '#/api/system/role-user';

import { useVbenModal } from '@vben/common-ui';

import { message } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import {
  getUnallocatedUserList,
  selectAuthUserAll,
} from '#/api/system/role-user';

const emit = defineEmits<{ success: [] }>();

let roleId = 0;

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    schema: [
      { component: 'Input', fieldName: 'userName', label: '用户名称' },
      { component: 'Input', fieldName: 'phonenumber', label: '手机号码' },
    ],
    submitOnChange: true,
  },
  gridOptions: {
    columns: [
      { type: 'checkbox', width: 50 },
      { field: 'userId', title: '用户编号', width: 100 },
      { field: 'userName', title: '用户名称', width: 130 },
      { field: 'nickName', title: '用户昵称', width: 130 },
      { field: 'phonenumber', title: '手机号码', width: 130 },
    ],
    height: 400,
    keepSource: true,
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          return await getUnallocatedUserList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            roleId,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'userId' },
    toolbarConfig: { custom: false, export: false, refresh: true, search: true },
  } as VxeTableGridOptions<RoleUserApi.User>,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const rows = gridApi.grid?.getCheckboxRecords() ?? [];
    if (rows.length === 0) {
      message.warning('请至少选择一个用户');
      return;
    }
    modalApi.lock();
    try {
      const userIds = rows.map((row: RoleUserApi.User) => row.userId);
      await selectAuthUserAll(roleId, userIds);
      message.success('添加成功');
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  onOpenChange(isOpen) {
    if (isOpen) {
      const data = modalApi.getData<{ roleId: number }>();
      roleId = data?.roleId ?? 0;
    }
  },
});
</script>

<template>
  <Modal title="添加用户" class="w-[800px]">
    <Grid />
  </Modal>
</template>
