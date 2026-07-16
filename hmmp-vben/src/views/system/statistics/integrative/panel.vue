<script lang="ts" setup>
/**
 * 综合统计子页占位：按路由 meta.title 展示，后续按 path 拆分具体图表。
 */
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const title = computed(() => String(route.meta?.title ?? '综合统计'));
</script>

<template>
  <div class="p-4">
    <a-card :title="title" :bordered="false">
      <a-empty :description="`${title}功能开发中`" />
    </a-card>
  </div>
</template>
