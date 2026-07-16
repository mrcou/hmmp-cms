-- =============================================
-- HMMP-CMS 菜单初始化SQL
-- 创建时间: 2026-07-15
-- 说明: 初始化期刊业务模块的菜单和权限
-- menu_id 分配:
--   2000-2099: 采编中心
--   2100-2199: 发行中心
--   2200-2299: 统计中心
--   2300-2399: 广告中心
--   2400-2499: 作者中心
--   2500-2599: 审稿中心
--   2600-2699: 会议管理
--   2700-2799: 互动管理
-- =============================================

-- ----------------------------
-- 1. 采编中心 - 主菜单 (menu_id=2000, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2000, '采编中心', 0, 1, 'editor', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:file-edit', 'admin', NOW(), '', NULL, '');

-- 稿件处理 (menu_id=2001, parent_id=2000) —— 二级目录，下挂三级 leaf
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2001, '稿件处理', 2000, 1, 'manuscript', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:files', 'admin', NOW(), '', NULL, ''),
(2002, '收稿登记', 2001, 1, 'register', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:register', 'lucide:clipboard-pen', 'admin', NOW(), '', NULL, ''),
(2041, '新收稿', 2001, 2, 'new', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:new', 'lucide:file-plus', 'admin', NOW(), '', NULL, ''),
(2042, '审稿中', 2001, 3, 'reviewing', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:reviewing', 'lucide:hourglass', 'admin', NOW(), '', NULL, ''),
(2043, '已退修', 2001, 4, 'revised', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:revised', 'lucide:undo-2', 'admin', NOW(), '', NULL, ''),
(2044, '已修回', 2001, 5, 'resubmitted', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:resubmitted', 'lucide:rotate-ccw', 'admin', NOW(), '', NULL, ''),
(2045, '已采用', 2001, 6, 'adopted', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:adopted', 'lucide:badge-check', 'admin', NOW(), '', NULL, ''),
(2046, '已退稿', 2001, 7, 'rejected', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:rejected', 'lucide:x-circle', 'admin', NOW(), '', NULL, ''),
(2047, '已发表', 2001, 8, 'published', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:published', 'lucide:book-open', 'admin', NOW(), '', NULL, ''),
(2048, '稿件查询', 2001, 9, 'search', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:search', 'lucide:search', 'admin', NOW(), '', NULL, ''),
(2049, '归档查询', 2001, 10, 'archived', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:archived', 'lucide:archive', 'admin', NOW(), '', NULL, ''),
(2003, '稿件修改', 2001, 11, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:edit', '#', 'admin', NOW(), '', NULL, ''),
(2004, '稿件删除', 2001, 12, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:remove', '#', 'admin', NOW(), '', NULL, ''),
(2005, '稿件分配', 2001, 13, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:assign', '#', 'admin', NOW(), '', NULL, ''),
(2006, '稿件送审', 2001, 14, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:review', '#', 'admin', NOW(), '', NULL, ''),
(2007, '稿件退修', 2001, 15, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:revise', '#', 'admin', NOW(), '', NULL, ''),
(2008, '稿件录用', 2001, 16, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:adopt', '#', 'admin', NOW(), '', NULL, ''),
(2009, '稿件退稿', 2001, 17, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:reject', '#', 'admin', NOW(), '', NULL, ''),
(2010, '稿件导出', 2001, 18, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:export', '#', 'admin', NOW(), '', NULL, ''),
(2050, '新增稿件', 2001, 19, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:add', '#', 'admin', NOW(), '', NULL, '');

-- 审稿管理 (menu_id=2011, parent_id=2000) —— 二级目录，下挂三级 leaf
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2011, '审稿管理', 2000, 2, 'audit', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:clipboard-list', 'admin', NOW(), '', NULL, ''),
(2051, '未审回', 2011, 1, 'pending', 'system/editor/audit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:audit:status:pending', 'lucide:clock', 'admin', NOW(), '', NULL, ''),
(2052, '已撤审', 2011, 2, 'withdrawn', 'system/editor/audit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:audit:status:withdrawn', 'lucide:ban', 'admin', NOW(), '', NULL, ''),
(2053, '已接受退审', 2011, 3, 'accept-withdraw', 'system/editor/audit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:audit:status:acceptWithdraw', 'lucide:circle-off', 'admin', NOW(), '', NULL, ''),
(2054, '已审回', 2011, 4, 'returned', 'system/editor/audit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:audit:status:returned', 'lucide:check-check', 'admin', NOW(), '', NULL, ''),
(2012, '邀请审稿', 2011, 5, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:audit:invite', '#', 'admin', NOW(), '', NULL, ''),
(2013, '撤审', 2011, 6, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:audit:withdraw', '#', 'admin', NOW(), '', NULL, ''),
(2014, '接受退审', 2011, 7, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:audit:acceptWithdraw', '#', 'admin', NOW(), '', NULL, ''),
(2015, '审稿导出', 2011, 8, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:audit:export', '#', 'admin', NOW(), '', NULL, '');

-- 我的审稿 (menu_id=2055, parent_id=2000) —— 采编中心第三个二级目录（原审稿中心三个页面迁入）
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2055, '我的审稿', 2000, 3, 'my-review', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:clipboard-pen-line', 'admin', NOW(), '', NULL, ''),
(2056, '新邀请审稿列表', 2055, 1, 'new-invite', 'system/reviewer/invite/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:myReview:newInvite', 'lucide:mail-plus', 'admin', NOW(), '', NULL, '原 reviewer/manuscript/newInvite'),
(2057, '未审完稿件列表', 2055, 2, 'reviewing', 'system/reviewer/reviewing/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:myReview:reviewing', 'lucide:list-todo', 'admin', NOW(), '', NULL, '原 reviewer/manuscript/reviewing'),
(2058, '已审完稿件列表', 2055, 3, 'reviewed', 'system/reviewer/reviewed/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:myReview:reviewed', 'lucide:list-checks', 'admin', NOW(), '', NULL, '原 reviewer/manuscript/reviewed');

-- 编辑加工管理 (menu_id=2016, parent_id=2000) —— 二级目录，下挂三级 leaf（含原排刊组稿）
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2016, '编辑加工管理', 2000, 4, 'edit', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:pen-tool', 'admin', NOW(), '', NULL, ''),
(2059, '待编辑稿件', 2016, 1, 'pending', 'system/editor/edit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:edit:status:pending', 'lucide:file-pen', 'admin', NOW(), '', NULL, ''),
(2060, '编辑稿件管理', 2016, 2, 'manage', 'system/editor/edit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:edit:status:manage', 'lucide:folder-pen', 'admin', NOW(), '', NULL, ''),
(2061, '编辑明细查询', 2016, 3, 'detail', 'system/editor/edit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:edit:status:detail', 'lucide:list', 'admin', NOW(), '', NULL, ''),
(2020, '排刊组稿', 2016, 4, 'group', 'system/editor/group/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:group:list', 'lucide:layout-grid', 'admin', NOW(), '', NULL, ''),
(2062, '简单组稿', 2016, 5, 'simple-group', 'system/editor/edit/simple-group', NULL, '', 1, 0, 'C', '0', '0', 'editor:edit:simpleGroup', 'lucide:layout-list', 'admin', NOW(), '', NULL, ''),
(2063, '待下载排版文件', 2016, 6, 'layout-files', 'system/editor/edit/layout-files', NULL, '', 1, 0, 'C', '0', '0', 'editor:edit:layoutFiles', 'lucide:download', 'admin', NOW(), '', NULL, ''),
(2064, '稿件签发', 2016, 7, 'sign', 'system/editor/edit/sign', NULL, '', 1, 0, 'C', '0', '0', 'editor:sign:list', 'lucide:stamp', 'admin', NOW(), '', NULL, ''),
(2017, '新建编辑任务', 2016, 8, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:edit:add', '#', 'admin', NOW(), '', NULL, ''),
(2018, '编辑任务修改', 2016, 9, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:edit:edit', '#', 'admin', NOW(), '', NULL, ''),
(2019, '完成编辑', 2016, 10, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:edit:complete', '#', 'admin', NOW(), '', NULL, ''),
(2021, '新建组稿', 2016, 11, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:group:add', '#', 'admin', NOW(), '', NULL, ''),
(2022, '组稿修改', 2016, 12, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:group:edit', '#', 'admin', NOW(), '', NULL, ''),
(2023, '组稿删除', 2016, 13, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:group:remove', '#', 'admin', NOW(), '', NULL, ''),
(2024, '添加稿件', 2016, 14, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:group:addManuscript', '#', 'admin', NOW(), '', NULL, ''),
(2025, '签发操作', 2016, 15, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:sign:sign', '#', 'admin', NOW(), '', NULL, '');

-- 稿件删除管理 (menu_id=2026, parent_id=2000) —— 二级目录，下挂三级 leaf
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2026, '稿件删除管理', 2000, 5, 'delete', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:trash-2', 'admin', NOW(), '', NULL, ''),
(2065, '稿件删除', 2026, 1, 'remove', 'system/editor/delete/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:delete:remove', 'lucide:file-x', 'admin', NOW(), '', NULL, ''),
(2027, '稿件恢复', 2026, 2, 'recover', 'system/editor/delete/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:delete:recover', 'lucide:recycle', 'admin', NOW(), '', NULL, ''),
(2028, '审稿意见恢复', 2026, 3, 'opinion', 'system/editor/delete/opinion', NULL, '', 1, 0, 'C', '0', '0', 'editor:delete:recoverOpinion', 'lucide:message-square-reply', 'admin', NOW(), '', NULL, '');

-- 用户管理 (menu_id=2066, parent_id=2000) —— 二级目录，下挂三级 leaf
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2066, '用户管理', 2000, 6, 'user', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:users', 'admin', NOW(), '', NULL, ''),
(2029, '作者管理', 2066, 1, 'author', 'system/editor/author/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:author:list', 'lucide:pen-line', 'admin', NOW(), '', NULL, ''),
(2034, '审稿人管理', 2066, 2, 'reviewer', 'system/editor/reviewer/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:reviewer:list', 'lucide:user-round-check', 'admin', NOW(), '', NULL, ''),
(2038, '申请审稿人', 2066, 3, 'apply', 'system/editor/apply/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:reviewer:apply', 'lucide:user-plus', 'admin', NOW(), '', NULL, ''),
(2040, '推荐审稿人', 2066, 4, 'recommend', 'system/editor/recommend/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:reviewer:recommend', 'lucide:user-star', 'admin', NOW(), '', NULL, ''),
(2067, '已删除用户管理', 2066, 5, 'deleted', 'system/editor/user/deleted', NULL, '', 1, 0, 'C', '0', '0', 'editor:user:deleted', 'lucide:user-x', 'admin', NOW(), '', NULL, ''),
(2068, '变更用户帐号', 2066, 6, 'change-account', 'system/editor/user/change-account', NULL, '', 1, 0, 'C', '0', '0', 'editor:user:changeAccount', 'lucide:user-cog', 'admin', NOW(), '', NULL, ''),
(2030, '新增作者', 2066, 7, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:author:add', '#', 'admin', NOW(), '', NULL, ''),
(2031, '修改作者', 2066, 8, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:author:edit', '#', 'admin', NOW(), '', NULL, ''),
(2032, '删除作者', 2066, 9, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:author:remove', '#', 'admin', NOW(), '', NULL, ''),
(2033, '作者导出', 2066, 10, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:author:export', '#', 'admin', NOW(), '', NULL, ''),
(2035, '新增审稿人', 2066, 11, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:reviewer:add', '#', 'admin', NOW(), '', NULL, ''),
(2036, '修改审稿人', 2066, 12, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:reviewer:edit', '#', 'admin', NOW(), '', NULL, ''),
(2037, '删除审稿人', 2066, 13, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:reviewer:remove', '#', 'admin', NOW(), '', NULL, ''),
(2039, '审核申请', 2066, 14, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:reviewer:audit', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 2. 发行中心 - 主菜单 (menu_id=2100, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2100, '发行中心', 0, 2, 'publisher', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:send', 'admin', NOW(), '', NULL, '');

-- 网刊数据管理 (menu_id=2101, parent_id=2100)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2101, '网刊数据管理', 2100, 1, 'issue', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:newspaper', 'admin', NOW(), '', NULL, '网刊数据二级目录'),
(2102, '年份管理', 2101, 1, 'year', 'system/publisher/year/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:year:list', 'lucide:calendar', 'admin', NOW(), '', NULL, ''),
(2103, '刊期管理', 2101, 2, 'period', 'system/publisher/period/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:issue:list', 'lucide:book-open', 'admin', NOW(), '', NULL, ''),
(2104, '新增刊期', 2103, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:issue:add', '#', 'admin', NOW(), '', NULL, ''),
(2105, '栏目管理', 2101, 3, 'column', 'system/publisher/column/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:column:list', 'lucide:layout-list', 'admin', NOW(), '', NULL, ''),
(2106, '新增栏目', 2105, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:column:add', '#', 'admin', NOW(), '', NULL, ''),
(2107, '文章管理', 2101, 5, 'article', 'system/publisher/article/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:article:list', 'lucide:file-text', 'admin', NOW(), '', NULL, ''),
(2108, '批量发布文章', 2101, 4, 'batch-publish', 'system/publisher/article/batch', NULL, '', 1, 0, 'C', '0', '0', 'publisher:article:batchPublish', 'lucide:upload', 'admin', NOW(), '', NULL, ''),
(2109, '文章评论管理', 2101, 6, 'comment', 'system/publisher/comment/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:comment:list', 'lucide:message-square', 'admin', NOW(), '', NULL, ''),
(2110, '虚拟专辑管理', 2101, 7, 'album', 'system/publisher/album/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:album:list', 'lucide:library', 'admin', NOW(), '', NULL, ''),
(2111, '知网文章标题核对', 2101, 8, 'cnki', 'system/publisher/article/cnki', NULL, '', 1, 0, 'C', '0', '0', 'publisher:article:cnkiCheck', 'lucide:file-search', 'admin', NOW(), '', NULL, '');

-- 读者用户管理 (menu_id=2112, parent_id=2100)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2112, '读者用户管理', 2100, 2, 'reader', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:users', 'admin', NOW(), '', NULL, '读者用户二级目录'),
(2125, '读者管理', 2112, 1, 'list', 'system/publisher/reader/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:reader:list', 'lucide:user', 'admin', NOW(), '', NULL, ''),
(2113, '新增读者', 2125, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:reader:add', '#', 'admin', NOW(), '', NULL, ''),
(2114, '订户管理', 2112, 2, 'subscriber', 'system/publisher/subscriber/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:subscriber:list', 'lucide:user-check', 'admin', NOW(), '', NULL, ''),
(2115, '新增订户', 2114, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:subscriber:add', '#', 'admin', NOW(), '', NULL, '');

-- 征订管理 (menu_id=2116, parent_id=2100)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2116, '征订管理', 2100, 3, 'order', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:shopping-bag', 'admin', NOW(), '', NULL, '征订管理二级目录'),
(2117, '期刊定价管理', 2116, 1, 'price', 'system/publisher/price/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:price:list', 'lucide:tag', 'admin', NOW(), '', NULL, ''),
(2118, '期刊库存管理', 2116, 2, 'inventory', 'system/publisher/inventory/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:inventory:list', 'lucide:package', 'admin', NOW(), '', NULL, ''),
(2130, '未收款订单', 2116, 3, 'unpaid', 'system/publisher/order/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:order:unpaid', 'lucide:circle-dollar-sign', 'admin', NOW(), '', NULL, ''),
(2131, '发票未寄订单', 2116, 4, 'invoice-unsent', 'system/publisher/order/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:order:invoiceUnsent', 'lucide:mail-warning', 'admin', NOW(), '', NULL, ''),
(2119, '订单查询', 2116, 8, 'list', 'system/publisher/order/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:order:list', 'lucide:clipboard-list', 'admin', NOW(), '', NULL, ''),
(2120, '发票列表', 2116, 99, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:invoice:list', '#', 'admin', NOW(), '', NULL, '列表接口权限；侧栏由发票申请/审核/开发票承接'),
(2121, '发票申请', 2116, 5, 'invoice-apply', 'system/publisher/invoice/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:invoice:apply', 'lucide:file-plus', 'admin', NOW(), '', NULL, ''),
(2122, '发票审核', 2116, 6, 'invoice-audit', 'system/publisher/invoice/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:invoice:audit', 'lucide:file-check', 'admin', NOW(), '', NULL, ''),
(2123, '开发票', 2116, 7, 'invoice-make', 'system/publisher/invoice/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:invoice:make', 'lucide:stamp', 'admin', NOW(), '', NULL, ''),
(2124, '寄书管理', 2116, 9, 'post', 'system/publisher/post/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:post:list', 'lucide:truck', 'admin', NOW(), '', NULL, ''),
(2132, '邮寄样刊登记', 2116, 10, 'sample', 'system/publisher/post/sample', NULL, '', 1, 0, 'C', '0', '0', 'publisher:post:sample', 'lucide:book-marked', 'admin', NOW(), '', NULL, ''),
(2133, '邮寄合订本', 2116, 11, 'bound', 'system/publisher/post/bound', NULL, '', 1, 0, 'C', '0', '0', 'publisher:post:bound', 'lucide:book-copy', 'admin', NOW(), '', NULL, '');

-- 精准推送管理 (menu_id=2127, parent_id=2100)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2127, '精准推送管理', 2100, 4, 'push', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:radar', 'admin', NOW(), '', NULL, '精准推送二级目录'),
(2128, '订阅用户管理', 2127, 1, 'subscriber', 'system/publisher/push/subscriber/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:push:subscriber', 'lucide:bell', 'admin', NOW(), '', NULL, ''),
(2129, '关键词推送', 2127, 2, 'keyword', 'system/publisher/push/keyword/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:push:keyword', 'lucide:key-round', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 3. 统计中心 - 主菜单 (menu_id=2200, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2200, '统计中心', 0, 3, 'statistics', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:bar-chart-3', 'admin', NOW(), '', NULL, '');

-- 综合统计 (menu_id=2201, parent_id=2200)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2201, '综合统计', 2200, 1, 'integrative', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:pie-chart', 'admin', NOW(), '', NULL, '综合统计二级目录'),
(2202, '期刊统计', 2201, 1, 'journal', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:total', 'lucide:book-open', 'admin', NOW(), '', NULL, ''),
(2203, '审理周期统计', 2201, 2, 'audit-cycle', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:auditCycle', 'lucide:timer', 'admin', NOW(), '', NULL, ''),
(2204, '出版周期统计', 2201, 3, 'publish-cycle', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:publishCycle', 'lucide:calendar-clock', 'admin', NOW(), '', NULL, ''),
(2205, '责编处理周期统计', 2201, 4, 'edit-process-cycle', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:editProcessCycle', 'lucide:hourglass', 'admin', NOW(), '', NULL, ''),
(2206, '加工周期', 2201, 5, 'edit-article-cycle', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:editArticleCycle', 'lucide:cog', 'admin', NOW(), '', NULL, ''),
(2207, '责编工作量统计', 2201, 6, 'edit-work', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:editWork', 'lucide:briefcase', 'admin', NOW(), '', NULL, ''),
(2208, '责编加工工作量', 2201, 7, 'edit-production', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:editProduction', 'lucide:hammer', 'admin', NOW(), '', NULL, ''),
(2209, '审稿统计', 2201, 8, 'reviewer-audit', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:reviewerAudit', 'lucide:clipboard-check', 'admin', NOW(), '', NULL, ''),
(2210, '被引文章查询', 2201, 9, 'referenced-article', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:referencedArticle', 'lucide:quote', 'admin', NOW(), '', NULL, ''),
(2211, '审稿周期统计', 2201, 10, 'review-cycle', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:reviewCycle', 'lucide:clock-3', 'admin', NOW(), '', NULL, ''),
(2212, '用户统计', 2201, 11, 'user', 'system/statistics/integrative/panel', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:userStat', 'lucide:users', 'admin', NOW(), '', NULL, '');

-- 网站统计 (menu_id=2213, parent_id=2200)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2213, '网站统计', 2200, 2, 'site', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:globe', 'admin', NOW(), '', NULL, '网站统计二级目录'),
(2214, '网站访问量统计', 2213, 1, 'visit', 'system/statistics/site/index', NULL, '', 1, 0, 'C', '0', '0', 'statistics:site:total', 'lucide:activity', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 4. 广告中心 - 主菜单 (menu_id=2300, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2300, '广告中心', 0, 4, 'ad', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:megaphone', 'admin', NOW(), '', NULL, '');

-- 广告系统管理 (menu_id=2301, parent_id=2300)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2301, '广告系统管理', 2300, 1, 'book', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:notebook-tabs', 'admin', NOW(), '', NULL, '广告系统管理二级目录'),
(2302, '客户信息管理', 2301, 1, 'customer', 'system/ad/customer/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:customer', 'lucide:building-2', 'admin', NOW(), '', NULL, ''),
(2303, '合同信息管理', 2301, 2, 'bargain', 'system/ad/bargain/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:bargain', 'lucide:file-signature', 'admin', NOW(), '', NULL, ''),
(2304, '合同申请', 2301, 5, 'bargain-apply', 'system/ad/bargain/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:apply:bargain', 'lucide:file-plus', 'admin', NOW(), '', NULL, ''),
(2305, '合同审核', 2301, 6, 'bargain-audit', 'system/ad/bargain/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:audit:bargain', 'lucide:file-check', 'admin', NOW(), '', NULL, ''),
(2306, '合同打印', 2301, 7, 'bargain-print', 'system/ad/bargain/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:make:bargain', 'lucide:printer', 'admin', NOW(), '', NULL, ''),
(2307, '广告信息管理', 2301, 3, 'ad', 'system/ad/ad/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:ad', 'lucide:image', 'admin', NOW(), '', NULL, ''),
(2308, '广告申请', 2301, 8, 'ad-apply', 'system/ad/ad/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:apply:ad', 'lucide:upload', 'admin', NOW(), '', NULL, ''),
(2309, '广告审核', 2301, 9, 'ad-audit', 'system/ad/ad/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:audit:ad', 'lucide:badge-check', 'admin', NOW(), '', NULL, ''),
(2310, '已审核广告', 2301, 10, 'ad-approved', 'system/ad/ad/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:make:ad', 'lucide:circle-check', 'admin', NOW(), '', NULL, ''),
(2311, '支付信息管理', 2301, 4, 'cost', 'system/ad/cost/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:cost', 'lucide:wallet', 'admin', NOW(), '', NULL, ''),
(2312, '发票列表', 2301, 99, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:apply:invoice:list', '#', 'admin', NOW(), '', NULL, '列表接口权限；侧栏由发票申请/审核/开发票承接'),
(2313, '发票申请', 2301, 11, 'invoice-apply', 'system/ad/invoice/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:apply:invoice', 'lucide:receipt', 'admin', NOW(), '', NULL, ''),
(2314, '发票审核', 2301, 13, 'invoice-audit', 'system/ad/invoice/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:audit:invoice', 'lucide:file-search', 'admin', NOW(), '', NULL, ''),
(2315, '开发票', 2301, 14, 'invoice-make', 'system/ad/invoice/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:make:invoice', 'lucide:stamp', 'admin', NOW(), '', NULL, ''),
(2316, '信息统计管理', 2301, 12, 'stat', 'system/ad/stat/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:stat', 'lucide:chart-column', 'admin', NOW(), '', NULL, ''),
(2317, '版位统计', 2301, 15, 'position-stat', 'system/ad/stat/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:position', 'lucide:layout-grid', 'admin', NOW(), '', NULL, '');

-- 系统初始配置 (menu_id=2318, parent_id=2300)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2318, '系统初始配置', 2300, 2, 'dict', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:settings-2', 'admin', NOW(), '', NULL, '系统初始配置二级目录'),
(2319, '区域信息管理', 2318, 1, 'area', 'system/ad/area/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:area', 'lucide:map-pinned', 'admin', NOW(), '', NULL, ''),
(2320, '广告位管理', 2318, 2, 'position', 'system/ad/position/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:position', 'lucide:panel-top', 'admin', NOW(), '', NULL, ''),
(2321, '广告位类型管理', 2318, 3, 'positionType', 'system/ad/positionType/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:positionType', 'lucide:layers', 'admin', NOW(), '', NULL, ''),
(2322, '广告类型管理', 2318, 4, 'type', 'system/ad/type/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:type', 'lucide:tags', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 5. 作者中心 - 主菜单 (menu_id=2400, parent_id=0)
-- 二级均为 leaf：提交新稿 / 待修改稿 / 稿件查询 / 联系编审 / 修改资料
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2400, '作者中心', 0, 5, 'author', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:pen-line', 'admin', NOW(), '', NULL, ''),
(2404, '作者中心', 2400, 0, 'home', 'system/author/portal/home', NULL, '', 1, 0, 'C', '1', '0', 'author:home', 'lucide:house', 'admin', NOW(), '', NULL, '作者工作台入口，侧栏不展示'),
(2401, '提交新稿', 2400, 1, 'contribution', 'system/author/contribution/index', NULL, '', 1, 0, 'C', '0', '0', 'author:contribution', 'lucide:file-plus', 'admin', NOW(), '', NULL, ''),
(2402, '待修改稿', 2400, 2, 'revision', 'system/author/revision/index', NULL, '', 1, 0, 'C', '0', '0', 'author:revision', 'lucide:file-pen', 'admin', NOW(), '', NULL, ''),
(2403, '稿件查询', 2400, 3, 'search', 'system/author/search/index', NULL, '', 1, 0, 'C', '0', '0', 'author:search', 'lucide:search', 'admin', NOW(), '', NULL, ''),
(2430, '联系编审', 2400, 4, 'contact', 'system/author/contact/index', NULL, '', 1, 0, 'C', '0', '0', 'author:contact', 'lucide:mail', 'admin', NOW(), '', NULL, '作者联系编辑部 / 编审'),
(2420, '修改资料', 2400, 5, 'member', 'system/author/account/index', NULL, '', 1, 0, 'C', '0', '0', 'author:account', 'lucide:user-round', 'admin', NOW(), '', NULL, '复用 Vben Profile 个人中心模板'),
(2411, '最新消息', 2400, 6, 'messages', 'system/author/center/index', NULL, '', 1, 0, 'C', '1', '0', 'author:center', 'lucide:bell', 'admin', NOW(), '', NULL, '右侧消息区「更多」入口，侧栏不展示');

-- ----------------------------
-- 6. 审稿中心 - 主菜单 (menu_id=2500, parent_id=0)
-- 门户三栏布局：稿件处理（新收稿/新分配/已修回）+ 邀请/未审完/已审完；最新消息在右侧 sidebar
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2500, '审稿中心', 0, 6, 'reviewer', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:clipboard-check', 'admin', NOW(), '', NULL, ''),
(2504, '审稿中心', 2500, 0, 'home', 'system/reviewer/portal/home', NULL, '', 1, 0, 'C', '1', '0', 'reviewer:home', 'lucide:house', 'admin', NOW(), '', NULL, '审稿工作台入口，侧栏不展示'),
(2505, '新收稿', 2500, 1, 'new', 'system/reviewer/new/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:manuscript:new', 'lucide:file-plus', 'admin', NOW(), '', NULL, '稿件处理 · 新收稿'),
(2506, '新分配', 2500, 2, 'assign', 'system/reviewer/assign/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:manuscript:assign', 'lucide:user-plus', 'admin', NOW(), '', NULL, '稿件处理 · 新分配'),
(2507, '已修回', 2500, 3, 'resubmitted', 'system/reviewer/resubmitted/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:manuscript:resubmitted', 'lucide:rotate-ccw', 'admin', NOW(), '', NULL, '稿件处理 · 已修回'),
(2501, '最新邀请审稿', 2500, 4, 'invite', 'system/reviewer/invite/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:manuscript:newInvite', 'lucide:mail-plus', 'admin', NOW(), '', NULL, ''),
(2502, '未审完稿件', 2500, 5, 'reviewing', 'system/reviewer/reviewing/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:manuscript:reviewing', 'lucide:list-todo', 'admin', NOW(), '', NULL, ''),
(2503, '已审完稿件', 2500, 6, 'reviewed', 'system/reviewer/reviewed/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:manuscript:reviewed', 'lucide:list-checks', 'admin', NOW(), '', NULL, ''),
(2520, '修改资料', 2500, 7, 'member', 'system/reviewer/account/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:account', 'lucide:user-round', 'admin', NOW(), '', NULL, '复用 Profile 个人中心模板'),
(2511, '最新消息', 2500, 8, 'messages', 'system/reviewer/messages/index', NULL, '', 1, 0, 'C', '1', '0', 'reviewer:messages', 'lucide:bell', 'admin', NOW(), '', NULL, '右侧消息区「更多」入口，侧栏不展示');

-- ----------------------------
-- 7. 会议管理 - 主菜单 (menu_id=2600, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2600, '会议管理', 0, 7, 'meeting', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:calendar', 'admin', NOW(), '', NULL, ''),
(2601, '会议管理', 2600, 1, 'meeting', 'system/meeting/meeting/index', NULL, 'MeetingList', 1, 0, 'C', '0', '0', 'meeting:meeting:list', 'lucide:calendar-days', 'admin', NOW(), '', NULL, ''),
(2602, '酒店管理', 2600, 2, 'hotel', 'system/meeting/hotel/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:hotel:list', 'lucide:building-2', 'admin', NOW(), '', NULL, ''),
(2603, '用户类型管理', 2600, 3, 'user/group', 'system/meeting/userGroup/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:user:group', 'lucide:tags', 'admin', NOW(), '', NULL, ''),
(2604, '注册费价格管理', 2600, 4, 'fees/price', 'system/meeting/feesPrice/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:fees:price', 'lucide:circle-dollar-sign', 'admin', NOW(), '', NULL, ''),
(2605, '参会代表管理', 2600, 5, 'user', 'system/meeting/user/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:user:list', 'lucide:users', 'admin', NOW(), '', NULL, ''),
(2606, '票务管理', 2600, 6, 'tickets', 'system/meeting/tickets/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:tickets:list', 'lucide:ticket', 'admin', NOW(), '', NULL, ''),
(2607, '旅游线路管理', 2600, 7, 'travel/way', 'system/meeting/travelWay/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:travel:way', 'lucide:route', 'admin', NOW(), '', NULL, ''),
(2608, '旅游线路预定详情', 2600, 8, 'travel/booked', 'system/meeting/travelBooked/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:travel:booked', 'lucide:map-pinned', 'admin', NOW(), '', NULL, ''),
(2609, '酒店房型预定详情', 2600, 9, 'hotel/booked', 'system/meeting/hotelBooked/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:hotel:booked', 'lucide:bed-double', 'admin', NOW(), '', NULL, ''),
(2610, '会议财务管理', 2600, 10, 'fees', 'system/meeting/fees/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:fees:list', 'lucide:wallet', 'admin', NOW(), '', NULL, '');

-- 互动管理 (menu_id=2700, parent_id=2000) —— 挂到采编中心下，路由仍为 /message/*
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2700, '互动管理', 2000, 7, 'message', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:message-circle', 'admin', NOW(), '', NULL, '二级菜单挂在采编中心，URL 保持 /message/*'),
(2701, '用户留言', 2700, 1, 'article/remark', 'system/message/remark/index', NULL, '', 1, 0, 'C', '0', '0', 'message:remark:list', 'lucide:messages-square', 'admin', NOW(), '', NULL, ''),
(2702, '回复留言', 2701, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'message:remark:reply', '#', 'admin', NOW(), '', NULL, ''),
(2703, '待发送邮件', 2700, 2, 'email/sending', 'system/message/email/sending/index', NULL, '', 1, 0, 'C', '0', '0', 'message:email:sending', 'lucide:mail', 'admin', NOW(), '', NULL, ''),
(2704, '已发送邮件', 2700, 3, 'email/sended', 'system/message/email/sended/index', NULL, '', 1, 0, 'C', '0', '0', 'message:email:sended', 'lucide:mail-check', 'admin', NOW(), '', NULL, ''),
(2705, '发送邮件', 2700, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'message:email:send', '#', 'admin', NOW(), '', NULL, ''),
(2706, '待发送短信', 2700, 5, 'msg/sending', 'system/message/msg/sending/index', NULL, '', 1, 0, 'C', '0', '0', 'message:msg:sending', 'lucide:smartphone', 'admin', NOW(), '', NULL, ''),
(2707, '已发送短信', 2700, 6, 'msg/sended', 'system/message/msg/sended/index', NULL, '', 1, 0, 'C', '0', '0', 'message:msg:sended', 'lucide:message-square-check', 'admin', NOW(), '', NULL, ''),
(2708, '失败的短信', 2700, 7, 'msg/failed', 'system/message/msg/failed/index', NULL, '', 1, 0, 'C', '0', '0', 'message:msg:failed', 'lucide:message-square-x', 'admin', NOW(), '', NULL, ''),
(2709, '发送短信', 2700, 8, '', '', NULL, '', 1, 0, 'F', '0', '0', 'message:msg:send', '#', 'admin', NOW(), '', NULL, ''),
(2710, '测试发送', 2700, 9, 'msg/test', 'system/message/msg/test/index', NULL, '', 1, 0, 'C', '0', '0', 'message:msg:test', 'lucide:send', 'admin', NOW(), '', NULL, '短信测试发送（阿里云/腾讯云/火山云）');

-- =============================================
-- 角色菜单关联（将以上所有菜单关联到超级管理员角色 role_id=1）
-- 如需关联到特定角色，请修改 role_id 和 menu_id 范围
-- =============================================
-- INSERT INTO sys_role_menu (role_id, menu_id) SELECT 1, menu_id FROM sys_menu WHERE menu_id BETWEEN 2000 AND 2710;
