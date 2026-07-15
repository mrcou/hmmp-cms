<script setup lang="ts">
import type { VbenFormSchema } from '#/adapter/form';

import { computed, onMounted, ref } from 'vue';

import { ProfileBaseSetting } from '@vben/common-ui';
import { useUserStore } from '@vben/stores';

import { message } from 'antdv-next';

import { getProfile, updateProfile } from '#/api/system/profile';

const userStore = useUserStore();
const profileBaseSettingRef = ref();

const formSchema = computed((): VbenFormSchema[] => {
  return [
    { fieldName: 'userName', component: 'Input', label: '用户名', componentProps: { disabled: true } },
    { fieldName: 'nickName', component: 'Input', label: '昵称', rules: 'required' },
    { fieldName: 'phonenumber', component: 'Input', label: '手机号码' },
    { fieldName: 'email', component: 'Input', label: '邮箱' },
    {
      fieldName: 'sex',
      component: 'RadioGroup',
      componentProps: {
        options: [
          { label: '男', value: '0' },
          { label: '女', value: '1' },
        ],
      },
      label: '性别',
    },
  ];
});

async function handleSubmit(values: Record<string, any>) {
  await updateProfile(values);
  message.success('保存成功');
  await userStore.setUserInfo({
    ...(userStore.userInfo as any),
    realName: values.nickName,
  });
}

onMounted(async () => {
  const res = await getProfile();
  profileBaseSettingRef.value.getFormApi().setValues(res.data);
});
</script>
<template>
  <ProfileBaseSetting
    ref="profileBaseSettingRef"
    :form-schema="formSchema"
    @submit="handleSubmit"
  />
</template>
