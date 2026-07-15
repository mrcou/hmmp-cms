<script lang="ts" setup>
import { Page } from '@vben/common-ui';
import { ExternalLink } from '@vben/icons';

import { Button, Result } from 'antdv-next';

defineOptions({ name: 'Swagger' });

const url = 'http://localhost:8080/swagger-ui.html';

function open() {
  window.open(url, '_blank');
}
</script>

<template>
  <Page auto-content-height title="系统接口">
    <Result title="系统接口文档" sub-title="出于浏览器同源安全限制，接口文档无法内嵌显示，请在新标签页打开查看">
      <template #extra>
        <Button type="primary" @click="open">
          <ExternalLink class="size-4" />
          在新标签页打开
        </Button>
      </template>
    </Result>
  </Page>
</template>
