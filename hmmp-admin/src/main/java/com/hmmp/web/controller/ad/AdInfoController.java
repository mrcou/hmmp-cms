package com.hmmp.web.controller.ad;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.system.domain.AdInfo;
import com.hmmp.system.service.IAdInfoService;

/**
 * 广告信息管理
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/ad")
public class AdInfoController extends BaseController
{
    @Autowired
    private IAdInfoService adService;

    /**
     * 获取广告列表
     */
    @PreAuthorize("@ss.hasPermi('ad:book:ad:list')")
    @GetMapping("/book/ad/list")
    public TableDataInfo list(AdInfo ad)
    {
        startPage();
        List<AdInfo> list = adService.selectAdList(ad);
        return getDataTable(list);
    }

    /**
     * 广告申请
     */
    @PreAuthorize("@ss.hasPermi('ad:apply:ad')")
    @Log(title = "广告管理", businessType = BusinessType.INSERT)
    @PostMapping("/apply/ad")
    public AjaxResult apply(@RequestBody AdInfo ad)
    {
        ad.setCreateBy(getUsername());
        ad.setApplyStatus("1");
        return toAjax(adService.insertAd(ad));
    }

    /**
     * 广告审核
     */
    @PreAuthorize("@ss.hasPermi('ad:audit:ad')")
    @Log(title = "广告管理", businessType = BusinessType.UPDATE)
    @PutMapping("/audit/ad")
    public AjaxResult audit(@RequestBody AdInfo ad)
    {
        ad.setUpdateBy(getUsername());
        ad.setAuditBy(getUsername());
        ad.setAuditTime(new Date());
        return toAjax(adService.updateAd(ad));
    }

    /**
     * 广告制作
     */
    @PreAuthorize("@ss.hasPermi('ad:make:ad')")
    @Log(title = "广告管理", businessType = BusinessType.UPDATE)
    @PutMapping("/make/ad")
    public AjaxResult make(@RequestBody AdInfo ad)
    {
        ad.setUpdateBy(getUsername());
        ad.setMakeStatus("1");
        return toAjax(adService.updateAd(ad));
    }
}