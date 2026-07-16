import type { RouteRecordRaw } from 'vue-router';

/**
 * 作者中心 —— 由 scripts/generate-menu-routes.mjs 根据
 * sql/hmmpcms_menu.json 自动生成，请勿手工修改本文件。
 * 改动请编辑 sql/hmmpcms_menu.json 后重新运行生成脚本。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'Author',
    path: '/author',
    meta: {
      icon: 'lucide:pen-line',
      order: 5,
      title: '作者中心',
      hideChildrenInMenu: true,
    },
    redirect: '/author/home',
    children: [
      {
        name: 'AuthorPortal',
        path: '',
        component: () => import('#/views/system/author/portal/layout.vue'),
        meta: {
          hideInMenu: true,
        },
        children: [
        {
          name: 'AuthorHome',
          path: 'home',
          component: () => import('#/views/system/author/portal/home.vue'),
          meta: {
            title: '作者中心',
            order: 0,
            icon: 'lucide:house',
            keepAlive: true,
          },
        },
        {
          name: 'AuthorContribution',
          path: 'contribution',
          component: () => import('#/views/system/author/contribution/index.vue'),
          meta: {
            title: '提交新稿',
            order: 1,
            icon: 'lucide:file-plus',
            keepAlive: true,
          },
        },
        {
          name: 'AuthorRevision',
          path: 'revision',
          component: () => import('#/views/system/author/revision/index.vue'),
          meta: {
            title: '待修改稿',
            order: 2,
            icon: 'lucide:file-pen',
            keepAlive: true,
          },
        },
        {
          name: 'AuthorSearch',
          path: 'search',
          component: () => import('#/views/system/author/search/index.vue'),
          meta: {
            title: '稿件查询',
            order: 3,
            icon: 'lucide:search',
            keepAlive: true,
          },
        },
        {
          name: 'AuthorContact',
          path: 'contact',
          component: () => import('#/views/system/author/contact/index.vue'),
          meta: {
            title: '联系编审',
            order: 4,
            icon: 'lucide:mail',
            keepAlive: true,
          },
        },
        {
          name: 'AuthorMember',
          path: 'member',
          component: () => import('#/views/system/author/account/index.vue'),
          meta: {
            title: '修改资料',
            order: 5,
            icon: 'lucide:user-round',
            keepAlive: true,
          },
        },
        {
          name: 'AuthorMessages',
          path: 'messages',
          component: () => import('#/views/system/author/center/index.vue'),
          meta: {
            title: '最新消息',
            order: 6,
            icon: 'lucide:bell',
            keepAlive: true,
          },
        },
        ],
      },
    ],
  },
];

export default routes;
