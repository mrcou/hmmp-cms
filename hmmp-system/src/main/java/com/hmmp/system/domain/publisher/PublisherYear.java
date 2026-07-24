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

    /** 杂志编号 */
    private String journalCode;

    /** 年份 */
    private Integer year;

    /** 期数 */
    private String period;

    /** 卷号 */
    private Integer volume;

    /** 中文名 */
    private String nameCn;

    /** 英文名 */
    private String nameEn;

    /** 目录文件 */
    private String catalogFile;

    /** 是否免费(0否 1是) */
    private String isFree;

    /** 会议ID */
    private Long meetingId;

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

    public String getJournalCode()
    {
        return journalCode;
    }

    public void setJournalCode(String journalCode)
    {
        this.journalCode = journalCode;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public Integer getVolume()
    {
        return volume;
    }

    public void setVolume(Integer volume)
    {
        this.volume = volume;
    }

    public String getNameCn()
    {
        return nameCn;
    }

    public void setNameCn(String nameCn)
    {
        this.nameCn = nameCn;
    }

    public String getNameEn()
    {
        return nameEn;
    }

    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }

    public String getCatalogFile()
    {
        return catalogFile;
    }

    public void setCatalogFile(String catalogFile)
    {
        this.catalogFile = catalogFile;
    }

    public String getIsFree()
    {
        return isFree;
    }

    public void setIsFree(String isFree)
    {
        this.isFree = isFree;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
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
            .append("journalCode", getJournalCode())
            .append("year", getYear())
            .append("period", getPeriod())
            .append("volume", getVolume())
            .append("nameCn", getNameCn())
            .append("nameEn", getNameEn())
            .append("catalogFile", getCatalogFile())
            .append("isFree", getIsFree())
            .append("meetingId", getMeetingId())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
