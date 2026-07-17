package com.hmmp.system.domain;

import com.hmmp.common.annotation.Excel;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 被引文章查询明细（publisher_article 联查 publisher_issue）
 *
 * @author hmmp
 */
public class StatReferencedArticle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文章ID */
    private Long articleId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 作者信息 */
    @Excel(name = "作者信息")
    private String authorNames;

    /** 年份 */
    @Excel(name = "年份")
    private Integer year;

    /** 刊期 */
    @Excel(name = "刊期")
    private String period;

    /** 起始页码 */
    @Excel(name = "起始页码")
    private Integer pageStart;

    /** 截止页码 */
    @Excel(name = "截止页码")
    private Integer pageEnd;

    /** 被引次数 */
    @Excel(name = "被引次数")
    private Integer citedCount;

    /** 自引次数 */
    @Excel(name = "自引次数")
    private Integer selfCitedCount;

    /** 他引次数 */
    @Excel(name = "他引次数")
    private Integer otherCitedCount;

    public Long getArticleId()
    {
        return articleId;
    }

    public void setArticleId(Long articleId)
    {
        this.articleId = articleId;
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

    public Integer getCitedCount()
    {
        return citedCount;
    }

    public void setCitedCount(Integer citedCount)
    {
        this.citedCount = citedCount;
    }

    public Integer getSelfCitedCount()
    {
        return selfCitedCount;
    }

    public void setSelfCitedCount(Integer selfCitedCount)
    {
        this.selfCitedCount = selfCitedCount;
    }

    public Integer getOtherCitedCount()
    {
        return otherCitedCount;
    }

    public void setOtherCitedCount(Integer otherCitedCount)
    {
        this.otherCitedCount = otherCitedCount;
    }
}
