-- 邮寄合订本：订单扩展字段 + 寄书登记表
ALTER TABLE `publisher_order`
  ADD COLUMN IF NOT EXISTS `journal_code` varchar(32) DEFAULT '' COMMENT '杂志编号' AFTER `order_no`,
  ADD COLUMN IF NOT EXISTS `category` varchar(100) DEFAULT '' COMMENT '行业分类' AFTER `journal_code`,
  ADD COLUMN IF NOT EXISTS `unit_type` varchar(100) DEFAULT '' COMMENT '企业类型' AFTER `category`,
  ADD COLUMN IF NOT EXISTS `distribute_type` varchar(50) DEFAULT '' COMMENT '发行方式' AFTER `unit_type`,
  ADD COLUMN IF NOT EXISTS `send_method` varchar(50) DEFAULT '' COMMENT '邮寄方式' AFTER `distribute_type`,
  ADD COLUMN IF NOT EXISTS `order_book_num` int(11) DEFAULT '1' COMMENT '邮寄数量' AFTER `send_method`,
  ADD COLUMN IF NOT EXISTS `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID' AFTER `order_book_num`;

CREATE TABLE IF NOT EXISTS `publisher_bound_post` (
  `bound_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '合订本寄书登记ID',
  `order_id` bigint(20) NOT NULL COMMENT '关联订单ID',
  `receive_user` varchar(50) DEFAULT '' COMMENT '收书人姓名',
  `receive_unit` varchar(200) DEFAULT '' COMMENT '收书人单位',
  `receive_address` varchar(500) DEFAULT '' COMMENT '收书人地址',
  `receive_postcode` varchar(10) DEFAULT '' COMMENT '读者邮编',
  `send_book_time` datetime DEFAULT NULL COMMENT '寄书时间',
  `send_user` varchar(64) DEFAULT '' COMMENT '寄书人',
  `can_post` char(1) DEFAULT '1' COMMENT '是否可邮寄(0否 1是)',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`bound_id`),
  UNIQUE KEY `uk_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮寄合订本登记表';
