package com.hmmp.web.controller.ad;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.system.domain.AdPayment;
import com.hmmp.system.service.IAdPaymentService;

/**
 * 广告支付管理
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/ad/book/cost")
public class AdPaymentController extends BaseController
{
    @Autowired
    private IAdPaymentService paymentService;

    /**
     * 获取支付列表
     */
    @PreAuthorize("@ss.hasPermi('ad:book:cost:list')")
    @GetMapping("/list")
    public TableDataInfo list(AdPayment payment)
    {
        startPage();
        List<AdPayment> list = paymentService.selectPaymentList(payment);
        return getDataTable(list);
    }

    /**
     * 新增支付记录
     */
    @PreAuthorize("@ss.hasPermi('ad:book:cost:add')")
    @Log(title = "支付管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AdPayment payment)
    {
        payment.setCreateBy(getUsername());
        return toAjax(paymentService.insertPayment(payment));
    }
}