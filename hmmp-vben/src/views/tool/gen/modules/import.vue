<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { GenApi } from '#/api/tool/gen';

import { useVbenModal } from '@vben/common-ui';

import { message } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { getDbTableList, importTable } from '#/api/tool/gen';

import { useDbColumns, useGridFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    schema: useGridFormSchema(),
    submitOnChange: true,
  },
  gridOptions: {
    columns: useDbColumns(),
    height: 400,
    keepSource: true,
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          return await getDbTableList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'tableName' },
    toolbarConfig: { custom: false, export: false, refresh: true, search: true },
  } as VxeTableGridOptions<GenApi.GenTable>,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const rows = gridApi.grid?.getCheckboxRecords() ?? [];
    if (rows.length === 0) {
      message.warning('请至少选择一张表');
      return;
    }
    modalApi.lock();
    try {
      const tables = rows.map((row: GenApi.GenTable) => row.tableName).join(',');
      await importTable(tables);
      message.success('导入成功');
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
});
</script>

<template>
  <Modal title="导入数据表" class="w-[800px]">
    <Grid />
  </Modal>
</template>
