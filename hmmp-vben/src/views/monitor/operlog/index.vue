<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { MonitorOperlogApi } from '#/api/monitor/operlog';

import { Page, useVbenModal } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';

import { Button, message, Modal } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { VbenTableAction } from '@vben/common-ui';
import {
  cleanOperlog,
  deleteOperlog,
  exportOperlog,
  getOperlogList,
} from '#/api/monitor/operlog';
import { compactSearchFormOptions } from '#/views/_shared/table-search';

import { useColumns, useGridFormSchema } from './data';
import Detail from './modules/detail.vue';

const [DetailModal, detailModalApi] = useVbenModal({
  connectedComponent: Detail,
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
          return await getOperlogList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'operId' },
    toolbarConfig: {
      custom: true,
      export: false,
      refresh: true,
      search: true,
      zoom: true,
    },
  } as VxeTableGridOptions<MonitorOperlogApi.SysOperLog>,
});

function confirm(content: string, title: string) {
  return new Promise((resolve, reject) => {
    Modal.confirm({
      content,
      onCancel: () => reject(new Error('已取消')),
      onOk: () => resolve(true),
      title,
    });
  });
}

function onRefresh() {
  gridApi.query();
}

async function onExport() {
  const formValues = (await gridApi.formApi?.getValues()) ?? {};
  await exportOperlog(formValues);
}

function onView(row: MonitorOperlogApi.SysOperLog) {
  detailModalApi.setData(row).open();
}

function onDelete(row: MonitorOperlogApi.SysOperLog) {
  deleteOperlog(row.operId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}

async function onBatchDelete() {
  const rows = gridApi.grid?.getCheckboxRecords() as
    | MonitorOperlogApi.SysOperLog[]
    | undefined;
  if (!rows || rows.length === 0) {
    message.warning('请至少选择一条记录');
    return;
  }
  try {
    await confirm(`确认删除选中的${rows.length}条日志吗？`, '提示');
    await deleteOperlog(rows.map((row) => row.operId!));
    message.success('删除成功');
    onRefresh();
  } catch {
    // 用户取消
  }
}

async function onClean() {
  try {
    await confirm('确认清空所有操作日志吗？', '提示');
    await cleanOperlog();
    message.success('清空成功');
    onRefresh();
  } catch {
    // 用户取消
  }
}
</script>

<template>
  <Page auto-content-height>
    <DetailModal />
    <Grid table-title="操作日志列表">
      <template #toolbar-tools>
        <Button class="mr-2" @click="onExport">导出</Button>
        <Button danger @click="onBatchDelete">
          <IconifyIcon icon="lucide:trash-2" class="size-5" />
          删除
        </Button>
        <Button danger @click="onClean">清空</Button>
      </template>
      <template #action="{ row }">
        <VbenTableAction
          :actions="[
            { text: '查看详情', icon: 'lucide:eye', onClick: () => onView(row) },
          ]"
          :dropdown-actions="[
            {
              text: '删除',
              icon: 'lucide:trash-2',
              danger: true,
              popConfirm: { title: '确认删除该条日志吗？', confirm: () => onDelete(row) },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
