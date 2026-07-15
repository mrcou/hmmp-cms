import { requestClient } from '#/api/request';

export namespace SystemUserApi {
  export interface SysDept {
    ancestors?: string;
    children?: SysDept[];
    deptId: number;
    deptName: string;
    parentId?: number;
  }

  export interface DeptTreeNode {
    children?: DeptTreeNode[];
    icon?: string;
    id: number;
    label: string;
  }

  export interface SysRole {
    roleId: number;
    roleKey: string;
    roleName: string;
  }

  export interface SysPost {
    postId: number;
    postName: string;
  }

  export interface SystemUser {
    avatar?: string;
    createTime?: string;
    dept?: SysDept;
    deptId?: number;
    email?: string;
    nickName: string;
    password?: string;
    phonenumber?: string;
    postIds?: number[];
    remark?: string;
    roleIds?: number[];
    roles?: SysRole[];
    sex?: string;
    status: string;
    userId?: number;
    userName: string;
  }

  export interface UserListParams {
    deptId?: number;
    nickName?: string;
    pageNum?: number;
    pageSize?: number;
    phonenumber?: string;
    status?: string;
    userName?: string;
  }

  export interface UserInfoResult {
    data?: SystemUser;
    postIds?: number[];
    posts: SysPost[];
    roleIds?: number[];
    roles: SysRole[];
  }
}

/** 用户分页列表 */
export async function getUserList(params: SystemUserApi.UserListParams) {
  return requestClient.get<{ rows: SystemUserApi.SystemUser[]; total: number }>(
    '/system/user/list',
    { params },
  );
}

/** 新增/编辑用户时用到的角色、岗位候选数据，编辑时还带出用户详情 */
export async function getUser(userId?: number) {
  return requestClient.get<SystemUserApi.UserInfoResult>(
    userId ? `/system/user/${userId}` : '/system/user/',
  );
}

export async function createUser(data: SystemUserApi.SystemUser) {
  return requestClient.post('/system/user', data);
}

export async function updateUser(data: SystemUserApi.SystemUser) {
  return requestClient.put('/system/user', data);
}

export async function deleteUser(userId: number | number[]) {
  const ids = Array.isArray(userId) ? userId.join(',') : userId;
  return requestClient.delete(`/system/user/${ids}`);
}

export async function resetUserPwd(userId: number, password: string) {
  return requestClient.put('/system/user/resetPwd', { userId, password });
}

export async function changeUserStatus(userId: number, status: string) {
  return requestClient.put('/system/user/changeStatus', { userId, status });
}

export async function getUserDeptTree() {
  return requestClient.get<{ data: SystemUserApi.DeptTreeNode[] }>(
    '/system/user/deptTree',
  );
}

export async function getUserAuthRole(userId: number) {
  return requestClient.get<{
    roles: SystemUserApi.SysRole[];
    user: SystemUserApi.SystemUser;
  }>(`/system/user/authRole/${userId}`);
}

export async function updateUserAuthRole(userId: number, roleIds: number[]) {
  return requestClient.put(
    `/system/user/authRole?userId=${userId}&roleIds=${roleIds.join(',')}`,
  );
}

export async function exportUser(params: SystemUserApi.UserListParams) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/system/user/export', params, '用户数据.xlsx');
}

export async function downloadUserImportTemplate() {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/system/user/importTemplate', {}, '用户导入模板.xlsx');
}

export async function importUserData(file: File, updateSupport: boolean) {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('updateSupport', String(updateSupport));
  return requestClient.post<{ msg: string }>('/system/user/importData', formData);
}
