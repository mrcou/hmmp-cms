<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import dayjs from 'dayjs';
import * as adApi from '#/api/biz/ad';

const loading = ref(false);
const queryParams = reactive({
  paymentNo: '',
  bargainId: undefined as number | undefined,
  paymentStatus: undefined as string | undefined,
});
const dataSource = ref<adApi.AdApi.Payment[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增支付');
const formData = reactive<adApi.AdApi.Payment>({
  bargainId: undefined,
  adId: undefined,
  paymentAmount: undefined,
  paymentMethod: '',
  paymentStatus: '0',
  paymentTime: '',
  receiptFile: '',
  remark: '',
});
const isEdit = ref(false);

const paymentStatusOptions = [
  { label: '未支付', value: '0' },
  { label: '已支付', value: '1' },
  { label: '已退款', value: '2' },
];

const paymentMethodOptions = [
  { label: '银行转账', value: 'bank' },
  { label: '支付宝', value: 'alipay' },
  { label: '微信支付', value: 'wechat' },
  { label: '现金', value: 'cash' },
];

const columns = [
  { title: '支付编号', dataIndex: 'paymentId', key: 'paymentId', width: 100 },
  { title: '支付单号', dataIndex: 'paymentNo', key: 'paymentNo', width: 140 },
  { title: '合同ID', dataIndex: 'bargainId', key: 'bargainId', width: 100 },
  { title: '广告ID', dataIndex: 'adId', key: 'adId', width: 100 },
  { title: '支付金额', dataIndex: 'paymentAmount', key: 'paymentAmount', width: 120 },
  { title: '支付方式', dataIndex: 'paymentMethod', key: 'paymentMethod', width: 110 },
  { title: '支付状态', dataIndex: 'paymentStatus', key: 'paymentStatus', width: 100 },
  { title: '支付时间', dataIndex: 'paymentTime', key: 'paymentTime', width: 160 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await adApi.getPaymentList({
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
  queryParams.paymentNo = '';
  queryParams.bargainId = undefined;
  queryParams.paymentStatus = undefined;
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
  modalTitle.value = '新增支付';
  Object.assign(formData, {
    bargainId: undefined,
    adId: undefined,
    paymentAmount: undefined,
    paymentMethod: '',
    paymentStatus: '0',
    paymentTime: '',
    receiptFile: '',
    remark: '',
  });
  modalVisible.value = true;
}

function openEditModal(record: adApi.AdApi.Payment) {
  isEdit.value = true;
  modalTitle.value = '编辑支付';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.paymentAmount) {
    message.warning('请输入支付金额');
    return;
  }
  try {
    const data = {
      ...formData,
      paymentTime: formData.paymentTime ? dayjs(formData.paymentTime).format('YYYY-MM-DD HH:mm:ss') : undefined,
    };
    if (isEdit.value) {
      await adApi.updatePayment(data as adApi.AdApi.Payment);
      message.success('修改成功');
    } else {
      await adApi.createPayment(data as adApi.AdApi.Payment);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: adApi.AdApi.Payment) {
  await adApi.deletePayment([record.paymentId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="支付管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="支付单号">
          <a-input v-model:value="queryParams.paymentNo" placeholder="请输入支付单号" allow-clear />
        </a-form-item>
        <a-form-item label="合同ID">
          <a-input-number v-model:value="queryParams.bargainId" placeholder="合同ID" style="width: 120px" />
        </a-form-item>
        <a-form-item label="支付状态">
          <a-select v-model:value="queryParams.paymentStatus" placeholder="支付状态" allow-clear style="width: 130px">
            <a-select-option v-for="opt in paymentStatusOptions" :key="opt.value" :value="opt.value">
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
        <a-button type="primary" @click="openCreateModal">新增支付</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="paymentId"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'paymentMethod'">
            <a-tag v-if="record.paymentMethod === 'bank'" color="blue">银行转账</a-tag>
            <a-tag v-else-if="record.paymentMethod === 'alipay'" color="cyan">支付宝</a-tag>
            <a-tag v-else-if="record.paymentMethod === 'wechat'" color="green">微信支付</a-tag>
            <a-tag v-else-if="record.paymentMethod === 'cash'" color="orange">现金</a-tag>
            <span v-else>{{ record.paymentMethod }}</span>
          </template>
          <template v-if="column.key === 'paymentStatus'">
            <a-tag v-if="record.paymentStatus === '0'" color="orange">未支付</a-tag>
            <a-tag v-else-if="record.paymentStatus === '1'" color="green">已支付</a-tag>
            <a-tag v-else-if="record.paymentStatus === '2'" color="red">已退款</a-tag>
            <span v-else>{{ record.paymentStatus }}</span>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该支付记录吗？"
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
      width="600px"
    >
      <a-form layout="vertical">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="合同ID">
              <a-input-number v-model:value="formData.bargainId" placeholder="合同ID" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="广告ID">
              <a-input-number v-model:value="formData.adId" placeholder="广告ID" style="width: 100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="支付金额" required>
          <a-input-number v-model:value="formData.paymentAmount" placeholder="请输入支付金额" style="width: 100%" :min="0" :precision="2" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="支付方式">
              <a-select v-model:value="formData.paymentMethod" placeholder="请选择支付方式" style="width: 100%">
                <a-select-option v-for="opt in paymentMethodOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="支付状态">
              <a-select v-model:value="formData.paymentStatus" style="width: 100%">
                <a-select-option v-for="opt in paymentStatusOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="支付时间">
          <a-date-picker v-model:value="formData.paymentTime" show-time style="width: 100%" placeholder="请选择支付时间" />
        </a-form-item>
        <a-form-item label="回执文件">
          <a-input v-model:value="formData.receiptFile" placeholder="回执文件路径" />
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea v-model:value="formData.remark" placeholder="请输入备注" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>