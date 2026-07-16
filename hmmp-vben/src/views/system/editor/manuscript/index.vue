<script lang="ts" setup>
import type { EditorApi } from '#/api/biz/editor';

import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';

// ---------- 状态常量 ----------
const manuscriptStatusMap: Record<string, { label: string; color: string }> = {
  '0': { label: '待处理', color: 'blue' },
  '1': { label: '已分配', color: 'cyan' },
  '2': { label: '审稿中', color: 'orange' },
  '3': { label: '退修中', color: 'purple' },
  '4': { label: '已录用', color: 'green' },
  '5': { label: '已退稿', color: 'red' },
  '6': { label: '已发表', color: 'geekblue' },
  '7': { label: '已修回', color: 'lime' },
  '9': { label: '已归档', color: 'default' },
};

// 状态标签页
const statusTabs = [
  { key: '', label: '全部' },
  { key: '0', label: '新收稿' },
  { key: '2', label: '审稿中' },
  { key: '3', label: '已退修' },
  { key: '7', label: '已修回' },
  { key: '4', label: '已采用' },
  { key: '5', label: '已退稿' },
  { key: '6', label: '已发表' },
  { key: '9', label: '归档查询' },
];
const activeTab = ref('');

const route = useRoute();
const router = useRouter();

const manuscriptTypeMap: Record<string, string> = {
  '1': '研究论文',
  '2': '综述',
  '3': '简报',
  '4': '评论',
  '5': '其他',
};

// ---------- 查询参数 ----------
const searchForm = reactive<EditorApi.ManuscriptListParams>({
  fileNo: '',
  title: '',
  authorNames: '',
  status: undefined,
  manuscriptType: undefined,
  beginTime: '',
  endTime: '',
});

const dateRange = computed({
  get: () => [searchForm.beginTime, searchForm.endTime] as [string, string],
  set: (val: [string, string] | null) => {
    if (val) {
      searchForm.beginTime = val[0] || '';
      searchForm.endTime = val[1] || '';
    } else {
      searchForm.beginTime = '';
      searchForm.endTime = '';
    }
  },
});

// ---------- 表格 ----------
const tableData = ref<EditorApi.Manuscript[]>([]);
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
  { title: '稿件ID', dataIndex: 'manuscriptId', key: 'manuscriptId', width: 80 },
  { title: '文件编号', dataIndex: 'fileNo', key: 'fileNo', width: 140 },
  { title: '标题', dataIndex: 'title', key: 'title', ellipsis: true, width: 220 },
  { title: '作者', dataIndex: 'authorNames', key: 'authorNames', width: 120 },
  { title: '稿件类型', dataIndex: 'manuscriptType', key: 'manuscriptType', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '收稿时间', dataIndex: 'receiveTime', key: 'receiveTime', width: 160 },
  { title: '编辑', dataIndex: 'editorName', key: 'editorName', width: 100 },
  { title: '审稿次数', dataIndex: 'auditCount', key: 'auditCount', width: 90 },
  { title: '操作', key: 'action', width: 280, fixed: 'right' },
];

// ---------- 弹窗 ----------
const formModalOpen = ref(false);
const formModalTitle = ref('新增稿件');
const isEdit = ref(false);
const formLoading = ref(false);
const formData = reactive<EditorApi.Manuscript>({
  title: '',
  authorNames: '',
  authorIds: '',
  correspondingAuthor: '',
  correspondingEmail: '',
  manuscriptType: undefined,
  keywords: '',
  abstractText: '',
  fundProject: '',
  fundNo: '',
  wordCount: undefined,
  filePath: '',
  remark: '',
});

// 分配编辑弹窗
const assignModalOpen = ref(false);
const assignForm = reactive({ manuscriptId: 0, editorId: undefined as number | undefined, editorName: '' });

// 送审弹窗
const reviewModalOpen = ref(false);
const reviewForm = reactive({ manuscriptId: 0, reviewerId: undefined as number | undefined, reviewRound: 1, deadlineTime: '', inviteMessage: '' });

// 退修弹窗
const revisionModalOpen = ref(false);
const revisionForm = reactive({ manuscriptId: 0, revisionContent: '' });

// ---------- 生命周期 ----------
onMounted(() => {
  // 从路由 query 读取初始 tab
  const tabFromRoute = route.query.tab as string;
  if (tabFromRoute !== undefined) {
    activeTab.value = tabFromRoute;
  }
  loadTable();
});

// 监听路由 query 变化，同步 tab
watch(
  () => route.query.tab,
  (newTab) => {
    const tab = (newTab as string) ?? '';
    if (tab !== activeTab.value) {
      activeTab.value = tab;
      pagination.current = 1;
      loadTable();
    }
  },
);

// ---------- 方法 ----------
function onTabChange(key: string) {
  activeTab.value = key;
  pagination.current = 1;
  // 同步路由 query
  if (key) {
    router.push({ query: { tab: key } });
  } else {
    router.push({ query: {} });
  }
  loadTable();
}

async function loadTable() {
  tableLoading.value = true;
  try {
    const params: EditorApi.ManuscriptListParams = {
      ...searchForm,
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
    };
    // 标签页过滤：如果选中了状态标签，覆盖搜索表单的 status
    if (activeTab.value) {
      params.status = activeTab.value;
    }
    const res = await editorApi.getManuscriptList(params);
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载稿件列表失败');
  } finally {
    tableLoading.value = false;
  }
}

function onSearch() {
  pagination.current = 1;
  loadTable();
}

function onReset() {
  searchForm.fileNo = '';
  searchForm.title = '';
  searchForm.authorNames = '';
  searchForm.status = undefined;
  searchForm.manuscriptType = undefined;
  searchForm.beginTime = '';
  searchForm.endTime = '';
  activeTab.value = '';
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
  formModalTitle.value = '新增稿件';
  Object.assign(formData, {
    manuscriptId: undefined,
    title: '',
    authorNames: '',
    authorIds: '',
    correspondingAuthor: '',
    correspondingEmail: '',
    manuscriptType: undefined,
    keywords: '',
    abstractText: '',
    fundProject: '',
    fundNo: '',
    wordCount: undefined,
    filePath: '',
    remark: '',
  });
  formModalOpen.value = true;
}

function onEdit(row: EditorApi.Manuscript) {
  isEdit.value = true;
  formModalTitle.value = '编辑稿件';
  Object.assign(formData, row);
  formModalOpen.value = true;
}

async function onDelete(row: EditorApi.Manuscript) {
  try {
    await editorApi.deleteManuscript([row.manuscriptId!]);
    message.success('删除成功');
    loadTable();
  } catch {
    message.error('删除失败');
  }
}

async function onFormSubmit() {
  formLoading.value = true;
  try {
    if (isEdit.value) {
      await editorApi.updateManuscript({ ...formData });
      message.success('修改成功');
    } else {
      await editorApi.createManuscript({ ...formData });
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

// 业务操作
function onAssign(row: EditorApi.Manuscript) {
  assignForm.manuscriptId = row.manuscriptId!;
  assignForm.editorId = undefined;
  assignForm.editorName = '';
  assignModalOpen.value = true;
}

async function onAssignSubmit() {
  try {
    await editorApi.assignEditor({ manuscriptId: assignForm.manuscriptId, editorId: assignForm.editorId });
    message.success('分配编辑成功');
    assignModalOpen.value = false;
    loadTable();
  } catch {
    message.error('分配编辑失败');
  }
}

function onSendForReview(row: EditorApi.Manuscript) {
  reviewForm.manuscriptId = row.manuscriptId!;
  reviewForm.reviewerId = undefined;
  reviewForm.reviewRound = 1;
  reviewForm.deadlineTime = '';
  reviewForm.inviteMessage = '';
  reviewModalOpen.value = true;
}

async function onReviewSubmit() {
  try {
    await editorApi.sendForReview({ ...reviewForm });
    message.success('送审成功');
    reviewModalOpen.value = false;
    loadTable();
  } catch {
    message.error('送审失败');
  }
}

function onSendForRevision(row: EditorApi.Manuscript) {
  revisionForm.manuscriptId = row.manuscriptId!;
  revisionForm.revisionContent = '';
  revisionModalOpen.value = true;
}

async function onRevisionSubmit() {
  try {
    await editorApi.sendForRevision({ manuscriptId: revisionForm.manuscriptId, remark: revisionForm.revisionContent });
    message.success('退修成功');
    revisionModalOpen.value = false;
    loadTable();
  } catch {
    message.error('退修失败');
  }
}

async function onAccept(row: EditorApi.Manuscript) {
  try {
    await editorApi.acceptManuscript({ manuscriptId: row.manuscriptId! });
    message.success('录用成功');
    loadTable();
  } catch {
    message.error('录用失败');
  }
}

async function onReject(row: EditorApi.Manuscript) {
  try {
    await editorApi.rejectManuscript({ manuscriptId: row.manuscriptId! });
    message.success('退稿成功');
    loadTable();
  } catch {
    message.error('退稿失败');
  }
}

function getStatusTag(status: string | undefined) {
  const info = manuscriptStatusMap[status ?? ''] ?? { label: status ?? '未知', color: 'default' };
  return info;
}

function getTypeLabel(type: string | undefined) {
  return manuscriptTypeMap[type ?? ''] ?? type ?? '';
}
</script>

<template>
  <div class="p-4">
    <!-- 状态标签页 -->
    <a-card class="mb-4" size="small">
      <a-tabs v-model:activeKey="activeTab" @change="onTabChange">
        <a-tab-pane v-for="tab in statusTabs" :key="tab.key" :tab="tab.label" />
      </a-tabs>
    </a-card>

    <!-- 搜索区域 -->
    <a-card class="mb-4" size="small">
      <a-form layout="inline">
        <a-form-item label="文件编号">
          <a-input v-model:value="searchForm.fileNo" placeholder="请输入" allow-clear style="width:150px" />
        </a-form-item>
        <a-form-item label="标题">
          <a-input v-model:value="searchForm.title" placeholder="请输入" allow-clear style="width:180px" />
        </a-form-item>
        <a-form-item label="作者">
          <a-input v-model:value="searchForm.authorNames" placeholder="请输入" allow-clear style="width:130px" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear style="width:130px">
            <a-select-option v-for="(val, key) in manuscriptStatusMap" :key="key" :value="key">
              {{ val.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="稿件类型">
          <a-select v-model:value="searchForm.manuscriptType" placeholder="请选择" allow-clear style="width:130px">
            <a-select-option v-for="(val, key) in manuscriptTypeMap" :key="key" :value="key">
              {{ val }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="收稿时间">
          <a-range-picker
            v-model:value="dateRange"
            value-format="YYYY-MM-DD"
            style="width:240px"
          />
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
        <a-button type="primary" @click="onCreate">新增稿件</a-button>
      </div>
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        :scroll="{ x: 1400 }"
        row-key="manuscriptId"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'manuscriptType'">
            {{ getTypeLabel(record.manuscriptType) }}
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="getStatusTag(record.status).color">
              {{ getStatusTag(record.status).label }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button size="small" @click="onEdit(record)">编辑</a-button>
              <a-popconfirm title="确认删除该稿件？" @confirm="onDelete(record)">
                <a-button size="small" danger>删除</a-button>
              </a-popconfirm>
              <a-button v-if="record.status === '0'" size="small" type="primary" @click="onAssign(record)">分配编辑</a-button>
              <a-button v-if="record.status === '1'" size="small" type="primary" @click="onSendForReview(record)">送审</a-button>
              <a-button v-if="record.status === '2'" size="small" @click="onSendForRevision(record)">退修</a-button>
              <a-button v-if="record.status === '2' || record.status === '3'" size="small" type="primary" @click="onAccept(record)">录用</a-button>
              <a-button v-if="record.status === '2' || record.status === '3'" size="small" danger @click="onReject(record)">退稿</a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <!-- 新增/编辑弹窗 -->
    <a-modal v-model:open="formModalOpen" :title="formModalTitle" :confirm-loading="formLoading" @ok="onFormSubmit" width="680px" destroy-on-close>
      <a-form layout="vertical">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="标题" required>
              <a-input v-model:value="formData.title" placeholder="请输入标题" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="稿件类型">
              <a-select v-model:value="formData.manuscriptType" placeholder="请选择稿件类型">
                <a-select-option v-for="(val, key) in manuscriptTypeMap" :key="key" :value="key">{{ val }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="作者">
              <a-input v-model:value="formData.authorNames" placeholder="请输入作者姓名，多个用逗号分隔" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="通讯作者">
              <a-input v-model:value="formData.correspondingAuthor" placeholder="请输入通讯作者" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="通讯邮箱">
              <a-input v-model:value="formData.correspondingEmail" placeholder="请输入通讯邮箱" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="字数">
              <a-input-number v-model:value="formData.wordCount" placeholder="请输入字数" style="width:100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="关键词">
          <a-input v-model:value="formData.keywords" placeholder="请输入关键词，多个用逗号分隔" />
        </a-form-item>
        <a-form-item label="摘要">
          <a-textarea v-model:value="formData.abstractText" placeholder="请输入摘要" :rows="3" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="基金项目">
              <a-input v-model:value="formData.fundProject" placeholder="请输入基金项目" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="基金编号">
              <a-input v-model:value="formData.fundNo" placeholder="请输入基金编号" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="文件路径">
          <a-input v-model:value="formData.filePath" placeholder="请输入文件路径" />
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea v-model:value="formData.remark" placeholder="请输入备注" :rows="2" />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 分配编辑弹窗 -->
    <a-modal v-model:open="assignModalOpen" title="分配编辑" @ok="onAssignSubmit" destroy-on-close>
      <a-form layout="vertical">
        <a-form-item label="编辑ID">
          <a-input-number v-model:value="assignForm.editorId" placeholder="请输入编辑ID" style="width:100%" />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 送审弹窗 -->
    <a-modal v-model:open="reviewModalOpen" title="送审" @ok="onReviewSubmit" destroy-on-close>
      <a-form layout="vertical">
        <a-form-item label="审稿人ID" required>
          <a-input-number v-model:value="reviewForm.reviewerId" placeholder="请输入审稿人ID" style="width:100%" />
        </a-form-item>
        <a-form-item label="审稿轮次">
          <a-input-number v-model:value="reviewForm.reviewRound" :min="1" placeholder="请输入审稿轮次" style="width:100%" />
        </a-form-item>
        <a-form-item label="截止日期">
          <a-date-picker v-model:value="reviewForm.deadlineTime" value-format="YYYY-MM-DD" style="width:100%" placeholder="请选择截止日期" />
        </a-form-item>
        <a-form-item label="邀请信息">
          <a-textarea v-model:value="reviewForm.inviteMessage" placeholder="请输入邀请信息" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 退修弹窗 -->
    <a-modal v-model:open="revisionModalOpen" title="退修" @ok="onRevisionSubmit" destroy-on-close>
      <a-form layout="vertical">
        <a-form-item label="退修意见" required>
          <a-textarea v-model:value="revisionForm.revisionContent" placeholder="请输入退修意见" :rows="5" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>