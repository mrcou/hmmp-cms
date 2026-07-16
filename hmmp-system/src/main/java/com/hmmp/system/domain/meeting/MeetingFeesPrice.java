package com.hmmp.system.domain.meeting;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 注册费价格表 meeting_fees_price
 *
 * @author hmmp
 */
public class MeetingFeesPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long priceId;
    private Long meetingId;
    private Long groupId;
    private String feeType;
    private BigDecimal price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validEndDate;

    public Long getPriceId()
    {
        return priceId;
    }

    public void setPriceId(Long priceId)
    {
        this.priceId = priceId;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public String getFeeType()
    {
        return feeType;
    }

    public void setFeeType(String feeType)
    {
        this.feeType = feeType;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public Date getValidStartDate()
    {
        return validStartDate;
    }

    public void setValidStartDate(Date validStartDate)
    {
        this.validStartDate = validStartDate;
    }

    public Date getValidEndDate()
    {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate)
    {
        this.validEndDate = validEndDate;
    }

    @Override
    public String toString() {
        return "MeetingFeesPrice{" +
            "priceId=" + priceId +
            ", meetingId=" + meetingId +
            ", groupId=" + groupId +
            ", feeType='" + feeType + '\'' +
            ", price=" + price +
            '}';
    }
}