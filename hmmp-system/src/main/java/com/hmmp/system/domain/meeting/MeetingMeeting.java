package com.hmmp.system.domain.meeting;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 会议表 meeting_meeting
 *
 * @author hmmp
 */
public class MeetingMeeting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long meetingId;
    private String meetingName;
    private String meetingCode;
    private String meetingType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String location;
    private String host;
    private String contactPerson;
    private String contactPhone;
    private String description;
    private String status;
    private Integer maxParticipants;
    private Integer actualParticipants;

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public String getMeetingName()
    {
        return meetingName;
    }

    public void setMeetingName(String meetingName)
    {
        this.meetingName = meetingName;
    }

    public String getMeetingCode()
    {
        return meetingCode;
    }

    public void setMeetingCode(String meetingCode)
    {
        this.meetingCode = meetingCode;
    }

    public String getMeetingType()
    {
        return meetingType;
    }

    public void setMeetingType(String meetingType)
    {
        this.meetingType = meetingType;
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

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getContactPerson()
    {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson)
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
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

    public Integer getMaxParticipants()
    {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants)
    {
        this.maxParticipants = maxParticipants;
    }

    public Integer getActualParticipants()
    {
        return actualParticipants;
    }

    public void setActualParticipants(Integer actualParticipants)
    {
        this.actualParticipants = actualParticipants;
    }

    @Override
    public String toString() {
        return "MeetingMeeting{" +
            "meetingId=" + meetingId +
            ", meetingName='" + meetingName + '\'' +
            ", meetingCode='" + meetingCode + '\'' +
            ", meetingType='" + meetingType + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", location='" + location + '\'' +
            ", host='" + host + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}