import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemConfigApi } from '#/api/system/config';

export const CONFIG_TYPE_OPTIONS = [
  { label: '是', value: 'Y' },
  { label: '否', value: 'N' },
];

export const CONFIG_TYPE_TAG_OPTIONS = [
  { color: 'success', label: '是', value: 'Y' },
  { color: 'default', label: '否', value: 'N' },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'configName', label: '参数名称' },
    { component: 'Input', fieldName: 'configKey', label: '参数键名' },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: CONFIG_TYPE_OPTIONS },
      fieldName: 'configType',
      label: '系统内置',
    },
  ];
}

export function useFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Input',
      fieldName: 'configName',
      label: '参数名称',
      rules: 'required',
    },
    {
      component: 'Input',
      fieldName: 'configKey',
      label: '参数键名',
      rules: 'required',
    },
    {
      component: 'Input',
      fieldName: 'configValue',
      label: '参数键值',
      rules: 'required',
    },
    {
      component: 'RadioGroup',
      componentProps: {
        buttonStyle: 'solid',
        options: CONFIG_TYPE_OPTIONS,
        optionType: 'button',
      },
      defaultValue: 'N',
      fieldName: 'configType',
      label: '系统内置',
    },
    { component: 'Textarea', fieldName: 'remark', label: '备注' },
  ];
}

export function useColumns(): VxeTableGridColumns<SystemConfigApi.SysConfig> {
  return [
    { field: 'configId', minWidth: 110, title: '参数主键' },
    { field: 'configName', minWidth: 220, title: '参数名称' },
    { field: 'configKey', minWidth: 240, title: '参数键名' },
    { field: 'configValue', minWidth: 240, title: '参数键值' },
    {
      cellRender: { name: 'CellTag', options: CONFIG_TYPE_TAG_OPTIONS },
      field: 'configType',
      minWidth: 120,
      title: '系统内置',
    },
    { field: 'remark', minWidth: 220, title: '备注' },
    { field: 'createTime', minWidth: 190, title: '创建时间' },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      slots: { default: 'action' },
      title: '操作',
      width: 180,
    },
  ];
}
