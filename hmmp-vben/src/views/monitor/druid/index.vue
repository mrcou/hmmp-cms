<script lang="ts" setup>
import { Page } from '@vben/common-ui';
import { ExternalLink } from '@vben/icons';

import { Button, Result } from 'antdv-next';

defineOptions({ name: 'Druid' });

const url = 'http://localhost:8080/druid/login.html';

function open() {
  window.open(url, '_blank');
}
</script>

<template>
  <Page auto-content-height title="数据监控">
    <Result title="Druid 数据源监控" sub-title="出于浏览器同源安全限制，监控页面无法内嵌显示，请在新标签页打开查看（默认账号密码见 application-druid.yml）">
      <template #extra>
        <Button type="primary" @click="open">
          <ExternalLink class="size-4" />
          在新标签页打开
        </Button>
      </template>
    </Result>
  </Page>
</template>
