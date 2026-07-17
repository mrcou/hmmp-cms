-- 被引文章查询：为已有库补充引用计数字段（可重复执行需自行判断列是否已存在）
ALTER TABLE `publisher_article`
  ADD COLUMN `cited_count` int(11) DEFAULT '0' COMMENT '被引次数' AFTER `view_count`,
  ADD COLUMN `self_cited_count` int(11) DEFAULT '0' COMMENT '自引次数' AFTER `cited_count`,
  ADD COLUMN `other_cited_count` int(11) DEFAULT '0' COMMENT '他引次数' AFTER `self_cited_count`;
