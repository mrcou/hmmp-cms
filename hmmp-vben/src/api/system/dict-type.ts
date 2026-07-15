import { requestClient } from '#/api/request';

export namespace SystemDictTypeApi {
  export interface SysDictType {
    createTime?: string;
    dictId?: number;
    dictName: string;
    dictType: string;
    remark?: string;
    status: string;
  }

  export interface DictTypeListParams {
    dictName?: string;
    dictType?: string;
    pageNum?: number;
    pageSize?: number;
    status?: string;
  }
}

/** 字典类型分页列表 */
export async function getDictTypeList(
  params: SystemDictTypeApi.DictTypeListParams,
) {
  return requestClient.get<{
    rows: SystemDictTypeApi.SysDictType[];
    total: number;
  }>('/system/dict/type/list', { params });
}

/** 字典类型详情 */
export async function getDictType(dictId: number) {
  const res = await requestClient.get<{ data: SystemDictTypeApi.SysDictType }>(
    `/system/dict/type/${dictId}`,
  );
  return res.data;
}

export async function createDictType(data: SystemDictTypeApi.SysDictType) {
  return requestClient.post('/system/dict/type', data);
}

export async function updateDictType(data: SystemDictTypeApi.SysDictType) {
  return requestClient.put('/system/dict/type', data);
}

export async function deleteDictType(dictId: number | number[]) {
  const ids = Array.isArray(dictId) ? dictId.join(',') : dictId;
  return requestClient.delete(`/system/dict/type/${ids}`);
}
