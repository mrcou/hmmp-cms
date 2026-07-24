import type { VbenFormSchema } from '#/adapter/form';

export function useCombiningArticlesFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Select',
      fieldName: 'strPublicationChargesText',
      label: '版面费',
      componentProps: {
              "options": [
                      {
                              "label": "全部",
                              "value": "0"
                      },
                      {
                              "label": "已收款",
                              "value": "1"
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
      fieldName: 'SetIssueParentID',
      label: '版面费',
      defaultValue: '001',
      componentProps: {
              "options": [
                      {
                              "label": "新稿",
                              "value": "555"
                      },
                      {
                              "label": "审稿中",
                              "value": "002"
                      },
                      {
                              "label": "退修",
                              "value": "003"
                      },
                      {
                              "label": "采用",
                              "value": "001"
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
