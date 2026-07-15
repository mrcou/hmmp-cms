import type {
  ComponentRecordType,
  GenerateMenuAndRoutesOptions,
  MenuRecordRaw,
} from '@vben/types';

import { generateAccessible } from '@vben/access';
import { preferences } from '@vben/preferences';

import { message } from 'antdv-next';

import { getAllMenusApi } from '#/api';
import { BasicLayout, IFrameView, ParentView } from '#/layouts';
import { $t } from '#/locales';

const forbiddenComponent = () => import('#/views/_core/fallback/forbidden.vue');
const WELCOME_MENU: MenuRecordRaw = {
  icon: 'lucide:home',
  name: '首页',
  order: 1,
  path: '/dashboard',
  show: true,
};

function normalizeMenus(menus: MenuRecordRaw[]) {
  return [
    WELCOME_MENU,
    ...menus.filter((menu) => {
      const isRuoYiGuide =
        menu.name === '若依官网' || String(menu.path).includes('ruoyi.vip');
      const isWelcomeMenu = menu.path === WELCOME_MENU.path;
      return !isRuoYiGuide && !isWelcomeMenu;
    }),
  ];
}

async function generateAccess(options: GenerateMenuAndRoutesOptions) {
  const pageMap: ComponentRecordType = import.meta.glob('../views/**/*.vue');

  const layoutMap: ComponentRecordType = {
    BasicLayout,
    IFrameView,
    Layout: BasicLayout,
    ParentView,
  };

  const access = await generateAccessible(preferences.app.accessMode, {
    ...options,
    fetchMenuListAsync: async () => {
      message.loading({
        content: `${$t('common.loadingMenu')}...`,
        duration: 1.5,
      });
      return await getAllMenusApi();
    },
    // 可以指定没有权限跳转403页面
    forbiddenComponent,
    // 如果 route.meta.menuVisibleWithForbidden = true
    layoutMap,
    pageMap,
  });

  return {
    ...access,
    accessibleMenus: normalizeMenus(access.accessibleMenus),
  };
}

export { generateAccess };
