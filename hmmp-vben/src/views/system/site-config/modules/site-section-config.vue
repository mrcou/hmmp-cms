<script setup lang="ts">
import { computed, onMounted, watch } from 'vue';

import { Button, message } from 'antdv-next';

import { useVbenForm } from '#/adapter/form';
import {
  getSectionConfig,
  saveSectionConfig,
} from '#/api/system/site-config';

import { SITE_CONFIG_SECTION_SCHEMAS } from '../schemas';

const props = defineProps<{
  section: string;
}>();

const schemaFactory = computed(
  () => SITE_CONFIG_SECTION_SCHEMAS[props.section],
);

const [Form, formApi] = useVbenForm({
  commonConfig: {
    labelWidth: 220,
  },
  schema: [],
  showDefaultActions: false,
  wrapperClass: 'grid-cols-1 md:grid-cols-2 xl:max-w-6xl',
});

async function initForm() {
  const factory = schemaFactory.value;
  if (!factory) {
    return;
  }
  formApi.setState({ schema: factory() });
  await loadConfig();
}

async function loadConfig() {
  const res = await getSectionConfig(props.section);
  formApi.setValues(res.data ?? {});
}

async function handleSave() {
  const { valid } = await formApi.validate();
  if (!valid) {
    return;
  }
  const values = await formApi.getValues();
  const payload = { ...values };
  for (const key of Object.keys(payload)) {
    if (key.startsWith('divider_')) {
      delete payload[key];
    }
  }
  await saveSectionConfig(props.section, payload);
  message.success('保存成功');
}

onMounted(initForm);

watch(
  () => props.section,
  () => {
    initForm();
  },
);
</script>

<template>
  <div class="site-section-config">
    <Form />
    <div class="mt-6 flex gap-3 xl:max-w-6xl">
      <Button type="primary" @click="handleSave">保存</Button>
    </div>
  </div>
</template>

<style scoped>
.site-section-config :deep(.site-config-select) {
  width: 100%;
}

.site-section-config :deep(.site-config-select .ant-select-selector) {
  height: auto;
  min-height: 32px;
}

.site-section-config :deep(.site-config-select .ant-select-selection-item) {
  line-height: 1.5;
  white-space: normal;
  word-break: break-word;
}
</style>
