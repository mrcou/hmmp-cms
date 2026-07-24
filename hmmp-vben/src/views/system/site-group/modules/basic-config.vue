<script setup lang="ts">
import { computed, h, onMounted, ref, watch } from 'vue';

import { IconifyIcon } from '@vben/icons';

import { Button, message, Upload } from 'antdv-next';

import { useVbenForm } from '#/adapter/form';
import { upload_file } from '#/api/core/upload';
import {
  getSectionConfig,
  saveSectionConfig,
} from '#/api/system/site-config';

import {
  JOURNAL_MEDIA_FIELDS,
  JOURNAL_MODULE_FIELDS,
  useJournalBasicFormSchema,
} from '../schemas/journal-basic';

const SECTION = 'journal-basic';
const uploadingField = ref<string | null>(null);
const initialValues = ref<Record<string, unknown>>({});

const [Form, formApi] = useVbenForm({
  commonConfig: {
    labelWidth: 220,
  },
  schema: [],
  showDefaultActions: false,
  wrapperClass: 'grid-cols-1 md:grid-cols-2 xl:max-w-6xl',
});

function extractUploadPath(data: Record<string, unknown> | undefined) {
  if (!data) {
    return '';
  }
  return String(
    data.fileName ??
      (data.data as Record<string, unknown> | undefined)?.fileName ??
      data.url ??
      (data.data as Record<string, unknown> | undefined)?.url ??
      (data.data as Record<string, unknown> | undefined)?.fileUrl ??
      '',
  ).trim();
}

function makeUploadBefore(field: string) {
  return (file: File) => {
    uploadFieldFile(field, file);
    return Upload.LIST_IGNORE;
  };
}

async function uploadFieldFile(field: string, file: File) {
  uploadingField.value = field;
  try {
    await upload_file({
      file,
      onSuccess: (data) => {
        const filePath = extractUploadPath(data);
        if (!filePath) {
          message.error('上传失败：未返回文件路径');
          return;
        }
        formApi.setFieldValue(field, filePath);
        message.success('上传成功');
      },
      onError: () => {
        message.error('上传失败');
      },
    });
  } finally {
    uploadingField.value = null;
  }
}

function createUploadSuffix(field: string, loadingField: string | null) {
  return () =>
    h(
      Upload,
      {
        accept: 'image/*',
        beforeUpload: makeUploadBefore(field),
        disabled: loadingField === field,
        showUploadList: false,
      },
      () =>
        h(
          Button,
          {
            loading: loadingField === field,
            size: 'small',
          },
          () => [
            h(IconifyIcon, {
              class: 'mr-1 size-3.5',
              icon: 'lucide:folder-open',
            }),
            '浏览...',
          ],
        ),
    );
}

const formSchema = computed(() => {
  const loadingField = uploadingField.value;
  return useJournalBasicFormSchema().map((item) => {
    if (
      JOURNAL_MEDIA_FIELDS.includes(
        item.fieldName as (typeof JOURNAL_MEDIA_FIELDS)[number],
      )
    ) {
      return {
        ...item,
        suffix: createUploadSuffix(item.fieldName, loadingField),
      };
    }
    return item;
  });
});

watch(
  formSchema,
  (schema) => {
    formApi.setState({ schema });
  },
  { immediate: true },
);

function normalizeLoadedValues(data: Record<string, unknown>) {
  const values: Record<string, unknown> = { ...data };
  values.included_modules = JOURNAL_MODULE_FIELDS.filter(
    (field) => String(values[field]) === '1',
  );
  return values;
}

function buildSavePayload(values: Record<string, unknown>) {
  const payload: Record<string, unknown> = { ...values };
  const selectedModules = new Set(
    Array.isArray(payload.included_modules)
      ? (payload.included_modules as string[])
      : [],
  );

  for (const field of JOURNAL_MODULE_FIELDS) {
    payload[field] = selectedModules.has(field) ? '1' : '0';
  }

  delete payload.included_modules;
  for (const key of Object.keys(payload)) {
    if (key.startsWith('divider_')) {
      delete payload[key];
    }
  }
  return payload;
}

async function loadConfig() {
  const res = await getSectionConfig(SECTION);
  const values = normalizeLoadedValues(res.data ?? {});
  initialValues.value = values;
  formApi.setValues(values);
}

async function handleSave() {
  const { valid } = await formApi.validate();
  if (!valid) {
    return;
  }
  const values = await formApi.getValues();
  await saveSectionConfig(SECTION, buildSavePayload(values));
  initialValues.value = values;
  message.success('保存成功');
}

function handleCancel() {
  formApi.setValues({ ...initialValues.value });
  message.info('已恢复为上次保存的内容');
}

onMounted(loadConfig);
</script>

<template>
  <div class="journal-basic-config">
    <Form />
    <div class="mt-6 flex gap-3 xl:max-w-6xl">
      <Button type="primary" @click="handleSave">保存</Button>
      <Button @click="handleCancel">取消</Button>
    </div>
  </div>
</template>

<style scoped>
.journal-basic-config :deep(.ant-checkbox-group) {
  display: flex;
  flex-wrap: wrap;
  gap: 12px 24px;
}

.journal-basic-config :deep(.col-span-full .flex-auto .flex.items-center) {
  width: 100%;
}

.journal-basic-config :deep(.col-span-full .flex-auto .flex.items-center .flex-auto) {
  flex: 1;
  min-width: 0;
}

.journal-basic-config :deep(.col-span-full input) {
  width: 100%;
}
</style>
