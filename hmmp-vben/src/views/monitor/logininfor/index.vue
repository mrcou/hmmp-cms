<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { MonitorLogininforApi } from '#/api/monitor/logininfor';

import { Page } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';

import { Button, message, Modal } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { VbenTableAction } from '@vben/common-ui';
import {
  cleanLogininfor,
  deleteLogininfor,
  exportLogininfor,
  getLogininforList,
  unlockLogininfor,
} from '#/api/monitor/logininfor';
import { compactSearchFormOptions } from '#/views/_shared/table-search';

import { useColumns, useGridFormSchema } from './data';

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
          return await getLogininforList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'infoId' },
    toolbarConfig: {
      custom: true,
      export: false,
      refresh: true,
      search: true,
      zoom: true,
    },
  } as VxeTableGridOptions<MonitorLogininforApi.SysLogininfor>,
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
  await exportLogininfor(formValues);
}

function onDelete(row: MonitorLogininforApi.SysLogininfor) {
  deleteLogininfor(row.infoId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}

async function onUnlock(row: MonitorLogininforApi.SysLogininfor) {
  try {
    await confirm(`确认解锁账号"${row.userName}"吗？`, '提示');
    await unlockLogininfor(row.userName!);
    message.success('解锁成功');
    onRefresh();
  } catch {
    // 用户取消
  }
}

async function onBatchDelete() {
  const rows = gridApi.grid?.getCheckboxRecords() as
    | MonitorLogininforApi.SysLogininfor[]
    | undefined;
  if (!rows || rows.length === 0) {
    message.warning('请至少选择一条记录');
    return;
  }
  try {
    await confirm(`确认删除选中的${rows.length}条日志吗？`, '提示');
    await deleteLogininfor(rows.map((row) => row.infoId!));
    message.success('删除成功');
    onRefresh();
  } catch {
    // 用户取消
  }
}

async function onClean() {
  try {
    await confirm('确认清空所有登录日志吗？', '提示');
    await cleanLogininfor();
    message.success('清空成功');
    onRefresh();
  } catch {
    // 用户取消
  }
}
</script>

<template>
  <Page auto-content-height>
    <Grid table-title="登录日志列表">
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
            { text: '账号解锁', icon: 'lucide:unlock', onClick: () => onUnlock(row) },
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
