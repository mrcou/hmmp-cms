<script lang="ts" setup>
import type { GenApi } from '#/api/tool/gen';

import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { Page } from '@vben/common-ui';

import {
  Button,
  Checkbox,
  Input,
  message,
  Select,
  Table,
  Tabs,
} from 'antdv-next';

import { useVbenForm } from '#/adapter/form';
import { getGenTable, updateGenTable } from '#/api/tool/gen';

const JAVA_TYPE_OPTIONS = [
  'Long',
  'String',
  'Integer',
  'Double',
  'BigDecimal',
  'Date',
  'Boolean',
].map((v) => ({ label: v, value: v }));

const QUERY_TYPE_OPTIONS = [
  'EQ',
  'NE',
  'GT',
  'GTE',
  'LT',
  'LTE',
  'LIKE',
  'BETWEEN',
].map((v) => ({ label: v, value: v }));

const HTML_TYPE_OPTIONS = [
  { label: '文本框', value: 'input' },
  { label: '文本域', value: 'textarea' },
  { label: '下拉框', value: 'select' },
  { label: '单选框', value: 'radio' },
  { label: '复选框', value: 'checkbox' },
  { label: '日期控件', value: 'datetime' },
  { label: '图片上传', value: 'imageUpload' },
  { label: '文件上传', value: 'fileUpload' },
  { label: '富文本', value: 'editor' },
];

const route = useRoute();
const router = useRouter();
const tableId = Number(route.query.tableId);

const activeTab = ref('basic');
const columns = ref<GenApi.GenTableColumn[]>([]);
const editableFlagFields = [
  'isInsert',
  'isEdit',
  'isList',
  'isQuery',
  'isRequired',
] as const;

type EditableFlagField = (typeof editableFlagFields)[number];

function isEditableFlagField(dataIndex: unknown): dataIndex is EditableFlagField {
  return (
    typeof dataIndex === 'string' &&
    (editableFlagFields as readonly string[]).includes(dataIndex)
  );
}

function getFlagChecked(
  record: GenApi.GenTableColumn,
  dataIndex: unknown,
) {
  return isEditableFlagField(dataIndex) && record[dataIndex] === '1';
}

function setFlagChecked(
  record: GenApi.GenTableColumn,
  dataIndex: unknown,
  checked: boolean,
) {
  if (isEditableFlagField(dataIndex)) {
    record[dataIndex] = checked ? '1' : '0';
  }
}

const [Form, formApi] = useVbenForm({
  schema: [
    { component: 'Input', componentProps: { disabled: true }, fieldName: 'tableName', label: '表名称' },
    { component: 'Input', fieldName: 'tableComment', label: '表描述', rules: 'required' },
    { component: 'Input', fieldName: 'className', label: '实体类名称', rules: 'required' },
    { component: 'Input', fieldName: 'functionAuthor', label: '作者', rules: 'required' },
    {
      component: 'RadioGroup',
      componentProps: {
        options: [
          { label: '单表（增删改查）', value: 'crud' },
          { label: '树表（增删改查）', value: 'tree' },
        ],
      },
      defaultValue: 'crud',
      fieldName: 'tplCategory',
      label: '生成模板',
    },
    {
      component: 'Select',
      componentProps: {
        options: [
          { label: 'element-ui 模版', value: '' },
          { label: 'element-plus 模版', value: 'element-plus' },
          { label: 'element-plus-typescript 模版', value: 'element-plus-typescript' },
        ],
      },
      defaultValue: '',
      fieldName: 'tplWebType',
      label: '前端类型',
    },
    { component: 'Input', fieldName: 'packageName', label: '生成包路径', rules: 'required' },
    { component: 'Input', fieldName: 'moduleName', label: '生成模块名', rules: 'required' },
    { component: 'Input', fieldName: 'businessName', label: '生成业务名', rules: 'required' },
    { component: 'Input', fieldName: 'functionName', label: '生成功能名', rules: 'required' },
    { component: 'Textarea', fieldName: 'remark', label: '备注' },
  ],
  showDefaultActions: false,
  wrapperClass: 'grid-cols-2 gap-x-4',
});

async function load() {
  const res = await getGenTable(tableId);
  formApi.setValues(res.data.info);
  columns.value = res.data.rows;
}

async function onSave() {
  const { valid } = await formApi.validate();
  if (!valid) return;
  const values = await formApi.getValues<GenApi.GenTable>();
  await updateGenTable({ ...values, tableId, columns: columns.value });
  message.success('保存成功');
  router.push({ path: '/tool/gen' });
}

function onCancel() {
  router.push({ path: '/tool/gen' });
}

const tableColumns = [
  { title: '字段列名', dataIndex: 'columnName', width: 120 },
  { title: '字段描述', dataIndex: 'columnComment', width: 140 },
  { title: '物理类型', dataIndex: 'columnType', width: 100 },
  { title: 'Java类型', dataIndex: 'javaType', width: 130 },
  { title: 'Java属性', dataIndex: 'javaField', width: 120 },
  { title: '插入', dataIndex: 'isInsert', width: 60 },
  { title: '编辑', dataIndex: 'isEdit', width: 60 },
  { title: '列表', dataIndex: 'isList', width: 60 },
  { title: '查询', dataIndex: 'isQuery', width: 60 },
  { title: '查询方式', dataIndex: 'queryType', width: 110 },
  { title: '必填', dataIndex: 'isRequired', width: 60 },
  { title: '显示类型', dataIndex: 'htmlType', width: 130 },
  { title: '字典类型', dataIndex: 'dictType', width: 120 },
];

onMounted(() => {
  load();
});
</script>

<template>
  <Page auto-content-height title="修改生成配置">
    <Tabs
      v-model:active-key="activeTab"
      :items="[
        { key: 'basic', label: '基本信息' },
        { key: 'columns', label: '字段信息' },
      ]"
    />
    <div v-show="activeTab === 'basic'" class="p-4">
      <Form />
    </div>
    <div v-show="activeTab === 'columns'" class="overflow-x-auto p-4">
      <Table
        :columns="tableColumns"
        :data-source="columns"
        :pagination="false"
        row-key="columnId"
        size="small"
        :scroll="{ x: 1400 }"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="isEditableFlagField(column.dataIndex)">
            <Checkbox
              :checked="getFlagChecked(record, column.dataIndex)"
              @update:checked="(v: boolean) => setFlagChecked(record, column.dataIndex, v)"
            />
          </template>
          <template v-else-if="column.dataIndex === 'columnComment'">
            <Input v-model:value="record.columnComment" size="small" />
          </template>
          <template v-else-if="column.dataIndex === 'javaField'">
            <Input v-model:value="record.javaField" size="small" />
          </template>
          <template v-else-if="column.dataIndex === 'javaType'">
            <Select v-model:value="record.javaType" :options="JAVA_TYPE_OPTIONS" size="small" class="w-full" />
          </template>
          <template v-else-if="column.dataIndex === 'queryType'">
            <Select v-model:value="record.queryType" :options="QUERY_TYPE_OPTIONS" size="small" class="w-full" />
          </template>
          <template v-else-if="column.dataIndex === 'htmlType'">
            <Select v-model:value="record.htmlType" :options="HTML_TYPE_OPTIONS" size="small" class="w-full" />
          </template>
          <template v-else-if="column.dataIndex === 'dictType'">
            <Input v-model:value="record.dictType" size="small" />
          </template>
        </template>
      </Table>
    </div>
    <div class="flex gap-2 p-4">
      <Button type="primary" @click="onSave">保存</Button>
      <Button @click="onCancel">返回</Button>
    </div>
  </Page>
</template>
