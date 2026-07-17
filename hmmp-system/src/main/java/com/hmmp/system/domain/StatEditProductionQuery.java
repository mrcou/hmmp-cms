package com.hmmp.system.domain;

/**
 * 责编加工工作量统计查询参数（beginTime / endTime 已规范化为 yyyy-MM-dd）
 *
 * @author hmmp
 */
public class StatEditProductionQuery
{
    /** 起始日期 yyyy-MM-dd */
    private String beginTime;

    /** 截止日期 yyyy-MM-dd */
    private String endTime;

    /**
     * 时间筛选字段：editEndTime（完成日期）/ editStartTime（选件日期）/ createTime（创建日期）
     * 默认 editEndTime
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
