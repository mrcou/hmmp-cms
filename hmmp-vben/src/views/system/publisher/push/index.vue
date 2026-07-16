<template>
  <div class="publisher-push-container">
    <a-card title="精准推送管理" :bordered="false">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="subscriber" tab="订阅用户管理" />
        <a-tab-pane key="keyword" tab="关键词推送" />
      </a-tabs>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const activeTab = ref('subscriber');
</script>