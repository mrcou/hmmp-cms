package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingHotelBooked;
import com.hmmp.system.mapper.meeting.MeetingHotelBookedMapper;
import com.hmmp.system.service.IMeetingHotelBookedService;

/**
 * 酒店预订 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingHotelBookedServiceImpl implements IMeetingHotelBookedService
{
    @Autowired
    private MeetingHotelBookedMapper hotelBookedMapper;

    @Override
    public List<MeetingHotelBooked> selectHotelBookedList(MeetingHotelBooked booked)
    {
        return hotelBookedMapper.selectHotelBookedList(booked);
    }

    @Override
    public MeetingHotelBooked selectHotelBookedById(Long bookedId)
    {
        return hotelBookedMapper.selectHotelBookedById(bookedId);
    }

    @Override
    public int insertHotelBooked(MeetingHotelBooked booked)
    {
        return hotelBookedMapper.insertHotelBooked(booked);
    }

    @Override
    public int updateHotelBooked(MeetingHotelBooked booked)
    {
        return hotelBookedMapper.updateHotelBooked(booked);
    }

    @Override
    public int deleteHotelBookedByIds(Long[] bookedIds)
    {
        return hotelBookedMapper.deleteHotelBookedByIds(bookedIds);
    }
}