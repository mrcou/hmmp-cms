CREATE TABLE IF NOT EXISTS `editor_manuscript_author` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `manuscript_id` bigint(20) NOT NULL COMMENT '稿件ID',
  `author_id` bigint(20) DEFAULT NULL COMMENT '作者ID',
  `author_name` varchar(50) DEFAULT '' COMMENT '作者姓名',
  `author_sort` int(4) DEFAULT '1' COMMENT '排序号',
  `is_corresponding` char(1) DEFAULT '0' COMMENT '是否通讯作者(0否 1是)',
  `affiliation` varchar(200) DEFAULT '' COMMENT '单位',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_manuscript_id` (`manuscript_id`),
  KEY `idx_author_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='稿件作者关联表';