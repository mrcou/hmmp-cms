<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import dayjs from 'dayjs';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  priceName: '',
});
const dataSource = ref<meetingApi.MeetingApi.FeesPrice[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增价格');
const formData = reactive<meetingApi.MeetingApi.FeesPrice>({
  meetingId: undefined,
  userGroupId: undefined,
  priceName: '',
  price: undefined,
  startDate: '',
  endDate: '',
});
const isEdit = ref(false);

const columns = [
  { title: '价格名称', dataIndex: 'priceName', key: 'priceName', width: 180 },
  { title: '金额', dataIndex: 'price', key: 'price', width: 120 },
  { title: '所属会议ID', dataIndex: 'meetingId', key: 'meetingId', width: 120 },
  { title: '用户类型ID', dataIndex: 'userGroupId', key: 'userGroupId', width: 120 },
  { title: '有效开始日期', dataIndex: 'startDate', key: 'startDate', width: 140 },
  { title: '有效结束日期', dataIndex: 'endDate', key: 'endDate', width: 140 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getFeesPriceList({
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
  queryParams.priceName = '';
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
  modalTitle.value = '新增价格';
  Object.assign(formData, {
    meetingId: undefined,
    userGroupId: undefined,
    priceName: '',
    price: undefined,
    startDate: '',
    endDate: '',
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.FeesPrice) {
  isEdit.value = true;
  modalTitle.value = '编辑价格';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.priceName) {
    message.warning('请输入价格名称');
    return;
  }
  try {
    const data = {
      ...formData,
      startDate: formData.startDate ? dayjs(formData.startDate).format('YYYY-MM-DD') : undefined,
      endDate: formData.endDate ? dayjs(formData.endDate).format('YYYY-MM-DD') : undefined,
    };
    if (isEdit.value) {
      await meetingApi.updateFeesPrice(data as meetingApi.MeetingApi.FeesPrice);
      message.success('修改成功');
    } else {
      await meetingApi.createFeesPrice(data as meetingApi.MeetingApi.FeesPrice);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: meetingApi.MeetingApi.FeesPrice) {
  await meetingApi.deleteFeesPrice([record.priceId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="注册费价格管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="价格名称">
          <a-input v-model:value="queryParams.priceName" placeholder="请输入价格名称" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-space class="mb-4">
        <a-button type="primary" @click="openCreateModal">新增价格</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="priceId"
        :scroll="{ x: 1000 }"
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
                title="确认删除该价格吗？"
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
        <a-form-item label="价格名称" required>
          <a-input v-model:value="formData.priceName" placeholder="请输入价格名称" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="所属会议">
              <a-input-number v-model:value="formData.meetingId" placeholder="会议ID" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="用户类型">
              <a-input-number v-model:value="formData.userGroupId" placeholder="用户类型ID" style="width: 100%" :min="0" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="金额">
          <a-input-number v-model:value="formData.price" placeholder="金额" style="width: 100%" :min="0" :precision="2" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="有效开始日期">
              <a-date-picker v-model:value="formData.startDate" style="width: 100%" placeholder="开始日期" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="有效结束日期">
              <a-date-picker v-model:value="formData.endDate" style="width: 100%" placeholder="结束日期" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>