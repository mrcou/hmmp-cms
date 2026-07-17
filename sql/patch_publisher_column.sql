-- 栏目管理字段补丁（按旧系统栏目弹窗字段补齐）
-- 若列已存在请跳过对应语句

ALTER TABLE `publisher_column`
  ADD COLUMN `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号' AFTER `column_id`,
  ADD COLUMN `name_en` varchar(100) DEFAULT '' COMMENT '英文栏目' AFTER `column_name`,
  ADD COLUMN `need_recommender` char(1) DEFAULT '0' COMMENT '是否需要推荐人(0否 1是)' AFTER `order_num`,
  ADD COLUMN `usage_scope` varchar(200) DEFAULT '' COMMENT '使用范围文本' AFTER `need_recommender`;

ALTER TABLE `publisher_column`
  ADD COLUMN `is_post_column` char(1) DEFAULT '1' COMMENT '使用范围(1投稿、内部查询、网刊发布 0网刊发布 2内部查询、网刊发布)' AFTER `usage_scope`,
  ADD COLUMN `file_no_prefix` varchar(50) DEFAULT '' COMMENT '稿件编号前缀' AFTER `is_post_column`,
  ADD COLUMN `en_abstract_money` decimal(10,2) DEFAULT NULL COMMENT '英文摘要加工费' AFTER `file_no_prefix`,
  ADD COLUMN `edit_name` varchar(255) DEFAULT '' COMMENT '编辑账号' AFTER `en_abstract_money`;

ALTER TABLE `publisher_column` ADD KEY `idx_journal_code` (`journal_code`);
ALTER TABLE `publisher_column` ADD KEY `idx_journal_column_code` (`journal_code`, `column_code`);
