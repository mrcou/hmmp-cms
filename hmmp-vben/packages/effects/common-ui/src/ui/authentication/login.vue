<script setup lang="ts">
import type { Recordable } from '@vben/types';

import type { VbenFormSchema } from '@vben-core/form-ui';

import type { AuthenticationProps } from './types';

import { computed, onMounted, reactive, ref } from 'vue';

import { $t } from '@vben/locales';

import { useVbenForm } from '@vben-core/form-ui';
import { VbenButton, VbenCheckbox } from '@vben-core/shadcn-ui';

import Title from './auth-title.vue';

interface Props extends AuthenticationProps {
  formSchema?: VbenFormSchema[];
}

defineOptions({
  name: 'AuthenticationLogin',
});

const props = withDefaults(defineProps<Props>(), {
  formSchema: () => [],
  loading: false,
  showRememberMe: true,
  submitButtonText: '',
  title: '',
});

const emit = defineEmits<{
  submit: [Recordable<any>];
}>();

const [Form, formApi] = useVbenForm(
  reactive({
    commonConfig: {
      hideLabel: true,
      hideRequiredMark: true,
    },
    schema: computed(() => props.formSchema),
    showDefaultActions: false,
  }),
);

const REMEMBER_ME_KEY = `REMEMBER_ME_USERNAME_${location.hostname}`;

const localUsername = localStorage.getItem(REMEMBER_ME_KEY) || '';

const rememberMe = ref(!!localUsername);

async function handleSubmit() {
  const { valid } = await formApi.validate();
  const values = await formApi.getValues();
  if (valid) {
    localStorage.setItem(
      REMEMBER_ME_KEY,
      rememberMe.value ? values?.username : '',
    );
    emit('submit', values);
  }
}

onMounted(() => {
  if (localUsername) {
    formApi.setFieldValue('username', localUsername);
  }
});

defineExpose({
  getFormApi: () => formApi,
});
</script>

<template>
  <div @keydown.enter.prevent="handleSubmit">
    <slot name="title">
      <Title>
        <slot name="title">
          {{ title || $t('common.login') }}
        </slot>
      </Title>
    </slot>

    <Form />

    <div v-if="showRememberMe" class="mb-6 flex justify-between">
      <div class="flex-center">
        <VbenCheckbox v-model="rememberMe" name="rememberMe">
          {{ $t('authentication.rememberMe') }}
        </VbenCheckbox>
      </div>
    </div>
    <VbenButton
      :class="{
        'cursor-wait': loading,
      }"
      :loading="loading"
      aria-label="login"
      class="w-full"
      @click="handleSubmit"
    >
      {{ submitButtonText || $t('common.login') }}
    </VbenButton>
  </div>
</template>
