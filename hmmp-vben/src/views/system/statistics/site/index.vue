<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Table,
  DatePicker,
  Button,
  Space,
  Input,
  Form,
  FormItem,
} from 'antdv-next';
import * as statisticsApi from '#/api/biz/statistics';

const { RangePicker } = DatePicker;

const loading = ref(false);
const dataSource = ref<any[]>([]);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const formState = reactive({
  dateRange: null as [string, string] | null,
  pageUrl: '',
  userName: '',
});

const columns = [
  { title: 'ID', dataIndex: 'accessId', key: 'accessId', width: 80 },
  { title: '用户名', dataIndex: 'userName', key: 'userName', width: 120 },
  { title: '页面URL', dataIndex: 'pageUrl', key: 'pageUrl', ellipsis: true },
  { title: '页面名称', dataIndex: 'pageName', key: 'pageName', width: 150 },
  { title: '来源URL', dataIndex: 'refererUrl', key: 'refererUrl', ellipsis: true },
  { title: 'IP地址', dataIndex: 'ipAddr', key: 'ipAddr', width: 140 },
  { title: '浏览器', dataIndex: 'browser', key: 'browser', width: 120 },
  { title: '操作系统', dataIndex: 'os', key: 'os', width: 120 },
  { title: '访问时间', dataIndex: 'accessTime', key: 'accessTime', width: 180 },
  { title: '停留时长(秒)', dataIndex: 'stayTime', key: 'stayTime', width: 120 },
];

async function loadData(page = 1) {
  loading.value = true;
  const params: any = {
    pageNum: page,
    pageSize: pagination.pageSize,
  };

  if (formState.dateRange && formState.dateRange.length === 2) {
    params.beginDate = formState.dateRange[0];
    params.endDate = formState.dateRange[1];
  }
  if (formState.pageUrl) {
    params.pageUrl = formState.pageUrl;
  }
  if (formState.userName) {
    params.userName = formState.userName;
  }

  try {
    const res = (await statisticsApi.getSiteAccessList(params)) as any;
    dataSource.value = res?.rows ?? res?.data ?? [];
    pagination.total = res?.total ?? 0;
    pagination.current = page;
  } catch {
    message.error('加载访问日志失败');
  } finally {
    loading.value = false;
  }
}

function onSearch() {
  loadData(1);
}

function onReset() {
  formState.dateRange = null;
  formState.pageUrl = '';
  formState.userName = '';
  loadData(1);
}

function onTableChange(pag: any) {
  loadData(pag.current);
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <Card title="网站访问量统计" class="mb-4">
      <!-- 搜索区域 -->
      <Form layout="inline" class="mb-4">
        <FormItem label="日期范围">
          <RangePicker
            v-model:value="formState.dateRange"
            :placeholder="['开始日期', '结束日期']"
            format="YYYY-MM-DD"
          />
        </FormItem>
        <FormItem label="页面URL">
          <Input
            v-model:value="formState.pageUrl"
            placeholder="请输入页面URL"
            style="width: 200px"
            allow-clear
          />
        </FormItem>
        <FormItem label="用户名">
          <Input
            v-model:value="formState.userName"
            placeholder="请输入用户名"
            style="width: 160px"
            allow-clear
          />
        </FormItem>
        <FormItem>
          <Space>
            <Button type="primary" @click="onSearch">查询</Button>
            <Button @click="onReset">重置</Button>
          </Space>
        </FormItem>
      </Form>

      <Table
        :columns="columns"
        :data-source="dataSource"
        :loading="loading"
        :pagination="pagination"
        row-key="accessId"
        :scroll="{ x: 1400 }"
        @change="onTableChange"
      />
    </Card>
  </div>
</template>