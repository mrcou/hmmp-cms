<script lang="ts" setup>
import { onMounted } from 'vue';

import { Input } from 'antdv-next';

import { useCaptcha } from './use-captcha';

defineProps<{
  value?: string;
}>();
const emit = defineEmits<{
  'update:value': [string];
}>();

const { refreshCaptcha, state } = useCaptcha();

onMounted(() => {
  if (!state.img) {
    refreshCaptcha();
  }
});
</script>

<template>
  <div class="flex items-center gap-2">
    <Input
      :value="value"
      placeholder="请输入验证码"
      @update:value="(val: string) => emit('update:value', val)"
    />
    <img
      v-if="state.img"
      :src="state.img"
      class="h-10 shrink-0 cursor-pointer rounded border"
      title="点击刷新验证码"
      @click="refreshCaptcha"
    />
  </div>
</template>
