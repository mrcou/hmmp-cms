package com.hmmp.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 审稿记录实体 editor_audit
 * 
 * @author hmmp
 */
public class EditorAudit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审稿ID */
    private Long auditId;

    /** 稿件ID */
    private Long manuscriptId;

    /** 审稿人ID */
    private Long reviewerId;

    /** 审稿人姓名 */
    private String reviewerName;

    /** 审稿轮次 */
    private Integer reviewRound;

    /** 审稿状态 */
    private String auditStatus;

    /** 审稿意见 */
    private String auditOpinion;

    /** 审稿评分 */
    private BigDecimal auditScore;

    /** 审稿结果 */
    private String auditResult;

    /** 邀请时间 */
    private Date inviteTime;

    /** 截止时间 */
    private Date deadlineTime;

    /** 审稿时间 */
    private Date auditTime;

    /** 撤审时间 */
    private Date withdrawTime;

    /** 撤审原因 */
    private String withdrawReason;

    /** 同意撤审时间 */
    private Date acceptWithdrawTime;

    /** 邀请信息 */
    private String inviteMessage;

    /** 是否删除 */
    private String isDeleted;

    public Long getAuditId()
    {
        return auditId;
    }

    public void setAuditId(Long auditId)
    {
        this.auditId = auditId;
    }

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
    }

    public Long getReviewerId()
    {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId)
    {
        this.reviewerId = reviewerId;
    }

    public String getReviewerName()
    {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }

    public Integer getReviewRound()
    {
        return reviewRound;
    }

    public void setReviewRound(Integer reviewRound)
    {
        this.reviewRound = reviewRound;
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

    public BigDecimal getAuditScore()
    {
        return auditScore;
    }

    public void setAuditScore(BigDecimal auditScore)
    {
        this.auditScore = auditScore;
    }

    public String getAuditResult()
    {
        return auditResult;
    }

    public void setAuditResult(String auditResult)
    {
        this.auditResult = auditResult;
    }

    public Date getInviteTime()
    {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime)
    {
        this.inviteTime = inviteTime;
    }

    public Date getDeadlineTime()
    {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime)
    {
        this.deadlineTime = deadlineTime;
    }

    public Date getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public Date getWithdrawTime()
    {
        return withdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime)
    {
        this.withdrawTime = withdrawTime;
    }

    public String getWithdrawReason()
    {
        return withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason)
    {
        this.withdrawReason = withdrawReason;
    }

    public Date getAcceptWithdrawTime()
    {
        return acceptWithdrawTime;
    }

    public void setAcceptWithdrawTime(Date acceptWithdrawTime)
    {
        this.acceptWithdrawTime = acceptWithdrawTime;
    }

    public String getInviteMessage()
    {
        return inviteMessage;
    }

    public void setInviteMessage(String inviteMessage)
    {
        this.inviteMessage = inviteMessage;
    }

    public String getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("auditId", getAuditId())
            .append("manuscriptId", getManuscriptId())
            .append("reviewerId", getReviewerId())
            .append("reviewerName", getReviewerName())
            .append("reviewRound", getReviewRound())
            .append("auditStatus", getAuditStatus())
            .append("auditOpinion", getAuditOpinion())
            .append("auditScore", getAuditScore())
            .append("auditResult", getAuditResult())
            .append("inviteTime", getInviteTime())
            .append("deadlineTime", getDeadlineTime())
            .append("auditTime", getAuditTime())
            .append("withdrawTime", getWithdrawTime())
            .append("withdrawReason", getWithdrawReason())
            .append("acceptWithdrawTime", getAcceptWithdrawTime())
            .append("inviteMessage", getInviteMessage())
            .append("isDeleted", getIsDeleted())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}