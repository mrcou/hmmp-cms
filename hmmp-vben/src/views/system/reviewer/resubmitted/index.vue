<script lang="ts" setup>
/**
 * 已修回 —— 稿件处理列表
 */
import ManuscriptHandleGrid from '../_components/manuscript-handle-grid.vue';

defineOptions({ name: 'ReviewerResubmitted' });
</script>

<template>
  <ManuscriptHandleGrid list-type="resubmitted" />
</template>
