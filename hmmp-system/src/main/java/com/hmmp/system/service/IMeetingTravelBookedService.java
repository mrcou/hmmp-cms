package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingTravelBooked;

/**
 * 旅游线路预订 服务层
 *
 * @author hmmp
 */
public interface IMeetingTravelBookedService
{
    public List<MeetingTravelBooked> selectTravelBookedList(MeetingTravelBooked booked);

    public MeetingTravelBooked selectTravelBookedById(Long bookedId);

    public int insertTravelBooked(MeetingTravelBooked booked);

    public int updateTravelBooked(MeetingTravelBooked booked);

    public int deleteTravelBookedByIds(Long[] bookedIds);
}