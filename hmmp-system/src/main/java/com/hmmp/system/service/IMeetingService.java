package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingMeeting;

/**
 * 会议 服务层
 *
 * @author hmmp
 */
public interface IMeetingService
{
    public List<MeetingMeeting> selectMeetingList(MeetingMeeting meeting);

    public MeetingMeeting selectMeetingById(Long meetingId);

    public int insertMeeting(MeetingMeeting meeting);

    public int updateMeeting(MeetingMeeting meeting);

    public int deleteMeetingByIds(Long[] meetingIds);
}