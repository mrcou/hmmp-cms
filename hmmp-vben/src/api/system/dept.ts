import { requestClient } from '#/api/request';

export namespace SystemDeptApi {
  export interface SystemDept {
    children?: SystemDept[];
    deptId?: number;
    deptName: string;
    email?: string;
    leader?: string;
    orderNum: number;
    parentId?: number;
    phone?: string;
    status: string;
  }
}

export async function getDeptList(deptName?: string) {
  const res = await requestClient.get<{ data: SystemDeptApi.SystemDept[] }>(
    '/system/dept/list',
    { params: deptName ? { deptName } : {} },
  );
  return res.data;
}

export async function getDeptExcludeChild(deptId: number) {
  const res = await requestClient.get<{ data: SystemDeptApi.SystemDept[] }>(
    `/system/dept/list/exclude/${deptId}`,
  );
  return res.data;
}

export async function getDept(deptId: number) {
  const res = await requestClient.get<{ data: SystemDeptApi.SystemDept }>(
    `/system/dept/${deptId}`,
  );
  return res.data;
}

export async function createDept(data: SystemDeptApi.SystemDept) {
  return requestClient.post('/system/dept', data);
}

export async function updateDept(data: SystemDeptApi.SystemDept) {
  return requestClient.put('/system/dept', data);
}

export async function deleteDept(deptId: number) {
  return requestClient.delete(`/system/dept/${deptId}`);
}
