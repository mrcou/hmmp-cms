<script lang="ts" setup>
/**
 * 新收稿 —— 稿件处理列表
 */
import ManuscriptHandleGrid from '../_components/manuscript-handle-grid.vue';

defineOptions({ name: 'ReviewerNewReceipt' });
</script>

<template>
  <ManuscriptHandleGrid list-type="new" />
</template>
