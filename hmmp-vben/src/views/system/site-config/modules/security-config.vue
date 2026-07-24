<script setup lang="ts">
import { onMounted } from 'vue';

import { Button, message } from 'antdv-next';

import { useVbenForm } from '#/adapter/form';
import {
  getSecurityConfig,
  saveSecurityConfig,
} from '#/api/system/site-config';

import { useSecurityFormSchema } from '../data';

const [Form, formApi] = useVbenForm({
  commonConfig: {
    labelWidth: 180,
  },
  schema: useSecurityFormSchema(),
  showDefaultActions: false,
  wrapperClass: 'grid-cols-1 md:grid-cols-2 lg:max-w-4xl',
});

async function loadConfig() {
  const res = await getSecurityConfig();
  formApi.setValues(res.data);
}

async function handleSave() {
  const { valid } = await formApi.validate();
  if (!valid) {
    return;
  }
  const values = await formApi.getValues();
  await saveSecurityConfig(values);
  message.success('保存成功');
}

onMounted(loadConfig);
</script>

<template>
  <div class="site-security-config">
    <Form />
    <div class="mt-6 flex gap-3 lg:max-w-4xl">
      <Button type="primary" @click="handleSave">保存</Button>
    </div>
  </div>
</template>

<style scoped>
.site-security-config :deep(.site-config-select) {
  width: 100%;
}

.site-security-config :deep(.site-config-select .ant-select-selector) {
  height: auto;
  min-height: 32px;
}

.site-security-config :deep(.site-config-select .ant-select-selection-item) {
  line-height: 1.5;
  white-space: normal;
  word-break: break-word;
}
</style>

<style>
.site-config-select-popup {
  min-width: unset !important;
  width: max-content !important;
}
</style>
