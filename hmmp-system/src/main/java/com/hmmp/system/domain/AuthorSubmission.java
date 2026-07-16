package com.hmmp.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 作者投稿实体 author_submission
 * 
 * @author hmmp
 */
public class AuthorSubmission extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 投稿ID */
    private Long submissionId;

    /** 作者用户ID */
    private Long userId;

    /** 关联稿件ID */
    private Long manuscriptId;

    /** 稿件标题 */
    private String title;

    /** 摘要 */
    private String abstractText;

    /** 关键词 */
    private String keywords;

    /** 稿件文件路径 */
    private String filePath;

    /** 投稿时间 */
    private Date submissionTime;

    /** 状态 */
    private String status;

    /** 编辑反馈 */
    private String feedback;

    /** 反馈时间 */
    private Date feedbackTime;

    /** 删除标志 */
    private String isDeleted;

    public Long getSubmissionId()
    {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId)
    {
        this.submissionId = submissionId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
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

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public Date getSubmissionTime()
    {
        return submissionTime;
    }

    public void setSubmissionTime(Date submissionTime)
    {
        this.submissionTime = submissionTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getFeedback()
    {
        return feedback;
    }

    public void setFeedback(String feedback)
    {
        this.feedback = feedback;
    }

    public Date getFeedbackTime()
    {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime)
    {
        this.feedbackTime = feedbackTime;
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
            .append("submissionId", getSubmissionId())
            .append("userId", getUserId())
            .append("manuscriptId", getManuscriptId())
            .append("title", getTitle())
            .append("abstractText", getAbstractText())
            .append("keywords", getKeywords())
            .append("filePath", getFilePath())
            .append("submissionTime", getSubmissionTime())
            .append("status", getStatus())
            .append("feedback", getFeedback())
            .append("feedbackTime", getFeedbackTime())
            .append("isDeleted", getIsDeleted())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}