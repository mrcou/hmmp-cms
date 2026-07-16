<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as adApi from '#/api/biz/ad';

const loading = ref(false);
const queryParams = reactive({ areaName: '', status: undefined as string | undefined });
const dataSource = ref<adApi.AdApi.Area[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const modalVisible = ref(false);
const modalTitle = ref('新增区域');
const formData = reactive<adApi.AdApi.Area>({ areaName: '', parentId: undefined, areaCode: '', orderNum: 0, status: '0' });
const isEdit = ref(false);

const statusOptions = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' },
];

const columns = [
  { title: '区域编号', dataIndex: 'areaId', key: 'areaId', width: 100 },
  { title: '区域名称', dataIndex: 'areaName', key: 'areaName', width: 150 },
  { title: '父级ID', dataIndex: 'parentId', key: 'parentId', width: 100 },
  { title: '区域编码', dataIndex: 'areaCode', key: 'areaCode', width: 120 },
  { title: '排序', dataIndex: 'orderNum', key: 'orderNum', width: 80 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 200, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await adApi.getAreaList({
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
  queryParams.areaName = '';
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
  modalTitle.value = '新增区域';
  Object.assign(formData, { areaName: '', parentId: undefined, areaCode: '', orderNum: 0, status: '0' });
  modalVisible.value = true;
}

function openEditModal(record: adApi.AdApi.Area) {
  isEdit.value = true;
  modalTitle.value = '编辑区域';
  Object.assign(formData, { ...record });
  modalVisible.value = true;
}

async function handleSubmit() {
  if (!formData.areaName) {
    message.warning('请输入区域名称');
    return;
  }
  try {
    if (isEdit.value) {
      await adApi.updateArea({ ...formData });
      message.success('修改成功');
    } else {
      await adApi.createArea({ ...formData });
      message.success('新增成功');
    }
    modalVisible.value = false;
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: adApi.AdApi.Area) {
  await adApi.deleteArea([record.areaId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="区域管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="区域名称">
          <a-input v-model:value="queryParams.areaName" placeholder="请输入区域名称" allow-clear />
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
        <a-button type="primary" @click="openCreateModal">新增区域</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="areaId"
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
                title="确认删除该区域吗？"
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
    >
      <a-form layout="vertical">
        <a-form-item label="区域名称" required>
          <a-input v-model:value="formData.areaName" placeholder="请输入区域名称" />
        </a-form-item>
        <a-form-item label="父级ID">
          <a-input-number v-model:value="formData.parentId" placeholder="请输入父级ID" style="width: 100%" />
        </a-form-item>
        <a-form-item label="区域编码">
          <a-input v-model:value="formData.areaCode" placeholder="请输入区域编码" />
        </a-form-item>
        <a-form-item label="排序">
          <a-input-number v-model:value="formData.orderNum" :min="0" style="width: 100%" />
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