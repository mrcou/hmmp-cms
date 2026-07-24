package com.hmmp.system.service;

import java.util.Map;

import com.hmmp.system.domain.vo.SiteSecurityConfigVo;

/**
 * 站点配置 服务层
 */
public interface ISysSiteConfigService
{
    /**
     * 获取安全配置
     */
    SiteSecurityConfigVo getSecurityConfig();

    /**
     * 保存安全配置
     */
    void saveSecurityConfig(SiteSecurityConfigVo config, String operator);

    /**
     * 获取站点分区配置
     */
    Map<String, Object> getSectionConfig(String section);

    /**
     * 保存站点分区配置
     */
    void saveSectionConfig(String section, Map<String, Object> config, String operator);
}
