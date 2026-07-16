package com.hmmp.web.controller.ad;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.system.domain.AdBargain;
import com.hmmp.system.domain.AdInfo;
import com.hmmp.system.domain.AdPayment;
import com.hmmp.system.service.IAdBargainService;
import com.hmmp.system.service.IAdInfoService;
import com.hmmp.system.service.IAdPaymentService;

/**
 * 广告信息统计
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/ad/book")
public class AdStatController extends BaseController
{
    @Autowired
    private IAdBargainService bargainService;

    @Autowired
    private IAdInfoService adService;

    @Autowired
    private IAdPaymentService paymentService;

    /**
     * 信息统计
     */
    @PreAuthorize("@ss.hasPermi('ad:book:stat')")
    @GetMapping("/stat")
    public AjaxResult stat()
    {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("bargainCount", bargainService.selectBargainList(new AdBargain()).size());
        ajax.put("adCount", adService.selectAdList(new AdInfo()).size());
        ajax.put("paymentCount", paymentService.selectPaymentList(new AdPayment()).size());
        return ajax;
    }

    /**
     * 版位统计
     */
    @PreAuthorize("@ss.hasPermi('ad:book:position')")
    @GetMapping("/position")
    public AjaxResult position()
    {
        AjaxResult ajax = AjaxResult.success();
        List<AdInfo> adList = adService.selectAdList(new AdInfo());
        ajax.put("totalCount", adList.size());
        ajax.put("adList", adList);
        return ajax;
    }
}