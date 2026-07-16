package com.hmmp.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 审稿任务实体 reviewer_task
 * 
 * @author hmmp
 */
public class ReviewerTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long taskId;

    /** 关联审稿记录ID */
    private Long auditId;

    /** 稿件ID */
    private Long manuscriptId;

    /** 审稿人ID */
    private Long reviewerId;

    /** 稿件标题 */
    private String title;

    /** 稿件摘要 */
    private String abstractText;

    /** 任务状态(0待接受 1已接受 2审稿中 3已完成 4已拒绝) */
    private String taskStatus;

    /** 接受时间 */
    private Date acceptTime;

    /** 拒绝理由 */
    private String rejectReason;

    /** 截止时间 */
    private Date deadlineTime;

    /** 提交时间 */
    private Date submitTime;

    public Long getTaskId()
    {
        return taskId;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAbstractText()
    {
        return abstractText;
    }

    public void setAbstractText(String abstractText)
    {
        this.abstractText = abstractText;
    }

    public String getTaskStatus()
    {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public Date getAcceptTime()
    {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime)
    {
        this.acceptTime = acceptTime;
    }

    public String getRejectReason()
    {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason)
    {
        this.rejectReason = rejectReason;
    }

    public Date getDeadlineTime()
    {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime)
    {
        this.deadlineTime = deadlineTime;
    }

    public Date getSubmitTime()
    {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("auditId", getAuditId())
            .append("manuscriptId", getManuscriptId())
            .append("reviewerId", getReviewerId())
            .append("title", getTitle())
            .append("abstractText", getAbstractText())
            .append("taskStatus", getTaskStatus())
            .append("acceptTime", getAcceptTime())
            .append("rejectReason", getRejectReason())
            .append("deadlineTime", getDeadlineTime())
            .append("submitTime", getSubmitTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}