package com.hmmp.system.domain.publisher;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 发票对象 publisher_invoice
 * 
 * @author hmmp
 */
public class PublisherInvoice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 发票ID */
    private Long invoiceId;

    /** 关联订单ID */
    private Long orderId;

    /** 发票编号 */
    private String invoiceNo;

    /** 发票类型 */
    private String invoiceType;

    /** 发票金额 */
    private BigDecimal amount;

    /** 税额 */
    private BigDecimal taxAmount;

    /** 价税合计 */
    private BigDecimal totalAmount;

    /** 发票抬头 */
    private String title;

    /** 税号 */
    private String taxId;

    /** 申请状态 */
    private String applyStatus;

    /** 审核时间 */
    private Date auditTime;

    /** 审核人 */
    private String auditBy;

    /** 审核意见 */
    private String auditOpinion;

    /** 开票状态 */
    private String makeStatus;

    /** 开票时间 */
    private Date makeTime;

    /** 邮寄状态 */
    private String postStatus;

    /** 邮寄时间 */
    private Date postTime;

    /** 邮寄单号 */
    private String trackingNo;

    public Long getInvoiceId()
    {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getInvoiceNo()
    {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo)
    {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceType()
    {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTaxId()
    {
        return taxId;
    }

    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
    }

    public String getApplyStatus()
    {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus)
    {
        this.applyStatus = applyStatus;
    }

    public Date getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public String getAuditBy()
    {
        return auditBy;
    }

    public void setAuditBy(String auditBy)
    {
        this.auditBy = auditBy;
    }

    public String getAuditOpinion()
    {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion)
    {
        this.auditOpinion = auditOpinion;
    }

    public String getMakeStatus()
    {
        return makeStatus;
    }

    public void setMakeStatus(String makeStatus)
    {
        this.makeStatus = makeStatus;
    }

    public Date getMakeTime()
    {
        return makeTime;
    }

    public void setMakeTime(Date makeTime)
    {
        this.makeTime = makeTime;
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

    public String getTrackingNo()
    {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo)
    {
        this.trackingNo = trackingNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("invoiceId", getInvoiceId())
            .append("orderId", getOrderId())
            .append("invoiceNo", getInvoiceNo())
            .append("invoiceType", getInvoiceType())
            .append("amount", getAmount())
            .append("taxAmount", getTaxAmount())
            .append("totalAmount", getTotalAmount())
            .append("title", getTitle())
            .append("taxId", getTaxId())
            .append("applyStatus", getApplyStatus())
            .append("auditTime", getAuditTime())
            .append("auditBy", getAuditBy())
            .append("auditOpinion", getAuditOpinion())
            .append("makeStatus", getMakeStatus())
            .append("makeTime", getMakeTime())
            .append("postStatus", getPostStatus())
            .append("postTime", getPostTime())
            .append("trackingNo", getTrackingNo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}