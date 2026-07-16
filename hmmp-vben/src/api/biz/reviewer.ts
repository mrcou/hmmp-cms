import { requestClient } from '#/api/request';

export namespace ReviewerApi {
  export interface Task {
    taskId?: number;
    auditId?: number;
    manuscriptId?: number;
    reviewerId?: number;
    title?: string;
    abstract?: string;
    taskStatus?: string;
    acceptTime?: string;
    rejectReason?: string;
    deadlineTime?: string;
    submitTime?: string;
  }
}

/** 新邀请审稿列表 */
export async function getNewInviteList(params: any) {
  return requestClient.get('/reviewer/manuscript/newInvite', { params });
}

/** 未审完稿件列表 */
export async function getReviewingList(params: any) {
  return requestClient.get('/reviewer/manuscript/reviewing', { params });
}

/** 已审完稿件列表 */
export async function getReviewedList(params: any) {
  return requestClient.get('/reviewer/manuscript/reviewed', { params });
}

/** 接受审稿 */
export async function acceptReview(data: any) {
  return requestClient.put('/reviewer/manuscript/accept', data);
}

/** 拒绝审稿 */
export async function rejectReview(data: any) {
  return requestClient.put('/reviewer/manuscript/reject', data);
}

/** 提交审稿意见 */
export async function submitReview(data: any) {
  return requestClient.post('/reviewer/manuscript/submit', data);
}