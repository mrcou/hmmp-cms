package com.hmmp.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.system.domain.vo.SiteSecurityConfigVo;
import com.hmmp.system.service.ISysSiteConfigService;

/**
 * 站点配置
 */
@RestController
@RequestMapping("/system/site-config")
public class SysSiteConfigController extends BaseController
{
    @Autowired
    private ISysSiteConfigService siteConfigService;

    /**
     * 获取安全配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping("/security")
    public AjaxResult getSecurityConfig()
    {
        return success(siteConfigService.getSecurityConfig());
    }

    /**
     * 保存安全配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @Log(title = "站点安全配置", businessType = BusinessType.UPDATE)
    @PutMapping("/security")
    public AjaxResult saveSecurityConfig(@RequestBody SiteSecurityConfigVo config)
    {
        siteConfigService.saveSecurityConfig(config, getUsername());
        return success();
    }

    /**
     * 获取站点分区配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping("/section/{section}")
    public AjaxResult getSectionConfig(@PathVariable("section") String section)
    {
        return success(siteConfigService.getSectionConfig(section));
    }

    /**
     * 保存站点分区配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @Log(title = "站点分区配置", businessType = BusinessType.UPDATE)
    @PutMapping("/section/{section}")
    public AjaxResult saveSectionConfig(@PathVariable("section") String section,
            @RequestBody Map<String, Object> config)
    {
        siteConfigService.saveSectionConfig(section, config, getUsername());
        return success();
    }
}
