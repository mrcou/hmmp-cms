package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingTravelWay;
import com.hmmp.system.mapper.meeting.MeetingTravelWayMapper;
import com.hmmp.system.service.IMeetingTravelWayService;

/**
 * 旅游线路 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingTravelWayServiceImpl implements IMeetingTravelWayService
{
    @Autowired
    private MeetingTravelWayMapper travelWayMapper;

    @Override
    public List<MeetingTravelWay> selectTravelWayList(MeetingTravelWay travelWay)
    {
        return travelWayMapper.selectTravelWayList(travelWay);
    }

    @Override
    public MeetingTravelWay selectTravelWayById(Long wayId)
    {
        return travelWayMapper.selectTravelWayById(wayId);
    }

    @Override
    public int insertTravelWay(MeetingTravelWay travelWay)
    {
        return travelWayMapper.insertTravelWay(travelWay);
    }

    @Override
    public int updateTravelWay(MeetingTravelWay travelWay)
    {
        return travelWayMapper.updateTravelWay(travelWay);
    }

    @Override
    public int deleteTravelWayByIds(Long[] wayIds)
    {
        return travelWayMapper.deleteTravelWayByIds(wayIds);
    }
}