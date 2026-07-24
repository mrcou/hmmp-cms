<script setup lang="ts">
import type { Component } from 'vue';

import { computed } from 'vue';
import { useRoute } from 'vue-router';

import { Page } from '@vben/common-ui';

import { Card, Empty } from 'antdv-next';

import { SITE_GROUP_LEAF_TITLES } from './data';
import BasicConfig from './modules/basic-config.vue';
import ExtendConfig from './modules/extend-config.vue';

const route = useRoute();

const leafComponentMap: Record<string, Component> = {
  basic: BasicConfig,
  extend: ExtendConfig,
};

const currentLeaf = computed(
  () => route.path.split('/').filter(Boolean).pop() ?? 'basic',
);

const pageTitle = computed(
  () => SITE_GROUP_LEAF_TITLES[currentLeaf.value] ?? '站群设置',
);
</script>

<template>
  <Page auto-content-height :title="pageTitle">
    <Card :bordered="false">
      <component
        :is="leafComponentMap[currentLeaf]"
        v-if="leafComponentMap[currentLeaf]"
      />
      <div v-else class="flex min-h-[320px] items-center justify-center">
        <Empty description="页面不存在" />
      </div>
    </Card>
  </Page>
</template>
