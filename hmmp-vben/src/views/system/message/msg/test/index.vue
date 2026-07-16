<script lang="ts" setup>
import { reactive, ref } from 'vue';

import { message } from 'antdv-next';

import * as messageApi from '#/api/biz/message';

const submitting = ref(false);

const providerOptions = [
  { label: '阿里云', value: 'aliyun' },
  { label: '腾讯云', value: 'tencent' },
  { label: '火山云', value: 'volcengine' },
];

const formData = reactive<messageApi.MessageApi.Sms>({
  phone: '',
  content: '',
  smsProvider: 'aliyun',
});

const resultLog = ref('');

async function handleTestSend() {
  if (!formData.phone?.trim()) {
    message.warning('请输入手机号');
    return;
  }
  if (!/^1\d{10}$/.test(formData.phone.trim())) {
    message.warning('请输入正确的11位手机号');
    return;
  }
  if (!formData.content?.trim()) {
    message.warning('请输入短信内容');
    return;
  }
  if (!formData.smsProvider) {
    message.warning('请选择短信服务商');
    return;
  }

  submitting.value = true;
  resultLog.value = '';
  try {
    const res: any = await messageApi.testSendSms({
      phone: formData.phone.trim(),
      content: formData.content.trim(),
      smsProvider: formData.smsProvider,
    });
    const providerLabel =
      providerOptions.find((p) => p.value === formData.smsProvider)?.label ??
      formData.smsProvider;
    const msgText = res?.msg ?? res?.message ?? '发送完成';
    resultLog.value = `[${new Date().toLocaleString()}] ${providerLabel} → ${formData.phone}\n${msgText}`;
    message.success(msgText || '测试发送成功');
  } catch (e: any) {
    const errMsg = e?.message ?? e?.msg ?? '测试发送失败';
    resultLog.value = `[${new Date().toLocaleString()}] 失败\n${errMsg}`;
  } finally {
    submitting.value = false;
  }
}

function handleReset() {
  formData.phone = '';
  formData.content = '';
  formData.smsProvider = 'aliyun';
  resultLog.value = '';
}
</script>

<template>
  <div class="p-4">
    <a-card title="测试发送" style="max-width: 640px">
      <a-alert
        class="mb-4"
        type="info"
        show-icon
        message="用于验证短信通道。支持阿里云、腾讯云、火山云（当前后端为可切换服务商，具体以配置为准）。"
      />

      <a-form layout="vertical">
        <a-form-item label="短信服务商" required>
          <a-radio-group v-model:value="formData.smsProvider" button-style="solid">
            <a-radio-button
              v-for="opt in providerOptions"
              :key="opt.value"
              :value="opt.value"
            >
              {{ opt.label }}
            </a-radio-button>
          </a-radio-group>
        </a-form-item>

        <a-form-item label="手机号" required>
          <a-input
            v-model:value="formData.phone"
            placeholder="请输入接收手机号"
            maxlength="11"
            style="max-width: 280px"
          />
        </a-form-item>

        <a-form-item label="短信内容" required>
          <a-textarea
            v-model:value="formData.content"
            placeholder="请输入测试短信内容"
            :rows="5"
            :maxlength="500"
            show-count
          />
        </a-form-item>

        <a-form-item>
          <a-space>
            <a-button type="primary" :loading="submitting" @click="handleTestSend">
              测试发送
            </a-button>
            <a-button @click="handleReset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>

      <a-card v-if="resultLog" size="small" title="发送结果" class="mt-2">
        <pre class="m-0 whitespace-pre-wrap text-sm">{{ resultLog }}</pre>
      </a-card>
    </a-card>
  </div>
</template>
