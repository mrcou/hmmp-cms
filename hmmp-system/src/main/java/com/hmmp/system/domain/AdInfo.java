package com.hmmp.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 广告信息对象 ad_info
 * 
 * @author hmmp
 */
public class AdInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 广告ID */
    private Long adId;

    /** 关联合同ID */
    private Long bargainId;

    /** 客户ID */
    private Long customerId;

    /** 广告名称 */
    private String adName;

    /** 广告类型ID */
    private Long typeId;

    /** 广告类型名称 */
    private String typeName;

    /** 广告位ID */
    private Long positionId;

    /** 广告位名称 */
    private String positionName;

    /** 广告内容 */
    private String adContent;

    /** 广告图片路径 */
    private String adImage;

    /** 广告链接 */
    private String adLink;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 广告价格 */
    private Long price;

    /** 投放刊期ID列表 */
    private String issueIds;

    /** 申请状态 */
    private String applyStatus;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核人 */
    private String auditBy;

    /** 审核意见 */
    private String auditOpinion;

    /** 制作状态 */
    private String makeStatus;

    /** 状态 */
    private String status;

    /** 浏览次数 */
    private Integer viewCount;

    /** 点击次数 */
    private Integer clickCount;

    public Long getAdId()
    {
        return adId;
    }

    public void setAdId(Long adId)
    {
        this.adId = adId;
    }

    public Long getBargainId()
    {
        return bargainId;
    }

    public void setBargainId(Long bargainId)
    {
        this.bargainId = bargainId;
    }

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public String getAdName()
    {
        return adName;
    }

    public void setAdName(String adName)
    {
        this.adName = adName;
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public Long getPositionId()
    {
        return positionId;
    }

    public void setPositionId(Long positionId)
    {
        this.positionId = positionId;
    }

    public String getPositionName()
    {
        return positionName;
    }

    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public String getAdContent()
    {
        return adContent;
    }

    public void setAdContent(String adContent)
    {
        this.adContent = adContent;
    }

    public String getAdImage()
    {
        return adImage;
    }

    public void setAdImage(String adImage)
    {
        this.adImage = adImage;
    }

    public String getAdLink()
    {
        return adLink;
    }

    public void setAdLink(String adLink)
    {
        this.adLink = adLink;
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

    public Long getPrice()
    {
        return price;
    }

    public void setPrice(Long price)
    {
        this.price = price;
    }

    public String getIssueIds()
    {
        return issueIds;
    }

    public void setIssueIds(String issueIds)
    {
        this.issueIds = issueIds;
    }

    public String getApplyStatus()
    {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus)
    {
        this.applyStatus = applyStatus;
    }

    public Date getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public String getAuditBy()
    {
        return auditBy;
    }

    public void setAuditBy(String auditBy)
    {
        this.auditBy = auditBy;
    }

    public String getAuditOpinion()
    {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion)
    {
        this.auditOpinion = auditOpinion;
    }

    public String getMakeStatus()
    {
        return makeStatus;
    }

    public void setMakeStatus(String makeStatus)
    {
        this.makeStatus = makeStatus;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(Integer viewCount)
    {
        this.viewCount = viewCount;
    }

    public Integer getClickCount()
    {
        return clickCount;
    }

    public void setClickCount(Integer clickCount)
    {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("adId", getAdId())
            .append("bargainId", getBargainId())
            .append("customerId", getCustomerId())
            .append("adName", getAdName())
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .append("positionId", getPositionId())
            .append("positionName", getPositionName())
            .append("adContent", getAdContent())
            .append("adImage", getAdImage())
            .append("adLink", getAdLink())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("price", getPrice())
            .append("issueIds", getIssueIds())
            .append("applyStatus", getApplyStatus())
            .append("auditTime", getAuditTime())
            .append("auditBy", getAuditBy())
            .append("auditOpinion", getAuditOpinion())
            .append("makeStatus", getMakeStatus())
            .append("status", getStatus())
            .append("viewCount", getViewCount())
            .append("clickCount", getClickCount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}