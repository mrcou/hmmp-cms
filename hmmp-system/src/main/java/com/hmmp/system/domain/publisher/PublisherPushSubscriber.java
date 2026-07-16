package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 订阅推送用户对象 publisher_push_subscriber
 * 
 * @author hmmp
 */
public class PublisherPushSubscriber extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订阅ID */
    private Long subId;

    /** 读者ID */
    private Long readerId;

    /** 推送类型 */
    private String pushType;

    /** 订阅关键词 */
    private String keywords;

    /** 订阅栏目ID列表 */
    private String columnIds;

    /** 状态 */
    private String status;

    public Long getSubId()
    {
        return subId;
    }

    public void setSubId(Long subId)
    {
        this.subId = subId;
    }

    public Long getReaderId()
    {
        return readerId;
    }

    public void setReaderId(Long readerId)
    {
        this.readerId = readerId;
    }

    public String getPushType()
    {
        return pushType;
    }

    public void setPushType(String pushType)
    {
        this.pushType = pushType;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public String getColumnIds()
    {
        return columnIds;
    }

    public void setColumnIds(String columnIds)
    {
        this.columnIds = columnIds;
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
            .append("subId", getSubId())
            .append("readerId", getReaderId())
            .append("pushType", getPushType())
            .append("keywords", getKeywords())
            .append("columnIds", getColumnIds())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}