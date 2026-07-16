<script lang="ts" setup>
import type { EditorApi } from '#/api/biz/editor';

import { onMounted, reactive, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';

// ---------- 状态常量 ----------
const editStatusMap: Record<string, { label: string; color: string }> = {
  '0': { label: '待编辑', color: 'orange' },
  '1': { label: '编辑中', color: 'blue' },
  '2': { label: '已完成', color: 'green' },
  '3': { label: '已退回', color: 'red' },
};

/** 三级 leaf：pending=待编辑(0)，manage/detail 不强制状态 */
const pathModeMap: Record<string, string | undefined> = {
  pending: '0',
  manage: undefined,
  detail: undefined,
};

const route = useRoute();
const router = useRouter();

function resolveStatusFromRoute(): string | undefined {
  const leaf = route.path.split('/').filter(Boolean).pop() ?? '';
  return pathModeMap[leaf];
}

const pageMode = ref(route.path.split('/').filter(Boolean).pop() ?? 'manage');

// ---------- 查询参数 ----------
const searchForm = reactive({
  manuscriptId: undefined as number | undefined,
  editorName: '',
  editStatus: undefined as string | undefined,
});

// ---------- 表格 ----------
const tableData = ref<EditorApi.Edit[]>([]);
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
  { title: '编辑ID', dataIndex: 'editId', key: 'editId', width: 80 },
  { title: '稿件ID', dataIndex: 'manuscriptId', key: 'manuscriptId', width: 80 },
  { title: '编辑人', dataIndex: 'editorName', key: 'editorName', width: 110 },
  { title: '编辑状态', dataIndex: 'editStatus', key: 'editStatus', width: 100 },
  { title: '版本', dataIndex: 'version', key: 'version', width: 70 },
  { title: '字数(前)', dataIndex: 'wordCountBefore', key: 'wordCountBefore', width: 90 },
  { title: '字数(后)', dataIndex: 'wordCountAfter', key: 'wordCountAfter', width: 90 },
  { title: '格式检查', dataIndex: 'formatCheck', key: 'formatCheck', width: 100 },
  { title: '开始时间', dataIndex: 'editStartTime', key: 'editStartTime', width: 160 },
  { title: '结束时间', dataIndex: 'editEndTime', key: 'editEndTime', width: 160 },
  { title: '操作', key: 'action', width: 160, fixed: 'right' },
];

// ---------- 弹窗 ----------
const formModalOpen = ref(false);
const formModalTitle = ref('新增编辑任务');
const isEdit = ref(false);
const formLoading = ref(false);
const formData = reactive<EditorApi.Edit>({
  manuscriptId: 0,
  editorId: undefined,
  editorName: '',
  editStatus: '0',
  editStartTime: '',
  editEndTime: '',
  editContent: '',
  editSuggestion: '',
  formatCheck: '',
  wordCountBefore: undefined,
  wordCountAfter: undefined,
  version: 1,
});

// ---------- 生命周期 ----------
onMounted(() => {
  pageMode.value = route.path.split('/').filter(Boolean).pop() ?? 'manage';
  if (Object.keys(route.query).length > 0) {
    router.replace({ path: route.path });
  }
  const forced = resolveStatusFromRoute();
  if (forced !== undefined) {
    searchForm.editStatus = forced;
  }
  loadTable();
});

watch(
  () => route.path,
  () => {
    pageMode.value = route.path.split('/').filter(Boolean).pop() ?? 'manage';
    searchForm.editStatus = resolveStatusFromRoute();
    pagination.current = 1;
    loadTable();
  },
);

// ---------- 方法 ----------
async function loadTable() {
  tableLoading.value = true;
  try {
    const params = {
      ...searchForm,
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
    };
    const forced = resolveStatusFromRoute();
    if (forced !== undefined) {
      params.editStatus = forced;
    }
    const res = await editorApi.getEditList(params);
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载编辑加工列表失败');
  } finally {
    tableLoading.value = false;
  }
}

function onSearch() {
  pagination.current = 1;
  loadTable();
}

function onReset() {
  searchForm.manuscriptId = undefined;
  searchForm.editorName = '';
  searchForm.editStatus = resolveStatusFromRoute();
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
  formModalTitle.value = '新增编辑任务';
  Object.assign(formData, {
    editId: undefined,
    manuscriptId: 0,
    editorId: undefined,
    editorName: '',
    editStatus: '0',
    editStartTime: '',
    editEndTime: '',
    editContent: '',
    editSuggestion: '',
    formatCheck: '',
    wordCountBefore: undefined,
    wordCountAfter: undefined,
    version: 1,
  });
  formModalOpen.value = true;
}

function onEdit(row: EditorApi.Edit) {
  isEdit.value = true;
  formModalTitle.value = '编辑加工任务';
  Object.assign(formData, row);
  formModalOpen.value = true;
}

async function onFormSubmit() {
  formLoading.value = true;
  try {
    if (isEdit.value) {
      await editorApi.updateEdit({ ...formData });
      message.success('修改成功');
    } else {
      await editorApi.createEdit({ ...formData });
      message.success('新增成功');
    }
    formModalOpen.value = false;
    loadTable();
  } catch {
    message.error('操作失败');
  } finally {
    formLoading.value = false;
  }
}

function getStatusTag(status: string | undefined) {
  return editStatusMap[status ?? ''] ?? { label: status ?? '未知', color: 'default' };
}
</script>

<template>
  <div class="p-4">
    <a-card class="mb-4" size="small">
      <a-form layout="inline">
        <a-form-item label="稿件ID">
          <a-input-number v-model:value="searchForm.manuscriptId" placeholder="请输入" style="width:150px" />
        </a-form-item>
        <a-form-item label="编辑人">
          <a-input v-model:value="searchForm.editorName" placeholder="请输入" allow-clear style="width:150px" />
        </a-form-item>
        <a-form-item v-if="pageMode !== 'pending'" label="编辑状态">
          <a-select v-model:value="searchForm.editStatus" placeholder="请选择" allow-clear style="width:150px">
            <a-select-option v-for="(val, key) in editStatusMap" :key="key" :value="key">
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

    <a-card>
      <div class="mb-4">
        <a-button type="primary" @click="onCreate">新增编辑任务</a-button>
      </div>
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        :scroll="{ x: 1200 }"
        row-key="editId"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'editStatus'">
            <a-tag :color="getStatusTag(record.editStatus).color">
              {{ getStatusTag(record.editStatus).label }}
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

    <a-modal v-model:open="formModalOpen" :title="formModalTitle" :confirm-loading="formLoading" @ok="onFormSubmit" width="600px" destroy-on-close>
      <a-form layout="vertical">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="稿件ID" required>
              <a-input-number v-model:value="formData.manuscriptId" placeholder="请输入稿件ID" style="width:100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="编辑人">
              <a-input v-model:value="formData.editorName" placeholder="请输入编辑人" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="编辑状态">
              <a-select v-model:value="formData.editStatus" placeholder="请选择状态">
                <a-select-option v-for="(val, key) in editStatusMap" :key="key" :value="key">{{ val.label }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="版本">
              <a-input-number v-model:value="formData.version" :min="1" style="width:100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="开始时间">
              <a-date-picker v-model:value="formData.editStartTime" show-time value-format="YYYY-MM-DD HH:mm:ss" style="width:100%" placeholder="请选择开始时间" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="结束时间">
              <a-date-picker v-model:value="formData.editEndTime" show-time value-format="YYYY-MM-DD HH:mm:ss" style="width:100%" placeholder="请选择结束时间" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="字数(编辑前)">
              <a-input-number v-model:value="formData.wordCountBefore" style="width:100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="字数(编辑后)">
              <a-input-number v-model:value="formData.wordCountAfter" style="width:100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="格式检查">
          <a-input v-model:value="formData.formatCheck" placeholder="请输入格式检查结果" />
        </a-form-item>
        <a-form-item label="编辑内容">
          <a-textarea v-model:value="formData.editContent" placeholder="请输入编辑内容" :rows="3" />
        </a-form-item>
        <a-form-item label="编辑建议">
          <a-textarea v-model:value="formData.editSuggestion" placeholder="请输入编辑建议" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
