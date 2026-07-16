<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as messageApi from '#/api/biz/message';

const loading = ref(false);
const queryParams = reactive({
  userName: '',
  auditStatus: undefined as string | undefined,
});
const dataSource = ref<messageApi.MessageApi.Remark[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const auditStatusOptions = [
  { label: '待审核', value: '0' },
  { label: '审核通过', value: '1' },
  { label: '审核拒绝', value: '2' },
];

const columns = [
  { title: '用户名', dataIndex: 'userName', key: 'userName', width: 120 },
  { title: '留言内容', dataIndex: 'content', key: 'content', width: 300, ellipsis: true },
  { title: '审核状态', dataIndex: 'auditStatus', key: 'auditStatus', width: 100 },
  { title: '审核时间', dataIndex: 'auditTime', key: 'auditTime', width: 160 },
  { title: '审核人', dataIndex: 'auditBy', key: 'auditBy', width: 120 },
  { title: '留言时间', dataIndex: 'createTime', key: 'createTime', width: 160 },
  { title: '操作', key: 'action', width: 220, fixed: 'right' as const },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await messageApi.getRemarkList({
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
  queryParams.userName = '';
  queryParams.auditStatus = undefined;
  pagination.current = 1;
  fetchData();
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchData();
}

async function handleAudit(record: messageApi.MessageApi.Remark, status: string) {
  try {
    await messageApi.auditRemark({
      remarkId: record.remarkId,
      auditStatus: status,
    });
    message.success(status === '1' ? '审核通过' : '审核拒绝');
    fetchData();
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(record: messageApi.MessageApi.Remark) {
  await messageApi.deleteRemark([record.remarkId!]);
  message.success('删除成功');
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="用户留言管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="用户名">
          <a-input v-model:value="queryParams.userName" placeholder="请输入用户名" allow-clear style="width: 200px" />
        </a-form-item>
        <a-form-item label="审核状态">
          <a-select v-model:value="queryParams.auditStatus" placeholder="请选择" allow-clear style="width: 120px">
            <a-select-option v-for="opt in auditStatusOptions" :key="opt.value" :value="opt.value">
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

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="remarkId"
        :scroll="{ x: 1200 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'auditStatus'">
            <a-tag v-if="record.auditStatus === '0'" color="orange">待审核</a-tag>
            <a-tag v-else-if="record.auditStatus === '1'" color="green">审核通过</a-tag>
            <a-tag v-else-if="record.auditStatus === '2'" color="red">审核拒绝</a-tag>
            <span v-else>{{ record.auditStatus }}</span>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button
                v-if="record.auditStatus === '0'"
                type="link"
                size="small"
                @click="handleAudit(record, '1')"
              >
                审核通过
              </a-button>
              <a-popconfirm
                v-if="record.auditStatus === '0'"
                title="确认拒绝该留言吗？"
                ok-text="确认"
                cancel-text="取消"
                @confirm="handleAudit(record, '2')"
              >
                <a-button type="link" danger size="small">审核拒绝</a-button>
              </a-popconfirm>
              <a-popconfirm
                title="确认删除该留言吗？"
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
  </div>
</template>