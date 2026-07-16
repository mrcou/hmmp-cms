package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.message.MsgSms;
import com.hmmp.system.mapper.message.MsgSmsMapper;
import com.hmmp.system.service.IMsgSmsService;

/**
 * 短信 服务层处理
 *
 * @author hmmp
 */
@Service
public class MsgSmsServiceImpl implements IMsgSmsService
{
    @Autowired
    private MsgSmsMapper smsMapper;

    @Override
    public List<MsgSms> selectSmsList(MsgSms sms)
    {
        return smsMapper.selectSmsList(sms);
    }

    @Override
    public MsgSms selectSmsById(Long smsId)
    {
        return smsMapper.selectSmsById(smsId);
    }

    @Override
    public int insertSms(MsgSms sms)
    {
        return smsMapper.insertSms(sms);
    }

    @Override
    public int updateSms(MsgSms sms)
    {
        return smsMapper.updateSms(sms);
    }

    @Override
    public int deleteSmsByIds(Long[] smsIds)
    {
        return smsMapper.deleteSmsByIds(smsIds);
    }
}