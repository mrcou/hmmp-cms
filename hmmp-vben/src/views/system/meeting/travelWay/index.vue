<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  wayName: '',
});
const dataSource = ref<meetingApi.MeetingApi.TravelWay[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增旅游线路');
const formData = reactive<meetingApi.MeetingApi.TravelWay>({
  meetingId: undefined,
  wayName: '',
  description: '',
  price: undefined,
  maxPeople: undefined,
  availablePeople: undefined,
});
const isEdit = ref(false);

const columns = [
  { title: '线路名称', dataIndex: 'wayName', key: 'wayName', width: 180 },
  { title: '描述', dataIndex: 'description', key: 'description', width: 250, ellipsis: true },
  { title: '价格', dataIndex: 'price', key: 'price', width: 120 },
  { title: '最大人数', dataIndex: 'maxPeople', key: 'maxPeople', width: 120 },
  { title: '可用人数', dataIndex: 'availablePeople', key: 'availablePeople', width: 120 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getTravelWayList({
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
  queryParams.wayName = '';
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
  modalTitle.value = '新增旅游线路';
  Object.assign(formData, {
    meetingId: undefined,
    wayName: '',
    description: '',
    price: undefined,
    maxPeople: undefined,
    availablePeople: undefined,
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.TravelWay) {
  isEdit.value = true;
  modalTitle.value = '编辑旅游线路';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.wayName) {
    message.warning('请输入线路名称');
    return;
  }
  try {
    if (isEdit.value) {
      await meetingApi.updateTravelWay(formData);
      message.success('修改成功');
    } else {
      await meetingApi.createTravelWay(formData);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: meetingApi.MeetingApi.TravelWay) {
  await meetingApi.deleteTravelWay([record.wayId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="旅游线路管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="线路名称">
          <a-input v-model:value="queryParams.wayName" placeholder="请输入线路名称" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-space class="mb-4">
        <a-button type="primary" @click="openCreateModal">新增线路</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="wayId"
        :scroll="{ x: 900 }"
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
                title="确认删除该线路吗？"
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
        <a-form-item label="线路名称" required>
          <a-input v-model:value="formData.wayName" placeholder="请输入线路名称" />
        </a-form-item>
        <a-form-item label="所属会议">
          <a-input-number v-model:value="formData.meetingId" placeholder="会议ID" style="width: 100%" :min="0" />
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="formData.description" placeholder="请输入描述" :rows="3" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="价格">
              <a-input-number v-model:value="formData.price" placeholder="价格" style="width: 100%" :min="0" :precision="2" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="最大人数">
              <a-input-number v-model:value="formData.maxPeople" placeholder="最大人数" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="可用人数">
          <a-input-number v-model:value="formData.availablePeople" placeholder="可用人数" style="width: 100%" :min="0" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>