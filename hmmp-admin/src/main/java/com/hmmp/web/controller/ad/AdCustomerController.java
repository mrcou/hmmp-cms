package com.hmmp.web.controller.ad;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.hmmp.system.domain.AdCustomer;
import com.hmmp.system.service.IAdCustomerService;

/**
 * 广告客户管理
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/ad/book/customer")
public class AdCustomerController extends BaseController
{
    @Autowired
    private IAdCustomerService customerService;

    /**
     * 获取客户列表
     */
    @PreAuthorize("@ss.hasPermi('ad:book:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(AdCustomer customer)
    {
        startPage();
        List<AdCustomer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    /**
     * 获取客户详情
     */
    @PreAuthorize("@ss.hasPermi('ad:book:customer:query')")
    @GetMapping("/{customerId}")
    public AjaxResult getInfo(@PathVariable Long customerId)
    {
        return success(customerService.selectCustomerById(customerId));
    }

    /**
     * 新增客户
     */
    @PreAuthorize("@ss.hasPermi('ad:book:customer:add')")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AdCustomer customer)
    {
        customer.setCreateBy(getUsername());
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@ss.hasPermi('ad:book:customer:edit')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AdCustomer customer)
    {
        customer.setUpdateBy(getUsername());
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除客户
     */
    @PreAuthorize("@ss.hasPermi('ad:book:customer:remove')")
    @Log(title = "客户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{customerIds}")
    public AjaxResult remove(@PathVariable Long[] customerIds)
    {
        customerService.deleteCustomerByIds(customerIds);
        return success();
    }
}