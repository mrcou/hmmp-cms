<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  roomType: '',
});
const dataSource = ref<meetingApi.MeetingApi.HotelRoom[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增房型');
const formData = reactive<meetingApi.MeetingApi.HotelRoom>({
  hotelId: null as unknown as number,
  roomType: '',
  price: undefined,
  maxPeople: undefined,
  totalQty: undefined,
  availableQty: undefined,
  description: '',
});
const isEdit = ref(false);

const columns = [
  { title: '房型名称', dataIndex: 'roomType', key: 'roomType', width: 140 },
  { title: '所属酒店ID', dataIndex: 'hotelId', key: 'hotelId', width: 120 },
  { title: '价格', dataIndex: 'price', key: 'price', width: 120 },
  { title: '最大入住数', dataIndex: 'maxPeople', key: 'maxPeople', width: 120 },
  { title: '总数', dataIndex: 'totalQty', key: 'totalQty', width: 100 },
  { title: '可用数', dataIndex: 'availableQty', key: 'availableQty', width: 100 },
  { title: '描述', dataIndex: 'description', key: 'description', width: 200, ellipsis: true },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getHotelRoomList({
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
  queryParams.roomType = '';
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
  modalTitle.value = '新增房型';
  Object.assign(formData, {
    hotelId: undefined,
    roomType: '',
    price: undefined,
    maxPeople: undefined,
    totalQty: undefined,
    availableQty: undefined,
    description: '',
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.HotelRoom) {
  isEdit.value = true;
  modalTitle.value = '编辑房型';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.roomType) {
    message.warning('请输入房型名称');
    return;
  }
  try {
    if (isEdit.value) {
      await meetingApi.updateHotelRoom(formData);
      message.success('修改成功');
    } else {
      await meetingApi.createHotelRoom(formData);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: meetingApi.MeetingApi.HotelRoom) {
  await meetingApi.deleteHotelRoom([record.roomId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="酒店房型管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="房型名称">
          <a-input v-model:value="queryParams.roomType" placeholder="请输入房型名称" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-space class="mb-4">
        <a-button type="primary" @click="openCreateModal">新增房型</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="roomId"
        :scroll="{ x: 1100 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'price'">
            <span>¥{{ record.price?.toFixed(2) }}</span>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该房型吗？"
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
        <a-form-item label="房型名称" required>
          <a-input v-model:value="formData.roomType" placeholder="请输入房型名称" />
        </a-form-item>
        <a-form-item label="所属酒店">
          <a-input-number v-model:value="formData.hotelId" placeholder="酒店ID" style="width: 100%" :min="0" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="价格">
              <a-input-number v-model:value="formData.price" placeholder="价格" style="width: 100%" :min="0" :precision="2" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="最大入住数">
              <a-input-number v-model:value="formData.maxPeople" placeholder="最大入住数" style="width: 100%" :min="1" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="总数">
              <a-input-number v-model:value="formData.totalQty" placeholder="总数" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="可用数">
              <a-input-number v-model:value="formData.availableQty" placeholder="可用数" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="描述">
          <a-textarea v-model:value="formData.description" placeholder="请输入描述" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>