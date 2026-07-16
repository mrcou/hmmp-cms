import type { RouteRecordRaw } from 'vue-router';

/**
 * 会议管理 —— 由 scripts/generate-menu-routes.mjs 根据
 * sql/hmmpcms_menu.json 自动生成，请勿手工修改本文件。
 * 改动请编辑 sql/hmmpcms_menu.json 后重新运行生成脚本。
 */
const routes: RouteRecordRaw[] = [
  {
    name: 'Meeting',
    path: '/meeting',
    meta: {
      icon: 'lucide:calendar',
      order: 7,
      title: '会议管理',
    },
    children: [
      {
        name: 'MeetingMeeting',
        path: 'meeting',
        component: () => import('#/views/system/meeting/meeting/index.vue'),
        meta: {
          title: '会议管理',
          order: 1,
          icon: 'lucide:calendar-days',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingHotel',
        path: 'hotel',
        component: () => import('#/views/system/meeting/hotel/index.vue'),
        meta: {
          title: '酒店管理',
          order: 2,
          icon: 'lucide:building-2',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingUserGroup',
        path: 'user/group',
        component: () => import('#/views/system/meeting/userGroup/index.vue'),
        meta: {
          title: '用户类型管理',
          order: 3,
          icon: 'lucide:tags',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingFeesPrice',
        path: 'fees/price',
        component: () => import('#/views/system/meeting/feesPrice/index.vue'),
        meta: {
          title: '注册费价格管理',
          order: 4,
          icon: 'lucide:circle-dollar-sign',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingUser',
        path: 'user',
        component: () => import('#/views/system/meeting/user/index.vue'),
        meta: {
          title: '参会代表管理',
          order: 5,
          icon: 'lucide:users',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingTickets',
        path: 'tickets',
        component: () => import('#/views/system/meeting/tickets/index.vue'),
        meta: {
          title: '票务管理',
          order: 6,
          icon: 'lucide:ticket',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingTravelWay',
        path: 'travel/way',
        component: () => import('#/views/system/meeting/travelWay/index.vue'),
        meta: {
          title: '旅游线路管理',
          order: 7,
          icon: 'lucide:route',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingTravelBooked',
        path: 'travel/booked',
        component: () => import('#/views/system/meeting/travelBooked/index.vue'),
        meta: {
          title: '旅游线路预定详情',
          order: 8,
          icon: 'lucide:map-pinned',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingHotelBooked',
        path: 'hotel/booked',
        component: () => import('#/views/system/meeting/hotelBooked/index.vue'),
        meta: {
          title: '酒店房型预定详情',
          order: 9,
          icon: 'lucide:bed-double',
          keepAlive: true,
        },
      },
      {
        name: 'MeetingFees',
        path: 'fees',
        component: () => import('#/views/system/meeting/fees/index.vue'),
        meta: {
          title: '会议财务管理',
          order: 10,
          icon: 'lucide:wallet',
          keepAlive: true,
        },
      },
    ],
  },
];

export default routes;
