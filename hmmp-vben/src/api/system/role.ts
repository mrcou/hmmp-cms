import { requestClient } from '#/api/request';

export namespace SystemRoleApi {
  export interface SystemRole {
    createTime?: string;
    dataScope?: string;
    deptCheckStrictly?: boolean;
    deptIds?: number[];
    menuCheckStrictly?: boolean;
    menuIds?: number[];
    remark?: string;
    roleId?: number;
    roleKey: string;
    roleName: string;
    roleSort: number;
    status: string;
  }

  export interface RoleListParams {
    pageNum?: number;
    pageSize?: number;
    roleKey?: string;
    roleName?: string;
    status?: string;
  }

  export interface MenuTreeNode {
    children?: MenuTreeNode[];
    id: number;
    label: string;
  }

  export interface DeptTreeNode {
    children?: DeptTreeNode[];
    id: number;
    label: string;
  }
}

export async function getRoleList(params: SystemRoleApi.RoleListParams) {
  return requestClient.get<{ rows: SystemRoleApi.SystemRole[]; total: number }>(
    '/system/role/list',
    { params },
  );
}

export async function getRole(roleId: number) {
  const res = await requestClient.get<{ data: SystemRoleApi.SystemRole }>(
    `/system/role/${roleId}`,
  );
  return res.data;
}

export async function createRole(data: SystemRoleApi.SystemRole) {
  return requestClient.post('/system/role', data);
}

export async function updateRole(data: SystemRoleApi.SystemRole) {
  return requestClient.put('/system/role', data);
}

export async function updateRoleDataScope(data: {
  deptIds?: number[];
  roleId: number;
  dataScope: string;
}) {
  return requestClient.put('/system/role/dataScope', data);
}

export async function deleteRole(roleId: number | number[]) {
  const ids = Array.isArray(roleId) ? roleId.join(',') : roleId;
  return requestClient.delete(`/system/role/${ids}`);
}

export async function changeRoleStatus(roleId: number, status: string) {
  return requestClient.put('/system/role/changeStatus', { roleId, status });
}

export async function getRoleMenuTree(roleId: number) {
  return requestClient.get<{
    checkedKeys: number[];
    menus: SystemRoleApi.MenuTreeNode[];
  }>(`/system/menu/roleMenuTreeselect/${roleId}`);
}

export async function getRoleDeptTree(roleId: number) {
  return requestClient.get<{
    checkedKeys: number[];
    depts: SystemRoleApi.DeptTreeNode[];
  }>(`/system/role/deptTree/${roleId}`);
}

export async function exportRole(params: SystemRoleApi.RoleListParams) {
  const { downloadExcel } = await import('#/api/core/download');
  return downloadExcel('/system/role/export', params, '角色数据.xlsx');
}
