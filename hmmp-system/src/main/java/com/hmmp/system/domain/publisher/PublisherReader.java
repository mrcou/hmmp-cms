package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 读者信息对象 publisher_reader
 * 
 * @author hmmp
 */
public class PublisherReader extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 读者ID */
    private Long readerId;

    /** 关联系统用户ID */
    private Long userId;

    /** 真实姓名 */
    private String realName;

    /** 昵称 */
    private String nickName;

    /** 性别 */
    private String gender;

    /** 出生日期 */
    private Date birthday;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 手机 */
    private String mobile;

    /** 通讯地址 */
    private String address;

    /** 邮编 */
    private String zipCode;

    /** 注册时间 */
    private Date registerTime;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 状态 */
    private String status;

    public Long getReaderId()
    {
        return readerId;
    }

    public void setReaderId(Long readerId)
    {
        this.readerId = readerId;
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

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
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

    public Date getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime)
    {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime()
    {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("readerId", getReaderId())
            .append("userId", getUserId())
            .append("realName", getRealName())
            .append("nickName", getNickName())
            .append("gender", getGender())
            .append("birthday", getBirthday())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("mobile", getMobile())
            .append("address", getAddress())
            .append("zipCode", getZipCode())
            .append("registerTime", getRegisterTime())
            .append("lastLoginTime", getLastLoginTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}