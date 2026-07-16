package com.hmmp.system.service.sms;

import java.util.List;
import java.util.Map;

/**
 * 短信批量发送请求
 * 
 * @author hmmp
 */
public class SmsBatchSendRequest
{
    /** 手机号列表 */
    private List<String> phones;

    /** 短信内容 */
    private String content;

    /** 签名名称 */
    private String signName;

    /** 模板编码 */
    private String templateCode;

    /** 模板参数 */
    private Map<String, String> templateParams;

    public List<String> getPhones()
    {
        return phones;
    }

    public void setPhones(List<String> phones)
    {
        this.phones = phones;
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