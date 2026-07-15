import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemDeptApi } from '#/api/system/dept';

export const STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

export function useFormSchema(currentDeptId?: number): VbenFormSchema[] {
  return [
    {
      component: 'ApiTreeSelect',
      componentProps: {
        allowClear: true,
        api: async () => {
          const { getDeptExcludeChild, getDeptList } = await import(
            '#/api/system/dept'
          );
          return currentDeptId
            ? await getDeptExcludeChild(currentDeptId)
            : await getDeptList();
        },
        childrenField: 'children',
        class: 'w-full',
        labelField: 'deptName',
        valueField: 'deptId',
      },
      fieldName: 'parentId',
      label: '上级部门',
      rules: 'required',
    },
    { component: 'Input', fieldName: 'deptName', label: '部门名称', rules: 'required' },
    {
      component: 'InputNumber',
      defaultValue: 0,
      fieldName: 'orderNum',
      label: '显示排序',
      rules: 'required',
    },
    { component: 'Input', fieldName: 'leader', label: '负责人' },
    { component: 'Input', fieldName: 'phone', label: '联系电话' },
    { component: 'Input', fieldName: 'email', label: '邮箱' },
    {
      component: 'RadioGroup',
      componentProps: { buttonStyle: 'solid', options: STATUS_OPTIONS, optionType: 'button' },
      defaultValue: '0',
      fieldName: 'status',
      label: '部门状态',
    },
  ];
}

export function useColumns(): VxeTableGridColumns<SystemDeptApi.SystemDept> {
  return [
    { field: 'deptName', minWidth: 220, title: '部门名称', treeNode: true },
    { field: 'orderNum', title: '排序', width: 80 },
    { field: 'leader', title: '负责人', width: 120 },
    { field: 'phone', title: '联系电话', width: 130 },
    {
      cellRender: { name: 'CellTag', options: STATUS_OPTIONS },
      field: 'status',
      title: '状态',
      width: 90,
    },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      slots: { default: 'action' },
      title: '操作',
      width: 260,
    },
  ];
}
