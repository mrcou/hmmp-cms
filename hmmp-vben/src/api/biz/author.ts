import { requestClient } from '#/api/request';

export namespace AuthorApi {
  export interface SubmissionAttachment {
    /** copyright | letter | cnki | ethics | fund | custom */
    type?: string;
    label: string;
    path: string;
    required?: boolean;
  }

  export interface SubmissionAuthor {
    sequence?: number;
    isContactAuthor?: boolean;
    isFirstAuthor?: boolean;
    realName: string;
    pinYin?: string;
    sex?: string;
    birthday?: string;
    unit?: string;
    enUnit?: string;
    address?: string;
    postcode?: string;
    country?: string;
    province?: string;
    major?: string;
    education?: string;
    title?: string;
    tel?: string;
    mobile?: string;
    email?: string;
    resume?: string;
  }

  export interface Submission {
    submissionId?: number;
    userId?: number;
    manuscriptId?: number;
    title: string;
    /** 摘要（对接后端 abstractText） */
    abstractText?: string;
    /** @deprecated 兼容旧字段名 */
    abstract?: string;
    keywords?: string;
    filePath?: string;
    remark?: string;
    enTitle?: string;
    enKeywords?: string;
    enAbstract?: string;
    fundProject?: string;
    enFundProject?: string;
    studyClassCode?: string;
    clcNumber?: string;
    pageNum?: number;
    authors?: SubmissionAuthor[];
    /** 附加材料（后端就绪后落库） */
    attachments?: SubmissionAttachment[];
    submissionTime?: string;
    status?: string;
    feedback?: string;
    feedbackTime?: string;
    isDeleted?: string;
  }
}

/** 通用文件上传 */
export async function uploadAuthorFile(file: File) {
  return requestClient.upload('/common/upload', { file });
}

export interface ManuscriptMeta {
  title?: string;
  enTitle?: string;
  keywords?: string[];
  enKeywords?: string[];
  abstractText?: string;
  enAbstract?: string;
  rawPreview?: string;
}

/** 扫描已上传 Word，自动提取标题/英文标题/关键词/摘要 */
export async function parseManuscriptMeta(filePath: string) {
  return requestClient.post<ManuscriptMeta>('/author/manuscript/parseMeta', {
    filePath,
  });
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