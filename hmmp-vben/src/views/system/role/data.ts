import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemRoleApi } from '#/api/system/role';

export const STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

export const DATA_SCOPE_OPTIONS = [
  { label: '全部数据权限', value: '1' },
  { label: '自定义数据权限', value: '2' },
  { label: '本部门数据权限', value: '3' },
  { label: '本部门及以下数据权限', value: '4' },
  { label: '仅本人数据权限', value: '5' },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'roleName', label: '角色名称' },
    { component: 'Input', fieldName: 'roleKey', label: '权限字符' },
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
    { component: 'Input', fieldName: 'roleName', label: '角色名称', rules: 'required' },
    { component: 'Input', fieldName: 'roleKey', label: '权限字符', rules: 'required' },
    {
      component: 'InputNumber',
      defaultValue: 0,
      fieldName: 'roleSort',
      label: '显示顺序',
      rules: 'required',
    },
    {
      component: 'RadioGroup',
      componentProps: { buttonStyle: 'solid', options: STATUS_OPTIONS, optionType: 'button' },
      defaultValue: '0',
      fieldName: 'status',
      label: '状态',
    },
    { component: 'Textarea', fieldName: 'remark', label: '备注' },
  ];
}

export function useColumns(
  onStatusChange: (
    newStatus: string,
    row: SystemRoleApi.SystemRole,
  ) => PromiseLike<boolean | undefined>,
): VxeTableGridColumns<SystemRoleApi.SystemRole> {
  return [
    { field: 'roleId', minWidth: 100, title: '角色编号' },
    { field: 'roleName', minWidth: 160, title: '角色名称' },
    { field: 'roleKey', minWidth: 160, title: '权限字符' },
    { field: 'roleSort', minWidth: 100, title: '显示顺序' },
    {
      cellRender: { attrs: { beforeChange: onStatusChange }, name: 'CellSwitch' },
      field: 'status',
      minWidth: 110,
      title: '状态',
    },
    { field: 'createTime', minWidth: 180, title: '创建时间' },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      minWidth: 360,
      showOverflow: false,
      slots: { default: 'action' },
      title: '操作',
    },
  ];
}
