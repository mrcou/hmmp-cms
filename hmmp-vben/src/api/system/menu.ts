import { requestClient } from '#/api/request';

export namespace SystemMenuApi {
  export interface SystemMenu {
    children?: SystemMenu[];
    component?: string;
    icon?: string;
    isCache?: string;
    isFrame?: string;
    menuId?: number;
    menuName: string;
    menuType: 'C' | 'F' | 'M';
    orderNum: number;
    parentId?: number;
    path?: string;
    perms?: string;
    query?: string;
    routeName?: string;
    status: string;
    visible: string;
  }

  export interface MenuTreeSelectNode {
    children?: MenuTreeSelectNode[];
    id: number;
    label: string;
  }
}

export async function getMenuList(menuName?: string) {
  const res = await requestClient.get<{ data: SystemMenuApi.SystemMenu[] }>(
    '/system/menu/list',
    { params: menuName ? { menuName } : {} },
  );
  return res.data;
}

export async function getMenu(menuId: number) {
  const res = await requestClient.get<{ data: SystemMenuApi.SystemMenu }>(
    `/system/menu/${menuId}`,
  );
  return res.data;
}

export async function getMenuTreeSelect() {
  const res = await requestClient.get<{
    data: SystemMenuApi.MenuTreeSelectNode[];
  }>('/system/menu/treeselect');
  return res.data;
}

export async function createMenu(data: SystemMenuApi.SystemMenu) {
  return requestClient.post('/system/menu', data);
}

export async function updateMenu(data: SystemMenuApi.SystemMenu) {
  return requestClient.put('/system/menu', data);
}

export async function deleteMenu(menuId: number) {
  return requestClient.delete(`/system/menu/${menuId}`);
}
