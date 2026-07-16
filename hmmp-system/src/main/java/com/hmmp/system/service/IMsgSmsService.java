package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.message.MsgSms;

/**
 * 短信 服务层
 *
 * @author hmmp
 */
public interface IMsgSmsService
{
    public List<MsgSms> selectSmsList(MsgSms sms);

    public MsgSms selectSmsById(Long smsId);

    public int insertSms(MsgSms sms);

    public int updateSms(MsgSms sms);

    public int deleteSmsByIds(Long[] smsIds);
}