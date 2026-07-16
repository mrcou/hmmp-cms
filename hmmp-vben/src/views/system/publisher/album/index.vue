<template>
  <div class="publisher-album-container">
    <a-card title="虚拟专辑管理" :bordered="false">
      <div style="margin-bottom: 16px">
        <a-button type="primary" @click="openCreate">新增专辑</a-button>
        <a-button style="margin-left: 8px" @click="fetchData">刷新</a-button>
      </div>
      <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination" row-key="albumId" @change="handleTableChange">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === '0' ? 'green' : 'red'">{{ record.status === '0' ? '正常' : '停用' }}</a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a @click="openEdit(record)">编辑</a>
              <a @click="openArticles(record)">管理文章</a>
              <a-popconfirm title="确认删除?" @confirm="handleDelete(record)">
                <a style="color: red">删除</a>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
      <a-modal v-model:open="modalOpen" :title="isEdit ? '编辑专辑' : '新增专辑'" @ok="handleSubmit" @cancel="modalOpen = false">
        <a-form :model="formData" layout="vertical">
          <a-form-item label="专辑名称" required>
            <a-input v-model:value="formData.albumName" />
          </a-form-item>
          <a-form-item label="专辑描述">
            <a-textarea v-model:value="formData.description" :rows="3" />
          </a-form-item>
          <a-form-item label="排序号">
            <a-input-number v-model:value="formData.orderNum" :min="0" style="width: 100%" />
          </a-form-item>
          <a-form-item label="状态">
            <a-select v-model:value="formData.status">
              <a-select-option value="0">正常</a-select-option>
              <a-select-option value="1">停用</a-select-option>
            </a-select>
          </a-form-item>
        </a-form>
      </a-modal>
      <a-modal v-model:open="articleModalOpen" title="管理专辑文章" width="800px" @cancel="articleModalOpen = false" :footer="null">
        <a-transfer
          :data-source="allArticles"
          :target-keys="selectedArticleIds"
          :render="(item: any) => item.title"
          :titles="['可选文章', '已选文章']"
          @change="onArticleChange"
          style="margin-bottom: 16px"
        />
        <a-button type="primary" @click="saveArticles">保存</a-button>
      </a-modal>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { message } from 'antdv-next';
import { requestClient } from '#/api/request';

const loading = ref(false);
const modalOpen = ref(false);
const articleModalOpen = ref(false);
const isEdit = ref(false);
const currentAlbum = ref<any>(null);
const dataSource = ref([]);
const allArticles = ref([]);
const selectedArticleIds = ref<string[]>([]);
const formData = reactive({ albumId: null, albumName: '', description: '', orderNum: 0, status: '0' });
const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

const columns = [
  { title: 'ID', dataIndex: 'albumId', key: 'albumId', width: 80 },
  { title: '专辑名称', dataIndex: 'albumName', key: 'albumName', ellipsis: true },
  { title: '描述', dataIndex: 'description', key: 'description', ellipsis: true },
  { title: '文章数', dataIndex: 'articleCount', key: 'articleCount', width: 100 },
  { title: '状态', key: 'status', width: 100 },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 180 },
  { title: '操作', key: 'action', width: 220, fixed: 'right' },
];

const fetchData = async () => {
  loading.value = true;
  try {
    const params = { pageNum: pagination.current, pageSize: pagination.pageSize };
    const res = await requestClient.get('/publisher/album/list', { params });
    dataSource.value = res.rows || [];
    pagination.total = res.total || 0;
  } catch (e) { message.error('获取数据失败'); } finally { loading.value = false; }
};

const handleTableChange = (pag: any) => { pagination.current = pag.current; pagination.pageSize = pag.pageSize; fetchData(); };

const openCreate = () => { isEdit.value = false; Object.assign(formData, { albumId: null, albumName: '', description: '', orderNum: 0, status: '0' }); modalOpen.value = true; };

const openEdit = (record: any) => { isEdit.value = true; Object.assign(formData, record); modalOpen.value = true; };

const openArticles = async (record: any) => {
  currentAlbum.value = record;
  try {
    const res = await requestClient.get('/publisher/article/simpleList');
    allArticles.value = (res.rows || []).map((item: any) => ({ key: String(item.articleId), title: item.title }));
    const albumRes = await requestClient.get(`/publisher/album/articles/${record.albumId}`);
    selectedArticleIds.value = (albumRes.rows || []).map((item: any) => String(item.articleId));
    articleModalOpen.value = true;
  } catch (e) { message.error('获取数据失败'); }
};

const onArticleChange = (targetKeys: string[]) => { selectedArticleIds.value = targetKeys; };

const saveArticles = async () => {
  try {
    await requestClient.post(`/publisher/album/${currentAlbum.value.albumId}/articles`, { articleIds: selectedArticleIds.value });
    message.success('保存成功');
    articleModalOpen.value = false;
    fetchData();
  } catch (e) { message.error('保存失败'); }
};

const handleSubmit = async () => {
  try {
    isEdit.value ? await requestClient.put('/publisher/album', formData) : await requestClient.post('/publisher/album', formData);
    message.success(isEdit.value ? '修改成功' : '新增成功');
    modalOpen.value = false;
    fetchData();
  } catch (e) { message.error('操作失败'); }
};

const handleDelete = async (record: any) => {
  try {
    await requestClient.delete(`/publisher/album/${record.albumId}`);
    message.success('删除成功');
    fetchData();
  } catch (e) { message.error('删除失败'); }
};

onMounted(() => fetchData());
</script>