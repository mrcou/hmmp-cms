import type { RouteRecordRaw } from 'vue-router';

/**
 * 系统设置（若依原 sys_menu：系统 / 系统监控 / 日志）
 * accessMode=frontend 时由静态路由提供菜单，不再依赖 getRouters。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'System',
    path: '/system',
    meta: {
      icon: 'lucide:settings',
      order: 100,
      title: '系统',
    },
    children: [
      {
        name: 'SystemUser',
        path: 'user',
        component: () => import('#/views/system/user/index.vue'),
        meta: {
          icon: 'lucide:user',
          order: 1,
          title: '用户管理',
        },
      },
      {
        name: 'SystemRole',
        path: 'role',
        component: () => import('#/views/system/role/index.vue'),
        meta: {
          icon: 'lucide:shield-user',
          order: 2,
          title: '角色管理',
        },
      },
      {
        name: 'SystemMenu',
        path: 'menu',
        component: () => import('#/views/system/menu/index.vue'),
        meta: {
          icon: 'lucide:git-fork',
          order: 3,
          title: '菜单管理',
        },
      },
      {
        name: 'SystemDept',
        path: 'dept',
        component: () => import('#/views/system/dept/index.vue'),
        meta: {
          icon: 'lucide:network',
          order: 4,
          title: '部门管理',
        },
      },
      {
        name: 'SystemPost',
        path: 'post',
        component: () => import('#/views/system/post/index.vue'),
        meta: {
          icon: 'lucide:badge-check',
          order: 5,
          title: '岗位管理',
        },
      },
      {
        name: 'SystemDict',
        path: 'dict',
        component: () => import('#/views/system/dict/index.vue'),
        meta: {
          icon: 'lucide:book-type',
          order: 6,
          title: '字典管理',
        },
      },
      {
        name: 'SystemConfig',
        path: 'config',
        component: () => import('#/views/system/config/index.vue'),
        meta: {
          icon: 'lucide:square-pen',
          order: 7,
          title: '参数设置',
        },
      },
      {
        name: 'SystemNotice',
        path: 'notice',
        component: () => import('#/views/system/notice/index.vue'),
        meta: {
          icon: 'lucide:message-square',
          order: 8,
          title: '通知公告',
        },
      },
      {
        name: 'SystemLog',
        path: 'log',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          icon: 'lucide:scroll-text',
          order: 9,
          title: '日志管理',
        },
        redirect: '/system/log/operlog',
        children: [
          {
            name: 'SystemLogOperlog',
            path: 'operlog',
            component: () => import('#/views/monitor/operlog/index.vue'),
            meta: {
              icon: 'lucide:file-text',
              order: 1,
              title: '操作日志',
            },
          },
          {
            name: 'SystemLogLogininfor',
            path: 'logininfor',
            component: () => import('#/views/monitor/logininfor/index.vue'),
            meta: {
              icon: 'lucide:log-in',
              order: 2,
              title: '登录日志',
            },
          },
        ],
      },
      {
        name: 'SystemMonitor',
        path: 'monitor',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          icon: 'lucide:monitor-cog',
          order: 10,
          title: '系统监控',
        },
        redirect: '/system/monitor/online',
        children: [
          {
            name: 'SystemMonitorOnline',
            path: 'online',
            component: () => import('#/views/monitor/online/index.vue'),
            meta: {
              icon: 'lucide:radio',
              order: 1,
              title: '在线用户',
            },
          },
          {
            name: 'SystemMonitorJob',
            path: 'job',
            component: () => import('#/views/monitor/job/index.vue'),
            meta: {
              icon: 'lucide:calendar-clock',
              order: 2,
              title: '定时任务',
            },
          },
          {
            name: 'SystemMonitorDruid',
            path: 'druid',
            component: () => import('#/views/monitor/druid/index.vue'),
            meta: {
              icon: 'lucide:gauge',
              order: 3,
              title: '数据监控',
            },
          },
          {
            name: 'SystemMonitorServer',
            path: 'server',
            component: () => import('#/views/monitor/server/index.vue'),
            meta: {
              icon: 'lucide:server',
              order: 4,
              title: '服务监控',
            },
          },
          {
            name: 'SystemMonitorCache',
            path: 'cache',
            component: () => import('#/views/monitor/cache/index.vue'),
            meta: {
              icon: 'lucide:database-zap',
              order: 5,
              title: '缓存监控',
            },
          },
        ],
      },
    ],
  },
];

export default routes;
