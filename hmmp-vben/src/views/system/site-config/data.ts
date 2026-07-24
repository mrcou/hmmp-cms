import type { VbenFormSchema } from '#/adapter/form';

export const PASSWORD_CHECK_OPTIONS = [
  { label: '不做复杂度验证', value: '' },
  {
    label: '密码必须超过8位，一般验证，密码可以是数字、字母或者特殊字符',
    value: '1',
  },
  {
    label:
      '密码必须超过8位，中等验证，密码必须包含数字、字母或特殊字符中的任意两种',
    value: '2',
  },
  {
    label: '密码必须超过8位，高强度验证，密码必须同时包含数字、字母和特殊字符',
    value: '3',
  },
];

export const YES_NO_OPTIONS = [
  { label: '否', value: '' },
  { label: '是', value: '1' },
];

export const LOGIN_REMINDER_OPTIONS = [
  { label: '不开启', value: '' },
  { label: '所有用户全部开启', value: '1' },
  { label: '用户自行选择开启', value: '2' },
];

/** 下拉框通用样式：占满表单项宽度 */
const BASE_SELECT_PROPS = {
  class: 'site-config-select',
  style: { width: '100%' },
};

/** 长选项下拉框：弹层宽度随最长选项内容自适应 */
const LONG_SELECT_PROPS = {
  ...BASE_SELECT_PROPS,
  classNames: {
    popup: {
      root: 'site-config-select-popup',
    },
  },
  popupMatchSelectWidth: false,
  styles: {
    popup: {
      listItem: {
        lineHeight: '1.5',
        whiteSpace: 'nowrap',
      },
      root: {
        width: 'max-content',
      },
    },
  },
};

export function useSecurityFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'dividerPassword',
      formItemClass: 'col-span-full',
      label: '密码安全设置',
    },
    {
      component: 'Select',
      componentProps: {
        ...LONG_SELECT_PROPS,
        options: PASSWORD_CHECK_OPTIONS,
      },
      fieldName: 'passwordCheck',
      label: '密码复杂度验证',
    },
    {
      component: 'Select',
      componentProps: {
        ...BASE_SELECT_PROPS,
        options: YES_NO_OPTIONS,
      },
      fieldName: 'forcedPasswordChange',
      label: '新用户强制改密',
    },
    {
      component: 'InputNumber',
      componentProps: { class: 'w-full', min: 0, max: 999, precision: 0 },
      defaultValue: 0,
      fieldName: 'passwordLife',
      help: '有效值1-999。如果设置为0，则密码不过期。默认值0。',
      label: '密码使用期限(天)',
    },
    {
      component: 'Divider',
      fieldName: 'dividerLock',
      formItemClass: 'col-span-full',
      label: '用户锁定',
    },
    {
      component: 'InputNumber',
      componentProps: { class: 'w-full', min: 1, max: 10, precision: 0 },
      defaultValue: 3,
      fieldName: 'passwordErrorNum',
      help: '有效值1-10，默认值3。',
      label: '密码尝试次数',
    },
    {
      component: 'InputNumber',
      componentProps: { class: 'w-full', min: 0, max: 10080, precision: 0 },
      defaultValue: 60,
      fieldName: 'lockTime',
      help: '有效值0-10080。如果设置为0，则锁定帐户直到管理员解除。默认值60。',
      label: '锁定时长(分钟)',
    },
    {
      component: 'InputNumber',
      componentProps: { class: 'w-full', min: 1, max: 10080, precision: 0 },
      defaultValue: 60,
      fieldName: 'resetCounter',
      help: '有效值1-10080。登录尝试密码失败之后，将登录尝试失败计数器重置为0次所需要的时间。默认值60。',
      label: '重置计数器(分钟)',
    },
    {
      component: 'Divider',
      fieldName: 'dividerLogin',
      formItemClass: 'col-span-full',
      label: '登录控制',
    },
    {
      component: 'InputNumber',
      componentProps: { class: 'w-full', min: 1, max: 43200, precision: 0 },
      defaultValue: 30,
      fieldName: 'cookieExpires',
      help: '有效值1-43200。当用户超过设定时长无操作时，再次操作需要重新登录。默认30。',
      label: '登录超时时间(分钟)',
    },
    {
      component: 'Select',
      componentProps: {
        ...LONG_SELECT_PROPS,
        options: LOGIN_REMINDER_OPTIONS,
      },
      fieldName: 'loginReminder',
      help: '开启登录提醒后，用户登录成功后，系统将以邮件或者短信通知用户，以增加安全。',
      label: '登录提醒',
    },
    {
      component: 'Divider',
      fieldName: 'dividerAudit',
      formItemClass: 'col-span-full',
      label: '审计日志控制',
    },
    {
      component: 'InputNumber',
      componentProps: { class: 'w-full', min: 0, precision: 0 },
      defaultValue: 0,
      fieldName: 'saveAuditLogDayNum',
      help: '如果设置为0或空，则永久保留审计日志。',
      label: '审计日志容量上限(G)',
    },
  ];
}

/** 站点配置 leaf 路径 → 页面标题 */
export const SITE_CONFIG_LEAF_TITLES: Record<string, string> = {
  security: '安全配置',
  'journal-info': '编辑部信息',
  'mail-info': '邮件服务器',
  'timed-task': '定时任务',
  'cost-processing': '费用处理',
  'author-config': '作者参数',
  'editorial-config': '编委参数',
  'reviewer-config': '审稿人参数',
  'reader-config': '读者参数',
  'external-interface': '外部接口',
  'combining-articles': '组稿规则',
  'other-config': '其他参数',
};
