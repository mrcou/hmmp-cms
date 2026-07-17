package com.hmmp.system.domain;

import com.hmmp.common.annotation.Excel;

/**
 * 责编工作量统计行（按责任编辑分组，按稿件当前状态分列计数）
 *
 * @author hmmp
 */
public class StatEditWork
{
    /** 责任编辑ID，空表示未分配 */
    private Long editorId;

    /** 责任编辑姓名 */
    @Excel(name = "责任编辑姓名")
    private String editorName;

    /** 责任部门 */
    @Excel(name = "责任部门")
    private String deptName;

    /** 总件 */
    @Excel(name = "总件")
    private Long totalCount;

    /** 未送审（新收稿 / 新分配） */
    @Excel(name = "未送审")
    private Long notSentCount;

    /** 退修 */
    @Excel(name = "退修")
    private Long reviseCount;

    /** 已修回 */
    @Excel(name = "已修回")
    private Long resubmittedCount;

    /** 审稿中（送审中、暂无待审也无已审回，如刚送审或审次间隙） */
    @Excel(name = "审稿中")
    private Long reviewingCount;

    /** 已审回（送审中、审稿意见已全部返回） */
    @Excel(name = "已审回")
    private Long returnedCount;

    /** 未审回（送审中、仍有待审未返回） */
    @Excel(name = "未审回")
    private Long notReturnedCount;

    /** 采用 */
    @Excel(name = "采用")
    private Long adoptCount;

    /** 退稿 */
    @Excel(name = "退稿")
    private Long rejectCount;

    /** 发表（含已归档） */
    @Excel(name = "发表")
    private Long publishCount;

    public Long getEditorId()
    {
        return editorId;
    }

    public void setEditorId(Long editorId)
    {
        this.editorId = editorId;
    }

    public String getEditorName()
    {
        return editorName;
    }

    public void setEditorName(String editorName)
    {
        this.editorName = editorName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public Long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(Long totalCount)
    {
        this.totalCount = totalCount;
    }

    public Long getNotSentCount()
    {
        return notSentCount;
    }

    public void setNotSentCount(Long notSentCount)
    {
        this.notSentCount = notSentCount;
    }

    public Long getReviseCount()
    {
        return reviseCount;
    }

    public void setReviseCount(Long reviseCount)
    {
        this.reviseCount = reviseCount;
    }

    public Long getResubmittedCount()
    {
        return resubmittedCount;
    }

    public void setResubmittedCount(Long resubmittedCount)
    {
        this.resubmittedCount = resubmittedCount;
    }

    public Long getReviewingCount()
    {
        return reviewingCount;
    }

    public void setReviewingCount(Long reviewingCount)
    {
        this.reviewingCount = reviewingCount;
    }

    public Long getReturnedCount()
    {
        return returnedCount;
    }

    public void setReturnedCount(Long returnedCount)
    {
        this.returnedCount = returnedCount;
    }

    public Long getNotReturnedCount()
    {
        return notReturnedCount;
    }

    public void setNotReturnedCount(Long notReturnedCount)
    {
        this.notReturnedCount = notReturnedCount;
    }

    public Long getAdoptCount()
    {
        return adoptCount;
    }

    public void setAdoptCount(Long adoptCount)
    {
        this.adoptCount = adoptCount;
    }

    public Long getRejectCount()
    {
        return rejectCount;
    }

    public void setRejectCount(Long rejectCount)
    {
        this.rejectCount = rejectCount;
    }

    public Long getPublishCount()
    {
        return publishCount;
    }

    public void setPublishCount(Long publishCount)
    {
        this.publishCount = publishCount;
    }
}
