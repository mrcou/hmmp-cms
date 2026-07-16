<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as adApi from '#/api/biz/ad';

const loading = ref(false);
const queryParams = reactive({
  invoiceNo: '',
  bargainId: undefined as number | undefined,
  applyStatus: undefined as string | undefined,
});
const dataSource = ref<adApi.AdApi.AdInvoice[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增发票');
const formData = reactive<adApi.AdApi.AdInvoice>({
  bargainId: undefined,
  invoiceNo: '',
  invoiceType: '',
  amount: undefined,
  taxAmount: undefined,
  totalAmount: undefined,
  title: '',
  taxId: '',
});
const isEdit = ref(false);

const applyStatusOptions = [
  { label: '待审核', value: '0' },
  { label: '审核通过', value: '1' },
  { label: '审核驳回', value: '2' },
];

const invoiceTypeOptions = [
  { label: '增值税普通发票', value: '0' },
  { label: '增值税专用发票', value: '1' },
  { label: '电子发票', value: '2' },
];

const columns = [
  { title: '发票编号', dataIndex: 'invoiceId', key: 'invoiceId', width: 100 },
  { title: '发票号', dataIndex: 'invoiceNo', key: 'invoiceNo', width: 150 },
  { title: '合同ID', dataIndex: 'bargainId', key: 'bargainId', width: 100 },
  { title: '发票抬头', dataIndex: 'title', key: 'title', width: 180 },
  { title: '金额', dataIndex: 'amount', key: 'amount', width: 110 },
  { title: '税额', dataIndex: 'taxAmount', key: 'taxAmount', width: 110 },
  { title: '价税合计', dataIndex: 'totalAmount', key: 'totalAmount', width: 120 },
  { title: '发票类型', dataIndex: 'invoiceType', key: 'invoiceType', width: 120 },
  { title: '审核状态', dataIndex: 'applyStatus', key: 'applyStatus', width: 100 },
  { title: '开票状态', dataIndex: 'makeStatus', key: 'makeStatus', width: 100 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await adApi.getAdInvoiceList({
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
  queryParams.invoiceNo = '';
  queryParams.bargainId = undefined;
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
  modalTitle.value = '新增发票';
  Object.assign(formData, {
    bargainId: undefined,
    invoiceNo: '',
    invoiceType: '',
    amount: undefined,
    taxAmount: undefined,
    totalAmount: undefined,
    title: '',
    taxId: '',
  });
  modalVisible.value = true;
}

function openEditModal(record: adApi.AdApi.AdInvoice) {
  isEdit.value = true;
  modalTitle.value = '编辑发票';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.title) {
    message.warning('请输入发票抬头');
    return;
  }
  try {
    if (isEdit.value) {
      await adApi.updateAdInvoice({ ...formData });
      message.success('修改成功');
    } else {
      await adApi.createAdInvoice({ ...formData });
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: adApi.AdApi.AdInvoice) {
  await adApi.deleteAdInvoice([record.invoiceId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="发票管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="发票号">
          <a-input v-model:value="queryParams.invoiceNo" placeholder="请输入发票号" allow-clear />
        </a-form-item>
        <a-form-item label="合同ID">
          <a-input-number v-model:value="queryParams.bargainId" placeholder="合同ID" style="width: 120px" />
        </a-form-item>
        <a-form-item label="审核状态">
          <a-select v-model:value="queryParams.applyStatus" placeholder="审核状态" allow-clear style="width: 130px">
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
        <a-button type="primary" @click="openCreateModal">新增发票</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="invoiceId"
        :scroll="{ x: 1400 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'invoiceType'">
            <a-tag v-if="record.invoiceType === '0'" color="blue">普通发票</a-tag>
            <a-tag v-else-if="record.invoiceType === '1'" color="purple">专用发票</a-tag>
            <a-tag v-else-if="record.invoiceType === '2'" color="cyan">电子发票</a-tag>
            <span v-else>{{ record.invoiceType }}</span>
          </template>
          <template v-if="column.key === 'applyStatus'">
            <a-tag v-if="record.applyStatus === '0'" color="orange">待审核</a-tag>
            <a-tag v-else-if="record.applyStatus === '1'" color="green">审核通过</a-tag>
            <a-tag v-else-if="record.applyStatus === '2'" color="red">审核驳回</a-tag>
            <span v-else>{{ record.applyStatus }}</span>
          </template>
          <template v-if="column.key === 'makeStatus'">
            <a-tag v-if="record.makeStatus === '0'" color="orange">待开票</a-tag>
            <a-tag v-else-if="record.makeStatus === '1'" color="green">已开票</a-tag>
            <span v-else>{{ record.makeStatus }}</span>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该发票吗？"
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
            <a-form-item label="发票号">
              <a-input v-model:value="formData.invoiceNo" placeholder="请输入发票号" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="发票抬头" required>
          <a-input v-model:value="formData.title" placeholder="请输入发票抬头" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="发票类型">
              <a-select v-model:value="formData.invoiceType" placeholder="请选择发票类型" style="width: 100%">
                <a-select-option v-for="opt in invoiceTypeOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="税号">
              <a-input v-model:value="formData.taxId" placeholder="请输入税号" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="金额">
              <a-input-number v-model:value="formData.amount" placeholder="金额" style="width: 100%" :min="0" :precision="2" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="税额">
              <a-input-number v-model:value="formData.taxAmount" placeholder="税额" style="width: 100%" :min="0" :precision="2" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="价税合计">
              <a-input-number v-model:value="formData.totalAmount" placeholder="合计" style="width: 100%" :min="0" :precision="2" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>