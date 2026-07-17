-- 虚拟专辑管理字段补丁（对齐旧系统：杂志编号/中英文名/完成日期/顺序/类型/会议）
-- 若列已存在请跳过对应语句

ALTER TABLE `publisher_virtual_album`
  ADD COLUMN `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号' AFTER `album_id`,
  ADD COLUMN `name_cn` varchar(200) DEFAULT '' COMMENT '中文名' AFTER `journal_code`,
  ADD COLUMN `name_en` varchar(200) DEFAULT '' COMMENT '英文名' AFTER `name_cn`,
  ADD COLUMN `finish_date` date DEFAULT NULL COMMENT '完成日期' AFTER `name_en`,
  ADD COLUMN `order_num` int(4) DEFAULT '0' COMMENT '顺序' AFTER `finish_date`,
  ADD COLUMN `album_type` varchar(50) DEFAULT '' COMMENT '类型' AFTER `order_num`,
  ADD COLUMN `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID' AFTER `album_type`;

-- 兼容旧字段：将 album_name 回填到 name_cn，并放宽非空约束
UPDATE `publisher_virtual_album`
SET `name_cn` = `album_name`
WHERE (`name_cn` IS NULL OR `name_cn` = '') AND `album_name` IS NOT NULL AND `album_name` != '';

ALTER TABLE `publisher_virtual_album`
  MODIFY `album_name` varchar(200) DEFAULT '' COMMENT '专辑名称(兼容旧字段)';

ALTER TABLE `publisher_virtual_album` ADD KEY `idx_journal_code` (`journal_code`);
ALTER TABLE `publisher_virtual_album` ADD KEY `idx_meeting_id` (`meeting_id`);
