package com.hmmp.web.controller.meeting;

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
import com.hmmp.system.domain.meeting.MeetingFees;
import com.hmmp.system.domain.meeting.MeetingFeesPrice;
import com.hmmp.system.domain.meeting.MeetingTickets;
import com.hmmp.system.domain.meeting.MeetingTravelBooked;
import com.hmmp.system.domain.meeting.MeetingTravelWay;
import com.hmmp.system.service.IMeetingFeesPriceService;
import com.hmmp.system.service.IMeetingFeesService;
import com.hmmp.system.service.IMeetingTicketsService;
import com.hmmp.system.service.IMeetingTravelBookedService;
import com.hmmp.system.service.IMeetingTravelWayService;

/**
 * 财务/票务/旅游管理
 *
 * @author hmmp
 */
@RestController
@RequestMapping("/meeting")
public class MeetingFinanceController extends BaseController
{
    @Autowired
    private IMeetingFeesPriceService feesPriceService;

    @Autowired
    private IMeetingFeesService feesService;

    @Autowired
    private IMeetingTicketsService ticketsService;

    @Autowired
    private IMeetingTravelWayService travelWayService;

    @Autowired
    private IMeetingTravelBookedService travelBookedService;

    // ==================== 注册费价格 ====================

    /**
     * 获取注册费价格列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:fees:price:list')")
    @GetMapping("/fees/price/list")
    public TableDataInfo priceList(MeetingFeesPrice price)
    {
        startPage();
        List<MeetingFeesPrice> list = feesPriceService.selectPriceList(price);
        return getDataTable(list);
    }

    /**
     * 获取注册费价格详细信息
     */
    @PreAuthorize("@ss.hasPermi('meeting:fees:price:query')")
    @GetMapping(value = "/fees/price/{priceId}")
    public AjaxResult getPriceInfo(@PathVariable Long priceId)
    {
        return success(feesPriceService.selectPriceById(priceId));
    }

    /**
     * 新增注册费价格
     */
    @PreAuthorize("@ss.hasPermi('meeting:fees:price:add')")
    @PostMapping("/fees/price")
    public AjaxResult addPrice(@RequestBody MeetingFeesPrice price)
    {
        return toAjax(feesPriceService.insertPrice(price));
    }

    /**
     * 修改注册费价格
     */
    @PreAuthorize("@ss.hasPermi('meeting:fees:price:edit')")
    @PutMapping("/fees/price")
    public AjaxResult editPrice(@RequestBody MeetingFeesPrice price)
    {
        return toAjax(feesPriceService.updatePrice(price));
    }

    /**
     * 删除注册费价格
     */
    @PreAuthorize("@ss.hasPermi('meeting:fees:price:remove')")
    @DeleteMapping("/fees/price/{priceIds}")
    public AjaxResult removePrice(@PathVariable Long[] priceIds)
    {
        return toAjax(feesPriceService.deletePriceByIds(priceIds));
    }

    // ==================== 财务流水 ====================

    /**
     * 获取财务流水列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:fees:list')")
    @GetMapping("/fees/list")
    public TableDataInfo feesList(MeetingFees fees)
    {
        startPage();
        List<MeetingFees> list = feesService.selectFeesList(fees);
        return getDataTable(list);
    }

    // ==================== 票务管理 ====================

    /**
     * 获取票务列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:tickets:list')")
    @GetMapping("/tickets/list")
    public TableDataInfo ticketsList(MeetingTickets tickets)
    {
        startPage();
        List<MeetingTickets> list = ticketsService.selectTicketsList(tickets);
        return getDataTable(list);
    }

    /**
     * 获取票务详细信息
     */
    @PreAuthorize("@ss.hasPermi('meeting:tickets:query')")
    @GetMapping(value = "/tickets/{ticketId}")
    public AjaxResult getTicketsInfo(@PathVariable Long ticketId)
    {
        return success(ticketsService.selectTicketsById(ticketId));
    }

    /**
     * 新增票务
     */
    @PreAuthorize("@ss.hasPermi('meeting:tickets:add')")
    @PostMapping("/tickets")
    public AjaxResult addTickets(@RequestBody MeetingTickets tickets)
    {
        return toAjax(ticketsService.insertTickets(tickets));
    }

    /**
     * 修改票务
     */
    @PreAuthorize("@ss.hasPermi('meeting:tickets:edit')")
    @PutMapping("/tickets")
    public AjaxResult editTickets(@RequestBody MeetingTickets tickets)
    {
        return toAjax(ticketsService.updateTickets(tickets));
    }

    /**
     * 删除票务
     */
    @PreAuthorize("@ss.hasPermi('meeting:tickets:remove')")
    @DeleteMapping("/tickets/{ticketIds}")
    public AjaxResult removeTickets(@PathVariable Long[] ticketIds)
    {
        return toAjax(ticketsService.deleteTicketsByIds(ticketIds));
    }

    // ==================== 旅游线路 ====================

    /**
     * 获取旅游线路列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:travel:way:list')")
    @GetMapping("/travel/way/list")
    public TableDataInfo travelWayList(MeetingTravelWay travelWay)
    {
        startPage();
        List<MeetingTravelWay> list = travelWayService.selectTravelWayList(travelWay);
        return getDataTable(list);
    }

    /**
     * 获取旅游线路详细信息
     */
    @PreAuthorize("@ss.hasPermi('meeting:travel:way:query')")
    @GetMapping(value = "/travel/way/{wayId}")
    public AjaxResult getTravelWayInfo(@PathVariable Long wayId)
    {
        return success(travelWayService.selectTravelWayById(wayId));
    }

    /**
     * 新增旅游线路
     */
    @PreAuthorize("@ss.hasPermi('meeting:travel:way:add')")
    @PostMapping("/travel/way")
    public AjaxResult addTravelWay(@RequestBody MeetingTravelWay travelWay)
    {
        return toAjax(travelWayService.insertTravelWay(travelWay));
    }

    /**
     * 修改旅游线路
     */
    @PreAuthorize("@ss.hasPermi('meeting:travel:way:edit')")
    @PutMapping("/travel/way")
    public AjaxResult editTravelWay(@RequestBody MeetingTravelWay travelWay)
    {
        return toAjax(travelWayService.updateTravelWay(travelWay));
    }

    /**
     * 删除旅游线路
     */
    @PreAuthorize("@ss.hasPermi('meeting:travel:way:remove')")
    @DeleteMapping("/travel/way/{wayIds}")
    public AjaxResult removeTravelWay(@PathVariable Long[] wayIds)
    {
        return toAjax(travelWayService.deleteTravelWayByIds(wayIds));
    }

    // ==================== 线路预订 ====================

    /**
     * 获取线路预订列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:travel:booked:list')")
    @GetMapping("/travel/booked/list")
    public TableDataInfo travelBookedList(MeetingTravelBooked booked)
    {
        startPage();
        List<MeetingTravelBooked> list = travelBookedService.selectTravelBookedList(booked);
        return getDataTable(list);
    }
}