import { requestClient } from '#/api/request';

export namespace MonitorOperlogApi {
  export interface SysOperLog {
    businessType?: number;
    costTime?: number;
    deptName?: string;
    errorMsg?: string;
    jsonResult?: string;
    method?: string;
    operId?: number;
    operIp?: string;
    operLocation?: string;
    operName?: string;
    operParam?: string;
    operTime?: string;
    operUrl?: string;
    operatorType?: number;
    requestMethod?: string;
    status?: number;
    title?: string;
  }

  export interface OperlogListParams {
    businessType?: number | string;
    operName?: string;
    pageNum?: number;
    pageSize?: number;
    status?: number | string;
    title?: string;
  }
}

/** 操作日志分页列表 */
export async function getOperlogList(
  params: MonitorOperlogApi.OperlogListParams,
) {
  return requestClient.get<{
    rows: MonitorOperlogApi.SysOperLog[];
    total: number;
  }>('/monitor/operlog/list', { params });
}

/** 删除操作日志（支持批量） */
export async function deleteOperlog(operIds: number | number[]) {
  const ids = Array.isArray(operIds) ? operIds.join(',') : operIds;
  return requestClient.delete(`/monitor/operlog/${ids}`);
}

/** 清空操作日志 */
export async function cleanOperlog() {
  return requestClient.delete('/monitor/operlog/clean');
}

/** 导出操作日志 */
export async function exportOperlog(
  params: MonitorOperlogApi.OperlogListParams,
) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/monitor/operlog/export', params, '操作日志.xlsx');
}
