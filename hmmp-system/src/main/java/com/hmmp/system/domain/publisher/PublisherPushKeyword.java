package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 关键词推送记录对象 publisher_push_keyword
 * 
 * @author hmmp
 */
public class PublisherPushKeyword extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 推送ID */
    private Long pushId;

    /** 关键词 */
    private String keyword;

    /** 推送内容 */
    private String pushContent;

    /** 推送时间 */
    private Date pushTime;

    /** 推送状态 */
    private String pushStatus;

    /** 推送数量 */
    private Integer pushCount;

    /** 成功数量 */
    private Integer successCount;

    /** 失败数量 */
    private Integer failCount;

    public Long getPushId()
    {
        return pushId;
    }

    public void setPushId(Long pushId)
    {
        this.pushId = pushId;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getPushContent()
    {
        return pushContent;
    }

    public void setPushContent(String pushContent)
    {
        this.pushContent = pushContent;
    }

    public Date getPushTime()
    {
        return pushTime;
    }

    public void setPushTime(Date pushTime)
    {
        this.pushTime = pushTime;
    }

    public String getPushStatus()
    {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus)
    {
        this.pushStatus = pushStatus;
    }

    public Integer getPushCount()
    {
        return pushCount;
    }

    public void setPushCount(Integer pushCount)
    {
        this.pushCount = pushCount;
    }

    public Integer getSuccessCount()
    {
        return successCount;
    }

    public void setSuccessCount(Integer successCount)
    {
        this.successCount = successCount;
    }

    public Integer getFailCount()
    {
        return failCount;
    }

    public void setFailCount(Integer failCount)
    {
        this.failCount = failCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("pushId", getPushId())
            .append("keyword", getKeyword())
            .append("pushContent", getPushContent())
            .append("pushTime", getPushTime())
            .append("pushStatus", getPushStatus())
            .append("pushCount", getPushCount())
            .append("successCount", getSuccessCount())
            .append("failCount", getFailCount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}