<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import dayjs from 'dayjs';
import * as adApi from '#/api/biz/ad';

const loading = ref(false);
const queryParams = reactive({
  bargainNo: '',
  customerName: '',
  applyStatus: undefined as string | undefined,
});
const dataSource = ref<adApi.AdApi.Bargain[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增合同');
const formData = reactive<adApi.AdApi.Bargain>({
  customerId: undefined as unknown as number,
  bargainName: '',
  totalAmount: undefined,
  signDate: '',
  startDate: '',
  endDate: '',
  bargainContent: '',
  bargainFile: '',
  status: '0',
});
const isEdit = ref(false);

const applyStatusOptions = [
  { label: '待审核', value: '0' },
  { label: '审核通过', value: '1' },
  { label: '审核驳回', value: '2' },
];

const columns = [
  { title: '合同编号', dataIndex: 'bargainId', key: 'bargainId', width: 100 },
  { title: '合同号', dataIndex: 'bargainNo', key: 'bargainNo', width: 140 },
  { title: '客户名称', dataIndex: 'customerName', key: 'customerName', width: 150 },
  { title: '合同名称', dataIndex: 'bargainName', key: 'bargainName', width: 180 },
  { title: '合同金额', dataIndex: 'totalAmount', key: 'totalAmount', width: 120 },
  { title: '签订日期', dataIndex: 'signDate', key: 'signDate', width: 120 },
  { title: '开始日期', dataIndex: 'startDate', key: 'startDate', width: 120 },
  { title: '结束日期', dataIndex: 'endDate', key: 'endDate', width: 120 },
  { title: '审核状态', dataIndex: 'applyStatus', key: 'applyStatus', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await adApi.getBargainList({
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
  queryParams.bargainNo = '';
  queryParams.customerName = '';
  queryParams.applyStatus = undefined;
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
  modalTitle.value = '新增合同';
  Object.assign(formData, {
    customerId: undefined,
    bargainName: '',
    totalAmount: undefined,
    signDate: '',
    startDate: '',
    endDate: '',
    bargainContent: '',
    bargainFile: '',
    status: '0',
  });
  modalVisible.value = true;
}

function openEditModal(record: adApi.AdApi.Bargain) {
  isEdit.value = true;
  modalTitle.value = '编辑合同';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.customerId) {
    message.warning('请选择客户');
    return;
  }
  try {
    const data = {
      ...formData,
      signDate: formData.signDate ? dayjs(formData.signDate).format('YYYY-MM-DD') : undefined,
      startDate: formData.startDate ? dayjs(formData.startDate).format('YYYY-MM-DD') : undefined,
      endDate: formData.endDate ? dayjs(formData.endDate).format('YYYY-MM-DD') : undefined,
    };
    if (isEdit.value) {
      await adApi.updateBargain(data as adApi.AdApi.Bargain);
      message.success('修改成功');
    } else {
      await adApi.createBargain(data as adApi.AdApi.Bargain);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: adApi.AdApi.Bargain) {
  await adApi.deleteBargain([record.bargainId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="合同管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="合同号">
          <a-input v-model:value="queryParams.bargainNo" placeholder="请输入合同号" allow-clear />
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input v-model:value="queryParams.customerName" placeholder="请输入客户名称" allow-clear />
        </a-form-item>
        <a-form-item label="审核状态">
          <a-select v-model:value="queryParams.applyStatus" placeholder="请选择审核状态" allow-clear style="width: 130px">
            <a-select-option v-for="opt in applyStatusOptions" :key="opt.value" :value="opt.value">
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
        <a-button type="primary" @click="openCreateModal">新增合同</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="bargainId"
        :scroll="{ x: 1400 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'applyStatus'">
            <a-tag v-if="record.applyStatus === '0'" color="orange">待审核</a-tag>
            <a-tag v-else-if="record.applyStatus === '1'" color="green">审核通过</a-tag>
            <a-tag v-else-if="record.applyStatus === '2'" color="red">审核驳回</a-tag>
            <span v-else>{{ record.applyStatus }}</span>
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === '0' ? 'green' : 'red'">
              {{ record.status === '0' ? '正常' : '停用' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该合同吗？"
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
      width="680px"
    >
      <a-form layout="vertical">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="客户ID" required>
              <a-input-number v-model:value="formData.customerId" placeholder="请输入客户ID" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="合同名称">
              <a-input v-model:value="formData.bargainName" placeholder="请输入合同名称" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="合同金额">
          <a-input-number v-model:value="formData.totalAmount" placeholder="请输入合同金额" style="width: 100%" :min="0" :precision="2" />
        </a-form-item>
        <a-form-item label="签订日期">
          <a-date-picker v-model:value="formData.signDate" style="width: 100%" placeholder="请选择签订日期" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="开始日期">
              <a-date-picker v-model:value="formData.startDate" style="width: 100%" placeholder="请选择开始日期" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="结束日期">
              <a-date-picker v-model:value="formData.endDate" style="width: 100%" placeholder="请选择结束日期" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="合同内容">
          <a-textarea v-model:value="formData.bargainContent" placeholder="请输入合同内容" :rows="4" />
        </a-form-item>
        <a-form-item label="合同附件">
          <a-input v-model:value="formData.bargainFile" placeholder="合同附件路径" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>