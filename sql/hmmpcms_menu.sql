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
(2000, '采编中心', 0, 1, 'editor', NULL, NULL, '', 1, 0, 'M', '0', '0', '', '#', 'admin', NOW(), '', NULL, '');

-- 稿件处理 (menu_id=2001, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2001, '稿件处理', 2000, 1, 'manuscript', 'system/editor/manuscript/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:manuscript:list', '#', 'admin', NOW(), '', NULL, ''),
(2002, '收稿登记', 2001, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:add', '#', 'admin', NOW(), '', NULL, ''),
(2003, '稿件修改', 2001, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:edit', '#', 'admin', NOW(), '', NULL, ''),
(2004, '稿件删除', 2001, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:remove', '#', 'admin', NOW(), '', NULL, ''),
(2005, '稿件分配', 2001, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:assign', '#', 'admin', NOW(), '', NULL, ''),
(2006, '稿件送审', 2001, 5, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:review', '#', 'admin', NOW(), '', NULL, ''),
(2007, '稿件退修', 2001, 6, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:revise', '#', 'admin', NOW(), '', NULL, ''),
(2008, '稿件录用', 2001, 7, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:adopt', '#', 'admin', NOW(), '', NULL, ''),
(2009, '稿件退稿', 2001, 8, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:reject', '#', 'admin', NOW(), '', NULL, ''),
(2010, '稿件导出', 2001, 9, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:manuscript:export', '#', 'admin', NOW(), '', NULL, ''),
(2041, '新收稿', 2001, 10, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"0\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:new', '#', 'admin', NOW(), '', NULL, ''),
(2042, '审稿中', 2001, 11, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"2\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:reviewing', '#', 'admin', NOW(), '', NULL, ''),
(2043, '已退修', 2001, 12, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"3\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:revised', '#', 'admin', NOW(), '', NULL, ''),
(2044, '已修回', 2001, 13, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"7\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:resubmitted', '#', 'admin', NOW(), '', NULL, ''),
(2045, '已采用', 2001, 14, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"4\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:adopted', '#', 'admin', NOW(), '', NULL, ''),
(2046, '已退稿', 2001, 15, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"5\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:rejected', '#', 'admin', NOW(), '', NULL, ''),
(2047, '已发表', 2001, 16, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"6\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:published', '#', 'admin', NOW(), '', NULL, ''),
(2048, '稿件查询', 2001, 17, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:search', '#', 'admin', NOW(), '', NULL, ''),
(2049, '归档查询', 2001, 18, 'manuscript', 'system/editor/manuscript/index', '{\"tab\":\"9\"}', '', 1, 0, 'C', '0', '0', 'editor:manuscript:status:archived', '#', 'admin', NOW(), '', NULL, '');

-- 审稿管理 (menu_id=2011, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2011, '审稿管理', 2000, 2, 'audit', 'system/editor/audit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:audit:list', '#', 'admin', NOW(), '', NULL, ''),
(2012, '邀请审稿', 2011, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:audit:invite', '#', 'admin', NOW(), '', NULL, ''),
(2013, '撤审', 2011, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:audit:withdraw', '#', 'admin', NOW(), '', NULL, ''),
(2014, '接受退审', 2011, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:audit:acceptWithdraw', '#', 'admin', NOW(), '', NULL, ''),
(2015, '审稿导出', 2011, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:audit:export', '#', 'admin', NOW(), '', NULL, '');

-- 编辑加工管理 (menu_id=2016, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2016, '编辑加工管理', 2000, 3, 'edit', 'system/editor/edit/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:edit:list', '#', 'admin', NOW(), '', NULL, ''),
(2017, '新建编辑任务', 2016, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:edit:add', '#', 'admin', NOW(), '', NULL, ''),
(2018, '编辑任务修改', 2016, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:edit:edit', '#', 'admin', NOW(), '', NULL, ''),
(2019, '完成编辑', 2016, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:edit:complete', '#', 'admin', NOW(), '', NULL, '');

-- 排刊组稿 (menu_id=2020, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2020, '排刊组稿', 2000, 4, 'group', 'system/editor/group/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:group:list', '#', 'admin', NOW(), '', NULL, ''),
(2021, '新建组稿', 2020, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:group:add', '#', 'admin', NOW(), '', NULL, ''),
(2022, '组稿修改', 2020, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:group:edit', '#', 'admin', NOW(), '', NULL, ''),
(2023, '组稿删除', 2020, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:group:remove', '#', 'admin', NOW(), '', NULL, ''),
(2024, '添加稿件', 2020, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:group:addManuscript', '#', 'admin', NOW(), '', NULL, ''),
(2025, '稿件签发', 2020, 5, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:sign:sign', '#', 'admin', NOW(), '', NULL, '');

-- 稿件删除管理 (menu_id=2026, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2026, '稿件删除管理', 2000, 5, 'delete', 'system/editor/delete/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:delete:list', '#', 'admin', NOW(), '', NULL, ''),
(2027, '稿件恢复', 2026, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:delete:recover', '#', 'admin', NOW(), '', NULL, ''),
(2028, '审稿意见恢复', 2026, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:delete:recoverOpinion', '#', 'admin', NOW(), '', NULL, '');

-- 作者管理 (menu_id=2029, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2029, '作者管理', 2000, 6, 'author', 'system/editor/author/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:author:list', '#', 'admin', NOW(), '', NULL, ''),
(2030, '新增作者', 2029, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:author:add', '#', 'admin', NOW(), '', NULL, ''),
(2031, '修改作者', 2029, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:author:edit', '#', 'admin', NOW(), '', NULL, ''),
(2032, '删除作者', 2029, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:author:remove', '#', 'admin', NOW(), '', NULL, ''),
(2033, '作者导出', 2029, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:author:export', '#', 'admin', NOW(), '', NULL, '');

-- 审稿人管理 (menu_id=2034, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2034, '审稿人管理', 2000, 7, 'reviewer', 'system/editor/reviewer/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:reviewer:list', '#', 'admin', NOW(), '', NULL, ''),
(2035, '新增审稿人', 2034, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:reviewer:add', '#', 'admin', NOW(), '', NULL, ''),
(2036, '修改审稿人', 2034, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:reviewer:edit', '#', 'admin', NOW(), '', NULL, ''),
(2037, '删除审稿人', 2034, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:reviewer:remove', '#', 'admin', NOW(), '', NULL, '');

-- 申请审稿人 (menu_id=2038, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2038, '申请审稿人', 2000, 8, 'apply', 'system/editor/apply/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:reviewer:apply', '#', 'admin', NOW(), '', NULL, ''),
(2039, '审核申请', 2038, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'editor:reviewer:audit', '#', 'admin', NOW(), '', NULL, '');

-- 推荐审稿人 (menu_id=2040, parent_id=2000)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2040, '推荐审稿人', 2000, 9, 'recommend', 'system/editor/recommend/index', NULL, '', 1, 0, 'C', '0', '0', 'editor:reviewer:recommend', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 2. 发行中心 - 主菜单 (menu_id=2100, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2100, '发行中心', 0, 2, 'publisher', NULL, NULL, '', 1, 0, 'M', '0', '0', '', '#', 'admin', NOW(), '', NULL, '');

-- 网刊数据管理 (menu_id=2101, parent_id=2100)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2101, '网刊数据管理', 2100, 1, 'issue', 'system/publisher/issue/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:issue:list', '#', 'admin', NOW(), '', NULL, ''),
(2102, '年份管理', 2101, 1, 'year', 'system/publisher/year/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:year:list', '#', 'admin', NOW(), '', NULL, ''),
(2103, '刊期管理', 2101, 2, 'period', 'system/publisher/period/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:issue:list', '#', 'admin', NOW(), '', NULL, ''),
(2104, '新增刊期', 2103, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:issue:add', '#', 'admin', NOW(), '', NULL, ''),
(2105, '栏目管理', 2101, 3, 'column', 'system/publisher/column/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:column:list', '#', 'admin', NOW(), '', NULL, ''),
(2106, '新增栏目', 2105, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:column:add', '#', 'admin', NOW(), '', NULL, ''),
(2107, '文章管理', 2101, 4, 'article', 'system/publisher/article/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:article:list', '#', 'admin', NOW(), '', NULL, ''),
(2108, '批量发布', 2107, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:article:batchPublish', '#', 'admin', NOW(), '', NULL, ''),
(2109, '文章评论管理', 2101, 5, 'comment', 'system/publisher/comment/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:comment:list', '#', 'admin', NOW(), '', NULL, ''),
(2110, '虚拟专辑管理', 2101, 6, 'album', 'system/publisher/album/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:album:list', '#', 'admin', NOW(), '', NULL, ''),
(2111, '知网标题核对', 2101, 7, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:article:cnkiCheck', '#', 'admin', NOW(), '', NULL, '');

-- 读者用户管理 (menu_id=2112, parent_id=2100)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2112, '读者用户管理', 2100, 2, 'reader', 'system/publisher/reader/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:reader:list', '#', 'admin', NOW(), '', NULL, ''),
(2113, '新增读者', 2112, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:reader:add', '#', 'admin', NOW(), '', NULL, ''),
(2114, '订户管理', 2112, 2, 'subscriber', 'system/publisher/subscriber/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:subscriber:list', '#', 'admin', NOW(), '', NULL, ''),
(2115, '新增订户', 2114, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:subscriber:add', '#', 'admin', NOW(), '', NULL, '');

-- 征订管理 (menu_id=2116, parent_id=2100)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2116, '征订管理', 2100, 3, 'order', 'system/publisher/order/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:order:list', '#', 'admin', NOW(), '', NULL, ''),
(2117, '期刊订价管理', 2116, 1, 'price', 'system/publisher/price/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:price:list', '#', 'admin', NOW(), '', NULL, ''),
(2118, '期刊库存管理', 2116, 2, 'inventory', 'system/publisher/inventory/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:inventory:list', '#', 'admin', NOW(), '', NULL, ''),
(2119, '订单查询', 2116, 3, 'list', 'system/publisher/order/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:order:list', '#', 'admin', NOW(), '', NULL, ''),
(2120, '发票管理', 2116, 4, 'invoice', 'system/publisher/invoice/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:invoice:list', '#', 'admin', NOW(), '', NULL, ''),
(2121, '发票申请', 2116, 5, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:invoice:apply', '#', 'admin', NOW(), '', NULL, ''),
(2122, '发票审核', 2116, 6, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:invoice:audit', '#', 'admin', NOW(), '', NULL, ''),
(2123, '开发票', 2116, 7, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:invoice:make', '#', 'admin', NOW(), '', NULL, ''),
(2124, '寄书管理', 2116, 8, 'post', 'system/publisher/post/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:post:list', '#', 'admin', NOW(), '', NULL, '');

-- 精准推送管理 (menu_id=2127, parent_id=2100)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2127, '精准推送管理', 2100, 4, 'push', 'system/publisher/push/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:push:subscriber', '#', 'admin', NOW(), '', NULL, ''),
(2128, '订阅用户管理', 2127, 1, 'subscriber', 'system/publisher/push/subscriber/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:push:subscriber', '#', 'admin', NOW(), '', NULL, ''),
(2129, '关键词推送', 2127, 2, 'keyword', 'system/publisher/push/keyword/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:push:keyword', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 3. 统计中心 - 主菜单 (menu_id=2200, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2200, '统计中心', 0, 3, 'statistics', NULL, NULL, '', 1, 0, 'M', '0', '0', '', '#', 'admin', NOW(), '', NULL, '');

-- 综合统计 (menu_id=2201, parent_id=2200)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2201, '综合统计', 2200, 1, 'integrative', 'system/statistics/integrative/index', NULL, '', 1, 0, 'C', '0', '0', 'statistics:integrative:total', '#', 'admin', NOW(), '', NULL, ''),
(2202, '期刊统计', 2201, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:total', '#', 'admin', NOW(), '', NULL, ''),
(2203, '审理周期统计', 2201, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:auditCycle', '#', 'admin', NOW(), '', NULL, ''),
(2204, '出版周期统计', 2201, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:publishCycle', '#', 'admin', NOW(), '', NULL, ''),
(2205, '责编处理周期统计', 2201, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:editProcessCycle', '#', 'admin', NOW(), '', NULL, ''),
(2206, '加工周期', 2201, 5, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:editArticleCycle', '#', 'admin', NOW(), '', NULL, ''),
(2207, '责编工作量统计', 2201, 6, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:editWork', '#', 'admin', NOW(), '', NULL, ''),
(2208, '责编加工工作量', 2201, 7, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:editProduction', '#', 'admin', NOW(), '', NULL, ''),
(2209, '审稿统计', 2201, 8, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:reviewerAudit', '#', 'admin', NOW(), '', NULL, ''),
(2210, '被引文章查询', 2201, 9, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:referencedArticle', '#', 'admin', NOW(), '', NULL, ''),
(2211, '审稿周期统计', 2201, 10, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:reviewCycle', '#', 'admin', NOW(), '', NULL, ''),
(2212, '用户统计', 2201, 11, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:integrative:userStat', '#', 'admin', NOW(), '', NULL, '');

-- 网站统计 (menu_id=2213, parent_id=2200)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2213, '网站统计', 2200, 2, 'site', 'system/statistics/site/index', NULL, '', 1, 0, 'C', '0', '0', 'statistics:site:total', '#', 'admin', NOW(), '', NULL, ''),
(2214, '网站访问量统计', 2213, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'statistics:site:total', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 4. 广告中心 - 主菜单 (menu_id=2300, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2300, '广告中心', 0, 4, 'ad', NULL, NULL, '', 1, 0, 'M', '0', '0', '', '#', 'admin', NOW(), '', NULL, '');

-- 广告系统管理 (menu_id=2301, parent_id=2300)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2301, '广告系统管理', 2300, 1, 'book', 'system/ad/customer/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:customer', '#', 'admin', NOW(), '', NULL, ''),
(2302, '客户信息管理', 2301, 1, 'customer', 'system/ad/customer/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:customer', '#', 'admin', NOW(), '', NULL, ''),
(2303, '合同信息管理', 2301, 2, 'bargain', 'system/ad/bargain/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:bargain', '#', 'admin', NOW(), '', NULL, ''),
(2304, '合同申请', 2301, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:apply:bargain', '#', 'admin', NOW(), '', NULL, ''),
(2305, '合同审核', 2301, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:audit:bargain', '#', 'admin', NOW(), '', NULL, ''),
(2306, '合同打印', 2301, 5, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:make:bargain', '#', 'admin', NOW(), '', NULL, ''),
(2307, '广告信息管理', 2301, 6, 'ad', 'system/ad/ad/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:ad', '#', 'admin', NOW(), '', NULL, ''),
(2308, '广告申请', 2301, 7, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:apply:ad', '#', 'admin', NOW(), '', NULL, ''),
(2309, '广告审核', 2301, 8, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:audit:ad', '#', 'admin', NOW(), '', NULL, ''),
(2310, '已审核广告', 2301, 9, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:make:ad', '#', 'admin', NOW(), '', NULL, ''),
(2311, '支付信息管理', 2301, 10, 'cost', 'system/ad/cost/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:cost', '#', 'admin', NOW(), '', NULL, ''),
(2312, '发票管理', 2301, 11, 'invoice', 'system/ad/invoice/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:apply:invoice:list', '#', 'admin', NOW(), '', NULL, ''),
(2313, '发票申请', 2301, 12, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:apply:invoice', '#', 'admin', NOW(), '', NULL, ''),
(2314, '发票审核', 2301, 13, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:audit:invoice', '#', 'admin', NOW(), '', NULL, ''),
(2315, '开发票', 2301, 14, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:make:invoice', '#', 'admin', NOW(), '', NULL, ''),
(2316, '信息统计管理', 2301, 15, 'stat', 'system/ad/stat/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:book:stat', '#', 'admin', NOW(), '', NULL, ''),
(2317, '版位统计', 2301, 16, '', '', NULL, '', 1, 0, 'F', '0', '0', 'ad:book:position', '#', 'admin', NOW(), '', NULL, '');

-- 系统初始配置 (menu_id=2318, parent_id=2300)
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2318, '系统初始配置', 2300, 2, 'dict', 'system/ad/area/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:area', '#', 'admin', NOW(), '', NULL, ''),
(2319, '区域信息管理', 2318, 1, 'area', 'system/ad/area/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:area', '#', 'admin', NOW(), '', NULL, ''),
(2320, '广告位管理', 2318, 2, 'position', 'system/ad/position/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:position', '#', 'admin', NOW(), '', NULL, ''),
(2321, '广告位类型管理', 2318, 3, 'positionType', 'system/ad/positionType/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:positionType', '#', 'admin', NOW(), '', NULL, ''),
(2322, '广告类型管理', 2318, 4, 'type', 'system/ad/type/index', NULL, '', 1, 0, 'C', '0', '0', 'ad:dict:type', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 5. 作者中心 - 主菜单 (menu_id=2400, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2400, '作者中心', 0, 5, 'author', NULL, NULL, '', 1, 0, 'M', '0', '0', '', '#', 'admin', NOW(), '', NULL, ''),
(2401, '提交新稿', 2400, 1, 'manuscript/contribution', 'system/author/contribution/index', NULL, '', 1, 0, 'C', '0', '0', 'author:contribution', '#', 'admin', NOW(), '', NULL, ''),
(2402, '待修改稿', 2400, 2, 'manuscript/revision', 'system/author/revision/index', NULL, '', 1, 0, 'C', '0', '0', 'author:revision', '#', 'admin', NOW(), '', NULL, ''),
(2403, '稿件查询', 2400, 3, 'manuscript/search', 'system/author/search/index', NULL, '', 1, 0, 'C', '0', '0', 'author:search', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 6. 审稿中心 - 主菜单 (menu_id=2500, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2500, '审稿中心', 0, 6, 'reviewer', NULL, NULL, '', 1, 0, 'M', '0', '0', '', '#', 'admin', NOW(), '', NULL, ''),
(2501, '最新邀请审稿', 2500, 1, 'manuscript/newInvite', 'system/reviewer/invite/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:newInvite', '#', 'admin', NOW(), '', NULL, ''),
(2502, '未审完稿件', 2500, 2, 'manuscript/reviewing', 'system/reviewer/reviewing/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:reviewing', '#', 'admin', NOW(), '', NULL, ''),
(2503, '已审完稿件', 2500, 3, 'manuscript/reviewed', 'system/reviewer/reviewed/index', NULL, '', 1, 0, 'C', '0', '0', 'reviewer:reviewed', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 7. 会议管理 - 主菜单 (menu_id=2600, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2600, '会议管理', 0, 7, 'meeting', NULL, NULL, '', 1, 0, 'M', '0', '0', '', '#', 'admin', NOW(), '', NULL, ''),
(2601, '会议管理', 2600, 1, 'meeting', 'system/meeting/meeting/index', NULL, 'MeetingList', 1, 0, 'C', '0', '0', 'meeting:meeting:list', '#', 'admin', NOW(), '', NULL, ''),
(2602, '酒店管理', 2600, 2, 'hotel', 'system/meeting/hotel/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:hotel:list', '#', 'admin', NOW(), '', NULL, ''),
(2603, '用户类型管理', 2600, 3, 'user/group', 'system/meeting/userGroup/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:user:group', '#', 'admin', NOW(), '', NULL, ''),
(2604, '注册费价格管理', 2600, 4, 'fees/price', 'system/meeting/feesPrice/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:fees:price', '#', 'admin', NOW(), '', NULL, ''),
(2605, '参会代表管理', 2600, 5, 'user', 'system/meeting/user/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:user:list', '#', 'admin', NOW(), '', NULL, ''),
(2606, '票务管理', 2600, 6, 'tickets', 'system/meeting/tickets/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:tickets:list', '#', 'admin', NOW(), '', NULL, ''),
(2607, '旅游线路管理', 2600, 7, 'travel/way', 'system/meeting/travelWay/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:travel:way', '#', 'admin', NOW(), '', NULL, ''),
(2608, '旅游线路预定详情', 2600, 8, 'travel/booked', 'system/meeting/travelBooked/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:travel:booked', '#', 'admin', NOW(), '', NULL, ''),
(2609, '酒店房型预定详情', 2600, 9, 'hotel/booked', 'system/meeting/hotelBooked/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:hotel:booked', '#', 'admin', NOW(), '', NULL, ''),
(2610, '会议财务管理', 2600, 10, 'fees', 'system/meeting/fees/index', NULL, '', 1, 0, 'C', '0', '0', 'meeting:fees:list', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 8. 互动管理 - 主菜单 (menu_id=2700, parent_id=0)
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2700, '互动管理', 0, 8, 'message', NULL, NULL, '', 1, 0, 'M', '0', '0', '', '#', 'admin', NOW(), '', NULL, ''),
(2701, '用户留言', 2700, 1, 'article/remark', 'system/message/remark/index', NULL, '', 1, 0, 'C', '0', '0', 'message:remark:list', '#', 'admin', NOW(), '', NULL, ''),
(2702, '回复留言', 2701, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'message:remark:reply', '#', 'admin', NOW(), '', NULL, ''),
(2703, '待发送邮件', 2700, 2, 'email/sending', 'system/message/email/sending/index', NULL, '', 1, 0, 'C', '0', '0', 'message:email:sending', '#', 'admin', NOW(), '', NULL, ''),
(2704, '已发送邮件', 2700, 3, 'email/sended', 'system/message/email/sended/index', NULL, '', 1, 0, 'C', '0', '0', 'message:email:sended', '#', 'admin', NOW(), '', NULL, ''),
(2705, '发送邮件', 2700, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'message:email:send', '#', 'admin', NOW(), '', NULL, ''),
(2706, '待发送短信', 2700, 5, 'msg/sending', 'system/message/msg/sending/index', NULL, '', 1, 0, 'C', '0', '0', 'message:msg:sending', '#', 'admin', NOW(), '', NULL, ''),
(2707, '已发送短信', 2700, 6, 'msg/sended', 'system/message/msg/sended/index', NULL, '', 1, 0, 'C', '0', '0', 'message:msg:sended', '#', 'admin', NOW(), '', NULL, ''),
(2708, '失败的短信', 2700, 7, 'msg/failed', 'system/message/msg/failed/index', NULL, '', 1, 0, 'C', '0', '0', 'message:msg:failed', '#', 'admin', NOW(), '', NULL, ''),
(2709, '发送短信', 2700, 8, '', '', NULL, '', 1, 0, 'F', '0', '0', 'message:msg:send', '#', 'admin', NOW(), '', NULL, '');

-- =============================================
-- 角色菜单关联（将以上所有菜单关联到超级管理员角色 role_id=1）
-- 如需关联到特定角色，请修改 role_id 和 menu_id 范围
-- =============================================
-- INSERT INTO sys_role_menu (role_id, menu_id) SELECT 1, menu_id FROM sys_menu WHERE menu_id BETWEEN 2000 AND 2709;