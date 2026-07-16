import type { RouteRecordRaw } from 'vue-router';

/**
 * 审稿中心 —— 由 scripts/generate-menu-routes.mjs 根据
 * sql/hmmpcms_menu.json 自动生成，请勿手工修改本文件。
 * 改动请编辑 sql/hmmpcms_menu.json 后重新运行生成脚本。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'Reviewer',
    path: '/reviewer',
    meta: {
      icon: 'lucide:clipboard-check',
      order: 6,
      title: '审稿中心',
      hideChildrenInMenu: true,
    },
    redirect: '/reviewer/home',
    children: [
      {
        name: 'ReviewerPortal',
        path: '',
        component: () => import('#/views/system/reviewer/portal/layout.vue'),
        meta: {
          hideInMenu: true,
        },
        children: [
        {
          name: 'ReviewerHome',
          path: 'home',
          component: () => import('#/views/system/reviewer/portal/home.vue'),
          meta: {
            title: '审稿中心',
            order: 0,
            icon: 'lucide:house',
            keepAlive: true,
          },
        },
        {
          name: 'ReviewerNew',
          path: 'new',
          component: () => import('#/views/system/reviewer/new/index.vue'),
          meta: {
            title: '新收稿',
            order: 1,
            icon: 'lucide:file-plus',
            keepAlive: true,
          },
        },
        {
          name: 'ReviewerAssign',
          path: 'assign',
          component: () => import('#/views/system/reviewer/assign/index.vue'),
          meta: {
            title: '新分配',
            order: 2,
            icon: 'lucide:user-plus',
            keepAlive: true,
          },
        },
        {
          name: 'ReviewerResubmitted',
          path: 'resubmitted',
          component: () => import('#/views/system/reviewer/resubmitted/index.vue'),
          meta: {
            title: '已修回',
            order: 3,
            icon: 'lucide:rotate-ccw',
            keepAlive: true,
          },
        },
        {
          name: 'ReviewerInvite',
          path: 'invite',
          component: () => import('#/views/system/reviewer/invite/index.vue'),
          meta: {
            title: '最新邀请审稿',
            order: 4,
            icon: 'lucide:mail-plus',
            keepAlive: true,
          },
        },
        {
          name: 'ReviewerReviewing',
          path: 'reviewing',
          component: () => import('#/views/system/reviewer/reviewing/index.vue'),
          meta: {
            title: '未审完稿件',
            order: 5,
            icon: 'lucide:list-todo',
            keepAlive: true,
          },
        },
        {
          name: 'ReviewerReviewed',
          path: 'reviewed',
          component: () => import('#/views/system/reviewer/reviewed/index.vue'),
          meta: {
            title: '已审完稿件',
            order: 6,
            icon: 'lucide:list-checks',
            keepAlive: true,
          },
        },
        {
          name: 'ReviewerMember',
          path: 'member',
          component: () => import('#/views/system/reviewer/account/index.vue'),
          meta: {
            title: '修改资料',
            order: 7,
            icon: 'lucide:user-round',
            keepAlive: true,
          },
        },
        {
          name: 'ReviewerMessages',
          path: 'messages',
          component: () => import('#/views/system/reviewer/messages/index.vue'),
          meta: {
            title: '最新消息',
            order: 8,
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
