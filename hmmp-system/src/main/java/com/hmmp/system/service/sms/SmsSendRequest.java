package com.hmmp.system.service.sms;

import java.util.Map;

/**
 * 短信发送请求
 * 
 * @author hmmp
 */
public class SmsSendRequest
{
    /** 手机号 */
    private String phone;

    /** 短信内容 */
    private String content;

    /** 签名名称 */
    private String signName;

    /** 模板编码 */
    private String templateCode;

    /** 模板参数 */
    private Map<String, String> templateParams;

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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
}