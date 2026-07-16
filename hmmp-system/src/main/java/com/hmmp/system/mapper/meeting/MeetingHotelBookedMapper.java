package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingHotelBooked;

/**
 * 酒店预订表 数据层
 *
 * @author hmmp
 */
public interface MeetingHotelBookedMapper
{
    public List<MeetingHotelBooked> selectHotelBookedList(MeetingHotelBooked booked);

    public MeetingHotelBooked selectHotelBookedById(Long bookedId);

    public int insertHotelBooked(MeetingHotelBooked booked);

    public int updateHotelBooked(MeetingHotelBooked booked);

    public int deleteHotelBookedById(Long bookedId);

    public int deleteHotelBookedByIds(Long[] bookedIds);
}