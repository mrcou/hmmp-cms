-- =============================================
-- HMMP-CMS 期刊业务数据库初始化脚本
-- 创建时间: 2026-07-15
-- 说明: 包含采编中心、发行中心、广告中心、统计中心、作者中心、审稿中心、会议管理、互动管理所有业务表
-- =============================================

-- ----------------------------
-- 1. 采编中心 - 稿件表
-- ----------------------------
DROP TABLE IF EXISTS `editor_manuscript`;
CREATE TABLE `editor_manuscript` (
  `manuscript_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '稿件ID',
  `file_no` varchar(64) NOT NULL COMMENT '稿件编号',
  `title` varchar(500) NOT NULL COMMENT '标题',
  `author_ids` varchar(1000) DEFAULT '' COMMENT '作者ID列表',
  `author_names` varchar(500) DEFAULT '' COMMENT '作者姓名',
  `corresponding_author` varchar(50) DEFAULT '' COMMENT '通讯作者',
  `corresponding_email` varchar(100) DEFAULT '' COMMENT '通讯邮箱',
  `abstract` text COMMENT '摘要',
  `keywords` varchar(500) DEFAULT '' COMMENT '关键词',
  `manuscript_type` char(2) DEFAULT '' COMMENT '稿件类型',
  `word_count` int(11) DEFAULT '0' COMMENT '字数',
  `fund_project` varchar(500) DEFAULT '' COMMENT '基金项目',
  `fund_no` varchar(100) DEFAULT '' COMMENT '基金编号',
  `status` char(2) DEFAULT '01' COMMENT '状态(01新收稿 02新分配 03审稿中 04已退修 05已修回 06已采用 07已退稿 08已发表 09已归档)',
  `receive_time` datetime DEFAULT NULL COMMENT '收稿时间',
  `assign_time` datetime DEFAULT NULL COMMENT '分配时间',
  `review_start_time` datetime DEFAULT NULL COMMENT '审稿开始时间',
  `review_end_time` datetime DEFAULT NULL COMMENT '审稿结束时间',
  `conclusion` varchar(2000) DEFAULT '' COMMENT '处理结论',
  `conclusion_time` datetime DEFAULT NULL COMMENT '结论时间',
  `editor_id` bigint(20) DEFAULT NULL COMMENT '责任编辑ID',
  `editor_name` varchar(50) DEFAULT '' COMMENT '责任编辑姓名',
  `audit_count` int(4) DEFAULT '0' COMMENT '审稿次数',
  `revision_count` int(4) DEFAULT '0' COMMENT '退修次数',
  `is_deleted` char(1) DEFAULT '0' COMMENT '删除标志(0未删除 2已删除)',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_by` varchar(64) DEFAULT '' COMMENT '删除人',
  `archive_time` datetime DEFAULT NULL COMMENT '归档时间',
  `publish_time` datetime DEFAULT NULL COMMENT '发表时间',
  `issue_id` bigint(20) DEFAULT NULL COMMENT '发表刊期ID',
  `page_start` int(4) DEFAULT '0' COMMENT '起始页码',
  `page_end` int(4) DEFAULT '0' COMMENT '结束页码',
  `file_path` varchar(500) DEFAULT '' COMMENT '稿件文件路径',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`manuscript_id`),
  KEY `idx_file_no` (`file_no`),
  KEY `idx_status` (`status`),
  KEY `idx_editor_id` (`editor_id`),
  KEY `idx_receive_time` (`receive_time`),
  KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='稿件信息表';

-- ----------------------------
-- 2. 采编中心 - 审稿记录表
-- ----------------------------
DROP TABLE IF EXISTS `editor_audit`;
CREATE TABLE `editor_audit` (
  `audit_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '审稿记录ID',
  `manuscript_id` bigint(20) NOT NULL COMMENT '稿件ID',
  `reviewer_id` bigint(20) NOT NULL COMMENT '审稿人ID',
  `reviewer_name` varchar(50) DEFAULT '' COMMENT '审稿人姓名',
  `review_round` int(4) DEFAULT '1' COMMENT '审稿轮次',
  `audit_status` char(1) DEFAULT '0' COMMENT '审稿状态(0待审 1已审回 2已撤审 3已接受退审)',
  `audit_opinion` text COMMENT '审稿意见',
  `audit_score` decimal(3,1) DEFAULT NULL COMMENT '审稿评分',
  `audit_result` char(2) DEFAULT '' COMMENT '审稿结论(01建议录用 02建议修改后录用 03建议退稿)',
  `invite_time` datetime DEFAULT NULL COMMENT '邀请时间',
  `deadline_time` datetime DEFAULT NULL COMMENT '截止时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审回时间',
  `withdraw_time` datetime DEFAULT NULL COMMENT '撤审时间',
  `withdraw_reason` varchar(500) DEFAULT '' COMMENT '撤审原因',
  `accept_withdraw_time` datetime DEFAULT NULL COMMENT '接受退审时间',
  `invite_message` varchar(500) DEFAULT '' COMMENT '邀请留言',
  `is_deleted` char(1) DEFAULT '0' COMMENT '删除标志',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`audit_id`),
  KEY `idx_manuscript_id` (`manuscript_id`),
  KEY `idx_reviewer_id` (`reviewer_id`),
  KEY `idx_audit_status` (`audit_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审稿记录表';

-- ----------------------------
-- 3. 采编中心 - 编辑加工表
-- ----------------------------
DROP TABLE IF EXISTS `editor_edit`;
CREATE TABLE `editor_edit` (
  `edit_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编辑记录ID',
  `manuscript_id` bigint(20) NOT NULL COMMENT '稿件ID',
  `editor_id` bigint(20) DEFAULT NULL COMMENT '编辑ID',
  `editor_name` varchar(50) DEFAULT '' COMMENT '编辑姓名',
  `edit_status` char(1) DEFAULT '0' COMMENT '编辑状态(0待编辑 1编辑中 2已完成)',
  `edit_start_time` datetime DEFAULT NULL COMMENT '编辑开始时间',
  `edit_end_time` datetime DEFAULT NULL COMMENT '编辑完成时间',
  `edit_content` text COMMENT '编辑内容',
  `edit_suggestion` varchar(2000) DEFAULT '' COMMENT '编辑建议',
  `word_count_before` int(11) DEFAULT '0' COMMENT '编辑前字数',
  `word_count_after` int(11) DEFAULT '0' COMMENT '编辑后字数',
  `version` int(4) DEFAULT '1' COMMENT '版本号',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`edit_id`),
  KEY `idx_manuscript_id` (`manuscript_id`),
  KEY `idx_editor_id` (`editor_id`),
  KEY `idx_edit_status` (`edit_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='编辑加工记录表';

-- ----------------------------
-- 4. 采编中心 - 组稿表
-- ----------------------------
DROP TABLE IF EXISTS `editor_group`;
CREATE TABLE `editor_group` (
  `group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组稿ID',
  `group_name` varchar(100) DEFAULT '' COMMENT '组稿名称',
  `issue_id` bigint(20) DEFAULT NULL COMMENT '关联刊期ID',
  `group_type` char(1) DEFAULT '0' COMMENT '组稿类型(0简单组稿 1专题组稿)',
  `status` char(1) DEFAULT '0' COMMENT '状态(0组稿中 1已完成 2已签发)',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`group_id`),
  KEY `idx_issue_id` (`issue_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组稿信息表';

-- ----------------------------
-- 5. 组稿稿件关联表
-- ----------------------------
DROP TABLE IF EXISTS `editor_group_manuscript`;
CREATE TABLE `editor_group_manuscript` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` bigint(20) NOT NULL COMMENT '组稿ID',
  `manuscript_id` bigint(20) NOT NULL COMMENT '稿件ID',
  `sort_no` int(4) DEFAULT '1' COMMENT '排序号',
  `page_position` varchar(50) DEFAULT '' COMMENT '版面位置',
  `word_count` int(11) DEFAULT '0' COMMENT '预估字数',
  `page_count` decimal(3,1) DEFAULT '0.0' COMMENT '预估页数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_group_id` (`group_id`),
  KEY `idx_manuscript_id` (`manuscript_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组稿稿件关联表';

-- ----------------------------
-- 6. 稿件签发表
-- ----------------------------
DROP TABLE IF EXISTS `editor_sign`;
CREATE TABLE `editor_sign` (
  `sign_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签发ID',
  `manuscript_id` bigint(20) NOT NULL COMMENT '稿件ID',
  `issue_id` bigint(20) DEFAULT NULL COMMENT '刊期ID',
  `signer_id` bigint(20) DEFAULT NULL COMMENT '签发人ID',
  `signer_name` varchar(50) DEFAULT '' COMMENT '签发人姓名',
  `sign_status` char(1) DEFAULT '0' COMMENT '签发状态(0待签发 1已签发)',
  `sign_time` datetime DEFAULT NULL COMMENT '签发时间',
  `sign_opinion` varchar(500) DEFAULT '' COMMENT '签发意见',
  `page_start` int(4) DEFAULT '0' COMMENT '起始页码',
  `page_end` int(4) DEFAULT '0' COMMENT '结束页码',
  `layout_file_path` varchar(500) DEFAULT '' COMMENT '排版文件路径',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sign_id`),
  KEY `idx_manuscript_id` (`manuscript_id`),
  KEY `idx_sign_status` (`sign_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='稿件签发表';

-- ----------------------------
-- 7. 稿件操作日志表
-- ----------------------------
DROP TABLE IF EXISTS `editor_manuscript_log`;
CREATE TABLE `editor_manuscript_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `manuscript_id` bigint(20) NOT NULL COMMENT '稿件ID',
  `log_type` char(2) DEFAULT '' COMMENT '日志类型(01收稿 02分配 03送审 04审回 05退修 06修回 07录用 08退稿 09编辑 10签发 11发表)',
  `old_status` char(2) DEFAULT '' COMMENT '原状态',
  `new_status` char(2) DEFAULT '' COMMENT '新状态',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(50) DEFAULT '' COMMENT '操作人姓名',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(1000) DEFAULT '' COMMENT '操作备注',
  PRIMARY KEY (`log_id`),
  KEY `idx_manuscript_id` (`manuscript_id`),
  KEY `idx_operate_time` (`operate_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='稿件操作日志表';

-- ----------------------------
-- 8. 作者信息表
-- ----------------------------
DROP TABLE IF EXISTS `editor_author`;
CREATE TABLE `editor_author` (
  `author_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '作者ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关联系统用户ID',
  `real_name` varchar(50) DEFAULT '' COMMENT '真实姓名',
  `pen_name` varchar(50) DEFAULT '' COMMENT '笔名',
  `gender` char(1) DEFAULT '' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `affiliation` varchar(200) DEFAULT '' COMMENT '工作单位',
  `department` varchar(100) DEFAULT '' COMMENT '部门',
  `title` varchar(50) DEFAULT '' COMMENT '职称',
  `academic_degree` varchar(50) DEFAULT '' COMMENT '学历',
  `research_direction` varchar(200) DEFAULT '' COMMENT '研究方向',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机',
  `address` varchar(300) DEFAULT '' COMMENT '通讯地址',
  `zip_code` varchar(10) DEFAULT '' COMMENT '邮编',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `is_deleted` char(1) DEFAULT '0' COMMENT '删除标志',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`author_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_email` (`email`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='作者信息表';

-- ----------------------------
-- 9. 审稿人信息表
-- ----------------------------
DROP TABLE IF EXISTS `editor_reviewer`;
CREATE TABLE `editor_reviewer` (
  `reviewer_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '审稿人ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关联系统用户ID',
  `real_name` varchar(50) DEFAULT '' COMMENT '真实姓名',
  `gender` char(1) DEFAULT '' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `affiliation` varchar(200) DEFAULT '' COMMENT '工作单位',
  `department` varchar(100) DEFAULT '' COMMENT '部门',
  `title` varchar(50) DEFAULT '' COMMENT '职称',
  `academic_degree` varchar(50) DEFAULT '' COMMENT '学历',
  `research_direction` varchar(500) DEFAULT '' COMMENT '研究方向',
  `expertise_fields` varchar(500) DEFAULT '' COMMENT '专业领域',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机',
  `address` varchar(300) DEFAULT '' COMMENT '通讯地址',
  `review_count` int(11) DEFAULT '0' COMMENT '审稿次数',
  `accept_rate` decimal(5,2) DEFAULT '0.00' COMMENT '接受率',
  `avg_review_days` decimal(5,1) DEFAULT '0.0' COMMENT '平均审稿天数',
  `review_score_avg` decimal(3,1) DEFAULT '0.0' COMMENT '平均评分',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `is_deleted` char(1) DEFAULT '0' COMMENT '删除标志',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`reviewer_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_email` (`email`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审稿人信息表';

-- ----------------------------
-- 10. 审稿人申请/推荐表
-- ----------------------------
DROP TABLE IF EXISTS `editor_reviewer_apply`;
CREATE TABLE `editor_reviewer_apply` (
  `apply_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `user_id` bigint(20) NOT NULL COMMENT '申请人/被推荐人ID',
  `apply_type` char(1) DEFAULT '0' COMMENT '申请类型(0申请 1推荐)',
  `recommender_id` bigint(20) DEFAULT NULL COMMENT '推荐人ID',
  `recommender_name` varchar(50) DEFAULT '' COMMENT '推荐人姓名',
  `real_name` varchar(50) DEFAULT '' COMMENT '姓名',
  `affiliation` varchar(200) DEFAULT '' COMMENT '工作单位',
  `title` varchar(50) DEFAULT '' COMMENT '职称',
  `research_direction` varchar(500) DEFAULT '' COMMENT '研究方向',
  `expertise_fields` varchar(500) DEFAULT '' COMMENT '专业领域',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `apply_reason` varchar(1000) DEFAULT '' COMMENT '申请理由',
  `audit_status` char(1) DEFAULT '0' COMMENT '审核状态(0待审核 1已通过 2已拒绝)',
  `audit_opinion` varchar(500) DEFAULT '' COMMENT '审核意见',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_by` varchar(64) DEFAULT '' COMMENT '审核人',
  `is_deleted` char(1) DEFAULT '0' COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`apply_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_audit_status` (`audit_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审稿人申请/推荐表';

-- =============================================
-- 发行中心
-- =============================================
DROP TABLE IF EXISTS `publisher_year`;
CREATE TABLE `publisher_year` (
  `year_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '年份ID',
  `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号',
  `year` int(4) NOT NULL COMMENT '年份',
  `volume` int(4) DEFAULT NULL COMMENT '卷号',
  `name_cn` varchar(100) DEFAULT '' COMMENT '中文名',
  `name_en` varchar(100) DEFAULT '' COMMENT '英文名',
  `catalog_file` varchar(500) DEFAULT '' COMMENT '目录文件',
  `is_free` char(1) DEFAULT '0' COMMENT '是否免费(0否 1是)',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`year_id`),
  UNIQUE KEY `uk_journal_year` (`journal_code`, `year`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='期刊年份表';

DROP TABLE IF EXISTS `publisher_issue`;
CREATE TABLE `publisher_issue` (
  `issue_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '刊期ID',
  `year_id` bigint(20) NOT NULL COMMENT '年份ID',
  `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号',
  `year` int(4) NOT NULL COMMENT '年份',
  `volume` int(4) DEFAULT NULL COMMENT '卷',
  `period` varchar(50) NOT NULL COMMENT '期数/刊期（可为数字或字符）',
  `issue_name` varchar(100) DEFAULT '' COMMENT '刊期名称',
  `name_cn` varchar(100) DEFAULT '' COMMENT '中文名',
  `name_en` varchar(100) DEFAULT '' COMMENT '英文名',
  `sequence` int(4) DEFAULT NULL COMMENT '顺序',
  `special_name` varchar(100) DEFAULT '' COMMENT '专辑中文名',
  `special_en_name` varchar(100) DEFAULT '' COMMENT '专辑英文名',
  `important` text COMMENT '重点(中文)',
  `en_important` text COMMENT '重点(英文)',
  `image_url` varchar(500) DEFAULT '' COMMENT '封面(小图)',
  `larger_cover_url` varchar(500) DEFAULT '' COMMENT '封面(大图)',
  `contents_file` varchar(500) DEFAULT '' COMMENT '目录文件',
  `cn_image_explain` text COMMENT '封面说明(中文)',
  `en_image_explain` text COMMENT '封面说明(英文)',
  `request_money` char(1) DEFAULT '0' COMMENT '是否收费(0否 1是)',
  `is_free` char(1) DEFAULT '0' COMMENT '是否免费(0否 1是)',
  `is_latest` char(1) DEFAULT '0' COMMENT '是否最新一期文章(0否 1是)',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID',
  `subscribe_status` char(1) DEFAULT '0' COMMENT '订阅发送状态(0未发送 1已发送)',
  `doi_status` char(1) DEFAULT '0' COMMENT 'DOI注册状态(0未注册 1已注册)',
  `cstr_status` char(1) DEFAULT '0' COMMENT 'CSTR注册状态(0未注册 1已注册)',
  `baidu_status` char(1) DEFAULT '0' COMMENT '百度发布状态(0未发送 1已发送)',
  `publish_date` date DEFAULT NULL COMMENT '出版日期',
  `status` char(1) DEFAULT '0' COMMENT '状态(0待发布 1已发布 2已归档)',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `article_count` int(4) DEFAULT '0' COMMENT '文章数量',
  `page_count` int(4) DEFAULT '0' COMMENT '总页数',
  `cover_image` varchar(200) DEFAULT '' COMMENT '封面图片',
  `print_money` decimal(10,2) DEFAULT NULL COMMENT '印刷费',
  `publish_money` decimal(10,2) DEFAULT NULL COMMENT '发行费',
  `post_money` decimal(10,2) DEFAULT NULL COMMENT '邮费',
  `start_page` varchar(50) DEFAULT '' COMMENT '起始页码',
  `end_page` varchar(50) DEFAULT '' COMMENT '截止页码',
  `columns_info` text COMMENT '栏目信息(JSON)',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`issue_id`),
  KEY `idx_year_id` (`year_id`),
  KEY `idx_journal_code` (`journal_code`),
  KEY `idx_meeting_id` (`meeting_id`),
  KEY `idx_journal_year_period` (`journal_code`, `year`, `period`),
  KEY `idx_year_period` (`year`, `period`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='期刊刊期表';

DROP TABLE IF EXISTS `publisher_column`;
CREATE TABLE `publisher_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '栏目主键',
  `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号',
  `column_code` varchar(50) DEFAULT '' COMMENT '栏目ID/编码',
  `column_name` varchar(100) NOT NULL COMMENT '中文栏目',
  `name_en` varchar(100) DEFAULT '' COMMENT '英文栏目',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父栏目ID',
  `order_num` int(4) DEFAULT '0' COMMENT '顺序',
  `need_recommender` char(1) DEFAULT '0' COMMENT '是否需要推荐人(0否 1是)',
  `usage_scope` varchar(200) DEFAULT '' COMMENT '使用范围文本',
  `is_post_column` char(1) DEFAULT '1' COMMENT '使用范围(1投稿、内部查询、网刊发布 0网刊发布 2内部查询、网刊发布)',
  `file_no_prefix` varchar(50) DEFAULT '' COMMENT '稿件编号前缀',
  `en_abstract_money` decimal(10,2) DEFAULT NULL COMMENT '英文摘要加工费',
  `edit_name` varchar(255) DEFAULT '' COMMENT '编辑账号',
  `column_type` char(1) DEFAULT '0' COMMENT '栏目类型',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `description` varchar(500) DEFAULT '' COMMENT '描述',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`column_id`),
  KEY `idx_journal_column_code` (`journal_code`, `column_code`),
  KEY `idx_journal_code` (`journal_code`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='期刊栏目表';

DROP TABLE IF EXISTS `publisher_article`;
CREATE TABLE `publisher_article` (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `manuscript_id` bigint(20) DEFAULT NULL COMMENT '关联稿件ID',
  `issue_id` bigint(20) DEFAULT NULL COMMENT '关联刊期ID',
  `column_id` bigint(20) DEFAULT NULL COMMENT '关联栏目ID',
  `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号',
  `file_no` varchar(50) DEFAULT '' COMMENT '稿件编号',
  `title` varchar(500) NOT NULL COMMENT '标题',
  `title_en` varchar(500) DEFAULT '' COMMENT '英文标题',
  `subtitle` varchar(500) DEFAULT '' COMMENT '副标题',
  `author_names` varchar(500) DEFAULT '' COMMENT '作者姓名',
  `author_names_en` varchar(500) DEFAULT '' COMMENT '作者英文名',
  `author_org_zh` varchar(1000) DEFAULT '' COMMENT '作者单位中文名',
  `author_org_en` varchar(1000) DEFAULT '' COMMENT '作者单位英文名',
  `doi` varchar(200) DEFAULT '' COMMENT 'DOI',
  `abstract` text COMMENT '摘要',
  `abstract_en` text COMMENT '英文摘要',
  `keywords` varchar(500) DEFAULT '' COMMENT '关键词',
  `keywords_en` varchar(500) DEFAULT '' COMMENT '英文关键词',
  `fund_zh` varchar(500) DEFAULT '' COMMENT '基金中文名',
  `fund_en` varchar(500) DEFAULT '' COMMENT '基金英文名',
  `content` longtext COMMENT '正文内容',
  `reference_text` longtext COMMENT '参考文献',
  `citation_text` longtext COMMENT '引用文本',
  `page_start` int(4) DEFAULT '0' COMMENT '起始页码',
  `page_end` int(4) DEFAULT '0' COMMENT '结束页码',
  `word_count` int(11) DEFAULT '0' COMMENT '字数',
  `submit_time` datetime DEFAULT NULL COMMENT '投稿时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `volume` int(4) DEFAULT NULL COMMENT '卷',
  `period` int(4) DEFAULT NULL COMMENT '期',
  `year_no` int(4) DEFAULT NULL COMMENT '年',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览次数',
  `download_count` int(11) DEFAULT '0' COMMENT '下载次数',
  `html_view_count` int(11) DEFAULT '0' COMMENT 'HTML浏览次数',
  `cited_count` int(11) DEFAULT '0' COMMENT '被引次数',
  `self_cited_count` int(11) DEFAULT '0' COMMENT '自引次数',
  `other_cited_count` int(11) DEFAULT '0' COMMENT '他引次数',
  `cnki_cited_count` int(11) DEFAULT '0' COMMENT '知网引用次数',
  `cnki_title` varchar(500) DEFAULT '' COMMENT '知网标题',
  `cnki_match_status` char(1) DEFAULT '' COMMENT '知网匹配状态',
  `article_url` varchar(500) DEFAULT '' COMMENT '网址',
  `pdf_path` varchar(500) DEFAULT '' COMMENT '全文PDF路径',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  KEY `idx_manuscript_id` (`manuscript_id`),
  KEY `idx_issue_id` (`issue_id`),
  KEY `idx_file_no` (`file_no`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章信息表';

DROP TABLE IF EXISTS `publisher_comment`;
CREATE TABLE `publisher_comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户名',
  `content` varchar(2000) DEFAULT '' COMMENT '评论内容',
  `audit_status` char(1) DEFAULT '0' COMMENT '审核状态',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_by` varchar(64) DEFAULT '' COMMENT '审核人',
  `is_top` char(1) DEFAULT '0' COMMENT '是否置顶',
  `reply_to` bigint(20) DEFAULT NULL COMMENT '回复评论ID',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`comment_id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章评论表';

DROP TABLE IF EXISTS `publisher_virtual_album`;
CREATE TABLE `publisher_virtual_album` (
  `album_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专辑ID',
  `journal_code` varchar(50) DEFAULT '' COMMENT '杂志编号',
  `name_cn` varchar(200) NOT NULL COMMENT '中文名',
  `name_en` varchar(200) DEFAULT '' COMMENT '英文名',
  `finish_date` date DEFAULT NULL COMMENT '完成日期',
  `order_num` int(4) DEFAULT '0' COMMENT '顺序',
  `album_type` varchar(50) DEFAULT '' COMMENT '类型',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID',
  `cover_image` varchar(200) DEFAULT '' COMMENT '封面图片',
  `description` varchar(2000) DEFAULT '' COMMENT '专辑描述',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`album_id`),
  KEY `idx_journal_code` (`journal_code`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='虚拟专辑表';

DROP TABLE IF EXISTS `publisher_album_article`;
CREATE TABLE `publisher_album_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `album_id` bigint(20) NOT NULL COMMENT '专辑ID',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `sort_no` int(4) DEFAULT '1' COMMENT '排序号',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_album_id` (`album_id`),
  KEY `idx_article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='虚拟专辑文章关联表';

DROP TABLE IF EXISTS `publisher_reader`;
CREATE TABLE `publisher_reader` (
  `reader_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '读者ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关联系统用户ID',
  `real_name` varchar(50) DEFAULT '' COMMENT '真实姓名',
  `nick_name` varchar(50) DEFAULT '' COMMENT '昵称',
  `gender` char(1) DEFAULT '' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机',
  `address` varchar(300) DEFAULT '' COMMENT '通讯地址',
  `zip_code` varchar(10) DEFAULT '' COMMENT '邮编',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`reader_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='读者信息表';

DROP TABLE IF EXISTS `publisher_subscriber`;
CREATE TABLE `publisher_subscriber` (
  `subscriber_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订户ID',
  `reader_id` bigint(20) DEFAULT NULL COMMENT '关联读者ID',
  `real_name` varchar(50) NOT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机',
  `address` varchar(300) DEFAULT '' COMMENT '邮寄地址',
  `zip_code` varchar(10) DEFAULT '' COMMENT '邮编',
  `company` varchar(200) DEFAULT '' COMMENT '单位',
  `subscribe_start_date` date DEFAULT NULL COMMENT '订阅开始日期',
  `subscribe_end_date` date DEFAULT NULL COMMENT '订阅结束日期',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`subscriber_id`),
  KEY `idx_reader_id` (`reader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订户信息表';

DROP TABLE IF EXISTS `publisher_price`;
CREATE TABLE `publisher_price` (
  `price_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '定价ID',
  `year_id` bigint(20) DEFAULT NULL COMMENT '年份ID',
  `year` int(4) DEFAULT '0' COMMENT '年份',
  `period` int(3) DEFAULT '0' COMMENT '期数',
  `price_type` char(1) DEFAULT '0' COMMENT '定价类型',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `valid_start_date` date DEFAULT NULL COMMENT '生效开始日期',
  `valid_end_date` date DEFAULT NULL COMMENT '生效结束日期',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`price_id`),
  KEY `idx_year` (`year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='期刊定价表';

DROP TABLE IF EXISTS `publisher_inventory`;
CREATE TABLE `publisher_inventory` (
  `inventory_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库存ID',
  `issue_id` bigint(20) NOT NULL COMMENT '刊期ID',
  `year` int(4) NOT NULL COMMENT '年份',
  `period` int(3) NOT NULL COMMENT '期数',
  `stock_qty` int(11) DEFAULT '0' COMMENT '库存量',
  `sold_qty` int(11) DEFAULT '0' COMMENT '已售数量',
  `allocated_qty` int(11) DEFAULT '0' COMMENT '已分配数量',
  `warehouse` varchar(100) DEFAULT '' COMMENT '仓库',
  `location` varchar(100) DEFAULT '' COMMENT '库位',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`inventory_id`),
  KEY `idx_issue_id` (`issue_id`),
  KEY `idx_year_period` (`year`, `period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='期刊库存表';

DROP TABLE IF EXISTS `publisher_order`;
CREATE TABLE `publisher_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(64) NOT NULL COMMENT '订单编号',
  `subscriber_id` bigint(20) DEFAULT NULL COMMENT '订户ID',
  `order_type` char(1) DEFAULT '0' COMMENT '订单类型',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '订单金额',
  `paid_amount` decimal(12,2) DEFAULT '0.00' COMMENT '已付金额',
  `pay_status` char(1) DEFAULT '0' COMMENT '支付状态',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_method` varchar(20) DEFAULT '' COMMENT '支付方式',
  `order_status` char(1) DEFAULT '0' COMMENT '订单状态',
  `shipping_name` varchar(50) DEFAULT '' COMMENT '收货人',
  `shipping_phone` varchar(20) DEFAULT '' COMMENT '收货电话',
  `shipping_address` varchar(500) DEFAULT '' COMMENT '收货地址',
  `shipping_zip_code` varchar(10) DEFAULT '' COMMENT '邮编',
  `tracking_no` varchar(100) DEFAULT '' COMMENT '物流单号',
  `shipping_time` datetime DEFAULT NULL COMMENT '发货时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_subscriber_id` (`subscriber_id`),
  KEY `idx_pay_status` (`pay_status`),
  KEY `idx_order_status` (`order_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

DROP TABLE IF EXISTS `publisher_order_item`;
CREATE TABLE `publisher_order_item` (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `issue_id` bigint(20) DEFAULT NULL COMMENT '刊期ID',
  `year` int(4) DEFAULT '0' COMMENT '年份',
  `period` int(3) DEFAULT '0' COMMENT '期数',
  `product_name` varchar(200) DEFAULT '' COMMENT '商品名称',
  `qty` int(4) DEFAULT '1' COMMENT '数量',
  `unit_price` decimal(10,2) DEFAULT '0.00' COMMENT '单价',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '金额',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

DROP TABLE IF EXISTS `publisher_invoice`;
CREATE TABLE `publisher_invoice` (
  `invoice_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '发票ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '关联订单ID',
  `invoice_no` varchar(64) DEFAULT '' COMMENT '发票编号',
  `invoice_type` char(1) DEFAULT '0' COMMENT '发票类型',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '发票金额',
  `tax_amount` decimal(12,2) DEFAULT '0.00' COMMENT '税额',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '价税合计',
  `title` varchar(200) DEFAULT '' COMMENT '发票抬头',
  `tax_id` varchar(50) DEFAULT '' COMMENT '税号',
  `apply_status` char(1) DEFAULT '0' COMMENT '申请状态',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_by` varchar(64) DEFAULT '' COMMENT '审核人',
  `audit_opinion` varchar(500) DEFAULT '' COMMENT '审核意见',
  `make_status` char(1) DEFAULT '0' COMMENT '开票状态',
  `make_time` datetime DEFAULT NULL COMMENT '开票时间',
  `post_status` char(1) DEFAULT '0' COMMENT '邮寄状态',
  `post_time` datetime DEFAULT NULL COMMENT '邮寄时间',
  `tracking_no` varchar(100) DEFAULT '' COMMENT '邮寄单号',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_apply_status` (`apply_status`),
  KEY `idx_post_status` (`post_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='发票表';

DROP TABLE IF EXISTS `publisher_post`;
CREATE TABLE `publisher_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '寄书ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '关联订单ID',
  `post_type` char(1) DEFAULT '0' COMMENT '邮寄类型',
  `shipping_name` varchar(50) DEFAULT '' COMMENT '收货人',
  `shipping_phone` varchar(20) DEFAULT '' COMMENT '收货电话',
  `shipping_address` varchar(500) DEFAULT '' COMMENT '收货地址',
  `shipping_zip_code` varchar(10) DEFAULT '' COMMENT '邮编',
  `tracking_no` varchar(100) DEFAULT '' COMMENT '物流单号',
  `courier` varchar(50) DEFAULT '' COMMENT '快递公司',
  `post_status` char(1) DEFAULT '0' COMMENT '邮寄状态',
  `post_time` datetime DEFAULT NULL COMMENT '邮寄时间',
  `sign_time` datetime DEFAULT NULL COMMENT '签收时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='寄书记录表';

DROP TABLE IF EXISTS `publisher_sample_post`;
CREATE TABLE `publisher_sample_post` (
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

DROP TABLE IF EXISTS `publisher_push_subscriber`;
CREATE TABLE `publisher_push_subscriber` (
  `sub_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订阅ID',
  `reader_id` bigint(20) NOT NULL COMMENT '读者ID',
  `push_type` char(1) DEFAULT '0' COMMENT '推送类型',
  `keywords` varchar(500) DEFAULT '' COMMENT '订阅关键词',
  `column_ids` varchar(500) DEFAULT '' COMMENT '订阅栏目ID列表',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`sub_id`),
  KEY `idx_reader_id` (`reader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订阅用户表';

DROP TABLE IF EXISTS `publisher_push_keyword`;
CREATE TABLE `publisher_push_keyword` (
  `push_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '推送ID',
  `keyword` varchar(100) NOT NULL COMMENT '关键词',
  `push_content` varchar(2000) DEFAULT '' COMMENT '推送内容',
  `push_time` datetime DEFAULT NULL COMMENT '推送时间',
  `push_status` char(1) DEFAULT '0' COMMENT '推送状态',
  `push_count` int(11) DEFAULT '0' COMMENT '推送数量',
  `success_count` int(11) DEFAULT '0' COMMENT '成功数量',
  `fail_count` int(11) DEFAULT '0' COMMENT '失败数量',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`push_id`),
  KEY `idx_keyword` (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='关键词推送记录表';

-- =============================================
-- 广告中心
-- =============================================
DROP TABLE IF EXISTS `ad_area`;
CREATE TABLE `ad_area` (
  `area_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '区域ID',
  `area_name` varchar(100) NOT NULL COMMENT '区域名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父区域ID',
  `area_code` varchar(20) DEFAULT '' COMMENT '区域编码',
  `order_num` int(4) DEFAULT '0' COMMENT '排序号',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='区域信息表';

DROP TABLE IF EXISTS `ad_position_type`;
CREATE TABLE `ad_position_type` (
  `type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `type_name` varchar(100) NOT NULL COMMENT '类型名称',
  `type_code` varchar(50) DEFAULT '' COMMENT '类型编码',
  `description` varchar(500) DEFAULT '' COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告位类型表';

DROP TABLE IF EXISTS `ad_position`;
CREATE TABLE `ad_position` (
  `position_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '广告位ID',
  `position_name` varchar(100) NOT NULL COMMENT '广告位名称',
  `type_id` bigint(20) DEFAULT NULL COMMENT '广告位类型ID',
  `type_name` varchar(100) DEFAULT '' COMMENT '广告位类型名称',
  `width` int(6) DEFAULT '0' COMMENT '宽度',
  `height` int(6) DEFAULT '0' COMMENT '高度',
  `position_code` varchar(50) DEFAULT '' COMMENT '广告位编码',
  `page_location` varchar(100) DEFAULT '' COMMENT '页面位置',
  `description` varchar(500) DEFAULT '' COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `sort_order` int(4) DEFAULT '0' COMMENT '排序号',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`position_id`),
  KEY `idx_type_id` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告位表';

DROP TABLE IF EXISTS `ad_type`;
CREATE TABLE `ad_type` (
  `type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `type_name` varchar(100) NOT NULL COMMENT '类型名称',
  `type_code` varchar(50) DEFAULT '' COMMENT '类型编码',
  `description` varchar(500) DEFAULT '' COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告类型表';

DROP TABLE IF EXISTS `ad_customer`;
CREATE TABLE `ad_customer` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_name` varchar(200) NOT NULL COMMENT '客户名称',
  `contact_person` varchar(50) DEFAULT '' COMMENT '联系人',
  `phone` varchar(20) DEFAULT '' COMMENT '联系电话',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `address` varchar(300) DEFAULT '' COMMENT '地址',
  `area_id` bigint(20) DEFAULT NULL COMMENT '区域ID',
  `company_name` varchar(200) DEFAULT '' COMMENT '公司名称',
  `company_type` varchar(50) DEFAULT '' COMMENT '公司类型',
  `tax_id` varchar(50) DEFAULT '' COMMENT '税号',
  `bank_name` varchar(100) DEFAULT '' COMMENT '开户银行',
  `bank_account` varchar(50) DEFAULT '' COMMENT '银行账号',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  KEY `idx_area_id` (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告客户表';

DROP TABLE IF EXISTS `ad_bargain`;
CREATE TABLE `ad_bargain` (
  `bargain_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '合同ID',
  `bargain_no` varchar(64) NOT NULL COMMENT '合同编号',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `customer_name` varchar(200) DEFAULT '' COMMENT '客户名称',
  `bargain_name` varchar(200) DEFAULT '' COMMENT '合同名称',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '合同金额',
  `sign_date` date DEFAULT NULL COMMENT '签订日期',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `bargain_content` text COMMENT '合同内容',
  `bargain_file` varchar(500) DEFAULT '' COMMENT '合同文件路径',
  `apply_status` char(1) DEFAULT '0' COMMENT '申请状态',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_by` varchar(64) DEFAULT '' COMMENT '审核人',
  `audit_opinion` varchar(500) DEFAULT '' COMMENT '审核意见',
  `print_status` char(1) DEFAULT '0' COMMENT '打印状态',
  `print_time` datetime DEFAULT NULL COMMENT '打印时间',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`bargain_id`),
  UNIQUE KEY `uk_bargain_no` (`bargain_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告合同表';

DROP TABLE IF EXISTS `ad_info`;
CREATE TABLE `ad_info` (
  `ad_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '广告ID',
  `bargain_id` bigint(20) DEFAULT NULL COMMENT '关联合同ID',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户ID',
  `ad_name` varchar(200) NOT NULL COMMENT '广告名称',
  `type_id` bigint(20) DEFAULT NULL COMMENT '广告类型ID',
  `type_name` varchar(100) DEFAULT '' COMMENT '广告类型名称',
  `position_id` bigint(20) DEFAULT NULL COMMENT '广告位ID',
  `position_name` varchar(100) DEFAULT '' COMMENT '广告位名称',
  `ad_content` text COMMENT '广告内容',
  `ad_image` varchar(500) DEFAULT '' COMMENT '广告图片路径',
  `ad_link` varchar(500) DEFAULT '' COMMENT '广告链接',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `price` decimal(12,2) DEFAULT '0.00' COMMENT '广告价格',
  `issue_ids` varchar(500) DEFAULT '' COMMENT '投放刊期ID列表',
  `apply_status` char(1) DEFAULT '0' COMMENT '申请状态',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_by` varchar(64) DEFAULT '' COMMENT '审核人',
  `audit_opinion` varchar(500) DEFAULT '' COMMENT '审核意见',
  `make_status` char(1) DEFAULT '0' COMMENT '制作状态',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览次数',
  `click_count` int(11) DEFAULT '0' COMMENT '点击次数',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ad_id`),
  KEY `idx_bargain_id` (`bargain_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告信息表';

DROP TABLE IF EXISTS `ad_payment`;
CREATE TABLE `ad_payment` (
  `payment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '支付ID',
  `bargain_id` bigint(20) DEFAULT NULL COMMENT '关联合同ID',
  `ad_id` bigint(20) DEFAULT NULL COMMENT '关联广告ID',
  `payment_no` varchar(64) NOT NULL COMMENT '支付编号',
  `payment_amount` decimal(12,2) DEFAULT '0.00' COMMENT '支付金额',
  `payment_method` varchar(20) DEFAULT '' COMMENT '支付方式',
  `payment_status` char(1) DEFAULT '0' COMMENT '支付状态',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `receipt_file` varchar(500) DEFAULT '' COMMENT '收据文件',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  UNIQUE KEY `uk_payment_no` (`payment_no`),
  KEY `idx_bargain_id` (`bargain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付信息表';

DROP TABLE IF EXISTS `ad_invoice`;
CREATE TABLE `ad_invoice` (
  `invoice_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '发票ID',
  `bargain_id` bigint(20) DEFAULT NULL COMMENT '关联合同ID',
  `invoice_no` varchar(64) DEFAULT '' COMMENT '发票编号',
  `invoice_type` char(1) DEFAULT '0' COMMENT '发票类型',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '发票金额',
  `tax_amount` decimal(12,2) DEFAULT '0.00' COMMENT '税额',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '价税合计',
  `title` varchar(200) DEFAULT '' COMMENT '发票抬头',
  `tax_id` varchar(50) DEFAULT '' COMMENT '税号',
  `apply_status` char(1) DEFAULT '0' COMMENT '申请状态',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_by` varchar(64) DEFAULT '' COMMENT '审核人',
  `make_status` char(1) DEFAULT '0' COMMENT '开票状态',
  `make_time` datetime DEFAULT NULL COMMENT '开票时间',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `idx_bargain_id` (`bargain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告发票表';

-- =============================================
-- 统计中心 + 作者中心 + 审稿中心 + 会议管理 + 互动管理
-- =============================================
DROP TABLE IF EXISTS `stat_data`;
CREATE TABLE `stat_data` (
  `stat_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '统计ID',
  `stat_type` varchar(50) NOT NULL COMMENT '统计类型',
  `stat_name` varchar(100) DEFAULT '' COMMENT '统计名称',
  `stat_date` date DEFAULT NULL COMMENT '统计日期',
  `year` int(4) DEFAULT '0' COMMENT '统计年份',
  `month` int(2) DEFAULT '0' COMMENT '统计月份',
  `data_value` decimal(18,4) DEFAULT '0.0000' COMMENT '统计数值',
  `data_unit` varchar(20) DEFAULT '' COMMENT '数据单位',
  `data_json` text COMMENT '统计详情JSON',
  `stat_time` datetime DEFAULT NULL COMMENT '统计时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`stat_id`),
  KEY `idx_stat_type` (`stat_type`),
  KEY `idx_stat_date` (`stat_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统计数据表';

DROP TABLE IF EXISTS `stat_site_access`;
CREATE TABLE `stat_site_access` (
  `access_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户名',
  `page_url` varchar(500) DEFAULT '' COMMENT '访问页面URL',
  `page_name` varchar(100) DEFAULT '' COMMENT '页面名称',
  `referer_url` varchar(500) DEFAULT '' COMMENT '来源URL',
  `ip_addr` varchar(50) DEFAULT '' COMMENT '访问IP',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `access_time` datetime DEFAULT NULL COMMENT '访问时间',
  `stay_time` int(11) DEFAULT '0' COMMENT '停留时间(秒)',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`access_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_access_time` (`access_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='网站访问日志表';

DROP TABLE IF EXISTS `author_submission`;
CREATE TABLE `author_submission` (
  `submission_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '投稿ID',
  `user_id` bigint(20) NOT NULL COMMENT '作者用户ID',
  `manuscript_id` bigint(20) DEFAULT NULL COMMENT '关联稿件ID',
  `title` varchar(500) NOT NULL COMMENT '稿件标题',
  `abstract` text COMMENT '摘要',
  `keywords` varchar(500) DEFAULT '' COMMENT '关键词',
  `file_path` varchar(500) DEFAULT '' COMMENT '稿件文件路径',
  `submission_time` datetime DEFAULT NULL COMMENT '投稿时间',
  `status` char(2) DEFAULT '01' COMMENT '状态',
  `feedback` text COMMENT '编辑反馈',
  `feedback_time` datetime DEFAULT NULL COMMENT '反馈时间',
  `is_deleted` char(1) DEFAULT '0' COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`submission_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='作者投稿表';

DROP TABLE IF EXISTS `reviewer_task`;
CREATE TABLE `reviewer_task` (
  `task_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `audit_id` bigint(20) NOT NULL COMMENT '关联审稿记录ID',
  `manuscript_id` bigint(20) NOT NULL COMMENT '稿件ID',
  `reviewer_id` bigint(20) NOT NULL COMMENT '审稿人ID',
  `title` varchar(500) DEFAULT '' COMMENT '稿件标题',
  `abstract` text COMMENT '稿件摘要',
  `task_status` char(1) DEFAULT '0' COMMENT '任务状态(0待接受 1已接受 2审稿中 3已完成 4已拒绝)',
  `accept_time` datetime DEFAULT NULL COMMENT '接受时间',
  `reject_reason` varchar(500) DEFAULT '' COMMENT '拒绝理由',
  `deadline_time` datetime DEFAULT NULL COMMENT '截止时间',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `idx_reviewer_id` (`reviewer_id`),
  KEY `idx_task_status` (`task_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审稿任务表';

DROP TABLE IF EXISTS `meeting_meeting`;
CREATE TABLE `meeting_meeting` (
  `meeting_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会议ID',
  `meeting_name` varchar(200) NOT NULL COMMENT '会议名称',
  `meeting_code` varchar(50) DEFAULT '' COMMENT '会议编号',
  `meeting_type` varchar(50) DEFAULT '' COMMENT '会议类型',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `location` varchar(200) DEFAULT '' COMMENT '会议地点',
  `host` varchar(100) DEFAULT '' COMMENT '主办方',
  `contact_person` varchar(50) DEFAULT '' COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT '' COMMENT '联系电话',
  `description` varchar(2000) DEFAULT '' COMMENT '会议描述',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `max_participants` int(6) DEFAULT '0' COMMENT '最大参会人数',
  `actual_participants` int(6) DEFAULT '0' COMMENT '实际参会人数',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议表';

DROP TABLE IF EXISTS `meeting_hotel`;
CREATE TABLE `meeting_hotel` (
  `hotel_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '酒店ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '关联会议ID',
  `hotel_name` varchar(200) NOT NULL COMMENT '酒店名称',
  `address` varchar(300) DEFAULT '' COMMENT '地址',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `star_level` int(1) DEFAULT '0' COMMENT '星级',
  `description` varchar(500) DEFAULT '' COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`hotel_id`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='酒店表';

DROP TABLE IF EXISTS `meeting_hotel_room`;
CREATE TABLE `meeting_hotel_room` (
  `room_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '房型ID',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店ID',
  `room_type` varchar(50) NOT NULL COMMENT '房型名称',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `max_people` int(2) DEFAULT '1' COMMENT '最大入住人数',
  `total_qty` int(4) DEFAULT '0' COMMENT '房间总数',
  `available_qty` int(4) DEFAULT '0' COMMENT '可用数量',
  `description` varchar(500) DEFAULT '' COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`room_id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='酒店房型表';

DROP TABLE IF EXISTS `meeting_user_group`;
CREATE TABLE `meeting_user_group` (
  `group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '关联会议ID',
  `group_name` varchar(100) NOT NULL COMMENT '用户类型名称',
  `group_code` varchar(50) DEFAULT '' COMMENT '类型编码',
  `description` varchar(500) DEFAULT '' COMMENT '描述',
  `sort_order` int(4) DEFAULT '0' COMMENT '排序号',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='参会用户类型表';

DROP TABLE IF EXISTS `meeting_fees_price`;
CREATE TABLE `meeting_fees_price` (
  `price_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '价格ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '关联会议ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '用户类型ID',
  `fee_type` varchar(50) DEFAULT '' COMMENT '费用类型',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `valid_start_date` date DEFAULT NULL COMMENT '生效开始日期',
  `valid_end_date` date DEFAULT NULL COMMENT '生效结束日期',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`price_id`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='注册费价格表';

DROP TABLE IF EXISTS `meeting_user`;
CREATE TABLE `meeting_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '参会代表ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '关联会议ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '用户类型ID',
  `real_name` varchar(50) NOT NULL COMMENT '姓名',
  `gender` char(1) DEFAULT '' COMMENT '性别',
  `affiliation` varchar(200) DEFAULT '' COMMENT '单位',
  `title` varchar(50) DEFAULT '' COMMENT '职称',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `payment_status` char(1) DEFAULT '0' COMMENT '支付状态',
  `payment_amount` decimal(10,2) DEFAULT '0.00' COMMENT '支付金额',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='参会代表表';

DROP TABLE IF EXISTS `meeting_tickets`;
CREATE TABLE `meeting_tickets` (
  `ticket_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '票务ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '关联会议ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '参会代表ID',
  `ticket_type` varchar(50) DEFAULT '' COMMENT '票务类型',
  `ticket_no` varchar(64) DEFAULT '' COMMENT '票务编号',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='票务表';

DROP TABLE IF EXISTS `meeting_travel_way`;
CREATE TABLE `meeting_travel_way` (
  `way_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '线路ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '关联会议ID',
  `way_name` varchar(200) NOT NULL COMMENT '线路名称',
  `description` varchar(2000) DEFAULT '' COMMENT '线路描述',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `start_date` date DEFAULT NULL COMMENT '出发日期',
  `end_date` date DEFAULT NULL COMMENT '返回日期',
  `max_people` int(4) DEFAULT '0' COMMENT '最大人数',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`way_id`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='旅游线路表';

DROP TABLE IF EXISTS `meeting_travel_booked`;
CREATE TABLE `meeting_travel_booked` (
  `booked_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预订ID',
  `way_id` bigint(20) DEFAULT NULL COMMENT '线路ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '参会代表ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID',
  `qty` int(4) DEFAULT '1' COMMENT '人数',
  `total_amount` decimal(10,2) DEFAULT '0.00' COMMENT '总金额',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`booked_id`),
  KEY `idx_way_id` (`way_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='旅游线路预订表';

DROP TABLE IF EXISTS `meeting_hotel_booked`;
CREATE TABLE `meeting_hotel_booked` (
  `booked_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预订ID',
  `hotel_id` bigint(20) DEFAULT NULL COMMENT '酒店ID',
  `room_id` bigint(20) DEFAULT NULL COMMENT '房型ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '参会代表ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '会议ID',
  `check_in_date` date DEFAULT NULL COMMENT '入住日期',
  `check_out_date` date DEFAULT NULL COMMENT '退房日期',
  `qty` int(4) DEFAULT '1' COMMENT '房间数量',
  `total_amount` decimal(10,2) DEFAULT '0.00' COMMENT '总金额',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`booked_id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='酒店预订表';

DROP TABLE IF EXISTS `meeting_fees`;
CREATE TABLE `meeting_fees` (
  `fee_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '财务ID',
  `meeting_id` bigint(20) DEFAULT NULL COMMENT '关联会议ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '参会代表ID',
  `fee_type` varchar(50) DEFAULT '' COMMENT '费用类型',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `payment_method` varchar(20) DEFAULT '' COMMENT '支付方式',
  `payment_status` char(1) DEFAULT '0' COMMENT '支付状态',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fee_id`),
  KEY `idx_meeting_id` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议财务表';

DROP TABLE IF EXISTS `msg_remark`;
CREATE TABLE `msg_remark` (
  `remark_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '留言ID',
  `article_id` bigint(20) DEFAULT NULL COMMENT '关联文章ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户名',
  `content` varchar(2000) DEFAULT '' COMMENT '留言内容',
  `reply_content` varchar(2000) DEFAULT '' COMMENT '回复内容',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `reply_by` varchar(64) DEFAULT '' COMMENT '回复人',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`remark_id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户留言表';

DROP TABLE IF EXISTS `msg_email`;
CREATE TABLE `msg_email` (
  `email_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '邮件ID',
  `send_from` varchar(100) DEFAULT '' COMMENT '发件人',
  `send_to` varchar(500) DEFAULT '' COMMENT '收件人',
  `cc` varchar(500) DEFAULT '' COMMENT '抄送',
  `bcc` varchar(500) DEFAULT '' COMMENT '密送',
  `subject` varchar(500) DEFAULT '' COMMENT '邮件主题',
  `content` text COMMENT '邮件内容',
  `attachments` varchar(1000) DEFAULT '' COMMENT '附件路径',
  `send_status` char(1) DEFAULT '0' COMMENT '发送状态(0待发送 1已发送 2发送失败)',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `fail_reason` varchar(500) DEFAULT '' COMMENT '失败原因',
  `retry_count` int(4) DEFAULT '0' COMMENT '重试次数',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`email_id`),
  KEY `idx_send_status` (`send_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮件表';

DROP TABLE IF EXISTS `msg_sms`;
CREATE TABLE `msg_sms` (
  `sms_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '短信ID',
  `phone` varchar(20) NOT NULL COMMENT '手机号码',
  `content` varchar(500) DEFAULT '' COMMENT '短信内容',
  `send_status` char(1) DEFAULT '0' COMMENT '发送状态(0待发送 1已发送 2发送失败)',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `fail_reason` varchar(500) DEFAULT '' COMMENT '失败原因',
  `retry_count` int(4) DEFAULT '0' COMMENT '重试次数',
  `send_by` varchar(64) DEFAULT '' COMMENT '发送人',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`sms_id`),
  KEY `idx_phone` (`phone`),
  KEY `idx_send_status` (`send_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信表';
