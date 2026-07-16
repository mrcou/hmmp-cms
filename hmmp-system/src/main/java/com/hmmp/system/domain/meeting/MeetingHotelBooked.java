package com.hmmp.system.domain.meeting;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 酒店预订表 meeting_hotel_booked
 *
 * @author hmmp
 */
public class MeetingHotelBooked extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long bookedId;
    private Long hotelId;
    private Long roomId;
    private Long userId;
    private Long meetingId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkOutDate;

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

    public Long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(Long hotelId)
    {
        this.hotelId = hotelId;
    }

    public Long getRoomId()
    {
        return roomId;
    }

    public void setRoomId(Long roomId)
    {
        this.roomId = roomId;
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

    public Date getCheckInDate()
    {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate()
    {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate)
    {
        this.checkOutDate = checkOutDate;
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
        return "MeetingHotelBooked{" +
            "bookedId=" + bookedId +
            ", hotelId=" + hotelId +
            ", roomId=" + roomId +
            ", userId=" + userId +
            ", meetingId=" + meetingId +
            ", totalAmount=" + totalAmount +
            '}';
    }
}