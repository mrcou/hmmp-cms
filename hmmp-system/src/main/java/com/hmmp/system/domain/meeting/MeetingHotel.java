package com.hmmp.system.domain.meeting;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 酒店表 meeting_hotel
 *
 * @author hmmp
 */
public class MeetingHotel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long hotelId;
    private Long meetingId;
    private String hotelName;
    private String address;
    private String phone;
    private Integer starLevel;
    private String description;
    private String status;

    public Long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(Long hotelId)
    {
        this.hotelId = hotelId;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Integer getStarLevel()
    {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel)
    {
        this.starLevel = starLevel;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
        return "MeetingHotel{" +
            "hotelId=" + hotelId +
            ", meetingId=" + meetingId +
            ", hotelName='" + hotelName + '\'' +
            ", address='" + address + '\'' +
            ", starLevel=" + starLevel +
            ", status='" + status + '\'' +
            '}';
    }
}