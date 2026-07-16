<script lang="ts" setup>
import type { EditorApi } from '#/api/biz/editor';

import { onMounted, reactive, ref } from 'vue';

import { message } from 'antdv-next';

import * as editorApi from '#/api/biz/editor';

// ---------- 查询参数 ----------
const searchForm = reactive({
  realName: '',
  affiliation: '',
  expertiseFields: '',
  status: undefined as string | undefined,
});

// ---------- 表格 ----------
const tableData = ref<EditorApi.Reviewer[]>([]);
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
  { title: '审稿人ID', dataIndex: 'reviewerId', key: 'reviewerId', width: 90 },
  { title: '姓名', dataIndex: 'realName', key: 'realName', width: 100 },
  { title: '单位', dataIndex: 'affiliation', key: 'affiliation', ellipsis: true, width: 180 },
  { title: '部门', dataIndex: 'department', key: 'department', width: 140 },
  { title: '职称', dataIndex: 'title', key: 'title', width: 100 },
  { title: '研究方向', dataIndex: 'researchDirection', key: 'researchDirection', ellipsis: true, width: 150 },
  { title: '擅长领域', dataIndex: 'expertiseFields', key: 'expertiseFields', ellipsis: true, width: 150 },
  { title: '邮箱', dataIndex: 'email', key: 'email', width: 180 },
  { title: '电话', dataIndex: 'phone', key: 'phone', width: 130 },
  { title: '审稿次数', dataIndex: 'reviewCount', key: 'reviewCount', width: 90 },
  { title: '接受率', dataIndex: 'acceptRate', key: 'acceptRate', width: 80 },
  { title: '平均审稿天数', dataIndex: 'avgReviewDays', key: 'avgReviewDays', width: 110 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' },
];

// ---------- 弹窗 ----------
const formModalOpen = ref(false);
const formModalTitle = ref('新增审稿人');
const isEdit = ref(false);
const formLoading = ref(false);
const formData = reactive<EditorApi.Reviewer>({
  realName: '',
  gender: undefined,
  birthday: '',
  affiliation: '',
  department: '',
  title: '',
  academicDegree: '',
  researchDirection: '',
  expertiseFields: '',
  email: '',
  phone: '',
  mobile: '',
  address: '',
  zipCode: '',
  idCard: '',
  workNo: '',
  status: '0',
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
    const res = await editorApi.getReviewerList(params);
    const data = res.data ?? res;
    tableData.value = data.rows ?? data.list ?? [];
    pagination.total = data.total ?? 0;
  } catch {
    message.error('加载审稿人列表失败');
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
  searchForm.expertiseFields = '';
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
  formModalTitle.value = '新增审稿人';
  Object.assign(formData, {
    reviewerId: undefined,
    userId: undefined,
    realName: '',
    gender: undefined,
    birthday: '',
    affiliation: '',
    department: '',
    title: '',
    academicDegree: '',
    researchDirection: '',
    expertiseFields: '',
    email: '',
    phone: '',
    mobile: '',
    address: '',
    zipCode: '',
    idCard: '',
    workNo: '',
    status: '0',
  });
  formModalOpen.value = true;
}

function onEdit(row: EditorApi.Reviewer) {
  isEdit.value = true;
  formModalTitle.value = '编辑审稿人';
  Object.assign(formData, row);
  formModalOpen.value = true;
}

async function onDelete(row: EditorApi.Reviewer) {
  try {
    await editorApi.deleteReviewer([row.reviewerId!]);
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
      await editorApi.updateReviewer({ ...formData });
      message.success('修改成功');
    } else {
      await editorApi.createReviewer({ ...formData });
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
        <a-form-item label="擅长领域">
          <a-input v-model:value="searchForm.expertiseFields" placeholder="请输入" allow-clear style="width:180px" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="searchForm.status" placeholder="请选择" allow-clear style="width:120px">
            <a-select-option value="0">正常</a-select-option>
            <a-select-option value="1">停用</a-select-option>
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
        <a-button type="primary" @click="onCreate">新增审稿人</a-button>
      </div>
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        :scroll="{ x: 1700 }"
        row-key="reviewerId"
        @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'acceptRate'">
            {{ record.acceptRate != null ? record.acceptRate + '%' : '-' }}
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button size="small" @click="onEdit(record)">编辑</a-button>
              <a-popconfirm title="确认删除该审稿人？" @confirm="onDelete(record)">
                <a-button size="small" danger>删除</a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <!-- 新增/编辑弹窗 -->
    <a-modal v-model:open="formModalOpen" :title="formModalTitle" :confirm-loading="formLoading" @ok="onFormSubmit" width="720px" destroy-on-close>
      <a-form layout="vertical">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="姓名" required>
              <a-input v-model:value="formData.realName" placeholder="请输入姓名" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="性别">
              <a-select v-model:value="formData.gender" placeholder="请选择性别">
                <a-select-option value="0">男</a-select-option>
                <a-select-option value="1">女</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="出生日期">
              <a-date-picker v-model:value="formData.birthday" value-format="YYYY-MM-DD" style="width:100%" placeholder="请选择出生日期" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="学历">
              <a-input v-model:value="formData.academicDegree" placeholder="请输入学历" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="单位">
              <a-input v-model:value="formData.affiliation" placeholder="请输入单位" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="部门">
              <a-input v-model:value="formData.department" placeholder="请输入部门" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="职称">
              <a-input v-model:value="formData.title" placeholder="请输入职称" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="状态">
              <a-select v-model:value="formData.status" placeholder="请选择状态">
                <a-select-option value="0">正常</a-select-option>
                <a-select-option value="1">停用</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="研究方向">
          <a-input v-model:value="formData.researchDirection" placeholder="请输入研究方向" />
        </a-form-item>
        <a-form-item label="擅长领域">
          <a-textarea v-model:value="formData.expertiseFields" placeholder="请输入擅长领域，多个用逗号分隔" :rows="2" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="邮箱">
              <a-input v-model:value="formData.email" placeholder="请输入邮箱" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="电话">
              <a-input v-model:value="formData.phone" placeholder="请输入电话" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="手机">
              <a-input v-model:value="formData.mobile" placeholder="请输入手机号" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="身份证号">
              <a-input v-model:value="formData.idCard" placeholder="请输入身份证号" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="地址">
              <a-input v-model:value="formData.address" placeholder="请输入地址" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="邮编">
              <a-input v-model:value="formData.zipCode" placeholder="请输入邮编" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="工号">
          <a-input v-model:value="formData.workNo" placeholder="请输入工号" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>