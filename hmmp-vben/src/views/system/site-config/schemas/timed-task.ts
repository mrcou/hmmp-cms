import type { VbenFormSchema } from '#/adapter/form';

export function useTimedTaskFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_稿件催审',
      formItemClass: 'col-span-full',
      label: '稿件催审',
    },
    {
      component: 'Select',
      fieldName: 'AutoReSendAuditArticleToAuditor',
      label: '自动催审',
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
      component: 'InputNumber',
      fieldName: 'FirstIntervalHastenAudit',
      label: '首次催审间隔(天)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'IntervalReSendAuditArticleToAuditor',
      label: '再次催审间隔(天)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'MaxHastenAuditNum',
      label: '最大催审(次)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Select',
      fieldName: 'AutoAuditHastenAgree',
      label: '只催审同意审的',
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
      fieldName: 'divider_稿件催修',
      formItemClass: 'col-span-full',
      label: '稿件催修',
    },
    {
      component: 'Select',
      fieldName: 'AutoSendModifyInfoToAuthor',
      label: '自动催修',
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
      component: 'InputNumber',
      fieldName: 'FirstIntervalHastenModify',
      label: '首次催修间隔(天)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'IntervalReSendModifyArticleToAuthor',
      label: '再次催修间隔(天)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'MaxHastenModifyNum',
      label: '最大催修(次)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Divider',
      fieldName: 'divider_催缴审理费',
      formItemClass: 'col-span-full',
      label: '催缴审理费',
    },
    {
      component: 'Select',
      fieldName: 'AutoSendPayInquisitionInfoToAuthor',
      label: '自动催缴',
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
      component: 'InputNumber',
      fieldName: 'IntervalSendPayInquisitionInfoToAuthor',
      label: '催缴间隔(天)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'MaxHastenPayInquisitionNum',
      label: '最大催缴(次)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Divider',
      fieldName: 'divider_催缴版面费',
      formItemClass: 'col-span-full',
      label: '催缴版面费',
    },
    {
      component: 'Select',
      fieldName: 'AutoSendPayPageCostInfoToAuthor',
      label: '自动催缴',
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
      component: 'InputNumber',
      fieldName: 'IntervalSendPayPageCostInfoToAuthor',
      label: '催缴间隔(天)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'MaxHastenPayPageCostNum',
      label: '最大催缴(次)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Divider',
      fieldName: 'divider_责编处理提醒',
      formItemClass: 'col-span-full',
      label: '责编处理提醒',
    },
    {
      component: 'Select',
      fieldName: 'AutoSendHastenNoteToEditor',
      label: '自动提醒',
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
      component: 'InputNumber',
      fieldName: 'IntervalSendHastenNoteToEditor',
      label: '提醒间隔(天)',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Divider',
      fieldName: 'divider_其它设置',
      formItemClass: 'col-span-full',
      label: '其它设置',
    },
    {
      component: 'Select',
      fieldName: 'AutoSendAuditReturnToAuthor',
      label: '自动发送审回通知给作者',
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
      component: 'Select',
      fieldName: 'AutoSendAuditReturnToEditor',
      label: '自动发送审回通知给责编',
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
      component: 'Select',
      fieldName: 'AutoSendThanksNoteToReferee',
      label: '自动发送审稿致谢通知',
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
      component: 'Select',
      fieldName: 'AutoSendModifyReturnToEditor',
      label: '自动发送修回通知给责任编辑',
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
      component: 'Select',
      fieldName: 'AutoSendPublishInfoToAuthor',
      label: '自动发送修回通知给责任编辑',
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
  ];
}
