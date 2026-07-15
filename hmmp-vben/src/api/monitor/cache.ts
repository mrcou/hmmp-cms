import { requestClient } from '#/api/request';

export namespace MonitorCacheApi {
  export interface CacheCommandStat {
    name: string;
    value: string;
  }

  export interface CacheInfo {
    commandStats: CacheCommandStat[];
    dbSize: number;
    info: Record<string, string>;
  }

  export interface CacheName {
    cacheKey?: string;
    cacheName: string;
    cacheValue?: string;
    remark: string;
  }
}

/** 缓存首页信息：redis info + 命令统计 + dbSize */
export async function getCacheInfo() {
  return requestClient.get<{ data: MonitorCacheApi.CacheInfo }>(
    '/monitor/cache',
  );
}

/** 缓存名称列表 */
export async function getCacheNames() {
  return requestClient.get<{ data: MonitorCacheApi.CacheName[] }>(
    '/monitor/cache/getNames',
  );
}

/** 某个缓存名下的所有 key */
export async function getCacheKeys(cacheName: string) {
  return requestClient.get<{ data: string[] }>(
    `/monitor/cache/getKeys/${cacheName}`,
  );
}

/** 某个 key 的缓存内容 */
export async function getCacheValue(cacheName: string, cacheKey: string) {
  return requestClient.get<{ data: MonitorCacheApi.CacheName }>(
    `/monitor/cache/getValue/${cacheName}/${cacheKey}`,
  );
}

/** 清除某一类缓存 */
export async function clearCacheName(cacheName: string) {
  return requestClient.delete(`/monitor/cache/clearCacheName/${cacheName}`);
}

/** 清除某个 key */
export async function clearCacheKey(cacheKey: string) {
  return requestClient.delete(`/monitor/cache/clearCacheKey/${cacheKey}`);
}

/** 清空全部缓存 */
export async function clearCacheAll() {
  return requestClient.delete('/monitor/cache/clearCacheAll');
}
