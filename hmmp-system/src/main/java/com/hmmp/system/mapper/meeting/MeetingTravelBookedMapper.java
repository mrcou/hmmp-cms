package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingTravelBooked;

/**
 * 旅游线路预订表 数据层
 *
 * @author hmmp
 */
public interface MeetingTravelBookedMapper
{
    public List<MeetingTravelBooked> selectTravelBookedList(MeetingTravelBooked booked);

    public MeetingTravelBooked selectTravelBookedById(Long bookedId);

    public int insertTravelBooked(MeetingTravelBooked booked);

    public int updateTravelBooked(MeetingTravelBooked booked);

    public int deleteTravelBookedById(Long bookedId);

    public int deleteTravelBookedByIds(Long[] bookedIds);
}