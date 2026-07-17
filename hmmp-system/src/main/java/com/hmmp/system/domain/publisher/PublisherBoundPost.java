package com.hmmp.system.domain.publisher;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.annotation.Excel;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 邮寄合订本 publisher_bound_post（联查 publisher_order / publisher_subscriber）
 *
 * @author hmmp
 */
public class PublisherBoundPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合订本寄书登记ID */
    private Long boundId;

    /** 关联订单ID */
    private Long orderId;

    /** 订单编号（非表字段） */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 杂志编号 */
    @Excel(name = "杂志编号")
    private String journalCode;

    /** 行业分类 */
    @Excel(name = "行业分类")
    private String category;

    /** 企业类型 */
    @Excel(name = "企业类型")
    private String unitType;

    /** 发行方式 */
    @Excel(name = "发行方式")
    private String distributeType;

    /** 邮寄方式 */
    @Excel(name = "邮寄方式")
    private String sendMethodName;

    /** 订购年份 */
    @Excel(name = "订购年份")
    private Integer yearId;

    /** 收书人姓名 */
    @Excel(name = "收书人姓名")
    private String receiveUser;

    /** 收书人单位 */
    @Excel(name = "收书人单位")
    private String receiveUnit;

    /** 收书人地址 */
    @Excel(name = "收书人地址")
    private String receiveAddress;

    /** 读者邮编 */
    @Excel(name = "读者邮编")
    private String receivePostcode;

    /** 订单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "订单日期", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    /** 邮寄数量 */
    @Excel(name = "邮寄数量")
    private Integer orderBookNum;

    /** 寄书时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "寄书时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date sendBookTime;

    /** 寄书人 */
    @Excel(name = "寄书人")
    private String sendUser;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal rmbOrderCost;

    /** 是否可邮寄 */
    private String canPost;

    /** 会议ID（筛选） */
    private Long meetingId;

    /** 是否仅查已寄书 */
    private Boolean mailedOnly;

    /** 是否仅查待寄书 */
    private Boolean pendingOnly;

    /** 批量操作订单ID */
    private Long[] orderIds;

    public Long getBoundId()
    {
        return boundId;
    }

    public void setBoundId(Long boundId)
    {
        this.boundId = boundId;
    }

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

    public String getJournalCode()
    {
        return journalCode;
    }

    public void setJournalCode(String journalCode)
    {
        this.journalCode = journalCode;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getUnitType()
    {
        return unitType;
    }

    public void setUnitType(String unitType)
    {
        this.unitType = unitType;
    }

    public String getDistributeType()
    {
        return distributeType;
    }

    public void setDistributeType(String distributeType)
    {
        this.distributeType = distributeType;
    }

    public String getSendMethodName()
    {
        return sendMethodName;
    }

    public void setSendMethodName(String sendMethodName)
    {
        this.sendMethodName = sendMethodName;
    }

    public Integer getYearId()
    {
        return yearId;
    }

    public void setYearId(Integer yearId)
    {
        this.yearId = yearId;
    }

    public String getReceiveUser()
    {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser)
    {
        this.receiveUser = receiveUser;
    }

    public String getReceiveUnit()
    {
        return receiveUnit;
    }

    public void setReceiveUnit(String receiveUnit)
    {
        this.receiveUnit = receiveUnit;
    }

    public String getReceiveAddress()
    {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress)
    {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePostcode()
    {
        return receivePostcode;
    }

    public void setReceivePostcode(String receivePostcode)
    {
        this.receivePostcode = receivePostcode;
    }

    public Date getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    public Integer getOrderBookNum()
    {
        return orderBookNum;
    }

    public void setOrderBookNum(Integer orderBookNum)
    {
        this.orderBookNum = orderBookNum;
    }

    public Date getSendBookTime()
    {
        return sendBookTime;
    }

    public void setSendBookTime(Date sendBookTime)
    {
        this.sendBookTime = sendBookTime;
    }

    public String getSendUser()
    {
        return sendUser;
    }

    public void setSendUser(String sendUser)
    {
        this.sendUser = sendUser;
    }

    public BigDecimal getRmbOrderCost()
    {
        return rmbOrderCost;
    }

    public void setRmbOrderCost(BigDecimal rmbOrderCost)
    {
        this.rmbOrderCost = rmbOrderCost;
    }

    public String getCanPost()
    {
        return canPost;
    }

    public void setCanPost(String canPost)
    {
        this.canPost = canPost;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public Boolean getMailedOnly()
    {
        return mailedOnly;
    }

    public void setMailedOnly(Boolean mailedOnly)
    {
        this.mailedOnly = mailedOnly;
    }

    public Boolean getPendingOnly()
    {
        return pendingOnly;
    }

    public void setPendingOnly(Boolean pendingOnly)
    {
        this.pendingOnly = pendingOnly;
    }

    public Long[] getOrderIds()
    {
        return orderIds;
    }

    public void setOrderIds(Long[] orderIds)
    {
        this.orderIds = orderIds;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("boundId", getBoundId())
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("journalCode", getJournalCode())
            .append("receiveUser", getReceiveUser())
            .append("receiveAddress", getReceiveAddress())
            .append("orderBookNum", getOrderBookNum())
            .append("sendBookTime", getSendBookTime())
            .append("sendUser", getSendUser())
            .toString();
    }
}
