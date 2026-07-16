<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import {
  Card,
  Row,
  Col,
  Statistic,
  DatePicker,
  Button,
  Space,
  Table,
  Spin,
} from 'antdv-next';
import * as statisticsApi from '#/api/biz/statistics';

const { RangePicker } = DatePicker;

const loading = ref(false);
const dateRange = ref<[string, string] | null>(null);

const journalTotal = reactive({
  totalArticles: 0,
  totalAuthors: 0,
  totalReviewers: 0,
  publishedCount: 0,
});

const auditCycle = reactive({
  avgDays: 0,
  minDays: 0,
  maxDays: 0,
  totalCount: 0,
});

const publishCycle = reactive({
  avgDays: 0,
  minDays: 0,
  maxDays: 0,
  totalCount: 0,
});

const editorWorkload = ref<any[]>([]);
const editorWorkloadColumns = [
  { title: '责编', dataIndex: 'editorName', key: 'editorName' },
  { title: '处理稿件数', dataIndex: 'totalCount', key: 'totalCount' },
  { title: '完成数', dataIndex: 'completedCount', key: 'completedCount' },
  { title: '平均处理天数', dataIndex: 'avgDays', key: 'avgDays' },
];

const auditStat = ref<any[]>([]);
const auditStatColumns = [
  { title: '审稿人', dataIndex: 'reviewerName', key: 'reviewerName' },
  { title: '邀请数', dataIndex: 'inviteCount', key: 'inviteCount' },
  { title: '接受数', dataIndex: 'acceptCount', key: 'acceptCount' },
  { title: '拒绝数', dataIndex: 'rejectCount', key: 'rejectCount' },
  { title: '完成数', dataIndex: 'completedCount', key: 'completedCount' },
];

const citedArticles = ref<any[]>([]);
const citedColumns = [
  { title: '稿件标题', dataIndex: 'title', key: 'title' },
  { title: '作者', dataIndex: 'authorName', key: 'authorName' },
  { title: '被引次数', dataIndex: 'citedCount', key: 'citedCount' },
  { title: '发表时间', dataIndex: 'publishTime', key: 'publishTime' },
];

const userStat = ref<any[]>([]);
const userStatColumns = [
  { title: '用户', dataIndex: 'userName', key: 'userName' },
  { title: '投稿数', dataIndex: 'submissionCount', key: 'submissionCount' },
  { title: '审稿数', dataIndex: 'reviewCount', key: 'reviewCount' },
  { title: '登录次数', dataIndex: 'loginCount', key: 'loginCount' },
];

async function loadData() {
  loading.value = true;
  const params: any = {};
  if (dateRange.value && dateRange.value.length === 2) {
    params.beginDate = dateRange.value[0];
    params.endDate = dateRange.value[1];
  }

  try {
    const [
      totalRes,
      auditCycleRes,
      publishCycleRes,
      workloadRes,
      auditStatRes,
      citedRes,
      userRes,
    ] = await Promise.all([
      statisticsApi.getJournalTotal(params),
      statisticsApi.getAuditCycle(params),
      statisticsApi.getPublishCycle(params),
      statisticsApi.getEditorWorkload(params),
      statisticsApi.getAuditStat(params),
      statisticsApi.getCitedArticle(params),
      statisticsApi.getUserStat(params),
    ]);

    const td = totalRes as any;
    if (td) {
      journalTotal.totalArticles = td.totalArticles ?? 0;
      journalTotal.totalAuthors = td.totalAuthors ?? 0;
      journalTotal.totalReviewers = td.totalReviewers ?? 0;
      journalTotal.publishedCount = td.publishedCount ?? 0;
    }

    const ac = auditCycleRes as any;
    if (ac) {
      auditCycle.avgDays = ac.avgDays ?? 0;
      auditCycle.minDays = ac.minDays ?? 0;
      auditCycle.maxDays = ac.maxDays ?? 0;
      auditCycle.totalCount = ac.totalCount ?? 0;
    }

    const pc = publishCycleRes as any;
    if (pc) {
      publishCycle.avgDays = pc.avgDays ?? 0;
      publishCycle.minDays = pc.minDays ?? 0;
      publishCycle.maxDays = pc.maxDays ?? 0;
      publishCycle.totalCount = pc.totalCount ?? 0;
    }

    editorWorkload.value = (workloadRes as any)?.rows ?? (workloadRes as any)?.data ?? [];
    auditStat.value = (auditStatRes as any)?.rows ?? (auditStatRes as any)?.data ?? [];
    citedArticles.value = (citedRes as any)?.rows ?? (citedRes as any)?.data ?? [];
    userStat.value = (userRes as any)?.rows ?? (userRes as any)?.data ?? [];
  } catch {
    message.error('加载统计数据失败');
  } finally {
    loading.value = false;
  }
}

function onSearch() {
  loadData();
}

function onReset() {
  dateRange.value = null;
  loadData();
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="p-4">
    <Spin :spinning="loading">
      <!-- 日期筛选 -->
      <Card class="mb-4">
        <Space>
          <RangePicker
            v-model:value="dateRange"
            :placeholder="['开始日期', '结束日期']"
            format="YYYY-MM-DD"
          />
          <Button type="primary" @click="onSearch">查询</Button>
          <Button @click="onReset">重置</Button>
        </Space>
      </Card>

      <!-- 期刊统计卡片 -->
      <Card title="期刊统计" class="mb-4">
        <Row :gutter="16">
          <Col :span="6">
            <Statistic title="总投稿数" :value="journalTotal.totalArticles" />
          </Col>
          <Col :span="6">
            <Statistic title="总作者数" :value="journalTotal.totalAuthors" />
          </Col>
          <Col :span="6">
            <Statistic title="总审稿人数" :value="journalTotal.totalReviewers" />
          </Col>
          <Col :span="6">
            <Statistic title="已发表数" :value="journalTotal.publishedCount" />
          </Col>
        </Row>
      </Card>

      <!-- 审稿周期统计 -->
      <Card title="审稿周期统计" class="mb-4">
        <Row :gutter="16">
          <Col :span="6">
            <Statistic title="平均审稿天数" :value="auditCycle.avgDays" suffix="天" />
          </Col>
          <Col :span="6">
            <Statistic title="最短审稿天数" :value="auditCycle.minDays" suffix="天" />
          </Col>
          <Col :span="6">
            <Statistic title="最长审稿天数" :value="auditCycle.maxDays" suffix="天" />
          </Col>
          <Col :span="6">
            <Statistic title="审稿总次数" :value="auditCycle.totalCount" />
          </Col>
        </Row>
      </Card>

      <!-- 出版周期统计 -->
      <Card title="出版周期统计" class="mb-4">
        <Row :gutter="16">
          <Col :span="6">
            <Statistic title="平均出版天数" :value="publishCycle.avgDays" suffix="天" />
          </Col>
          <Col :span="6">
            <Statistic title="最短出版天数" :value="publishCycle.minDays" suffix="天" />
          </Col>
          <Col :span="6">
            <Statistic title="最长出版天数" :value="publishCycle.maxDays" suffix="天" />
          </Col>
          <Col :span="6">
            <Statistic title="已出版稿件数" :value="publishCycle.totalCount" />
          </Col>
        </Row>
      </Card>

      <!-- 责编工作量 -->
      <Card title="责编工作量" class="mb-4">
        <Table
          :columns="editorWorkloadColumns"
          :data-source="editorWorkload"
          :pagination="false"
          row-key="editorName"
          size="small"
        />
      </Card>

      <!-- 审稿人统计 -->
      <Card title="审稿人统计" class="mb-4">
        <Table
          :columns="auditStatColumns"
          :data-source="auditStat"
          :pagination="false"
          row-key="reviewerName"
          size="small"
        />
      </Card>

      <!-- 高被引文章 -->
      <Card title="高被引文章" class="mb-4">
        <Table
          :columns="citedColumns"
          :data-source="citedArticles"
          :pagination="false"
          row-key="title"
          size="small"
        />
      </Card>

      <!-- 用户统计 -->
      <Card title="用户统计" class="mb-4">
        <Table
          :columns="userStatColumns"
          :data-source="userStat"
          :pagination="false"
          row-key="userName"
          size="small"
        />
      </Card>
    </Spin>
  </div>
</template>