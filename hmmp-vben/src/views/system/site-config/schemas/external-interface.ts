import type { VbenFormSchema } from '#/adapter/form';

export function useExternalInterfaceFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_全文检测参数设置',
      formItemClass: 'col-span-full',
      label: '全文检测参数设置',
    },
    {
      component: 'InputPassword',
      fieldName: 'ZipPassword',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'WanfangCheckUserName',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'InputPassword',
      fieldName: 'WanfangCheckPassword',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Select',
      fieldName: 'WanfangdataCheck',
      label: 'CNKI全文检测报告解压密码',
      componentProps: {
              "options": [
                      {
                              "label": "是",
                              "value": "1"
                      },
                      {
                              "label": "否",
                              "value": "0"
                      },
                      {
                              "label": "收到审理费后再检测",
                              "value": "2"
                      }
              ],
              "class": "site-config-select",
              "style": {
                      "width": "100%"
              }
      },
    },
    {
      component: 'Input',
      fieldName: 'WenChaClientID',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'InputPassword',
      fieldName: 'WenChaSecret',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Select',
      fieldName: 'WenChaCheck',
      label: 'CNKI全文检测报告解压密码',
      componentProps: {
              "options": [
                      {
                              "label": "是",
                              "value": "1"
                      },
                      {
                              "label": "否",
                              "value": "0"
                      },
                      {
                              "label": "收到审理费后再检测",
                              "value": "2"
                      }
              ],
              "class": "site-config-select",
              "style": {
                      "width": "100%"
              }
      },
    },
    {
      component: 'Input',
      fieldName: 'DayaUnitID',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'DayaUserID',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'InputPassword',
      fieldName: 'DayaUserPassword',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Select',
      fieldName: 'DayaCheck',
      label: 'CNKI全文检测报告解压密码',
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
              "class": "site-config-select",
              "style": {
                      "width": "100%"
              }
      },
    },
    {
      component: 'Divider',
      fieldName: 'divider_DOI注册信息',
      formItemClass: 'col-span-full',
      label: 'DOI注册信息',
    },
    {
      component: 'Select',
      fieldName: 'DoiRegisterServer',
      label: 'CNKI全文检测报告解压密码',
      componentProps: {
              "options": [
                      {
                              "label": "中信所",
                              "value": "istic"
                      }
              ],
              "class": "site-config-select",
              "style": {
                      "width": "100%"
              }
      },
    },
    {
      component: 'Input',
      fieldName: 'WfDoiJournalID',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'WfDoiUserName',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'InputPassword',
      fieldName: 'WfDoiPassword',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Divider',
      fieldName: 'divider_在线支付参数设置',
      formItemClass: 'col-span-full',
      label: '在线支付参数设置',
    },
    {
      component: 'Select',
      fieldName: 'can_onlinepay',
      label: 'CNKI全文检测报告解压密码',
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
              "class": "site-config-select",
              "style": {
                      "width": "100%"
              }
      },
    },
    {
      component: 'Input',
      fieldName: 'paypal_email',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'paypal_access_token',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'alipay_appid',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'alipay_private_key',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'alipay_public_key',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'chinabank_mid',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'chinabank_key',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'chinabank_text_key',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'payease_mid',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'payease_key',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'payease_private_key_file_name',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'payease_public_key_file_name',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'weixin_appid',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'weixin_key',
      label: 'CNKI全文检测报告解压密码',
    },
    {
      component: 'Input',
      fieldName: 'weixin_mchid',
      label: 'CNKI全文检测报告解压密码',
    },
  ];
}
