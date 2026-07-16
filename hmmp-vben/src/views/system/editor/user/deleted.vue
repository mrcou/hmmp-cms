<script lang="ts" setup>
import { reactive, ref } from 'vue';

import { message } from 'antdv-next';

const searchForm = reactive({
  userName: '',
  userType: undefined as string | undefined,
});

const tableData = ref<any[]>([]);
const tableLoading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showTotal: (total: number) => `共 ${total} 条`,
});

const columns = [
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 90 },
  { title: '用户名', dataIndex: 'userName', key: 'userName', width: 140 },
  { title: '用户类型', dataIndex: 'userType', key: 'userType', width: 120 },
  { title: '删除时间', dataIndex: 'deleteTime', key: 'deleteTime', width: 180 },
  { title: '删除人', dataIndex: 'deleteBy', key: 'deleteBy', width: 120 },
  { title: '操作', key: 'action', width: 140 },
];

function onSearch() {
  message.info('查询已删除用户');
}

function onReset() {
  searchForm.userName = '';
  searchForm.userType = undefined;
}

function onRecover(row: any) {
  message.success(`已恢复用户：${row.userName ?? row.userId}`);
}
</script>

<template>
  <div class="p-4">
    <a-card class="mb-4" size="small">
      <a-form layout="inline">
        <a-form-item label="用户名">
          <a-input v-model:value="searchForm.userName" placeholder="请输入" allow-clear style="width:160px" />
        </a-form-item>
        <a-form-item label="用户类型">
          <a-select v-model:value="searchForm.userType" placeholder="请选择" allow-clear style="width:140px">
            <a-select-option value="author">作者</a-select-option>
            <a-select-option value="reviewer">审稿人</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="onSearch">查询</a-button>
            <a-button @click="onReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>
    </a-card>

    <a-card title="已删除用户管理">
      <a-table
        :columns="columns"
        :data-source="tableData"
        :pagination="pagination"
        :loading="tableLoading"
        row-key="userId"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <a-button size="small" type="primary" @click="onRecover(record)">恢复</a-button>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>
