<template>
  <div class="publisher-issue-container">
    <a-card title="网刊数据管理" :bordered="false">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="year" tab="年份管理" />
        <a-tab-pane key="period" tab="刊期管理" />
        <a-tab-pane key="column" tab="栏目管理" />
        <a-tab-pane key="article" tab="文章管理" />
        <a-tab-pane key="comment" tab="文章评论" />
        <a-tab-pane key="album" tab="虚拟专辑" />
        <a-tab-pane key="cnki" tab="知网核对" />
      </a-tabs>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const activeTab = ref('year');
</script>