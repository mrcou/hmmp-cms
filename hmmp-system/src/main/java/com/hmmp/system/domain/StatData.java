package com.hmmp.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 统计数据实体 stat_data
 * 
 * @author hmmp
 */
public class StatData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 统计ID */
    private Long statId;

    /** 统计类型 */
    private String statType;

    /** 统计名称 */
    private String statName;

    /** 统计日期 */
    private Date statDate;

    /** 统计年份 */
    private Integer year;

    /** 统计月份 */
    private Integer month;

    /** 统计数值 */
    private BigDecimal dataValue;

    /** 数据单位 */
    private String dataUnit;

    /** 统计详情JSON */
    private String dataJson;

    /** 统计时间 */
    private Date statTime;

    public Long getStatId()
    {
        return statId;
    }

    public void setStatId(Long statId)
    {
        this.statId = statId;
    }

    public String getStatType()
    {
        return statType;
    }

    public void setStatType(String statType)
    {
        this.statType = statType;
    }

    public String getStatName()
    {
        return statName;
    }

    public void setStatName(String statName)
    {
        this.statName = statName;
    }

    public Date getStatDate()
    {
        return statDate;
    }

    public void setStatDate(Date statDate)
    {
        this.statDate = statDate;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getMonth()
    {
        return month;
    }

    public void setMonth(Integer month)
    {
        this.month = month;
    }

    public BigDecimal getDataValue()
    {
        return dataValue;
    }

    public void setDataValue(BigDecimal dataValue)
    {
        this.dataValue = dataValue;
    }

    public String getDataUnit()
    {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit)
    {
        this.dataUnit = dataUnit;
    }

    public String getDataJson()
    {
        return dataJson;
    }

    public void setDataJson(String dataJson)
    {
        this.dataJson = dataJson;
    }

    public Date getStatTime()
    {
        return statTime;
    }

    public void setStatTime(Date statTime)
    {
        this.statTime = statTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("statId", getStatId())
            .append("statType", getStatType())
            .append("statName", getStatName())
            .append("statDate", getStatDate())
            .append("year", getYear())
            .append("month", getMonth())
            .append("dataValue", getDataValue())
            .append("dataUnit", getDataUnit())
            .append("dataJson", getDataJson())
            .append("statTime", getStatTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}