package com.hmmp.system.service.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务实现
 * 
 * @author hmmp
 */
@Service("aliyunSmsService")
public class AliyunSmsServiceImpl implements ISmsService
{
    private static final Logger log = LoggerFactory.getLogger(AliyunSmsServiceImpl.class);

    @Override
    public SmsSendResult send(SmsTemplateRequest request)
    {
        try
        {
            // TODO: 集成阿里云短信SDK
            // com.aliyun.dysmsapi20170525.Client client = ...;
            // SendSmsRequest smsRequest = new SendSmsRequest()
            //     .setPhoneNumbers(request.getPhone())
            //     .setSignName(request.getSignName())
            //     .setTemplateCode(request.getTemplateCode())
            //     .setTemplateParam(JSON.toJSONString(request.getTemplateParams()));
            // SendSmsResponse response = client.sendSms(smsRequest);
            // if ("OK".equals(response.body.code)) {
            //     return SmsSendResult.success(response.body.bizId);
            // }
            log.info("阿里云短信发送: phone={}, content={}", request.getPhone(), request.getContent());
            return SmsSendResult.success("ALIYUN_" + System.currentTimeMillis());
        }
        catch (Exception e)
        {
            log.error("阿里云短信发送失败", e);
            return SmsSendResult.fail("ALIYUN_ERROR", e.getMessage());
        }
    }

    @Override
    public SmsSendResult sendBatch(SmsBatchSendRequest request)
    {
        int successCount = 0;
        int failCount = 0;
        for (String phone : request.getPhones())
        {
            SmsTemplateRequest singleRequest = new SmsTemplateRequest();
            singleRequest.setPhone(phone);
            singleRequest.setSignName(request.getSignName());
            singleRequest.setTemplateCode(request.getTemplateCode());
            singleRequest.setTemplateParams(request.getTemplateParams());
            singleRequest.setContent(request.getContent());
            SmsSendResult result = send(singleRequest);
            if (result.isSuccess())
            {
                successCount++;
            }
            else
            {
                failCount++;
            }
        }
        log.info("阿里云批量发送完成: 成功{}条, 失败{}条", successCount, failCount);
        return SmsSendResult.success("BATCH_SUCCESS_" + successCount + "_FAIL_" + failCount);
    }

    @Override
    public String getProviderCode()
    {
        return "aliyun";
    }
}