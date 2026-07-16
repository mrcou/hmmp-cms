package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingTravelBooked;
import com.hmmp.system.mapper.meeting.MeetingTravelBookedMapper;
import com.hmmp.system.service.IMeetingTravelBookedService;

/**
 * 旅游线路预订 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingTravelBookedServiceImpl implements IMeetingTravelBookedService
{
    @Autowired
    private MeetingTravelBookedMapper travelBookedMapper;

    @Override
    public List<MeetingTravelBooked> selectTravelBookedList(MeetingTravelBooked booked)
    {
        return travelBookedMapper.selectTravelBookedList(booked);
    }

    @Override
    public MeetingTravelBooked selectTravelBookedById(Long bookedId)
    {
        return travelBookedMapper.selectTravelBookedById(bookedId);
    }

    @Override
    public int insertTravelBooked(MeetingTravelBooked booked)
    {
        return travelBookedMapper.insertTravelBooked(booked);
    }

    @Override
    public int updateTravelBooked(MeetingTravelBooked booked)
    {
        return travelBookedMapper.updateTravelBooked(booked);
    }

    @Override
    public int deleteTravelBookedByIds(Long[] bookedIds)
    {
        return travelBookedMapper.deleteTravelBookedByIds(bookedIds);
    }
}