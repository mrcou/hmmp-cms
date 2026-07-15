<script lang="ts" setup>
import type { MonitorOperlogApi } from '#/api/monitor/operlog';

import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { Descriptions, DescriptionsItem, Tag } from 'antdv-next';

import { BUSINESS_TYPE_OPTIONS, STATUS_OPTIONS } from '../data';

const detail = ref<MonitorOperlogApi.SysOperLog>({});

const [Modal, modalApi] = useVbenModal({
  showCancelButton: false,
  showConfirmButton: false,
  onOpenChange(isOpen) {
    if (!isOpen) return;
    detail.value = modalApi.getData<MonitorOperlogApi.SysOperLog>() ?? {};
  },
});

function findOption(
  options: { color: string; label: string; value: number }[],
  value?: number,
) {
  return options.find((item) => item.value === value);
}
</script>

<template>
  <Modal title="操作日志详情" class="w-[700px]">
    <Descriptions :column="2" bordered size="small">
      <DescriptionsItem label="日志编号">
        {{ detail.operId }}
      </DescriptionsItem>
      <DescriptionsItem label="系统模块">
        {{ detail.title }}
      </DescriptionsItem>
      <DescriptionsItem label="操作类型">
        <Tag :color="findOption(BUSINESS_TYPE_OPTIONS, detail.businessType)?.color">
          {{ findOption(BUSINESS_TYPE_OPTIONS, detail.businessType)?.label }}
        </Tag>
      </DescriptionsItem>
      <DescriptionsItem label="操作状态">
        <Tag :color="findOption(STATUS_OPTIONS, detail.status)?.color">
          {{ findOption(STATUS_OPTIONS, detail.status)?.label }}
        </Tag>
      </DescriptionsItem>
      <DescriptionsItem label="请求方式">
        {{ detail.requestMethod }}
      </DescriptionsItem>
      <DescriptionsItem label="操作人员">
        {{ detail.operName }}
        <template v-if="detail.deptName">（{{ detail.deptName }}）</template>
      </DescriptionsItem>
      <DescriptionsItem label="主机地址" :span="2">
        {{ detail.operIp }}
      </DescriptionsItem>
      <DescriptionsItem label="操作地点" :span="2">
        {{ detail.operLocation }}
      </DescriptionsItem>
      <DescriptionsItem label="请求方法" :span="2">
        {{ detail.method }}
      </DescriptionsItem>
      <DescriptionsItem label="请求地址" :span="2">
        {{ detail.operUrl }}
      </DescriptionsItem>
      <DescriptionsItem label="请求参数" :span="2">
        <div class="max-h-40 overflow-auto whitespace-pre-wrap break-all">
          {{ detail.operParam }}
        </div>
      </DescriptionsItem>
      <DescriptionsItem label="返回参数" :span="2">
        <div class="max-h-40 overflow-auto whitespace-pre-wrap break-all">
          {{ detail.jsonResult }}
        </div>
      </DescriptionsItem>
      <DescriptionsItem v-if="detail.errorMsg" label="错误消息" :span="2">
        <div class="max-h-40 overflow-auto whitespace-pre-wrap break-all text-red-500">
          {{ detail.errorMsg }}
        </div>
      </DescriptionsItem>
      <DescriptionsItem label="操作日期">
        {{ detail.operTime }}
      </DescriptionsItem>
      <DescriptionsItem label="消耗时间">
        {{ detail.costTime }}毫秒
      </DescriptionsItem>
    </Descriptions>
  </Modal>
</template>
