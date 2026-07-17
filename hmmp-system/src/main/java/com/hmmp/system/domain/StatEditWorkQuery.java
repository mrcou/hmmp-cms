package com.hmmp.system.domain;

/**
 * 责编工作量统计查询参数（beginTime / endTime 已规范化为 yyyy-MM-dd）
 *
 * @author hmmp
 */
public class StatEditWorkQuery
{
    /** 起始日期 yyyy-MM-dd */
    private String beginTime;

    /** 截止日期 yyyy-MM-dd */
    private String endTime;

    /**
     * 时间筛选字段：receiveTime / assignTime / reviewStartTime / conclusionTime / publishTime
     * 默认 receiveTime（投稿日期）
     */
    private String dateField;

    public String getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getDateField()
    {
        return dateField;
    }

    public void setDateField(String dateField)
    {
        this.dateField = dateField;
    }
}
