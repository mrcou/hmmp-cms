<script lang="ts" setup>
import type { MonitorJobApi } from '#/api/monitor/job';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { Tag } from 'antdv-next';

import { getJob, getJobLog } from '#/api/monitor/job';

import {
  CONCURRENT_TAG_OPTIONS,
  JOB_GROUP_OPTIONS,
  JOB_LOG_STATUS_OPTIONS,
  JOB_STATUS_TAG_OPTIONS,
  MISFIRE_POLICY_OPTIONS,
} from '../data';

type DetailPayload =
  | { jobId: number; type: 'job' }
  | { jobLogId: number; type: 'log' };

const detailType = ref<DetailPayload['type']>('job');
const job = ref<MonitorJobApi.SysJob>();
const jobLog = ref<MonitorJobApi.SysJobLog>();

const [Modal, modalApi] = useVbenModal({
  footer: false,
  async onOpenChange(isOpen) {
    if (!isOpen) return;

    const data = modalApi.getData<DetailPayload>();
    detailType.value = data.type;
    job.value = undefined;
    jobLog.value = undefined;
    modalApi.lock();
    try {
      if (data.type === 'job') {
        const res = await getJob(data.jobId);
        job.value = res.data;
      } else {
        const res = await getJobLog(data.jobLogId);
        jobLog.value = res.data;
      }
    } finally {
      modalApi.unlock();
    }
  },
});

const title = computed(() =>
  detailType.value === 'job' ? '任务详情' : '调度日志详情',
);

function optionLabel(
  options: Array<{ label: string; value: string }>,
  value?: string,
) {
  return options.find((item) => item.value === value)?.label ?? value ?? '-';
}

function optionColor(
  options: Array<{ color?: string; value: string }>,
  value?: string,
) {
  return options.find((item) => item.value === value)?.color ?? 'default';
}
</script>

<template>
  <Modal :title="title" class="w-[760px]">
    <dl
      v-if="detailType === 'job' && job"
      class="grid grid-cols-[120px_1fr] gap-x-4 gap-y-3 text-sm"
    >
      <dt class="text-foreground/60">任务编号</dt>
      <dd>{{ job.jobId }}</dd>
      <dt class="text-foreground/60">任务名称</dt>
      <dd>{{ job.jobName }}</dd>
      <dt class="text-foreground/60">任务分组</dt>
      <dd>
        <Tag>{{ optionLabel(JOB_GROUP_OPTIONS, job.jobGroup) }}</Tag>
      </dd>
      <dt class="text-foreground/60">调用目标</dt>
      <dd class="break-all font-mono text-xs">{{ job.invokeTarget }}</dd>
      <dt class="text-foreground/60">Cron表达式</dt>
      <dd class="font-mono text-xs">{{ job.cronExpression }}</dd>
      <dt class="text-foreground/60">下次执行时间</dt>
      <dd>{{ job.nextValidTime || '-' }}</dd>
      <dt class="text-foreground/60">执行策略</dt>
      <dd>{{ optionLabel(MISFIRE_POLICY_OPTIONS, job.misfirePolicy) }}</dd>
      <dt class="text-foreground/60">并发执行</dt>
      <dd>
        <Tag :color="optionColor(CONCURRENT_TAG_OPTIONS, job.concurrent)">
          {{ optionLabel(CONCURRENT_TAG_OPTIONS, job.concurrent) }}
        </Tag>
      </dd>
      <dt class="text-foreground/60">任务状态</dt>
      <dd>
        <Tag :color="optionColor(JOB_STATUS_TAG_OPTIONS, job.status)">
          {{ optionLabel(JOB_STATUS_TAG_OPTIONS, job.status) }}
        </Tag>
      </dd>
      <dt class="text-foreground/60">备注</dt>
      <dd class="break-all">{{ job.remark || '-' }}</dd>
      <dt class="text-foreground/60">创建时间</dt>
      <dd>{{ job.createTime || '-' }}</dd>
      <dt class="text-foreground/60">更新时间</dt>
      <dd>{{ job.updateTime || '-' }}</dd>
    </dl>

    <dl
      v-else-if="detailType === 'log' && jobLog"
      class="grid grid-cols-[120px_1fr] gap-x-4 gap-y-3 text-sm"
    >
      <dt class="text-foreground/60">日志编号</dt>
      <dd>{{ jobLog.jobLogId }}</dd>
      <dt class="text-foreground/60">任务名称</dt>
      <dd>{{ jobLog.jobName }}</dd>
      <dt class="text-foreground/60">任务分组</dt>
      <dd>
        <Tag>{{ optionLabel(JOB_GROUP_OPTIONS, jobLog.jobGroup) }}</Tag>
      </dd>
      <dt class="text-foreground/60">调用目标</dt>
      <dd class="break-all font-mono text-xs">{{ jobLog.invokeTarget }}</dd>
      <dt class="text-foreground/60">执行状态</dt>
      <dd>
        <Tag :color="optionColor(JOB_LOG_STATUS_OPTIONS, jobLog.status)">
          {{ optionLabel(JOB_LOG_STATUS_OPTIONS, jobLog.status) }}
        </Tag>
      </dd>
      <dt class="text-foreground/60">日志信息</dt>
      <dd class="break-all">{{ jobLog.jobMessage || '-' }}</dd>
      <dt class="text-foreground/60">异常信息</dt>
      <dd class="max-h-48 overflow-auto whitespace-pre-wrap break-all">
        {{ jobLog.exceptionInfo || '-' }}
      </dd>
      <dt class="text-foreground/60">开始时间</dt>
      <dd>{{ jobLog.startTime || '-' }}</dd>
      <dt class="text-foreground/60">结束时间</dt>
      <dd>{{ jobLog.endTime || '-' }}</dd>
      <dt class="text-foreground/60">创建时间</dt>
      <dd>{{ jobLog.createTime || '-' }}</dd>
    </dl>
  </Modal>
</template>
