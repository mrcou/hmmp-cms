<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import * as meetingApi from '#/api/biz/meeting';

const loading = ref(false);
const queryParams = reactive({
  userName: '',
});
const dataSource = ref<meetingApi.MeetingApi.TravelBooked[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const columns = [
  { title: '线路名称', dataIndex: 'wayName', key: 'wayName', width: 180, ellipsis: true },
  { title: '预订人', dataIndex: 'userName', key: 'userName', width: 120 },
  { title: '人数', dataIndex: 'peopleCount', key: 'peopleCount', width: 100 },
  { title: '总价', dataIndex: 'totalPrice', key: 'totalPrice', width: 120 },
  { title: '预订时间', dataIndex: 'bookTime', key: 'bookTime', width: 160 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await meetingApi.getTravelBookedList({
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
  pagination.current = 1;
  fetchData();
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchData();
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="旅游线路预订详情">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="预订人">
          <a-input v-model:value="queryParams.userName" placeholder="请输入预订人" allow-clear style="width: 200px" />
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
        row-key="bookedId"
        :scroll="{ x: 800 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'totalPrice'">
            <span>¥{{ record.totalPrice?.toFixed(2) }}</span>
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === '0' ? 'blue' : record.status === '1' ? 'green' : 'red'">
              {{ record.status === '0' ? '待确认' : record.status === '1' ? '已确认' : '已取消' }}
            </a-tag>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>