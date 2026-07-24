import type { VbenFormSchema } from '#/adapter/form';

const REFERENCE_STYLE_HELP =
  '说明：[author_cn_name] 作者中文名；[cn_title] 中文标题；[journal_cn_name] 期刊名称；' +
  '[year_id] 年份编号；[volume] 卷号；[issue] 期号；[start_page] 起始页码；[end_page] 截止页码；' +
  '[author_en_name] 作者英文名；[en_title] 英文标题；[journal_en_name] 期刊英文名';

const MODULE_OPTIONS = [
  { label: '网红产品', value: 'has_core_products' },
  { label: '服务需求', value: 'has_service' },
  { label: '技术需求', value: 'has_technical' },
  { label: '人员需求', value: 'has_person' },
  { label: '科学问题', value: 'has_technical_problems' },
  { label: '单位信息', value: 'has_enterprise' },
  { label: '专家信息', value: 'has_expert' },
  { label: '信息发布', value: 'has_information' },
  { label: '产品成果', value: 'has_product_introduction' },
];

export function useJournalBasicFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_basic_info',
      formItemClass: 'col-span-full',
      label: '基本信息',
    },
    {
      component: 'Input',
      fieldName: 'journal_name',
      label: '期刊名称',
      rules: 'required',
    },
    {
      component: 'Input',
      fieldName: 'journal_code',
      label: '期刊编号',
      rules: 'required',
    },
    {
      component: 'Input',
      fieldName: 'journal_en_name',
      label: '英文名',
    },
    {
      component: 'InputNumber',
      fieldName: 'publish_cycle',
      label: '发行周期',
      help: '发行周期即每年发行多少期，如月刊为 12，双月刊为 6',
      componentProps: { class: 'w-full', min: 0 },
    },
    {
      component: 'Input',
      fieldName: 'country_id',
      label: '国家',
    },
    {
      component: 'Input',
      fieldName: 'province_id',
      label: '省份',
    },
    {
      component: 'Textarea',
      fieldName: 'meeting_brief',
      formItemClass: 'col-span-full',
      label: '活动简介（中文）',
      componentProps: { rows: 4 },
    },
    {
      component: 'Textarea',
      fieldName: 'meeting_en_brief',
      formItemClass: 'col-span-full',
      label: '活动简介（英文）',
      componentProps: { rows: 4 },
    },
    {
      component: 'Textarea',
      fieldName: 'institution_brief',
      formItemClass: 'col-span-full',
      label: '单位简介（中文）',
      componentProps: { rows: 4 },
    },
    {
      component: 'Textarea',
      fieldName: 'institution_en_brief',
      formItemClass: 'col-span-full',
      label: '单位简介（英文）',
      componentProps: { rows: 4 },
    },
    {
      component: 'Input',
      fieldName: 'contact_name',
      label: '联系人姓名（中文名）',
    },
    {
      component: 'Input',
      fieldName: 'contact_en_name',
      label: '联系人姓名（英文名）',
    },
    {
      component: 'Input',
      fieldName: 'contact_mobile',
      label: '联系电话',
    },
    {
      component: 'Input',
      fieldName: 'email',
      label: 'E-mail',
    },
    {
      component: 'Input',
      fieldName: 'reference_style',
      formItemClass: 'col-span-full',
      label: '参考文献格式',
      help: REFERENCE_STYLE_HELP,
    },
    {
      component: 'Input',
      fieldName: 'cn_reference_style',
      formItemClass: 'col-span-full',
      label: '中文参考文献格式',
    },
    {
      component: 'Input',
      fieldName: 'en_reference_style',
      formItemClass: 'col-span-full',
      label: '英文参考文献格式',
    },
    {
      component: 'Input',
      fieldName: 'wait_publish_reference_style',
      formItemClass: 'col-span-full',
      label: '待发表文章参考文献格式',
    },
    {
      component: 'Input',
      fieldName: 'adopted_reference_style',
      formItemClass: 'col-span-full',
      label: '已录用文章中文参考文献格式',
    },
    {
      component: 'Input',
      fieldName: 'en_adopted_reference_style',
      formItemClass: 'col-span-full',
      label: '已录用文章英文参考文献格式',
    },
    {
      component: 'InputNumber',
      fieldName: 'reference_author_num',
      label: '参考文献中作者个数',
      help: '为 0 或者为空表示用全部作者',
      componentProps: { class: 'w-full', min: 0 },
    },
    {
      component: 'Divider',
      fieldName: 'divider_media',
      formItemClass: 'col-span-full',
      label: '图片与 Banner',
    },
    {
      component: 'Input',
      fieldName: 'journal_logo_url',
      formItemClass: 'col-span-full',
      label: '活动 logo',
    },
    {
      component: 'Input',
      fieldName: 'journal_ewm_url',
      formItemClass: 'col-span-full',
      label: '网站二维码',
    },
    {
      component: 'Input',
      fieldName: 'journal_weixin_ewm_url',
      formItemClass: 'col-span-full',
      label: '微信公众号二维码',
    },
    {
      component: 'Input',
      fieldName: 'pc_banner_url',
      formItemClass: 'col-span-full',
      label: '网站 Banner',
    },
    {
      component: 'Input',
      fieldName: 'mobile_banner_url',
      formItemClass: 'col-span-full',
      label: '手机版 Banner',
    },
    {
      component: 'Input',
      fieldName: 'discussion_group_qr_code',
      formItemClass: 'col-span-full',
      label: '讨论群二维码',
    },
    {
      component: 'Divider',
      fieldName: 'divider_modules',
      formItemClass: 'col-span-full',
      label: '包含模块',
    },
    {
      component: 'CheckboxGroup',
      fieldName: 'included_modules',
      formItemClass: 'col-span-full',
      label: '包含模块',
      componentProps: {
        options: MODULE_OPTIONS,
      },
    },
  ];
}

/** 图片与 Banner 字段（支持上传） */
export const JOURNAL_MEDIA_FIELDS = [
  'journal_logo_url',
  'journal_ewm_url',
  'journal_weixin_ewm_url',
  'pc_banner_url',
  'mobile_banner_url',
  'discussion_group_qr_code',
] as const;

/** 模块字段名列表（与 legacy checkbox 字段对应） */
export const JOURNAL_MODULE_FIELDS = MODULE_OPTIONS.map((item) => item.value);
