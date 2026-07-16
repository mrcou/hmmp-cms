package com.hmmp.system.domain.meeting;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 旅游线路预订表 meeting_travel_booked
 *
 * @author hmmp
 */
public class MeetingTravelBooked extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long bookedId;
    private Long wayId;
    private Long userId;
    private Long meetingId;
    private Integer qty;
    private BigDecimal totalAmount;
    private String status;

    public Long getBookedId()
    {
        return bookedId;
    }

    public void setBookedId(Long bookedId)
    {
        this.bookedId = bookedId;
    }

    public Long getWayId()
    {
        return wayId;
    }

    public void setWayId(Long wayId)
    {
        this.wayId = wayId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public Integer getQty()
    {
        return qty;
    }

    public void setQty(Integer qty)
    {
        this.qty = qty;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
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
        return "MeetingTravelBooked{" +
            "bookedId=" + bookedId +
            ", wayId=" + wayId +
            ", userId=" + userId +
            ", meetingId=" + meetingId +
            ", qty=" + qty +
            ", totalAmount=" + totalAmount +
            '}';
    }
}