package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 期刊刊期对象 publisher_issue
 * 
 * @author hmmp
 */
public class PublisherIssue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 刊期ID */
    private Long issueId;

    /** 年份ID */
    private Long yearId;

    /** 年份 */
    private Integer year;

    /** 期数 */
    private Integer period;

    /** 刊期名称 */
    private String issueName;

    /** 出版日期 */
    private Date publishDate;

    /** 状态(0待发布 1已发布 2已归档) */
    private String status;

    /** 发布时间 */
    private Date publishTime;

    /** 文章数量 */
    private Integer articleCount;

    /** 总页数 */
    private Integer pageCount;

    /** 封面图片 */
    private String coverImage;

    public Long getIssueId()
    {
        return issueId;
    }

    public void setIssueId(Long issueId)
    {
        this.issueId = issueId;
    }

    public Long getYearId()
    {
        return yearId;
    }

    public void setYearId(Long yearId)
    {
        this.yearId = yearId;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public String getIssueName()
    {
        return issueName;
    }

    public void setIssueName(String issueName)
    {
        this.issueName = issueName;
    }

    public Date getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(Date publishDate)
    {
        this.publishDate = publishDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Integer getArticleCount()
    {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount)
    {
        this.articleCount = articleCount;
    }

    public Integer getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(Integer pageCount)
    {
        this.pageCount = pageCount;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("issueId", getIssueId())
            .append("yearId", getYearId())
            .append("year", getYear())
            .append("period", getPeriod())
            .append("issueName", getIssueName())
            .append("publishDate", getPublishDate())
            .append("status", getStatus())
            .append("publishTime", getPublishTime())
            .append("articleCount", getArticleCount())
            .append("pageCount", getPageCount())
            .append("coverImage", getCoverImage())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}