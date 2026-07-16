<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as adApi from '#/api/biz/ad';

const loading = ref(false);
const queryParams = reactive({ positionName: '', typeId: undefined as number | undefined, status: undefined as string | undefined });
const dataSource = ref<adApi.AdApi.Position[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增广告位');
const formData = reactive<adApi.AdApi.Position>({
  positionName: '',
  typeId: undefined,
  width: undefined,
  height: undefined,
  positionCode: '',
  pageLocation: '',
  description: '',
  status: '0',
  sortOrder: 0,
});
const isEdit = ref(false);

const statusOptions = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

const columns = [
  { title: '广告位编号', dataIndex: 'positionId', key: 'positionId', width: 100 },
  { title: '广告位名称', dataIndex: 'positionName', key: 'positionName', width: 150 },
  { title: '所属类型', dataIndex: 'typeName', key: 'typeName', width: 120 },
  { title: '宽度', dataIndex: 'width', key: 'width', width: 80 },
  { title: '高度', dataIndex: 'height', key: 'height', width: 80 },
  { title: '广告位编码', dataIndex: 'positionCode', key: 'positionCode', width: 120 },
  { title: '页面位置', dataIndex: 'pageLocation', key: 'pageLocation', width: 120 },
  { title: '排序', dataIndex: 'sortOrder', key: 'sortOrder', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await adApi.getPositionList({
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
  queryParams.positionName = '';
  queryParams.typeId = undefined;
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
  modalTitle.value = '新增广告位';
  Object.assign(formData, {
    positionName: '',
    typeId: undefined,
    width: undefined,
    height: undefined,
    positionCode: '',
    pageLocation: '',
    description: '',
    status: '0',
    sortOrder: 0,
  });
  modalVisible.value = true;
}

function openEditModal(record: adApi.AdApi.Position) {
  isEdit.value = true;
  modalTitle.value = '编辑广告位';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.positionName) {
    message.warning('请输入广告位名称');
    return;
  }
  try {
    if (isEdit.value) {
      await adApi.updatePosition({ ...formData });
      message.success('修改成功');
    } else {
      await adApi.createPosition({ ...formData });
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: adApi.AdApi.Position) {
  await adApi.deletePosition([record.positionId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="广告位管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="广告位名称">
          <a-input v-model:value="queryParams.positionName" placeholder="请输入广告位名称" allow-clear />
        </a-form-item>
        <a-form-item label="类型ID">
          <a-input-number v-model:value="queryParams.typeId" placeholder="类型ID" style="width: 120px" />
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
        <a-button type="primary" @click="openCreateModal">新增广告位</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="positionId"
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
                title="确认删除该广告位吗？"
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
      width="640px"
    >
      <a-form layout="vertical">
        <a-form-item label="广告位名称" required>
          <a-input v-model:value="formData.positionName" placeholder="请输入广告位名称" />
        </a-form-item>
        <a-form-item label="所属类型">
          <a-input-number v-model:value="formData.typeId" placeholder="请输入类型ID" style="width: 100%" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="宽度">
              <a-input-number v-model:value="formData.width" placeholder="宽度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="高度">
              <a-input-number v-model:value="formData.height" placeholder="高度" style="width: 100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="广告位编码">
          <a-input v-model:value="formData.positionCode" placeholder="请输入广告位编码" />
        </a-form-item>
        <a-form-item label="页面位置">
          <a-input v-model:value="formData.pageLocation" placeholder="请输入页面位置" />
        </a-form-item>
        <a-form-item label="排序">
          <a-input-number v-model:value="formData.sortOrder" :min="0" style="width: 100%" />
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="formData.description" placeholder="请输入描述" :rows="3" />
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