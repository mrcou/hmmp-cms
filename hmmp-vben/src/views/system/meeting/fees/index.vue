<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import dayjs from 'dayjs';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  feesType: undefined as string | undefined,
  payStatus: undefined as string | undefined,
});
const dataSource = ref<meetingApi.MeetingApi.Fees[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增财务记录');
const formData = reactive<meetingApi.MeetingApi.Fees>({
  meetingId: undefined,
  userId: undefined,
  userName: '',
  feesType: '',
  amount: undefined,
  payStatus: '0',
  payTime: '',
  payMethod: '',
});
const isEdit = ref(false);

const feesTypeOptions = [
  { label: '注册费', value: '注册费' },
  { label: '住宿费', value: '住宿费' },
  { label: '旅游费', value: '旅游费' },
  { label: '其他', value: '其他' },
];

const payStatusOptions = [
  { label: '未支付', value: '0' },
  { label: '已支付', value: '1' },
  { label: '已退款', value: '2' },
];

const payMethodOptions = [
  { label: '微信支付', value: '微信支付' },
  { label: '支付宝', value: '支付宝' },
  { label: '银行转账', value: '银行转账' },
  { label: '现金', value: '现金' },
];

const columns = [
  { title: '费用类型', dataIndex: 'feesType', key: 'feesType', width: 120 },
  { title: '用户', dataIndex: 'userName', key: 'userName', width: 120 },
  { title: '金额', dataIndex: 'amount', key: 'amount', width: 120 },
  { title: '支付状态', dataIndex: 'payStatus', key: 'payStatus', width: 100 },
  { title: '支付方式', dataIndex: 'payMethod', key: 'payMethod', width: 120 },
  { title: '支付时间', dataIndex: 'payTime', key: 'payTime', width: 160 },
  { title: '操作', key: 'action', width: 180, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getFeesList({
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
  queryParams.feesType = undefined;
  queryParams.payStatus = undefined;
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
  modalTitle.value = '新增财务记录';
  Object.assign(formData, {
    meetingId: undefined,
    userId: undefined,
    userName: '',
    feesType: '',
    amount: undefined,
    payStatus: '0',
    payTime: '',
    payMethod: '',
  });
  modalVisible.value = true;
}

function openEditModal(record: meetingApi.MeetingApi.Fees) {
  isEdit.value = true;
  modalTitle.value = '编辑财务记录';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.feesType) {
    message.warning('请选择费用类型');
    return;
  }
  try {
    const data = {
      ...formData,
      payTime: formData.payTime ? dayjs(formData.payTime).format('YYYY-MM-DD HH:mm:ss') : undefined,
    };
    if (isEdit.value) {
      await meetingApi.updateFees(data as meetingApi.MeetingApi.Fees);
      message.success('修改成功');
    } else {
      await meetingApi.createFees(data as meetingApi.MeetingApi.Fees);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="会议财务管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="费用类型">
          <a-select v-model:value="queryParams.feesType" placeholder="请选择" allow-clear style="width: 130px">
            <a-select-option v-for="opt in feesTypeOptions" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="支付状态">
          <a-select v-model:value="queryParams.payStatus" placeholder="请选择" allow-clear style="width: 120px">
            <a-select-option v-for="opt in payStatusOptions" :key="opt.value" :value="opt.value">
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
        <a-button type="primary" @click="openCreateModal">新增财务记录</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="feesId"
        :scroll="{ x: 1000 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'amount'">
            <span>¥{{ record.amount?.toFixed(2) }}</span>
          </template>
          <template v-if="column.key === 'payStatus'">
            <a-tag :color="record.payStatus === '1' ? 'green' : record.payStatus === '2' ? 'red' : 'orange'">
              {{ record.payStatus === '0' ? '未支付' : record.payStatus === '1' ? '已支付' : '已退款' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
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
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="费用类型" required>
              <a-select v-model:value="formData.feesType" placeholder="请选择费用类型">
                <a-select-option v-for="opt in feesTypeOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="金额">
              <a-input-number v-model:value="formData.amount" placeholder="金额" style="width: 100%" :min="0" :precision="2" />
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
        <a-form-item label="用户名称">
          <a-input v-model:value="formData.userName" placeholder="请输入用户名称" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="支付状态">
              <a-select v-model:value="formData.payStatus">
                <a-select-option v-for="opt in payStatusOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="支付方式">
              <a-select v-model:value="formData.payMethod" placeholder="请选择支付方式">
                <a-select-option v-for="opt in payMethodOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="支付时间">
          <a-date-picker v-model:value="formData.payTime" show-time style="width: 100%" placeholder="支付时间" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>