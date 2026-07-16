<script lang="ts" setup>
import type { EditorApi } from '#/api/biz/editor';

import { onMounted, reactive, ref } from 'vue';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';

// ---------- 状态常量 ----------
const auditStatusMap: Record<string, { label: string; color: string }> = {
  '0': { label: '待审', color: 'orange' },
  '1': { label: '已审回', color: 'green' },
  '2': { label: '已撤审', color: 'red' },
  '3': { label: '已接受退审', color: 'blue' },
};

// ---------- 查询参数 ----------
const searchForm = reactive<EditorApi.AuditListParams>({
  manuscriptId: undefined,
  reviewerId: undefined,
  auditStatus: undefined,
});

// ---------- 表格 ----------
const tableData = ref<EditorApi.Audit[]>([]);
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
  { title: '审稿ID', dataIndex: 'auditId', key: 'auditId', width: 80 },
  { title: '稿件ID', dataIndex: 'manuscriptId', key: 'manuscriptId', width: 80 },
  { title: '审稿人', dataIndex: 'reviewerName', key: 'reviewerName', width: 120 },
  { title: '审稿轮次', dataIndex: 'reviewRound', key: 'reviewRound', width: 90 },
  { title: '审稿状态', dataIndex: 'auditStatus', key: 'auditStatus', width: 110 },
  { title: '审稿结果', dataIndex: 'auditResult', key: 'auditResult', width: 100 },
  { title: '审稿评分', dataIndex: 'auditScore', key: 'auditScore', width: 90 },
  { title: '邀请时间', dataIndex: 'inviteTime', key: 'inviteTime', width: 160 },
  { title: '截止时间', dataIndex: 'deadlineTime', key: 'deadlineTime', width: 160 },
  { title: '审稿时间', dataIndex: 'auditTime', key: 'auditTime', width: 160 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' },
];

// ---------- 弹窗 ----------
const formModalOpen = ref(false);
const formModalTitle = ref('邀请审稿人');
const isEdit = ref(false);
const formLoading = ref(false);
const formData = reactive<EditorApi.Audit>({
  manuscriptId: 0,
  reviewerId: 0,
  reviewerName: '',
  reviewRound: 1,
  auditStatus: '0',
  deadlineTime: '',
  inviteMessage: '',
});

// ---------- 生命周期 ----------
onMounted(() => {
  loadTable();
});

// ---------- 方法 ----------
async function loadTable() {
  tableLoading.value = true;
  try {
    const params: EditorApi.AuditListParams = {
      ...searchForm,
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
    };
    const res = await editorApi.getAuditList(params);
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载审稿列表失败');
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
  searchForm.reviewerId = undefined;
  searchForm.auditStatus = undefined;
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
  formModalTitle.value = '邀请审稿人';
  Object.assign(formData, {
    auditId: undefined,
    manuscriptId: 0,
    reviewerId: 0,
    reviewerName: '',
    reviewRound: 1,
    auditStatus: '0',
    deadlineTime: '',
    inviteMessage: '',
  });
  formModalOpen.value = true;
}

function onEdit(row: EditorApi.Audit) {
  isEdit.value = true;
  formModalTitle.value = '编辑审稿记录';
  Object.assign(formData, row);
  formModalOpen.value = true;
}

async function onFormSubmit() {
  formLoading.value = true;
  try {
    if (isEdit.value) {
      await editorApi.returnAudit({ ...formData });
      message.success('修改成功');
    } else {
      await editorApi.createAudit({ ...formData });
      message.success('邀请成功');
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
  return auditStatusMap[status ?? ''] ?? { label: status ?? '未知', color: 'default' };
}
</script>

<template>
  <div class="p-4">
    <!-- 搜索区域 -->
    <a-card class="mb-4" size="small">
      <a-form layout="inline">
        <a-form-item label="稿件ID">
          <a-input-number v-model:value="searchForm.manuscriptId" placeholder="请输入" style="width:150px" />
        </a-form-item>
        <a-form-item label="审稿人ID">
          <a-input-number v-model:value="searchForm.reviewerId" placeholder="请输入" style="width:150px" />
        </a-form-item>
        <a-form-item label="审稿状态">
          <a-select v-model:value="searchForm.auditStatus" placeholder="请选择" allow-clear style="width:150px">
            <a-select-option v-for="(val, key) in auditStatusMap" :key="key" :value="key">
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
        <a-button type="primary" @click="onCreate">邀请审稿人</a-button>
      </div>
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        :scroll="{ x: 1300 }"
        row-key="auditId"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'auditStatus'">
            <a-tag :color="getStatusTag(record.auditStatus).color">
              {{ getStatusTag(record.auditStatus).label }}
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

    <!-- 邀请/编辑弹窗 -->
    <a-modal v-model:open="formModalOpen" :title="formModalTitle" :confirm-loading="formLoading" @ok="onFormSubmit" width="520px" destroy-on-close>
      <a-form layout="vertical">
        <a-form-item label="稿件ID" required>
          <a-input-number v-model:value="formData.manuscriptId" placeholder="请输入稿件ID" style="width:100%" />
        </a-form-item>
        <a-form-item label="审稿人ID" required>
          <a-input-number v-model:value="formData.reviewerId" placeholder="请输入审稿人ID" style="width:100%" />
        </a-form-item>
        <a-form-item label="审稿轮次">
          <a-input-number v-model:value="formData.reviewRound" :min="1" placeholder="请输入审稿轮次" style="width:100%" />
        </a-form-item>
        <a-form-item label="截止日期">
          <a-date-picker v-model:value="formData.deadlineTime" value-format="YYYY-MM-DD" style="width:100%" placeholder="请选择截止日期" />
        </a-form-item>
        <a-form-item label="邀请信息">
          <a-textarea v-model:value="formData.inviteMessage" placeholder="请输入邀请信息" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>