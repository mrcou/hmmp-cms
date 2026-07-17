package com.hmmp.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.annotation.Excel;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 审稿周期统计明细（editor_audit 联查 editor_manuscript / publisher_article / publisher_column）
 * 按审稿记录（人×轮次）统计送审至审回的周期天数
 *
 * @author hmmp
 */
public class StatReviewCycle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审稿记录ID */
    private Long auditId;

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

    /** 审稿人 */
    @Excel(name = "审稿人")
    private String reviewerName;

    /** 栏目名称 */
    @Excel(name = "栏目名称")
    private String columnName;

    /** 审稿阶段（轮次） */
    @Excel(name = "审稿阶段")
    private Integer reviewRound;

    /** 投稿日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投稿日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /** 送审日期（邀请时间） */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "送审日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inviteTime;

    /** 审回日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审回日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 审稿结论 */
    @Excel(name = "审稿结论", readConverterExp = "01=建议录用,02=建议修改后录用,03=建议退稿")
    private String auditResult;

    /** 审稿周期（天） */
    @Excel(name = "审稿周期(天)")
    private Integer reviewCycleDays;

    /** 稿件状态（筛选） */
    private String status;

    public Long getAuditId()
    {
        return auditId;
    }

    public void setAuditId(Long auditId)
    {
        this.auditId = auditId;
    }

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

    public String getReviewerName()
    {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public Integer getReviewRound()
    {
        return reviewRound;
    }

    public void setReviewRound(Integer reviewRound)
    {
        this.reviewRound = reviewRound;
    }

    public Date getReceiveTime()
    {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }

    public Date getInviteTime()
    {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime)
    {
        this.inviteTime = inviteTime;
    }

    public Date getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public String getAuditResult()
    {
        return auditResult;
    }

    public void setAuditResult(String auditResult)
    {
        this.auditResult = auditResult;
    }

    public Integer getReviewCycleDays()
    {
        return reviewCycleDays;
    }

    public void setReviewCycleDays(Integer reviewCycleDays)
    {
        this.reviewCycleDays = reviewCycleDays;
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
