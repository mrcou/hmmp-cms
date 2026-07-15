import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemMenuApi } from '#/api/system/menu';

export const MENU_TYPE_OPTIONS = [
  { color: 'processing', label: '目录', value: 'M' },
  { color: 'success', label: '菜单', value: 'C' },
  { color: 'error', label: '按钮', value: 'F' },
];

export const VISIBLE_OPTIONS = [
  { label: '显示', value: '0' },
  { label: '隐藏', value: '1' },
];

export const STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

export const FRAME_OPTIONS = [
  { label: '是', value: '0' },
  { label: '否', value: '1' },
];

export const CACHE_OPTIONS = [
  { label: '缓存', value: '0' },
  { label: '不缓存', value: '1' },
];

export function useFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'RadioGroup',
      componentProps: { buttonStyle: 'solid', options: MENU_TYPE_OPTIONS, optionType: 'button' },
      defaultValue: 'M',
      fieldName: 'menuType',
      label: '菜单类型',
    },
    {
      component: 'ApiTreeSelect',
      componentProps: {
        allowClear: true,
        api: async () => {
          const { getMenuTreeSelect } = await import('#/api/system/menu');
          return await getMenuTreeSelect();
        },
        childrenField: 'children',
        class: 'w-full',
        labelField: 'label',
        valueField: 'id',
      },
      fieldName: 'parentId',
      label: '上级菜单',
    },
    { component: 'Input', fieldName: 'menuName', label: '菜单名称', rules: 'required' },
    {
      component: 'InputNumber',
      defaultValue: 0,
      fieldName: 'orderNum',
      label: '显示排序',
      rules: 'required',
    },
    {
      component: 'Input',
      dependencies: {
        show: (values) => ['C', 'M'].includes(values.menuType),
        triggerFields: ['menuType'],
      },
      fieldName: 'path',
      label: '路由地址',
    },
    {
      component: 'Input',
      dependencies: {
        show: (values) => values.menuType === 'C',
        triggerFields: ['menuType'],
      },
      fieldName: 'component',
      label: '组件路径',
    },
    {
      component: 'Input',
      dependencies: {
        show: (values) => values.menuType === 'C',
        triggerFields: ['menuType'],
      },
      fieldName: 'routeName',
      label: '路由名称',
    },
    {
      component: 'Input',
      dependencies: {
        show: (values) => ['C', 'F'].includes(values.menuType),
        triggerFields: ['menuType'],
      },
      fieldName: 'perms',
      label: '权限字符',
    },
    {
      component: 'IconPicker',
      dependencies: {
        show: (values) => ['C', 'M'].includes(values.menuType),
        triggerFields: ['menuType'],
      },
      fieldName: 'icon',
      label: '菜单图标',
    },
    {
      component: 'RadioGroup',
      componentProps: { options: FRAME_OPTIONS },
      defaultValue: '1',
      dependencies: {
        show: (values) => values.menuType === 'C',
        triggerFields: ['menuType'],
      },
      fieldName: 'isFrame',
      label: '是否外链',
    },
    {
      component: 'RadioGroup',
      componentProps: { options: CACHE_OPTIONS },
      defaultValue: '0',
      dependencies: {
        show: (values) => values.menuType === 'C',
        triggerFields: ['menuType'],
      },
      fieldName: 'isCache',
      label: '是否缓存',
    },
    {
      component: 'RadioGroup',
      componentProps: { options: VISIBLE_OPTIONS },
      defaultValue: '0',
      dependencies: {
        show: (values) => ['C', 'M'].includes(values.menuType),
        triggerFields: ['menuType'],
      },
      fieldName: 'visible',
      label: '显示状态',
    },
    {
      component: 'RadioGroup',
      componentProps: { options: STATUS_OPTIONS },
      defaultValue: '0',
      dependencies: {
        show: (values) => ['C', 'M'].includes(values.menuType),
        triggerFields: ['menuType'],
      },
      fieldName: 'status',
      label: '菜单状态',
    },
  ];
}

export function useColumns(): VxeTableGridColumns<SystemMenuApi.SystemMenu> {
  return [
    { field: 'menuName', title: '菜单名称', treeNode: true, width: 220 },
    { field: 'icon', title: '图标', width: 70 },
    { field: 'orderNum', title: '排序', width: 70 },
    { field: 'perms', title: '权限标识', width: 200 },
    { field: 'component', title: '组件路径', minWidth: 180 },
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
