package com.hmmp.system.service.sms;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信服务工厂
 * 
 * @author hmmp
 */
@Component
public class SmsServiceFactory
{
    @Autowired(required = false)
    private Map<String, ISmsService> smsServiceMap;

    /** 默认服务商 */
    private String defaultProvider = "aliyun";

    public ISmsService getSmsService()
    {
        return getSmsService(defaultProvider);
    }

    public ISmsService getSmsService(String providerCode)
    {
        if (smsServiceMap == null || smsServiceMap.isEmpty())
        {
            throw new RuntimeException("未找到可用的短信服务商");
        }
        ISmsService service = smsServiceMap.get(providerCode + "SmsService");
        if (service == null)
        {
            throw new RuntimeException("不支持的短信服务商: " + providerCode);
        }
        return service;
    }

    public void setDefaultProvider(String providerCode)
    {
        this.defaultProvider = providerCode;
    }
}