import type { RouteRecordRaw } from 'vue-router';

/**
 * 采编中心 —— 由 scripts/generate-menu-routes.mjs 根据
 * sql/hmmpcms_menu.json 自动生成，请勿手工修改本文件。
 * 改动请编辑 sql/hmmpcms_menu.json 后重新运行生成脚本。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'Editor',
    path: '/editor',
    meta: {
      icon: 'lucide:file-edit',
      order: 1,
      title: '采编中心',
    },
    children: [
      {
        name: 'EditorManuscript',
        path: 'manuscript',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '稿件处理',
          order: 1,
          icon: 'lucide:files',
        },
        redirect: '/editor/manuscript/register',
        children: [
        {
          name: 'EditorManuscriptRegister',
          path: 'register',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '收稿登记',
            order: 1,
            icon: 'lucide:clipboard-pen',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptNew',
          path: 'new',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '新收稿',
            order: 2,
            icon: 'lucide:file-plus',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptReviewing',
          path: 'reviewing',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '审稿中',
            order: 3,
            icon: 'lucide:hourglass',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptRevised',
          path: 'revised',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '已退修',
            order: 4,
            icon: 'lucide:undo-2',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptResubmitted',
          path: 'resubmitted',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '已修回',
            order: 5,
            icon: 'lucide:rotate-ccw',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptAdopted',
          path: 'adopted',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '已采用',
            order: 6,
            icon: 'lucide:badge-check',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptRejected',
          path: 'rejected',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '已退稿',
            order: 7,
            icon: 'lucide:x-circle',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptPublished',
          path: 'published',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '已发表',
            order: 8,
            icon: 'lucide:book-open',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptSearch',
          path: 'search',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '稿件查询',
            order: 9,
            icon: 'lucide:search',
            keepAlive: true,
          },
        },
        {
          name: 'EditorManuscriptArchived',
          path: 'archived',
          component: () => import('#/views/system/editor/manuscript/index.vue'),
          meta: {
            title: '归档查询',
            order: 10,
            icon: 'lucide:archive',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'EditorAudit',
        path: 'audit',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '审稿管理',
          order: 2,
          icon: 'lucide:clipboard-list',
        },
        redirect: '/editor/audit/pending',
        children: [
        {
          name: 'EditorAuditPending',
          path: 'pending',
          component: () => import('#/views/system/editor/audit/index.vue'),
          meta: {
            title: '未审回',
            order: 1,
            icon: 'lucide:clock',
            keepAlive: true,
          },
        },
        {
          name: 'EditorAuditWithdrawn',
          path: 'withdrawn',
          component: () => import('#/views/system/editor/audit/index.vue'),
          meta: {
            title: '已撤审',
            order: 2,
            icon: 'lucide:ban',
            keepAlive: true,
          },
        },
        {
          name: 'EditorAuditAcceptWithdraw',
          path: 'accept-withdraw',
          component: () => import('#/views/system/editor/audit/index.vue'),
          meta: {
            title: '已接受退审',
            order: 3,
            icon: 'lucide:circle-off',
            keepAlive: true,
          },
        },
        {
          name: 'EditorAuditReturned',
          path: 'returned',
          component: () => import('#/views/system/editor/audit/index.vue'),
          meta: {
            title: '已审回',
            order: 4,
            icon: 'lucide:check-check',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'EditorMyReview',
        path: 'my-review',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '我的审稿',
          order: 3,
          icon: 'lucide:clipboard-pen-line',
        },
        redirect: '/editor/my-review/new-invite',
        children: [
        {
          name: 'EditorMyReviewNewInvite',
          path: 'new-invite',
          component: () => import('#/views/system/reviewer/invite/index.vue'),
          meta: {
            title: '新邀请审稿列表',
            order: 1,
            icon: 'lucide:mail-plus',
            keepAlive: true,
          },
        },
        {
          name: 'EditorMyReviewReviewing',
          path: 'reviewing',
          component: () => import('#/views/system/reviewer/reviewing/index.vue'),
          meta: {
            title: '未审完稿件列表',
            order: 2,
            icon: 'lucide:list-todo',
            keepAlive: true,
          },
        },
        {
          name: 'EditorMyReviewReviewed',
          path: 'reviewed',
          component: () => import('#/views/system/reviewer/reviewed/index.vue'),
          meta: {
            title: '已审完稿件列表',
            order: 3,
            icon: 'lucide:list-checks',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'EditorEdit',
        path: 'edit',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '编辑加工管理',
          order: 4,
          icon: 'lucide:pen-tool',
        },
        redirect: '/editor/edit/pending',
        children: [
        {
          name: 'EditorEditPending',
          path: 'pending',
          component: () => import('#/views/system/editor/edit/index.vue'),
          meta: {
            title: '待编辑稿件',
            order: 1,
            icon: 'lucide:file-pen',
            keepAlive: true,
          },
        },
        {
          name: 'EditorEditManage',
          path: 'manage',
          component: () => import('#/views/system/editor/edit/index.vue'),
          meta: {
            title: '编辑稿件管理',
            order: 2,
            icon: 'lucide:folder-pen',
            keepAlive: true,
          },
        },
        {
          name: 'EditorEditDetail',
          path: 'detail',
          component: () => import('#/views/system/editor/edit/index.vue'),
          meta: {
            title: '编辑明细查询',
            order: 3,
            icon: 'lucide:list',
            keepAlive: true,
          },
        },
        {
          name: 'EditorEditGroup',
          path: 'group',
          component: () => import('#/views/system/editor/group/index.vue'),
          meta: {
            title: '排刊组稿',
            order: 4,
            icon: 'lucide:layout-grid',
            keepAlive: true,
          },
        },
        {
          name: 'EditorEditSimpleGroup',
          path: 'simple-group',
          component: () => import('#/views/system/editor/edit/simple-group.vue'),
          meta: {
            title: '简单组稿',
            order: 5,
            icon: 'lucide:layout-list',
            keepAlive: true,
          },
        },
        {
          name: 'EditorEditLayoutFiles',
          path: 'layout-files',
          component: () => import('#/views/system/editor/edit/layout-files.vue'),
          meta: {
            title: '待下载排版文件',
            order: 6,
            icon: 'lucide:download',
            keepAlive: true,
          },
        },
        {
          name: 'EditorEditSign',
          path: 'sign',
          component: () => import('#/views/system/editor/edit/sign.vue'),
          meta: {
            title: '稿件签发',
            order: 7,
            icon: 'lucide:stamp',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'EditorDelete',
        path: 'delete',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '稿件删除管理',
          order: 5,
          icon: 'lucide:trash-2',
        },
        redirect: '/editor/delete/remove',
        children: [
        {
          name: 'EditorDeleteRemove',
          path: 'remove',
          component: () => import('#/views/system/editor/delete/index.vue'),
          meta: {
            title: '稿件删除',
            order: 1,
            icon: 'lucide:file-x',
            keepAlive: true,
          },
        },
        {
          name: 'EditorDeleteRecover',
          path: 'recover',
          component: () => import('#/views/system/editor/delete/index.vue'),
          meta: {
            title: '稿件恢复',
            order: 2,
            icon: 'lucide:recycle',
            keepAlive: true,
          },
        },
        {
          name: 'EditorDeleteOpinion',
          path: 'opinion',
          component: () => import('#/views/system/editor/delete/opinion.vue'),
          meta: {
            title: '审稿意见恢复',
            order: 3,
            icon: 'lucide:message-square-reply',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'EditorUser',
        path: 'user',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '用户管理',
          order: 6,
          icon: 'lucide:users',
        },
        redirect: '/editor/user/author',
        children: [
        {
          name: 'EditorUserAuthor',
          path: 'author',
          component: () => import('#/views/system/editor/author/index.vue'),
          meta: {
            title: '作者管理',
            order: 1,
            icon: 'lucide:pen-line',
            keepAlive: true,
          },
        },
        {
          name: 'EditorUserReviewer',
          path: 'reviewer',
          component: () => import('#/views/system/editor/reviewer/index.vue'),
          meta: {
            title: '审稿人管理',
            order: 2,
            icon: 'lucide:user-round-check',
            keepAlive: true,
          },
        },
        {
          name: 'EditorUserApply',
          path: 'apply',
          component: () => import('#/views/system/editor/apply/index.vue'),
          meta: {
            title: '申请审稿人',
            order: 3,
            icon: 'lucide:user-plus',
            keepAlive: true,
          },
        },
        {
          name: 'EditorUserRecommend',
          path: 'recommend',
          component: () => import('#/views/system/editor/recommend/index.vue'),
          meta: {
            title: '推荐审稿人',
            order: 4,
            icon: 'lucide:user-star',
            keepAlive: true,
          },
        },
        {
          name: 'EditorUserDeleted',
          path: 'deleted',
          component: () => import('#/views/system/editor/user/deleted.vue'),
          meta: {
            title: '已删除用户管理',
            order: 5,
            icon: 'lucide:user-x',
            keepAlive: true,
          },
        },
        {
          name: 'EditorUserChangeAccount',
          path: 'change-account',
          component: () => import('#/views/system/editor/user/change-account.vue'),
          meta: {
            title: '变更用户帐号',
            order: 6,
            icon: 'lucide:user-cog',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'Message',
        path: '/message',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '互动管理',
          order: 7,
          icon: 'lucide:message-circle',
        },
        redirect: '/message/article/remark',
        children: [
        {
          name: 'MessageArticleRemark',
          path: 'article/remark',
          component: () => import('#/views/system/message/remark/index.vue'),
          meta: {
            title: '用户留言',
            order: 1,
            icon: 'lucide:messages-square',
            keepAlive: true,
          },
        },
        {
          name: 'MessageEmailSending',
          path: 'email/sending',
          component: () => import('#/views/system/message/email/sending/index.vue'),
          meta: {
            title: '待发送邮件',
            order: 2,
            icon: 'lucide:mail',
            keepAlive: true,
          },
        },
        {
          name: 'MessageEmailSended',
          path: 'email/sended',
          component: () => import('#/views/system/message/email/sended/index.vue'),
          meta: {
            title: '已发送邮件',
            order: 3,
            icon: 'lucide:mail-check',
            keepAlive: true,
          },
        },
        {
          name: 'MessageMsgSending',
          path: 'msg/sending',
          component: () => import('#/views/system/message/msg/sending/index.vue'),
          meta: {
            title: '待发送短信',
            order: 5,
            icon: 'lucide:smartphone',
            keepAlive: true,
          },
        },
        {
          name: 'MessageMsgSended',
          path: 'msg/sended',
          component: () => import('#/views/system/message/msg/sended/index.vue'),
          meta: {
            title: '已发送短信',
            order: 6,
            icon: 'lucide:message-square-check',
            keepAlive: true,
          },
        },
        {
          name: 'MessageMsgFailed',
          path: 'msg/failed',
          component: () => import('#/views/system/message/msg/failed/index.vue'),
          meta: {
            title: '失败的短信',
            order: 7,
            icon: 'lucide:message-square-x',
            keepAlive: true,
          },
        },
        {
          name: 'MessageMsgTest',
          path: 'msg/test',
          component: () => import('#/views/system/message/msg/test/index.vue'),
          meta: {
            title: '测试发送',
            order: 9,
            icon: 'lucide:send',
            keepAlive: true,
          },
        },
        ],
      },
    ],
  },
];

export default routes;
