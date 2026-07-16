package com.hmmp.system.domain.message;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 用户留言表 msg_remark
 *
 * @author hmmp
 */
public class MsgRemark extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long remarkId;
    private Long articleId;
    private Long userId;
    private String userName;
    private String content;
    private String replyContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;

    private String replyBy;
    private String status;

    public Long getRemarkId()
    {
        return remarkId;
    }

    public void setRemarkId(Long remarkId)
    {
        this.remarkId = remarkId;
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

    public String getReplyContent()
    {
        return replyContent;
    }

    public void setReplyContent(String replyContent)
    {
        this.replyContent = replyContent;
    }

    public Date getReplyTime()
    {
        return replyTime;
    }

    public void setReplyTime(Date replyTime)
    {
        this.replyTime = replyTime;
    }

    public String getReplyBy()
    {
        return replyBy;
    }

    public void setReplyBy(String replyBy)
    {
        this.replyBy = replyBy;
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
        return "MsgRemark{" +
            "remarkId=" + remarkId +
            ", articleId=" + articleId +
            ", userId=" + userId +
            ", userName='" + userName + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}