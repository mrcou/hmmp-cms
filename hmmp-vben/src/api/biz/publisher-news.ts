import { requestClient } from '#/api/request';

export namespace PublisherNewsApi {
  export interface Column {
    columnId?: number;
    columnName: string;
    sortOrder?: number;
    contentCount?: number;
    createTime?: string;
    updateTime?: string;
  }

  export interface Content {
    contentId?: number;
    columnId?: number;
    columnName?: string;
    title: string;
    summary?: string;
    content: string;
    status?: 'draft' | 'published';
    images?: string;
    videos?: string;
    attachments?: string;
    source?: string;
    author?: string;
    editor?: string;
    sourceUrl?: string;
    publishTime?: string;
    sortOrder?: number;
    createTime?: string;
    updateTime?: string;
  }
}

export async function getNewsColumnList(params?: Record<string, any>) {
  return requestClient.get('/publisher/news/column/list', { params });
}

export async function getNewsColumn(columnId: number) {
  return requestClient.get(`/publisher/news/column/${columnId}`);
}

export async function createNewsColumn(data: PublisherNewsApi.Column) {
  return requestClient.post('/publisher/news/column', data);
}

export async function updateNewsColumn(data: PublisherNewsApi.Column) {
  return requestClient.put('/publisher/news/column', data);
}

export async function deleteNewsColumn(columnIds: number[]) {
  return requestClient.delete(
    `/publisher/news/column/${columnIds.join(',')}`,
  );
}

export async function getNewsContentList(params?: Record<string, any>) {
  return requestClient.get('/publisher/news/content/list', { params });
}

export async function getNewsContent(contentId: number) {
  return requestClient.get(`/publisher/news/content/${contentId}`);
}

export async function createNewsContent(data: PublisherNewsApi.Content) {
  return requestClient.post('/publisher/news/content', data);
}

export async function updateNewsContent(data: PublisherNewsApi.Content) {
  return requestClient.put('/publisher/news/content', data);
}

export async function updateNewsContentSort(
  data: Pick<PublisherNewsApi.Content, 'contentId' | 'sortOrder'>[],
) {
  return requestClient.put('/publisher/news/content/sort', data);
}

export async function deleteNewsContent(contentIds: number[]) {
  return requestClient.delete(
    `/publisher/news/content/${contentIds.join(',')}`,
  );
}

export async function uploadNewsFile(file: File) {
  return requestClient.upload('/common/upload', { file });
}
