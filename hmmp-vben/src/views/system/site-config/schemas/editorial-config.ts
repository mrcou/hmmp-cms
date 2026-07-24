import type { VbenFormSchema } from '#/adapter/form';

export function useEditorialConfigFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_执行编委负责制相关参数设置',
      formItemClass: 'col-span-full',
      label: '执行编委负责制相关参数设置',
    },
    {
      component: 'Select',
      fieldName: 'IsBianWeiProcess',
      label: '执行编委负责稿件送审',
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
      fieldName: 'BianWeiModifyAdvice',
      label: '执行编委可直接将稿件退给作者修改',
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
      fieldName: 'BianWeiQueryManuscriptType',
      label: '编委查询稿件类型',
      componentProps: {
              "options": [
                      {
                              "label": "仅未处理完的稿件",
                              "value": "0"
                      },
                      {
                              "label": "全部自己的稿件",
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
      fieldName: 'BianWeiCanReject',
      label: '执行编委可直接做稿件退稿处理',
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
      fieldName: 'BianWeiCanMergeAdvice',
      label: '编委可组合审稿意见',
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
      fieldName: 'AssignEditUserType',
      label: '稿件分配用户类型',
      componentProps: {
              "options": [
                      {
                              "label": "请选择...",
                              "value": ""
                      },
                      {
                              "label": "仅分配编委",
                              "value": "1"
                      },
                      {
                              "label": "仅分配给编辑",
                              "value": "2"
                      },
                      {
                              "label": "仅分配给主编",
                              "value": "3"
                      },
                      {
                              "label": "可分配给编辑和编委",
                              "value": "4"
                      },
                      {
                              "label": "可分配给编辑、编委和主编",
                              "value": "5"
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
