package com.hmmp.system.domain.publisher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 期刊年份对象 publisher_year
 * 
 * @author hmmp
 */
public class PublisherYear extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 年份ID */
    private Long yearId;

    /** 年份 */
    private Integer year;

    /** 状态 */
    private String status;

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
            .append("yearId", getYearId())
            .append("year", getYear())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}