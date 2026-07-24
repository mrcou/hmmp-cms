package com.hmmp.system.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.hmmp.common.constant.CacheConstants;
import com.hmmp.common.core.text.Convert;
import com.hmmp.common.core.redis.RedisCache;
import com.hmmp.common.exception.ServiceException;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.system.domain.SysConfig;
import com.hmmp.system.domain.vo.SiteSecurityConfigVo;
import com.hmmp.system.mapper.SysConfigMapper;
import com.hmmp.system.service.ISysConfigService;
import com.hmmp.system.service.ISysSiteConfigService;

/**
 * 站点配置 服务层实现
 */
@Service
public class SysSiteConfigServiceImpl implements ISysSiteConfigService
{
    private static final String KEY_PASSWORD_CHECK = "sys.security.passwordCheck";
    private static final String KEY_FORCED_PASSWORD_CHANGE = "sys.account.initPasswordModify";
    private static final String KEY_PASSWORD_LIFE = "sys.account.passwordValidateDays";
    private static final String KEY_PASSWORD_ERROR_NUM = "sys.security.passwordErrorNum";
    private static final String KEY_LOCK_TIME = "sys.security.lockTime";
    private static final String KEY_RESET_COUNTER = "sys.security.resetCounter";
    private static final String KEY_COOKIE_EXPIRES = "sys.security.cookieExpires";
    private static final String KEY_LOGIN_REMINDER = "sys.security.loginReminder";
    private static final String KEY_AUDIT_LOG_CAPACITY = "sys.security.auditLogCapacity";
    private static final String KEY_SECTION_PREFIX = "sys.site.section.";

    private static final Map<String, String> SECTION_NAMES;
    static
    {
        Map<String, String> sections = new HashMap<>();
        sections.put("journal-info", "编辑部信息");
        sections.put("journal-basic", "期刊设置");
        sections.put("mail-info", "邮件服务器");
        sections.put("timed-task", "定时任务");
        sections.put("cost-processing", "费用处理");
        sections.put("author-config", "作者参数");
        sections.put("editorial-config", "编委参数");
        sections.put("reviewer-config", "审稿人参数");
        sections.put("reader-config", "读者参数");
        sections.put("external-interface", "外部接口");
        sections.put("combining-articles", "组稿规则");
        sections.put("other-config", "其他参数");
        SECTION_NAMES = Collections.unmodifiableMap(sections);
    }

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public SiteSecurityConfigVo getSecurityConfig()
    {
        SiteSecurityConfigVo config = new SiteSecurityConfigVo();
        config.setPasswordCheck(normalizeSelectValue(configService.selectConfigByKey(KEY_PASSWORD_CHECK)));
        config.setForcedPasswordChange(mapForcedPasswordChangeFromDb(
                configService.selectConfigByKey(KEY_FORCED_PASSWORD_CHANGE)));
        config.setPasswordLife(Convert.toInt(configService.selectConfigByKey(KEY_PASSWORD_LIFE), 0));
        config.setPasswordErrorNum(Convert.toInt(configService.selectConfigByKey(KEY_PASSWORD_ERROR_NUM), 3));
        config.setLockTime(Convert.toInt(configService.selectConfigByKey(KEY_LOCK_TIME), 60));
        config.setResetCounter(Convert.toInt(configService.selectConfigByKey(KEY_RESET_COUNTER), 60));
        config.setCookieExpires(Convert.toInt(configService.selectConfigByKey(KEY_COOKIE_EXPIRES), 30));
        config.setLoginReminder(normalizeSelectValue(configService.selectConfigByKey(KEY_LOGIN_REMINDER)));
        config.setSaveAuditLogDayNum(Convert.toInt(configService.selectConfigByKey(KEY_AUDIT_LOG_CAPACITY), 0));
        return config;
    }

    @Override
    public void saveSecurityConfig(SiteSecurityConfigVo config, String operator)
    {
        validateSecurityConfig(config);
        updateConfigValue(KEY_PASSWORD_CHECK, normalizeSelectValue(config.getPasswordCheck()), operator);
        updateConfigValue(KEY_FORCED_PASSWORD_CHANGE, mapForcedPasswordChangeToDb(config.getForcedPasswordChange()), operator);
        updateConfigValue(KEY_PASSWORD_LIFE, String.valueOf(config.getPasswordLife()), operator);
        updateConfigValue(KEY_PASSWORD_ERROR_NUM, String.valueOf(config.getPasswordErrorNum()), operator);
        updateConfigValue(KEY_LOCK_TIME, String.valueOf(config.getLockTime()), operator);
        updateConfigValue(KEY_RESET_COUNTER, String.valueOf(config.getResetCounter()), operator);
        updateConfigValue(KEY_COOKIE_EXPIRES, String.valueOf(config.getCookieExpires()), operator);
        updateConfigValue(KEY_LOGIN_REMINDER, normalizeSelectValue(config.getLoginReminder()), operator);
        updateConfigValue(KEY_AUDIT_LOG_CAPACITY, String.valueOf(config.getSaveAuditLogDayNum()), operator);
    }

    private void validateSecurityConfig(SiteSecurityConfigVo config)
    {
        if (config.getPasswordLife() == null || config.getPasswordLife() < 0 || config.getPasswordLife() > 999)
        {
            throw new ServiceException("密码使用期限有效值为0-999");
        }
        if (config.getPasswordErrorNum() == null || config.getPasswordErrorNum() < 1 || config.getPasswordErrorNum() > 10)
        {
            throw new ServiceException("密码尝试次数有效值为1-10");
        }
        if (config.getLockTime() == null || config.getLockTime() < 0 || config.getLockTime() > 10080)
        {
            throw new ServiceException("锁定时长有效值为0-10080");
        }
        if (config.getResetCounter() == null || config.getResetCounter() < 1 || config.getResetCounter() > 10080)
        {
            throw new ServiceException("重置计数器有效值为1-10080");
        }
        if (config.getCookieExpires() == null || config.getCookieExpires() < 1 || config.getCookieExpires() > 43200)
        {
            throw new ServiceException("登录超时时间有效值为1-43200");
        }
        if (config.getSaveAuditLogDayNum() == null || config.getSaveAuditLogDayNum() < 0)
        {
            throw new ServiceException("审计日志容量上限不能小于0");
        }
    }

    private void updateConfigValue(String configKey, String configValue, String operator)
    {
        SysConfig query = new SysConfig();
        query.setConfigKey(configKey);
        SysConfig existing = configMapper.selectConfig(query);
        if (existing == null)
        {
            throw new ServiceException("参数键名不存在: " + configKey);
        }
        existing.setConfigValue(StringUtils.nvl(configValue, ""));
        existing.setUpdateBy(operator);
        int row = configMapper.updateConfigValue(existing);
        if (row > 0)
        {
            redisCache.setCacheObject(CacheConstants.SYS_CONFIG_KEY + configKey, existing.getConfigValue());
        }
    }

    private String normalizeSelectValue(String value)
    {
        return StringUtils.isEmpty(value) ? "" : value;
    }

    private String mapForcedPasswordChangeFromDb(String value)
    {
        return "1".equals(value) ? "1" : "";
    }

    private String mapForcedPasswordChangeToDb(String value)
    {
        return "1".equals(value) ? "1" : "0";
    }

    @Override
    public Map<String, Object> getSectionConfig(String section)
    {
        validateSection(section);
        String configKey = KEY_SECTION_PREFIX + section;
        String json = configService.selectConfigByKey(configKey);
        if (StringUtils.isEmpty(json))
        {
            return new HashMap<>();
        }
        Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
        return data == null ? new HashMap<>() : data;
    }

    @Override
    public void saveSectionConfig(String section, Map<String, Object> config, String operator)
    {
        validateSection(section);
        if (config == null)
        {
            config = new HashMap<>();
        }
        String configKey = KEY_SECTION_PREFIX + section;
        upsertSectionConfigValue(section, configKey, JSON.toJSONString(config), operator);
    }

    private void upsertSectionConfigValue(String section, String configKey, String configValue, String operator)
    {
        SysConfig query = new SysConfig();
        query.setConfigKey(configKey);
        SysConfig existing = configMapper.selectConfig(query);
        if (existing == null)
        {
            SysConfig created = new SysConfig();
            created.setConfigName("站点配置-" + SECTION_NAMES.get(section));
            created.setConfigKey(configKey);
            created.setConfigValue(StringUtils.nvl(configValue, "{}"));
            created.setConfigType("Y");
            created.setCreateBy(operator);
            configService.insertConfig(created);
            return;
        }
        existing.setConfigValue(StringUtils.nvl(configValue, ""));
        existing.setUpdateBy(operator);
        int row = configMapper.updateConfigValue(existing);
        if (row > 0)
        {
            redisCache.setCacheObject(CacheConstants.SYS_CONFIG_KEY + configKey, existing.getConfigValue());
        }
    }

    private void validateSection(String section)
    {
        if (!SECTION_NAMES.containsKey(section))
        {
            throw new ServiceException("不支持的站点配置分区: " + section);
        }
    }
}
