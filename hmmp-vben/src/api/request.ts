/**
 * 该文件可自行根据业务逻辑进行调整
 * 后端（RuoYi/HMMP）约定：HTTP 状态码恒为 200，业务结果通过响应体 code 字段表达：
 *   200 成功 / 401 未认证 / 403 无权限 / 500 系统异常 / 601 警告
 * 因此不能直接复用 @vben/request 默认的 successCode:0 + dataField:'data' 解包逻辑，
 * 这里自定义响应拦截器按 code 字段分流处理。
 */
import type { RequestClientOptions } from '@vben/request';

import { useAppConfig } from '@vben/hooks';
import { preferences } from '@vben/preferences';
import { errorMessageResponseInterceptor, RequestClient } from '@vben/request';
import { useAccessStore } from '@vben/stores';

import { message } from 'antdv-next';

import { useAuthStore } from '#/store';

const { apiURL } = useAppConfig(import.meta.env, import.meta.env.PROD);

/** 后端业务状态码 */
export const ResultEnum = {
  ERROR: 500,
  SUCCESS: 200,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  WARN: 601,
} as const;

function createRequestClient(baseURL: string, options?: RequestClientOptions) {
  const client = new RequestClient({
    ...options,
    baseURL,
  });

  function formatToken(token: null | string) {
    return token ? `Bearer ${token}` : null;
  }

  // 请求头处理
  client.addRequestInterceptor({
    fulfilled: async (config) => {
      const accessStore = useAccessStore();

      config.headers.Authorization = formatToken(accessStore.accessToken);
      config.headers['Accept-Language'] = preferences.app.locale;
      return config;
    },
  });

  // 处理 RuoYi 风格的响应数据格式（HTTP 恒为200，业务code在body里）
  client.addResponseInterceptor({
    fulfilled: (response) => {
      const { config, data: responseData, status } = response;

      if (config.responseReturn === 'raw') {
        return response;
      }

      if (status < 200 || status >= 400) {
        throw Object.assign({}, response, { response });
      }

      if (config.responseReturn === 'body') {
        return responseData;
      }

      const code = responseData?.code;

      if (code === ResultEnum.SUCCESS) {
        return responseData;
      }

      if (code === ResultEnum.UNAUTHORIZED) {
        const accessStore = useAccessStore();
        const authStore = useAuthStore();
        accessStore.setAccessToken(null);
        if (
          preferences.app.loginExpiredMode === 'modal' &&
          accessStore.isAccessChecked
        ) {
          accessStore.setLoginExpired(true);
        } else {
          authStore.logout();
        }
        throw Object.assign({}, response, { response });
      }

      if (code === ResultEnum.FORBIDDEN) {
        message.error(responseData?.msg || '没有权限访问该资源');
        throw Object.assign({}, response, { response });
      }

      if (code === ResultEnum.WARN) {
        message.warning(responseData?.msg || '');
        throw Object.assign({}, response, { response });
      }

      message.error(responseData?.msg || '系统异常');
      throw Object.assign({}, response, { response });
    },
  });

  // 通用的错误处理,如果没有进入上面的错误处理逻辑，就会进入这里（网络错误、超时、非200 HTTP状态码等）
  client.addResponseInterceptor(
    errorMessageResponseInterceptor((msg: string, error) => {
      const responseData = error?.response?.data ?? {};
      const errorMessage = responseData?.msg ?? '';
      if (errorMessage) {
        message.error(errorMessage);
        return;
      }
      message.error(msg);
    }),
  );

  return client;
}

export const requestClient = createRequestClient(apiURL, {
  responseReturn: 'data',
});

export const baseRequestClient = new RequestClient({ baseURL: apiURL });
