package com.hmmp.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 审稿人信息 editor_reviewer
 * 
 * @author hmmp
 */
public class EditorReviewer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审稿人ID */
    private Long reviewerId;

    /** 用户ID */
    private Long userId;

    /** 真实姓名 */
    private String realName;

    /** 性别 */
    private String gender;

    /** 出生日期 */
    private Date birthday;

    /** 所属单位 */
    private String affiliation;

    /** 部门 */
    private String department;

    /** 职称 */
    private String title;

    /** 学位 */
    private String academicDegree;

    /** 研究方向 */
    private String researchDirection;

    /** 专长领域 */
    private String expertiseFields;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 手机 */
    private String mobile;

    /** 地址 */
    private String address;

    /** 审稿次数 */
    private Integer reviewCount;

    /** 审稿通过率 */
    private BigDecimal acceptRate;

    /** 平均审稿天数 */
    private BigDecimal avgReviewDays;

    /** 审稿平均分 */
    private BigDecimal reviewScoreAvg;

    /** 状态 */
    private String status;

    /** 是否删除 */
    private String isDeleted;

    /** 删除时间 */
    private Date deleteTime;

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

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getAffiliation()
    {
        return affiliation;
    }

    public void setAffiliation(String affiliation)
    {
        this.affiliation = affiliation;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAcademicDegree()
    {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree)
    {
        this.academicDegree = academicDegree;
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

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Integer getReviewCount()
    {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount)
    {
        this.reviewCount = reviewCount;
    }

    public BigDecimal getAcceptRate()
    {
        return acceptRate;
    }

    public void setAcceptRate(BigDecimal acceptRate)
    {
        this.acceptRate = acceptRate;
    }

    public BigDecimal getAvgReviewDays()
    {
        return avgReviewDays;
    }

    public void setAvgReviewDays(BigDecimal avgReviewDays)
    {
        this.avgReviewDays = avgReviewDays;
    }

    public BigDecimal getReviewScoreAvg()
    {
        return reviewScoreAvg;
    }

    public void setReviewScoreAvg(BigDecimal reviewScoreAvg)
    {
        this.reviewScoreAvg = reviewScoreAvg;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("reviewerId", getReviewerId())
            .append("userId", getUserId())
            .append("realName", getRealName())
            .append("gender", getGender())
            .append("birthday", getBirthday())
            .append("affiliation", getAffiliation())
            .append("department", getDepartment())
            .append("title", getTitle())
            .append("academicDegree", getAcademicDegree())
            .append("researchDirection", getResearchDirection())
            .append("expertiseFields", getExpertiseFields())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("mobile", getMobile())
            .append("address", getAddress())
            .append("reviewCount", getReviewCount())
            .append("acceptRate", getAcceptRate())
            .append("avgReviewDays", getAvgReviewDays())
            .append("reviewScoreAvg", getReviewScoreAvg())
            .append("status", getStatus())
            .append("isDeleted", getIsDeleted())
            .append("deleteTime", getDeleteTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}