package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 文章信息对象 publisher_article
 * 
 * @author hmmp
 */
public class PublisherArticle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文章ID */
    private Long articleId;

    /** 关联稿件ID */
    private Long manuscriptId;

    /** 关联刊期ID */
    private Long issueId;

    /** 关联栏目ID */
    private Long columnId;

    /** 标题 */
    private String title;

    /** 副标题 */
    private String subtitle;

    /** 作者姓名 */
    private String authorNames;

    /** 摘要 */
    private String abstractText;

    /** 关键词 */
    private String keywords;

    /** 正文内容 */
    private String content;

    /** 起始页码 */
    private Integer pageStart;

    /** 结束页码 */
    private Integer pageEnd;

    /** 字数 */
    private Integer wordCount;

    /** 状态 */
    private String status;

    /** 发布时间 */
    private Date publishTime;

    /** 浏览次数 */
    private Integer viewCount;

    /** 知网标题 */
    private String cnkiTitle;

    /** 知网匹配状态 */
    private String cnkiMatchStatus;

    public Long getArticleId()
    {
        return articleId;
    }

    public void setArticleId(Long articleId)
    {
        this.articleId = articleId;
    }

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
    }

    public Long getIssueId()
    {
        return issueId;
    }

    public void setIssueId(Long issueId)
    {
        this.issueId = issueId;
    }

    public Long getColumnId()
    {
        return columnId;
    }

    public void setColumnId(Long columnId)
    {
        this.columnId = columnId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getAuthorNames()
    {
        return authorNames;
    }

    public void setAuthorNames(String authorNames)
    {
        this.authorNames = authorNames;
    }

    public String getAbstractText()
    {
        return abstractText;
    }

    public void setAbstractText(String abstractText)
    {
        this.abstractText = abstractText;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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

    public Integer getWordCount()
    {
        return wordCount;
    }

    public void setWordCount(Integer wordCount)
    {
        this.wordCount = wordCount;
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

    public Integer getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(Integer viewCount)
    {
        this.viewCount = viewCount;
    }

    public String getCnkiTitle()
    {
        return cnkiTitle;
    }

    public void setCnkiTitle(String cnkiTitle)
    {
        this.cnkiTitle = cnkiTitle;
    }

    public String getCnkiMatchStatus()
    {
        return cnkiMatchStatus;
    }

    public void setCnkiMatchStatus(String cnkiMatchStatus)
    {
        this.cnkiMatchStatus = cnkiMatchStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("articleId", getArticleId())
            .append("manuscriptId", getManuscriptId())
            .append("issueId", getIssueId())
            .append("columnId", getColumnId())
            .append("title", getTitle())
            .append("subtitle", getSubtitle())
            .append("authorNames", getAuthorNames())
            .append("abstractText", getAbstractText())
            .append("keywords", getKeywords())
            .append("content", getContent())
            .append("pageStart", getPageStart())
            .append("pageEnd", getPageEnd())
            .append("wordCount", getWordCount())
            .append("status", getStatus())
            .append("publishTime", getPublishTime())
            .append("viewCount", getViewCount())
            .append("cnkiTitle", getCnkiTitle())
            .append("cnkiMatchStatus", getCnkiMatchStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}