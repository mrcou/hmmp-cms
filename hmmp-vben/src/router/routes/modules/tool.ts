import type { RouteRecordRaw } from 'vue-router';

/**
 * 后台 API（若依原 sys_menu：tool / 表单构建 / 代码生成 / 系统接口）
 * accessMode=frontend 时由静态路由提供菜单，不再依赖 getRouters。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'Tool',
    path: '/tool',
    meta: {
      icon: 'lucide:plug',
      order: 110,
      title: '后台 API',
    },
    children: [
      {
        name: 'ToolBuild',
        path: 'build',
        component: () => import('#/views/tool/build/index.vue'),
        meta: {
          icon: 'lucide:layout-template',
          order: 1,
          title: '表单构建',
        },
      },
      {
        name: 'ToolGen',
        path: 'gen',
        component: () => import('#/views/tool/gen/index.vue'),
        meta: {
          icon: 'lucide:code-xml',
          order: 2,
          title: '代码生成',
        },
      },
      {
        name: 'ToolSwagger',
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
];

export default routes;
