package com.hmmp.system.domain.meeting;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 票务表 meeting_tickets
 *
 * @author hmmp
 */
public class MeetingTickets extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long ticketId;
    private Long meetingId;
    private Long userId;
    private String ticketType;
    private String ticketNo;
    private BigDecimal price;
    private String status;

    public Long getTicketId()
    {
        return ticketId;
    }

    public void setTicketId(Long ticketId)
    {
        this.ticketId = ticketId;
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

    public String getTicketType()
    {
        return ticketType;
    }

    public void setTicketType(String ticketType)
    {
        this.ticketType = ticketType;
    }

    public String getTicketNo()
    {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo)
    {
        this.ticketNo = ticketNo;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MeetingTickets{" +
            "ticketId=" + ticketId +
            ", meetingId=" + meetingId +
            ", userId=" + userId +
            ", ticketType='" + ticketType + '\'' +
            ", ticketNo='" + ticketNo + '\'' +
            ", price=" + price +
            '}';
    }
}