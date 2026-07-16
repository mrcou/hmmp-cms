package com.hmmp.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 组稿实体 editor_group
 * 
 * @author hmmp
 */
public class EditorGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 组稿ID */
    private Long groupId;

    /** 组稿名称 */
    private String groupName;

    /** 期号ID */
    private Long issueId;

    /** 组稿类型 */
    private String groupType;

    /** 状态 */
    private String status;

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public Long getIssueId()
    {
        return issueId;
    }

    public void setIssueId(Long issueId)
    {
        this.issueId = issueId;
    }

    public String getGroupType()
    {
        return groupType;
    }

    public void setGroupType(String groupType)
    {
        this.groupType = groupType;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("groupName", getGroupName())
            .append("issueId", getIssueId())
            .append("groupType", getGroupType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}