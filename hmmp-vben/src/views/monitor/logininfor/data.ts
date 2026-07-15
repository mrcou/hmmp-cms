import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { MonitorLogininforApi } from '#/api/monitor/logininfor';

export const STATUS_OPTIONS = [
  { color: 'success', label: '成功', value: '0' },
  { color: 'error', label: '失败', value: '1' },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'ipaddr', label: '登录地址' },
    { component: 'Input', fieldName: 'userName', label: '用户账号' },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: STATUS_OPTIONS },
      fieldName: 'status',
      label: '登录状态',
    },
  ];
}

export function useColumns(): VxeTableGridColumns<MonitorLogininforApi.SysLogininfor> {
  return [
    { type: 'checkbox', width: 50 },
    { field: 'infoId', title: '访问编号', width: 90 },
    { field: 'userName', title: '用户账号', width: 120 },
    { field: 'ipaddr', title: '登录地址', width: 130 },
    { field: 'loginLocation', title: '登录地点', width: 150 },
    { field: 'browser', title: '浏览器', width: 120 },
    { field: 'os', title: '操作系统', width: 130 },
    {
      cellRender: { name: 'CellTag', options: STATUS_OPTIONS },
      field: 'status',
      title: '登录状态',
      width: 90,
    },
    { field: 'msg', title: '操作信息', minWidth: 160 },
    { field: 'loginTime', title: '登录日期', width: 170 },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      slots: { default: 'action' },
      title: '操作',
      width: 160,
    },
  ];
}
