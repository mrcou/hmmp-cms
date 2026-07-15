import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemUserApi } from '#/api/system/user';

export const SEX_OPTIONS = [
  { label: '男', value: '0' },
  { label: '女', value: '1' },
  { label: '未知', value: '2' },
];

export const STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'userName', label: '用户名称' },
    { component: 'Input', fieldName: 'phonenumber', label: '手机号码' },
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
      component: 'ApiTreeSelect',
      componentProps: {
        allowClear: true,
        api: async () => {
          const { getUserDeptTree } = await import('#/api/system/user');
          const res = await getUserDeptTree();
          return res.data;
        },
        childrenField: 'children',
        class: 'w-full',
        labelField: 'label',
        valueField: 'id',
      },
      fieldName: 'deptId',
      label: '归属部门',
      rules: 'required',
    },
    { component: 'Input', fieldName: 'nickName', label: '用户昵称', rules: 'required' },
    {
      component: 'Input',
      componentProps: { disabled: isEdit },
      fieldName: 'userName',
      label: '用户名称',
      rules: 'required',
    },
    {
      component: 'InputPassword',
      dependencies: { show: () => !isEdit, triggerFields: ['userName'] },
      fieldName: 'password',
      label: '用户密码',
      rules: isEdit ? undefined : 'required',
    },
    { component: 'Input', fieldName: 'phonenumber', label: '手机号码' },
    { component: 'Input', fieldName: 'email', label: '邮箱' },
    {
      component: 'RadioGroup',
      componentProps: { options: SEX_OPTIONS },
      defaultValue: '0',
      fieldName: 'sex',
      label: '性别',
    },
    {
      component: 'RadioGroup',
      componentProps: { buttonStyle: 'solid', options: STATUS_OPTIONS, optionType: 'button' },
      defaultValue: '0',
      fieldName: 'status',
      label: '状态',
    },
    {
      component: 'ApiSelect',
      componentProps: {
        api: async () => {
          const { getUser } = await import('#/api/system/user');
          const res = await getUser();
          return res.posts;
        },
        class: 'w-full',
        labelField: 'postName',
        mode: 'multiple',
        valueField: 'postId',
      },
      fieldName: 'postIds',
      label: '岗位',
    },
    {
      component: 'ApiSelect',
      componentProps: {
        api: async () => {
          const { getUser } = await import('#/api/system/user');
          const res = await getUser();
          return res.roles;
        },
        class: 'w-full',
        labelField: 'roleName',
        mode: 'multiple',
        valueField: 'roleId',
      },
      fieldName: 'roleIds',
      label: '角色',
    },
    { component: 'Textarea', fieldName: 'remark', label: '备注' },
  ];
}

export function useColumns(
  onStatusChange: (
    newStatus: string,
    row: SystemUserApi.SystemUser,
  ) => PromiseLike<boolean | undefined>,
): VxeTableGridColumns<SystemUserApi.SystemUser> {
  return [
    { field: 'userId', title: '用户编号', width: 90 },
    { field: 'userName', title: '用户名称', width: 130 },
    { field: 'nickName', title: '用户昵称', width: 130 },
    { field: 'dept.deptName', title: '部门', width: 130 },
    { field: 'phonenumber', title: '手机号码', width: 130 },
    {
      cellRender: { attrs: { beforeChange: onStatusChange }, name: 'CellSwitch' },
      field: 'status',
      title: '状态',
      width: 100,
    },
    { field: 'createTime', title: '创建时间', width: 170 },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      slots: { default: 'action' },
      title: '操作',
      width: 220,
    },
  ];
}
