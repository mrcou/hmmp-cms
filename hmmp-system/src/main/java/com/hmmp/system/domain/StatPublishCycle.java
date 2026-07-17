package com.hmmp.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.annotation.Excel;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 出版周期统计明细（editor_manuscript 联查 publisher_article / publisher_issue / publisher_column）
 *
 * @author hmmp
 */
public class StatPublishCycle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 稿件ID */
    private Long manuscriptId;

    /** 稿件编号 */
    @Excel(name = "稿件编号")
    private String fileNo;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 作者 */
    @Excel(name = "作者")
    private String authorNames;

    /** 年份 */
    @Excel(name = "年份")
    private Integer year;

    /** 刊期 */
    @Excel(name = "刊期")
    private String period;

    /** 栏目名称 */
    @Excel(name = "栏目名称")
    private String columnName;

    /** 起始页码 */
    @Excel(name = "起始页码")
    private Integer pageStart;

    /** 截止页码 */
    @Excel(name = "截止页码")
    private Integer pageEnd;

    /** 纸质出版时间（刊期出版日期） */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "纸质出版时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paperPublishTime;

    /** 网络出版时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "网络出版时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date onlinePublishTime;

    /** 稿件状态编码 */
    private String status;

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
    }

    public String getFileNo()
    {
        return fileNo;
    }

    public void setFileNo(String fileNo)
    {
        this.fileNo = fileNo;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthorNames()
    {
        return authorNames;
    }

    public void setAuthorNames(String authorNames)
    {
        this.authorNames = authorNames;
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

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public Integer getPageStart()
    {
        return pageStart;
    }

    public void setPageStart(Integer pageStart)
    {
        this.pageStart = pageStart;
    }

    public Integer getPageEnd()
    {
        return pageEnd;
    }

    public void setPageEnd(Integer pageEnd)
    {
        this.pageEnd = pageEnd;
    }

    public Date getPaperPublishTime()
    {
        return paperPublishTime;
    }

    public void setPaperPublishTime(Date paperPublishTime)
    {
        this.paperPublishTime = paperPublishTime;
    }

    public Date getOnlinePublishTime()
    {
        return onlinePublishTime;
    }

    public void setOnlinePublishTime(Date onlinePublishTime)
    {
        this.onlinePublishTime = onlinePublishTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
