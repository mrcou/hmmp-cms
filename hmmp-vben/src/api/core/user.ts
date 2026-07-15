import type { UserInfo } from '@vben/types';

import { preferences } from '@vben/preferences';

import { requestClient } from '#/api/request';

interface GetInfoResult {
  permissions: string[];
  roles: string[];
  user: {
    avatar?: string;
    nickName: string;
    userId: number;
    userName: string;
  };
}

/**
 * 获取用户信息
 */
export async function getUserInfoApi(): Promise<UserInfo> {
  const res = await requestClient.get<GetInfoResult>('/getInfo');
  const { user } = res;
  return {
    avatar: user.avatar || preferences.app.defaultAvatar,
    desc: '',
    homePath: preferences.app.defaultHomePath,
    realName: user.nickName || user.userName,
    roles: res.roles,
    token: '',
    userId: `${user.userId}`,
    username: user.userName,
  };
}
