-- 刊期管理旧系统添加表单字段补齐
-- 适用于已执行过早期 patch_publisher_issue.sql 的现有库；若列已存在请跳过对应语句。

ALTER TABLE `publisher_issue`
  MODIFY COLUMN `period` varchar(50) NOT NULL COMMENT '期数/刊期（可为数字或字符）';

ALTER TABLE `publisher_issue` ADD COLUMN `sequence` int(4) DEFAULT NULL COMMENT '顺序' AFTER `name_en`;
ALTER TABLE `publisher_issue` ADD COLUMN `special_name` varchar(100) DEFAULT '' COMMENT '专辑中文名' AFTER `sequence`;
ALTER TABLE `publisher_issue` ADD COLUMN `special_en_name` varchar(100) DEFAULT '' COMMENT '专辑英文名' AFTER `special_name`;
ALTER TABLE `publisher_issue` ADD COLUMN `important` text COMMENT '重点(中文)' AFTER `special_en_name`;
ALTER TABLE `publisher_issue` ADD COLUMN `en_important` text COMMENT '重点(英文)' AFTER `important`;
ALTER TABLE `publisher_issue` ADD COLUMN `image_url` varchar(500) DEFAULT '' COMMENT '封面(小图)' AFTER `en_important`;
ALTER TABLE `publisher_issue` ADD COLUMN `larger_cover_url` varchar(500) DEFAULT '' COMMENT '封面(大图)' AFTER `image_url`;
ALTER TABLE `publisher_issue` ADD COLUMN `contents_file` varchar(500) DEFAULT '' COMMENT '目录文件' AFTER `larger_cover_url`;
ALTER TABLE `publisher_issue` ADD COLUMN `cn_image_explain` text COMMENT '封面说明(中文)' AFTER `contents_file`;
ALTER TABLE `publisher_issue` ADD COLUMN `en_image_explain` text COMMENT '封面说明(英文)' AFTER `cn_image_explain`;
ALTER TABLE `publisher_issue` ADD COLUMN `request_money` char(1) DEFAULT '0' COMMENT '是否收费(0否 1是)' AFTER `en_image_explain`;
ALTER TABLE `publisher_issue` ADD COLUMN `print_money` decimal(10,2) DEFAULT NULL COMMENT '印刷费' AFTER `cover_image`;
ALTER TABLE `publisher_issue` ADD COLUMN `publish_money` decimal(10,2) DEFAULT NULL COMMENT '发行费' AFTER `print_money`;
ALTER TABLE `publisher_issue` ADD COLUMN `post_money` decimal(10,2) DEFAULT NULL COMMENT '邮费' AFTER `publish_money`;
ALTER TABLE `publisher_issue` ADD COLUMN `start_page` varchar(50) DEFAULT '' COMMENT '起始页码' AFTER `post_money`;
ALTER TABLE `publisher_issue` ADD COLUMN `end_page` varchar(50) DEFAULT '' COMMENT '截止页码' AFTER `start_page`;
ALTER TABLE `publisher_issue` ADD COLUMN `columns_info` text COMMENT '栏目信息(JSON)' AFTER `end_page`;

