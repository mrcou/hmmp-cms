<script lang="ts" setup>
import type { PublisherNewsApi } from '#/api/biz/publisher-news';

import { onMounted, reactive, ref } from 'vue';

import { Page, VbenTableAction } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';

import { message } from 'antdv-next';

import * as newsApi from '#/api/biz/publisher-news';

defineOptions({ name: 'PublisherNewsColumn' });

const loading = ref(false);
const submitting = ref(false);
const drawerOpen = ref(false);
const isEdit = ref(false);
const dataSource = ref<PublisherNewsApi.Column[]>([]);
const searchName = ref('');
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条记录`,
});
const formData = reactive<PublisherNewsApi.Column>({
  columnName: '',
  sortOrder: 0,
});

const columns = [
  { title: '栏目ID', dataIndex: 'columnId', key: 'columnId', width: 100 },
  { title: '栏目名称', dataIndex: 'columnName', key: 'columnName' },
  { title: '排序', dataIndex: 'sortOrder', key: 'sortOrder', width: 100 },
  {
    title: '内容数量',
    dataIndex: 'contentCount',
    key: 'contentCount',
    width: 110,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
    width: 180,
  },
  {
    title: '操作',
    dataIndex: 'action',
    key: 'action',
    fixed: 'right' as const,
    width: 150,
  },
];

function resetForm() {
  formData.columnId = undefined;
  formData.columnName = '';
  formData.sortOrder = 0;
}

async function fetchList() {
  loading.value = true;
  try {
    const res = (await newsApi.getNewsColumnList({
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
      columnName: searchName.value.trim() || undefined,
    })) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
  } finally {
    loading.value = false;
  }
}

function handleSearch() {
  pagination.current = 1;
  fetchList();
}

function handleReset() {
  searchName.value = '';
  pagination.current = 1;
  fetchList();
}

function handleAdd() {
  isEdit.value = false;
  resetForm();
  drawerOpen.value = true;
}

function handleEdit(record: PublisherNewsApi.Column) {
  isEdit.value = true;
  Object.assign(formData, {
    columnId: record.columnId,
    columnName: record.columnName,
    sortOrder: record.sortOrder ?? 0,
  });
  drawerOpen.value = true;
}

async function handleSubmit() {
  const columnName = formData.columnName.trim();
  if (!columnName) {
    message.warning('请输入栏目名称');
    return;
  }
  submitting.value = true;
  try {
    const payload = {
      ...formData,
      columnName,
      sortOrder: Number(formData.sortOrder || 0),
    };
    if (isEdit.value) {
      await newsApi.updateNewsColumn(payload);
      message.success('修改成功');
    } else {
      await newsApi.createNewsColumn(payload);
      message.success('添加成功');
    }
    drawerOpen.value = false;
    fetchList();
  } finally {
    submitting.value = false;
  }
}

async function handleDelete(record: PublisherNewsApi.Column) {
  await newsApi.deleteNewsColumn([record.columnId!]);
  message.success('删除成功');
  if (dataSource.value.length === 1 && pagination.current > 1) {
    pagination.current -= 1;
  }
  fetchList();
}

function handleTableChange(page: { current: number; pageSize: number }) {
  pagination.current = page.current;
  pagination.pageSize = page.pageSize;
  fetchList();
}

onMounted(fetchList);
</script>

<template>
  <Page auto-content-height>
    <div class="mb-4 flex flex-wrap items-center justify-between gap-3">
      <a-button type="primary" @click="handleAdd">
        <IconifyIcon icon="lucide:plus" class="mr-1 size-4" />
        添加栏目
      </a-button>
      <a-form layout="inline">
        <a-form-item label="栏目名称">
          <a-input
            v-model:value="searchName"
            allow-clear
            placeholder="请输入栏目名称"
            style="width: 220px"
            @press-enter="handleSearch"
          />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :loading="loading"
      :pagination="pagination"
      :scroll="{ x: 850 }"
      row-key="columnId"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'contentCount'">
          <a-tag color="blue">{{ record.contentCount || 0 }}</a-tag>
        </template>
        <template v-else-if="column.key === 'action'">
          <VbenTableAction
            :actions="[
              {
                text: '编辑',
                icon: 'lucide:edit',
                onClick: () => handleEdit(record),
              },
              {
                text: '删除',
                icon: 'lucide:trash-2',
                danger: true,
                disabled: record.contentCount > 0,
                popConfirm: {
                  title: `确认删除栏目“${record.columnName}”吗？`,
                  confirm: () => handleDelete(record),
                },
              },
            ]"
          />
        </template>
      </template>
    </a-table>

    <a-drawer
      v-model:open="drawerOpen"
      :title="isEdit ? '编辑栏目' : '添加栏目'"
      placement="right"
      :width="520"
      destroy-on-close
    >
      <a-form layout="vertical">
        <a-form-item label="栏目名称" required>
          <a-input
            v-model:value="formData.columnName"
            :maxlength="100"
            show-count
            placeholder="请输入栏目名称"
          />
        </a-form-item>
        <a-form-item
          label="排序"
          extra="数值越小越靠前，排序相同时按栏目ID排列"
        >
          <a-input-number
            v-model:value="formData.sortOrder"
            :min="0"
            :max="9999"
            style="width: 100%"
          />
        </a-form-item>
      </a-form>
      <template #footer>
        <div class="flex justify-end gap-2">
          <a-button @click="drawerOpen = false">取消</a-button>
          <a-button
            type="primary"
            :loading="submitting"
            @click="handleSubmit"
          >
            保存
          </a-button>
        </div>
      </template>
    </a-drawer>
  </Page>
</template>
