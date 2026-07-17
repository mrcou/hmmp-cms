<script lang="ts" setup>
/**
 * 批量发布文章 —— 按过刊样表一对一导入 Excel 元数据，或 ZIP 导入全文 PDF
 */
import { computed, reactive, ref } from 'vue';

import { IconifyIcon } from '@vben/icons';
import { preferences } from '@vben/preferences';

import { message, Upload } from 'antdv-next';

import {
  batchImportArticles,
  downloadArticleImportTemplate,
} from '#/api/biz/publisher';

const submitting = ref(false);
const updateSupport = ref(false);
const fileList = ref<any[]>([]);

const formState = reactive({
  magazineId: 'default' as string,
});

const formLabelWidth = '170px';
/** 杂志名称 / 文件路径 input 同宽 */
const formControlWidth = '300px';
/** 保存 / 下载导入模板按钮宽度 */
const actionBtnWidth = '10em';

const magazineOptions = computed(() => [
  {
    value: 'default',
    label: preferences.app.name || '默认杂志',
  },
]);

const selectedFile = computed(() => fileList.value[0]?.originFileObj as File | undefined);

/** 浏览器出于安全限制无法读取真实本地路径，展示文件名；有 webkitRelativePath 时一并显示 */
const selectedFilePath = computed(() => {
  const file = selectedFile.value;
  if (!file) {
    return '';
  }
  const relative = (file as File & { webkitRelativePath?: string }).webkitRelativePath;
  if (relative) {
    return relative;
  }
  return file.name;
});

function beforeUpload(file: File) {
  const name = file.name.toLowerCase();
  const ok =
    name.endsWith('.xls') ||
    name.endsWith('.xlsx') ||
    name.endsWith('.zip');
  if (!ok) {
    message.warning('仅支持 xls、xlsx 或 zip 文件');
    return Upload.LIST_IGNORE;
  }
  fileList.value = [{ originFileObj: file, name: file.name, uid: String(Date.now()) }];
  return false;
}

function handleRemove() {
  fileList.value = [];
}

async function handleDownloadTemplate() {
  await downloadArticleImportTemplate();
}

async function handleSubmit() {
  if (!selectedFile.value) {
    message.warning('请选择 Excel 或 zip 文件');
    return;
  }
  submitting.value = true;
  try {
    const res = await batchImportArticles(selectedFile.value, updateSupport.value);
    const raw = res.msg || '导入成功';
    const text = String(raw).replace(/<br\s*\/?>/gi, '\n');
    message.success({
      content: text.length > 200 ? `${text.slice(0, 200)}…` : text,
      duration: 6,
    });
    fileList.value = [];
  } catch (e: any) {
    const raw = e?.response?.data?.msg || e?.message || '导入失败';
    const text = String(raw).replace(/<br\s*\/?>/gi, '\n');
    message.error({
      content: text.length > 300 ? `${text.slice(0, 300)}…` : text,
      duration: 8,
    });
  } finally {
    submitting.value = false;
  }
}
</script>

<template>
  <div class="p-4">
    <a-card title="批量发布文章" variant="borderless">
      <a-form
        :label-col="{ style: { width: formLabelWidth } }"
        :wrapper-col="{ style: { maxWidth: 'none' } }"
        class="max-w-4xl"
      >
        <a-form-item label="杂志名称" required>
          <a-select
            v-model:value="formState.magazineId"
            :options="magazineOptions"
            placeholder="请选择杂志"
            :style="{ width: formControlWidth }"
          />
        </a-form-item>

        <a-form-item label="Excel文件或者zip文件" required>
          <div class="batch-file-picker flex items-stretch">
            <a-input
              :value="selectedFilePath"
              readonly
              placeholder="请选择文件"
              :style="{ width: formControlWidth }"
              :title="selectedFilePath"
              class="batch-file-picker__input"
            />
            <a-upload
              :file-list="fileList"
              :before-upload="beforeUpload"
              :max-count="1"
              :show-upload-list="false"
              accept=".xls,.xlsx,.zip"
              class="batch-file-picker__upload"
              @remove="handleRemove"
            >
              <a-button type="primary" class="batch-file-picker__btn">
                <IconifyIcon icon="lucide:folder-open" class="mr-1 size-3.5" />
                浏览...
              </a-button>
            </a-upload>
          </div>
          <div class="text-muted-foreground mt-1 text-xs">
            支持 .xls / .xlsx（稿件明细）或 .zip（全文 PDF，文件名需与稿件编号一致）
          </div>
        </a-form-item>

        <a-form-item label="更新已存在数据">
          <a-checkbox v-model:checked="updateSupport">
            稿件编号已存在时覆盖更新
          </a-checkbox>
        </a-form-item>

        <a-form-item :wrapper-col="{ style: { marginLeft: formLabelWidth } }">
          <div class="flex flex-wrap gap-2">
            <a-button
              type="primary"
              :loading="submitting"
              :style="{ width: actionBtnWidth }"
              @click="handleSubmit"
            >
              保存
            </a-button>
            <a-button
              :style="{ width: actionBtnWidth }"
              @click="handleDownloadTemplate"
            >
              下载导入模板
            </a-button>
          </div>
        </a-form-item>
      </a-form>

      <a-divider />

      <div class="text-sm leading-7">
        <div class="mb-2 font-medium">过刊数据入库说明：</div>
        <ol class="list-decimal space-y-2 pl-5">
          <li>
            稿件基本信息存放在 Excel 文件中，必须严格按照
            <a class="cursor-pointer text-primary" @click="handleDownloadTemplate">
              Excel 模板
            </a>
            格式填写（与过刊稿件明细列表字段一一对应）。一次只能导入一期的文章。
          </li>
          <li>
            导入全文（PDF 或其他格式）：PDF 文件名需与稿件编号对应，例如稿件编号为
            <code class="mx-1 rounded bg-muted px-1">200501001</code>
            ，则 PDF 命名为
            <code class="mx-1 rounded bg-muted px-1">200501001.pdf</code>
            。可将同一期全部 PDF 打成 ZIP，命名建议为
            <code class="mx-1 rounded bg-muted px-1">年_期_pdf.zip</code>
            （如
            <code class="mx-1 rounded bg-muted px-1">2005_1_pdf.zip</code>
            ）后批量上传。请先导入 Excel，再导入 ZIP。
          </li>
        </ol>

        <div class="text-muted-foreground mt-4">
          样表字段：杂志编号、稿件编号、中文/英文标题、中文/英文摘要、中文/英文关键词、基金中/英文名、起始/截止页码、投稿/最后修改/发布时间、年、卷、期、栏目编号/名称、参考文献、引用文本、作者及单位中英文、DOI、浏览/下载/HTML浏览/被引/知网引用次数、网址。
        </div>
      </div>
    </a-card>
  </div>
</template>

<style scoped>
.batch-file-picker :deep(.batch-file-picker__input.ant-input),
.batch-file-picker :deep(.batch-file-picker__input .ant-input) {
  border-top-right-radius: 0 !important;
  border-bottom-right-radius: 0 !important;
}

.batch-file-picker__upload :deep(.ant-upload) {
  display: block;
}

.batch-file-picker :deep(.batch-file-picker__btn.ant-btn) {
  border-top-left-radius: 0 !important;
  border-bottom-left-radius: 0 !important;
  margin-inline-start: -1px;
}
</style>
