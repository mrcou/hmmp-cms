import { requestClient } from '#/api/request';

export namespace AuthorApi {
  export interface Submission {
    submissionId?: number;
    userId?: number;
    manuscriptId?: number;
    title: string;
    abstract?: string;
    keywords?: string;
    filePath?: string;
    submissionTime?: string;
    status?: string;
    feedback?: string;
    feedbackTime?: string;
    isDeleted?: string;
  }
}

/** 最新消息 */
export async function getAuthorMessages(params: any) {
  return requestClient.get('/author/center', { params });
}

/** 提交新稿 */
export async function submitNewArticle(data: AuthorApi.Submission) {
  return requestClient.post('/author/manuscript/contribution', data);
}

/** 待修改稿 */
export async function getRevisionList(params: any) {
  return requestClient.get('/author/manuscript/revision', { params });
}

/** 稿件查询 */
export async function getSubmissionList(params: any) {
  return requestClient.get('/author/manuscript/search', { params });
}

export async function submitRevision(data: AuthorApi.Submission) {
  return requestClient.put('/author/manuscript/revision', data);
}

/** 修改稿件 */
export async function updateSubmission(data: AuthorApi.Submission) {
  return requestClient.put('/author/manuscript', data);
}

/** 删除稿件 */
export async function deleteSubmission(submissionIds: number[]) {
  return requestClient.delete(`/author/manuscript/${submissionIds.join(',')}`);
}