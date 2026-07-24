-- 站点安全配置参数（在已有 sys_config 基础上追加）
INSERT INTO `sys_config` (`config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `remark`) VALUES
('安全配置-密码复杂度验证', 'sys.security.passwordCheck', '', 'Y', 'admin', NOW(), '空=不验证，1=一般，2=中等，3=高强度'),
('安全配置-密码尝试次数', 'sys.security.passwordErrorNum', '3', 'Y', 'admin', NOW(), '有效值1-10，默认值3'),
('安全配置-锁定时长(分钟)', 'sys.security.lockTime', '60', 'Y', 'admin', NOW(), '有效值0-10080，默认值60'),
('安全配置-重置计数器(分钟)', 'sys.security.resetCounter', '60', 'Y', 'admin', NOW(), '有效值1-10080，默认值60'),
('安全配置-登录超时时间(分钟)', 'sys.security.cookieExpires', '30', 'Y', 'admin', NOW(), '有效值1-43200，默认30'),
('安全配置-登录提醒', 'sys.security.loginReminder', '', 'Y', 'admin', NOW(), '空=不开启，1=全部开启，2=用户自行选择'),
('安全配置-审计日志容量上限(G)', 'sys.security.auditLogCapacity', '0', 'Y', 'admin', NOW(), '0或空永久保留');
