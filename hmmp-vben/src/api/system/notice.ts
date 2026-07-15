import { requestClient } from '#/api/request';

export namespace SystemNoticeApi {
  export interface SysNotice {
    createBy?: string;
    createTime?: string;
    isRead?: boolean;
    noticeContent?: string;
    noticeId?: number;
    noticeTitle: string;
    noticeType: string;
    remark?: string;
    status: string;
  }

  export interface NoticeListParams {
    noticeTitle?: string;
    noticeType?: string;
    pageNum?: number;
    pageSize?: number;
  }

  export interface TopNoticeListResult {
    data: SysNotice[];
    unreadCount: number;
  }
}

/** 通知公告分页列表 */
export async function getNoticeList(params: SystemNoticeApi.NoticeListParams) {
  return requestClient.get<{
    rows: SystemNoticeApi.SysNotice[];
    total: number;
  }>('/system/notice/list', { params });
}

/** 顶部通知公告列表 */
export async function getTopNoticeList() {
  return requestClient.get<SystemNoticeApi.TopNoticeListResult>(
    '/system/notice/listTop',
  );
}

/** 通知公告详情 */
export async function getNotice(noticeId: number) {
  return requestClient.get<{ data: SystemNoticeApi.SysNotice }>(
    `/system/notice/${noticeId}`,
  );
}

export async function createNotice(data: SystemNoticeApi.SysNotice) {
  return requestClient.post('/system/notice', data);
}

export async function updateNotice(data: SystemNoticeApi.SysNotice) {
  return requestClient.put('/system/notice', data);
}

export async function deleteNotice(noticeId: number | number[]) {
  const ids = Array.isArray(noticeId) ? noticeId.join(',') : noticeId;
  return requestClient.delete(`/system/notice/${ids}`);
}

/** 标记顶部通知为已读 */
export async function markNoticeRead(noticeId: number | string) {
  const params = new URLSearchParams({ noticeId: String(noticeId) });
  return requestClient.post(`/system/notice/markRead?${params.toString()}`);
}

/** 批量标记顶部通知为已读 */
export async function markAllNoticesRead(noticeIds: Array<number | string>) {
  if (noticeIds.length === 0) {
    return;
  }

  const params = new URLSearchParams({ ids: noticeIds.join(',') });
  return requestClient.post(`/system/notice/markReadAll?${params.toString()}`);
}
