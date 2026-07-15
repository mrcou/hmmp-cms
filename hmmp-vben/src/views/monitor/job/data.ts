import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { MonitorJobApi } from '#/api/monitor/job';

export const JOB_STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '暂停', value: '1' },
];

export const JOB_STATUS_TAG_OPTIONS = [
  { color: 'success', label: '正常', value: '0' },
  { color: 'error', label: '暂停', value: '1' },
];

export const JOB_LOG_STATUS_OPTIONS = [
  { color: 'success', label: '成功', value: '0' },
  { color: 'error', label: '失败', value: '1' },
];

export const JOB_GROUP_OPTIONS = [
  { label: '默认', value: 'DEFAULT' },
  { label: '系统', value: 'SYSTEM' },
];

export const MISFIRE_POLICY_OPTIONS = [
  { label: '默认', value: '0' },
  { label: '立即执行', value: '1' },
  { label: '执行一次', value: '2' },
  { label: '放弃执行', value: '3' },
];

export const CONCURRENT_OPTIONS = [
  { label: '允许', value: '0' },
  { label: '禁止', value: '1' },
];

export const CONCURRENT_TAG_OPTIONS = [
  { color: 'warning', label: '允许', value: '0' },
  { color: 'success', label: '禁止', value: '1' },
];

export function useJobGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'jobName', label: '任务名称' },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: JOB_GROUP_OPTIONS },
      fieldName: 'jobGroup',
      label: '任务分组',
    },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: JOB_STATUS_OPTIONS },
      fieldName: 'status',
      label: '任务状态',
    },
    { component: 'Input', fieldName: 'invokeTarget', label: '调用目标' },
  ];
}

export function useJobLogGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'jobName', label: '任务名称' },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: JOB_GROUP_OPTIONS },
      fieldName: 'jobGroup',
      label: '任务分组',
    },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: JOB_LOG_STATUS_OPTIONS },
      fieldName: 'status',
      label: '执行状态',
    },
    {
      component: 'RangePicker',
      componentProps: { valueFormat: 'YYYY-MM-DD' },
      fieldName: 'dateRange',
      label: '执行时间',
    },
  ];
}

export function useFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Input',
      fieldName: 'jobName',
      label: '任务名称',
      rules: 'required',
    },
    {
      component: 'Select',
      componentProps: { options: JOB_GROUP_OPTIONS },
      defaultValue: 'DEFAULT',
      fieldName: 'jobGroup',
      label: '任务分组',
      rules: 'selectRequired',
    },
    {
      component: 'Input',
      componentProps: {
        placeholder: "ryTask.ryNoParams 或 ryTask.ryParams('hmmp')",
      },
      fieldName: 'invokeTarget',
      label: '调用目标',
      rules: 'required',
    },
    {
      component: 'Input',
      componentProps: { placeholder: '0/30 * * * * ?' },
      fieldName: 'cronExpression',
      label: 'Cron表达式',
      rules: 'required',
    },
    {
      component: 'RadioGroup',
      componentProps: {
        buttonStyle: 'solid',
        options: MISFIRE_POLICY_OPTIONS,
        optionType: 'button',
      },
      defaultValue: '3',
      fieldName: 'misfirePolicy',
      label: '执行策略',
    },
    {
      component: 'RadioGroup',
      componentProps: {
        buttonStyle: 'solid',
        options: CONCURRENT_OPTIONS,
        optionType: 'button',
      },
      defaultValue: '1',
      fieldName: 'concurrent',
      label: '并发执行',
    },
    {
      component: 'RadioGroup',
      componentProps: {
        buttonStyle: 'solid',
        options: JOB_STATUS_OPTIONS,
        optionType: 'button',
      },
      defaultValue: '1',
      fieldName: 'status',
      label: '任务状态',
    },
    { component: 'Textarea', fieldName: 'remark', label: '备注' },
  ];
}

export function useJobColumns(
  onStatusChange: (
    newStatus: string,
    row: MonitorJobApi.SysJob,
  ) => PromiseLike<boolean | undefined>,
): VxeTableGridColumns<MonitorJobApi.SysJob> {
  return [
    { type: 'checkbox', width: 50 },
    { field: 'jobId', title: '任务编号', width: 90 },
    { field: 'jobName', minWidth: 160, title: '任务名称' },
    {
      cellRender: { name: 'CellTag', options: JOB_GROUP_OPTIONS },
      field: 'jobGroup',
      title: '任务分组',
      width: 100,
    },
    { field: 'invokeTarget', minWidth: 260, title: '调用目标' },
    { field: 'cronExpression', minWidth: 160, title: 'Cron表达式' },
    { field: 'nextValidTime', title: '下次执行时间', width: 170 },
    {
      cellRender: { attrs: { beforeChange: onStatusChange }, name: 'CellSwitch' },
      field: 'status',
      title: '状态',
      width: 90,
    },
    {
      cellRender: { name: 'CellTag', options: CONCURRENT_TAG_OPTIONS },
      field: 'concurrent',
      title: '并发',
      width: 90,
    },
    { field: 'createTime', title: '创建时间', width: 170 },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      showOverflow: false,
      slots: { default: 'action' },
      title: '操作',
      width: 320,
    },
  ];
}

export function useJobLogColumns(): VxeTableGridColumns<MonitorJobApi.SysJobLog> {
  return [
    { type: 'checkbox', width: 50 },
    { field: 'jobLogId', title: '日志编号', width: 90 },
    { field: 'jobName', minWidth: 150, title: '任务名称' },
    {
      cellRender: { name: 'CellTag', options: JOB_GROUP_OPTIONS },
      field: 'jobGroup',
      title: '任务分组',
      width: 100,
    },
    { field: 'invokeTarget', minWidth: 260, title: '调用目标' },
    { field: 'jobMessage', minWidth: 240, title: '日志信息' },
    {
      cellRender: { name: 'CellTag', options: JOB_LOG_STATUS_OPTIONS },
      field: 'status',
      title: '执行状态',
      width: 90,
    },
    { field: 'exceptionInfo', minWidth: 220, title: '异常信息' },
    { field: 'startTime', title: '开始时间', width: 170 },
    { field: 'endTime', title: '结束时间', width: 170 },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      slots: { default: 'action' },
      title: '操作',
      width: 150,
    },
  ];
}
