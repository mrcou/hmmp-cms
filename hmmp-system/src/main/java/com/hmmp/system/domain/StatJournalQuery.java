package com.hmmp.system.domain;

/**
 * 期刊统计查询参数（beginTime / endTime 已规范化为 yyyy-MM-dd）
 *
 * @author hmmp
 */
public class StatJournalQuery
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
