package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingMeeting;

/**
 * 会议表 数据层
 *
 * @author hmmp
 */
public interface MeetingMeetingMapper
{
    public List<MeetingMeeting> selectMeetingList(MeetingMeeting meeting);

    public MeetingMeeting selectMeetingById(Long meetingId);

    public int insertMeeting(MeetingMeeting meeting);

    public int updateMeeting(MeetingMeeting meeting);

    public int deleteMeetingById(Long meetingId);

    public int deleteMeetingByIds(Long[] meetingIds);
}