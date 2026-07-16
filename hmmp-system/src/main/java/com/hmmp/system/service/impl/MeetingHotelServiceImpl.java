package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingHotel;
import com.hmmp.system.mapper.meeting.MeetingHotelMapper;
import com.hmmp.system.service.IMeetingHotelService;

/**
 * 酒店 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingHotelServiceImpl implements IMeetingHotelService
{
    @Autowired
    private MeetingHotelMapper hotelMapper;

    @Override
    public List<MeetingHotel> selectHotelList(MeetingHotel hotel)
    {
        return hotelMapper.selectHotelList(hotel);
    }

    @Override
    public MeetingHotel selectHotelById(Long hotelId)
    {
        return hotelMapper.selectHotelById(hotelId);
    }

    @Override
    public int insertHotel(MeetingHotel hotel)
    {
        return hotelMapper.insertHotel(hotel);
    }

    @Override
    public int updateHotel(MeetingHotel hotel)
    {
        return hotelMapper.updateHotel(hotel);
    }

    @Override
    public int deleteHotelByIds(Long[] hotelIds)
    {
        return hotelMapper.deleteHotelByIds(hotelIds);
    }
}