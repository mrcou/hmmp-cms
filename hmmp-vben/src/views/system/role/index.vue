<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemRoleApi } from '#/api/system/role';

import { useRouter } from 'vue-router';

import { Page, useVbenModal, VbenTableAction } from '@vben/common-ui';
import { Plus } from '@vben/icons';

import { Button, message, Modal } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import {
  changeRoleStatus,
  deleteRole,
  exportRole,
  getRoleList,
} from '#/api/system/role';
import { compactSearchFormOptions } from '#/views/_shared/table-search';

import { useColumns, useGridFormSchema } from './data';
import DataScope from './modules/data-scope.vue';
import Form from './modules/form.vue';

const router = useRouter();

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnHidden: true,
});

const [DataScopeModal, dataScopeModalApi] = useVbenModal({
  connectedComponent: DataScope,
  destroyOnHidden: true,
});

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    ...compactSearchFormOptions,
    schema: useGridFormSchema(),
    submitOnChange: true,
  },
  gridOptions: {
    columns: useColumns(onStatusChange),
    height: 'auto',
    keepSource: true,
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          return await getRoleList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'roleId' },
    toolbarConfig: { custom: true, export: false, refresh: true, search: true, zoom: true },
  } as VxeTableGridOptions<SystemRoleApi.SystemRole>,
});

function confirm(content: string, title: string) {
  return new Promise((resolve, reject) => {
    Modal.confirm({ content, onCancel: () => reject(new Error('已取消')), onOk: () => resolve(true), title });
  });
}

async function onStatusChange(newStatus: string, row: SystemRoleApi.SystemRole) {
  const text = newStatus === '0' ? '启用' : '停用';
  try {
    await confirm(`确认要${text}"${row.roleName}"角色吗？`, '提示');
    await changeRoleStatus(row.roleId!, newStatus);
    message.success(`${text}成功`);
    return true;
  } catch {
    return false;
  }
}

function onRefresh() {
  gridApi.query();
}

async function onExport() {
  const formValues = (await gridApi.formApi?.getValues()) ?? {};
  await exportRole(formValues);
}

function onCreate() {
  formModalApi.setData({}).open();
}

function onEdit(row: SystemRoleApi.SystemRole) {
  formModalApi.setData(row).open();
}

function onDataScope(row: SystemRoleApi.SystemRole) {
  dataScopeModalApi.setData(row).open();
}

function onAssignUser(row: SystemRoleApi.SystemRole) {
  router.push({
    path: '/system/role-auth',
    query: { roleId: row.roleId, roleName: row.roleName },
  });
}

function onDelete(row: SystemRoleApi.SystemRole) {
  deleteRole(row.roleId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}
</script>

<template>
  <Page auto-content-height>
    <FormModal @success="onRefresh" />
    <DataScopeModal @success="onRefresh" />
    <Grid table-title="角色列表">
      <template #toolbar-tools>
        <Button class="mr-2" @click="onExport">导出</Button>
        <Button type="primary" @click="onCreate">
          <Plus class="size-5" />
          新增角色
        </Button>
      </template>
      <template #action="{ row }">
        <VbenTableAction
          :actions="[
            { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
            { text: '数据权限', icon: 'lucide:lock', onClick: () => onDataScope(row) },
            { text: '分配用户', icon: 'lucide:users', onClick: () => onAssignUser(row) },
            {
              text: '删除',
              icon: 'lucide:trash-2',
              danger: true,
              popConfirm: { title: `确认删除角色'${row.roleName}'吗？`, confirm: () => onDelete(row) },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
