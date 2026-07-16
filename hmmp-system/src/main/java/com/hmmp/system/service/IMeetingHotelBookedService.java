package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingHotelBooked;

/**
 * 酒店预订 服务层
 *
 * @author hmmp
 */
public interface IMeetingHotelBookedService
{
    public List<MeetingHotelBooked> selectHotelBookedList(MeetingHotelBooked booked);

    public MeetingHotelBooked selectHotelBookedById(Long bookedId);

    public int insertHotelBooked(MeetingHotelBooked booked);

    public int updateHotelBooked(MeetingHotelBooked booked);

    public int deleteHotelBookedByIds(Long[] bookedIds);
}