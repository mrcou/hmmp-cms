<script lang="ts" setup>
/**
 * 新分配 —— 稿件处理列表
 */
import ManuscriptHandleGrid from '../_components/manuscript-handle-grid.vue';

defineOptions({ name: 'ReviewerNewAssign' });
</script>

<template>
  <ManuscriptHandleGrid list-type="assign" />
</template>
