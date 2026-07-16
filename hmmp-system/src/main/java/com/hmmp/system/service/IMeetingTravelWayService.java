package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingTravelWay;

/**
 * 旅游线路 服务层
 *
 * @author hmmp
 */
public interface IMeetingTravelWayService
{
    public List<MeetingTravelWay> selectTravelWayList(MeetingTravelWay travelWay);

    public MeetingTravelWay selectTravelWayById(Long wayId);

    public int insertTravelWay(MeetingTravelWay travelWay);

    public int updateTravelWay(MeetingTravelWay travelWay);

    public int deleteTravelWayByIds(Long[] wayIds);
}