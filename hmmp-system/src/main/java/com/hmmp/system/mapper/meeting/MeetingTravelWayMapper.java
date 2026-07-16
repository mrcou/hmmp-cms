package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingTravelWay;

/**
 * 旅游线路表 数据层
 *
 * @author hmmp
 */
public interface MeetingTravelWayMapper
{
    public List<MeetingTravelWay> selectTravelWayList(MeetingTravelWay travelWay);

    public MeetingTravelWay selectTravelWayById(Long wayId);

    public int insertTravelWay(MeetingTravelWay travelWay);

    public int updateTravelWay(MeetingTravelWay travelWay);

    public int deleteTravelWayById(Long wayId);

    public int deleteTravelWayByIds(Long[] wayIds);
}