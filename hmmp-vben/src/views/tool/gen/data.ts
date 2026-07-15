import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { GenApi } from '#/api/tool/gen';

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'tableName', label: '表名称' },
    { component: 'Input', fieldName: 'tableComment', label: '表描述' },
  ];
}

export function useColumns(): VxeTableGridColumns<GenApi.GenTable> {
  return [
    { type: 'checkbox', width: 50 },
    { field: 'tableName', title: '表名称', width: 160 },
    { field: 'tableComment', title: '表描述', width: 200 },
    { field: 'className', title: '实体类名称', width: 160 },
    { field: 'functionAuthor', title: '作者', width: 100 },
    { field: 'createTime', title: '创建时间', width: 160 },
    { field: 'updateTime', title: '更新时间', width: 160 },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      slots: { default: 'action' },
      title: '操作',
      width: 280,
    },
  ];
}

export function useDbColumns(): VxeTableGridColumns<GenApi.GenTable> {
  return [
    { type: 'checkbox', width: 50 },
    { field: 'tableName', title: '表名称', width: 200 },
    { field: 'tableComment', title: '表描述', width: 260 },
    { field: 'createTime', title: '创建时间', width: 160 },
    { field: 'updateTime', title: '更新时间', width: 160 },
  ];
}
