package com.hmmp.system.domain;

/**
 * 网站访问量统计查询参数（时间已规范为 yyyy-MM-dd）
 *
 * @author hmmp
 */
public class StatSiteVisitQuery
{
    /** 起始日期 yyyy-MM-dd */
    private String beginTime;

    /** 截止日期 yyyy-MM-dd */
    private String endTime;

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
}
