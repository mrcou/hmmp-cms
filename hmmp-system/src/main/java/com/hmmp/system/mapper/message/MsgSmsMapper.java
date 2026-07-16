package com.hmmp.system.mapper.message;

import java.util.List;
import com.hmmp.system.domain.message.MsgSms;

/**
 * 短信表 数据层
 *
 * @author hmmp
 */
public interface MsgSmsMapper
{
    public List<MsgSms> selectSmsList(MsgSms sms);

    public MsgSms selectSmsById(Long smsId);

    public int insertSms(MsgSms sms);

    public int updateSms(MsgSms sms);

    public int deleteSmsById(Long smsId);

    public int deleteSmsByIds(Long[] smsIds);
}