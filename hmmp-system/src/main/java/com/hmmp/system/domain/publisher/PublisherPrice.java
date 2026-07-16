package com.hmmp.system.domain.publisher;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 期刊定价对象 publisher_price
 * 
 * @author hmmp
 */
public class PublisherPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 定价ID */
    private Long priceId;

    /** 年份ID */
    private Long yearId;

    /** 年份 */
    private Integer year;

    /** 期数 */
    private Integer period;

    /** 定价类型 */
    private String priceType;

    /** 价格 */
    private BigDecimal price;

    /** 币种 */
    private String currency;

    /** 状态 */
    private String status;

    /** 生效开始日期 */
    private Date validStartDate;

    /** 生效结束日期 */
    private Date validEndDate;

    public Long getPriceId()
    {
        return priceId;
    }

    public void setPriceId(Long priceId)
    {
        this.priceId = priceId;
    }

    public Long getYearId()
    {
        return yearId;
    }

    public void setYearId(Long yearId)
    {
        this.yearId = yearId;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public String getPriceType()
    {
        return priceType;
    }

    public void setPriceType(String priceType)
    {
        this.priceType = priceType;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getValidStartDate()
    {
        return validStartDate;
    }

    public void setValidStartDate(Date validStartDate)
    {
        this.validStartDate = validStartDate;
    }

    public Date getValidEndDate()
    {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate)
    {
        this.validEndDate = validEndDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("priceId", getPriceId())
            .append("yearId", getYearId())
            .append("year", getYear())
            .append("period", getPeriod())
            .append("priceType", getPriceType())
            .append("price", getPrice())
            .append("currency", getCurrency())
            .append("status", getStatus())
            .append("validStartDate", getValidStartDate())
            .append("validEndDate", getValidEndDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}