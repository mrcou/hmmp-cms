import type { RouteRecordRaw } from 'vue-router';

/**
 * 发行中心 —— 由 scripts/generate-menu-routes.mjs 根据
 * sql/hmmpcms_menu.json 自动生成，请勿手工修改本文件。
 * 改动请编辑 sql/hmmpcms_menu.json 后重新运行生成脚本。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'Publisher',
    path: '/publisher',
    meta: {
      icon: 'lucide:send',
      order: 2,
      title: '发行中心',
    },
    children: [
      {
        name: 'PublisherIssue',
        path: 'issue',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '网刊数据管理',
          order: 1,
          icon: 'lucide:newspaper',
        },
        redirect: '/publisher/issue/year',
        children: [
        {
          name: 'PublisherIssueYear',
          path: 'year',
          component: () => import('#/views/system/publisher/year/index.vue'),
          meta: {
            title: '年份管理',
            order: 1,
            icon: 'lucide:calendar',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherIssuePeriod',
          path: 'period',
          component: () => import('#/views/system/publisher/period/index.vue'),
          meta: {
            title: '刊期管理',
            order: 2,
            icon: 'lucide:book-open',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherIssueColumn',
          path: 'column',
          component: () => import('#/views/system/publisher/column/index.vue'),
          meta: {
            title: '栏目管理',
            order: 3,
            icon: 'lucide:layout-list',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherIssueBatchPublish',
          path: 'batch-publish',
          component: () => import('#/views/system/publisher/article/batch.vue'),
          meta: {
            title: '批量发布文章',
            order: 4,
            icon: 'lucide:upload',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherIssueArticle',
          path: 'article',
          component: () => import('#/views/system/publisher/article/index.vue'),
          meta: {
            title: '文章管理',
            order: 5,
            icon: 'lucide:file-text',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherIssueComment',
          path: 'comment',
          component: () => import('#/views/system/publisher/comment/index.vue'),
          meta: {
            title: '文章评论管理',
            order: 6,
            icon: 'lucide:message-square',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherIssueAlbum',
          path: 'album',
          component: () => import('#/views/system/publisher/album/index.vue'),
          meta: {
            title: '虚拟专辑管理',
            order: 7,
            icon: 'lucide:library',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherIssueCnki',
          path: 'cnki',
          component: () => import('#/views/system/publisher/article/cnki.vue'),
          meta: {
            title: '知网文章标题核对',
            order: 8,
            icon: 'lucide:file-search',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'PublisherNews',
        path: 'news',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '新闻管理',
          order: 2,
          icon: 'lucide:newspaper',
        },
        redirect: '/publisher/news/column',
        children: [
        {
          name: 'PublisherNewsColumn',
          path: 'column',
          component: () => import('#/views/system/publisher/news/column/index.vue'),
          meta: {
            title: '栏目管理',
            order: 1,
            icon: 'lucide:layout-list',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherNewsContent',
          path: 'content',
          component: () => import('#/views/system/publisher/news/content/index.vue'),
          meta: {
            title: '内容管理',
            order: 2,
            icon: 'lucide:file-text',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'PublisherReader',
        path: 'reader',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '读者用户管理',
          order: 3,
          icon: 'lucide:users',
        },
        redirect: '/publisher/reader/list',
        children: [
        {
          name: 'PublisherReaderList',
          path: 'list',
          component: () => import('#/views/system/publisher/reader/index.vue'),
          meta: {
            title: '读者管理',
            order: 1,
            icon: 'lucide:user',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherReaderSubscriber',
          path: 'subscriber',
          component: () => import('#/views/system/publisher/subscriber/index.vue'),
          meta: {
            title: '订户管理',
            order: 2,
            icon: 'lucide:user-check',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'PublisherOrder',
        path: 'order',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '征订管理',
          order: 4,
          icon: 'lucide:shopping-bag',
        },
        redirect: '/publisher/order/price',
        children: [
        {
          name: 'PublisherOrderPrice',
          path: 'price',
          component: () => import('#/views/system/publisher/price/index.vue'),
          meta: {
            title: '期刊定价管理',
            order: 1,
            icon: 'lucide:tag',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderInventory',
          path: 'inventory',
          component: () => import('#/views/system/publisher/inventory/index.vue'),
          meta: {
            title: '期刊库存管理',
            order: 2,
            icon: 'lucide:package',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderUnpaid',
          path: 'unpaid',
          component: () => import('#/views/system/publisher/order/index.vue'),
          meta: {
            title: '未收款订单',
            order: 3,
            icon: 'lucide:circle-dollar-sign',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderInvoiceUnsent',
          path: 'invoice-unsent',
          component: () => import('#/views/system/publisher/order/index.vue'),
          meta: {
            title: '发票未寄订单',
            order: 4,
            icon: 'lucide:mail-warning',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderInvoiceApply',
          path: 'invoice-apply',
          component: () => import('#/views/system/publisher/invoice/index.vue'),
          meta: {
            title: '发票申请',
            order: 5,
            icon: 'lucide:file-plus',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderInvoiceAudit',
          path: 'invoice-audit',
          component: () => import('#/views/system/publisher/invoice/index.vue'),
          meta: {
            title: '发票审核',
            order: 6,
            icon: 'lucide:file-check',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderInvoiceMake',
          path: 'invoice-make',
          component: () => import('#/views/system/publisher/invoice/index.vue'),
          meta: {
            title: '开发票',
            order: 7,
            icon: 'lucide:stamp',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderList',
          path: 'list',
          component: () => import('#/views/system/publisher/order/index.vue'),
          meta: {
            title: '订单查询',
            order: 8,
            icon: 'lucide:clipboard-list',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderPost',
          path: 'post',
          component: () => import('#/views/system/publisher/post/index.vue'),
          meta: {
            title: '寄书管理',
            order: 9,
            icon: 'lucide:truck',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderSample',
          path: 'sample',
          component: () => import('#/views/system/publisher/post/sample.vue'),
          meta: {
            title: '邮寄样刊登记',
            order: 10,
            icon: 'lucide:book-marked',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherOrderBound',
          path: 'bound',
          component: () => import('#/views/system/publisher/post/bound.vue'),
          meta: {
            title: '邮寄合订本',
            order: 11,
            icon: 'lucide:book-copy',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'PublisherPush',
        path: 'push',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '精准推送管理',
          order: 5,
          icon: 'lucide:radar',
        },
        redirect: '/publisher/push/subscriber',
        children: [
        {
          name: 'PublisherPushSubscriber',
          path: 'subscriber',
          component: () => import('#/views/system/publisher/push/subscriber/index.vue'),
          meta: {
            title: '订阅用户管理',
            order: 1,
            icon: 'lucide:bell',
            keepAlive: true,
          },
        },
        {
          name: 'PublisherPushKeyword',
          path: 'keyword',
          component: () => import('#/views/system/publisher/push/keyword/index.vue'),
          meta: {
            title: '关键词推送',
            order: 2,
            icon: 'lucide:key-round',
            keepAlive: true,
          },
        },
        ],
      },
    ],
  },
];

export default routes;
