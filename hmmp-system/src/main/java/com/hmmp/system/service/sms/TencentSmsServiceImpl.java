package com.hmmp.system.service.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 腾讯云短信服务实现
 * 
 * @author hmmp
 */
@Service("tencentSmsService")
public class TencentSmsServiceImpl implements ISmsService
{
    private static final Logger log = LoggerFactory.getLogger(TencentSmsServiceImpl.class);

    @Override
    public SmsSendResult send(SmsTemplateRequest request)
    {
        try
        {
            // TODO: 集成腾讯云短信SDK
            // com.tencentcloudapi.sms.v20210111.SmsClient client = ...;
            // SendSmsRequest smsRequest = new SendSmsRequest();
            // smsRequest.setPhoneNumberSet(new String[]{request.getPhone()});
            // smsRequest.setSignName(request.getSignName());
            // smsRequest.setTemplateId(request.getTemplateCode());
            // smsRequest.setSmsSdkAppId(sdkAppId);
            // SendSmsResponse response = client.SendSms(smsRequest);
            // SendStatus[] statusSet = response.getSendStatusSet();
            // if (statusSet != null && statusSet.length > 0 && "Ok".equals(statusSet[0].getCode())) {
            //     return SmsSendResult.success(statusSet[0].getMessageId());
            // }
            log.info("腾讯云短信发送: phone={}, content={}", request.getPhone(), request.getContent());
            return SmsSendResult.success("TENCENT_" + System.currentTimeMillis());
        }
        catch (Exception e)
        {
            log.error("腾讯云短信发送失败", e);
            return SmsSendResult.fail("TENCENT_ERROR", e.getMessage());
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
        log.info("腾讯云批量发送完成: 成功{}条, 失败{}条", successCount, failCount);
        return SmsSendResult.success("BATCH_SUCCESS_" + successCount + "_FAIL_" + failCount);
    }

    @Override
    public String getProviderCode()
    {
        return "tencent";
    }
}