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
import com.hmmp.system.domain.AdBargain;
import com.hmmp.system.service.IAdBargainService;

/**
 * 广告合同管理
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/ad")
public class AdBargainController extends BaseController
{
    @Autowired
    private IAdBargainService bargainService;

    /**
     * 获取合同列表
     */
    @PreAuthorize("@ss.hasPermi('ad:book:bargain:list')")
    @GetMapping("/book/bargain/list")
    public TableDataInfo list(AdBargain bargain)
    {
        startPage();
        List<AdBargain> list = bargainService.selectBargainList(bargain);
        return getDataTable(list);
    }

    /**
     * 合同申请
     */
    @PreAuthorize("@ss.hasPermi('ad:apply:bargain')")
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping("/apply/bargain")
    public AjaxResult apply(@RequestBody AdBargain bargain)
    {
        bargain.setCreateBy(getUsername());
        bargain.setApplyStatus("1");
        return toAjax(bargainService.insertBargain(bargain));
    }

    /**
     * 合同审核
     */
    @PreAuthorize("@ss.hasPermi('ad:audit:bargain')")
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping("/audit/bargain")
    public AjaxResult audit(@RequestBody AdBargain bargain)
    {
        bargain.setUpdateBy(getUsername());
        bargain.setAuditBy(getUsername());
        bargain.setAuditTime(new Date());
        return toAjax(bargainService.updateBargain(bargain));
    }

    /**
     * 合同打印
     */
    @PreAuthorize("@ss.hasPermi('ad:make:bargain')")
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping("/make/bargain")
    public AjaxResult make(@RequestBody AdBargain bargain)
    {
        bargain.setUpdateBy(getUsername());
        bargain.setPrintStatus("1");
        bargain.setPrintTime(new Date());
        return toAjax(bargainService.updateBargain(bargain));
    }
}