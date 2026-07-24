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
      title: '系统设置',
    },
    children: [
      {
        name: 'SystemSiteGroup',
        path: 'site-group',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          icon: 'lucide:layout-grid',
          order: -1,
          title: '站群设置',
        },
        redirect: '/system/site-group/basic',
        children: [
          {
            name: 'SystemSiteGroupBasic',
            path: 'basic',
            component: () => import('#/views/system/site-group/index.vue'),
            meta: {
              icon: 'lucide:settings-2',
              order: 1,
              title: '期刊设置',
            },
          },
          {
            name: 'SystemSiteGroupExtend',
            path: 'extend',
            component: () => import('#/views/system/site-group/index.vue'),
            meta: {
              icon: 'lucide:puzzle',
              order: 2,
              title: '扩展配置',
            },
          },
        ],
      },
      {
        name: 'SystemSiteConfig',
        path: 'site-config',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          icon: 'lucide:globe',
          order: 0,
          title: '站点配置',
        },
        redirect: '/system/site-config/notice',
        children: [
          {
            name: 'SystemNotice',
            path: 'notice',
            component: () => import('#/views/system/notice/index.vue'),
            meta: {
              icon: 'lucide:message-square',
              order: 0,
              title: '站点通知',
            },
          },
          {
            name: 'SystemSiteConfigSecurity',
            path: 'security',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:shield-check',
              order: 1,
              title: '安全配置',
            },
          },
          {
            name: 'SystemSiteConfigJournalInfo',
            path: 'journal-info',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:building-2',
              order: 4,
              title: '编辑部信息',
            },
          },
          {
            name: 'SystemSiteConfigMailInfo',
            path: 'mail-info',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:mail',
              order: 5,
              title: '邮件服务器',
            },
          },
          {
            name: 'SystemSiteConfigTimedTask',
            path: 'timed-task',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:clock',
              order: 6,
              title: '定时任务',
            },
          },
          {
            name: 'SystemSiteConfigCostProcessing',
            path: 'cost-processing',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:dollar-sign',
              order: 7,
              title: '费用处理',
            },
          },
          {
            name: 'SystemSiteConfigAuthorConfig',
            path: 'author-config',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:users',
              order: 8,
              title: '作者参数',
            },
          },
          {
            name: 'SystemSiteConfigEditorialConfig',
            path: 'editorial-config',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:user-cog',
              order: 9,
              title: '编委参数',
            },
          },
          {
            name: 'SystemSiteConfigReviewerConfig',
            path: 'reviewer-config',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:user-check',
              order: 10,
              title: '审稿人参数',
            },
          },
          {
            name: 'SystemSiteConfigReaderConfig',
            path: 'reader-config',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:book-open',
              order: 11,
              title: '读者参数',
            },
          },
          {
            name: 'SystemSiteConfigExternalInterface',
            path: 'external-interface',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:plug',
              order: 12,
              title: '外部接口',
            },
          },
          {
            name: 'SystemSiteConfigCombiningArticles',
            path: 'combining-articles',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:layers',
              order: 13,
              title: '组稿规则',
            },
          },
          {
            name: 'SystemSiteConfigOtherConfig',
            path: 'other-config',
            component: () => import('#/views/system/site-config/index.vue'),
            meta: {
              icon: 'lucide:sliders-horizontal',
              order: 14,
              title: '其他参数',
            },
          },
        ],
      },
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
        name: 'SystemNoticeLegacy',
        path: 'notice',
        redirect: '/system/site-config/notice',
        meta: {
          hideInMenu: true,
          title: '站点通知',
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
      {
        name: 'SystemTool',
        path: 'tool',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          icon: 'lucide:plug',
          order: 11,
          title: '后台 API',
        },
        redirect: '/system/tool/build',
        children: [
          {
            name: 'SystemToolBuild',
            path: 'build',
            component: () => import('#/views/tool/build/index.vue'),
            meta: {
              icon: 'lucide:layout-template',
              order: 1,
              title: '表单构建',
            },
          },
          {
            name: 'SystemToolGen',
            path: 'gen',
            component: () => import('#/views/tool/gen/index.vue'),
            meta: {
              icon: 'lucide:code-xml',
              order: 2,
              title: '代码生成',
            },
          },
          {
            name: 'SystemToolSwagger',
            path: 'swagger',
            component: () => import('#/views/tool/swagger/index.vue'),
            meta: {
              icon: 'lucide:plug',
              order: 3,
              title: '系统接口',
            },
          },
        ],
      },
    ],
  },
];

export default routes;
