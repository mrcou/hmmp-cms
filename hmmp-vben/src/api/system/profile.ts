import { requestClient } from '#/api/request';

export namespace ProfileApi {
  export interface Profile {
    avatar?: string;
    email?: string;
    nickName: string;
    phonenumber?: string;
    sex?: string;
    userName: string;
    createTime?: string;
    dept?: { deptName: string };
  }

  export interface ProfileResult {
    data: Profile;
    postGroup: string;
    roleGroup: string;
  }
}

export async function getProfile() {
  return requestClient.get<ProfileApi.ProfileResult>('/system/user/profile');
}

export async function updateProfile(data: Partial<ProfileApi.Profile>) {
  return requestClient.put('/system/user/profile', data);
}

export async function updateProfilePwd(oldPassword: string, newPassword: string) {
  return requestClient.put('/system/user/profile/updatePwd', {
    oldPassword,
    newPassword,
  });
}

export async function uploadAvatar(file: File) {
  const formData = new FormData();
  formData.append('avatarfile', file);
  return requestClient.post<{ imgUrl: string }>(
    '/system/user/profile/avatar',
    formData,
  );
}
