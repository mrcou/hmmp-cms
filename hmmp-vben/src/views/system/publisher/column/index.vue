<script lang="ts" setup>
import type { PublisherApi } from '#/api/biz/publisher';

import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as publisherApi from '#/api/biz/publisher';

const loading = ref(false);
const dataSource = ref<PublisherApi.Column[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });
const searchForm = reactive({ columnName: '', columnType: undefined as string | undefined });
const modalVisible = ref(false);
const modalTitle = ref('新增栏目');
const formData = reactive<PublisherApi.Column>({ columnName: '', parentId: undefined, orderNum: 0, columnCode: '', columnType: '', status: '0', description: '' });
const isEdit = ref(false);

const columns = [
  { title: '栏目ID', dataIndex: 'columnId', key: 'columnId', width: 100 },
  { title: '栏目名称', dataIndex: 'columnName', key: 'columnName', width: 160 },
  { title: '栏目编码', dataIndex: 'columnCode', key: 'columnCode', width: 120 },
  { title: '栏目类型', dataIndex: 'columnType', key: 'columnType', width: 120 },
  { title: '排序', dataIndex: 'orderNum', key: 'orderNum', width: 80 },
  { title: '父级ID', dataIndex: 'parentId', key: 'parentId', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '描述', dataIndex: 'description', key: 'description' },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchList() {
  loading.value = true;
  try {
    const res = await publisherApi.getColumnList({
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
      ...searchForm,
    });
    dataSource.value = res.data?.rows ?? res.data ?? [];
    pagination.total = res.data?.total ?? 0;
  } finally {
    loading.value = false;
  }
}

function handleSearch() {
  pagination.current = 1;
  fetchList();
}

function handleReset() {
  searchForm.columnName = '';
  searchForm.columnType = undefined;
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  modalTitle.value = '新增栏目';
  formData.columnName = '';
  formData.parentId = undefined;
  formData.orderNum = 0;
  formData.columnCode = '';
  formData.columnType = '';
  formData.status = '0';
  formData.description = '';
  modalVisible.value = true;
}

function handleEdit(record: PublisherApi.Column) {
  isEdit.value = true;
  modalTitle.value = '编辑栏目';
  Object.assign(formData, record);
  modalVisible.value = true;
}

async function handleDelete(record: PublisherApi.Column) {
  await publisherApi.deleteColumn([record.columnId!]);
  message.success('删除成功');
  fetchList();
}

async function handleSubmit() {
  if (isEdit.value) {
    await publisherApi.updateColumn({ ...formData });
    message.success('编辑成功');
  } else {
    await publisherApi.createColumn({ ...formData });
    message.success('新增成功');
  }
  modalVisible.value = false;
  fetchList();
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchList();
}

onMounted(() => {
  fetchList();
});
</script>

<template>
  <div class="p-4">
    <a-form layout="inline" class="mb-4">
      <a-form-item label="栏目名称">
        <a-input v-model:value="searchForm.columnName" placeholder="请输入栏目名称" style="width: 180px" />
      </a-form-item>
      <a-form-item label="栏目类型">
        <a-select v-model:value="searchForm.columnType" placeholder="请选择" allow-clear style="width: 140px">
          <a-select-option value="regular">常规栏目</a-select-option>
          <a-select-option value="special">专题栏目</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleSearch">查询</a-button>
          <a-button @click="handleReset">重置</a-button>
        </a-space>
      </a-form-item>
    </a-form>

    <div class="mb-4">
      <a-button type="primary" @click="handleAdd">新增栏目</a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      row-key="columnId"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <a-tag :color="record.status === '0' ? 'green' : 'red'">
            {{ record.status === '0' ? '启用' : '停用' }}
          </a-tag>
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
            <a-popconfirm title="确认删除该栏目吗？" @confirm="handleDelete(record)">
              <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="handleSubmit" destroy-on-close :width="560">
      <a-form layout="vertical">
        <a-form-item label="栏目名称" required>
          <a-input v-model:value="formData.columnName" placeholder="请输入栏目名称" />
        </a-form-item>
        <a-form-item label="栏目编码">
          <a-input v-model:value="formData.columnCode" placeholder="请输入栏目编码" />
        </a-form-item>
        <a-form-item label="栏目类型">
          <a-select v-model:value="formData.columnType" placeholder="请选择栏目类型">
            <a-select-option value="regular">常规栏目</a-select-option>
            <a-select-option value="special">专题栏目</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="父级栏目ID">
          <a-input-number v-model:value="formData.parentId" :min="0" style="width: 100%" placeholder="0表示顶级栏目" />
        </a-form-item>
        <a-form-item label="排序">
          <a-input-number v-model:value="formData.orderNum" :min="0" style="width: 100%" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="formData.status">
            <a-select-option value="0">启用</a-select-option>
            <a-select-option value="1">停用</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="formData.description" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>