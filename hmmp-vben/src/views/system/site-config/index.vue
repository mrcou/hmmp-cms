<script setup lang="ts">
import type { Component } from 'vue';

import { computed } from 'vue';
import { useRoute } from 'vue-router';

import { Page } from '@vben/common-ui';

import { Card, Empty } from 'antdv-next';

import { SITE_CONFIG_LEAF_TITLES } from './data';
import SecurityConfig from './modules/security-config.vue';
import SiteSectionConfig from './modules/site-section-config.vue';

const route = useRoute();

/** 已实现的分区配置 leaf */
const SECTION_LEAVES = new Set([
  'journal-info',
  'mail-info',
  'timed-task',
  'cost-processing',
  'author-config',
  'editorial-config',
  'reviewer-config',
  'reader-config',
  'external-interface',
  'combining-articles',
  'other-config',
]);

const leafComponentMap: Record<string, Component> = {
  security: SecurityConfig,
};

const currentLeaf = computed(
  () => route.path.split('/').filter(Boolean).pop() ?? 'security',
);

const pageTitle = computed(
  () => SITE_CONFIG_LEAF_TITLES[currentLeaf.value] ?? '站点配置',
);

const isSectionLeaf = computed(() => SECTION_LEAVES.has(currentLeaf.value));
</script>

<template>
  <Page auto-content-height :title="pageTitle">
    <Card :bordered="false">
      <SiteSectionConfig
        v-if="isSectionLeaf"
        :section="currentLeaf"
      />
      <component
        :is="leafComponentMap[currentLeaf]"
        v-else-if="leafComponentMap[currentLeaf]"
      />
      <div v-else class="flex min-h-[320px] items-center justify-center">
        <Empty description="页面不存在" />
      </div>
    </Card>
  </Page>
</template>
