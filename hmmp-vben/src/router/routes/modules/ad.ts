import type { RouteRecordRaw } from 'vue-router';

/**
 * 广告中心 —— 由 scripts/generate-menu-routes.mjs 根据
 * sql/hmmpcms_menu.json 自动生成，请勿手工修改本文件。
 * 改动请编辑 sql/hmmpcms_menu.json 后重新运行生成脚本。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'Ad',
    path: '/ad',
    meta: {
      icon: 'lucide:megaphone',
      order: 4,
      title: '广告中心',
    },
    children: [
      {
        name: 'AdBook',
        path: 'book',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '广告系统管理',
          order: 1,
          icon: 'lucide:notebook-tabs',
        },
        redirect: '/ad/book/customer',
        children: [
        {
          name: 'AdBookCustomer',
          path: 'customer',
          component: () => import('#/views/system/ad/customer/index.vue'),
          meta: {
            title: '客户信息管理',
            order: 1,
            icon: 'lucide:building-2',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookBargain',
          path: 'bargain',
          component: () => import('#/views/system/ad/bargain/index.vue'),
          meta: {
            title: '合同信息管理',
            order: 2,
            icon: 'lucide:file-signature',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookAd',
          path: 'ad',
          component: () => import('#/views/system/ad/ad/index.vue'),
          meta: {
            title: '广告信息管理',
            order: 3,
            icon: 'lucide:image',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookCost',
          path: 'cost',
          component: () => import('#/views/system/ad/cost/index.vue'),
          meta: {
            title: '支付信息管理',
            order: 4,
            icon: 'lucide:wallet',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookBargainApply',
          path: 'bargain-apply',
          component: () => import('#/views/system/ad/bargain/index.vue'),
          meta: {
            title: '合同申请',
            order: 5,
            icon: 'lucide:file-plus',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookBargainAudit',
          path: 'bargain-audit',
          component: () => import('#/views/system/ad/bargain/index.vue'),
          meta: {
            title: '合同审核',
            order: 6,
            icon: 'lucide:file-check',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookBargainPrint',
          path: 'bargain-print',
          component: () => import('#/views/system/ad/bargain/index.vue'),
          meta: {
            title: '合同打印',
            order: 7,
            icon: 'lucide:printer',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookAdApply',
          path: 'ad-apply',
          component: () => import('#/views/system/ad/ad/index.vue'),
          meta: {
            title: '广告申请',
            order: 8,
            icon: 'lucide:upload',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookAdAudit',
          path: 'ad-audit',
          component: () => import('#/views/system/ad/ad/index.vue'),
          meta: {
            title: '广告审核',
            order: 9,
            icon: 'lucide:badge-check',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookAdApproved',
          path: 'ad-approved',
          component: () => import('#/views/system/ad/ad/index.vue'),
          meta: {
            title: '已审核广告',
            order: 10,
            icon: 'lucide:circle-check',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookInvoiceApply',
          path: 'invoice-apply',
          component: () => import('#/views/system/ad/invoice/index.vue'),
          meta: {
            title: '发票申请',
            order: 11,
            icon: 'lucide:receipt',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookStat',
          path: 'stat',
          component: () => import('#/views/system/ad/stat/index.vue'),
          meta: {
            title: '信息统计管理',
            order: 12,
            icon: 'lucide:chart-column',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookInvoiceAudit',
          path: 'invoice-audit',
          component: () => import('#/views/system/ad/invoice/index.vue'),
          meta: {
            title: '发票审核',
            order: 13,
            icon: 'lucide:file-search',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookInvoiceMake',
          path: 'invoice-make',
          component: () => import('#/views/system/ad/invoice/index.vue'),
          meta: {
            title: '开发票',
            order: 14,
            icon: 'lucide:stamp',
            keepAlive: true,
          },
        },
        {
          name: 'AdBookPositionStat',
          path: 'position-stat',
          component: () => import('#/views/system/ad/stat/index.vue'),
          meta: {
            title: '版位统计',
            order: 15,
            icon: 'lucide:layout-grid',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'AdDict',
        path: 'dict',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '系统初始配置',
          order: 2,
          icon: 'lucide:settings-2',
        },
        redirect: '/ad/dict/area',
        children: [
        {
          name: 'AdDictArea',
          path: 'area',
          component: () => import('#/views/system/ad/area/index.vue'),
          meta: {
            title: '区域信息管理',
            order: 1,
            icon: 'lucide:map-pinned',
            keepAlive: true,
          },
        },
        {
          name: 'AdDictPosition',
          path: 'position',
          component: () => import('#/views/system/ad/position/index.vue'),
          meta: {
            title: '广告位管理',
            order: 2,
            icon: 'lucide:panel-top',
            keepAlive: true,
          },
        },
        {
          name: 'AdDictPositionType',
          path: 'positionType',
          component: () => import('#/views/system/ad/positionType/index.vue'),
          meta: {
            title: '广告位类型管理',
            order: 3,
            icon: 'lucide:layers',
            keepAlive: true,
          },
        },
        {
          name: 'AdDictType',
          path: 'type',
          component: () => import('#/views/system/ad/type/index.vue'),
          meta: {
            title: '广告类型管理',
            order: 4,
            icon: 'lucide:tags',
            keepAlive: true,
          },
        },
        ],
      },
    ],
  },
];

export default routes;
