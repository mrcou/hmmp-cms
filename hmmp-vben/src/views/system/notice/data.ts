import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridColumns } from '#/adapter/vxe-table';
import type { SystemNoticeApi } from '#/api/system/notice';

export const NOTICE_TYPE_OPTIONS = [
  { label: '通知', value: '1' },
  { label: '公告', value: '2' },
];

export const NOTICE_TYPE_TAG_OPTIONS = [
  { color: 'blue', label: '通知', value: '1' },
  { color: 'orange', label: '公告', value: '2' },
];

export const STATUS_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '关闭', value: '1' },
];

export const STATUS_TAG_OPTIONS = [
  { color: 'success', label: '正常', value: '0' },
  { color: 'error', label: '关闭', value: '1' },
];

export function useGridFormSchema(): VbenFormSchema[] {
  return [
    { component: 'Input', fieldName: 'noticeTitle', label: '公告标题' },
    {
      component: 'Select',
      componentProps: { allowClear: true, options: NOTICE_TYPE_OPTIONS },
      fieldName: 'noticeType',
      label: '公告类型',
    },
  ];
}

export function useFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Input',
      fieldName: 'noticeTitle',
      label: '公告标题',
      rules: 'required',
    },
    {
      component: 'RadioGroup',
      componentProps: {
        buttonStyle: 'solid',
        options: NOTICE_TYPE_OPTIONS,
        optionType: 'button',
      },
      defaultValue: '1',
      fieldName: 'noticeType',
      label: '公告类型',
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
    {
      component: 'RichEditor',
      componentProps: {
        maxHeight: 520,
        minHeight: 260,
        placeholder: '请输入公告内容',
      },
      fieldName: 'noticeContent',
      label: '内容',
    },
  ];
}

export function useColumns(): VxeTableGridColumns<SystemNoticeApi.SysNotice> {
  return [
    { field: 'noticeId', title: '序号', width: 90 },
    { field: 'noticeTitle', minWidth: 220, title: '公告标题' },
    {
      cellRender: { name: 'CellTag', options: NOTICE_TYPE_TAG_OPTIONS },
      field: 'noticeType',
      title: '公告类型',
      width: 100,
    },
    {
      cellRender: { name: 'CellTag', options: STATUS_TAG_OPTIONS },
      field: 'status',
      title: '状态',
      width: 100,
    },
    { field: 'createBy', title: '创建者', width: 120 },
    { field: 'createTime', title: '创建时间', width: 170 },
    {
      align: 'center',
      field: 'operation',
      fixed: 'right',
      showOverflow: false,
      slots: { default: 'action' },
      title: '操作',
      width: 240,
    },
  ];
}
