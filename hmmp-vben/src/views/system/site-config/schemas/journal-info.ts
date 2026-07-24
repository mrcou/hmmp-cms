import type { VbenFormSchema } from '#/adapter/form';

export function useJournalInfoFormSchema(): VbenFormSchema[] {
  return [
    {
      component: 'Divider',
      fieldName: 'divider_编辑部基本信息设置',
      formItemClass: 'col-span-full',
      label: '编辑部基本信息设置',
    },
    {
      component: 'Input',
      fieldName: 'WorkRoom',
      label: '编辑部名称*',
    },
    {
      component: 'Input',
      fieldName: 'JournalSimpleName',
      label: '杂志名简写',
    },
    {
      component: 'Input',
      fieldName: 'ParentUnit',
      label: '主管单位',
    },
    {
      component: 'Input',
      fieldName: 'Unit',
      label: '主办单位',
    },
    {
      component: 'Input',
      fieldName: 'CoUnit',
      label: '协办单位',
    },
    {
      component: 'Input',
      fieldName: 'ISSN',
      label: '国际刊号',
    },
    {
      component: 'Input',
      fieldName: 'EISSN',
      label: '网络版刊号',
    },
    {
      component: 'Input',
      fieldName: 'CN',
      label: '国内刊号',
    },
    {
      component: 'Input',
      fieldName: 'ImpactFactor',
      label: '影响因子',
    },
    {
      component: 'InputNumber',
      fieldName: 'FirstYearPublication',
      label: '创刊年',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Input',
      fieldName: 'PublishingCycle',
      label: '出版周期',
    },
    {
      component: 'Input',
      fieldName: 'PublishingArea',
      label: '出版地区',
    },
    {
      component: 'Input',
      fieldName: 'JournalFormat',
      label: '期刊开本',
    },
    {
      component: 'Input',
      fieldName: 'MajorClassification',
      label: '专业分类',
    },
    {
      component: 'Input',
      fieldName: 'IndexedBy',
      label: '数据库收录',
    },
    {
      component: 'Input',
      fieldName: 'ICP',
      label: 'ICP',
    },
    {
      component: 'Input',
      fieldName: 'TrendmdJournalID',
      label: 'Trend MD Journal ID',
    },
    {
      component: 'Input',
      fieldName: 'Price',
      label: '期刊定价',
    },
    {
      component: 'Input',
      fieldName: 'ForeignPrice',
      label: '国外发行定价',
    },
    {
      component: 'Input',
      fieldName: 'UnitPrice',
      label: '单价',
    },
    {
      component: 'Input',
      fieldName: 'PostID',
      label: '邮发代号',
    },
    {
      component: 'Input',
      fieldName: 'Editor',
      label: '主编',
    },
    {
      component: 'Input',
      fieldName: 'ViceEditor',
      label: '常务副主编',
    },
    {
      component: 'Input',
      fieldName: 'ServiceTel',
      label: '联系电话',
    },
    {
      component: 'Input',
      fieldName: 'Address',
      label: '办公地址',
    },
    {
      component: 'Input',
      fieldName: 'ContactAddress',
      label: '通信地址',
    },
    {
      component: 'Input',
      fieldName: 'Postcode',
      label: '邮编',
    },
    {
      component: 'Input',
      fieldName: 'Email',
      label: 'E-mail',
    },
    {
      component: 'Input',
      fieldName: 'Fax',
      label: '传真',
    },
    {
      component: 'InputNumber',
      fieldName: 'QQ',
      label: 'QQ',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Input',
      fieldName: 'WeiXin',
      label: '微信',
    },
    {
      component: 'Input',
      fieldName: 'President',
      label: '社长',
    },
    {
      component: 'Input',
      fieldName: 'Director',
      label: '编辑部主任',
    },
    {
      component: 'Input',
      fieldName: 'PublishHouse',
      label: '编辑出版',
    },
    {
      component: 'Input',
      fieldName: 'PrintingPlant',
      label: '印刷',
    },
    {
      component: 'Input',
      fieldName: 'DomesticPublishHouse',
      label: '国内发行',
    },
    {
      component: 'Input',
      fieldName: 'ExternalPublishHouse',
      label: '国外发行',
    },
    {
      component: 'Input',
      fieldName: 'BankName',
      label: '开户银行',
    },
    {
      component: 'InputNumber',
      fieldName: 'BankAccount',
      label: '银行账号',
      componentProps: {
              "class": "w-full",
              "min": 0
      },
    },
    {
      component: 'Input',
      fieldName: 'BankUserName',
      label: '银行户名',
    },
    {
      component: 'Input',
      fieldName: 'FinanceUserName',
      label: '财务人员联系人账号',
    },
    {
      component: 'Input',
      fieldName: 'FinanceRealName',
      label: '财务人员联系人名',
    },
    {
      component: 'Input',
      fieldName: 'FinanceUserTel',
      label: '财务人员联系电话',
    },
    {
      component: 'Input',
      fieldName: 'Web_Site_Root',
      label: '网址根路径 *',
    },
    {
      component: 'Input',
      fieldName: 'Web_Site',
      label: '网址*',
    },
    {
      component: 'Input',
      fieldName: 'Outter_Web_Index',
      label: '外网首页地址*',
    },
    {
      component: 'Input',
      fieldName: 'Inner_Web_Index',
      label: '内网首页地址 *',
    },
  ];
}
