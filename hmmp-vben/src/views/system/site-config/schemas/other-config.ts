import type { VbenFormSchema } from '#/adapter/form';

export function useOtherConfigFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_其他参数信息设置',
      formItemClass: 'col-span-full',
      label: '其他参数信息设置',
    },
    {
      component: 'Input',
      fieldName: 'SystemTitle',
      label: '显示在每页的标题',
    },
    {
      component: 'Input',
      fieldName: 'Keywords',
      label: '搜索引擎检索主要关键词',
    },
    {
      component: 'Input',
      fieldName: 'Description',
      label: '搜索引擎检索详细描述',
    },
    {
      component: 'Input',
      fieldName: 'DownloadFileNameTemplate',
      label: '下载稿件的文件名规范',
      help: '支持如下参数或者几个参数的组合作为文件名: [file_no]:稿件编号 [title]:标题 [post_author_name]:投稿作者姓名 [first_author_name]:第一作者姓名 [contact_author_name]:通信作者姓名',
    },
    {
      component: 'Select',
      fieldName: 'EditMomentSelectRule',
      label: '编辑阶段选择规则',
      componentProps: {
              "options": [
                      {
                              "label": "随意选择",
                              "value": ""
                      },
                      {
                              "label": "严格按顺序选择",
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
      component: 'Input',
      fieldName: 'AcceptFileType',
      label: '系统允许上传的文件格式',
      help: '如：doc,docx,pdf,jpg,gif,png等，多个文件格式用逗号隔开',
    },
    {
      component: 'Input',
      fieldName: 'NewArticleAlert',
      label: '新投稿件提醒',
      help: '请输入收件人邮箱，多个Email用逗号隔开.',
    },
    {
      component: 'Input',
      fieldName: 'DOIFormat',
      label: 'DOI编码格式',
      help: '支持参数: [volume]:卷 [issue]:期 [start_page]:起始页码 [end_page]:截止页码 [page_num]:页数 [file_no]:文章编号 [sequence]:文章序号 [year]:出版年份 [month]:出版月份 [day]:出版日期 [hour]:出版时的小时 [minute]:出版时的分钟 [second]:出版时的秒钟 例：10.3979/j.issn.1673-825X.[year].[issue].[sequence]',
    },
    {
      component: 'Input',
      fieldName: 'DOIStartYear',
      label: 'DOI起始年份',
      help: '提示：设定起始年份后，早期的文章将不自动生成DOI编码',
    },
    {
      component: 'Input',
      fieldName: 'CstrClientID',
      label: 'CSTR',
    },
    {
      component: 'Input',
      fieldName: 'CstrPrefix',
      label: 'CSTR',
    },
    {
      component: 'Input',
      fieldName: 'CstrSercet',
      label: 'CSTR',
    },
    {
      component: 'Input',
      fieldName: 'CSTRFormat',
      label: 'CSTR编码格式',
      help: '支持参数: [volume]:卷号 [issue]:刊期 [start_page]:起始页码 [end_page]:截止页码 [page_num]:页数 [file_no]:文章编号 [sequence]:文章序号 [year]:年份 [month]:月 [day]:日 [hour]:时 [minute]:分 [second]:秒 例：10.3979/j.issn.1673-825X.[year].[issue].[sequence]',
    },
    {
      component: 'Input',
      fieldName: 'HtmlUrl',
      label: '全文HTML路径格式',
      help: '支持参数: [volume]:卷号 [issue]:刊期 [start_page]:起始页码 [end_page]:截止页码 [file_no]:文章编号 [year]:年份 例：/html/[year]/[issue]/[file_no].html.[issue].[sequence]',
    },
    {
      component: 'Input',
      fieldName: 'MobileHtmlUrl',
      label: '移动版HTML路径格式',
      help: '支持参数: [volume]:卷号 [issue]:刊期 [start_page]:起始页码 [end_page]:截止页码 [file_no]:文章编号 [year]:年份 例：/html/[year]/[issue]/[file_no].html.[issue].[sequence]',
    },
    {
      component: 'Select',
      fieldName: 'DiscardNoteSendByUser',
      label: '退稿邮件的发件邮箱设置',
      help: '提示：如果想专门针对退稿的稿件用公共邮箱发送，您可以再此做特殊设置，如果不需要，则按常规方式即可！',
      componentProps: {
              "options": [
                      {
                              "label": "按常规方式发送",
                              "value": ""
                      },
                      {
                              "label": "一律用公共邮箱发送",
                              "value": "0"
                      },
                      {
                              "label": "直接退稿的用公共邮箱发送",
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
      fieldName: 'ReviewSyle',
      label: '审稿模式',
      componentProps: {
              "options": [
                      {
                              "label": "普通word审稿",
                              "value": ""
                      },
                      {
                              "label": "方正PDF单盲",
                              "value": "1"
                      },
                      {
                              "label": "方正PDF双盲",
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
      fieldName: 'Language',
      label: '语言',
      componentProps: {
              "options": [
                      {
                              "label": "中文",
                              "value": "cn"
                      },
                      {
                              "label": "英文",
                              "value": "en"
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
      fieldName: 'UserRequireActivated',
      label: '用户注册需要激活',
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
      fieldName: 'RegisterType',
      label: '使用简单用户注册模式',
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
      fieldName: 'AuthorCanRegisterAsReviewer',
      label: '作者可申请成为审稿人',
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
      fieldName: 'DiscardDay',
      label: '默认计划退稿时间',
      help: '提示：此处的计划退稿时间是指手工直接退稿时，系统默认设置的时间！',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'AutoDiscardDay',
      label: '自动退稿',
      help: '提示：需设置好自动退稿的邮件模版！',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'AutoInquisitionCostDiscardDay',
      label: '自动退稿',
      help: '提示：需设置好自动退稿的邮件模版！',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'AutoPageCostDiscardDay',
      label: '自动退稿',
      help: '提示：需设置好自动退稿的邮件模版！',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'AutoRevisedDiscardDay',
      label: '自动退稿',
      help: '提示：需设置好自动退稿的邮件模版！',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Input',
      fieldName: 'AutoMakeInvoiceDay',
      label: '自动退稿',
    },
    {
      component: 'Input',
      fieldName: 'AutoMakeInvoiceWeek',
      label: '自动退稿',
    },
    {
      component: 'Input',
      fieldName: 'AutoMakeInvoiceHour',
      label: '自动退稿',
    },
    {
      component: 'Select',
      fieldName: 'DisabledSimulateAuditAdvice',
      label: '编辑可以模拟审稿人填写审稿意见',
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
      fieldName: 'AutoSendEmailAlertEmail',
      label: '自动发送Email Alert邮件',
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
      fieldName: 'CancelModifingAuditArticle',
      label: '稿件退修后是否自动撤销未审回的稿件',
      componentProps: {
              "options": [
                      {
                              "label": "请选择 ...",
                              "value": ""
                      },
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
      fieldName: 'PrintAdoptNote',
      label: '打印录用通知控制',
      componentProps: {
              "options": [
                      {
                              "label": "仅编辑可以打印",
                              "value": "1"
                      },
                      {
                              "label": "作者和编辑均可打印",
                              "value": "2"
                      },
                      {
                              "label": "作者和编辑均不需要打印",
                              "value": "0"
                      },
                      {
                              "label": "收到版面费后作者才可打印",
                              "value": "3"
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
      fieldName: 'PrintCopyright',
      label: '打印版权转让协议控制',
      componentProps: {
              "options": [
                      {
                              "label": "仅编辑可以打印",
                              "value": "1"
                      },
                      {
                              "label": "作者和编辑均可打印",
                              "value": "2"
                      },
                      {
                              "label": "作者和编辑均不需要打印",
                              "value": "0"
                      },
                      {
                              "label": "用户随时可打印",
                              "value": "4"
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
      fieldName: 'PrintPageCostNote',
      label: '打印版面费通知控制',
      componentProps: {
              "options": [
                      {
                              "label": "仅编辑可以打印",
                              "value": "1"
                      },
                      {
                              "label": "作者和编辑均可打印",
                              "value": "2"
                      },
                      {
                              "label": "作者和编辑均不需要打印",
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
      fieldName: 'PrintInquisitionCostNote',
      label: '打印审理费通知控制',
      componentProps: {
              "options": [
                      {
                              "label": "仅编辑可以打印",
                              "value": "1"
                      },
                      {
                              "label": "作者和编辑均可打印",
                              "value": "2"
                      },
                      {
                              "label": "作者和编辑均不需要打印",
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
      fieldName: 'DisabledEditViewEditInfo',
      label: '审稿人管理中，是否可管理责任编辑信息',
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
      fieldName: 'ReaderCanReadNewIssueArticle',
      label: '仅允许免费下载最新一期全文，以前的全文不允许下载',
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
      fieldName: 'ToAuditorHideAdvice',
      label: '稿件送审后，已审回的审稿意见自动隐藏',
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
      fieldName: 'ToEditRequireAuditAdvice',
      label: '稿件分配责编前，编务需先填写对稿件的评价',
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
      fieldName: 'ToAuditorRequireAuditAdvice',
      label: '稿件送审前，编辑需先填写对稿件的评价',
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
      fieldName: 'DisabledEditorAdoptArticle',
      label: '禁止编辑录用稿件',
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
      fieldName: 'DefaultSendSMS',
      label: '发邮件时默认同时发送短信',
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
      fieldName: 'AddReviewerControl',
      label: '添加审稿人权限控制',
      componentProps: {
              "options": [
                      {
                              "label": "编辑仅可在审稿人管理处添加",
                              "value": ""
                      },
                      {
                              "label": "编辑可在任意地方添加",
                              "value": "1"
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
      component: 'InputNumber',
      fieldName: 'FirstAuthorNumber',
      label: '第一作者人数设置',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'InputNumber',
      fieldName: 'ContactAuthorNumber',
      label: '通信作者人数设置',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
  ];
}
