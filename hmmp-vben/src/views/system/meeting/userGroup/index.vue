<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  groupName: '',
});
const dataSource = ref<meetingApi.MeetingApi.UserGroup[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增用户类型');
const formData = reactive<meetingApi.MeetingApi.UserGroup>({
  meetingId: undefined,
  groupName: '',
  description: '',
});
const isEdit = ref(false);

const columns = [
  { title: '类型名称', dataIndex: 'groupName', key: 'groupName', width: 200 },
  { title: '所属会议ID', dataIndex: 'meetingId', key: 'meetingId', width: 120 },
  { title: '描述', dataIndex: 'description', key: 'description', width: 300, ellipsis: true },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getUserGroupList({
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
  queryParams.groupName = '';
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
  modalTitle.value = '新增用户类型';
  Object.assign(formData, {
    meetingId: undefined,
    groupName: '',
    description: '',
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.UserGroup) {
  isEdit.value = true;
  modalTitle.value = '编辑用户类型';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.groupName) {
    message.warning('请输入类型名称');
    return;
  }
  try {
    if (isEdit.value) {
      await meetingApi.updateUserGroup(formData);
      message.success('修改成功');
    } else {
      await meetingApi.createUserGroup(formData);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: meetingApi.MeetingApi.UserGroup) {
  await meetingApi.deleteUserGroup([record.groupId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="用户类型管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="类型名称">
          <a-input v-model:value="queryParams.groupName" placeholder="请输入类型名称" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-space class="mb-4">
        <a-button type="primary" @click="openCreateModal">新增用户类型</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="groupId"
        :scroll="{ x: 800 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该用户类型吗？"
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
      :width="500"
    >
      <a-form layout="vertical">
        <a-form-item label="类型名称" required>
          <a-input v-model:value="formData.groupName" placeholder="请输入类型名称" />
        </a-form-item>
        <a-form-item label="所属会议">
          <a-input-number v-model:value="formData.meetingId" placeholder="会议ID" style="width: 100%" :min="0" />
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="formData.description" placeholder="请输入描述" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>