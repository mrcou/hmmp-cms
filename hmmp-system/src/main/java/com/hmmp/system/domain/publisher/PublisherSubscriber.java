package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 订户信息对象 publisher_subscriber
 * 
 * @author hmmp
 */
public class PublisherSubscriber extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订户ID */
    private Long subscriberId;

    /** 关联读者ID */
    private Long readerId;

    /** 姓名 */
    private String realName;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 手机 */
    private String mobile;

    /** 邮寄地址 */
    private String address;

    /** 邮编 */
    private String zipCode;

    /** 单位 */
    private String company;

    /** 订阅开始日期 */
    private Date subscribeStartDate;

    /** 订阅结束日期 */
    private Date subscribeEndDate;

    /** 状态 */
    private String status;

    public Long getSubscriberId()
    {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId)
    {
        this.subscriberId = subscriberId;
    }

    public Long getReaderId()
    {
        return readerId;
    }

    public void setReaderId(Long readerId)
    {
        this.readerId = readerId;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
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

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public Date getSubscribeStartDate()
    {
        return subscribeStartDate;
    }

    public void setSubscribeStartDate(Date subscribeStartDate)
    {
        this.subscribeStartDate = subscribeStartDate;
    }

    public Date getSubscribeEndDate()
    {
        return subscribeEndDate;
    }

    public void setSubscribeEndDate(Date subscribeEndDate)
    {
        this.subscribeEndDate = subscribeEndDate;
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
            .append("subscriberId", getSubscriberId())
            .append("readerId", getReaderId())
            .append("realName", getRealName())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("mobile", getMobile())
            .append("address", getAddress())
            .append("zipCode", getZipCode())
            .append("company", getCompany())
            .append("subscribeStartDate", getSubscribeStartDate())
            .append("subscribeEndDate", getSubscribeEndDate())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}