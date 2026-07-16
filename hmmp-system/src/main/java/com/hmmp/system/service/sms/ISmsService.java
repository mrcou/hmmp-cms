package com.hmmp.system.service.sms;

/**
 * 短信服务接口（策略模式）
 * 
 * @author hmmp
 */
public interface ISmsService
{
    /**
     * 发送短信
     */
    SmsSendResult send(SmsTemplateRequest request);

    /**
     * 批量发送短信
     */
    SmsSendResult sendBatch(SmsBatchSendRequest request);

    /**
     * 获取服务商编码
     */
    String getProviderCode();
}