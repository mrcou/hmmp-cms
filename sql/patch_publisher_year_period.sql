-- 年份管理增加「期数」字段
-- 若列已存在请跳过对应语句

ALTER TABLE `publisher_year`
  ADD COLUMN `period` varchar(50) DEFAULT NULL COMMENT '期数' AFTER `year`;

-- 若已执行 patch_publisher_year_month.sql，请改用下面语句重命名列：
-- ALTER TABLE `publisher_year` CHANGE COLUMN `month` `period` varchar(50) DEFAULT NULL COMMENT '期数' AFTER `year`;
