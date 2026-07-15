import { requestClient } from '#/api/request';

export namespace SystemDictDataApi {
  export interface SysDictData {
    cssClass?: string;
    createTime?: string;
    dictCode?: number;
    dictLabel: string;
    dictSort?: number;
    dictType: string;
    dictValue: string;
    isDefault?: string;
    listClass?: string;
    remark?: string;
    status: string;
  }

  export interface DictDataListParams {
    dictLabel?: string;
    dictType?: string;
    pageNum?: number;
    pageSize?: number;
    status?: string;
  }
}

/** 字典数据分页列表（需传 dictType 筛选某个类型下的数据） */
export async function getDictDataList(
  params: SystemDictDataApi.DictDataListParams,
) {
  return requestClient.get<{
    rows: SystemDictDataApi.SysDictData[];
    total: number;
  }>('/system/dict/data/list', { params });
}

/** 字典数据详情 */
export async function getDictData(dictCode: number) {
  const res = await requestClient.get<{ data: SystemDictDataApi.SysDictData }>(
    `/system/dict/data/${dictCode}`,
  );
  return res.data;
}

export async function createDictData(data: SystemDictDataApi.SysDictData) {
  return requestClient.post('/system/dict/data', data);
}

export async function updateDictData(data: SystemDictDataApi.SysDictData) {
  return requestClient.put('/system/dict/data', data);
}

export async function deleteDictData(dictCode: number | number[]) {
  const ids = Array.isArray(dictCode) ? dictCode.join(',') : dictCode;
  return requestClient.delete(`/system/dict/data/${ids}`);
}
