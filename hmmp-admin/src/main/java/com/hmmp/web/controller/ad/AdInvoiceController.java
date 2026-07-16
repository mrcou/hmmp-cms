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
import com.hmmp.system.domain.AdInvoice;
import com.hmmp.system.service.IAdInvoiceService;

/**
 * 广告发票管理
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/ad")
public class AdInvoiceController extends BaseController
{
    @Autowired
    private IAdInvoiceService invoiceService;

    /**
     * 获取发票申请列表
     */
    @PreAuthorize("@ss.hasPermi('ad:apply:invoice:list')")
    @GetMapping("/apply/invoice/list")
    public TableDataInfo list(AdInvoice invoice)
    {
        startPage();
        List<AdInvoice> list = invoiceService.selectInvoiceList(invoice);
        return getDataTable(list);
    }

    /**
     * 发票申请
     */
    @PreAuthorize("@ss.hasPermi('ad:apply:invoice')")
    @Log(title = "发票管理", businessType = BusinessType.INSERT)
    @PostMapping("/apply/invoice")
    public AjaxResult apply(@RequestBody AdInvoice invoice)
    {
        invoice.setCreateBy(getUsername());
        invoice.setApplyStatus("1");
        return toAjax(invoiceService.insertInvoice(invoice));
    }

    /**
     * 发票审核
     */
    @PreAuthorize("@ss.hasPermi('ad:audit:invoice')")
    @Log(title = "发票管理", businessType = BusinessType.UPDATE)
    @PutMapping("/audit/invoice")
    public AjaxResult audit(@RequestBody AdInvoice invoice)
    {
        invoice.setUpdateBy(getUsername());
        invoice.setAuditBy(getUsername());
        invoice.setAuditTime(new Date());
        return toAjax(invoiceService.updateInvoice(invoice));
    }

    /**
     * 开具发票
     */
    @PreAuthorize("@ss.hasPermi('ad:make:invoice')")
    @Log(title = "发票管理", businessType = BusinessType.UPDATE)
    @PutMapping("/make/invoice")
    public AjaxResult make(@RequestBody AdInvoice invoice)
    {
        invoice.setUpdateBy(getUsername());
        invoice.setMakeStatus("1");
        invoice.setMakeTime(new Date());
        return toAjax(invoiceService.updateInvoice(invoice));
    }
}