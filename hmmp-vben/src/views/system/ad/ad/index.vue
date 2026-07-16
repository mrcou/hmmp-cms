<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import dayjs from 'dayjs';
import * as adApi from '#/api/biz/ad';

const loading = ref(false);
const queryParams = reactive({
  adName: '',
  typeId: undefined as number | undefined,
  positionId: undefined as number | undefined,
  applyStatus: undefined as string | undefined,
});
const dataSource = ref<adApi.AdApi.AdInfo[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增广告');
const formData = reactive<adApi.AdApi.AdInfo>({
  bargainId: undefined,
  customerId: undefined,
  adName: '',
  typeId: undefined,
  positionId: undefined,
  adContent: '',
  adImage: '',
  adLink: '',
  startDate: '',
  endDate: '',
  price: undefined,
  issueIds: '',
  status: '0',
});
const isEdit = ref(false);

const applyStatusOptions = [
  { label: '待审核', value: '0' },
  { label: '审核通过', value: '1' },
  { label: '审核驳回', value: '2' },
];

const columns = [
  { title: '广告编号', dataIndex: 'adId', key: 'adId', width: 100 },
  { title: '广告名称', dataIndex: 'adName', key: 'adName', width: 180 },
  { title: '广告类型', dataIndex: 'typeName', key: 'typeName', width: 120 },
  { title: '广告位', dataIndex: 'positionName', key: 'positionName', width: 120 },
  { title: '开始日期', dataIndex: 'startDate', key: 'startDate', width: 120 },
  { title: '结束日期', dataIndex: 'endDate', key: 'endDate', width: 120 },
  { title: '价格', dataIndex: 'price', key: 'price', width: 100 },
  { title: '审核状态', dataIndex: 'applyStatus', key: 'applyStatus', width: 100 },
  { title: '浏览数', dataIndex: 'viewCount', key: 'viewCount', width: 90 },
  { title: '点击数', dataIndex: 'clickCount', key: 'clickCount', width: 90 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await adApi.getAdInfoList({
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
  queryParams.adName = '';
  queryParams.typeId = undefined;
  queryParams.positionId = undefined;
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
  modalTitle.value = '新增广告';
  Object.assign(formData, {
    bargainId: undefined,
    customerId: undefined,
    adName: '',
    typeId: undefined,
    positionId: undefined,
    adContent: '',
    adImage: '',
    adLink: '',
    startDate: '',
    endDate: '',
    price: undefined,
    issueIds: '',
    status: '0',
  });
  modalVisible.value = true;
}

function openEditModal(record: adApi.AdApi.AdInfo) {
  isEdit.value = true;
  modalTitle.value = '编辑广告';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.adName) {
    message.warning('请输入广告名称');
    return;
  }
  try {
    const data = {
      ...formData,
      startDate: formData.startDate ? dayjs(formData.startDate).format('YYYY-MM-DD') : undefined,
      endDate: formData.endDate ? dayjs(formData.endDate).format('YYYY-MM-DD') : undefined,
    };
    if (isEdit.value) {
      await adApi.updateAdInfo(data as adApi.AdApi.AdInfo);
      message.success('修改成功');
    } else {
      await adApi.createAdInfo(data as adApi.AdApi.AdInfo);
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: adApi.AdApi.AdInfo) {
  await adApi.deleteAdInfo([record.adId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="广告管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="广告名称">
          <a-input v-model:value="queryParams.adName" placeholder="请输入广告名称" allow-clear />
        </a-form-item>
        <a-form-item label="广告类型">
          <a-input-number v-model:value="queryParams.typeId" placeholder="类型ID" style="width: 120px" />
        </a-form-item>
        <a-form-item label="广告位">
          <a-input-number v-model:value="queryParams.positionId" placeholder="广告位ID" style="width: 120px" />
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
        <a-button type="primary" @click="openCreateModal">新增广告</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="adId"
        :scroll="{ x: 1500 }"
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
                title="确认删除该广告吗？"
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
            <a-form-item label="合同ID">
              <a-input-number v-model:value="formData.bargainId" placeholder="合同ID" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客户ID">
              <a-input-number v-model:value="formData.customerId" placeholder="客户ID" style="width: 100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="广告名称" required>
          <a-input v-model:value="formData.adName" placeholder="请输入广告名称" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="广告类型">
              <a-input-number v-model:value="formData.typeId" placeholder="类型ID" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="广告位">
              <a-input-number v-model:value="formData.positionId" placeholder="广告位ID" style="width: 100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="广告内容">
          <a-textarea v-model:value="formData.adContent" placeholder="请输入广告内容" :rows="3" />
        </a-form-item>
        <a-form-item label="广告图片">
          <a-input v-model:value="formData.adImage" placeholder="广告图片URL" />
        </a-form-item>
        <a-form-item label="广告链接">
          <a-input v-model:value="formData.adLink" placeholder="请输入广告链接" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="开始日期">
              <a-date-picker v-model:value="formData.startDate" style="width: 100%" placeholder="开始日期" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="结束日期">
              <a-date-picker v-model:value="formData.endDate" style="width: 100%" placeholder="结束日期" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="价格">
              <a-input-number v-model:value="formData.price" placeholder="价格" style="width: 100%" :min="0" :precision="2" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="期刊IDS">
              <a-input v-model:value="formData.issueIds" placeholder="期刊IDS" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>