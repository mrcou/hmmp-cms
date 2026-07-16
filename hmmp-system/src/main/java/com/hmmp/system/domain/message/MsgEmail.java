package com.hmmp.system.domain.message;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 邮件表 msg_email
 *
 * @author hmmp
 */
public class MsgEmail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long emailId;
    private String sendFrom;
    private String sendTo;
    private String cc;
    private String bcc;
    private String subject;
    private String content;
    private String attachments;
    private String sendStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    private String failReason;
    private Integer retryCount;

    public Long getEmailId()
    {
        return emailId;
    }

    public void setEmailId(Long emailId)
    {
        this.emailId = emailId;
    }

    public String getSendFrom()
    {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom)
    {
        this.sendFrom = sendFrom;
    }

    public String getSendTo()
    {
        return sendTo;
    }

    public void setSendTo(String sendTo)
    {
        this.sendTo = sendTo;
    }

    public String getCc()
    {
        return cc;
    }

    public void setCc(String cc)
    {
        this.cc = cc;
    }

    public String getBcc()
    {
        return bcc;
    }

    public void setBcc(String bcc)
    {
        this.bcc = bcc;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getAttachments()
    {
        return attachments;
    }

    public void setAttachments(String attachments)
    {
        this.attachments = attachments;
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

    @Override
    public String toString() {
        return "MsgEmail{" +
            "emailId=" + emailId +
            ", sendFrom='" + sendFrom + '\'' +
            ", sendTo='" + sendTo + '\'' +
            ", subject='" + subject + '\'' +
            ", sendStatus='" + sendStatus + '\'' +
            '}';
    }
}