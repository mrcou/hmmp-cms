<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { RoleUserApi } from '#/api/system/role-user';

import { useRoute } from 'vue-router';

import { Page, useVbenModal, VbenTableAction } from '@vben/common-ui';
import { Plus } from '@vben/icons';

import { Button, message, Modal } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import {
  cancelAuthUser,
  cancelAuthUserAll,
  getAllocatedUserList,
} from '#/api/system/role-user';

import SelectUser from './modules/select-user.vue';

const route = useRoute();
const roleId = Number(route.query.roleId);
const roleName = String(route.query.roleName ?? '');

const [SelectUserModal, selectUserModalApi] = useVbenModal({
  connectedComponent: SelectUser,
  destroyOnHidden: true,
});

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
      { field: 'createTime', title: '创建时间', width: 160 },
      {
        align: 'center',
        field: 'operation',
        fixed: 'right',
        slots: { default: 'action' },
        title: '操作',
        width: 120,
      },
    ],
    height: 'auto',
    keepSource: true,
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          return await getAllocatedUserList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            roleId,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'userId' },
    toolbarConfig: { custom: true, export: false, refresh: true, search: true },
  } as VxeTableGridOptions<RoleUserApi.User>,
});

function onRefresh() {
  gridApi.query();
}

function onAddUser() {
  selectUserModalApi.setData({ roleId }).open();
}

function onCancel(row: RoleUserApi.User) {
  cancelAuthUser(roleId, row.userId).then(() => {
    message.success('取消授权成功');
    onRefresh();
  });
}

function onBatchCancel() {
  const rows = gridApi.grid?.getCheckboxRecords() ?? [];
  if (rows.length === 0) {
    message.warning('请至少选择一条记录');
    return;
  }
  Modal.confirm({
    content: `确认取消选中的 ${rows.length} 个用户的角色授权吗？`,
    title: '提示',
    async onOk() {
      const userIds = rows.map((row: RoleUserApi.User) => row.userId);
      await cancelAuthUserAll(roleId, userIds);
      message.success('取消授权成功');
      onRefresh();
    },
  });
}
</script>

<template>
  <Page auto-content-height :title="`分配用户 - ${roleName}`">
    <SelectUserModal @success="onRefresh" />
    <Grid table-title="已分配用户列表">
      <template #toolbar-tools>
        <Button class="mr-2" @click="onBatchCancel">批量取消授权</Button>
        <Button type="primary" @click="onAddUser">
          <Plus class="size-5" />
          添加用户
        </Button>
      </template>
      <template #action="{ row }">
        <VbenTableAction
          :actions="[
            {
              text: '取消授权',
              icon: 'lucide:user-round-x',
              danger: true,
              popConfirm: {
                title: `确认取消该用户的角色授权吗？`,
                confirm: () => onCancel(row),
              },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
