package com.hmmp.system.domain.meeting;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 参会用户类型表 meeting_user_group
 *
 * @author hmmp
 */
public class MeetingUserGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long groupId;
    private Long meetingId;
    private String groupName;
    private String groupCode;
    private String description;
    private Integer sortOrder;

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupCode()
    {
        return groupCode;
    }

    public void setGroupCode(String groupCode)
    {
        this.groupCode = groupCode;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "MeetingUserGroup{" +
            "groupId=" + groupId +
            ", meetingId=" + meetingId +
            ", groupName='" + groupName + '\'' +
            ", groupCode='" + groupCode + '\'' +
            ", sortOrder=" + sortOrder +
            '}';
    }
}