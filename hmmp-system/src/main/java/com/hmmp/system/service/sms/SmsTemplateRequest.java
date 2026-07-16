package com.hmmp.system.service.sms;

import java.util.Map;

/**
 * 短信模板发送请求
 * 
 * @author hmmp
 */
public class SmsTemplateRequest
{
    /** 手机号 */
    private String phone;

    /** 签名名称 */
    private String signName;

    /** 模板编码 */
    private String templateCode;

    /** 模板参数 */
    private Map<String, String> templateParams;

    /** 内容 */
    private String content;

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getSignName()
    {
        return signName;
    }

    public void setSignName(String signName)
    {
        this.signName = signName;
    }

    public String getTemplateCode()
    {
        return templateCode;
    }

    public void setTemplateCode(String templateCode)
    {
        this.templateCode = templateCode;
    }

    public Map<String, String> getTemplateParams()
    {
        return templateParams;
    }

    public void setTemplateParams(Map<String, String> templateParams)
    {
        this.templateParams = templateParams;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}