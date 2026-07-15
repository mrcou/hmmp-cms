<script lang="ts" setup>
import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';

import { Checkbox, message, Upload } from 'antdv-next';

import {
  downloadUserImportTemplate,
  importUserData,
} from '#/api/system/user';

const emit = defineEmits<{ success: [] }>();

const updateSupport = ref(false);
const fileList = ref<any[]>([]);

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const file = fileList.value[0]?.originFileObj;
    if (!file) {
      message.warning('请先选择要导入的文件');
      return;
    }
    modalApi.lock();
    try {
      const res = await importUserData(file, updateSupport.value);
      message.success(res.msg || '导入成功');
      emit('success');
      modalApi.close();
    } finally {
      modalApi.unlock();
    }
  },
  onOpenChange(isOpen) {
    if (isOpen) {
      fileList.value = [];
      updateSupport.value = false;
    }
  },
});

function beforeUpload(file: any) {
  fileList.value = [{ originFileObj: file, name: file.name, uid: file.uid }];
  return false;
}

function downloadTemplate() {
  downloadUserImportTemplate();
}
</script>

<template>
  <Modal title="用户导入" class="w-[500px]">
    <div class="flex flex-col gap-4">
      <div>
        <Checkbox v-model:checked="updateSupport">
          是否更新已经存在的用户数据
        </Checkbox>
      </div>
      <Upload.Dragger
        :file-list="fileList"
        :before-upload="beforeUpload"
        :max-count="1"
        accept=".xlsx,.xls"
      >
        <p class="ant-upload-drag-icon flex justify-center">
          <IconifyIcon icon="lucide:upload-cloud" class="size-8" />
        </p>
        <p>点击或拖拽文件到此处上传</p>
        <p class="text-xs text-foreground/50">仅支持 xls、xlsx 格式文件</p>
      </Upload.Dragger>
      <div class="text-sm">
        <span class="text-foreground/50">
          提示：仅允许导入xls、xlsx格式文件！
        </span>
        <a class="ml-2 cursor-pointer text-primary" @click="downloadTemplate">
          下载模板
        </a>
      </div>
    </div>
  </Modal>
</template>
