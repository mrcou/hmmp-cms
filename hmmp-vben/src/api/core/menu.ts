import type { RouteRecordStringComponent } from '@vben/types';

import { requestClient } from '#/api/request';

/** 后端 RouterVo 结构（对应 hmmp-system 的 RouterVo/MetaVo） */
interface BackendRouter {
  alwaysShow?: boolean;
  children?: BackendRouter[];
  component?: string;
  hidden?: boolean;
  meta?: {
    icon?: string;
    link?: string;
    noCache?: boolean;
    title?: string;
  };
  name?: string;
  path: string;
  query?: string;
  redirect?: string;
}

const RUOYI_ICON_MAP: Record<string, string> = {
  build: 'lucide:layout-template',
  cache: 'lucide:database',
  code: 'lucide:code-xml',
  config: 'lucide:sliders-horizontal',
  dept: 'lucide:building-2',
  dict: 'lucide:book-type',
  druid: 'lucide:gauge',
  edit: 'lucide:square-pen',
  form: 'lucide:file-text',
  guide: 'lucide:map',
  job: 'lucide:calendar-clock',
  logininfor: 'lucide:log-in',
  log: 'lucide:scroll-text',
  menu: 'lucide:menu',
  message: 'lucide:message-square',
  monitor: 'lucide:monitor-cog',
  online: 'lucide:radio',
  peoples: 'lucide:users',
  post: 'lucide:badge-check',
  redis: 'lucide:database-zap',
  role: 'lucide:shield-user',
  server: 'lucide:server',
  swagger: 'lucide:plug',
  system: 'lucide:settings',
  tool: 'lucide:wrench',
  tree: 'lucide:network',
  'tree-table': 'lucide:git-fork',
  user: 'lucide:user',
};

function normalizeIcon(icon?: string) {
  const trimmedIcon = icon?.trim();

  if (!trimmedIcon || trimmedIcon === '#') {
    return undefined;
  }

  if (trimmedIcon.includes(':') || trimmedIcon.startsWith('http')) {
    return trimmedIcon;
  }

  return RUOYI_ICON_MAP[trimmedIcon] ?? 'lucide:circle-dot';
}

function convert(routers: BackendRouter[]): RouteRecordStringComponent[] {
  return routers.map((router) => {
    const meta = router.meta ?? {};
    const route: RouteRecordStringComponent = {
      component: router.component,
      meta: {
        icon: normalizeIcon(meta.icon),
        keepAlive: !meta.noCache,
        hideInMenu: !!router.hidden,
        link: meta.link,
        title: meta.title,
      },
      name: router.name || router.path,
      path: router.path,
    } as RouteRecordStringComponent;

    if (router.redirect) {
      route.redirect = router.redirect;
    }
    if (router.children && router.children.length > 0) {
      route.children = convert(router.children);
    }
    return route;
  });
}

/**
 * 获取用户所有菜单（对接 RuoYi 原生 /getRouters 接口）
 */
export async function getAllMenusApi() {
  const res = await requestClient.get<{ data: BackendRouter[] }>(
    '/getRouters',
  );
  return convert(res.data ?? []);
}
