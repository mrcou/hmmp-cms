package com.hmmp.system.domain;

import java.math.BigDecimal;
import com.hmmp.common.annotation.Excel;

/**
 * 用户统计行（按系统用户聚合其投稿与审稿数据）
 *
 * @author hmmp
 */
public class StatUser
{
    /** 用户ID */
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 姓名 */
    @Excel(name = "姓名")
    private String realName;

    /** 单位 */
    @Excel(name = "单位")
    private String affiliation;

    /** E-mail */
    @Excel(name = "E-mail")
    private String email;

    /** 稿件数（作为作者投稿的稿件数） */
    @Excel(name = "稿件数")
    private Long submissionCount;

    /** 发表（已发表/已归档的稿件数） */
    @Excel(name = "发表")
    private Long publishCount;

    /** 审稿篇数（作为审稿人审理的稿件数） */
    @Excel(name = "审稿篇数")
    private Long reviewManuscriptCount;

    /** 审稿次数（审稿记录条数） */
    @Excel(name = "审稿次数")
    private Long reviewTimes;

    /** 已审回（已完成审稿的记录数） */
    @Excel(name = "已审回")
    private Long completedReviewCount;

    /** 审稿得分（平均评分） */
    @Excel(name = "审稿得分")
    private BigDecimal reviewScoreAvg;

    /** 接收次数（已接受审稿邀请次数） */
    @Excel(name = "接收次数")
    private Long acceptedCount;

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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Long getSubmissionCount()
    {
        return submissionCount;
    }

    public void setSubmissionCount(Long submissionCount)
    {
        this.submissionCount = submissionCount;
    }

    public Long getPublishCount()
    {
        return publishCount;
    }

    public void setPublishCount(Long publishCount)
    {
        this.publishCount = publishCount;
    }

    public Long getReviewManuscriptCount()
    {
        return reviewManuscriptCount;
    }

    public void setReviewManuscriptCount(Long reviewManuscriptCount)
    {
        this.reviewManuscriptCount = reviewManuscriptCount;
    }

    public Long getReviewTimes()
    {
        return reviewTimes;
    }

    public void setReviewTimes(Long reviewTimes)
    {
        this.reviewTimes = reviewTimes;
    }

    public Long getCompletedReviewCount()
    {
        return completedReviewCount;
    }

    public void setCompletedReviewCount(Long completedReviewCount)
    {
        this.completedReviewCount = completedReviewCount;
    }

    public BigDecimal getReviewScoreAvg()
    {
        return reviewScoreAvg;
    }

    public void setReviewScoreAvg(BigDecimal reviewScoreAvg)
    {
        this.reviewScoreAvg = reviewScoreAvg;
    }

    public Long getAcceptedCount()
    {
        return acceptedCount;
    }

    public void setAcceptedCount(Long acceptedCount)
    {
        this.acceptedCount = acceptedCount;
    }
}
