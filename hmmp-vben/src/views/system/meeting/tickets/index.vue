<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import dayjs from 'dayjs';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  ticketType: undefined as string | undefined,
  ticketNo: '',
});
const dataSource = ref<meetingApi.MeetingApi.Tickets[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增票务');
const formData = reactive<meetingApi.MeetingApi.Tickets>({
  meetingId: undefined,
  userId: undefined,
  ticketType: '',
  ticketNo: '',
  startStation: '',
  endStation: '',
  departureTime: '',
  arrivalTime: '',
  status: '0',
});
const isEdit = ref(false);

const ticketTypeOptions = [
  { label: '机票', value: '机票' },
  { label: '火车票', value: '火车票' },
  { label: '汽车票', value: '汽车票' },
];

const statusOptions = [
  { label: '待使用', value: '0' },
  { label: '已使用', value: '1' },
  { label: '已退票', value: '2' },
];

const columns = [
  { title: '票务类型', dataIndex: 'ticketType', key: 'ticketType', width: 120 },
  { title: '票号', dataIndex: 'ticketNo', key: 'ticketNo', width: 150 },
  { title: '出发站', dataIndex: 'startStation', key: 'startStation', width: 150, ellipsis: true },
  { title: '到达站', dataIndex: 'endStation', key: 'endStation', width: 150, ellipsis: true },
  { title: '出发时间', dataIndex: 'departureTime', key: 'departureTime', width: 160 },
  { title: '到达时间', dataIndex: 'arrivalTime', key: 'arrivalTime', width: 160 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getTicketsList({
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
  queryParams.ticketType = undefined;
  queryParams.ticketNo = '';
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
  modalTitle.value = '新增票务';
  Object.assign(formData, {
    meetingId: undefined,
    userId: undefined,
    ticketType: '',
    ticketNo: '',
    startStation: '',
    endStation: '',
    departureTime: '',
    arrivalTime: '',
    status: '0',
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.Tickets) {
  isEdit.value = true;
  modalTitle.value = '编辑票务';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.ticketNo) {
    message.warning('请输入票号');
    return;
  }
  try {
    const data = {
      ...formData,
      departureTime: formData.departureTime ? dayjs(formData.departureTime).format('YYYY-MM-DD HH:mm:ss') : undefined,
      arrivalTime: formData.arrivalTime ? dayjs(formData.arrivalTime).format('YYYY-MM-DD HH:mm:ss') : undefined,
    };
    if (isEdit.value) {
      await meetingApi.updateTickets(data as meetingApi.MeetingApi.Tickets);
      message.success('修改成功');
    } else {
      await meetingApi.createTickets(data as meetingApi.MeetingApi.Tickets);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: meetingApi.MeetingApi.Tickets) {
  await meetingApi.deleteTickets([record.ticketId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="票务管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="票务类型">
          <a-select v-model:value="queryParams.ticketType" placeholder="请选择" allow-clear style="width: 130px">
            <a-select-option v-for="opt in ticketTypeOptions" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="票号">
          <a-input v-model:value="queryParams.ticketNo" placeholder="请输入票号" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-space class="mb-4">
        <a-button type="primary" @click="openCreateModal">新增票务</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="ticketId"
        :scroll="{ x: 1200 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'ticketType'">
            <a-tag :color="record.ticketType === '机票' ? 'blue' : record.ticketType === '火车票' ? 'green' : 'orange'">
              {{ record.ticketType }}
            </a-tag>
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === '0' ? 'blue' : record.status === '1' ? 'green' : 'red'">
              {{ record.status === '0' ? '待使用' : record.status === '1' ? '已使用' : '已退票' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该票务吗？"
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
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="票务类型">
              <a-select v-model:value="formData.ticketType" placeholder="请选择票务类型">
                <a-select-option v-for="opt in ticketTypeOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="票号" required>
              <a-input v-model:value="formData.ticketNo" placeholder="请输入票号" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="所属会议">
              <a-input-number v-model:value="formData.meetingId" placeholder="会议ID" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="用户ID">
              <a-input-number v-model:value="formData.userId" placeholder="用户ID" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="出发站">
              <a-input v-model:value="formData.startStation" placeholder="请输入出发站" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="到达站">
              <a-input v-model:value="formData.endStation" placeholder="请输入到达站" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="出发时间">
              <a-date-picker v-model:value="formData.departureTime" show-time style="width: 100%" placeholder="出发时间" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="到达时间">
              <a-date-picker v-model:value="formData.arrivalTime" show-time style="width: 100%" placeholder="到达时间" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="状态">
          <a-select v-model:value="formData.status">
            <a-select-option v-for="opt in statusOptions" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>