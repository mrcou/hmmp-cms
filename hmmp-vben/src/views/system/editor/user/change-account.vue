<script lang="ts" setup>
import { reactive, ref } from 'vue';

import { message } from 'antdv-next';

const formLoading = ref(false);
const formData = reactive({
  oldAccount: '',
  newAccount: '',
  userType: undefined as string | undefined,
  remark: '',
});

async function onSubmit() {
  if (!formData.oldAccount || !formData.newAccount) {
    message.warning('请填写原帐号与新帐号');
    return;
  }
  formLoading.value = true;
  try {
    message.success(`帐号已变更：${formData.oldAccount} → ${formData.newAccount}`);
    formData.oldAccount = '';
    formData.newAccount = '';
    formData.userType = undefined;
    formData.remark = '';
  } finally {
    formLoading.value = false;
  }
}
</script>

<template>
  <div class="p-4">
    <a-card title="变更用户帐号" style="max-width: 560px">
      <a-form layout="vertical">
        <a-form-item label="用户类型" required>
          <a-select v-model:value="formData.userType" placeholder="请选择">
            <a-select-option value="author">作者</a-select-option>
            <a-select-option value="reviewer">审稿人</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="原帐号" required>
          <a-input v-model:value="formData.oldAccount" placeholder="请输入原帐号" />
        </a-form-item>
        <a-form-item label="新帐号" required>
          <a-input v-model:value="formData.newAccount" placeholder="请输入新帐号" />
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea v-model:value="formData.remark" placeholder="请输入备注" :rows="3" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" :loading="formLoading" @click="onSubmit">确认变更</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>
