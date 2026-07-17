-- 批量发布文章（过刊 Excel 一对一导入）字段补丁
-- 可重复执行前请自行判断列是否已存在

ALTER TABLE `publisher_issue`
  ADD COLUMN `volume` int(4) DEFAULT NULL COMMENT '卷' AFTER `year`;

ALTER TABLE `publisher_article`
  ADD COLUMN `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号' AFTER `column_id`,
  ADD COLUMN `file_no` varchar(50) DEFAULT '' COMMENT '稿件编号' AFTER `journal_code`,
  ADD COLUMN `title_en` varchar(500) DEFAULT '' COMMENT '英文标题' AFTER `title`,
  ADD COLUMN `abstract_en` text COMMENT '英文摘要' AFTER `abstract`,
  ADD COLUMN `keywords_en` varchar(500) DEFAULT '' COMMENT '英文关键词' AFTER `keywords`,
  ADD COLUMN `fund_zh` varchar(500) DEFAULT '' COMMENT '基金中文名' AFTER `keywords_en`,
  ADD COLUMN `fund_en` varchar(500) DEFAULT '' COMMENT '基金英文名' AFTER `fund_zh`,
  ADD COLUMN `submit_time` datetime DEFAULT NULL COMMENT '投稿时间' AFTER `word_count`,
  ADD COLUMN `last_modify_time` datetime DEFAULT NULL COMMENT '最后修改时间' AFTER `submit_time`,
  ADD COLUMN `volume` int(4) DEFAULT NULL COMMENT '卷' AFTER `last_modify_time`,
  ADD COLUMN `period` int(4) DEFAULT NULL COMMENT '期' AFTER `volume`,
  ADD COLUMN `year_no` int(4) DEFAULT NULL COMMENT '年' AFTER `period`,
  ADD COLUMN `reference_text` longtext COMMENT '参考文献' AFTER `content`,
  ADD COLUMN `citation_text` longtext COMMENT '引用文本' AFTER `reference_text`,
  ADD COLUMN `author_names_en` varchar(500) DEFAULT '' COMMENT '作者英文名' AFTER `author_names`,
  ADD COLUMN `author_org_zh` varchar(1000) DEFAULT '' COMMENT '作者单位中文名' AFTER `author_names_en`,
  ADD COLUMN `author_org_en` varchar(1000) DEFAULT '' COMMENT '作者单位英文名' AFTER `author_org_zh`,
  ADD COLUMN `doi` varchar(200) DEFAULT '' COMMENT 'DOI' AFTER `author_org_en`,
  ADD COLUMN `download_count` int(11) DEFAULT '0' COMMENT '下载次数' AFTER `view_count`,
  ADD COLUMN `html_view_count` int(11) DEFAULT '0' COMMENT 'HTML浏览次数' AFTER `download_count`,
  ADD COLUMN `cnki_cited_count` int(11) DEFAULT '0' COMMENT '知网引用次数' AFTER `other_cited_count`,
  ADD COLUMN `article_url` varchar(500) DEFAULT '' COMMENT '网址' AFTER `cnki_match_status`,
  ADD COLUMN `pdf_path` varchar(500) DEFAULT '' COMMENT '全文PDF路径' AFTER `article_url`,
  ADD KEY `idx_file_no` (`file_no`);
