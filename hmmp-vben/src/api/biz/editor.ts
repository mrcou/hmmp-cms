import { requestClient } from '#/api/request';

export namespace EditorApi {
  export interface Manuscript {
    manuscriptId?: number;
    fileNo?: string;
    title: string;
    authorIds?: string;
    authorNames?: string;
    correspondingAuthor?: string;
    correspondingEmail?: string;
    abstractText?: string;
    keywords?: string;
    manuscriptType?: string;
    wordCount?: number;
    fundProject?: string;
    fundNo?: string;
    status?: string;
    receiveTime?: string;
    assignTime?: string;
    reviewStartTime?: string;
    reviewEndTime?: string;
    conclusion?: string;
    conclusionTime?: string;
    editorId?: number;
    editorName?: string;
    auditCount?: number;
    revisionCount?: number;
    isDeleted?: string;
    deleteTime?: string;
    deleteBy?: string;
    archiveTime?: string;
    publishTime?: string;
    issueId?: number;
    pageStart?: number;
    pageEnd?: number;
    filePath?: string;
    createBy?: string;
    createTime?: string;
    updateBy?: string;
    updateTime?: string;
    remark?: string;
  }

  export interface ManuscriptListParams {
    fileNo?: string;
    title?: string;
    authorNames?: string;
    status?: string;
    manuscriptType?: string;
    beginTime?: string;
    endTime?: string;
    pageNum?: number;
    pageSize?: number;
  }

  export interface Audit {
    auditId?: number;
    manuscriptId: number;
    reviewerId: number;
    reviewerName?: string;
    reviewRound?: number;
    auditStatus?: string;
    auditOpinion?: string;
    auditScore?: number;
    auditResult?: string;
    inviteTime?: string;
    deadlineTime?: string;
    auditTime?: string;
    withdrawTime?: string;
    withdrawReason?: string;
    acceptWithdrawTime?: string;
    inviteMessage?: string;
  }

  export interface AuditListParams {
    manuscriptId?: number;
    reviewerId?: number;
    auditStatus?: string;
    pageNum?: number;
    pageSize?: number;
  }

  export interface Edit {
    editId?: number;
    manuscriptId: number;
    editorId?: number;
    editorName?: string;
    editStatus?: string;
    editStartTime?: string;
    editEndTime?: string;
    editContent?: string;
    editSuggestion?: string;
    formatCheck?: string;
    wordCountBefore?: number;
    wordCountAfter?: number;
    version?: number;
  }

  export interface Group {
    groupId?: number;
    groupName?: string;
    issueId?: number;
    groupType?: string;
    status?: string;
    remark?: string;
  }

  export interface Author {
    authorId?: number;
    userId?: number;
    realName?: string;
    penName?: string;
    gender?: string;
    birthday?: string;
    nationality?: string;
    affiliation?: string;
    department?: string;
    title?: string;
    academicDegree?: string;
    researchDirection?: string;
    email?: string;
    phone?: string;
    mobile?: string;
    address?: string;
    zipCode?: string;
    idCard?: string;
    workNo?: string;
    status?: string;
    isDeleted?: string;
  }

  export interface Reviewer {
    reviewerId?: number;
    userId?: number;
    realName?: string;
    gender?: string;
    birthday?: string;
    affiliation?: string;
    department?: string;
    title?: string;
    academicDegree?: string;
    researchDirection?: string;
    expertiseFields?: string;
    email?: string;
    phone?: string;
    mobile?: string;
    address?: string;
    zipCode?: string;
    idCard?: string;
    workNo?: string;
    reviewCount?: number;
    acceptRate?: number;
    avgReviewDays?: number;
    reviewScoreAvg?: number;
    status?: string;
    isDeleted?: string;
  }

  export interface ReviewerApply {
    applyId?: number;
    userId?: number;
    applyType?: string;
    recommenderId?: number;
    recommenderName?: string;
    realName?: string;
    affiliation?: string;
    title?: string;
    researchDirection?: string;
    expertiseFields?: string;
    email?: string;
    phone?: string;
    applyReason?: string;
    auditStatus?: string;
    auditOpinion?: string;
    auditTime?: string;
    auditBy?: string;
  }
}

/** 稿件列表 */
export async function getManuscriptList(params: EditorApi.ManuscriptListParams) {
  return requestClient.get('/editor/manuscript/list', { params });
}

/** 稿件详情 */
export async function getManuscript(manuscriptId: number) {
  return requestClient.get(`/editor/manuscript/${manuscriptId}`);
}

/** 新增稿件 */
export async function createManuscript(data: EditorApi.Manuscript) {
  return requestClient.post('/editor/manuscript', data);
}

/** 修改稿件 */
export async function updateManuscript(data: EditorApi.Manuscript) {
  return requestClient.put('/editor/manuscript', data);
}

/** 删除稿件 */
export async function deleteManuscript(manuscriptIds: number[]) {
  return requestClient.delete(`/editor/manuscript/${manuscriptIds.join(',')}`);
}

/** 分配编辑 */
export async function assignEditor(data: Partial<EditorApi.Manuscript>) {
  return requestClient.put('/editor/manuscript/assign', data);
}

/** 送审 */
export async function sendForReview(data: Partial<EditorApi.Manuscript>) {
  return requestClient.put('/editor/manuscript/review', data);
}

/** 退修 */
export async function sendForRevision(data: Partial<EditorApi.Manuscript>) {
  return requestClient.put('/editor/manuscript/revise', data);
}

/** 录用 */
export async function acceptManuscript(data: Partial<EditorApi.Manuscript>) {
  return requestClient.put('/editor/manuscript/adopt', data);
}

/** 退稿 */
export async function rejectManuscript(data: Partial<EditorApi.Manuscript>) {
  return requestClient.put('/editor/manuscript/reject', data);
}

/** 审稿记录列表 */
export async function getAuditList(params: EditorApi.AuditListParams) {
  return requestClient.get('/editor/audit/list', { params });
}

/** 审稿详情 */
export async function getAudit(auditId: number) {
  return requestClient.get(`/editor/audit/${auditId}`);
}

/** 新增审稿（邀请审稿人） */
export async function createAudit(data: EditorApi.Audit) {
  return requestClient.post('/editor/audit/invite', data);
}

/** 撤审 */
export async function withdrawAudit(data: EditorApi.Audit) {
  return requestClient.put('/editor/audit/withdraw', data);
}

/** 接受退审 */
export async function acceptWithdraw(data: EditorApi.Audit) {
  return requestClient.put('/editor/audit/acceptWithdraw', data);
}

/** 审回处理 */
export async function returnAudit(data: EditorApi.Audit) {
  return requestClient.put('/editor/audit/return', data);
}

/** 修改审稿记录 */
export async function updateAudit(data: EditorApi.Audit) {
  return requestClient.put('/editor/audit', data);
}

/** 编辑加工列表 */
export async function getEditList(params: any) {
  return requestClient.get('/editor/edit/list', { params });
}

/** 编辑加工详情 */
export async function getEdit(editId: number) {
  return requestClient.get(`/editor/edit/${editId}`);
}

/** 新增编辑加工 */
export async function createEdit(data: EditorApi.Edit) {
  return requestClient.post('/editor/edit', data);
}

/** 修改编辑加工 */
export async function updateEdit(data: EditorApi.Edit) {
  return requestClient.put('/editor/edit', data);
}

/** 组稿列表 */
export async function getGroupList(params: any) {
  return requestClient.get('/editor/group/list', { params });
}

/** 组稿详情 */
export async function getGroup(groupId: number) {
  return requestClient.get(`/editor/group/${groupId}`);
}

/** 新增组稿 */
export async function createGroup(data: EditorApi.Group) {
  return requestClient.post('/editor/group', data);
}

/** 作者列表 */
export async function getAuthorList(params: any) {
  return requestClient.get('/editor/author/list', { params });
}

/** 作者详情 */
export async function getAuthor(authorId: number) {
  return requestClient.get(`/editor/author/${authorId}`);
}

/** 新增作者 */
export async function createAuthor(data: EditorApi.Author) {
  return requestClient.post('/editor/author', data);
}

/** 修改作者 */
export async function updateAuthor(data: EditorApi.Author) {
  return requestClient.put('/editor/author', data);
}

/** 删除作者 */
export async function deleteAuthor(authorIds: number[]) {
  return requestClient.delete(`/editor/author/${authorIds.join(',')}`);
}

/** 审稿人列表 */
export async function getReviewerList(params: any) {
  return requestClient.get('/editor/reviewer/list', { params });
}

/** 审稿人详情 */
export async function getReviewer(reviewerId: number) {
  return requestClient.get(`/editor/reviewer/${reviewerId}`);
}

/** 新增审稿人 */
export async function createReviewer(data: EditorApi.Reviewer) {
  return requestClient.post('/editor/reviewer', data);
}

/** 修改审稿人 */
export async function updateReviewer(data: EditorApi.Reviewer) {
  return requestClient.put('/editor/reviewer', data);
}

/** 删除审稿人 */
export async function deleteReviewer(reviewerIds: number[]) {
  return requestClient.delete(`/editor/reviewer/${reviewerIds.join(',')}`);
}

/** 审稿人申请列表 */
export async function getReviewerApplyList(params: any) {
  return requestClient.get('/editor/reviewer/apply/list', { params });
}

/** 审核申请 */
export async function auditReviewerApply(data: EditorApi.ReviewerApply) {
  return requestClient.put('/editor/reviewer/apply/audit', data);
}