import { requestClient } from '#/api/request';

export namespace SystemConfigApi {
  export interface SysConfig {
    configId?: number;
    configKey: string;
    configName: string;
    configType: string;
    configValue: string;
    createTime?: string;
    remark?: string;
  }

  export interface ConfigListParams {
    configKey?: string;
    configName?: string;
    configType?: string;
    pageNum?: number;
    pageSize?: number;
  }
}

/** 参数配置分页列表 */
export async function getConfigList(params: SystemConfigApi.ConfigListParams) {
  return requestClient.get<{
    rows: SystemConfigApi.SysConfig[];
    total: number;
  }>('/system/config/list', { params });
}

/** 参数配置详情 */
export async function getConfig(configId: number) {
  return requestClient.get<{ data: SystemConfigApi.SysConfig }>(
    `/system/config/${configId}`,
  );
}

export async function createConfig(data: SystemConfigApi.SysConfig) {
  return requestClient.post('/system/config', data);
}

export async function updateConfig(data: SystemConfigApi.SysConfig) {
  return requestClient.put('/system/config', data);
}

export async function deleteConfig(configId: number | number[]) {
  const ids = Array.isArray(configId) ? configId.join(',') : configId;
  return requestClient.delete(`/system/config/${ids}`);
}

/** 导出参数配置数据 */
export async function exportConfig(params: SystemConfigApi.ConfigListParams) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/system/config/export', params, '参数数据.xlsx');
}
