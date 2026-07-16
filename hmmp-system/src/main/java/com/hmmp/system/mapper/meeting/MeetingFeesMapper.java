package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingFees;

/**
 * 会议财务表 数据层
 *
 * @author hmmp
 */
public interface MeetingFeesMapper
{
    public List<MeetingFees> selectFeesList(MeetingFees fees);

    public MeetingFees selectFeesById(Long feeId);

    public int insertFees(MeetingFees fees);

    public int updateFees(MeetingFees fees);

    public int deleteFeesById(Long feeId);

    public int deleteFeesByIds(Long[] feeIds);
}