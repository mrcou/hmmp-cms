<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  realName: '',
  isPaid: undefined as string | undefined,
});
const dataSource = ref<meetingApi.MeetingApi.MeetingUser[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增参会代表');
const formData = reactive<meetingApi.MeetingApi.MeetingUser>({
  meetingId: undefined,
  realName: '',
  gender: '男',
  affiliation: '',
  title: '',
  email: '',
  phone: '',
  userGroupId: undefined,
  isPaid: '0',
});
const isEdit = ref(false);

const genderOptions = [
  { label: '男', value: '男' },
  { label: '女', value: '女' },
];

const isPaidOptions = [
  { label: '未缴费', value: '0' },
  { label: '已缴费', value: '1' },
];

const columns = [
  { title: '姓名', dataIndex: 'realName', key: 'realName', width: 120 },
  { title: '性别', dataIndex: 'gender', key: 'gender', width: 80 },
  { title: '单位', dataIndex: 'affiliation', key: 'affiliation', width: 200, ellipsis: true },
  { title: '职称', dataIndex: 'title', key: 'title', width: 120 },
  { title: '邮箱', dataIndex: 'email', key: 'email', width: 180, ellipsis: true },
  { title: '电话', dataIndex: 'phone', key: 'phone', width: 140 },
  { title: '用户类型ID', dataIndex: 'userGroupId', key: 'userGroupId', width: 120 },
  { title: '缴费状态', dataIndex: 'isPaid', key: 'isPaid', width: 100 },
  { title: '注册时间', dataIndex: 'registerTime', key: 'registerTime', width: 160 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getMeetingUserList({
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
      ...queryParams,
    });
    dataSource.value = res.rows ?? [];
    pagination.total = res.total ?? 0;
  } finally {
    loading.value = false;
  }
}

function handleSearch() {
  pagination.current = 1;
  fetchData();
}

function handleReset() {
  queryParams.realName = '';
  queryParams.isPaid = undefined;
  pagination.current = 1;
  fetchData();
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchData();
}

function openCreateModal() {
  isEdit.value = false;
  modalTitle.value = '新增参会代表';
  Object.assign(formData, {
    meetingId: undefined,
    realName: '',
    gender: '男',
    affiliation: '',
    title: '',
    email: '',
    phone: '',
    userGroupId: undefined,
    isPaid: '0',
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.MeetingUser) {
  isEdit.value = true;
  modalTitle.value = '编辑参会代表';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.realName) {
    message.warning('请输入姓名');
    return;
  }
  try {
    if (isEdit.value) {
      await meetingApi.updateMeetingUser(formData);
      message.success('修改成功');
    } else {
      await meetingApi.createMeetingUser(formData);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: meetingApi.MeetingApi.MeetingUser) {
  await meetingApi.deleteMeetingUser([record.userId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="参会代表管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="姓名">
          <a-input v-model:value="queryParams.realName" placeholder="请输入姓名" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item label="缴费状态">
          <a-select v-model:value="queryParams.isPaid" placeholder="请选择" allow-clear style="width: 120px">
            <a-select-option v-for="opt in isPaidOptions" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-space class="mb-4">
        <a-button type="primary" @click="openCreateModal">新增参会代表</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="userId"
        :scroll="{ x: 1400 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'isPaid'">
            <a-tag :color="record.isPaid === '1' ? 'green' : 'orange'">
              {{ record.isPaid === '1' ? '已缴费' : '未缴费' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该参会代表吗？"
                ok-text="确认"
                cancel-text="取消"
                @confirm="handleDelete(record)"
              >
                <a-button type="link" danger size="small">删除</a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal
      v-model:open="modalVisible"
      :title="modalTitle"
      ok-text="确认"
      cancel-text="取消"
      @ok="handleSubmit"
      destroy-on-close
      :width="650"
    >
      <a-form layout="vertical">
        <a-form-item label="姓名" required>
          <a-input v-model:value="formData.realName" placeholder="请输入姓名" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="所属会议">
              <a-input-number v-model:value="formData.meetingId" placeholder="会议ID" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="性别">
              <a-select v-model:value="formData.gender">
                <a-select-option v-for="opt in genderOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
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
            <a-form-item label="职称">
              <a-input v-model:value="formData.title" placeholder="请输入职称" />
            </a-form-item>
          </a-col>
        </a-row>
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
            <a-form-item label="用户类型">
              <a-input-number v-model:value="formData.userGroupId" placeholder="用户类型ID" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="缴费状态">
              <a-select v-model:value="formData.isPaid">
                <a-select-option v-for="opt in isPaidOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>