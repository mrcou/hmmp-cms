<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemNoticeApi } from '#/api/system/notice';

import { Page, useVbenModal } from '@vben/common-ui';
import { Plus } from '@vben/icons';

import { Button, message } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { VbenTableAction } from '@vben/common-ui';
import { deleteNotice, getNoticeList } from '#/api/system/notice';

import { useColumns, useGridFormSchema } from './data';
import Form from './modules/form.vue';
import Preview from './modules/preview.vue';

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnClose: true,
});

const [PreviewModal, previewModalApi] = useVbenModal({
  connectedComponent: Preview,
  destroyOnClose: true,
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
          return await getNoticeList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'noticeId' },
    toolbarConfig: {
      custom: true,
      export: false,
      refresh: true,
      search: true,
      zoom: true,
    },
    width: '100%',
  } as VxeTableGridOptions<SystemNoticeApi.SysNotice>,
});

function onRefresh() {
  gridApi.query();
}

function onCreate() {
  formModalApi.setData({}).open();
}

function onEdit(row: SystemNoticeApi.SysNotice) {
  formModalApi.setData(row).open();
}

function onPreview(row: SystemNoticeApi.SysNotice) {
  previewModalApi.setData({ noticeId: row.noticeId }).open();
}

function onDelete(row: SystemNoticeApi.SysNotice) {
  deleteNotice(row.noticeId!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}
</script>

<template>
  <Page auto-content-height>
    <FormModal @success="onRefresh" />
    <PreviewModal />
    <Grid class="w-full" table-title="通知公告列表">
      <template #toolbar-tools>
        <Button type="primary" @click="onCreate">
          <Plus class="size-5" />
          新增公告
        </Button>
      </template>
      <template #action="{ row }">
        <VbenTableAction
          :actions="[
            { text: '预览', icon: 'lucide:eye', onClick: () => onPreview(row) },
            { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
            {
              text: '删除',
              icon: 'lucide:trash-2',
              danger: true,
              popConfirm: {
                title: `确认删除公告'${row.noticeTitle}'吗？`,
                confirm: () => onDelete(row),
              },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
