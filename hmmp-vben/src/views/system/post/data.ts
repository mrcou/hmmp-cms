import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemPostApi } from '#/api/system/post';

export const STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

export const STATUS_TAG_OPTIONS = [
  { color: 'success', label: '正常', value: '0' },
  { color: 'error', label: '停用', value: '1' },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'postCode', label: '岗位编码' },
    { component: 'Input', fieldName: 'postName', label: '岗位名称' },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: STATUS_OPTIONS },
      fieldName: 'status',
      label: '状态',
    },
  ];
}

export function useFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Input',
      fieldName: 'postCode',
      label: '岗位编码',
      rules: 'required',
    },
    {
      component: 'Input',
      fieldName: 'postName',
      label: '岗位名称',
      rules: 'required',
    },
    {
      component: 'InputNumber',
      defaultValue: 0,
      fieldName: 'postSort',
      label: '显示顺序',
      rules: 'required',
    },
    {
      component: 'RadioGroup',
      componentProps: {
        buttonStyle: 'solid',
        options: STATUS_OPTIONS,
        optionType: 'button',
      },
      defaultValue: '0',
      fieldName: 'status',
      label: '状态',
    },
    { component: 'Textarea', fieldName: 'remark', label: '备注' },
  ];
}

export function useColumns(): VxeTableGridColumns<SystemPostApi.SysPost> {
  return [
    { field: 'postId', minWidth: 90, title: '岗位编号' },
    { field: 'postCode', minWidth: 130, title: '岗位编码' },
    { field: 'postName', minWidth: 130, title: '岗位名称' },
    { field: 'postSort', minWidth: 100, title: '显示顺序' },
    {
      cellRender: { name: 'CellTag', options: STATUS_TAG_OPTIONS },
      field: 'status',
      minWidth: 100,
      title: '状态',
    },
    { field: 'createTime', minWidth: 170, title: '创建时间' },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      minWidth: 180,
      showOverflow: false,
      slots: { default: 'action' },
      title: '操作',
    },
  ];
}
