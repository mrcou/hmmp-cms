import { requestClient } from '#/api/request';

export namespace MonitorOnlineApi {
  export interface SysUserOnline {
    browser?: string;
    deptName?: string;
    ipaddr?: string;
    loginLocation?: string;
    loginTime?: number;
    os?: string;
    tokenId?: string;
    userName?: string;
  }

  export interface OnlineListParams {
    ipaddr?: string;
    userName?: string;
  }
}

/** 在线用户列表（不分页） */
export async function getOnlineList(params: MonitorOnlineApi.OnlineListParams) {
  const res = await requestClient.get<{
    rows: MonitorOnlineApi.SysUserOnline[];
    total: number;
  }>('/monitor/online/list', { params });
  return res.rows;
}

/** 强退用户 */
export async function forceLogout(tokenId: string) {
  return requestClient.delete(`/monitor/online/${tokenId}`);
}
