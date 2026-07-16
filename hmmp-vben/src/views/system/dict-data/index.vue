<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SystemDictDataApi } from '#/api/system/dict-data';

import { computed } from 'vue';
import { useRoute } from 'vue-router';

import { Page, useVbenModal } from '@vben/common-ui';
import { Plus } from '@vben/icons';

import { Button, message } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { VbenTableAction } from '@vben/common-ui';
import { deleteDictData, getDictDataList } from '#/api/system/dict-data';

import { useColumns, useGridFormSchema } from './data';
import Form from './modules/form.vue';

const route = useRoute();
const dictType = computed(() => String(route.query.dictType ?? ''));

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
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
          return await getDictDataList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            dictType: dictType.value,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'dictCode' },
    toolbarConfig: { custom: true, export: false, refresh: true, search: true, zoom: true },
  } as VxeTableGridOptions<SystemDictDataApi.SysDictData>,
});

function onRefresh() {
  gridApi.query();
}

function onCreate() {
  formModalApi.setData({ dictType: dictType.value }).open();
}

function onEdit(row: SystemDictDataApi.SysDictData) {
  formModalApi.setData(row).open();
}

function onDelete(row: SystemDictDataApi.SysDictData) {
  deleteDictData(row.dictCode!).then(() => {
    message.success('删除成功');
    onRefresh();
  });
}
</script>

<template>
  <Page :title="`字典数据：${dictType}`" auto-content-height>
    <FormModal @success="onRefresh" />
    <Grid table-title="字典数据列表">
      <template #toolbar-tools>
        <Button type="primary" @click="onCreate">
          <Plus class="size-5" />
          新增数据
        </Button>
      </template>
      <template #action="{ row }">
        <VbenTableAction
          :actions="[
            { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
          ]"
          :dropdown-actions="[
            {
              text: '删除',
              icon: 'lucide:trash-2',
              danger: true,
              popConfirm: { title: `确认删除字典数据'${row.dictLabel}'吗？`, confirm: () => onDelete(row) },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
