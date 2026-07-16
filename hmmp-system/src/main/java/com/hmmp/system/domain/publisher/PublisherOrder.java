package com.hmmp.system.domain.publisher;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 订单对象 publisher_order
 * 
 * @author hmmp
 */
public class PublisherOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 订单编号 */
    private String orderNo;

    /** 订户ID */
    private Long subscriberId;

    /** 订单类型 */
    private String orderType;

    /** 订单金额 */
    private BigDecimal totalAmount;

    /** 已付金额 */
    private BigDecimal paidAmount;

    /** 支付状态 */
    private String payStatus;

    /** 支付时间 */
    private Date payTime;

    /** 支付方式 */
    private String payMethod;

    /** 订单状态 */
    private String orderStatus;

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

    /** 发货时间 */
    private Date shippingTime;

    /** 完成时间 */
    private Date completeTime;

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public Long getSubscriberId()
    {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId)
    {
        this.subscriberId = subscriberId;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount()
    {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount)
    {
        this.paidAmount = paidAmount;
    }

    public String getPayStatus()
    {
        return payStatus;
    }

    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }

    public Date getPayTime()
    {
        return payTime;
    }

    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public String getPayMethod()
    {
        return payMethod;
    }

    public void setPayMethod(String payMethod)
    {
        this.payMethod = payMethod;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
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

    public Date getShippingTime()
    {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime)
    {
        this.shippingTime = shippingTime;
    }

    public Date getCompleteTime()
    {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime)
    {
        this.completeTime = completeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("subscriberId", getSubscriberId())
            .append("orderType", getOrderType())
            .append("totalAmount", getTotalAmount())
            .append("paidAmount", getPaidAmount())
            .append("payStatus", getPayStatus())
            .append("payTime", getPayTime())
            .append("payMethod", getPayMethod())
            .append("orderStatus", getOrderStatus())
            .append("shippingName", getShippingName())
            .append("shippingPhone", getShippingPhone())
            .append("shippingAddress", getShippingAddress())
            .append("shippingZipCode", getShippingZipCode())
            .append("trackingNo", getTrackingNo())
            .append("shippingTime", getShippingTime())
            .append("completeTime", getCompleteTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}