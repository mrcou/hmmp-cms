import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { MonitorOnlineApi } from '#/api/monitor/online';

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'ipaddr', label: '登录地址' },
    { component: 'Input', fieldName: 'userName', label: '用户名称' },
  ];
}

export function useColumns(): VxeTableGridColumns<MonitorOnlineApi.SysUserOnline> {
  return [
    { field: 'tokenId', title: '会话编号', minWidth: 200 },
    { field: 'deptName', title: '所属部门', width: 130 },
    { field: 'userName', title: '登录名称', width: 120 },
    { field: 'ipaddr', title: '登录地址', width: 130 },
    { field: 'loginLocation', title: '登录地点', width: 150 },
    { field: 'browser', title: '浏览器', width: 120 },
    { field: 'os', title: '操作系统', width: 130 },
    {
      field: 'loginTime',
      formatter: ({ cellValue }) =>
        cellValue ? new Date(cellValue).toLocaleString() : '',
      title: '登录时间',
      width: 170,
    },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      slots: { default: 'action' },
      title: '操作',
      width: 100,
    },
  ];
}
