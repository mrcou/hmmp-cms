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

const applyTypeMap: Record<string, string> = {
  '1': '自荐申请',
  '2': '推荐申请',
};

// ---------- 查询参数 ----------
const searchForm = reactive({
  realName: '',
  affiliation: '',
  applyType: undefined as string | undefined,
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
  { title: '申请类型', dataIndex: 'applyType', key: 'applyType', width: 100 },
  { title: '审核状态', dataIndex: 'auditStatus', key: 'auditStatus', width: 100 },
  { title: '申请理由', dataIndex: 'applyReason', key: 'applyReason', ellipsis: true, width: 200 },
  { title: '审核时间', dataIndex: 'auditTime', key: 'auditTime', width: 160 },
  { title: '审核人', dataIndex: 'auditBy', key: 'auditBy', width: 110 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' },
];

// ---------- 审核弹窗 ----------
const auditModalOpen = ref(false);
const auditForm = reactive({
  applyId: 0,
  auditStatus: '1',
  auditOpinion: '',
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
    const res = await editorApi.getReviewerApplyList(params);
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载申请列表失败');
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
  searchForm.applyType = undefined;
  searchForm.auditStatus = undefined;
  pagination.current = 1;
  loadTable();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  loadTable();
}

function onAudit(row: EditorApi.ReviewerApply) {
  auditForm.applyId = row.applyId!;
  auditForm.auditStatus = '1';
  auditForm.auditOpinion = '';
  auditModalOpen.value = true;
}

async function onAuditSubmit() {
  try {
    await editorApi.auditReviewerApply({ ...auditForm } as any);
    message.success('审核成功');
    auditModalOpen.value = false;
    loadTable();
  } catch {
    message.error('审核失败');
  }
}

function getStatusTag(status: string | undefined) {
  return auditStatusMap[status ?? ''] ?? { label: status ?? '未知', color: 'default' };
}

function getApplyTypeLabel(type: string | undefined) {
  return applyTypeMap[type ?? ''] ?? type ?? '';
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
        <a-form-item label="申请类型">
          <a-select v-model:value="searchForm.applyType" placeholder="请选择" allow-clear style="width:150px">
            <a-select-option v-for="(val, key) in applyTypeMap" :key="key" :value="key">
              {{ val }}
            </a-select-option>
          </a-select>
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
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        :scroll="{ x: 1400 }"
        row-key="applyId"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'applyType'">
            {{ getApplyTypeLabel(record.applyType) }}
          </template>
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
                审核
              </a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <!-- 审核弹窗 -->
    <a-modal v-model:open="auditModalOpen" title="审核申请" @ok="onAuditSubmit" destroy-on-close>
      <a-form layout="vertical">
        <a-form-item label="审核结果" required>
          <a-radio-group v-model:value="auditForm.auditStatus">
            <a-radio value="1">通过</a-radio>
            <a-radio value="2">拒绝</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="审核意见">
          <a-textarea v-model:value="auditForm.auditOpinion" placeholder="请输入审核意见" :rows="4" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>