import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemDictDataApi } from '#/api/system/dict-data';

export const STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

export const IS_DEFAULT_OPTIONS = [
  { label: '是', value: 'Y' },
  { label: '否', value: 'N' },
];

export const LIST_CLASS_OPTIONS = [
  { label: 'default', value: 'default' },
  { label: 'primary', value: 'primary' },
  { label: 'success', value: 'success' },
  { label: 'info', value: 'info' },
  { label: 'warning', value: 'warning' },
  { label: 'danger', value: 'danger' },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'dictLabel', label: '字典标签' },
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
      fieldName: 'dictLabel',
      label: '数据标签',
      rules: 'required',
    },
    {
      component: 'Input',
      fieldName: 'dictValue',
      label: '数据键值',
      rules: 'required',
    },
    {
      component: 'InputNumber',
      defaultValue: 0,
      fieldName: 'dictSort',
      label: '显示排序',
      rules: 'required',
    },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: LIST_CLASS_OPTIONS },
      fieldName: 'listClass',
      label: '回显样式',
    },
    { component: 'Input', fieldName: 'cssClass', label: '样式属性' },
    {
      component: 'RadioGroup',
      componentProps: { options: IS_DEFAULT_OPTIONS },
      defaultValue: 'N',
      fieldName: 'isDefault',
      label: '是否默认',
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

export function useColumns(): VxeTableGridColumns<SystemDictDataApi.SysDictData> {
  return [
    { field: 'dictCode', title: '字典编码', width: 90 },
    { field: 'dictLabel', title: '字典标签', width: 150 },
    { field: 'dictValue', title: '字典键值', width: 150 },
    { field: 'listClass', title: '样式属性', width: 120 },
    { field: 'dictSort', title: '排序', width: 90 },
    {
      cellRender: { name: 'CellTag', options: STATUS_OPTIONS },
      field: 'status',
      title: '状态',
      width: 90,
    },
    { field: 'remark', title: '备注', minWidth: 150 },
    { field: 'createTime', title: '创建时间', width: 170 },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      slots: { default: 'action' },
      title: '操作',
      width: 200,
    },
  ];
}
