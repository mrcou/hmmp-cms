<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemConfigApi } from '#/api/system/config';

import { Page, useVbenModal } from '@vben/common-ui';
import { Plus } from '@vben/icons';

import { Button, message } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { VbenTableAction } from '@vben/common-ui';
import {
  deleteConfig,
  exportConfig,
  getConfigList,
} from '#/api/system/config';
import { compactSearchFormOptions } from '#/views/_shared/table-search';

import { useColumns, useGridFormSchema } from './data';
import Form from './modules/form.vue';

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnClose: true,
});

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    ...compactSearchFormOptions,
    schema: useGridFormSchema(),
    submitOnChange: true,
  },
  gridOptions: {
    columns: useColumns(),
    height: 'auto',
    keepSource: true,
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          return await getConfigList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'configId' },
    toolbarConfig: {
      custom: true,
      export: false,
      refresh: true,
      search: true,
      zoom: true,
    },
  } as VxeTableGridOptions<SystemConfigApi.SysConfig>,
});

function onRefresh() {
  gridApi.query();
}

async function onExport() {
  const formValues = (await gridApi.formApi?.getValues()) ?? {};
  await exportConfig(formValues);
}

function onCreate() {
  formModalApi.setData({}).open();
}

function onEdit(row: SystemConfigApi.SysConfig) {
  formModalApi.setData(row).open();
}

function onDelete(row: SystemConfigApi.SysConfig) {
  deleteConfig(row.configId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}
</script>

<template>
  <Page auto-content-height>
    <FormModal @success="onRefresh" />
    <Grid table-title="参数配置列表">
      <template #toolbar-tools>
        <Button class="mr-2" @click="onExport">导出</Button>
        <Button type="primary" @click="onCreate">
          <Plus class="size-5" />
          新增参数
        </Button>
      </template>
      <template #action="{ row }">
        <VbenTableAction
          :actions="[
            { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
            {
              text: '删除',
              icon: 'lucide:trash-2',
              danger: true,
              popConfirm: {
                title: `确认删除参数'${row.configName}'吗？`,
                confirm: () => onDelete(row),
              },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
