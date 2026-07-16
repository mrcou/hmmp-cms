package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingFees;
import com.hmmp.system.mapper.meeting.MeetingFeesMapper;
import com.hmmp.system.service.IMeetingFeesService;

/**
 * 会议财务 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingFeesServiceImpl implements IMeetingFeesService
{
    @Autowired
    private MeetingFeesMapper feesMapper;

    @Override
    public List<MeetingFees> selectFeesList(MeetingFees fees)
    {
        return feesMapper.selectFeesList(fees);
    }

    @Override
    public MeetingFees selectFeesById(Long feeId)
    {
        return feesMapper.selectFeesById(feeId);
    }

    @Override
    public int insertFees(MeetingFees fees)
    {
        return feesMapper.insertFees(fees);
    }

    @Override
    public int updateFees(MeetingFees fees)
    {
        return feesMapper.updateFees(fees);
    }

    @Override
    public int deleteFeesByIds(Long[] feeIds)
    {
        return feesMapper.deleteFeesByIds(feeIds);
    }
}