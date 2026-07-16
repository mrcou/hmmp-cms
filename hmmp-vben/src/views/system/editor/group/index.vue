<script lang="ts" setup>
import type { EditorApi } from '#/api/biz/editor';

import { onMounted, reactive, ref } from 'vue';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';

// ---------- 状态常量 ----------
const groupStatusMap: Record<string, { label: string; color: string }> = {
  '0': { label: '待组稿', color: 'orange' },
  '1': { label: '组稿中', color: 'blue' },
  '2': { label: '已完成', color: 'green' },
};

const groupTypeMap: Record<string, string> = {
  '1': '专题组稿',
  '2': '专栏组稿',
  '3': '自由组稿',
};

// ---------- 查询参数 ----------
const searchForm = reactive({
  groupName: '',
  groupType: undefined as string | undefined,
  status: undefined as string | undefined,
});

// ---------- 表格 ----------
const tableData = ref<EditorApi.Group[]>([]);
const tableLoading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  showTotal: (total: number) => `共 ${total} 条`,
});

const columns = [
  { title: '组稿ID', dataIndex: 'groupId', key: 'groupId', width: 80 },
  { title: '组稿名称', dataIndex: 'groupName', key: 'groupName', ellipsis: true, width: 200 },
  { title: '期号ID', dataIndex: 'issueId', key: 'issueId', width: 80 },
  { title: '组稿类型', dataIndex: 'groupType', key: 'groupType', width: 120 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '备注', dataIndex: 'remark', key: 'remark', ellipsis: true, width: 200 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' },
];

// ---------- 弹窗 ----------
const formModalOpen = ref(false);
const formModalTitle = ref('新增组稿');
const isEdit = ref(false);
const formLoading = ref(false);
const formData = reactive<EditorApi.Group>({
  groupName: '',
  issueId: undefined,
  groupType: '1',
  status: '0',
  remark: '',
});

// ---------- 生命周期 ----------
onMounted(() => {
  loadTable();
});

// ---------- 方法 ----------
async function loadTable() {
  tableLoading.value = true;
  try {
    const params = {
      ...searchForm,
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
    };
    const res = await editorApi.getGroupList(params);
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载组稿列表失败');
  } finally {
    tableLoading.value = false;
  }
}

function onSearch() {
  pagination.current = 1;
  loadTable();
}

function onReset() {
  searchForm.groupName = '';
  searchForm.groupType = undefined;
  searchForm.status = undefined;
  pagination.current = 1;
  loadTable();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  loadTable();
}

function onCreate() {
  isEdit.value = false;
  formModalTitle.value = '新增组稿';
  Object.assign(formData, {
    groupId: undefined,
    groupName: '',
    issueId: undefined,
    groupType: '1',
    status: '0',
    remark: '',
  });
  formModalOpen.value = true;
}

function onEdit(row: EditorApi.Group) {
  isEdit.value = true;
  formModalTitle.value = '编辑组稿';
  Object.assign(formData, row);
  formModalOpen.value = true;
}

async function onFormSubmit() {
  formLoading.value = true;
  try {
    await editorApi.createGroup({ ...formData });
    message.success(isEdit.value ? '修改成功' : '新增成功');
    formModalOpen.value = false;
    loadTable();
  } catch {
    message.error('操作失败');
  } finally {
    formLoading.value = false;
  }
}

function getStatusTag(status: string | undefined) {
  return groupStatusMap[status ?? ''] ?? { label: status ?? '未知', color: 'default' };
}

function getTypeLabel(type: string | undefined) {
  return groupTypeMap[type ?? ''] ?? type ?? '';
}
</script>

<template>
  <div class="p-4">
    <!-- 搜索区域 -->
    <a-card class="mb-4" size="small">
      <a-form layout="inline">
        <a-form-item label="组稿名称">
          <a-input v-model:value="searchForm.groupName" placeholder="请输入" allow-clear style="width:180px" />
        </a-form-item>
        <a-form-item label="组稿类型">
          <a-select v-model:value="searchForm.groupType" placeholder="请选择" allow-clear style="width:150px">
            <a-select-option v-for="(val, key) in groupTypeMap" :key="key" :value="key">
              {{ val }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear style="width:150px">
            <a-select-option v-for="(val, key) in groupStatusMap" :key="key" :value="key">
              {{ val.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="onSearch">查询</a-button>
            <a-button @click="onReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>
    </a-card>

    <!-- 表格区域 -->
    <a-card>
      <div class="mb-4">
        <a-button type="primary" @click="onCreate">新增组稿</a-button>
      </div>
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        :scroll="{ x: 1000 }"
        row-key="groupId"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'groupType'">
            {{ getTypeLabel(record.groupType) }}
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="getStatusTag(record.status).color">
              {{ getStatusTag(record.status).label }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button size="small" @click="onEdit(record)">编辑</a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <!-- 新增/编辑弹窗 -->
    <a-modal v-model:open="formModalOpen" :title="formModalTitle" :confirm-loading="formLoading" @ok="onFormSubmit" width="520px" destroy-on-close>
      <a-form layout="vertical">
        <a-form-item label="组稿名称" required>
          <a-input v-model:value="formData.groupName" placeholder="请输入组稿名称" />
        </a-form-item>
        <a-form-item label="期号ID">
          <a-input-number v-model:value="formData.issueId" placeholder="请输入期号ID" style="width:100%" />
        </a-form-item>
        <a-form-item label="组稿类型">
          <a-select v-model:value="formData.groupType" placeholder="请选择组稿类型">
            <a-select-option v-for="(val, key) in groupTypeMap" :key="key" :value="key">{{ val }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="formData.status" placeholder="请选择状态">
            <a-select-option v-for="(val, key) in groupStatusMap" :key="key" :value="key">{{ val.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea v-model:value="formData.remark" placeholder="请输入备注" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>