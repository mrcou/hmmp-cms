<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { MonitorJobApi } from '#/api/monitor/job';

import { nextTick, ref } from 'vue';

import { Page } from '@vben/common-ui';
import { IconifyIcon, Plus } from '@vben/icons';

import { Button, message, Modal, Tabs } from 'antdv-next';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { useVbenModal, VbenTableAction } from '@vben/common-ui';
import {
  changeJobStatus,
  cleanJobLog,
  deleteJob,
  deleteJobLog,
  exportJob,
  exportJobLog,
  getJobList,
  getJobLogList,
  runJob,
} from '#/api/monitor/job';
import { compactSearchFormOptions } from '#/views/_shared/table-search';

import {
  useJobColumns,
  useJobGridFormSchema,
  useJobLogColumns,
  useJobLogGridFormSchema,
} from './data';
import Detail from './modules/detail.vue';
import Form from './modules/form.vue';

defineOptions({ name: 'Job' });

const activeTab = ref('jobs');
const tabItems = [
  { key: 'jobs', label: '任务列表' },
  { key: 'logs', label: '调度日志' },
];

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnClose: true,
});

const [DetailModal, detailModalApi] = useVbenModal({
  connectedComponent: Detail,
  destroyOnClose: true,
});

const [JobGrid, jobGridApi] = useVbenVxeGrid({
  formOptions: {
    ...compactSearchFormOptions,
    schema: useJobGridFormSchema(),
    submitOnChange: true,
  },
  gridOptions: {
    columns: useJobColumns(onStatusChange),
    height: 'auto',
    keepSource: true,
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          return await getJobList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
          });
        },
      },
    },
    rowConfig: { keyField: 'jobId' },
    toolbarConfig: {
      custom: true,
      export: false,
      refresh: true,
      search: true,
      zoom: true,
    },
  } as VxeTableGridOptions<MonitorJobApi.SysJob>,
});

const [LogGrid, logGridApi] = useVbenVxeGrid({
  formOptions: {
    ...compactSearchFormOptions,
    schema: useJobLogGridFormSchema(),
    submitOnChange: true,
  },
  gridOptions: {
    columns: useJobLogColumns(),
    height: 'auto',
    keepSource: true,
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          return await getJobLogList({
            pageNum: page.currentPage,
            pageSize: page.pageSize,
            ...normalizeLogParams(formValues),
          });
        },
      },
    },
    rowConfig: { keyField: 'jobLogId' },
    toolbarConfig: {
      custom: true,
      export: false,
      refresh: true,
      search: true,
      zoom: true,
    },
  } as VxeTableGridOptions<MonitorJobApi.SysJobLog>,
});

function confirm(content: string, title: string) {
  return new Promise((resolve, reject) => {
    Modal.confirm({
      content,
      onCancel: () => reject(new Error('已取消')),
      onOk: () => resolve(true),
      title,
    });
  });
}

function normalizeDateValue(value: unknown) {
  if (!value) return undefined;
  if (typeof value === 'string') return value;
  if (
    typeof value === 'object' &&
    'format' in value &&
    typeof value.format === 'function'
  ) {
    return value.format('YYYY-MM-DD');
  }
  return String(value);
}

function normalizeLogParams(formValues: Record<string, any>) {
  const { dateRange, ...params } = formValues;
  const range = Array.isArray(dateRange) ? dateRange : [];
  const beginTime = normalizeDateValue(range[0]);
  const endTime = normalizeDateValue(range[1]);

  if (beginTime || endTime) {
    return {
      ...params,
      params: { beginTime, endTime },
    };
  }

  return params;
}

async function onStatusChange(
  newStatus: string,
  row: MonitorJobApi.SysJob,
) {
  const text = newStatus === '0' ? '恢复' : '暂停';
  try {
    await confirm(`确认要${text}"${row.jobName}"任务吗？`, '提示');
    await changeJobStatus(row.jobId!, newStatus);
    message.success(`${text}成功`);
    return true;
  } catch {
    return false;
  }
}

function refreshJobs() {
  jobGridApi.query();
}

function refreshLogs() {
  if (activeTab.value === 'logs') {
    logGridApi.query();
  }
}

async function onExportJobs() {
  const formValues = (await jobGridApi.formApi?.getValues()) ?? {};
  await exportJob(formValues);
}

async function onExportLogs() {
  const formValues = (await logGridApi.formApi?.getValues()) ?? {};
  await exportJobLog(normalizeLogParams(formValues));
}

function onCreate() {
  formModalApi.setData({}).open();
}

function onEdit(row: MonitorJobApi.SysJob) {
  formModalApi.setData({ jobId: row.jobId }).open();
}

function onViewJob(row: MonitorJobApi.SysJob) {
  detailModalApi.setData({ jobId: row.jobId!, type: 'job' }).open();
}

function onViewLog(row: MonitorJobApi.SysJobLog) {
  detailModalApi.setData({ jobLogId: row.jobLogId!, type: 'log' }).open();
}

async function onRun(row: MonitorJobApi.SysJob) {
  try {
    await confirm(`确认立即执行"${row.jobName}"任务吗？`, '提示');
    await runJob(row.jobId!, row.jobGroup);
    message.success('执行指令已发送');
    refreshLogs();
  } catch {
    // 用户取消
  }
}

function onDeleteJob(row: MonitorJobApi.SysJob) {
  deleteJob(row.jobId!).then(() => {
    message.success('删除成功');
    refreshJobs();
  });
}

function onDeleteLog(row: MonitorJobApi.SysJobLog) {
  deleteJobLog(row.jobLogId!).then(() => {
    message.success('删除成功');
    refreshLogs();
  });
}

async function onBatchDeleteJobs() {
  const rows = jobGridApi.grid?.getCheckboxRecords() as
    | MonitorJobApi.SysJob[]
    | undefined;
  if (!rows || rows.length === 0) {
    message.warning('请至少选择一条任务');
    return;
  }

  try {
    await confirm(`确认删除选中的${rows.length}条任务吗？`, '提示');
    await deleteJob(rows.map((row) => row.jobId!));
    message.success('删除成功');
    refreshJobs();
  } catch {
    // 用户取消
  }
}

async function onBatchDeleteLogs() {
  const rows = logGridApi.grid?.getCheckboxRecords() as
    | MonitorJobApi.SysJobLog[]
    | undefined;
  if (!rows || rows.length === 0) {
    message.warning('请至少选择一条日志');
    return;
  }

  try {
    await confirm(`确认删除选中的${rows.length}条日志吗？`, '提示');
    await deleteJobLog(rows.map((row) => row.jobLogId!));
    message.success('删除成功');
    refreshLogs();
  } catch {
    // 用户取消
  }
}

async function onCleanLogs() {
  try {
    await confirm('确认清空所有调度日志吗？', '提示');
    await cleanJobLog();
    message.success('清空成功');
    refreshLogs();
  } catch {
    // 用户取消
  }
}

async function onShowLogs(row: MonitorJobApi.SysJob) {
  activeTab.value = 'logs';
  await nextTick();
  logGridApi.formApi?.setValues({
    jobGroup: row.jobGroup,
    jobName: row.jobName,
  });
  refreshLogs();
}
</script>

<template>
  <Page auto-content-height content-class="flex flex-col gap-3 overflow-hidden">
    <FormModal @success="refreshJobs" />
    <DetailModal />

    <Tabs v-model:active-key="activeTab" :items="tabItems" class="shrink-0" />

    <div v-if="activeTab === 'jobs'" class="min-h-0 flex-1">
      <JobGrid class="h-full" table-title="定时任务列表">
        <template #toolbar-tools>
          <Button class="mr-2" @click="onExportJobs">导出</Button>
          <Button danger class="mr-2" @click="onBatchDeleteJobs">
            <IconifyIcon icon="lucide:trash-2" class="size-5" />
            删除
          </Button>
          <Button type="primary" @click="onCreate">
            <Plus class="size-5" />
            新增任务
          </Button>
        </template>
        <template #action="{ row }">
          <VbenTableAction
            :actions="[
              { text: '执行一次', icon: 'lucide:play', onClick: () => onRun(row) },
              { text: '编辑', icon: 'lucide:edit', onClick: () => onEdit(row) },
              { text: '日志', icon: 'lucide:list', onClick: () => onShowLogs(row) },
            ]"
            :dropdown-actions="[
              { text: '详情', icon: 'lucide:eye', onClick: () => onViewJob(row) },
              {
                text: '删除',
                icon: 'lucide:trash-2',
                danger: true,
                popConfirm: {
                  title: `确认删除任务'${row.jobName}'吗？`,
                  confirm: () => onDeleteJob(row),
                },
              },
            ]"
          />
        </template>
      </JobGrid>
    </div>

    <div v-if="activeTab === 'logs'" class="min-h-0 flex-1">
      <LogGrid class="h-full" table-title="调度日志列表">
        <template #toolbar-tools>
          <Button class="mr-2" @click="onExportLogs">导出</Button>
          <Button danger class="mr-2" @click="onBatchDeleteLogs">
            <IconifyIcon icon="lucide:trash-2" class="size-5" />
            删除
          </Button>
          <Button danger @click="onCleanLogs">清空</Button>
        </template>
        <template #action="{ row }">
          <VbenTableAction
            :actions="[
              { text: '详情', icon: 'lucide:eye', onClick: () => onViewLog(row) },
            ]"
            :dropdown-actions="[
              {
                text: '删除',
                icon: 'lucide:trash-2',
                danger: true,
                popConfirm: {
                  title: '确认删除该条日志吗？',
                  confirm: () => onDeleteLog(row),
                },
              },
            ]"
          />
        </template>
      </LogGrid>
    </div>
  </Page>
</template>
