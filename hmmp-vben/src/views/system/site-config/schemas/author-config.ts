import type { VbenFormSchema } from '#/adapter/form';

export function useAuthorConfigFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_作者相关参数设置',
      formItemClass: 'col-span-full',
      label: '作者相关参数设置',
    },
    {
      component: 'Input',
      fieldName: 'PostFileType',
      label: '作者投稿文件类型',
      help: '多个文件格式请用逗号隔开，*表示无限制',
    },
    {
      component: 'Select',
      fieldName: 'SameTitleType',
      label: '查重方式',
      componentProps: {
              "options": [
                      {
                              "label": "不查重",
                              "value": "0"
                      },
                      {
                              "label": "按标题查重，可接收稿件，在稿件标题上给出警示",
                              "value": "1"
                      },
                      {
                              "label": "按标题查重，不可接收稿件",
                              "value": "2"
                      },
                      {
                              "label": "按标题和第一作者查重，可接收稿件，在稿件标题上给出警示",
                              "value": "3"
                      },
                      {
                              "label": "按标题和第一作者查重，不可接收稿件",
                              "value": "4"
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
      component: 'Select',
      fieldName: 'AutoFileNOSet',
      label: '稿件编号规则',
      componentProps: {
              "options": [
                      {
                              "label": "",
                              "value": ""
                      },
                      {
                              "label": "年份+月份+日期+三位顺序数(序号每日从001开始)",
                              "value": "yyyymmdd000"
                      },
                      {
                              "label": "年份+月份+日期+两位顺序数(序号每日从01开始)",
                              "value": "yyyymmdd00"
                      },
                      {
                              "label": "年份+月份+日期+四位顺序数(序号每年从0001开始)",
                              "value": "yyyymmdd0000"
                      },
                      {
                              "label": "年份+月份+三位顺序数(序号每月从001开始)",
                              "value": "yyyymm000"
                      },
                      {
                              "label": "年份+五位顺序数(序号在上一年的基础上累加)",
                              "value": "yyyy00000_2"
                      },
                      {
                              "label": "年份+五位顺序数(序号每年从00001开始)",
                              "value": "yyyy00000"
                      },
                      {
                              "label": "年份+月份+五位顺序数(序号在上一年的基础上累加)",
                              "value": "yyyymm00000_2"
                      },
                      {
                              "label": "年份+月份+五位顺序数(序号每年从00001开始)",
                              "value": "yyyymm00000"
                      },
                      {
                              "label": "年份+月份+横线+三位顺序数(序号每月从001开始)",
                              "value": "yyyymm-000"
                      },
                      {
                              "label": "年份+四位顺序数(序号在上一年的基础上累加)",
                              "value": "yyyy0000_2"
                      },
                      {
                              "label": "年份+四位顺序数(序号每年从0001开始)",
                              "value": "yyyy0000"
                      },
                      {
                              "label": "年份+三位顺序数(序号每年从001开始)",
                              "value": "yyyy000"
                      },
                      {
                              "label": "年份后两位数+五位顺序数(序号在上一年的基础上累加)",
                              "value": "yy00000_2"
                      },
                      {
                              "label": "年份后两位数+五位顺序数(序号每年从00001开始)",
                              "value": "yy00000"
                      },
                      {
                              "label": "年份后两位数+四位顺序数(序号在上一年的基础上累加)",
                              "value": "yy0000_2"
                      },
                      {
                              "label": "年份后两位数+四位顺序数((序号每年从0001开始))",
                              "value": "yy0000"
                      },
                      {
                              "label": "年份后两位数+三位顺序数(序号每年从001开始)",
                              "value": "yy000"
                      },
                      {
                              "label": "年份后两位数+月份+四位顺序数(序号每月从0001开始)",
                              "value": "yymm0000"
                      },
                      {
                              "label": "年份后两位数+月份+日期+三位顺序数(序号每年从001开始)",
                              "value": "yymmdd000"
                      },
                      {
                              "label": "年份后两位数+月份+日期+两位顺序数((序号每日从01开始))",
                              "value": "yymmdd00"
                      },
                      {
                              "label": "年份第一位和最后一位+五位顺序数(序号在上一年的基础上累加)",
                              "value": "y1400000_2"
                      },
                      {
                              "label": "年份第一位和最后一位+五位顺序数(序号每年从00001开始)",
                              "value": "y1400000"
                      },
                      {
                              "label": "年份第一位和最后一位+四位顺序数(序号在上一年的基础上累加)",
                              "value": "y140000_2"
                      },
                      {
                              "label": "年份第一位和最后一位+四位顺序数((序号每年从0001开始))",
                              "value": "y140000"
                      },
                      {
                              "label": "年份第一位和最后一位+三位顺序数(序号每年从001开始)",
                              "value": "y14000"
                      },
                      {
                              "label": "年份后两位数+横线+顺序数(每年从200开始)+横线+顺序数",
                              "value": "yy-200-0000"
                      },
                      {
                              "label": "年份+横线+三位顺序数",
                              "value": "yyyy-000"
                      },
                      {
                              "label": "年份+横线+四位顺序数",
                              "value": "yyyy-0000"
                      },
                      {
                              "label": "六位顺序数",
                              "value": "six000000"
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
      component: 'Input',
      fieldName: 'FileNOPre',
      label: '稿件编号规则',
    },
    {
      component: 'Select',
      fieldName: 'LaterFileNo',
      label: '稿件编号规则',
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
      fieldName: 'AuthorCanReadAuditAppraise',
      label: '作者可查看审稿评价',
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
      fieldName: 'AuthorCanReadAuditConclusion',
      label: '作者可查看审稿结论',
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
      fieldName: 'AuthorCanViewArticleHistory',
      label: '作者可查看稿件处理历史',
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
      fieldName: 'AuthorCanViewEditInfo',
      label: '作者可查看责编的联系信息',
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
      fieldName: 'AuthorCanViewReviewerRevision',
      label: '作者可查看审稿人批阅稿?',
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
      fieldName: 'AuthorInsertAndUpdateReference',
      label: '作者可添加和修改参考文献?',
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
      fieldName: 'EnableMultiJournalStyle',
      label: '启用多杂志投稿模式?',
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
      fieldName: 'AutoSendReceiptEmail',
      label: '作者投稿完毕后,自动发送收稿回执',
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
      fieldName: 'AutoSendPayInquisitionEmail',
      label: '作者投稿完毕后,自动通知交审理费',
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
      fieldName: 'AuthorCanReadCheckReport',
      label: '作者可查看全文检测报告',
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
      fieldName: 'EnableAuthorCheckReference',
      label: '作者可检测参考文献',
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
      fieldName: 'ReceiveUserType',
      label: '收发票人默认作者类型',
      componentProps: {
              "options": [
                      {
                              "label": "通信作者",
                              "value": "1"
                      },
                      {
                              "label": "第一作者",
                              "value": "0"
                      },
                      {
                              "label": "手工输入",
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
      fieldName: 'ReceiveArticleCostUserType',
      label: '稿费收款人默认作者类型',
      componentProps: {
              "options": [
                      {
                              "label": "通信作者",
                              "value": "1"
                      },
                      {
                              "label": "第一作者",
                              "value": "0"
                      },
                      {
                              "label": "手工输入",
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
      fieldName: 'AutoGetArticleInfo',
      label: '作者投稿时自动抽取稿件信息',
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
      fieldName: 'EnableAuthorDuplicateChecking',
      label: '允许作者投稿时通过系统实现稿件查重',
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
      fieldName: 'DisabledSendModifyAuthorInfo',
      label: '稿件作者信息修改后禁止给责编发邮件提醒',
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
      fieldName: 'DisabledModifyTitle',
      label: '修改稿件时禁止作者修改标题',
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
      fieldName: 'DisabledModifyAuthor',
      label: '修改稿件时禁止作者修改标题',
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
      fieldName: 'EnableAuthorUploadAttachement',
      label: '作者可随时上传附件',
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
      fieldName: 'EnableAuthorWithdrawArticle',
      label: '作者可自行撤稿',
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
      fieldName: 'EditorOnlineAuditAdviceAuthorCanRead',
      label: '编辑填写的审稿意见，默认允许作者查看',
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
      fieldName: 'EnableAuthorYearIssue',
      label: '作者可查看已录用文章确定的年份和刊期',
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
      fieldName: 'UploadCopyrightTransferAgreement',
      label: '上传版权转让协议通知',
      componentProps: {
              "options": [
                      {
                              "label": "请选择 ...",
                              "value": ""
                      },
                      {
                              "label": "随时上传",
                              "value": "1"
                      },
                      {
                              "label": "稿件录用后上传",
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
      fieldName: 'ReadAuthorNoteTime',
      label: '投稿须知阅读时间',
      componentProps: {
              "options": [
                      {
                              "label": "不做强制控制",
                              "value": ""
                      },
                      {
                              "label": "至少阅读10秒",
                              "value": "10"
                      },
                      {
                              "label": "至少阅读20秒",
                              "value": "20"
                      },
                      {
                              "label": "至少阅读30秒",
                              "value": "30"
                      },
                      {
                              "label": "至少阅读40秒",
                              "value": "40"
                      },
                      {
                              "label": "至少阅读50秒",
                              "value": "50"
                      },
                      {
                              "label": "至少阅读60秒",
                              "value": "60"
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
      fieldName: 'AllowAuthorsViewReviewDeadline',
      label: '允许作者查看审稿倒计时',
      componentProps: {
              "options": [
                      {
                              "label": "不允许",
                              "value": ""
                      },
                      {
                              "label": "允许",
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
