import { requestClient } from '#/api/request';

export namespace SiteConfigApi {
  export interface SecurityConfig {
    passwordCheck?: string;
    forcedPasswordChange?: string;
    passwordLife?: number;
    passwordErrorNum?: number;
    lockTime?: number;
    resetCounter?: number;
    cookieExpires?: number;
    loginReminder?: string;
    saveAuditLogDayNum?: number;
  }
}

/** 获取安全配置 */
export async function getSecurityConfig() {
  return requestClient.get<{ data: SiteConfigApi.SecurityConfig }>(
    '/system/site-config/security',
  );
}

/** 保存安全配置 */
export async function saveSecurityConfig(data: SiteConfigApi.SecurityConfig) {
  return requestClient.put('/system/site-config/security', data);
}

/** 获取站点分区配置 */
export async function getSectionConfig(section: string) {
  return requestClient.get<{ data: Record<string, unknown> }>(
    `/system/site-config/section/${section}`,
  );
}

/** 保存站点分区配置 */
export async function saveSectionConfig(
  section: string,
  data: Record<string, unknown>,
) {
  return requestClient.put(`/system/site-config/section/${section}`, data);
}
