package com.hmmp.system.domain;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 审稿人申请 editor_reviewer_apply
 * 
 * @author hmmp
 */
public class EditorReviewerApply implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 申请ID */
    private Long applyId;

    /** 用户ID */
    private Long userId;

    /** 申请类型 */
    private String applyType;

    /** 推荐人ID */
    private Long recommenderId;

    /** 推荐人姓名 */
    private String recommenderName;

    /** 真实姓名 */
    private String realName;

    /** 所属单位 */
    private String affiliation;

    /** 职称 */
    private String title;

    /** 研究方向 */
    private String researchDirection;

    /** 专长领域 */
    private String expertiseFields;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 申请理由 */
    private String applyReason;

    /** 审核状态 */
    private String auditStatus;

    /** 审核意见 */
    private String auditOpinion;

    /** 审核时间 */
    private Date auditTime;

    /** 审核人 */
    private String auditBy;

    /** 是否删除 */
    private String isDeleted;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public Long getApplyId()
    {
        return applyId;
    }

    public void setApplyId(Long applyId)
    {
        this.applyId = applyId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getApplyType()
    {
        return applyType;
    }

    public void setApplyType(String applyType)
    {
        this.applyType = applyType;
    }

    public Long getRecommenderId()
    {
        return recommenderId;
    }

    public void setRecommenderId(Long recommenderId)
    {
        this.recommenderId = recommenderId;
    }

    public String getRecommenderName()
    {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName)
    {
        this.recommenderName = recommenderName;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getAffiliation()
    {
        return affiliation;
    }

    public void setAffiliation(String affiliation)
    {
        this.affiliation = affiliation;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getResearchDirection()
    {
        return researchDirection;
    }

    public void setResearchDirection(String researchDirection)
    {
        this.researchDirection = researchDirection;
    }

    public String getExpertiseFields()
    {
        return expertiseFields;
    }

    public void setExpertiseFields(String expertiseFields)
    {
        this.expertiseFields = expertiseFields;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getApplyReason()
    {
        return applyReason;
    }

    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }

    public String getAuditStatus()
    {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus)
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditOpinion()
    {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion)
    {
        this.auditOpinion = auditOpinion;
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

    public String getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("applyId", getApplyId())
            .append("userId", getUserId())
            .append("applyType", getApplyType())
            .append("recommenderId", getRecommenderId())
            .append("recommenderName", getRecommenderName())
            .append("realName", getRealName())
            .append("affiliation", getAffiliation())
            .append("title", getTitle())
            .append("researchDirection", getResearchDirection())
            .append("expertiseFields", getExpertiseFields())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("applyReason", getApplyReason())
            .append("auditStatus", getAuditStatus())
            .append("auditOpinion", getAuditOpinion())
            .append("auditTime", getAuditTime())
            .append("auditBy", getAuditBy())
            .append("isDeleted", getIsDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}