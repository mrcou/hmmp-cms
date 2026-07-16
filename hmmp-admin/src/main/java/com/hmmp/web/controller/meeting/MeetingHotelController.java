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
import com.hmmp.system.domain.meeting.MeetingHotel;
import com.hmmp.system.domain.meeting.MeetingHotelBooked;
import com.hmmp.system.domain.meeting.MeetingHotelRoom;
import com.hmmp.system.service.IMeetingHotelBookedService;
import com.hmmp.system.service.IMeetingHotelRoomService;
import com.hmmp.system.service.IMeetingHotelService;

/**
 * 酒店管理
 *
 * @author hmmp
 */
@RestController
@RequestMapping("/meeting/hotel")
public class MeetingHotelController extends BaseController
{
    @Autowired
    private IMeetingHotelService hotelService;

    @Autowired
    private IMeetingHotelRoomService roomService;

    @Autowired
    private IMeetingHotelBookedService hotelBookedService;

    /**
     * 获取酒店列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeetingHotel hotel)
    {
        startPage();
        List<MeetingHotel> list = hotelService.selectHotelList(hotel);
        return getDataTable(list);
    }

    /**
     * 获取酒店详细信息
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:query')")
    @GetMapping(value = "/{hotelId}")
    public AjaxResult getInfo(@PathVariable Long hotelId)
    {
        return success(hotelService.selectHotelById(hotelId));
    }

    /**
     * 新增酒店
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:add')")
    @PostMapping
    public AjaxResult add(@RequestBody MeetingHotel hotel)
    {
        return toAjax(hotelService.insertHotel(hotel));
    }

    /**
     * 修改酒店
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody MeetingHotel hotel)
    {
        return toAjax(hotelService.updateHotel(hotel));
    }

    /**
     * 删除酒店
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:remove')")
    @DeleteMapping("/{hotelIds}")
    public AjaxResult remove(@PathVariable Long[] hotelIds)
    {
        return toAjax(hotelService.deleteHotelByIds(hotelIds));
    }

    /**
     * 获取酒店房型列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:list')")
    @GetMapping("/room/list")
    public TableDataInfo roomList(MeetingHotelRoom room)
    {
        startPage();
        List<MeetingHotelRoom> list = roomService.selectRoomList(room);
        return getDataTable(list);
    }

    /**
     * 新增酒店房型
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:add')")
    @PostMapping("/room")
    public AjaxResult addRoom(@RequestBody MeetingHotelRoom room)
    {
        return toAjax(roomService.insertRoom(room));
    }

    /**
     * 修改酒店房型
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:edit')")
    @PutMapping("/room")
    public AjaxResult editRoom(@RequestBody MeetingHotelRoom room)
    {
        return toAjax(roomService.updateRoom(room));
    }

    /**
     * 删除酒店房型
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:remove')")
    @DeleteMapping("/room/{roomIds}")
    public AjaxResult removeRoom(@PathVariable Long[] roomIds)
    {
        return toAjax(roomService.deleteRoomByIds(roomIds));
    }

    /**
     * 获取酒店预订列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:hotel:list')")
    @GetMapping("/booked/list")
    public TableDataInfo bookedList(MeetingHotelBooked booked)
    {
        startPage();
        List<MeetingHotelBooked> list = hotelBookedService.selectHotelBookedList(booked);
        return getDataTable(list);
    }
}