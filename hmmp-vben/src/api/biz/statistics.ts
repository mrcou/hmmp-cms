import { requestClient } from '#/api/request';

export namespace StatisticsApi {
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
}

/** 综合统计 */
export async function getJournalTotal(params: any) {
  return requestClient.get('/statistics/integrative/total', { params });
}

export async function getAuditCycle(params: any) {
  return requestClient.get('/statistics/integrative/auditCycle', { params });
}

export async function getPublishCycle(params: any) {
  return requestClient.get('/statistics/integrative/publishCycle', { params });
}

export async function getEditorCycle(params: any) {
  return requestClient.get('/statistics/integrative/editProcessCycle', { params });
}

export async function getEditArticleCycle(params: any) {
  return requestClient.get('/statistics/integrative/editArticleCycle', { params });
}

export async function getEditorWorkload(params: any) {
  return requestClient.get('/statistics/integrative/editWork', { params });
}

export async function getEditProduction(params: any) {
  return requestClient.get('/statistics/integrative/editProduction', { params });
}

export async function getAuditStat(params: any) {
  return requestClient.get('/statistics/integrative/reviewerAudit', { params });
}

export async function getCitedArticle(params: any) {
  return requestClient.get('/statistics/integrative/referencedArticle', { params });
}

export async function getReviewCycle(params: any) {
  return requestClient.get('/statistics/integrative/reviewCycle', { params });
}

export async function getUserStat(params: any) {
  return requestClient.get('/statistics/integrative/userStat', { params });
}

/** 网站统计 */
export async function getSiteAccessList(params: any) {
  return requestClient.get('/statistics/site/total', { params });
}