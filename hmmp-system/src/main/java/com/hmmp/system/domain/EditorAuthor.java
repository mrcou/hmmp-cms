package com.hmmp.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 作者信息 editor_author
 * 
 * @author hmmp
 */
public class EditorAuthor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 作者ID */
    private Long authorId;

    /** 用户ID */
    private Long userId;

    /** 真实姓名 */
    private String realName;

    /** 笔名 */
    private String penName;

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

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 手机 */
    private String mobile;

    /** 地址 */
    private String address;

    /** 邮编 */
    private String zipCode;

    /** 状态 */
    private String status;

    /** 是否删除 */
    private String isDeleted;

    /** 删除时间 */
    private Date deleteTime;

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
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

    public String getPenName()
    {
        return penName;
    }

    public void setPenName(String penName)
    {
        this.penName = penName;
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

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
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
            .append("authorId", getAuthorId())
            .append("userId", getUserId())
            .append("realName", getRealName())
            .append("penName", getPenName())
            .append("gender", getGender())
            .append("birthday", getBirthday())
            .append("affiliation", getAffiliation())
            .append("department", getDepartment())
            .append("title", getTitle())
            .append("academicDegree", getAcademicDegree())
            .append("researchDirection", getResearchDirection())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("mobile", getMobile())
            .append("address", getAddress())
            .append("zipCode", getZipCode())
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