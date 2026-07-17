package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 文章评论对象 publisher_comment
 * 
 * @author hmmp
 */
public class PublisherComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    private Long commentId;

    /** 文章ID */
    private Long articleId;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 评论内容 */
    private String content;

    /** 审核状态 */
    private String auditStatus;

    /** 审核时间 */
    private Date auditTime;

    /** 审核人 */
    private String auditBy;

    /** 是否置顶 */
    private String isTop;

    /** 回复评论ID */
    private Long replyTo;

    /** 点赞数 */
    private Integer likeCount;

    /** 状态 */
    private String status;

    /** 稿件编号（关联文章，非表字段） */
    private String fileNo;

    /** 文章标题（关联文章，非表字段） */
    private String articleTitle;

    /** 年份（关联文章，非表字段） */
    private Integer yearNo;

    /** 刊期（关联文章，非表字段） */
    private Integer period;

    /** 杂志编号（筛选/关联，非表字段） */
    private String journalCode;

    /** 会议ID（筛选，非表字段） */
    private Long meetingId;

    /** 批量审核ID（非表字段） */
    private Long[] commentIds;

    public Long getCommentId()
    {
        return commentId;
    }

    public void setCommentId(Long commentId)
    {
        this.commentId = commentId;
    }

    public Long getArticleId()
    {
        return articleId;
    }

    public void setArticleId(Long articleId)
    {
        this.articleId = articleId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getAuditStatus()
    {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus)
    {
        this.auditStatus = auditStatus;
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

    public String getIsTop()
    {
        return isTop;
    }

    public void setIsTop(String isTop)
    {
        this.isTop = isTop;
    }

    public Long getReplyTo()
    {
        return replyTo;
    }

    public void setReplyTo(Long replyTo)
    {
        this.replyTo = replyTo;
    }

    public Integer getLikeCount()
    {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount)
    {
        this.likeCount = likeCount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getFileNo()
    {
        return fileNo;
    }

    public void setFileNo(String fileNo)
    {
        this.fileNo = fileNo;
    }

    public String getArticleTitle()
    {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle)
    {
        this.articleTitle = articleTitle;
    }

    public Integer getYearNo()
    {
        return yearNo;
    }

    public void setYearNo(Integer yearNo)
    {
        this.yearNo = yearNo;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public String getJournalCode()
    {
        return journalCode;
    }

    public void setJournalCode(String journalCode)
    {
        this.journalCode = journalCode;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public Long[] getCommentIds()
    {
        return commentIds;
    }

    public void setCommentIds(Long[] commentIds)
    {
        this.commentIds = commentIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("commentId", getCommentId())
            .append("articleId", getArticleId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("content", getContent())
            .append("auditStatus", getAuditStatus())
            .append("auditTime", getAuditTime())
            .append("auditBy", getAuditBy())
            .append("isTop", getIsTop())
            .append("replyTo", getReplyTo())
            .append("likeCount", getLikeCount())
            .append("status", getStatus())
            .append("fileNo", getFileNo())
            .append("articleTitle", getArticleTitle())
            .append("yearNo", getYearNo())
            .append("period", getPeriod())
            .append("journalCode", getJournalCode())
            .append("meetingId", getMeetingId())
            .append("createTime", getCreateTime())
            .toString();
    }
}