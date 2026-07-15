<script lang="ts" setup>
import type { MonitorServerApi } from '#/api/monitor/server';

import { onMounted, ref } from 'vue';

import { Page } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';

import {
  Button,
  Card,
  Descriptions,
  DescriptionsItem,
  Progress,
  Spin,
} from 'antdv-next';

import { getServerInfo } from '#/api/monitor/server';

const loading = ref(false);
const server = ref<MonitorServerApi.Server>();

function usageStatus(usage: number) {
  if (usage >= 90) return 'exception';
  if (usage >= 70) return 'active';
  return 'success';
}

async function loadServerInfo() {
  loading.value = true;
  try {
    const res = await getServerInfo();
    server.value = res.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  loadServerInfo();
});
</script>

<template>
  <Page auto-content-height title="服务监控">
    <Spin :spinning="loading">
      <div class="mb-4 flex justify-end">
        <Button @click="loadServerInfo">
          <IconifyIcon icon="lucide:refresh-cw" class="size-4" />
          刷新
        </Button>
      </div>

      <div v-if="server" class="grid grid-cols-1 gap-4 lg:grid-cols-2">
        <Card title="CPU">
          <Descriptions :column="2" size="small">
            <DescriptionsItem label="核心数">{{ server.cpu.cpuNum }}</DescriptionsItem>
            <DescriptionsItem label="用户使用率">{{ server.cpu.used }}%</DescriptionsItem>
            <DescriptionsItem label="系统使用率">{{ server.cpu.sys }}%</DescriptionsItem>
            <DescriptionsItem label="当前等待率">{{ server.cpu.wait }}%</DescriptionsItem>
            <DescriptionsItem label="当前空闲率">{{ server.cpu.free }}%</DescriptionsItem>
          </Descriptions>
          <Progress
            :percent="server.cpu.used"
            :status="usageStatus(server.cpu.used)"
          />
        </Card>

        <Card title="内存">
          <Descriptions :column="2" size="small">
            <DescriptionsItem label="总内存">{{ server.mem.total }}GB</DescriptionsItem>
            <DescriptionsItem label="已用内存">{{ server.mem.used }}GB</DescriptionsItem>
            <DescriptionsItem label="剩余内存">{{ server.mem.free }}GB</DescriptionsItem>
            <DescriptionsItem label="使用率">{{ server.mem.usage }}%</DescriptionsItem>
          </Descriptions>
          <Progress
            :percent="server.mem.usage"
            :status="usageStatus(server.mem.usage)"
          />
        </Card>

        <Card title="服务器信息">
          <Descriptions :column="1" size="small">
            <DescriptionsItem label="服务器名称">{{ server.sys.computerName }}</DescriptionsItem>
            <DescriptionsItem label="服务器IP">{{ server.sys.computerIp }}</DescriptionsItem>
            <DescriptionsItem label="操作系统">{{ server.sys.osName }}</DescriptionsItem>
            <DescriptionsItem label="系统架构">{{ server.sys.osArch }}</DescriptionsItem>
            <DescriptionsItem label="项目路径">{{ server.sys.userDir }}</DescriptionsItem>
          </Descriptions>
        </Card>

        <Card title="Java虚拟机">
          <Descriptions :column="1" size="small">
            <DescriptionsItem label="JDK版本">{{ server.jvm.version }}</DescriptionsItem>
            <DescriptionsItem label="JDK路径">{{ server.jvm.home }}</DescriptionsItem>
            <DescriptionsItem label="启动时间">{{ server.jvm.startTime }}</DescriptionsItem>
            <DescriptionsItem label="运行时长">{{ server.jvm.runTime }}</DescriptionsItem>
            <DescriptionsItem label="总内存">{{ server.jvm.total }}MB</DescriptionsItem>
            <DescriptionsItem label="已用内存">{{ server.jvm.used }}MB</DescriptionsItem>
            <DescriptionsItem label="剩余内存">{{ server.jvm.free }}MB</DescriptionsItem>
            <DescriptionsItem label="最大可用内存">{{ server.jvm.max }}MB</DescriptionsItem>
            <DescriptionsItem label="运行参数" :span="1">
              <div class="max-h-24 overflow-auto whitespace-pre-wrap break-all">
                {{ server.jvm.inputArgs }}
              </div>
            </DescriptionsItem>
          </Descriptions>
          <Progress
            :percent="server.jvm.usage"
            :status="usageStatus(server.jvm.usage)"
          />
        </Card>

        <Card title="磁盘状态" class="lg:col-span-2">
          <div class="overflow-x-auto">
            <table class="w-full text-sm">
              <thead>
                <tr class="border-b text-left text-gray-500">
                  <th class="p-2">盘符路径</th>
                  <th class="p-2">文件系统</th>
                  <th class="p-2">盘符类型</th>
                  <th class="p-2">总大小</th>
                  <th class="p-2">已用大小</th>
                  <th class="p-2">剩余大小</th>
                  <th class="p-2">使用率</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="file in server.sysFiles"
                  :key="file.dirName"
                  class="border-b last:border-b-0"
                >
                  <td class="p-2">{{ file.dirName }}</td>
                  <td class="p-2">{{ file.sysTypeName }}</td>
                  <td class="p-2">{{ file.typeName }}</td>
                  <td class="p-2">{{ file.total }}</td>
                  <td class="p-2">{{ file.used }}</td>
                  <td class="p-2">{{ file.free }}</td>
                  <td class="w-40 p-2">
                    <Progress
                      :percent="file.usage"
                      :status="usageStatus(file.usage)"
                      size="small"
                    />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </Card>
      </div>
    </Spin>
  </Page>
</template>
