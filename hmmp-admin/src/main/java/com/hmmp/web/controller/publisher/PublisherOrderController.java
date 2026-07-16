package com.hmmp.web.controller.publisher;

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
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.system.domain.publisher.PublisherInventory;
import com.hmmp.system.domain.publisher.PublisherInvoice;
import com.hmmp.system.domain.publisher.PublisherOrder;
import com.hmmp.system.domain.publisher.PublisherPost;
import com.hmmp.system.domain.publisher.PublisherPrice;
import com.hmmp.system.service.publisher.IPublisherInventoryService;
import com.hmmp.system.service.publisher.IPublisherInvoiceService;
import com.hmmp.system.service.publisher.IPublisherOrderService;
import com.hmmp.system.service.publisher.IPublisherPostService;
import com.hmmp.system.service.publisher.IPublisherPriceService;

/**
 * 征订管理 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/publisher")
public class PublisherOrderController extends BaseController {

    @Autowired
    private IPublisherPriceService priceService;

    @Autowired
    private IPublisherInventoryService inventoryService;

    @Autowired
    private IPublisherOrderService orderService;

    @Autowired
    private IPublisherInvoiceService invoiceService;

    @Autowired
    private IPublisherPostService postService;

    // ========== 定价管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:price:list')")
    @GetMapping("/price/list")
    public TableDataInfo priceList(PublisherPrice price) {
        startPage();
        List<PublisherPrice> list = priceService.selectPriceList(price);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:price:query')")
    @GetMapping("/price/{priceId}")
    public AjaxResult priceGetInfo(@PathVariable Long priceId) {
        return success(priceService.selectPriceById(priceId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:price:add')")
    @PostMapping("/price")
    public AjaxResult priceAdd(@RequestBody PublisherPrice price) {
        return toAjax(priceService.insertPrice(price));
    }

    @PreAuthorize("@ss.hasPermi('publisher:price:edit')")
    @PutMapping("/price")
    public AjaxResult priceEdit(@RequestBody PublisherPrice price) {
        return toAjax(priceService.updatePrice(price));
    }

    @PreAuthorize("@ss.hasPermi('publisher:price:remove')")
    @DeleteMapping("/price/{priceIds}")
    public AjaxResult priceRemove(@PathVariable Long[] priceIds) {
        return toAjax(priceService.deletePriceByIds(priceIds));
    }

    // ========== 库存管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:inventory:list')")
    @GetMapping("/inventory/list")
    public TableDataInfo inventoryList(PublisherInventory inventory) {
        startPage();
        List<PublisherInventory> list = inventoryService.selectInventoryList(inventory);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:inventory:query')")
    @GetMapping("/inventory/{inventoryId}")
    public AjaxResult inventoryGetInfo(@PathVariable Long inventoryId) {
        return success(inventoryService.selectInventoryById(inventoryId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:inventory:add')")
    @PostMapping("/inventory")
    public AjaxResult inventoryAdd(@RequestBody PublisherInventory inventory) {
        return toAjax(inventoryService.insertInventory(inventory));
    }

    @PreAuthorize("@ss.hasPermi('publisher:inventory:edit')")
    @PutMapping("/inventory")
    public AjaxResult inventoryEdit(@RequestBody PublisherInventory inventory) {
        return toAjax(inventoryService.updateInventory(inventory));
    }

    @PreAuthorize("@ss.hasPermi('publisher:inventory:remove')")
    @DeleteMapping("/inventory/{inventoryIds}")
    public AjaxResult inventoryRemove(@PathVariable Long[] inventoryIds) {
        return toAjax(inventoryService.deleteInventoryByIds(inventoryIds));
    }

    // ========== 订单管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:order:list')")
    @GetMapping("/order/list")
    public TableDataInfo orderList(PublisherOrder order) {
        startPage();
        List<PublisherOrder> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:order:query')")
    @GetMapping("/order/{orderId}")
    public AjaxResult orderGetInfo(@PathVariable Long orderId) {
        return success(orderService.selectOrderById(orderId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:order:add')")
    @PostMapping("/order")
    public AjaxResult orderAdd(@RequestBody PublisherOrder order) {
        return toAjax(orderService.insertOrder(order));
    }

    @PreAuthorize("@ss.hasPermi('publisher:order:pay')")
    @PutMapping("/order/pay")
    public AjaxResult orderPay(@RequestBody PublisherOrder order) {
        return toAjax(orderService.payOrder(order));
    }

    @PreAuthorize("@ss.hasPermi('publisher:order:ship')")
    @PutMapping("/order/ship")
    public AjaxResult orderShip(@RequestBody PublisherOrder order) {
        return toAjax(orderService.shipOrder(order));
    }

    // ========== 发票管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:invoice:list')")
    @GetMapping("/invoice/list")
    public TableDataInfo invoiceList(PublisherInvoice invoice) {
        startPage();
        List<PublisherInvoice> list = invoiceService.selectInvoiceList(invoice);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:invoice:apply')")
    @PostMapping("/invoice/apply")
    public AjaxResult invoiceApply(@RequestBody PublisherInvoice invoice) {
        return toAjax(invoiceService.applyInvoice(invoice));
    }

    @PreAuthorize("@ss.hasPermi('publisher:invoice:audit')")
    @PutMapping("/invoice/audit")
    public AjaxResult invoiceAudit(@RequestBody PublisherInvoice invoice) {
        return toAjax(invoiceService.auditInvoice(invoice));
    }

    @PreAuthorize("@ss.hasPermi('publisher:invoice:make')")
    @PutMapping("/invoice/make")
    public AjaxResult invoiceMake(@RequestBody PublisherInvoice invoice) {
        return toAjax(invoiceService.makeInvoice(invoice));
    }

    @PreAuthorize("@ss.hasPermi('publisher:invoice:post')")
    @PutMapping("/invoice/post")
    public AjaxResult invoicePost(@RequestBody PublisherInvoice invoice) {
        return toAjax(invoiceService.postInvoice(invoice));
    }

    // ========== 寄书管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:post:list')")
    @GetMapping("/post/list")
    public TableDataInfo postList(PublisherPost post) {
        startPage();
        List<PublisherPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:post:add')")
    @PostMapping("/post")
    public AjaxResult postAdd(@RequestBody PublisherPost post) {
        return toAjax(postService.insertPost(post));
    }

    @PreAuthorize("@ss.hasPermi('publisher:post:post')")
    @PutMapping("/post/post")
    public AjaxResult postExecute(@RequestBody PublisherPost post) {
        return toAjax(postService.executePost(post));
    }
}