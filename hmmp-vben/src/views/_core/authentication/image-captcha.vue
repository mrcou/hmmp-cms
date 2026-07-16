<script lang="ts" setup>
import { computed, onMounted, ref } from 'vue';

import { Input } from 'antdv-next';

import { useCaptcha } from './use-captcha';

const props = defineProps<{
  modelValue?: string;
}>();
const emit = defineEmits<{
  'update:modelValue': [string];
}>();

const { refreshCaptcha, state } = useCaptcha();
const fallbackCode = ref(createFallbackCode());
const fallbackVariant = ref(0);
const visibleCaptchaImage = ref(createFallbackImage());

const inputPlaceholder = computed(() =>
  state.enabled ? '请输入验证码' : '开发阶段可不填',
);
const captchaTitle = computed(() =>
  state.img ? '点击刷新验证码' : '点击变换验证码',
);

function createFallbackCode() {
  const chars = '23456789ABCDEFGHJKLMNPQRSTUVWXYZ';
  return Array.from({ length: 4 }, () =>
    chars.charAt(Math.floor(Math.random() * chars.length)),
  ).join('');
}

function createFallbackImage(code = fallbackCode.value) {
  const variant = fallbackVariant.value;
  const hue = (variant * 53) % 360;
  const bg = `hsl(${hue}, 82%, 95%)`;
  const stroke = `hsl(${(hue + 42) % 360}, 72%, 64%)`;
  const text = `hsl(${(hue + 220) % 360}, 68%, 42%)`;
  const wave = 20 + (variant % 8);
  const dots = Array.from({ length: 12 }, (_, index) => {
    const x = 8 + ((index * 31 + variant * 11) % 102);
    const y = 7 + ((index * 17 + variant * 5) % 30);
    return `<circle cx="${x}" cy="${y}" r="1.4" fill="${stroke}" opacity="0.5"/>`;
  }).join('');
  const svg = `
    <svg xmlns="http://www.w3.org/2000/svg" width="118" height="44" viewBox="0 0 118 44">
      <rect width="118" height="44" rx="6" fill="${bg}"/>
      <path d="M0 ${wave} C18 ${wave - 11}, 35 ${wave + 14}, 53 ${wave} S88 ${wave - 10}, 118 ${wave + 3}" fill="none" stroke="${stroke}" stroke-width="2" opacity="0.75"/>
      <path d="M4 35 L114 9" stroke="${stroke}" stroke-width="1.4" opacity="0.32"/>
      ${dots}
      <text x="59" y="29" text-anchor="middle" font-family="Arial, Microsoft YaHei, sans-serif" font-size="19" font-weight="700" fill="${text}" letter-spacing="2">${code}</text>
    </svg>
  `;
  return `data:image/svg+xml;charset=utf-8,${encodeURIComponent(svg)}`;
}

async function handleRefreshCaptcha() {
  fallbackCode.value = createFallbackCode();
  fallbackVariant.value += 1;
  visibleCaptchaImage.value = createFallbackImage();
  await refreshCaptcha();
  if (state.img) {
    visibleCaptchaImage.value = state.img;
  }
}

onMounted(() => {
  if (state.img) {
    visibleCaptchaImage.value = state.img;
    return;
  }
  handleRefreshCaptcha();
});
</script>

<template>
  <div class="hmmp-captcha">
    <Input
      :value="props.modelValue"
      autocomplete="off"
      :placeholder="inputPlaceholder"
      @update:value="(val: string) => emit('update:modelValue', val)"
    />
    <button
      type="button"
      class="hmmp-captcha-trigger"
      :title="captchaTitle"
      @click="handleRefreshCaptcha"
    >
      <img
        :src="visibleCaptchaImage"
        alt="验证码"
        class="hmmp-captcha-image"
      />
    </button>
  </div>
</template>

<style scoped>
.hmmp-captcha {
  display: flex;
  width: 100%;
  align-items: center;
  gap: 12px;
}

.hmmp-captcha :deep(.ant-input) {
  flex: 1 1 auto;
  min-width: 0;
}

.hmmp-captcha-trigger {
  display: block;
  width: 118px;
  height: 44px;
  flex: 0 0 auto;
  overflow: hidden;
  padding: 0;
  cursor: pointer;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  background: #eef2ff;
  color: #667eea;
  font-size: 12px;
  font-weight: 600;
  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;
}

.hmmp-captcha-trigger:hover {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgb(102 126 234 / 10%);
}

.hmmp-captcha-image {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

@media (max-width: 480px) {
  .hmmp-captcha {
    align-items: stretch;
    flex-direction: column;
  }

  .hmmp-captcha-trigger {
    width: 100%;
  }
}
</style>
