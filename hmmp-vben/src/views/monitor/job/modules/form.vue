<script lang="ts" setup>
import type { MonitorJobApi } from '#/api/monitor/job';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useVbenForm } from '#/adapter/form';
import { createJob, getJob, updateJob } from '#/api/monitor/job';

import { useFormSchema } from '../data';

const emit = defineEmits<{ success: [] }>();

const defaultValues = {
  concurrent: '1',
  cronExpression: '0/30 * * * * ?',
  jobGroup: 'DEFAULT',
  misfirePolicy: '3',
  status: '1',
};

const formData = ref<Partial<MonitorJobApi.SysJob>>();
const isEdit = computed(() => !!formData.value?.jobId);

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;

    const values = await formApi.getValues<MonitorJobApi.SysJob>();
    modalApi.lock();
    try {
      if (isEdit.value) {
        await updateJob({ ...values, jobId: formData.value?.jobId });
      } else {
        await createJob(values);
      }
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen) {
    if (!isOpen) return;

    const data = modalApi.getData<Partial<MonitorJobApi.SysJob>>();
    formData.value = data;
    formApi.resetForm();
    formApi.setValues(defaultValues);

    if (data?.jobId) {
      modalApi.lock();
      try {
        const res = await getJob(data.jobId);
        formApi.setValues({ ...defaultValues, ...res.data });
      } finally {
        modalApi.unlock();
      }
    }
  },
});

const title = computed(() => (isEdit.value ? '编辑任务' : '新增任务'));
</script>

<template>
  <Modal :title="title" class="w-[720px]">
    <Form />
  </Modal>
</template>
