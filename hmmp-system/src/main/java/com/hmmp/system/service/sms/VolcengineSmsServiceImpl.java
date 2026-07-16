package com.hmmp.system.service.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 火山引擎短信服务实现
 * 
 * @author hmmp
 */
@Service("volcengineSmsService")
public class VolcengineSmsServiceImpl implements ISmsService
{
    private static final Logger log = LoggerFactory.getLogger(VolcengineSmsServiceImpl.class);

    @Override
    public SmsSendResult send(SmsTemplateRequest request)
    {
        try
        {
            // TODO: 集成火山引擎短信SDK
            // com.volcengine.sms.SmsService client = volcengineSmsClient();
            // Map<String, String> params = new HashMap<>();
            // params.put("PhoneNumber", request.getPhone());
            // params.put("SignName", request.getSignName());
            // params.put("TemplateCode", request.getTemplateCode());
            // params.put("TemplateParam", JSON.toJSONString(request.getTemplateParams()));
            // com.volcengine.sms.model.SendSmsResponse response = client.sendSms(params);
            // if ("OK".equals(response.getCode())) {
            //     return SmsSendResult.success(response.getMessageId());
            // }
            log.info("火山引擎短信发送: phone={}, content={}", request.getPhone(), request.getContent());
            return SmsSendResult.success("VOLCENGINE_" + System.currentTimeMillis());
        }
        catch (Exception e)
        {
            log.error("火山引擎短信发送失败", e);
            return SmsSendResult.fail("VOLCENGINE_ERROR", e.getMessage());
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
        log.info("火山引擎批量发送完成: 成功{}条, 失败{}条", successCount, failCount);
        return SmsSendResult.success("BATCH_SUCCESS_" + successCount + "_FAIL_" + failCount);
    }

    @Override
    public String getProviderCode()
    {
        return "volcengine";
    }
}