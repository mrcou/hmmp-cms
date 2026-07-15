import { requestClient } from '#/api/request';

export namespace GenApi {
  export interface GenTableColumn {
    columnId?: number;
    columnComment: string;
    columnName: string;
    columnType: string;
    dictType?: string;
    htmlType: string;
    isEdit: string;
    isInsert: string;
    isList: string;
    isPk: string;
    isQuery: string;
    isRequired: string;
    javaField: string;
    javaType: string;
    queryType: string;
    sort?: number;
  }

  export interface GenTable {
    businessName: string;
    className: string;
    columns?: GenTableColumn[];
    functionAuthor: string;
    functionName: string;
    genPath?: string;
    genType?: string;
    moduleName: string;
    options?: string;
    packageName: string;
    parentMenuId?: number;
    remark?: string;
    subTableFkName?: string;
    subTableName?: string;
    tableComment: string;
    tableId?: number;
    tableName: string;
    tplCategory: string;
    tplWebType?: string;
    treeCode?: string;
    treeName?: string;
    treeParentCode?: string;
  }

  export interface TableListParams {
    pageNum?: number;
    pageSize?: number;
    tableComment?: string;
    tableName?: string;
  }
}

export async function getGenTableList(params: GenApi.TableListParams) {
  return requestClient.get<{ rows: GenApi.GenTable[]; total: number }>(
    '/tool/gen/list',
    { params },
  );
}

export async function getDbTableList(params: GenApi.TableListParams) {
  return requestClient.get<{ rows: GenApi.GenTable[]; total: number }>(
    '/tool/gen/db/list',
    { params },
  );
}

export async function getGenTable(tableId: number) {
  return requestClient.get<{
    data: {
      info: GenApi.GenTable;
      rows: GenApi.GenTableColumn[];
      tables: GenApi.GenTable[];
    };
  }>(`/tool/gen/${tableId}`);
}

export async function importTable(tables: string, tplWebType = '') {
  const params = new URLSearchParams();
  params.set('tables', tables);
  params.set('tplWebType', tplWebType);
  return requestClient.post(`/tool/gen/importTable?${params.toString()}`);
}

export async function updateGenTable(data: GenApi.GenTable) {
  return requestClient.put('/tool/gen', data);
}

export async function deleteGenTable(tableId: number | number[]) {
  const ids = Array.isArray(tableId) ? tableId.join(',') : tableId;
  return requestClient.delete(`/tool/gen/${ids}`);
}

export async function previewGenTable(tableId: number) {
  return requestClient.get<{ data: Record<string, string> }>(
    `/tool/gen/preview/${tableId}`,
  );
}

export async function synchDb(tableName: string) {
  return requestClient.get(`/tool/gen/synchDb/${tableName}`);
}

export async function downloadGenCode(tableName: string) {
  return requestClient.get<Blob>(`/tool/gen/download/${tableName}`, {
    responseType: 'blob',
    responseReturn: 'body',
  });
}

export async function batchDownloadGenCode(tables: string[]) {
  return requestClient.get<Blob>('/tool/gen/batchGenCode', {
    params: { tables: tables.join(',') },
    responseType: 'blob',
    responseReturn: 'body',
  });
}
