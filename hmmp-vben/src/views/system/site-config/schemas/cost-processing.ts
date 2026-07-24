import type { VbenFormSchema } from '#/adapter/form';

export function useCostProcessingFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_版面费计算方式',
      formItemClass: 'col-span-full',
      label: '版面费计算方式',
    },
    {
      component: 'RadioGroup',
      fieldName: 'GenPageCost',
      label: '',
      componentProps: {
              "options": [
                      {
                              "label": "不收版面费",
                              "value": "NoPageCost"
                      },
                      {
                              "label": "手工输入",
                              "value": "ManulPageCost"
                      },
                      {
                              "label": "按栏目计算",
                              "value": "ByColumnCost"
                      },
                      {
                              "label": "按篇计费",
                              "value": "ByBlackColorCost"
                      },
                      {
                              "label": "按字数计费",
                              "value": "ByWordNumPageCost"
                      },
                      {
                              "label": "按基础价",
                              "value": "ByBaseCost"
                      }
              ],
              "optionType": "button"
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'BlackPaperCost',
      label: '黑白版面 元 彩色版面 元',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'ColorPaperCost',
      label: '彩色版面 元',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'PageCostEveryWordNum',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'EveryWordNumPageCost',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'PageCostRemainderMin',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'PageCostRemainderMax',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'RemainderMaxPageCost',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'BasePageCost',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'BaseOverPageNum',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'BaseOverPageCost',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Select',
      fieldName: 'BaseColorPageNum',
      label: '',
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
      fieldName: 'ColorPageCost',
      label: '',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Select',
      fieldName: 'PageCostCanInvoice',
      label: '发票',
      componentProps: {
              "options": [
                      {
                              "label": "可以开",
                              "value": "1"
                      },
                      {
                              "label": "不开",
                              "value": "0"
                      },
                      {
                              "label": "必须开",
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
      component: 'Select',
      fieldName: 'PageCostCanOnlinePay',
      label: '允许在线支付',
      componentProps: {
              "options": [
                      {
                              "label": "允许",
                              "value": "1"
                      },
                      {
                              "label": "不允许",
                              "value": "0"
                      },
                      {
                              "label": "仅允许",
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
      component: 'Divider',
      fieldName: 'divider_审理费信息设置',
      formItemClass: 'col-span-full',
      label: '审理费信息设置',
    },
    {
      component: 'InputNumber',
      fieldName: 'InquisitionCost',
      label: '审理费',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Select',
      fieldName: 'RequireOnlinePayInquisition',
      label: '投稿完毕后立即在线支付审理费',
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
      fieldName: 'MustBePaidOnlinePayInquisition',
      label: '投稿完毕后立即在线支付审理费',
      componentProps: {
              "options": [
                      {
                              "label": "否",
                              "value": "0"
                      },
                      {
                              "label": "是",
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
      fieldName: 'AllocationEditorAutoSendPayInquisitionNote',
      label: '稿件分配责编后，自动通知作者交审理费',
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
      fieldName: 'InquisitionCanInvoice',
      label: '发票',
      componentProps: {
              "options": [
                      {
                              "label": "可以开",
                              "value": "1"
                      },
                      {
                              "label": "不开",
                              "value": "0"
                      },
                      {
                              "label": "必须开",
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
      component: 'Select',
      fieldName: 'InquisitionAddToPageCost',
      label: '和版面费一起支付',
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
      fieldName: 'InquisitionCanOnlinePay',
      label: '在线支付',
      componentProps: {
              "options": [
                      {
                              "label": "允许",
                              "value": "1"
                      },
                      {
                              "label": "不允许",
                              "value": "0"
                      },
                      {
                              "label": "仅允许",
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
      component: 'Divider',
      fieldName: 'divider_其它设置',
      formItemClass: 'col-span-full',
      label: '其它设置',
    },
    {
      component: 'RadioGroup',
      fieldName: 'GenEnAbstractCost',
      label: '英文摘要加工费计算方式',
      componentProps: {
              "options": [
                      {
                              "label": "不收取加工费",
                              "value": "NoEnAbstractCost"
                      },
                      {
                              "label": "手工输入",
                              "value": "ManulEnAbstractCost"
                      },
                      {
                              "label": "按栏目计算加工费",
                              "value": "EnAbstractCostByColumn"
                      },
                      {
                              "label": "EnAbstractCostByOne",
                              "value": "EnAbstractCostByOne"
                      }
              ],
              "optionType": "button"
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'EnAbstractCost',
      label: '英文摘要加工费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'RadioGroup',
      fieldName: 'GenBlackWhiteImageCost',
      label: '黑白图制作费计算方式',
      componentProps: {
              "options": [
                      {
                              "label": "不收取制作费",
                              "value": "NoBlackWhiteImageCost"
                      },
                      {
                              "label": "手工输入",
                              "value": "ManulBlackWhiteImageCost"
                      },
                      {
                              "label": "按栏目计算制作费",
                              "value": "BlackWhiteImageCostByColumn"
                      },
                      {
                              "label": "BlackWhiteImageCostByOne",
                              "value": "BlackWhiteImageCostByOne"
                      },
                      {
                              "label": "BlackWhiteImageCostByAll",
                              "value": "BlackWhiteImageCostByAll"
                      }
              ],
              "optionType": "button"
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'OneBlackWhiteImageCost',
      label: '黑白图制作费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'AllBlackWhiteImageCost',
      label: '黑白图制作费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'RadioGroup',
      fieldName: 'GenLineImageCost',
      label: '线条图制作费计算方式',
      componentProps: {
              "options": [
                      {
                              "label": "不收取制作费",
                              "value": "NoLineImageCost"
                      },
                      {
                              "label": "手工输入",
                              "value": "ManulLineImageCost"
                      },
                      {
                              "label": "按栏目计算制作费",
                              "value": "LineImageCostByColumn"
                      },
                      {
                              "label": "LineImageCostByOne",
                              "value": "LineImageCostByOne"
                      },
                      {
                              "label": "LineImageCostByAll",
                              "value": "LineImageCostByAll"
                      }
              ],
              "optionType": "button"
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'OneLineImageCost',
      label: '线条图制作费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'AllLineImageCost',
      label: '线条图制作费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'RadioGroup',
      fieldName: 'GenColorImageCost',
      label: '彩色图制作费计算方式',
      componentProps: {
              "options": [
                      {
                              "label": "不收取制作费",
                              "value": "NoColorImageCost"
                      },
                      {
                              "label": "手工输入",
                              "value": "ManulColorImageCost"
                      },
                      {
                              "label": "按栏目计算制作费",
                              "value": "ColorImageCostByColumn"
                      },
                      {
                              "label": "ColorImageCostByOne",
                              "value": "ColorImageCostByOne"
                      },
                      {
                              "label": "ColorImageCostByAll",
                              "value": "ColorImageCostByAll"
                      }
              ],
              "optionType": "button"
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'OneColorImageCost',
      label: '彩色图制作费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'AllColorImageCost',
      label: '彩色图制作费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'RadioGroup',
      fieldName: 'GenArticleCost',
      label: '稿费计算方式',
      componentProps: {
              "options": [
                      {
                              "label": "不给稿费",
                              "value": "NoArticleCost"
                      },
                      {
                              "label": "手工输入",
                              "value": "ManulArticleCost"
                      },
                      {
                              "label": "AutoArticlePageCost",
                              "value": "AutoArticlePageCost"
                      },
                      {
                              "label": "AutoArticleCost",
                              "value": "AutoArticleCost"
                      },
                      {
                              "label": "AutoByPageCostRate",
                              "value": "AutoByPageCostRate"
                      },
                      {
                              "label": "按栏目设置稿费",
                              "value": "ArticleCostByColumn"
                      }
              ],
              "optionType": "button"
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'EveryArticlePageCost',
      label: '稿费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'EveryArticleCost',
      label: '稿费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'ByArticleCostPageCostRate',
      label: '稿费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Select',
      fieldName: 'ArticleCostAddToPageCost',
      label: '稿费自动合计到版面费中一起先支付',
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
      fieldName: 'AuthorArticleCostBookType',
      label: '作者稿费信息登记类型',
      componentProps: {
              "options": [
                      {
                              "label": "必填",
                              "value": "1"
                      },
                      {
                              "label": "可选",
                              "value": "0"
                      },
                      {
                              "label": "不在版面费登记处登记，单独按需登记",
                              "value": "2"
                      }
              ],
              "class": "site-config-select",
              "style": {
                      "width": "100%"
              },
              "classNames": {
                      "popup": {
                              "root": "site-config-select-popup"
                      }
              },
              "popupMatchSelectWidth": false
      },
    },
    {
      component: 'RadioGroup',
      fieldName: 'GenAuditCost',
      label: '审稿费计算方式',
      componentProps: {
              "options": [
                      {
                              "label": "手工输入",
                              "value": "ManulAuditCost"
                      },
                      {
                              "label": "按审稿阶段计算",
                              "value": "AuditCostByAuditMoment"
                      },
                      {
                              "label": "AutoAuditNumCost",
                              "value": "AutoAuditNumCost"
                      }
              ],
              "optionType": "button"
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'EveryAuditCost',
      label: '审稿费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'IdealMoney',
      label: '审稿费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Select',
      fieldName: 'AuditCostOne',
      label: '同一个审稿人如果审同一篇稿件多次，只给一次审稿费',
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
      fieldName: 'EndPayDate',
      label: '交费截止日期',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'MinCost',
      label: '汇费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'MinRemittanceFee',
      label: '汇费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'RemittanceFeeRate',
      label: '汇费计算方式',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
  ];
}
