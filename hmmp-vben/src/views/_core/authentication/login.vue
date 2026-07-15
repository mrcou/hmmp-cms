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
const { refreshCaptcha, state } = useCaptcha();

onMounted(() => {
  refreshCaptcha();
});

const formSchema = computed((): VbenFormSchema[] => {
  const schema: VbenFormSchema[] = [
    {
      component: 'VbenInput',
      componentProps: {
        placeholder: $t('authentication.usernameTip'),
      },
      fieldName: 'username',
      label: $t('authentication.username'),
      rules: z.string().min(1, { message: $t('authentication.usernameTip') }),
    },
    {
      component: 'VbenInputPassword',
      componentProps: {
        placeholder: $t('authentication.password'),
      },
      fieldName: 'password',
      label: $t('authentication.password'),
      rules: z.string().min(1, { message: $t('authentication.passwordTip') }),
    },
  ];
  if (state.enabled) {
    schema.push({
      component: markRaw(ImageCaptcha),
      fieldName: 'code',
      label: '验证码',
      rules: z.string().min(1, { message: '请输入验证码' }),
    });
  }
  return schema;
});

function onSubmit(values: Record<string, any>) {
  authStore.authLogin(values).catch(() => {
    refreshCaptcha();
  });
}
</script>

<template>
  <AuthenticationLogin
    :form-schema="formSchema"
    :loading="authStore.loginLoading"
    @submit="onSubmit"
  />
</template>
