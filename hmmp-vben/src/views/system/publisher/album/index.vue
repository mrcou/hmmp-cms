<script lang="ts" setup>
/**
 * 虚拟专辑管理 —— 对齐旧系统列表与添加表单字段
 */
import type { PublisherApi } from '#/api/biz/publisher';

import { computed, onMounted, reactive, ref } from 'vue';

import { VbenTableAction } from '@vben/common-ui';
import { IconifyIcon } from '@vben/icons';
import { VbenTiptap } from '@vben/plugins/tiptap';
import { preferences } from '@vben/preferences';

import { message, Modal, Upload } from 'antdv-next';

import * as meetingApi from '#/api/biz/meeting';
import * as publisherApi from '#/api/biz/publisher';

defineOptions({ name: 'PublisherIssueAlbum' });

type UploadField = 'en_image_url' | 'image_url';

const loading = ref(false);
const dataSource = ref<PublisherApi.Album[]>([]);
const selectedKeys = ref<number[]>([]);

const pagination = reactive({
  current: 1,
  pageSize: 5,
  total: 0,
  showSizeChanger: true,
  pageSizeOptions: ['5', '10', '20', '50'],
  showTotal: (total: number, range: [number, number]) =>
    `当前显示 ${range[0]} - ${range[1]} 条，共 ${total} 条记录`,
});

/** 顶栏筛选 */
const filters = reactive({
  journalCode: undefined as string | undefined,
  meetingId: undefined as number | undefined,
  searchField: 'albumId' as
    | 'albumId'
    | 'albumType'
    | 'createBy'
    | 'journalCode'
    | 'nameCn'
    | 'nameEn',
  searchValue: '',
});

const searchFieldOptions = [
  { value: 'albumId', label: '专辑ID' },
  { value: 'journalCode', label: '杂志编号' },
  { value: 'nameCn', label: '中文名' },
  { value: 'nameEn', label: '英文名' },
  { value: 'createBy', label: '添加用户' },
  { value: 'albumType', label: '类型' },
];

const albumTypeOptions = [
  { value: '0', label: '虚拟专辑' },
  { value: '1', label: '出版专辑' },
  { value: '2', label: '讨论园地' },
  { value: '3', label: '优秀论文' },
  { value: '4', label: '特邀综述' },
  { value: '5', label: '特邀专题' },
];

/** antdv-next Select 不会转发 a-select-option 子节点，必须用 options */
const magazineOptions = computed(() => [
  {
    value: 'ddhl',
    label: preferences.app.name || '当代护理',
  },
]);

const meetingOptions = ref<{ label: string; value: number }[]>([]);

async function loadMeetingOptions() {
  try {
    const res = (await meetingApi.getMeetingList({
      pageNum: 1,
      pageSize: 200,
    })) as any;
    meetingOptions.value = (res?.rows ?? [])
      .map((r: any) => ({
        value: r.meetingId as number,
        label: (r.meetingName || r.title || `会议#${r.meetingId}`) as string,
      }))
      .filter((m: { value: number }) => m.value != null);
  } catch {
    meetingOptions.value = [];
  }
}

const allColumns = [
  {
    key: 'journalCode',
    title: '杂志编号',
    dataIndex: 'journalCode',
    width: 120,
  },
  { key: 'albumId', title: '专辑ID', dataIndex: 'albumId', width: 100 },
  {
    key: 'nameCn',
    title: '中文名',
    dataIndex: 'nameCn',
    width: 160,
    ellipsis: true,
  },
  {
    key: 'nameEn',
    title: '英文名',
    dataIndex: 'nameEn',
    width: 160,
    ellipsis: true,
  },
  {
    key: 'createTime',
    title: '添加时间',
    dataIndex: 'createTime',
    width: 170,
  },
  {
    key: 'finishDate',
    title: '完成日期',
    dataIndex: 'finishDate',
    width: 120,
  },
  {
    key: 'createBy',
    title: '添加用户',
    dataIndex: 'createBy',
    width: 110,
  },
  { key: 'orderNum', title: '顺序', dataIndex: 'orderNum', width: 80 },
  { key: 'albumType', title: '类型', dataIndex: 'albumType', width: 110 },
  { key: 'action', title: '操作', dataIndex: 'action', width: 90, fixed: 'right' as const },
];

const visibleColumnKeys = ref(allColumns.map((c) => c.key));
const columnDrawerOpen = ref(false);

const columns = computed(() => {
  const visible = new Set(visibleColumnKeys.value);
  return allColumns.filter((c) => visible.has(c.key));
});

const rowSelection = computed(() => ({
  selectedRowKeys: selectedKeys.value,
  onChange: (keys: (number | string)[]) => {
    selectedKeys.value = keys.map(Number);
  },
}));

function albumTypeLabel(value?: number | string) {
  const normalized = value == null ? '' : String(value);
  return (
    albumTypeOptions.find((item) => item.value === normalized)?.label ||
    normalized
  );
}

function buildSearchParams() {
  const params: Record<string, any> = {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  };
  if (filters.journalCode) {
    params.journalCode = filters.journalCode;
  }
  if (filters.meetingId != null) {
    params.meetingId = filters.meetingId;
  }
  const keyword = filters.searchValue.trim();
  if (keyword) {
    if (filters.searchField === 'albumId') {
      const num = Number(keyword);
      if (!Number.isNaN(num)) {
        params.albumId = num;
      }
    } else {
      params[filters.searchField] = keyword;
    }
  }
  return params;
}

async function fetchList() {
  loading.value = true;
  try {
    const res = (await publisherApi.getAlbumList(buildSearchParams())) as any;
    dataSource.value = res?.rows ?? res?.data?.rows ?? [];
    pagination.total = res?.total ?? res?.data?.total ?? 0;
    selectedKeys.value = [];
  } catch {
    message.error('加载虚拟专辑列表失败');
    dataSource.value = [];
    pagination.total = 0;
  } finally {
    loading.value = false;
  }
}

function onSearch() {
  pagination.current = 1;
  fetchList();
}

function onReset() {
  filters.journalCode = undefined;
  filters.meetingId = undefined;
  filters.searchField = 'albumId';
  filters.searchValue = '';
  pagination.current = 1;
  fetchList();
}

function onPageChange(page: number, pageSize: number) {
  pagination.current = page;
  pagination.pageSize = pageSize;
  fetchList();
}

/** 新增 / 编辑 */
const drawerOpen = ref(false);
const drawerTitle = ref('添加虚拟专辑');
const submitting = ref(false);
const isEdit = ref(false);
const formData = reactive<PublisherApi.Album>({
  journal_id: 'ddhl',
  journalCode: 'ddhl',
  cn_name: '',
  nameCn: '',
  en_name: '',
  nameEn: '',
  type: '0',
  albumType: '0',
  sequence: 0,
  orderNum: 0,
  meetingId: undefined,
  image_url: '',
  en_image_url: '',
  coverImage: '',
  CKEDITOR_cn_description: '',
  CKEDITOR_en_description: '',
  CKEDITOR_acquisition_editor: '',
  CKEDITOR_en_acquisition_editor: '',
  description: '',
  status: '0',
  remark: '',
});
const uploadFileLists = reactive<Record<UploadField, any[]>>({
  image_url: [],
  en_image_url: [],
});

function resetForm() {
  formData.albumId = undefined;
  formData.journal_id = filters.journalCode || 'ddhl';
  formData.journalCode = formData.journal_id;
  formData.cn_name = '';
  formData.nameCn = '';
  formData.en_name = '';
  formData.nameEn = '';
  formData.finishDate = undefined;
  formData.type = '0';
  formData.albumType = '0';
  formData.sequence = 0;
  formData.orderNum = 0;
  formData.meetingId = filters.meetingId;
  formData.image_url = '';
  formData.en_image_url = '';
  formData.coverImage = '';
  formData.CKEDITOR_cn_description = '';
  formData.CKEDITOR_en_description = '';
  formData.CKEDITOR_acquisition_editor = '';
  formData.CKEDITOR_en_acquisition_editor = '';
  formData.description = '';
  formData.status = '0';
  formData.remark = '';
  uploadFileLists.image_url = [];
  uploadFileLists.en_image_url = [];
}

function assignFormData(record: PublisherApi.Album) {
  const source = record as any;
  const journalId =
    source.journal_id || source.journalId || source.journalCode || 'ddhl';
  const cnName = source.cn_name || source.cnName || source.nameCn || '';
  const enName = source.en_name || source.enName || source.nameEn || '';
  const type = String(source.type ?? source.albumType ?? '0');
  const sequence = Number(source.sequence ?? source.orderNum ?? 0);
  const imageUrl = source.image_url || source.imageUrl || source.coverImage || '';
  const cnDescription =
    source.CKEDITOR_cn_description ||
    source.cnDescription ||
    source.description ||
    '';

  Object.assign(formData, {
    albumId: source.albumId,
    journal_id: journalId,
    journalCode: journalId,
    cn_name: cnName,
    nameCn: cnName,
    en_name: enName,
    nameEn: enName,
    finishDate: source.finishDate,
    type,
    albumType: type,
    sequence: Number.isNaN(sequence) ? 0 : sequence,
    orderNum: Number.isNaN(sequence) ? 0 : sequence,
    meetingId: source.meetingId,
    image_url: imageUrl,
    en_image_url:
      source.en_image_url || source.enImageUrl || source.enCoverImage || '',
    coverImage: imageUrl,
    CKEDITOR_cn_description: cnDescription,
    CKEDITOR_en_description:
      source.CKEDITOR_en_description || source.enDescription || '',
    CKEDITOR_acquisition_editor:
      source.CKEDITOR_acquisition_editor || source.acquisitionEditor || '',
    CKEDITOR_en_acquisition_editor:
      source.CKEDITOR_en_acquisition_editor ||
      source.enAcquisitionEditor ||
      '',
    description: cnDescription,
    status: source.status || '0',
    remark: source.remark || '',
  });

  uploadFileLists.image_url = imageUrl
    ? [{ uid: `image-${source.albumId || Date.now()}`, name: imageUrl, status: 'done' }]
    : [];
  uploadFileLists.en_image_url = formData.en_image_url
    ? [
        {
          uid: `en-image-${source.albumId || Date.now()}`,
          name: formData.en_image_url,
          status: 'done',
        },
      ]
    : [];
}

function handleAdd() {
  isEdit.value = false;
  drawerTitle.value = '添加虚拟专辑';
  resetForm();
  drawerOpen.value = true;
}

async function handleEdit(record: PublisherApi.Album) {
  isEdit.value = true;
  drawerTitle.value = '修改虚拟专辑';
  resetForm();
  drawerOpen.value = true;
  let source = record;
  if (record.albumId != null) {
    try {
      const res = (await publisherApi.getAlbum(record.albumId)) as any;
      source = { ...record, ...(res?.data ?? res ?? {}) };
    } catch {
      source = record;
    }
  }
  assignFormData(source);
}

function extractUploadPath(res: any) {
  return (
    res?.fileName ??
    res?.data?.fileName ??
    res?.url ??
    res?.data?.url ??
    res?.data?.fileUrl ??
    ''
  );
}

function makeUploadBefore(field: UploadField) {
  return (file: File) => {
    uploadFieldFile(field, file);
    return Upload.LIST_IGNORE;
  };
}

async function uploadFieldFile(field: UploadField, file: File) {
  try {
    const res = await publisherApi.uploadPublisherFile(file);
    const filePath = extractUploadPath(res);
    if (!filePath) {
      message.warning('上传成功，但未返回文件路径');
      return;
    }
    formData[field] = filePath;
    if (field === 'image_url') {
      formData.coverImage = filePath;
    }
    uploadFileLists[field] = [
      {
        uid: `${field}-${Date.now()}`,
        name: file.name,
        status: 'done',
      },
    ];
    message.success('上传成功');
  } catch {
    message.error('上传失败');
  }
}

const richEditorImageUpload = {
  upload: (file: File, onProgress?: (percent: number) => void) => {
    return new Promise<string>((resolve, reject) => {
      publisherApi
        .uploadPublisherFile(file)
        .then((res) => {
          onProgress?.(100);
          const filePath = extractUploadPath(res);
          if (!filePath) {
            reject(new Error('上传成功，但未返回文件路径'));
            return;
          }
          resolve(filePath);
        })
        .catch((error) => {
          reject(error instanceof Error ? error : new Error(String(error)));
        });
    });
  },
};

function buildPayload(): PublisherApi.Album | null {
  const journalId = formData.journal_id?.trim();
  if (!journalId) {
    message.warning('请选择杂志名称');
    return null;
  }
  const cnName = formData.cn_name?.trim();
  if (!cnName) {
    message.warning('请填写专辑中文名');
    return null;
  }

  const enName = formData.en_name?.trim() || '';
  const type = String(formData.type ?? '0');
  const sequence =
    formData.sequence == null ? 0 : Number(formData.sequence);
  const imageUrl = formData.image_url?.trim() || '';
  const cnDescription = formData.CKEDITOR_cn_description || '';

  return {
    ...formData,
    journal_id: journalId,
    journalCode: journalId,
    cn_name: cnName,
    nameCn: cnName,
    en_name: enName,
    nameEn: enName,
    type,
    albumType: type,
    sequence: Number.isNaN(sequence) ? 0 : sequence,
    orderNum: Number.isNaN(sequence) ? 0 : sequence,
    image_url: imageUrl,
    en_image_url: formData.en_image_url?.trim() || '',
    coverImage: imageUrl,
    CKEDITOR_cn_description: cnDescription,
    CKEDITOR_en_description: formData.CKEDITOR_en_description || '',
    CKEDITOR_acquisition_editor: formData.CKEDITOR_acquisition_editor || '',
    CKEDITOR_en_acquisition_editor:
      formData.CKEDITOR_en_acquisition_editor || '',
    description: cnDescription,
    status: formData.status || '0',
    remark: formData.remark?.trim() || '',
  };
}

async function handleSubmit() {
  const payload = buildPayload();
  if (!payload) {
    return;
  }
  submitting.value = true;
  try {
    if (isEdit.value) {
      await publisherApi.updateAlbum(payload);
      message.success('修改成功');
    } else {
      await publisherApi.createAlbum(payload);
      message.success('添加成功');
    }
    drawerOpen.value = false;
    fetchList();
  } finally {
    submitting.value = false;
  }
}

async function handleBatchDelete() {
  if (!selectedKeys.value.length) {
    message.warning('请先勾选要删除的记录');
    return;
  }
  Modal.confirm({
    title: '确认删除',
    content: `确定删除选中的 ${selectedKeys.value.length} 条虚拟专辑吗？`,
    okType: 'danger',
    async onOk() {
      await publisherApi.deleteAlbum(selectedKeys.value);
      message.success('删除成功');
      fetchList();
    },
  });
}

onMounted(() => {
  fetchList();
  loadMeetingOptions();
});
</script>

<template>
  <div class="p-4">
    <!-- 顶部筛选（统一图示格式：标签 + 控件 + 查询/重置） -->
    <div class="mb-4 flex items-center justify-between">
      <div class="flex items-center justify-end">
        <a-form layout="inline">
          <a-form-item label="杂志名称">
            <a-select
              v-model:value="filters.journalCode"
              allow-clear
              placeholder="请选择"
              style="width: 160px"
              :options="magazineOptions"
            />
          </a-form-item>
          <a-form-item label="会议">
            <a-select
              v-model:value="filters.meetingId"
              allow-clear
              placeholder="请选择"
              style="width: 160px"
              :options="meetingOptions"
            />
          </a-form-item>
          <a-form-item label="检索项">
            <a-select
              v-model:value="filters.searchField"
              style="width: 120px"
              :options="searchFieldOptions"
            />
          </a-form-item>
          <a-form-item label="关键词">
            <a-input
              v-model:value="filters.searchValue"
              allow-clear
              placeholder="请输入搜索词"
              style="width: 200px"
              @press-enter="onSearch"
            />
          </a-form-item>
          <a-form-item>
            <a-space>
              <a-button type="primary" @click="onSearch">查询</a-button>
              <a-button @click="onReset">重置</a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </div>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :loading="loading"
      :pagination="pagination"
      :row-selection="rowSelection"
      :scroll="{ x: 1200 }"
      row-key="albumId"
      size="middle"
      @change="(pg: any) => onPageChange(pg.current, pg.pageSize)"
    >
      <template #emptyText>
        <a-empty description="未查询到任何数据信息!" />
      </template>

      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <VbenTableAction
            :actions="[
              { text: '修改', icon: 'lucide:edit', onClick: () => handleEdit(record) },
            ]"
          />
        </template>
        <template v-else-if="column.key === 'finishDate'">
          {{ record.finishDate || '' }}
        </template>
        <template v-else-if="column.key === 'orderNum'">
          {{ record.orderNum ?? record.sequence ?? '' }}
        </template>
        <template v-else-if="column.key === 'albumType'">
          {{ albumTypeLabel(record.albumType ?? record.type) }}
        </template>
      </template>
    </a-table>

    <!-- 底部操作 -->
    <div class="batch-actions mt-4 flex flex-wrap gap-2 border-t pt-4">
      <a-button type="primary" @click="handleAdd">
        <IconifyIcon icon="lucide:plus" class="mr-1 size-3.5" />
        添加
      </a-button>
      <a-button type="primary" danger @click="handleBatchDelete">
        <IconifyIcon icon="lucide:trash-2" class="mr-1 size-3.5" />
        删除
      </a-button>
      <a-button type="primary" @click="columnDrawerOpen = true">
        <IconifyIcon icon="lucide:settings-2" class="mr-1 size-3.5" />
        显示设置
      </a-button>
    </div>

    <!-- 新增 / 修改 -->
    <a-drawer
      v-model:open="drawerOpen"
      :title="drawerTitle"
      placement="right"
      :width="920"
      destroy-on-close
      class="album-drawer"
    >
      <a-form layout="vertical" class="album-form">
        <a-divider orientation="left">基础信息</a-divider>
        <div class="form-grid">
          <a-form-item label="杂志名称" name="journal_id" required>
            <a-select
              v-model:value="formData.journal_id"
              placeholder="请选择杂志"
              :options="magazineOptions"
            />
          </a-form-item>
          <a-form-item label="专辑中文名" name="cn_name" required>
            <a-input
              v-model:value="formData.cn_name"
              allow-clear
              placeholder="请输入专辑中文名"
            />
          </a-form-item>
          <a-form-item label="专辑英文名" name="en_name">
            <a-input
              v-model:value="formData.en_name"
              allow-clear
              placeholder="请输入专辑英文名"
            />
          </a-form-item>
          <a-form-item label="类型" name="type">
            <a-select
              v-model:value="formData.type"
              placeholder="请选择类型"
              :options="albumTypeOptions"
            />
          </a-form-item>
          <a-form-item label="顺序" name="sequence">
            <a-input-number
              v-model:value="formData.sequence"
              :min="0"
              :max="9999"
              style="width: 100%"
            />
          </a-form-item>
        </div>

        <a-divider orientation="left">封面</a-divider>
        <div class="form-grid">
          <a-form-item
            label="中文封面"
            name="image_url"
            extra="提示：可在此上传本期封面"
          >
            <div class="file-picker">
              <a-input
                v-model:value="formData.image_url"
                allow-clear
                placeholder="中文封面路径或 URL"
              />
              <a-upload
                accept="image/*"
                :before-upload="makeUploadBefore('image_url')"
                :file-list="uploadFileLists.image_url"
                :max-count="1"
                :show-upload-list="false"
              >
                <a-button>
                  <IconifyIcon
                    icon="lucide:folder-open"
                    class="mr-1 size-3.5"
                  />
                  浏览...
                </a-button>
              </a-upload>
            </div>
          </a-form-item>
          <a-form-item
            label="英文封面"
            name="en_image_url"
            extra="提示：可在此上传本期封面"
          >
            <div class="file-picker">
              <a-input
                v-model:value="formData.en_image_url"
                allow-clear
                placeholder="英文封面路径或 URL"
              />
              <a-upload
                accept="image/*"
                :before-upload="makeUploadBefore('en_image_url')"
                :file-list="uploadFileLists.en_image_url"
                :max-count="1"
                :show-upload-list="false"
              >
                <a-button>
                  <IconifyIcon
                    icon="lucide:folder-open"
                    class="mr-1 size-3.5"
                  />
                  浏览...
                </a-button>
              </a-upload>
            </div>
          </a-form-item>
        </div>

        <a-divider orientation="left">描述</a-divider>
        <a-form-item
          label="中文描述"
          name="CKEDITOR_cn_description"
          class="rich-form-item"
        >
          <VbenTiptap
            v-model="formData.CKEDITOR_cn_description"
            :image-upload="richEditorImageUpload"
            :max-height="420"
            :min-height="220"
            placeholder="请输入中文描述"
          />
        </a-form-item>
        <a-form-item
          label="英文描述"
          name="CKEDITOR_en_description"
          class="rich-form-item"
        >
          <VbenTiptap
            v-model="formData.CKEDITOR_en_description"
            :image-upload="richEditorImageUpload"
            :max-height="420"
            :min-height="220"
            placeholder="请输入英文描述"
          />
        </a-form-item>

        <a-divider orientation="left">组稿编辑</a-divider>
        <a-form-item
          label="组稿编辑中文名"
          name="CKEDITOR_acquisition_editor"
          class="rich-form-item"
        >
          <VbenTiptap
            v-model="formData.CKEDITOR_acquisition_editor"
            :image-upload="richEditorImageUpload"
            :max-height="360"
            :min-height="180"
            placeholder="请输入组稿编辑中文名"
          />
        </a-form-item>
        <a-form-item
          label="组稿编辑英文名"
          name="CKEDITOR_en_acquisition_editor"
          class="rich-form-item"
        >
          <VbenTiptap
            v-model="formData.CKEDITOR_en_acquisition_editor"
            :image-upload="richEditorImageUpload"
            :max-height="360"
            :min-height="180"
            placeholder="请输入组稿编辑英文名"
          />
        </a-form-item>
      </a-form>

      <template #footer>
        <div class="drawer-footer">
          <a-button @click="drawerOpen = false">取消</a-button>
          <a-button type="primary" :loading="submitting" @click="handleSubmit">
            保存
          </a-button>
        </div>
      </template>
    </a-drawer>

    <!-- 显示设置 -->
    <a-drawer
      v-model:open="columnDrawerOpen"
      title="显示设置"
      placement="right"
      :width="320"
    >
      <p class="text-muted-foreground mb-3 text-sm">
        勾选需要在列表中显示的列：
      </p>
      <a-checkbox-group
        v-model:value="visibleColumnKeys"
        class="flex flex-col gap-2"
      >
        <a-checkbox v-for="col in allColumns" :key="col.key" :value="col.key">
          {{ col.title }}
        </a-checkbox>
      </a-checkbox-group>
      <div class="mt-6">
        <a-button type="primary" block @click="columnDrawerOpen = false">
          确定
        </a-button>
      </div>
    </a-drawer>
  </div>
</template>

<style scoped>
.batch-actions :deep(.ant-btn) {
  min-width: 7rem;
}

.album-form {
  padding-bottom: 1rem;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  column-gap: 1rem;
}

.file-picker {
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 0.5rem;
  align-items: center;
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

.rich-form-item :deep(.ant-form-item-control-input-content) {
  min-width: 0;
}

@media (max-width: 768px) {
  .form-grid,
  .file-picker {
    grid-template-columns: 1fr;
  }
}
</style>
