import type { RouteRecordRaw } from 'vue-router';

/**
 * 统计中心 —— 由 scripts/generate-menu-routes.mjs 根据
 * sql/hmmpcms_menu.json 自动生成，请勿手工修改本文件。
 * 改动请编辑 sql/hmmpcms_menu.json 后重新运行生成脚本。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'Statistics',
    path: '/statistics',
    meta: {
      icon: 'lucide:bar-chart-3',
      order: 3,
      title: '统计中心',
    },
    children: [
      {
        name: 'StatisticsIntegrative',
        path: 'integrative',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '综合统计',
          order: 1,
          icon: 'lucide:pie-chart',
        },
        redirect: '/statistics/integrative/journal',
        children: [
        {
          name: 'StatisticsIntegrativeJournal',
          path: 'journal',
          component: () => import('#/views/system/statistics/integrative/journal.vue'),
          meta: {
            title: '期刊统计',
            order: 1,
            icon: 'lucide:book-open',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativeAuditCycle',
          path: 'audit-cycle',
          component: () => import('#/views/system/statistics/integrative/audit-cycle.vue'),
          meta: {
            title: '审理周期统计',
            order: 2,
            icon: 'lucide:timer',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativePublishCycle',
          path: 'publish-cycle',
          component: () => import('#/views/system/statistics/integrative/publish-cycle.vue'),
          meta: {
            title: '出版周期统计',
            order: 3,
            icon: 'lucide:calendar-clock',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativeEditProcessCycle',
          path: 'edit-process-cycle',
          component: () => import('#/views/system/statistics/integrative/edit-process-cycle.vue'),
          meta: {
            title: '责编处理周期统计',
            order: 4,
            icon: 'lucide:hourglass',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativeEditArticleCycle',
          path: 'edit-article-cycle',
          component: () => import('#/views/system/statistics/integrative/edit-article-cycle.vue'),
          meta: {
            title: '加工周期',
            order: 5,
            icon: 'lucide:cog',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativeEditProduction',
          path: 'edit-production',
          component: () => import('#/views/system/statistics/integrative/edit-production.vue'),
          meta: {
            title: '责编加工工作量',
            order: 7,
            icon: 'lucide:hammer',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativeReviewerAudit',
          path: 'reviewer-audit',
          component: () => import('#/views/system/statistics/integrative/reviewer-audit.vue'),
          meta: {
            title: '审稿统计',
            order: 8,
            icon: 'lucide:clipboard-check',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativeReferencedArticle',
          path: 'referenced-article',
          component: () => import('#/views/system/statistics/integrative/referenced-article.vue'),
          meta: {
            title: '被引文章查询',
            order: 9,
            icon: 'lucide:quote',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativeReviewCycle',
          path: 'review-cycle',
          component: () => import('#/views/system/statistics/integrative/review-cycle.vue'),
          meta: {
            title: '审稿周期统计',
            order: 10,
            icon: 'lucide:clock-3',
            keepAlive: true,
          },
        },
        {
          name: 'StatisticsIntegrativeUser',
          path: 'user',
          component: () => import('#/views/system/statistics/integrative/user.vue'),
          meta: {
            title: '用户统计',
            order: 11,
            icon: 'lucide:users',
            keepAlive: true,
          },
        },
        ],
      },
      {
        name: 'StatisticsSite',
        path: 'site',
        component: () => import('#/layouts/parent-view.vue'),
        meta: {
          title: '网站统计',
          order: 2,
          icon: 'lucide:globe',
        },
        redirect: '/statistics/site/visit',
        children: [
        {
          name: 'StatisticsSiteVisit',
          path: 'visit',
          component: () => import('#/views/system/statistics/site/index.vue'),
          meta: {
            title: '网站访问量统计',
            order: 1,
            icon: 'lucide:activity',
            keepAlive: true,
          },
        },
        ],
      },
    ],
  },
];

export default routes;
