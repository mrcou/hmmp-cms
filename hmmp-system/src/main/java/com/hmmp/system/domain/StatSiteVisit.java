package com.hmmp.system.domain;

import com.hmmp.common.annotation.Excel;

/**
 * 网站访问量按年汇总行
 *
 * @author hmmp
 */
public class StatSiteVisit
{
    /** 访问年份 */
    @Excel(name = "年份")
    private Integer visitYear;

    /** (1) 访问量 PV */
    @Excel(name = "访问量")
    private Long pvCount = 0L;

    /** (2) 独立访客 UV（按 IP） */
    @Excel(name = "独立访客")
    private Long uvCount = 0L;

    /** (3) 登录访问量 */
    @Excel(name = "登录访问量")
    private Long loginCount = 0L;

    /** (3) 登录访问占比 % */
    @Excel(name = "登录访问占比(%)")
    private Double loginRate = 0D;

    /** (4) 匿名访问量 */
    @Excel(name = "匿名访问量")
    private Long anonCount = 0L;

    /** (4) 匿名访问占比 % */
    @Excel(name = "匿名访问占比(%)")
    private Double anonRate = 0D;

    /** (5) 访问页面种类数 */
    @Excel(name = "页面种类")
    private Long pageCount = 0L;

    /** (6) 平均停留时长（秒） */
    @Excel(name = "平均停留(秒)")
    private Double avgStayTime = 0D;

    /** (6) 最小停留时长（秒） */
    @Excel(name = "最小停留(秒)")
    private Integer minStayTime = 0;

    /** (6) 最大停留时长（秒） */
    @Excel(name = "最大停留(秒)")
    private Integer maxStayTime = 0;

    public Integer getVisitYear()
    {
        return visitYear;
    }

    public void setVisitYear(Integer visitYear)
    {
        this.visitYear = visitYear;
    }

    public Long getPvCount()
    {
        return pvCount;
    }

    public void setPvCount(Long pvCount)
    {
        this.pvCount = pvCount;
    }

    public Long getUvCount()
    {
        return uvCount;
    }

    public void setUvCount(Long uvCount)
    {
        this.uvCount = uvCount;
    }

    public Long getLoginCount()
    {
        return loginCount;
    }

    public void setLoginCount(Long loginCount)
    {
        this.loginCount = loginCount;
    }

    public Double getLoginRate()
    {
        return loginRate;
    }

    public void setLoginRate(Double loginRate)
    {
        this.loginRate = loginRate;
    }

    public Long getAnonCount()
    {
        return anonCount;
    }

    public void setAnonCount(Long anonCount)
    {
        this.anonCount = anonCount;
    }

    public Double getAnonRate()
    {
        return anonRate;
    }

    public void setAnonRate(Double anonRate)
    {
        this.anonRate = anonRate;
    }

    public Long getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(Long pageCount)
    {
        this.pageCount = pageCount;
    }

    public Double getAvgStayTime()
    {
        return avgStayTime;
    }

    public void setAvgStayTime(Double avgStayTime)
    {
        this.avgStayTime = avgStayTime;
    }

    public Integer getMinStayTime()
    {
        return minStayTime;
    }

    public void setMinStayTime(Integer minStayTime)
    {
        this.minStayTime = minStayTime;
    }

    public Integer getMaxStayTime()
    {
        return maxStayTime;
    }

    public void setMaxStayTime(Integer maxStayTime)
    {
        this.maxStayTime = maxStayTime;
    }
}
