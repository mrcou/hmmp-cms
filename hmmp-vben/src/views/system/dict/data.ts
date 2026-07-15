import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemDictTypeApi } from '#/api/system/dict-type';

export const STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'dictName', label: '字典名称' },
    { component: 'Input', fieldName: 'dictType', label: '字典类型' },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: STATUS_OPTIONS },
      fieldName: 'status',
      label: '状态',
    },
  ];
}

export function useFormSchema(isEdit: boolean): VbenFormSchema[] {
  return [
    {
      component: 'Input',
      fieldName: 'dictName',
      label: '字典名称',
      rules: 'required',
    },
    {
      component: 'Input',
      componentProps: { disabled: isEdit },
      fieldName: 'dictType',
      label: '字典类型',
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

export function useColumns(): VxeTableGridColumns<SystemDictTypeApi.SysDictType> {
  return [
    { field: 'dictId', title: '字典编号', width: 90 },
    { field: 'dictName', title: '字典名称', width: 150 },
    { field: 'dictType', title: '字典类型', width: 200 },
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
      width: 280,
    },
  ];
}
