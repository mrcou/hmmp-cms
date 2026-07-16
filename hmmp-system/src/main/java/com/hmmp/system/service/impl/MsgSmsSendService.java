package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.system.domain.message.MsgSms;
import com.hmmp.system.mapper.message.MsgSmsMapper;
import com.hmmp.system.service.sms.ISmsService;
import com.hmmp.system.service.sms.SmsSendResult;
import com.hmmp.system.service.sms.SmsServiceFactory;
import com.hmmp.system.service.sms.SmsTemplateRequest;

/**
 * 短信发送业务服务
 * 
 * @author hmmp
 */
@Service
public class MsgSmsSendService
{
    private static final Logger log = LoggerFactory.getLogger(MsgSmsSendService.class);

    @Autowired
    private SmsServiceFactory smsServiceFactory;

    @Autowired
    private MsgSmsMapper msgSmsMapper;

    /**
     * 发送单条短信
     */
    public AjaxResult sendSms(MsgSms sms)
    {
        ISmsService smsService = smsServiceFactory.getSmsService();

        SmsTemplateRequest request = new SmsTemplateRequest();
        request.setPhone(sms.getPhone());
        request.setContent(sms.getContent());

        SmsSendResult result = smsService.send(request);

        sms.setSendStatus(result.isSuccess() ? "1" : "2");
        sms.setSendTime(new Date());
        if (!result.isSuccess())
        {
            sms.setFailReason(result.getErrorMessage());
        }

        msgSmsMapper.insertSms(sms);

        if (result.isSuccess())
        {
            return AjaxResult.success("短信发送成功");
        }
        else
        {
            return AjaxResult.error("短信发送失败: " + result.getErrorMessage());
        }
    }

    /**
     * 发送短信（指定服务商）
     */
    public AjaxResult sendSms(MsgSms sms, String providerCode)
    {
        ISmsService smsService = smsServiceFactory.getSmsService(providerCode);

        SmsTemplateRequest request = new SmsTemplateRequest();
        request.setPhone(sms.getPhone());
        request.setContent(sms.getContent());

        SmsSendResult result = smsService.send(request);

        sms.setSendStatus(result.isSuccess() ? "1" : "2");
        sms.setSendTime(new Date());
        sms.setSmsProvider(providerCode);
        if (!result.isSuccess())
        {
            sms.setFailReason(result.getErrorMessage());
        }

        msgSmsMapper.insertSms(sms);

        if (result.isSuccess())
        {
            return AjaxResult.success("短信发送成功");
        }
        else
        {
            return AjaxResult.error("短信发送失败: " + result.getErrorMessage());
        }
    }

    /**
     * 批量发送短信
     */
    public void sendBatch(List<MsgSms> smsList)
    {
        for (MsgSms sms : smsList)
        {
            try
            {
                sendSms(sms);
            }
            catch (Exception e)
            {
                log.error("批量发送短信失败: phone={}", sms.getPhone(), e);
                sms.setSendStatus("2");
                sms.setFailReason(e.getMessage());
                sms.setSendTime(new Date());
                msgSmsMapper.insertSms(sms);
            }
        }
    }
}