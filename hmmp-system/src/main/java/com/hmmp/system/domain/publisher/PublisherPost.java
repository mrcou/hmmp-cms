package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 寄书记录对象 publisher_post
 * 
 * @author hmmp
 */
public class PublisherPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 寄书ID */
    private Long postId;

    /** 关联订单ID */
    private Long orderId;

    /** 邮寄类型 */
    private String postType;

    /** 收货人 */
    private String shippingName;

    /** 收货电话 */
    private String shippingPhone;

    /** 收货地址 */
    private String shippingAddress;

    /** 邮编 */
    private String shippingZipCode;

    /** 物流单号 */
    private String trackingNo;

    /** 快递公司 */
    private String courier;

    /** 邮寄状态 */
    private String postStatus;

    /** 邮寄时间 */
    private Date postTime;

    /** 签收时间 */
    private Date signTime;

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getPostType()
    {
        return postType;
    }

    public void setPostType(String postType)
    {
        this.postType = postType;
    }

    public String getShippingName()
    {
        return shippingName;
    }

    public void setShippingName(String shippingName)
    {
        this.shippingName = shippingName;
    }

    public String getShippingPhone()
    {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone)
    {
        this.shippingPhone = shippingPhone;
    }

    public String getShippingAddress()
    {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingZipCode()
    {
        return shippingZipCode;
    }

    public void setShippingZipCode(String shippingZipCode)
    {
        this.shippingZipCode = shippingZipCode;
    }

    public String getTrackingNo()
    {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo)
    {
        this.trackingNo = trackingNo;
    }

    public String getCourier()
    {
        return courier;
    }

    public void setCourier(String courier)
    {
        this.courier = courier;
    }

    public String getPostStatus()
    {
        return postStatus;
    }

    public void setPostStatus(String postStatus)
    {
        this.postStatus = postStatus;
    }

    public Date getPostTime()
    {
        return postTime;
    }

    public void setPostTime(Date postTime)
    {
        this.postTime = postTime;
    }

    public Date getSignTime()
    {
        return signTime;
    }

    public void setSignTime(Date signTime)
    {
        this.signTime = signTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("postId", getPostId())
            .append("orderId", getOrderId())
            .append("postType", getPostType())
            .append("shippingName", getShippingName())
            .append("shippingPhone", getShippingPhone())
            .append("shippingAddress", getShippingAddress())
            .append("shippingZipCode", getShippingZipCode())
            .append("trackingNo", getTrackingNo())
            .append("courier", getCourier())
            .append("postStatus", getPostStatus())
            .append("postTime", getPostTime())
            .append("signTime", getSignTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}