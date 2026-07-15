import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    name: 'Dashboard',
    path: '/dashboard',
    component: () => import('#/views/dashboard/workspace/index.vue'),
    meta: {
      icon: 'lucide:home',
      order: 1,
      title: '首页',
    },
  },
  {
    name: 'DashboardAnalytics',
    path: '/dashboard/analytics',
    component: () => import('#/views/dashboard/analytics/index.vue'),
    meta: {
      hideInMenu: true,
      icon: 'lucide:chart-line',
      title: '分析页',
    },
  },
  {
    name: 'AnalyticsRedirect',
    path: '/analytics',
    redirect: '/dashboard/analytics',
    meta: {
      hideInMenu: true,
      hideInTab: true,
      title: '分析页',
    },
  },
  {
    name: 'WelcomeRedirect',
    path: '/welcome',
    redirect: '/dashboard',
    meta: {
      hideInMenu: true,
      hideInTab: true,
      title: '首页',
    },
  },
  {
    name: 'Profile',
    path: '/profile',
    component: () => import('#/views/_core/profile/index.vue'),
    meta: {
      hideInMenu: true,
      title: '个人中心',
    },
  },
  {
    name: 'SystemDictData',
    path: '/system/dict-data',
    component: () => import('#/views/system/dict-data/index.vue'),
    meta: {
      hideInMenu: true,
      title: '字典数据',
    },
  },
  {
    name: 'GenEdit',
    path: '/tool/gen/edit',
    component: () => import('#/views/tool/gen/edit.vue'),
    meta: {
      hideInMenu: true,
      title: '修改生成配置',
    },
  },
  {
    name: 'SystemRoleAuth',
    path: '/system/role-auth',
    component: () => import('#/views/system/role-auth/index.vue'),
    meta: {
      hideInMenu: true,
      title: '分配用户',
    },
  },
];

export default routes;
