import { downloadExcel } from '#/api/core/download';
import { requestClient } from '#/api/request';

export namespace StatisticsApi {
  /** 审理周期统计明细行 */
  export interface AuditCycleRow {
    manuscriptId?: number;
    fileNo?: string;
    title?: string;
    authorNames?: string;
    receiveTime?: string;
    conclusionTime?: string;
    conclusion?: string;
    status?: string;
    year?: number;
    period?: number | string;
    columnName?: string;
    auditCycleDays?: number;
  }

  export interface AuditCycleListParams {
    fileNo?: string;
    title?: string;
    authorNames?: string;
    status?: string;
    conclusion?: string;
    year?: number;
    period?: number | string;
    columnName?: string;
    'params[beginTime]'?: string;
    'params[endTime]'?: string;
    pageNum?: number;
    pageSize?: number;
  }

  /** 责编处理周期统计明细行 */
  export interface EditProcessCycleRow {
    manuscriptId?: number;
    fileNo?: string;
    title?: string;
    authorNames?: string;
    editorName?: string;
    conclusionTime?: string;
    conclusion?: string;
    status?: string;
    responsibleEditor?: string;
    year?: number;
    period?: number | string;
    columnName?: string;
    editProcessCycleDays?: number;
    assignTime?: string;
  }

  export interface EditProcessCycleListParams {
    fileNo?: string;
    title?: string;
    authorNames?: string;
    editorName?: string;
    responsibleEditor?: string;
    status?: string;
    conclusion?: string;
    year?: number;
    period?: number | string;
    columnName?: string;
    'params[beginTime]'?: string;
    'params[endTime]'?: string;
    pageNum?: number;
    pageSize?: number;
  }

  /** 加工周期统计明细行 */
  export interface EditArticleCycleRow {
    manuscriptId?: number;
    editId?: number;
    fileNo?: string;
    title?: string;
    authorNames?: string;
    status?: string;
    editorName?: string;
    editEndTime?: string;
    editStartTime?: string;
    year?: number;
    period?: number | string;
    columnName?: string;
    editArticleCycleDays?: number;
    editStatus?: string;
  }

  export interface EditArticleCycleListParams {
    fileNo?: string;
    title?: string;
    authorNames?: string;
    status?: string;
    editorName?: string;
    editStatus?: string;
    year?: number;
    period?: number | string;
    columnName?: string;
    'params[beginTime]'?: string;
    'params[endTime]'?: string;
    pageNum?: number;
    pageSize?: number;
  }

  /** 审稿周期统计明细行（按审稿记录） */
  export interface ReviewCycleRow {
    auditId?: number;
    manuscriptId?: number;
    fileNo?: string;
    title?: string;
    authorNames?: string;
    reviewerName?: string;
    columnName?: string;
    reviewRound?: number;
    receiveTime?: string;
    inviteTime?: string;
    auditTime?: string;
    auditResult?: string;
    reviewCycleDays?: number;
    status?: string;
  }

  export interface ReviewCycleListParams {
    fileNo?: string;
    title?: string;
    authorNames?: string;
    reviewerName?: string;
    status?: string;
    columnName?: string;
    auditResult?: string;
    reviewRound?: number;
    'params[beginTime]'?: string;
    'params[endTime]'?: string;
    pageNum?: number;
    pageSize?: number;
  }

  /** 出版周期统计明细行 */
  export interface PublishCycleRow {
    manuscriptId?: number;
    fileNo?: string;
    title?: string;
    authorNames?: string;
    year?: number;
    period?: number | string;
    columnName?: string;
    pageStart?: number;
    pageEnd?: number;
    paperPublishTime?: string;
    onlinePublishTime?: string;
    status?: string;
  }

  export interface PublishCycleListParams {
    fileNo?: string;
    title?: string;
    authorNames?: string;
    status?: string;
    year?: number;
    period?: number | string;
    columnName?: string;
    'params[beginTime]'?: string;
    'params[endTime]'?: string;
    pageNum?: number;
    pageSize?: number;
  }

  /** 期刊统计查询参数（时间支持年 / 年-月 / 年-月-日） */
  export interface JournalTotalParams {
    beginTime?: string;
    endTime?: string;
    magazineId?: string;
  }

  /** 期刊统计聚合结果 */
  export interface JournalTotal {
    yearSpan?: number;
    receiveCount?: number;
    avgReceiveCount?: number;
    publishCount?: number;
    avgPublishCount?: number;
    publishRate?: number;
    rejectCount?: number;
    avgRejectCount?: number;
    rejectRate?: number;
    initialRejectCount?: number;
    avgInitialRejectCount?: number;
    initialRejectRate?: number;
    reviewRejectCount?: number;
    avgReviewRejectCount?: number;
    reviewRejectRate?: number;
    reReviewRejectCount?: number;
    avgReReviewRejectCount?: number;
    reReviewRejectRate?: number;
    finalRejectCount?: number;
    avgFinalRejectCount?: number;
    finalRejectRate?: number;
    adoptCount?: number;
    avgAdoptCount?: number;
    adoptRate?: number;
    sendReviewCount?: number;
    avgSendReviewCount?: number;
    sendReviewRate?: number;
    fundCount?: number;
    avgFundCount?: number;
    fundRate?: number;
    initialReviewTimes?: number;
    peerReviewTimes?: number;
    reReviewTimes?: number;
    finalReviewTimes?: number;
  }

  /** 责编工作量统计查询参数 */
  export interface EditWorkParams {
    beginTime?: string;
    endTime?: string;
    /** receiveTime | assignTime | reviewStartTime | conclusionTime | publishTime */
    dateField?: string;
    magazineId?: string;
  }

  /** 责编工作量统计行 */
  export interface EditWorkRow {
    editorId?: number | null;
    editorName?: string;
    deptName?: string;
    totalCount?: number;
    notSentCount?: number;
    reviseCount?: number;
    resubmittedCount?: number;
    reviewingCount?: number;
    returnedCount?: number;
    notReturnedCount?: number;
    adoptCount?: number;
    rejectCount?: number;
    publishCount?: number;
  }

  /** 责编加工工作量统计查询参数 */
  export interface EditProductionParams {
    beginTime?: string;
    endTime?: string;
    /** editEndTime | editStartTime | createTime */
    dateField?: string;
  }

  /** 责编加工工作量统计行 */
  export interface EditProductionRow {
    editorId?: number | null;
    editorName?: string;
    editUnfinishedCount?: number;
    editFinishedCount?: number;
    editUnfinishedWords?: number;
    editFinishedWords?: number;
    layoutUnfinishedCount?: number;
    layoutFinishedCount?: number;
    layoutUnfinishedWords?: number;
    layoutFinishedWords?: number;
    proofUnfinishedCount?: number;
    proofFinishedCount?: number;
    proofUnfinishedWords?: number;
    proofFinishedWords?: number;
    finalizeUnfinishedCount?: number;
    finalizeFinishedCount?: number;
    finalizeUnfinishedWords?: number;
    finalizeFinishedWords?: number;
  }

  /** 用户统计查询参数 */
  export interface UserStatListParams {
    userName?: string;
    realName?: string;
    email?: string;
    /** author 仅投稿用户 | reviewer 仅审稿用户 | 空表示全部 */
    role?: string;
    beginTime?: string;
    endTime?: string;
    pageNum?: number;
    pageSize?: number;
  }

  /** 用户统计行 */
  export interface UserStatRow {
    userId?: number;
    userName?: string;
    realName?: string;
    affiliation?: string;
    email?: string;
    submissionCount?: number;
    publishCount?: number;
    reviewManuscriptCount?: number;
    reviewTimes?: number;
    completedReviewCount?: number;
    reviewScoreAvg?: number;
    acceptedCount?: number;
  }

  /** 审稿统计查询参数 */
  export interface ReviewerAuditListParams {
    userName?: string;
    realName?: string;
    affiliation?: string;
    email?: string;
    mobile?: string;
    expertiseFields?: string;
    searchKeywords?: string;
    permissionLevel?: string;
    beginTime?: string;
    endTime?: string;
    pageNum?: number;
    pageSize?: number;
  }

  /** 审稿统计行 */
  export interface ReviewerAuditRow {
    reviewerId?: number;
    userId?: number;
    userName?: string;
    realName?: string;
    affiliation?: string;
    mobile?: string;
    email?: string;
    idCard?: string;
    expertiseFields?: string;
    searchKeywords?: string;
    permissionLevel?: string;
    reputation?: number;
    returnedCount?: number;
    reReviewReturnedCount?: number;
    returnedManuscriptCount?: number;
    reviewCycleDays?: number;
    reviewScoreAvg?: number;
    returnRate?: number;
    reviewFee?: number;
  }

  /** 被引文章查询参数 */
  export interface ReferencedArticleListParams {
    title?: string;
    authorNames?: string;
    year?: number;
    period?: number | string;
    magazineId?: string;
    'params[beginYear]'?: string;
    'params[endYear]'?: string;
    pageNum?: number;
    pageSize?: number;
  }

  /** 被引文章查询行 */
  export interface ReferencedArticleRow {
    articleId?: number;
    title?: string;
    authorNames?: string;
    year?: number;
    period?: number | string;
    pageStart?: number;
    pageEnd?: number;
    citedCount?: number;
    selfCitedCount?: number;
    otherCitedCount?: number;
  }

  export interface StatData {
    statId?: number;
    statType?: string;
    statName?: string;
    statDate?: string;
    year?: number;
    month?: number;
    dataValue?: number;
    dataUnit?: string;
    dataJson?: string;
    statTime?: string;
  }

  export interface SiteAccess {
    accessId?: number;
    userId?: number;
    userName?: string;
    pageUrl?: string;
    pageName?: string;
    refererUrl?: string;
    ipAddr?: string;
    browser?: string;
    os?: string;
    accessTime?: string;
    stayTime?: number;
  }

  /** 网站访问量按年汇总 */
  export interface SiteVisitRow {
    visitYear?: number;
    pvCount?: number;
    uvCount?: number;
    loginCount?: number;
    loginRate?: number;
    anonCount?: number;
    anonRate?: number;
    pageCount?: number;
    avgStayTime?: number;
    minStayTime?: number;
    maxStayTime?: number;
  }

  export interface SiteVisitParams {
    beginTime?: string;
    endTime?: string;
  }
}

/** 综合统计 */
export async function getJournalTotal(params?: StatisticsApi.JournalTotalParams) {
  return requestClient.get('/statistics/integrative/total', { params });
}

export async function getAuditCycle(params: any) {
  return requestClient.get('/statistics/integrative/auditCycle', { params });
}

/** 审理周期统计明细列表（分页） */
export async function getAuditCycleList(params: StatisticsApi.AuditCycleListParams) {
  return requestClient.get('/statistics/integrative/auditCycle/list', { params });
}

/** 审理周期统计明细导出（后端按查询串绑定条件，这里拼到 URL 上） */
export async function exportAuditCycleList(params: StatisticsApi.AuditCycleListParams) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries(params as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/auditCycle/export${query ? `?${query}` : ''}`,
    {},
    '审理周期统计.xlsx',
  );
}

export async function getPublishCycle(params: any) {
  return requestClient.get('/statistics/integrative/publishCycle', { params });
}

/** 出版周期统计明细列表（分页） */
export async function getPublishCycleList(params: StatisticsApi.PublishCycleListParams) {
  return requestClient.get('/statistics/integrative/publishCycle/list', { params });
}

/** 出版周期统计明细导出 */
export async function exportPublishCycleList(params: StatisticsApi.PublishCycleListParams) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries(params as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/publishCycle/export${query ? `?${query}` : ''}`,
    {},
    '出版周期统计.xlsx',
  );
}

export async function getEditorCycle(params: any) {
  return requestClient.get('/statistics/integrative/editProcessCycle', { params });
}

/** 责编处理周期统计明细列表（分页） */
export async function getEditProcessCycleList(
  params: StatisticsApi.EditProcessCycleListParams,
) {
  return requestClient.get('/statistics/integrative/editProcessCycle/list', { params });
}

/** 责编处理周期统计明细导出 */
export async function exportEditProcessCycleList(
  params: StatisticsApi.EditProcessCycleListParams,
) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries(params as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/editProcessCycle/export${query ? `?${query}` : ''}`,
    {},
    '责编处理周期统计.xlsx',
  );
}

export async function getEditArticleCycle(params: any) {
  return requestClient.get('/statistics/integrative/editArticleCycle', { params });
}

/** 加工周期统计明细列表（分页） */
export async function getEditArticleCycleList(
  params: StatisticsApi.EditArticleCycleListParams,
) {
  return requestClient.get('/statistics/integrative/editArticleCycle/list', { params });
}

/** 加工周期统计明细导出 */
export async function exportEditArticleCycleList(
  params: StatisticsApi.EditArticleCycleListParams,
) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries(params as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/editArticleCycle/export${query ? `?${query}` : ''}`,
    {},
    '加工周期统计.xlsx',
  );
}

export async function getEditorWorkload(params?: StatisticsApi.EditWorkParams) {
  return requestClient.get<StatisticsApi.EditWorkRow[]>('/statistics/integrative/editWork', {
    params,
  });
}

/** 责编工作量统计导出 */
export async function exportEditorWorkload(params?: StatisticsApi.EditWorkParams) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries((params ?? {}) as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/editWork/export${query ? `?${query}` : ''}`,
    {},
    '责编工作量统计.xlsx',
  );
}

/** 责编加工工作量统计 */
export async function getEditProduction(params?: StatisticsApi.EditProductionParams) {
  return requestClient.get<StatisticsApi.EditProductionRow[]>(
    '/statistics/integrative/editProduction',
    { params },
  );
}

/** 责编加工工作量统计导出 */
export async function exportEditProduction(params?: StatisticsApi.EditProductionParams) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries((params ?? {}) as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/editProduction/export${query ? `?${query}` : ''}`,
    {},
    '责编加工工作量.xlsx',
  );
}

export async function getAuditStat(params: any) {
  return requestClient.get('/statistics/integrative/reviewerAudit', { params });
}

/** 审稿统计明细列表（分页） */
export async function getReviewerAuditList(params: StatisticsApi.ReviewerAuditListParams) {
  return requestClient.get('/statistics/integrative/reviewerAudit/list', { params });
}

/** 审稿统计导出 */
export async function exportReviewerAuditList(params: StatisticsApi.ReviewerAuditListParams) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries(params as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/reviewerAudit/export${query ? `?${query}` : ''}`,
    {},
    '审稿统计.xlsx',
  );
}

export async function getCitedArticle(params: any) {
  return requestClient.get('/statistics/integrative/referencedArticle', { params });
}

/** 被引文章查询明细列表（分页） */
export async function getReferencedArticleList(
  params: StatisticsApi.ReferencedArticleListParams,
) {
  return requestClient.get('/statistics/integrative/referencedArticle/list', { params });
}

/** 被引文章查询导出 */
export async function exportReferencedArticleList(
  params: StatisticsApi.ReferencedArticleListParams,
) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries(params as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/referencedArticle/export${query ? `?${query}` : ''}`,
    {},
    '被引文章查询.xlsx',
  );
}

export async function getReviewCycle(params: any) {
  return requestClient.get('/statistics/integrative/reviewCycle', { params });
}

/** 审稿周期统计明细列表（分页） */
export async function getReviewCycleList(params: StatisticsApi.ReviewCycleListParams) {
  return requestClient.get('/statistics/integrative/reviewCycle/list', { params });
}

/** 审稿周期统计明细导出 */
export async function exportReviewCycleList(params: StatisticsApi.ReviewCycleListParams) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries(params as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/reviewCycle/export${query ? `?${query}` : ''}`,
    {},
    '审稿周期统计.xlsx',
  );
}

export async function getUserStat(params: any) {
  return requestClient.get('/statistics/integrative/userStat', { params });
}

/** 用户统计明细列表（分页） */
export async function getUserStatList(params: StatisticsApi.UserStatListParams) {
  return requestClient.get('/statistics/integrative/userStat/list', { params });
}

/** 用户统计导出 */
export async function exportUserStatList(params: StatisticsApi.UserStatListParams) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries(params as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/integrative/userStat/export${query ? `?${query}` : ''}`,
    {},
    '用户统计.xlsx',
  );
}

/** 网站访问量按年汇总 */
export async function getSiteVisitTotal(params?: StatisticsApi.SiteVisitParams) {
  return requestClient.get('/statistics/site/total', { params });
}

/** 网站访问量统计导出 */
export async function exportSiteVisitTotal(params?: StatisticsApi.SiteVisitParams) {
  const qs = new URLSearchParams();
  for (const [key, value] of Object.entries((params ?? {}) as Record<string, any>)) {
    if (value !== undefined && value !== null && value !== '') {
      qs.append(key, String(value));
    }
  }
  const query = qs.toString();
  return downloadExcel(
    `/statistics/site/total/export${query ? `?${query}` : ''}`,
    {},
    '网站访问量统计.xlsx',
  );
}

/** 网站访问日志明细（分页） */
export async function getSiteAccessList(params: any) {
  return requestClient.get('/statistics/site/total/list', { params });
}
