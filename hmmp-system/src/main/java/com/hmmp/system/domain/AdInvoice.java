package com.hmmp.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 发票信息对象 ad_invoice
 * 
 * @author hmmp
 */
public class AdInvoice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 发票ID */
    private Long invoiceId;

    /** 关联合同ID */
    private Long bargainId;

    /** 发票编号 */
    private String invoiceNo;

    /** 发票类型 */
    private String invoiceType;

    /** 发票金额 */
    private Long amount;

    /** 税额 */
    private Long taxAmount;

    /** 价税合计 */
    private Long totalAmount;

    /** 发票抬头 */
    private String title;

    /** 税号 */
    private String taxId;

    /** 申请状态 */
    private String applyStatus;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核人 */
    private String auditBy;

    /** 开票状态 */
    private String makeStatus;

    /** 开票时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date makeTime;

    public Long getInvoiceId()
    {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    public Long getBargainId()
    {
        return bargainId;
    }

    public void setBargainId(Long bargainId)
    {
        this.bargainId = bargainId;
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

    public Long getAmount()
    {
        return amount;
    }

    public void setAmount(Long amount)
    {
        this.amount = amount;
    }

    public Long getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(Long taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    public Long getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount)
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("invoiceId", getInvoiceId())
            .append("bargainId", getBargainId())
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
            .append("makeStatus", getMakeStatus())
            .append("makeTime", getMakeTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}