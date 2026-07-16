<script lang="ts" setup>
import type { VbenFormSchema } from '@vben/common-ui';

import { computed, markRaw, onMounted } from 'vue';

import { AuthenticationLogin, z } from '@vben/common-ui';
import { $t } from '@vben/locales';

import { useAuthStore } from '#/store';

import ImageCaptcha from './image-captcha.vue';
import { useCaptcha } from './use-captcha';

defineOptions({ name: 'Login' });

const authStore = useAuthStore();
const { refreshCaptcha, state: captchaState } = useCaptcha();
const isDev = import.meta.env.DEV;

onMounted(() => {
  refreshCaptcha();
});

const captchaRules = computed(() =>
  captchaState.enabled && !isDev
    ? z.string().min(1, { message: '请输入验证码' })
    : z.string().optional(),
);

const formSchema = computed(
  (): VbenFormSchema[] => [
    {
      component: 'VbenInput',
      componentProps: {
        placeholder: $t('authentication.usernameTip'),
      },
      fieldName: 'username',
      formItemClass: 'hmmp-login-field',
      label: $t('authentication.username'),
      rules: z.string().min(1, { message: $t('authentication.usernameTip') }),
    },
    {
      component: 'VbenInputPassword',
      componentProps: {
        placeholder: $t('authentication.password'),
      },
      fieldName: 'password',
      formItemClass: 'hmmp-login-field',
      label: $t('authentication.password'),
      rules: z.string().min(1, { message: $t('authentication.passwordTip') }),
    },
    {
      component: markRaw(ImageCaptcha),
      fieldName: 'code',
      formItemClass: 'hmmp-login-field hmmp-login-captcha-field',
      label: '验证码',
      rules: captchaRules.value,
    },
  ],
);

function onSubmit(values: Record<string, any>) {
  authStore.authLogin(values).catch(() => {
    refreshCaptcha();
  });
}
</script>

<template>
  <AuthenticationLogin
    class="hmmp-login-form-view"
    :form-schema="formSchema"
    :loading="authStore.loginLoading"
    submit-button-text="登 录"
    @submit="onSubmit"
  >
    <template #title>
      <div class="hmmp-login-heading">
        <h2>欢迎使用</h2>
        <p>请输入您的账号信息</p>
      </div>
    </template>
  </AuthenticationLogin>
</template>

<style scoped>
.hmmp-login-heading {
  margin-bottom: 34px;
}

.hmmp-login-form-view {
  width: min(100%, 382px);
  margin: 0 auto;
}

.hmmp-login-form-view :deep(form),
.hmmp-login-form-view :deep(form > div),
.hmmp-login-form-view :deep(.hmmp-login-field) {
  width: 100%;
  max-width: 382px;
}

.hmmp-login-form-view :deep(form > div) {
  display: flex;
  flex-direction: column;
}

.hmmp-login-heading h2 {
  margin: 0;
  color: #1e293b;
  font-size: 24px;
  font-weight: 800;
  line-height: 1.35;
  letter-spacing: 0;
}

.hmmp-login-heading p {
  margin: 8px 0 0;
  color: #8a8f99;
  font-size: 14px;
  line-height: 1.6;
}

.hmmp-login-form-view :deep(input) {
  min-height: 44px;
  border-color: #e2e8f0;
  border-radius: 6px;
  background: #fafafa;
  color: #334155;
  font-size: 14px;
  transition:
    border-color 0.2s ease,
    background-color 0.2s ease,
    box-shadow 0.2s ease;
}

.hmmp-login-form-view :deep(input:focus) {
  border-color: #667eea;
  background: #fff;
  box-shadow: 0 0 0 3px rgb(102 126 234 / 12%);
}

.hmmp-login-form-view :deep(input::placeholder) {
  color: #b3b7c0;
}

.hmmp-login-form-view :deep(button[aria-label='login']) {
  min-height: 52px;
  margin-top: 2px;
  border: 0;
  border-radius: 6px;
  background: linear-gradient(135deg, #6f82ea 0%, #7650a7 100%);
  color: #fff;
  font-size: 16px;
  font-weight: 700;
  letter-spacing: 0;
  box-shadow: 0 12px 24px rgb(102 126 234 / 26%);
}

.hmmp-login-form-view :deep(button[aria-label='login']:hover) {
  transform: translateY(-1px);
  box-shadow: 0 16px 28px rgb(102 126 234 / 32%);
}

.hmmp-login-form-view :deep(button[aria-label='login']:active) {
  transform: translateY(0);
}

.hmmp-login-form-view :deep(label) {
  color: #606975;
  font-size: 14px;
}

.hmmp-login-form-view :deep(.mb-6) {
  margin-bottom: 18px;
}

.hmmp-login-form-view :deep(.hmmp-login-field) {
  margin-bottom: 15px;
}
</style>
