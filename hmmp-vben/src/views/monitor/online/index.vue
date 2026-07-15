<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { MonitorOnlineApi } from '#/api/monitor/online';

import { Page } from '@vben/common-ui';

import { message } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { VbenTableAction } from '@vben/common-ui';
import { forceLogout, getOnlineList } from '#/api/monitor/online';

import { useColumns, useGridFormSchema } from './data';

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    schema: useGridFormSchema(),
    submitOnChange: true,
  },
  gridOptions: {
    columns: useColumns(),
    height: 'auto',
    keepSource: true,
    pagerConfig: { enabled: false },
    proxyConfig: {
      ajax: {
        query: async (_page, formValues) => {
          return await getOnlineList(formValues);
        },
      },
    },
    rowConfig: { keyField: 'tokenId' },
    toolbarConfig: {
      custom: true,
      export: false,
      refresh: true,
      search: true,
      zoom: true,
    },
  } as VxeTableGridOptions<MonitorOnlineApi.SysUserOnline>,
});

function onRefresh() {
  gridApi.query();
}

function onForceLogout(row: MonitorOnlineApi.SysUserOnline) {
  forceLogout(row.tokenId!).then(() => {
    message.success('强退成功');
    onRefresh();
  });
}
</script>

<template>
  <Page auto-content-height>
    <Grid table-title="在线用户列表">
      <template #action="{ row }">
        <VbenTableAction
          :actions="[
            {
              text: '强退',
              icon: 'lucide:power-off',
              danger: true,
              popConfirm: { title: `确认强退用户'${row.userName}'吗？`, confirm: () => onForceLogout(row) },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
