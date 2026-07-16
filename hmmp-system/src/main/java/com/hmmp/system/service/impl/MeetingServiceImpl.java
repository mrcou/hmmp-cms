package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingMeeting;
import com.hmmp.system.mapper.meeting.MeetingMeetingMapper;
import com.hmmp.system.service.IMeetingService;

/**
 * 会议 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingServiceImpl implements IMeetingService
{
    @Autowired
    private MeetingMeetingMapper meetingMapper;

    @Override
    public List<MeetingMeeting> selectMeetingList(MeetingMeeting meeting)
    {
        return meetingMapper.selectMeetingList(meeting);
    }

    @Override
    public MeetingMeeting selectMeetingById(Long meetingId)
    {
        return meetingMapper.selectMeetingById(meetingId);
    }

    @Override
    public int insertMeeting(MeetingMeeting meeting)
    {
        return meetingMapper.insertMeeting(meeting);
    }

    @Override
    public int updateMeeting(MeetingMeeting meeting)
    {
        return meetingMapper.updateMeeting(meeting);
    }

    @Override
    public int deleteMeetingByIds(Long[] meetingIds)
    {
        return meetingMapper.deleteMeetingByIds(meetingIds);
    }
}