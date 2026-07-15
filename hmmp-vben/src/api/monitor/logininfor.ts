import { requestClient } from '#/api/request';

export namespace MonitorLogininforApi {
  export interface SysLogininfor {
    browser?: string;
    infoId?: number;
    ipaddr?: string;
    loginLocation?: string;
    loginTime?: string;
    msg?: string;
    os?: string;
    status?: string;
    userName?: string;
  }

  export interface LogininforListParams {
    ipaddr?: string;
    pageNum?: number;
    pageSize?: number;
    status?: string;
    userName?: string;
  }
}

/** 登录日志分页列表 */
export async function getLogininforList(
  params: MonitorLogininforApi.LogininforListParams,
) {
  return requestClient.get<{
    rows: MonitorLogininforApi.SysLogininfor[];
    total: number;
  }>('/monitor/logininfor/list', { params });
}

/** 删除登录日志（支持批量） */
export async function deleteLogininfor(infoIds: number | number[]) {
  const ids = Array.isArray(infoIds) ? infoIds.join(',') : infoIds;
  return requestClient.delete(`/monitor/logininfor/${ids}`);
}

/** 清空登录日志 */
export async function cleanLogininfor() {
  return requestClient.delete('/monitor/logininfor/clean');
}

/** 解锁账号 */
export async function unlockLogininfor(userName: string) {
  return requestClient.get(`/monitor/logininfor/unlock/${userName}`);
}

/** 导出登录日志 */
export async function exportLogininfor(
  params: MonitorLogininforApi.LogininforListParams,
) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/monitor/logininfor/export', params, '登录日志.xlsx');
}
