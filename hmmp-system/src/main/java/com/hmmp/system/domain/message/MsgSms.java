package com.hmmp.system.domain.message;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 短信表 msg_sms
 *
 * @author hmmp
 */
public class MsgSms extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long smsId;
    private String phone;
    private String content;
    private String sendStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    private String failReason;
    private Integer retryCount;
    private String sendBy;
    private String smsProvider;

    public Long getSmsId()
    {
        return smsId;
    }

    public void setSmsId(Long smsId)
    {
        this.smsId = smsId;
    }

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

    public String getSendStatus()
    {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus)
    {
        this.sendStatus = sendStatus;
    }

    public Date getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }

    public String getFailReason()
    {
        return failReason;
    }

    public void setFailReason(String failReason)
    {
        this.failReason = failReason;
    }

    public Integer getRetryCount()
    {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount)
    {
        this.retryCount = retryCount;
    }

    public String getSendBy()
    {
        return sendBy;
    }

    public void setSendBy(String sendBy)
    {
        this.sendBy = sendBy;
    }

    public String getSmsProvider()
    {
        return smsProvider;
    }

    public void setSmsProvider(String smsProvider)
    {
        this.smsProvider = smsProvider;
    }

    @Override
    public String toString() {
        return "MsgSms{" +
            "smsId=" + smsId +
            ", phone='" + phone + '\'' +
            ", sendStatus='" + sendStatus + '\'' +
            '}';
    }
}