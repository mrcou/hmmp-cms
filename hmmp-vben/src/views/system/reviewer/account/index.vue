<script setup lang="ts">
/**
 * 审稿人修改资料 —— 复用 Profile 表单能力，布局适配审稿工作台中间区。
 */
import type { VbenFormSchema } from '#/adapter/form';

import { computed, onMounted, ref } from 'vue';

import {
  ProfileBaseSetting,
  ProfilePasswordSetting,
  z,
} from '@vben/common-ui';
import { preferences } from '@vben/preferences';
import { useUserStore } from '@vben/stores';

import { message } from 'antdv-next';

import {
  getProfile,
  updateProfile,
  updateProfilePwd,
} from '#/api/system/profile';

const userStore = useUserStore();
const tabsValue = ref('basic');
const profileBaseSettingRef = ref();

const tabs = [
  { label: '基本资料', value: 'basic' },
  { label: '修改密码', value: 'password' },
];

const avatar = computed(
  () => userStore.userInfo?.avatar ?? preferences.app.defaultAvatar,
);
const displayName = computed(
  () =>
    userStore.userInfo?.realName ||
    userStore.userInfo?.username ||
    '审稿用户',
);

const baseSchema = computed((): VbenFormSchema[] => [
  {
    fieldName: 'userName',
    component: 'Input',
    label: '用户名',
    componentProps: { disabled: true },
  },
  {
    fieldName: 'nickName',
    component: 'Input',
    label: '昵称',
    rules: 'required',
  },
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
]);

const passwordSchema = computed((): VbenFormSchema[] => [
  {
    fieldName: 'oldPassword',
    label: '旧密码',
    component: 'VbenInputPassword',
    componentProps: { placeholder: '请输入旧密码' },
    rules: 'required',
  },
  {
    fieldName: 'newPassword',
    label: '新密码',
    component: 'VbenInputPassword',
    componentProps: {
      passwordStrength: true,
      placeholder: '请输入新密码',
    },
    rules: z.string().min(5, { message: '密码长度不能少于5位' }),
  },
  {
    fieldName: 'confirmPassword',
    label: '确认密码',
    component: 'VbenInputPassword',
    componentProps: {
      passwordStrength: true,
      placeholder: '请再次输入新密码',
    },
    dependencies: {
      rules(values) {
        const { newPassword } = values;
        return z
          .string({ required_error: '请再次输入新密码' })
          .min(1, { message: '请再次输入新密码' })
          .refine((value) => value === newPassword, {
            message: '两次输入的密码不一致',
          });
      },
      triggerFields: ['newPassword'],
    },
  },
]);

async function handleBaseSubmit(values: Record<string, any>) {
  await updateProfile(values);
  message.success('保存成功');
  await userStore.setUserInfo({
    ...(userStore.userInfo as any),
    realName: values.nickName,
  });
}

async function handlePasswordSubmit(values: Record<string, any>) {
  await updateProfilePwd(values.oldPassword, values.newPassword);
  message.success('密码修改成功');
}

onMounted(async () => {
  const res = await getProfile();
  profileBaseSettingRef.value?.getFormApi()?.setValues(res.data);
});
</script>

<template>
  <div class="account-panel space-y-3 p-1">
    <section class="account-card flex items-center gap-3">
      <img :src="avatar" alt="" class="size-14 rounded-full object-cover" />
      <div>
        <div class="text-base font-semibold">{{ displayName }}</div>
        <div class="text-muted-foreground text-xs">
          {{ userStore.userInfo?.username }}
        </div>
      </div>
    </section>

    <section class="account-card">
      <div class="mb-4 flex gap-2">
        <button
          v-for="tab in tabs"
          :key="tab.value"
          type="button"
          class="tab-btn"
          :class="{ active: tabsValue === tab.value }"
          @click="tabsValue = tab.value"
        >
          {{ tab.label }}
        </button>
      </div>

      <ProfileBaseSetting
        v-if="tabsValue === 'basic'"
        ref="profileBaseSettingRef"
        :form-schema="baseSchema"
        @submit="handleBaseSubmit"
      />
      <ProfilePasswordSetting
        v-else
        class="max-w-md"
        :form-schema="passwordSchema"
        @submit="handlePasswordSubmit"
      />
    </section>
  </div>
</template>

<style scoped>
.account-card {
  border-radius: 0.75rem;
  background: hsl(var(--card));
  padding: 1rem 1.125rem;
  box-shadow: 0 1px 2px hsl(var(--foreground) / 4%);
}

.tab-btn {
  border: 0;
  border-radius: 999px;
  background: hsl(var(--muted) / 50%);
  padding: 0.375rem 0.875rem;
  color: hsl(var(--muted-foreground));
  font-size: 0.8125rem;
  cursor: pointer;
}

.tab-btn.active {
  background: hsl(var(--primary) / 12%);
  color: hsl(var(--primary));
  font-weight: 600;
}
</style>
