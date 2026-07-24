import type { VbenFormSchema } from '#/adapter/form';

export function useReviewerConfigFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_审稿人相关参数设置',
      formItemClass: 'col-span-full',
      label: '审稿人相关参数设置',
    },
    {
      component: 'Select',
      fieldName: 'AuditorClickTitleDownloadFulltext',
      label: '审稿人点击标题下载全文',
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
      fieldName: 'AuditorCanViewArticleStatus',
      label: '审稿人可查看稿件状态',
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
      fieldName: 'EnableAuditorSimilarArticles',
      label: '审稿人可查看相似文献',
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
      fieldName: 'AuditorViewAttacheStyle',
      label: '[AuditorViewAttacheStyleLabel]',
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
      fieldName: 'StopReviewCanViewOtherAdvice',
      label: '审稿结束后可查看其他审稿意见',
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
      fieldName: 'AllowReviewersViewEditorsName',
      label: '允许审稿人查看责编姓名',
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
      fieldName: 'ExcludeReviewersType',
      label: '屏蔽审稿人',
      componentProps: {
              "options": [
                      {
                              "label": "请选择 ...",
                              "value": ""
                      },
                      {
                              "label": "与任意作者同一单位",
                              "value": "0"
                      },
                      {
                              "label": "与第一作者同一单位",
                              "value": "1"
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
