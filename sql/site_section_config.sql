-- 站点分区配置（JSON 存储，每个分区一条 sys_config 记录）
INSERT INTO `sys_config` (`config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `remark`) VALUES
('站点配置-编辑部信息', 'sys.site.section.journal-info', '{}', 'Y', 'admin', NOW(), '站点配置-编辑部信息 JSON'),
('站点配置-期刊设置', 'sys.site.section.journal-basic', '{}', 'Y', 'admin', NOW(), '站群设置-期刊设置 JSON'),
('站点配置-邮件服务器', 'sys.site.section.mail-info', '{}', 'Y', 'admin', NOW(), '站点配置-邮件服务器 JSON'),
('站点配置-定时任务', 'sys.site.section.timed-task', '{}', 'Y', 'admin', NOW(), '站点配置-定时任务 JSON'),
('站点配置-费用处理', 'sys.site.section.cost-processing', '{}', 'Y', 'admin', NOW(), '站点配置-费用处理 JSON'),
('站点配置-作者参数', 'sys.site.section.author-config', '{}', 'Y', 'admin', NOW(), '站点配置-作者参数 JSON'),
('站点配置-编委参数', 'sys.site.section.editorial-config', '{}', 'Y', 'admin', NOW(), '站点配置-编委参数 JSON'),
('站点配置-审稿人参数', 'sys.site.section.reviewer-config', '{}', 'Y', 'admin', NOW(), '站点配置-审稿人参数 JSON'),
('站点配置-读者参数', 'sys.site.section.reader-config', '{}', 'Y', 'admin', NOW(), '站点配置-读者参数 JSON'),
('站点配置-外部接口', 'sys.site.section.external-interface', '{}', 'Y', 'admin', NOW(), '站点配置-外部接口 JSON'),
('站点配置-组稿规则', 'sys.site.section.combining-articles', '{}', 'Y', 'admin', NOW(), '站点配置-组稿规则 JSON'),
('站点配置-其他参数', 'sys.site.section.other-config', '{}', 'Y', 'admin', NOW(), '站点配置-其他参数 JSON');
