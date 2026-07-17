-- 刊期管理字段补丁（对齐旧系统添加刊期表单字段）
-- 若列已存在请跳过对应语句

ALTER TABLE `publisher_issue`
  MODIFY COLUMN `period` varchar(50) NOT NULL COMMENT '期数/刊期（可为数字或字符）';

ALTER TABLE `publisher_issue`
  ADD COLUMN `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号' AFTER `year_id`,
  ADD COLUMN `name_cn` varchar(100) DEFAULT '' COMMENT '中文名' AFTER `issue_name`,
  ADD COLUMN `name_en` varchar(100) DEFAULT '' COMMENT '英文名' AFTER `name_cn`,
  ADD COLUMN `sequence` int(4) DEFAULT NULL COMMENT '顺序' AFTER `name_en`,
  ADD COLUMN `special_name` varchar(100) DEFAULT '' COMMENT '专辑中文名' AFTER `sequence`,
  ADD COLUMN `special_en_name` varchar(100) DEFAULT '' COMMENT '专辑英文名' AFTER `special_name`,
  ADD COLUMN `important` text COMMENT '重点(中文)' AFTER `special_en_name`,
  ADD COLUMN `en_important` text COMMENT '重点(英文)' AFTER `important`,
  ADD COLUMN `image_url` varchar(500) DEFAULT '' COMMENT '封面(小图)' AFTER `en_important`,
  ADD COLUMN `larger_cover_url` varchar(500) DEFAULT '' COMMENT '封面(大图)' AFTER `image_url`,
  ADD COLUMN `contents_file` varchar(500) DEFAULT '' COMMENT '目录文件' AFTER `larger_cover_url`,
  ADD COLUMN `cn_image_explain` text COMMENT '封面说明(中文)' AFTER `contents_file`,
  ADD COLUMN `en_image_explain` text COMMENT '封面说明(英文)' AFTER `cn_image_explain`,
  ADD COLUMN `request_money` char(1) DEFAULT '0' COMMENT '是否收费(0否 1是)' AFTER `en_image_explain`,
  ADD COLUMN `is_free` char(1) DEFAULT '0' COMMENT '是否免费(0否 1是)' AFTER `request_money`,
  ADD COLUMN `is_latest` char(1) DEFAULT '0' COMMENT '是否最新一期文章(0否 1是)' AFTER `is_free`,
  ADD COLUMN `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID' AFTER `is_latest`,
  ADD COLUMN `subscribe_status` char(1) DEFAULT '0' COMMENT '订阅发送状态(0未发送 1已发送)' AFTER `meeting_id`,
  ADD COLUMN `doi_status` char(1) DEFAULT '0' COMMENT 'DOI注册状态(0未注册 1已注册)' AFTER `subscribe_status`,
  ADD COLUMN `cstr_status` char(1) DEFAULT '0' COMMENT 'CSTR注册状态(0未注册 1已注册)' AFTER `doi_status`,
  ADD COLUMN `baidu_status` char(1) DEFAULT '0' COMMENT '百度发布状态(0未发送 1已发送)' AFTER `cstr_status`,
  ADD COLUMN `print_money` decimal(10,2) DEFAULT NULL COMMENT '印刷费' AFTER `cover_image`,
  ADD COLUMN `publish_money` decimal(10,2) DEFAULT NULL COMMENT '发行费' AFTER `print_money`,
  ADD COLUMN `post_money` decimal(10,2) DEFAULT NULL COMMENT '邮费' AFTER `publish_money`,
  ADD COLUMN `start_page` varchar(50) DEFAULT '' COMMENT '起始页码' AFTER `post_money`,
  ADD COLUMN `end_page` varchar(50) DEFAULT '' COMMENT '截止页码' AFTER `start_page`,
  ADD COLUMN `columns_info` text COMMENT '栏目信息(JSON)' AFTER `end_page`;

ALTER TABLE `publisher_issue` ADD KEY `idx_journal_code` (`journal_code`);
ALTER TABLE `publisher_issue` ADD KEY `idx_meeting_id` (`meeting_id`);
ALTER TABLE `publisher_issue` ADD KEY `idx_journal_year_period` (`journal_code`, `year`, `period`);
