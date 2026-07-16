package com.hmmp.system.domain.meeting;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 旅游线路表 meeting_travel_way
 *
 * @author hmmp
 */
public class MeetingTravelWay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long wayId;
    private Long meetingId;
    private String wayName;
    private String description;
    private BigDecimal price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Integer maxPeople;
    private String status;

    public Long getWayId()
    {
        return wayId;
    }

    public void setWayId(Long wayId)
    {
        this.wayId = wayId;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public String getWayName()
    {
        return wayName;
    }

    public void setWayName(String wayName)
    {
        this.wayName = wayName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Integer getMaxPeople()
    {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople)
    {
        this.maxPeople = maxPeople;
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
        return "MeetingTravelWay{" +
            "wayId=" + wayId +
            ", meetingId=" + meetingId +
            ", wayName='" + wayName + '\'' +
            ", price=" + price +
            ", status='" + status + '\'' +
            '}';
    }
}