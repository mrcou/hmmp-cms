package com.hmmp.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 支付信息对象 ad_payment
 * 
 * @author hmmp
 */
public class AdPayment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付ID */
    private Long paymentId;

    /** 关联合同ID */
    private Long bargainId;

    /** 关联广告ID */
    private Long adId;

    /** 支付编号 */
    private String paymentNo;

    /** 支付金额 */
    private Long paymentAmount;

    /** 支付方式 */
    private String paymentMethod;

    /** 支付状态 */
    private String paymentStatus;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    /** 收据文件 */
    private String receiptFile;

    public Long getPaymentId()
    {
        return paymentId;
    }

    public void setPaymentId(Long paymentId)
    {
        this.paymentId = paymentId;
    }

    public Long getBargainId()
    {
        return bargainId;
    }

    public void setBargainId(Long bargainId)
    {
        this.bargainId = bargainId;
    }

    public Long getAdId()
    {
        return adId;
    }

    public void setAdId(Long adId)
    {
        this.adId = adId;
    }

    public String getPaymentNo()
    {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo)
    {
        this.paymentNo = paymentNo;
    }

    public Long getPaymentAmount()
    {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentTime()
    {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }

    public String getReceiptFile()
    {
        return receiptFile;
    }

    public void setReceiptFile(String receiptFile)
    {
        this.receiptFile = receiptFile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("paymentId", getPaymentId())
            .append("bargainId", getBargainId())
            .append("adId", getAdId())
            .append("paymentNo", getPaymentNo())
            .append("paymentAmount", getPaymentAmount())
            .append("paymentMethod", getPaymentMethod())
            .append("paymentStatus", getPaymentStatus())
            .append("paymentTime", getPaymentTime())
            .append("receiptFile", getReceiptFile())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}