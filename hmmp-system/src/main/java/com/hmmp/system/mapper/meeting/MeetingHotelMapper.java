package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingHotel;

/**
 * 酒店表 数据层
 *
 * @author hmmp
 */
public interface MeetingHotelMapper
{
    public List<MeetingHotel> selectHotelList(MeetingHotel hotel);

    public MeetingHotel selectHotelById(Long hotelId);

    public int insertHotel(MeetingHotel hotel);

    public int updateHotel(MeetingHotel hotel);

    public int deleteHotelById(Long hotelId);

    public int deleteHotelByIds(Long[] hotelIds);
}