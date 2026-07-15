import { requestClient } from '#/api/request';

export namespace RoleUserApi {
  export interface User {
    createTime?: string;
    nickName: string;
    phonenumber?: string;
    userId: number;
    userName: string;
  }

  export interface ListParams {
    pageNum?: number;
    pageSize?: number;
    phonenumber?: string;
    roleId: number;
    userName?: string;
  }
}

export async function getAllocatedUserList(params: RoleUserApi.ListParams) {
  return requestClient.get<{ rows: RoleUserApi.User[]; total: number }>(
    '/system/role/authUser/allocatedList',
    { params },
  );
}

export async function getUnallocatedUserList(params: RoleUserApi.ListParams) {
  return requestClient.get<{ rows: RoleUserApi.User[]; total: number }>(
    '/system/role/authUser/unallocatedList',
    { params },
  );
}

export async function cancelAuthUser(roleId: number, userId: number) {
  return requestClient.put('/system/role/authUser/cancel', { roleId, userId });
}

export async function cancelAuthUserAll(roleId: number, userIds: number[]) {
  return requestClient.put(
    `/system/role/authUser/cancelAll?roleId=${roleId}&userIds=${userIds.join(',')}`,
  );
}

export async function selectAuthUserAll(roleId: number, userIds: number[]) {
  return requestClient.put(
    `/system/role/authUser/selectAll?roleId=${roleId}&userIds=${userIds.join(',')}`,
  );
}
