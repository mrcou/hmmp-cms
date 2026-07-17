package com.hmmp.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.annotation.Excel;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 加工周期统计明细（editor_manuscript 联查 editor_edit / publisher_article / publisher_issue / publisher_column）
 *
 * @author hmmp
 */
public class StatEditArticleCycle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 稿件ID */
    private Long manuscriptId;

    /** 编辑加工记录ID */
    private Long editId;

    /** 稿件编号 */
    @Excel(name = "稿件编号")
    private String fileNo;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 投稿作者 */
    @Excel(name = "投稿作者")
    private String authorNames;

    /** 稿件阶段（状态编码） */
    @Excel(name = "稿件阶段", readConverterExp = "01=新收稿,02=新分配,03=审稿中,04=已退修,05=已修回,06=已采用,07=已退稿,08=已发表,09=已归档")
    private String status;

    /** 指定责编 */
    @Excel(name = "指定责编")
    private String editorName;

    /** 完成日期（编辑完成时间） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "完成日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date editEndTime;

    /** 选件时间（编辑开始时间） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "选件时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date editStartTime;

    /** 年份 */
    @Excel(name = "年份")
    private Integer year;

    /** 期 */
    @Excel(name = "期")
    private String period;

    /** 栏目名称 */
    @Excel(name = "栏目名称")
    private String columnName;

    /** 加工周期（天） */
    @Excel(name = "加工周期(天)")
    private Integer editArticleCycleDays;

    /** 编辑状态（筛选：0待编辑 1编辑中 2已完成） */
    private String editStatus;

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
    }

    public Long getEditId()
    {
        return editId;
    }

    public void setEditId(Long editId)
    {
        this.editId = editId;
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getEditorName()
    {
        return editorName;
    }

    public void setEditorName(String editorName)
    {
        this.editorName = editorName;
    }

    public Date getEditEndTime()
    {
        return editEndTime;
    }

    public void setEditEndTime(Date editEndTime)
    {
        this.editEndTime = editEndTime;
    }

    public Date getEditStartTime()
    {
        return editStartTime;
    }

    public void setEditStartTime(Date editStartTime)
    {
        this.editStartTime = editStartTime;
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

    public Integer getEditArticleCycleDays()
    {
        return editArticleCycleDays;
    }

    public void setEditArticleCycleDays(Integer editArticleCycleDays)
    {
        this.editArticleCycleDays = editArticleCycleDays;
    }

    public String getEditStatus()
    {
        return editStatus;
    }

    public void setEditStatus(String editStatus)
    {
        this.editStatus = editStatus;
    }
}
