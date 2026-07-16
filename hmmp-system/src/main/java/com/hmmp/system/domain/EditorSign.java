package com.hmmp.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 稿件签发 editor_sign
 * 
 * @author hmmp
 */
public class EditorSign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 签发ID */
    private Long signId;

    /** 稿件ID */
    private Long manuscriptId;

    /** 期号ID */
    private Long issueId;

    /** 签发人ID */
    private Long signerId;

    /** 签发人姓名 */
    private String signerName;

    /** 签发状态 */
    private String signStatus;

    /** 签发时间 */
    private Date signTime;

    /** 签发意见 */
    private String signOpinion;

    /** 起始页码 */
    private Integer pageStart;

    /** 结束页码 */
    private Integer pageEnd;

    /** 排版文件路径 */
    private String layoutFilePath;

    public Long getSignId()
    {
        return signId;
    }

    public void setSignId(Long signId)
    {
        this.signId = signId;
    }

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
    }

    public Long getIssueId()
    {
        return issueId;
    }

    public void setIssueId(Long issueId)
    {
        this.issueId = issueId;
    }

    public Long getSignerId()
    {
        return signerId;
    }

    public void setSignerId(Long signerId)
    {
        this.signerId = signerId;
    }

    public String getSignerName()
    {
        return signerName;
    }

    public void setSignerName(String signerName)
    {
        this.signerName = signerName;
    }

    public String getSignStatus()
    {
        return signStatus;
    }

    public void setSignStatus(String signStatus)
    {
        this.signStatus = signStatus;
    }

    public Date getSignTime()
    {
        return signTime;
    }

    public void setSignTime(Date signTime)
    {
        this.signTime = signTime;
    }

    public String getSignOpinion()
    {
        return signOpinion;
    }

    public void setSignOpinion(String signOpinion)
    {
        this.signOpinion = signOpinion;
    }

    public Integer getPageStart()
    {
        return pageStart;
    }

    public void setPageStart(Integer pageStart)
    {
        this.pageStart = pageStart;
    }

    public Integer getPageEnd()
    {
        return pageEnd;
    }

    public void setPageEnd(Integer pageEnd)
    {
        this.pageEnd = pageEnd;
    }

    public String getLayoutFilePath()
    {
        return layoutFilePath;
    }

    public void setLayoutFilePath(String layoutFilePath)
    {
        this.layoutFilePath = layoutFilePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("signId", getSignId())
            .append("manuscriptId", getManuscriptId())
            .append("issueId", getIssueId())
            .append("signerId", getSignerId())
            .append("signerName", getSignerName())
            .append("signStatus", getSignStatus())
            .append("signTime", getSignTime())
            .append("signOpinion", getSignOpinion())
            .append("pageStart", getPageStart())
            .append("pageEnd", getPageEnd())
            .append("layoutFilePath", getLayoutFilePath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}