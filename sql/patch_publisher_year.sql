-- 年份管理字段补丁（杂志编号/卷号/中英文名/目录文件/免费/会议）
-- 若列已存在请跳过对应语句

ALTER TABLE `publisher_year`
  ADD COLUMN `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号' AFTER `year_id`,
  ADD COLUMN `volume` int(4) DEFAULT NULL COMMENT '卷号' AFTER `year`,
  ADD COLUMN `name_cn` varchar(100) DEFAULT '' COMMENT '中文名' AFTER `volume`,
  ADD COLUMN `name_en` varchar(100) DEFAULT '' COMMENT '英文名' AFTER `name_cn`,
  ADD COLUMN `catalog_file` varchar(500) DEFAULT '' COMMENT '目录文件' AFTER `name_en`,
  ADD COLUMN `is_free` char(1) DEFAULT '0' COMMENT '是否免费(0否 1是)' AFTER `catalog_file`,
  ADD COLUMN `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID' AFTER `is_free`;

-- 唯一约束由「年份」调整为「杂志编号 + 年份」
ALTER TABLE `publisher_year` DROP INDEX `uk_year`;
ALTER TABLE `publisher_year` ADD UNIQUE KEY `uk_journal_year` (`journal_code`, `year`);
ALTER TABLE `publisher_year` ADD KEY `idx_meeting_id` (`meeting_id`);
