<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  hotelName: '',
  status: undefined as string | undefined,
});
const dataSource = ref<meetingApi.MeetingApi.Hotel[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增酒店');
const formData = reactive<meetingApi.MeetingApi.Hotel>({
  meetingId: undefined,
  hotelName: '',
  address: '',
  phone: '',
  starLevel: undefined,
  description: '',
  status: '0',
});
const isEdit = ref(false);

const statusOptions = [
  { label: '启用', value: '0' },
  { label: '停用', value: '1' },
];

const starLevelOptions = [
  { label: '一星', value: 1 },
  { label: '二星', value: 2 },
  { label: '三星', value: 3 },
  { label: '四星', value: 4 },
  { label: '五星', value: 5 },
];

const columns = [
  { title: '酒店名称', dataIndex: 'hotelName', key: 'hotelName', width: 200, ellipsis: true },
  { title: '地址', dataIndex: 'address', key: 'address', width: 250, ellipsis: true },
  { title: '电话', dataIndex: 'phone', key: 'phone', width: 140 },
  { title: '星级', dataIndex: 'starLevel', key: 'starLevel', width: 100 },
  { title: '描述', dataIndex: 'description', key: 'description', width: 200, ellipsis: true },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getHotelList({
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
  queryParams.hotelName = '';
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
  modalTitle.value = '新增酒店';
  Object.assign(formData, {
    meetingId: undefined,
    hotelName: '',
    address: '',
    phone: '',
    starLevel: undefined,
    description: '',
    status: '0',
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.Hotel) {
  isEdit.value = true;
  modalTitle.value = '编辑酒店';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.hotelName) {
    message.warning('请输入酒店名称');
    return;
  }
  try {
    if (isEdit.value) {
      await meetingApi.updateHotel(formData);
      message.success('修改成功');
    } else {
      await meetingApi.createHotel(formData);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: meetingApi.MeetingApi.Hotel) {
  await meetingApi.deleteHotel([record.hotelId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="酒店管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="酒店名称">
          <a-input v-model:value="queryParams.hotelName" placeholder="请输入酒店名称" allow-clear style="width: 200px" />
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
        <a-button type="primary" @click="openCreateModal">新增酒店</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="hotelId"
        :scroll="{ x: 1200 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'starLevel'">
            <a-tag color="gold">{{ '⭐'.repeat(record.starLevel ?? 0) }}</a-tag>
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === '0' ? 'green' : 'red'">
              {{ record.status === '0' ? '启用' : '停用' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该酒店吗？"
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
      :width="600"
    >
      <a-form layout="vertical">
        <a-form-item label="酒店名称" required>
          <a-input v-model:value="formData.hotelName" placeholder="请输入酒店名称" />
        </a-form-item>
        <a-form-item label="所属会议">
          <a-input-number v-model:value="formData.meetingId" placeholder="会议ID" style="width: 100%" :min="0" />
        </a-form-item>
        <a-form-item label="地址">
          <a-input v-model:value="formData.address" placeholder="请输入地址" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="电话">
              <a-input v-model:value="formData.phone" placeholder="请输入电话" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="星级">
              <a-select v-model:value="formData.starLevel" placeholder="请选择星级">
                <a-select-option v-for="opt in starLevelOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
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
        <a-form-item label="描述">
          <a-textarea v-model:value="formData.description" placeholder="请输入描述" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>