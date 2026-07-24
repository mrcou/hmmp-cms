import type { VbenFormSchema } from '#/adapter/form';

export function useMailInfoFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_发邮件服务器相关参数设置',
      formItemClass: 'col-span-full',
      label: '发邮件服务器相关参数设置',
    },
    {
      component: 'Input',
      fieldName: 'SMTPServer',
      label: '邮件服务器*',
    },
    {
      component: 'Input',
      fieldName: 'SMTPEmail',
      label: '发邮件邮箱*',
    },
    {
      component: 'Input',
      fieldName: 'SMTPUserName',
      label: '发邮件用户名 *',
    },
    {
      component: 'InputPassword',
      fieldName: 'SMTPPassword',
      label: '发邮件密码 *',
    },
    {
      component: 'InputNumber',
      fieldName: 'SMTPPort',
      label: '邮件服务器端口*',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'RadioGroup',
      fieldName: 'SmtpUseSSL',
      label: '邮件服务器要求安全连接(SSL)',
      componentProps: {
              "options": [
                      {
                              "label": "是",
                              "value": "1"
                      },
                      {
                              "label": "否",
                              "value": "0"
                      }
              ],
              "optionType": "button"
      },
    },
  ];
}
