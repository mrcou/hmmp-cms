-- 邮寄样刊登记表
CREATE TABLE IF NOT EXISTS `publisher_sample_post` (
  `sample_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '样刊登记ID',
  `article_id` bigint(20) NOT NULL COMMENT '关联文章ID',
  `payee_name` varchar(50) DEFAULT '' COMMENT '收款人姓名',
  `payee_address` varchar(500) DEFAULT '' COMMENT '收款人地址',
  `payee_org` varchar(200) DEFAULT '' COMMENT '收款人单位',
  `payee_zip` varchar(10) DEFAULT '' COMMENT '邮编',
  `payee_phone` varchar(20) DEFAULT '' COMMENT '联系电话',
  `sample_qty` int(11) DEFAULT '1' COMMENT '样刊册数',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`sample_id`),
  UNIQUE KEY `uk_article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮寄样刊登记表';
