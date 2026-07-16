package com.hmmp.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 合同信息对象 ad_bargain
 * 
 * @author hmmp
 */
public class AdBargain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合同ID */
    private Long bargainId;

    /** 合同编号 */
    private String bargainNo;

    /** 客户ID */
    private Long customerId;

    /** 客户名称 */
    private String customerName;

    /** 合同名称 */
    private String bargainName;

    /** 合同金额 */
    private Long totalAmount;

    /** 签订日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date signDate;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 合同内容 */
    private String bargainContent;

    /** 合同文件路径 */
    private String bargainFile;

    /** 申请状态 */
    private String applyStatus;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核人 */
    private String auditBy;

    /** 审核意见 */
    private String auditOpinion;

    /** 打印状态 */
    private String printStatus;

    /** 打印时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date printTime;

    /** 状态 */
    private String status;

    public Long getBargainId()
    {
        return bargainId;
    }

    public void setBargainId(Long bargainId)
    {
        this.bargainId = bargainId;
    }

    public String getBargainNo()
    {
        return bargainNo;
    }

    public void setBargainNo(String bargainNo)
    {
        this.bargainNo = bargainNo;
    }

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getBargainName()
    {
        return bargainName;
    }

    public void setBargainName(String bargainName)
    {
        this.bargainName = bargainName;
    }

    public Long getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public Date getSignDate()
    {
        return signDate;
    }

    public void setSignDate(Date signDate)
    {
        this.signDate = signDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getBargainContent()
    {
        return bargainContent;
    }

    public void setBargainContent(String bargainContent)
    {
        this.bargainContent = bargainContent;
    }

    public String getBargainFile()
    {
        return bargainFile;
    }

    public void setBargainFile(String bargainFile)
    {
        this.bargainFile = bargainFile;
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

    public String getPrintStatus()
    {
        return printStatus;
    }

    public void setPrintStatus(String printStatus)
    {
        this.printStatus = printStatus;
    }

    public Date getPrintTime()
    {
        return printTime;
    }

    public void setPrintTime(Date printTime)
    {
        this.printTime = printTime;
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
            .append("bargainId", getBargainId())
            .append("bargainNo", getBargainNo())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("bargainName", getBargainName())
            .append("totalAmount", getTotalAmount())
            .append("signDate", getSignDate())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("bargainContent", getBargainContent())
            .append("bargainFile", getBargainFile())
            .append("applyStatus", getApplyStatus())
            .append("auditTime", getAuditTime())
            .append("auditBy", getAuditBy())
            .append("auditOpinion", getAuditOpinion())
            .append("printStatus", getPrintStatus())
            .append("printTime", getPrintTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}