package com.hmmp.system.domain;

import java.math.BigDecimal;
import com.hmmp.common.annotation.Excel;

/**
 * 审稿统计行（按审稿人聚合审稿数据）
 *
 * @author hmmp
 */
public class StatReviewerAudit
{
    /** 审稿人ID */
    private Long reviewerId;

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

    /** 手机 */
    @Excel(name = "手机")
    private String mobile;

    /** E-mail */
    @Excel(name = "E-mail")
    private String email;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 审稿领域 */
    @Excel(name = "审稿领域")
    private String expertiseFields;

    /** 查询关键词 */
    @Excel(name = "查询关键词")
    private String searchKeywords;

    /** 权限级别 */
    @Excel(name = "权限级别")
    private String permissionLevel;

    /** 信誉度 */
    @Excel(name = "信誉度")
    private BigDecimal reputation;

    /** 审回次数 */
    @Excel(name = "审回次数")
    private Long returnedCount;

    /** 复审回次数 */
    @Excel(name = "复审回次数")
    private Long reReviewReturnedCount;

    /** 审回篇数 */
    @Excel(name = "审回篇数")
    private Long returnedManuscriptCount;

    /** 审稿周期（天） */
    @Excel(name = "审稿周期")
    private BigDecimal reviewCycleDays;

    /** 审稿得分 */
    @Excel(name = "审稿得分")
    private BigDecimal reviewScoreAvg;

    /** 审回率（0-100） */
    @Excel(name = "审回率")
    private BigDecimal returnRate;

    /** 审稿费 */
    @Excel(name = "审稿费")
    private BigDecimal reviewFee;

    public Long getReviewerId()
    {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId)
    {
        this.reviewerId = reviewerId;
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

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getIdCard()
    {
        return idCard;
    }

    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getExpertiseFields()
    {
        return expertiseFields;
    }

    public void setExpertiseFields(String expertiseFields)
    {
        this.expertiseFields = expertiseFields;
    }

    public String getSearchKeywords()
    {
        return searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords)
    {
        this.searchKeywords = searchKeywords;
    }

    public String getPermissionLevel()
    {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel)
    {
        this.permissionLevel = permissionLevel;
    }

    public BigDecimal getReputation()
    {
        return reputation;
    }

    public void setReputation(BigDecimal reputation)
    {
        this.reputation = reputation;
    }

    public Long getReturnedCount()
    {
        return returnedCount;
    }

    public void setReturnedCount(Long returnedCount)
    {
        this.returnedCount = returnedCount;
    }

    public Long getReReviewReturnedCount()
    {
        return reReviewReturnedCount;
    }

    public void setReReviewReturnedCount(Long reReviewReturnedCount)
    {
        this.reReviewReturnedCount = reReviewReturnedCount;
    }

    public Long getReturnedManuscriptCount()
    {
        return returnedManuscriptCount;
    }

    public void setReturnedManuscriptCount(Long returnedManuscriptCount)
    {
        this.returnedManuscriptCount = returnedManuscriptCount;
    }

    public BigDecimal getReviewCycleDays()
    {
        return reviewCycleDays;
    }

    public void setReviewCycleDays(BigDecimal reviewCycleDays)
    {
        this.reviewCycleDays = reviewCycleDays;
    }

    public BigDecimal getReviewScoreAvg()
    {
        return reviewScoreAvg;
    }

    public void setReviewScoreAvg(BigDecimal reviewScoreAvg)
    {
        this.reviewScoreAvg = reviewScoreAvg;
    }

    public BigDecimal getReturnRate()
    {
        return returnRate;
    }

    public void setReturnRate(BigDecimal returnRate)
    {
        this.returnRate = returnRate;
    }

    public BigDecimal getReviewFee()
    {
        return reviewFee;
    }

    public void setReviewFee(BigDecimal reviewFee)
    {
        this.reviewFee = reviewFee;
    }
}
