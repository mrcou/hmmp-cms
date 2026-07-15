<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemDeptApi } from '#/api/system/dept';

import { Page, useVbenModal, VbenTableAction } from '@vben/common-ui';
import { Plus } from '@vben/icons';

import { Button, message } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { deleteDept, getDeptList } from '#/api/system/dept';

import { useColumns } from './data';
import Form from './modules/form.vue';

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnClose: true,
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
          return await getDeptList();
        },
      },
    },
    rowConfig: { keyField: 'deptId' },
    toolbarConfig: { custom: true, export: false, refresh: true, zoom: true },
    treeConfig: {
      parentField: 'parentId',
      rowField: 'deptId',
      transform: true,
    },
  } as VxeTableGridOptions<SystemDeptApi.SystemDept>,
});

function onRefresh() {
  gridApi.query();
}

function onCreate() {
  formModalApi.setData({}).open();
}

function onAppend(row: SystemDeptApi.SystemDept) {
  formModalApi.setData({ parentId: row.deptId }).open();
}

function onEdit(row: SystemDeptApi.SystemDept) {
  formModalApi.setData(row).open();
}

function onDelete(row: SystemDeptApi.SystemDept) {
  deleteDept(row.deptId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}
</script>

<template>
  <Page auto-content-height content-class="overflow-hidden">
    <FormModal @success="onRefresh" />
    <div class="dept-grid">
      <Grid class="h-full" table-title="部门列表">
        <template #toolbar-tools>
          <Button type="primary" @click="onCreate">
            <Plus class="size-5" />
            新增部门
          </Button>
        </template>
        <template #action="{ row }">
          <VbenTableAction
            :actions="[
              { text: '新增', icon: 'lucide:plus', onClick: () => onAppend(row) },
              { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
              {
                text: '删除',
                icon: 'lucide:trash-2',
                danger: true,
                popConfirm: {
                  title: `确认删除部门'${row.deptName}'吗？`,
                  confirm: () => onDelete(row),
                },
              },
            ]"
          />
        </template>
      </Grid>
    </div>
  </Page>
</template>

<style scoped>
.dept-grid {
  display: flex;
  height: 100%;
  min-height: 0;
  width: 100%;
  min-width: 0;
}

.dept-grid :deep(.vxe-grid),
.dept-grid :deep(.vxe-table),
.dept-grid :deep(.vxe-table--render-wrapper),
.dept-grid :deep(.vxe-table--main-wrapper),
.dept-grid :deep(.vxe-table--header-wrapper),
.dept-grid :deep(.vxe-table--body-wrapper),
.dept-grid :deep(.vxe-table--header),
.dept-grid :deep(.vxe-table--body) {
  width: 100% !important;
}
</style>
