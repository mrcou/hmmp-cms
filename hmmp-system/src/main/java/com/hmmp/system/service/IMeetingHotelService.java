package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingHotel;

/**
 * 酒店 服务层
 *
 * @author hmmp
 */
public interface IMeetingHotelService
{
    public List<MeetingHotel> selectHotelList(MeetingHotel hotel);

    public MeetingHotel selectHotelById(Long hotelId);

    public int insertHotel(MeetingHotel hotel);

    public int updateHotel(MeetingHotel hotel);

    public int deleteHotelByIds(Long[] hotelIds);
}