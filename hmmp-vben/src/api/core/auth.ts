import { requestClient } from '#/api/request';
import { useCaptcha } from '#/views/_core/authentication/use-captcha';

export namespace AuthApi {
  /** 登录接口参数 */
  export interface LoginParams {
    code?: string;
    password?: string;
    username?: string;
  }

  /** 登录接口返回值 */
  export interface LoginResult {
    accessToken: string;
  }
}

/**
 * 登录
 */
export async function loginApi(data: AuthApi.LoginParams) {
  const { state } = useCaptcha();
  const res = await requestClient.post<{ token: string }>('/login', {
    ...data,
    uuid: state.uuid,
  });
  return { accessToken: res.token };
}

/**
 * 退出登录
 */
export async function logoutApi() {
  return requestClient.post('/logout', {}, { withCredentials: true });
}

/**
 * 获取用户权限码
 */
export async function getAccessCodesApi() {
  const res = await requestClient.get<{ permissions: string[] }>('/getInfo');
  return res.permissions ?? [];
}
