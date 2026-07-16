package com.hmmp.system.domain.meeting;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 会议财务表 meeting_fees
 *
 * @author hmmp
 */
public class MeetingFees extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long feeId;
    private Long meetingId;
    private Long userId;
    private String feeType;
    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    private String remark;

    public Long getFeeId()
    {
        return feeId;
    }

    public void setFeeId(Long feeId)
    {
        this.feeId = feeId;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getFeeType()
    {
        return feeType;
    }

    public void setFeeType(String feeType)
    {
        this.feeType = feeType;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentTime()
    {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }

    @Override
    public String getRemark()
    {
        return remark;
    }

    @Override
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MeetingFees{" +
            "feeId=" + feeId +
            ", meetingId=" + meetingId +
            ", userId=" + userId +
            ", feeType='" + feeType + '\'' +
            ", amount=" + amount +
            ", paymentStatus='" + paymentStatus + '\'' +
            '}';
    }
}