package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingFees;

/**
 * 会议财务 服务层
 *
 * @author hmmp
 */
public interface IMeetingFeesService
{
    public List<MeetingFees> selectFeesList(MeetingFees fees);

    public MeetingFees selectFeesById(Long feeId);

    public int insertFees(MeetingFees fees);

    public int updateFees(MeetingFees fees);

    public int deleteFeesByIds(Long[] feeIds);
}