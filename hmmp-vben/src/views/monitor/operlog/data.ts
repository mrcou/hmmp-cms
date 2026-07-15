import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { MonitorOperlogApi } from '#/api/monitor/operlog';

export const STATUS_OPTIONS = [
  { color: 'success', label: '正常', value: 0 },
  { color: 'error', label: '异常', value: 1 },
];

export const BUSINESS_TYPE_OPTIONS = [
  { color: 'default', label: '其他', value: 0 },
  { color: 'success', label: '新增', value: 1 },
  { color: 'processing', label: '修改', value: 2 },
  { color: 'error', label: '删除', value: 3 },
  { color: 'purple', label: '授权', value: 4 },
  { color: 'cyan', label: '导出', value: 5 },
  { color: 'blue', label: '导入', value: 6 },
  { color: 'magenta', label: '强退', value: 7 },
  { color: 'geekblue', label: '生成代码', value: 8 },
  { color: 'volcano', label: '清空数据', value: 9 },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'title', label: '模块标题' },
    { component: 'Input', fieldName: 'operName', label: '操作人员' },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: STATUS_OPTIONS },
      fieldName: 'status',
      label: '操作状态',
    },
  ];
}

export function useColumns(): VxeTableGridColumns<MonitorOperlogApi.SysOperLog> {
  return [
    { type: 'checkbox', width: 50 },
    { field: 'operId', title: '日志编号', width: 90 },
    { field: 'title', title: '系统模块', width: 130 },
    {
      cellRender: { name: 'CellTag', options: BUSINESS_TYPE_OPTIONS },
      field: 'businessType',
      title: '操作类型',
      width: 110,
    },
    { field: 'requestMethod', title: '请求方式', width: 90 },
    { field: 'operName', title: '操作人员', width: 110 },
    { field: 'operIp', title: '主机', width: 130 },
    { field: 'operLocation', title: '操作地点', width: 150 },
    {
      cellRender: { name: 'CellTag', options: STATUS_OPTIONS },
      field: 'status',
      title: '操作状态',
      width: 90,
    },
    { field: 'operTime', title: '操作日期', width: 170 },
    {
      field: 'costTime',
      formatter: ({ cellValue }) =>
        cellValue === null || cellValue === undefined
          ? ''
          : `${cellValue}毫秒`,
      title: '消耗时间',
      width: 110,
    },
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
