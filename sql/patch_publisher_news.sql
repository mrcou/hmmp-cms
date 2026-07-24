-- 发行中心 - 新闻管理
-- 可重复执行：创建新闻栏目/内容表，补齐菜单与权限，并调整发行中心菜单顺序。

CREATE TABLE IF NOT EXISTS `publisher_news_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '栏目ID',
  `column_name` varchar(100) NOT NULL COMMENT '栏目名称',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序号（升序）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`),
  UNIQUE KEY `uk_news_column_name` (`column_name`),
  KEY `idx_news_column_sort` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='新闻栏目表';

CREATE TABLE IF NOT EXISTS `publisher_news_content` (
  `content_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `column_id` bigint(20) NOT NULL COMMENT '栏目ID',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `summary` varchar(1000) DEFAULT '' COMMENT '摘要',
  `content` longtext COMMENT '富文本正文',
  `status` varchar(20) NOT NULL DEFAULT 'draft' COMMENT '状态(draft草稿 published已发布)',
  `images` text COMMENT '图片地址，逗号分隔',
  `videos` text COMMENT '视频地址，逗号分隔',
  `attachments` text COMMENT '附件地址，逗号分隔',
  `source` varchar(200) DEFAULT '' COMMENT '来源',
  `author` varchar(100) DEFAULT '' COMMENT '作者',
  `editor` varchar(100) DEFAULT '' COMMENT '编辑',
  `source_url` varchar(500) DEFAULT '' COMMENT '来源链接',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序号（降序）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`content_id`),
  KEY `idx_news_content_column` (`column_id`),
  KEY `idx_news_content_status` (`status`),
  KEY `idx_news_content_sort` (`sort_order`, `content_id`),
  KEY `idx_news_content_publish_time` (`publish_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='新闻内容表';

UPDATE `sys_menu` SET `order_num` = 3 WHERE `menu_id` = 2112;
UPDATE `sys_menu` SET `order_num` = 4 WHERE `menu_id` = 2116;
UPDATE `sys_menu` SET `order_num` = 5 WHERE `menu_id` = 2127;

INSERT INTO `sys_menu`
(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`,
 `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`,
 `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
(2140, '新闻管理', 2100, 2, 'news', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'lucide:newspaper', 'admin', NOW(), '', NULL, '新闻栏目与内容管理'),
(2141, '栏目管理', 2140, 1, 'column', 'system/publisher/news/column/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:newsColumn:list', 'lucide:layout-list', 'admin', NOW(), '', NULL, ''),
(2142, '内容管理', 2140, 2, 'content', 'system/publisher/news/content/index', NULL, '', 1, 0, 'C', '0', '0', 'publisher:newsContent:list', 'lucide:file-text', 'admin', NOW(), '', NULL, ''),
(2143, '查询新闻栏目', 2141, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:newsColumn:query', '#', 'admin', NOW(), '', NULL, ''),
(2144, '新增新闻栏目', 2141, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:newsColumn:add', '#', 'admin', NOW(), '', NULL, ''),
(2145, '修改新闻栏目', 2141, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:newsColumn:edit', '#', 'admin', NOW(), '', NULL, ''),
(2146, '删除新闻栏目', 2141, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:newsColumn:remove', '#', 'admin', NOW(), '', NULL, ''),
(2147, '查询新闻内容', 2142, 1, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:newsContent:query', '#', 'admin', NOW(), '', NULL, ''),
(2148, '新增新闻内容', 2142, 2, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:newsContent:add', '#', 'admin', NOW(), '', NULL, ''),
(2149, '修改新闻内容', 2142, 3, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:newsContent:edit', '#', 'admin', NOW(), '', NULL, ''),
(2150, '删除新闻内容', 2142, 4, '', '', NULL, '', 1, 0, 'F', '0', '0', 'publisher:newsContent:remove', '#', 'admin', NOW(), '', NULL, '')
ON DUPLICATE KEY UPDATE
  `menu_name` = VALUES(`menu_name`),
  `parent_id` = VALUES(`parent_id`),
  `order_num` = VALUES(`order_num`),
  `path` = VALUES(`path`),
  `component` = VALUES(`component`),
  `menu_type` = VALUES(`menu_type`),
  `visible` = VALUES(`visible`),
  `status` = VALUES(`status`),
  `perms` = VALUES(`perms`),
  `icon` = VALUES(`icon`),
  `remark` = VALUES(`remark`);
