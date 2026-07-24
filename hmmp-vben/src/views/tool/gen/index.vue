<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { GenApi } from '#/api/tool/gen';

import { useRouter } from 'vue-router';

import { Page, useVbenModal } from '@vben/common-ui';
import { Plus } from '@vben/icons';

import { Button, message, Modal } from 'antdv-next';

import { VbenTableAction } from '@vben/common-ui';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { saveBlob } from '#/api/core/download';
import {
  batchDownloadGenCode,
  deleteGenTable,
  downloadGenCode,
  getGenTableList,
  synchDb,
} from '#/api/tool/gen';

import { useColumns, useGridFormSchema } from './data';
import GenImport from './modules/import.vue';
import Preview from './modules/preview.vue';

const router = useRouter();

const [ImportModal, importModalApi] = useVbenModal({
  connectedComponent: GenImport,
  destroyOnHidden: true,
});

const [PreviewModal, previewModalApi] = useVbenModal({
  connectedComponent: Preview,
  destroyOnHidden: true,
});

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
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
          return await getGenTableList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'tableId' },
    toolbarConfig: { custom: true, export: false, refresh: true, search: true },
  } as VxeTableGridOptions<GenApi.GenTable>,
});

function onRefresh() {
  gridApi.query();
}

function onImport() {
  importModalApi.open();
}

function onEdit(row: GenApi.GenTable) {
  router.push({ path: '/system/tool/gen/edit', query: { tableId: row.tableId } });
}

function onPreview(row: GenApi.GenTable) {
  previewModalApi.setData({ tableId: row.tableId }).open();
}

function onDelete(row: GenApi.GenTable) {
  deleteGenTable(row.tableId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}

async function onSynch(row: GenApi.GenTable) {
  await synchDb(row.tableName);
  message.success('同步成功');
}

async function onDownload(row: GenApi.GenTable) {
  const blob = await downloadGenCode(row.tableName);
  saveBlob(blob, `${row.tableName}.zip`);
}

function onBatchDownload() {
  const rows = gridApi.grid?.getCheckboxRecords() ?? [];
  if (rows.length === 0) {
    message.warning('请至少选择一条记录');
    return;
  }
  Modal.confirm({
    content: `确认批量生成选中的 ${rows.length} 张表的代码吗？`,
    title: '提示',
    async onOk() {
      const tables = rows.map((row: GenApi.GenTable) => row.tableName);
      const blob = await batchDownloadGenCode(tables);
      saveBlob(blob, 'ruoyi.zip');
    },
  });
}
</script>

<template>
  <Page auto-content-height>
    <ImportModal @success="onRefresh" />
    <PreviewModal />
    <Grid table-title="代码生成列表">
      <template #toolbar-tools>
        <Button class="mr-2" @click="onBatchDownload">批量生成</Button>
        <Button type="primary" @click="onImport">
          <Plus class="size-5" />
          导入表
        </Button>
      </template>
      <template #action="{ row }">
        <VbenTableAction
          :actions="[
            { text: '预览', icon: 'lucide:eye', onClick: () => onPreview(row) },
            { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
            { text: '生成代码', icon: 'lucide:download', onClick: () => onDownload(row) },
          ]"
          :dropdown-actions="[
            {
              text: '同步',
              icon: 'lucide:refresh-cw',
              onClick: () => onSynch(row),
            },
            {
              text: '删除',
              icon: 'lucide:trash-2',
              danger: true,
              popConfirm: {
                title: `确认删除表'${row.tableName}'吗？`,
                confirm: () => onDelete(row),
              },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
