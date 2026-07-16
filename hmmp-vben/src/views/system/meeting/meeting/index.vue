<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import dayjs from 'dayjs';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  meetingName: '',
  meetingType: undefined as string | undefined,
  status: undefined as string | undefined,
});
const dataSource = ref<meetingApi.MeetingApi.Meeting[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增会议');
const formData = reactive<meetingApi.MeetingApi.Meeting>({
  meetingName: '',
  meetingCode: '',
  meetingType: '',
  startDate: '',
  endDate: '',
  location: '',
  host: '',
  contactPerson: '',
  contactPhone: '',
  description: '',
  status: '0',
  maxParticipants: undefined,
  actualParticipants: undefined,
});
const isEdit = ref(false);

const meetingTypeOptions = [
  { label: '学术会议', value: '学术会议' },
  { label: '行业会议', value: '行业会议' },
  { label: '研讨会', value: '研讨会' },
  { label: '培训', value: '培训' },
];

const statusOptions = [
  { label: '筹备中', value: '0' },
  { label: '进行中', value: '1' },
  { label: '已结束', value: '2' },
  { label: '已取消', value: '3' },
];

const columns = [
  { title: '会议名称', dataIndex: 'meetingName', key: 'meetingName', width: 200, ellipsis: true },
  { title: '会议编号', dataIndex: 'meetingCode', key: 'meetingCode', width: 120 },
  { title: '会议类型', dataIndex: 'meetingType', key: 'meetingType', width: 120 },
  { title: '开始日期', dataIndex: 'startDate', key: 'startDate', width: 120 },
  { title: '结束日期', dataIndex: 'endDate', key: 'endDate', width: 120 },
  { title: '地点', dataIndex: 'location', key: 'location', width: 150, ellipsis: true },
  { title: '主办方', dataIndex: 'host', key: 'host', width: 150, ellipsis: true },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '最大参会人数', dataIndex: 'maxParticipants', key: 'maxParticipants', width: 120 },
  { title: '实际参会人数', dataIndex: 'actualParticipants', key: 'actualParticipants', width: 120 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getMeetingList({
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
  queryParams.meetingName = '';
  queryParams.meetingType = undefined;
  queryParams.status = undefined;
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
  modalTitle.value = '新增会议';
  Object.assign(formData, {
    meetingName: '',
    meetingCode: '',
    meetingType: '',
    startDate: '',
    endDate: '',
    location: '',
    host: '',
    contactPerson: '',
    contactPhone: '',
    description: '',
    status: '0',
    maxParticipants: undefined,
    actualParticipants: undefined,
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.Meeting) {
  isEdit.value = true;
  modalTitle.value = '编辑会议';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.meetingName) {
    message.warning('请输入会议名称');
    return;
  }
  try {
    const data = {
      ...formData,
      startDate: formData.startDate ? dayjs(formData.startDate).format('YYYY-MM-DD') : undefined,
      endDate: formData.endDate ? dayjs(formData.endDate).format('YYYY-MM-DD') : undefined,
    };
    if (isEdit.value) {
      await meetingApi.updateMeeting(data as meetingApi.MeetingApi.Meeting);
      message.success('修改成功');
    } else {
      await meetingApi.createMeeting(data as meetingApi.MeetingApi.Meeting);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: meetingApi.MeetingApi.Meeting) {
  await meetingApi.deleteMeeting([record.meetingId!]);
  message.success('删除成功');
  fetchData();
}

function getStatusTag(status: string | undefined) {
  const map: Record<string, { color: string; label: string }> = {
    '0': { color: 'blue', label: '筹备中' },
    '1': { color: 'green', label: '进行中' },
    '2': { color: 'default', label: '已结束' },
    '3': { color: 'red', label: '已取消' },
  };
  return map[status ?? ''] ?? { color: 'default', label: status ?? '-' };
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="会议管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="会议名称">
          <a-input v-model:value="queryParams.meetingName" placeholder="请输入会议名称" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item label="会议类型">
          <a-select v-model:value="queryParams.meetingType" placeholder="请选择" allow-clear style="width: 130px">
            <a-select-option v-for="opt in meetingTypeOptions" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="queryParams.status" placeholder="请选择" allow-clear style="width: 120px">
            <a-select-option v-for="opt in statusOptions" :key="opt.value" :value="opt.value">
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
        <a-button type="primary" @click="openCreateModal">新增会议</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="meetingId"
        :scroll="{ x: 1400 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <a-tag :color="getStatusTag(record.status).color">
              {{ getStatusTag(record.status).label }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该会议吗？"
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
      :width="700"
    >
      <a-form layout="vertical">
        <a-form-item label="会议名称" required>
          <a-input v-model:value="formData.meetingName" placeholder="请输入会议名称" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="会议编号">
              <a-input v-model:value="formData.meetingCode" placeholder="请输入会议编号" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="会议类型">
              <a-select v-model:value="formData.meetingType" placeholder="请选择会议类型">
                <a-select-option v-for="opt in meetingTypeOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="开始日期">
              <a-date-picker v-model:value="formData.startDate" style="width: 100%" placeholder="开始日期" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="结束日期">
              <a-date-picker v-model:value="formData.endDate" style="width: 100%" placeholder="结束日期" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="地点">
          <a-input v-model:value="formData.location" placeholder="请输入会议地点" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="主办方">
              <a-input v-model:value="formData.host" placeholder="请输入主办方" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="状态">
              <a-select v-model:value="formData.status">
                <a-select-option v-for="opt in statusOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="联系人">
              <a-input v-model:value="formData.contactPerson" placeholder="请输入联系人" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="联系电话">
              <a-input v-model:value="formData.contactPhone" placeholder="请输入联系电话" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="最大参会人数">
              <a-input-number v-model:value="formData.maxParticipants" placeholder="最大参会人数" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="实际参会人数">
              <a-input-number v-model:value="formData.actualParticipants" placeholder="实际参会人数" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="描述">
          <a-textarea v-model:value="formData.description" placeholder="请输入会议描述" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>