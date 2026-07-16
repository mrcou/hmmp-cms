<script lang="ts" setup>
import type { EditorApi } from '#/api/biz/editor';

import { onMounted, reactive, ref } from 'vue';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';

// ---------- 状态常量 ----------
const auditStatusMap: Record<string, { label: string; color: string }> = {
  '0': { label: '待审核', color: 'orange' },
  '1': { label: '已通过', color: 'green' },
  '2': { label: '已拒绝', color: 'red' },
};

// ---------- 查询参数 ----------
const searchForm = reactive({
  realName: '',
  affiliation: '',
  auditStatus: undefined as string | undefined,
});

// ---------- 表格 ----------
const tableData = ref<EditorApi.ReviewerApply[]>([]);
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
  { title: '申请ID', dataIndex: 'applyId', key: 'applyId', width: 80 },
  { title: '姓名', dataIndex: 'realName', key: 'realName', width: 100 },
  { title: '单位', dataIndex: 'affiliation', key: 'affiliation', ellipsis: true, width: 180 },
  { title: '职称', dataIndex: 'title', key: 'title', width: 100 },
  { title: '研究方向', dataIndex: 'researchDirection', key: 'researchDirection', ellipsis: true, width: 150 },
  { title: '擅长领域', dataIndex: 'expertiseFields', key: 'expertiseFields', ellipsis: true, width: 150 },
  { title: '邮箱', dataIndex: 'email', key: 'email', width: 180 },
  { title: '电话', dataIndex: 'phone', key: 'phone', width: 130 },
  { title: '推荐人', dataIndex: 'recommenderName', key: 'recommenderName', width: 100 },
  { title: '审核状态', dataIndex: 'auditStatus', key: 'auditStatus', width: 100 },
  { title: '申请理由', dataIndex: 'applyReason', key: 'applyReason', ellipsis: true, width: 200 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' },
];

// ---------- 推荐弹窗 ----------
const recommendModalOpen = ref(false);
const recommendForm = reactive({
  realName: '',
  affiliation: '',
  title: '',
  researchDirection: '',
  expertiseFields: '',
  email: '',
  phone: '',
  applyReason: '',
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
      applyType: '2',
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
    };
    const res = await editorApi.getReviewerApplyList(params);
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载推荐列表失败');
  } finally {
    tableLoading.value = false;
  }
}

function onSearch() {
  pagination.current = 1;
  loadTable();
}

function onReset() {
  searchForm.realName = '';
  searchForm.affiliation = '';
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
  Object.assign(recommendForm, {
    realName: '',
    affiliation: '',
    title: '',
    researchDirection: '',
    expertiseFields: '',
    email: '',
    phone: '',
    applyReason: '',
  });
  recommendModalOpen.value = true;
}

async function onRecommendSubmit() {
  try {
    await editorApi.createReviewer({ ...recommendForm } as any);
    message.success('推荐成功');
    recommendModalOpen.value = false;
    loadTable();
  } catch {
    message.error('推荐失败');
  }
}

async function onAudit(row: EditorApi.ReviewerApply) {
  try {
    await editorApi.auditReviewerApply({ applyId: row.applyId, auditStatus: '1', auditOpinion: '' } as any);
    message.success('审核通过');
    loadTable();
  } catch {
    message.error('审核失败');
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
        <a-form-item label="姓名">
          <a-input v-model:value="searchForm.realName" placeholder="请输入" allow-clear style="width:150px" />
        </a-form-item>
        <a-form-item label="单位">
          <a-input v-model:value="searchForm.affiliation" placeholder="请输入" allow-clear style="width:180px" />
        </a-form-item>
        <a-form-item label="审核状态">
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
        <a-button type="primary" @click="onCreate">推荐审稿人</a-button>
      </div>
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        :scroll="{ x: 1600 }"
        row-key="applyId"
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
              <a-button
                v-if="record.auditStatus === '0'"
                size="small"
                type="primary"
                @click="onAudit(record)"
              >
                通过
              </a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <!-- 推荐审稿人弹窗 -->
    <a-modal v-model:open="recommendModalOpen" title="推荐审稿人" @ok="onRecommendSubmit" width="600px" destroy-on-close>
      <a-form layout="vertical">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="姓名" required>
              <a-input v-model:value="recommendForm.realName" placeholder="请输入姓名" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="职称">
              <a-input v-model:value="recommendForm.title" placeholder="请输入职称" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="单位">
          <a-input v-model:value="recommendForm.affiliation" placeholder="请输入单位" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="邮箱">
              <a-input v-model:value="recommendForm.email" placeholder="请输入邮箱" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="电话">
              <a-input v-model:value="recommendForm.phone" placeholder="请输入电话" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="研究方向">
          <a-input v-model:value="recommendForm.researchDirection" placeholder="请输入研究方向" />
        </a-form-item>
        <a-form-item label="擅长领域">
          <a-textarea v-model:value="recommendForm.expertiseFields" placeholder="请输入擅长领域" :rows="2" />
        </a-form-item>
        <a-form-item label="推荐理由">
          <a-textarea v-model:value="recommendForm.applyReason" placeholder="请输入推荐理由" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>