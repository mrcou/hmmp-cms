<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as adApi from '#/api/biz/ad';

const loading = ref(false);
const queryParams = reactive({ customerName: '', companyName: '', status: undefined as string | undefined });
const dataSource = ref<adApi.AdApi.Customer[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增客户');
const formData = reactive<adApi.AdApi.Customer>({
  customerName: '',
  contactPerson: '',
  phone: '',
  mobile: '',
  email: '',
  address: '',
  areaId: undefined,
  companyName: '',
  companyType: '',
  taxId: '',
  bankName: '',
  bankAccount: '',
  status: '0',
});
const isEdit = ref(false);

const statusOptions = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

const columns = [
  { title: '客户编号', dataIndex: 'customerId', key: 'customerId', width: 100 },
  { title: '客户名称', dataIndex: 'customerName', key: 'customerName', width: 150 },
  { title: '公司名称', dataIndex: 'companyName', key: 'companyName', width: 180 },
  { title: '联系人', dataIndex: 'contactPerson', key: 'contactPerson', width: 100 },
  { title: '手机号', dataIndex: 'mobile', key: 'mobile', width: 130 },
  { title: '邮箱', dataIndex: 'email', key: 'email', width: 180 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await adApi.getCustomerList({
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
  queryParams.customerName = '';
  queryParams.companyName = '';
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
  modalTitle.value = '新增客户';
  Object.assign(formData, {
    customerName: '',
    contactPerson: '',
    phone: '',
    mobile: '',
    email: '',
    address: '',
    areaId: undefined,
    companyName: '',
    companyType: '',
    taxId: '',
    bankName: '',
    bankAccount: '',
    status: '0',
  });
  modalVisible.value = true;
}

function openEditModal(record: adApi.AdApi.Customer) {
  isEdit.value = true;
  modalTitle.value = '编辑客户';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.customerName) {
    message.warning('请输入客户名称');
    return;
  }
  try {
    if (isEdit.value) {
      await adApi.updateCustomer({ ...formData });
      message.success('修改成功');
    } else {
      await adApi.createCustomer({ ...formData });
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: adApi.AdApi.Customer) {
  await adApi.deleteCustomer([record.customerId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="客户管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="客户名称">
          <a-input v-model:value="queryParams.customerName" placeholder="请输入客户名称" allow-clear />
        </a-form-item>
        <a-form-item label="公司名称">
          <a-input v-model:value="queryParams.companyName" placeholder="请输入公司名称" allow-clear />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="queryParams.status" placeholder="请选择状态" allow-clear style="width: 120px">
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
        <a-button type="primary" @click="openCreateModal">新增客户</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="customerId"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === '0' ? 'green' : 'red'">
              {{ record.status === '0' ? '正常' : '停用' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="openEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确认删除该客户吗？"
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
      width="700px"
    >
      <a-form layout="vertical">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="客户名称" required>
              <a-input v-model:value="formData.customerName" placeholder="请输入客户名称" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="公司名称">
              <a-input v-model:value="formData.companyName" placeholder="请输入公司名称" />
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
            <a-form-item label="公司类型">
              <a-input v-model:value="formData.companyType" placeholder="请输入公司类型" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="电话">
              <a-input v-model:value="formData.phone" placeholder="请输入电话" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="手机">
              <a-input v-model:value="formData.mobile" placeholder="请输入手机号" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="邮箱">
          <a-input v-model:value="formData.email" placeholder="请输入邮箱" />
        </a-form-item>
        <a-form-item label="地址">
          <a-textarea v-model:value="formData.address" placeholder="请输入地址" :rows="2" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="税号">
              <a-input v-model:value="formData.taxId" placeholder="请输入税号" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="区域ID">
              <a-input-number v-model:value="formData.areaId" placeholder="区域ID" style="width: 100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="开户银行">
          <a-input v-model:value="formData.bankName" placeholder="请输入开户银行" />
        </a-form-item>
        <a-form-item label="银行账户">
          <a-input v-model:value="formData.bankAccount" placeholder="请输入银行账户" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model:value="formData.status" style="width: 100%">
            <a-select-option v-for="opt in statusOptions" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>