<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import * as adApi from '#/api/biz/ad';

const loading = ref(false);
const queryParams = reactive({
  statType: undefined as string | undefined,
  startDate: '',
  endDate: '',
});
const dataSource = ref<any[]>([]);
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const statTypeOptions = [
  { label: '广告浏览', value: 'view' },
  { label: '广告点击', value: 'click' },
  { label: '广告收入', value: 'income' },
  { label: '广告支出', value: 'expense' },
];

const columns = [
  { title: '统计编号', dataIndex: 'statId', key: 'statId', width: 100 },
  { title: '广告名称', dataIndex: 'adName', key: 'adName', width: 180 },
  { title: '统计类型', dataIndex: 'statType', key: 'statType', width: 120 },
  { title: '统计日期', dataIndex: 'statDate', key: 'statDate', width: 120 },
  { title: '浏览数', dataIndex: 'viewCount', key: 'viewCount', width: 100 },
  { title: '点击数', dataIndex: 'clickCount', key: 'clickCount', width: 100 },
  { title: '点击率', dataIndex: 'clickRate', key: 'clickRate', width: 100 },
  { title: '收入金额', dataIndex: 'incomeAmount', key: 'incomeAmount', width: 120 },
  { title: '支出金额', dataIndex: 'expenseAmount', key: 'expenseAmount', width: 120 },
];

async function fetchData() {
  loading.value = true;
  try {
    const res = await adApi.getAdStat();
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
  queryParams.statType = undefined;
  queryParams.startDate = '';
  queryParams.endDate = '';
  pagination.current = 1;
  fetchData();
}

function handleTableChange(pag: { current: number; pageSize: number }) {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  fetchData();
}

function handleExport() {
  message.info('导出功能开发中...');
}

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="p-4">
    <a-card :bordered="false" title="信息统计管理">
      <a-form layout="inline" class="mb-4">
        <a-form-item label="统计类型">
          <a-select v-model:value="queryParams.statType" placeholder="请选择统计类型" allow-clear style="width: 150px">
            <a-select-option v-for="opt in statTypeOptions" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="开始日期">
          <a-date-picker v-model:value="queryParams.startDate" placeholder="开始日期" />
        </a-form-item>
        <a-form-item label="结束日期">
          <a-date-picker v-model:value="queryParams.endDate" placeholder="结束日期" />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleSearch">查询</a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-space class="mb-4">
        <a-button @click="handleExport">导出报表</a-button>
      </a-space>

      <a-table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="statId"
        :scroll="{ x: 1200 }"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'statType'">
            <a-tag v-if="record.statType === 'view'" color="blue">广告浏览</a-tag>
            <a-tag v-else-if="record.statType === 'click'" color="green">广告点击</a-tag>
            <a-tag v-else-if="record.statType === 'income'" color="orange">广告收入</a-tag>
            <a-tag v-else-if="record.statType === 'expense'" color="purple">广告支出</a-tag>
            <span v-else>{{ record.statType }}</span>
          </template>
          <template v-if="column.key === 'clickRate'">
            <span>{{ record.clickRate ? record.clickRate + '%' : '-' }}</span>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>