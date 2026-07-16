<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemMenuApi } from '#/api/system/menu';

import { Page, useVbenModal, VbenTableAction } from '@vben/common-ui';
import { Plus } from '@vben/icons';

import { Button, message } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { deleteMenu, getMenuList } from '#/api/system/menu';

import { useColumns } from './data';
import Form from './modules/form.vue';

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnHidden: true,
});

const [Grid, gridApi] = useVbenVxeGrid({
  gridOptions: {
    columns: useColumns(),
    height: 'auto',
    keepSource: true,
    pagerConfig: { enabled: false },
    proxyConfig: {
      ajax: {
        query: async () => {
          return await getMenuList();
        },
      },
    },
    rowConfig: { keyField: 'menuId' },
    toolbarConfig: { custom: true, export: false, refresh: true, zoom: true },
    treeConfig: {
      parentField: 'parentId',
      rowField: 'menuId',
      transform: true,
    },
  } as VxeTableGridOptions<SystemMenuApi.SystemMenu>,
});

function onRefresh() {
  gridApi.query();
}

function onCreate() {
  formModalApi.setData({}).open();
}

function onAppend(row: SystemMenuApi.SystemMenu) {
  formModalApi.setData({ parentId: row.menuId }).open();
}

function onEdit(row: SystemMenuApi.SystemMenu) {
  formModalApi.setData(row).open();
}

function onDelete(row: SystemMenuApi.SystemMenu) {
  deleteMenu(row.menuId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}
</script>

<template>
  <Page auto-content-height>
    <FormModal @success="onRefresh" />
    <Grid table-title="菜单列表">
      <template #toolbar-tools>
        <Button type="primary" @click="onCreate">
          <Plus class="size-5" />
          新增菜单
        </Button>
      </template>
      <template #action="{ row }">
        <VbenTableAction
          align="center"
          :actions="[
            { text: '新增', icon: 'lucide:plus', onClick: () => onAppend(row) },
            { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
            {
              text: '删除',
              icon: 'lucide:trash-2',
              danger: true,
              popConfirm: {
                title: `确认删除菜单'${row.menuName}'吗？`,
                confirm: () => onDelete(row),
              },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
