package com.hmmp.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.annotation.Excel;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 审理周期统计明细（editor_manuscript 联查 publisher_article / publisher_issue / publisher_column）
 * 
 * @author hmmp
 */
public class StatAuditCycle extends BaseEntity
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

    /** 投稿作者 */
    @Excel(name = "投稿作者")
    private String authorNames;

    /** 投稿时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投稿时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /** 终止审稿时间（结论时间） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "终止审稿", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date conclusionTime;

    /** 处理结论（不宜采用 / 直接退稿等） */
    @Excel(name = "稿件状态")
    private String conclusion;

    /** 稿件状态编码 */
    private String status;

    /** 年份 */
    @Excel(name = "年份")
    private Integer year;

    /** 刊期 */
    @Excel(name = "刊期")
    private String period;

    /** 栏目名称 */
    @Excel(name = "栏目名称")
    private String columnName;

    /** 审理周期（天） */
    @Excel(name = "审理周期(天)")
    private Integer auditCycleDays;

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

    public Date getReceiveTime()
    {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }

    public Date getConclusionTime()
    {
        return conclusionTime;
    }

    public void setConclusionTime(Date conclusionTime)
    {
        this.conclusionTime = conclusionTime;
    }

    public String getConclusion()
    {
        return conclusion;
    }

    public void setConclusion(String conclusion)
    {
        this.conclusion = conclusion;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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

    public Integer getAuditCycleDays()
    {
        return auditCycleDays;
    }

    public void setAuditCycleDays(Integer auditCycleDays)
    {
        this.auditCycleDays = auditCycleDays;
    }
}
