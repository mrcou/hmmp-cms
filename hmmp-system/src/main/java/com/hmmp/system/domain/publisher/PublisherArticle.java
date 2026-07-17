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

    /** 杂志编号 */
    private String journalCode;

    /** 稿件编号 */
    private String fileNo;

    /** 标题 */
    private String title;

    /** 英文标题 */
    private String titleEn;

    /** 副标题 */
    private String subtitle;

    /** 作者姓名 */
    private String authorNames;

    /** 作者英文名 */
    private String authorNamesEn;

    /** 作者单位中文名 */
    private String authorOrgZh;

    /** 作者单位英文名 */
    private String authorOrgEn;

    /** DOI */
    private String doi;

    /** 摘要 */
    private String abstractText;

    /** 英文摘要 */
    private String abstractEn;

    /** 关键词 */
    private String keywords;

    /** 英文关键词 */
    private String keywordsEn;

    /** 基金中文名 */
    private String fundZh;

    /** 基金英文名 */
    private String fundEn;

    /** 正文内容 */
    private String content;

    /** 参考文献 */
    private String referenceText;

    /** 引用文本 */
    private String citationText;

    /** 起始页码 */
    private Integer pageStart;

    /** 结束页码 */
    private Integer pageEnd;

    /** 字数 */
    private Integer wordCount;

    /** 投稿时间 */
    private Date submitTime;

    /** 最后修改时间 */
    private Date lastModifyTime;

    /** 卷 */
    private Integer volume;

    /** 期 */
    private Integer period;

    /** 年 */
    private Integer yearNo;

    /** 状态 */
    private String status;

    /** 发布时间 */
    private Date publishTime;

    /** 浏览次数 */
    private Integer viewCount;

    /** 下载次数 */
    private Integer downloadCount;

    /** HTML浏览次数 */
    private Integer htmlViewCount;

    /** 被引次数 */
    private Integer citedCount;

    /** 自引次数 */
    private Integer selfCitedCount;

    /** 他引次数 */
    private Integer otherCitedCount;

    /** 知网引用次数 */
    private Integer cnkiCitedCount;

    /** 知网标题 */
    private String cnkiTitle;

    /** 知网匹配状态 */
    private String cnkiMatchStatus;

    /** 网址 */
    private String articleUrl;

    /** 全文PDF路径 */
    private String pdfPath;

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

    public String getJournalCode()
    {
        return journalCode;
    }

    public void setJournalCode(String journalCode)
    {
        this.journalCode = journalCode;
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

    public String getTitleEn()
    {
        return titleEn;
    }

    public void setTitleEn(String titleEn)
    {
        this.titleEn = titleEn;
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

    public String getAuthorNamesEn()
    {
        return authorNamesEn;
    }

    public void setAuthorNamesEn(String authorNamesEn)
    {
        this.authorNamesEn = authorNamesEn;
    }

    public String getAuthorOrgZh()
    {
        return authorOrgZh;
    }

    public void setAuthorOrgZh(String authorOrgZh)
    {
        this.authorOrgZh = authorOrgZh;
    }

    public String getAuthorOrgEn()
    {
        return authorOrgEn;
    }

    public void setAuthorOrgEn(String authorOrgEn)
    {
        this.authorOrgEn = authorOrgEn;
    }

    public String getDoi()
    {
        return doi;
    }

    public void setDoi(String doi)
    {
        this.doi = doi;
    }

    public String getAbstractText()
    {
        return abstractText;
    }

    public void setAbstractText(String abstractText)
    {
        this.abstractText = abstractText;
    }

    public String getAbstractEn()
    {
        return abstractEn;
    }

    public void setAbstractEn(String abstractEn)
    {
        this.abstractEn = abstractEn;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public String getKeywordsEn()
    {
        return keywordsEn;
    }

    public void setKeywordsEn(String keywordsEn)
    {
        this.keywordsEn = keywordsEn;
    }

    public String getFundZh()
    {
        return fundZh;
    }

    public void setFundZh(String fundZh)
    {
        this.fundZh = fundZh;
    }

    public String getFundEn()
    {
        return fundEn;
    }

    public void setFundEn(String fundEn)
    {
        this.fundEn = fundEn;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getReferenceText()
    {
        return referenceText;
    }

    public void setReferenceText(String referenceText)
    {
        this.referenceText = referenceText;
    }

    public String getCitationText()
    {
        return citationText;
    }

    public void setCitationText(String citationText)
    {
        this.citationText = citationText;
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

    public Date getSubmitTime()
    {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }

    public Date getLastModifyTime()
    {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime)
    {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getVolume()
    {
        return volume;
    }

    public void setVolume(Integer volume)
    {
        this.volume = volume;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public Integer getYearNo()
    {
        return yearNo;
    }

    public void setYearNo(Integer yearNo)
    {
        this.yearNo = yearNo;
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

    public Integer getDownloadCount()
    {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount)
    {
        this.downloadCount = downloadCount;
    }

    public Integer getHtmlViewCount()
    {
        return htmlViewCount;
    }

    public void setHtmlViewCount(Integer htmlViewCount)
    {
        this.htmlViewCount = htmlViewCount;
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

    public Integer getCnkiCitedCount()
    {
        return cnkiCitedCount;
    }

    public void setCnkiCitedCount(Integer cnkiCitedCount)
    {
        this.cnkiCitedCount = cnkiCitedCount;
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

    public String getArticleUrl()
    {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl)
    {
        this.articleUrl = articleUrl;
    }

    public String getPdfPath()
    {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath)
    {
        this.pdfPath = pdfPath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("articleId", getArticleId())
            .append("manuscriptId", getManuscriptId())
            .append("issueId", getIssueId())
            .append("columnId", getColumnId())
            .append("journalCode", getJournalCode())
            .append("fileNo", getFileNo())
            .append("title", getTitle())
            .append("titleEn", getTitleEn())
            .append("subtitle", getSubtitle())
            .append("authorNames", getAuthorNames())
            .append("authorNamesEn", getAuthorNamesEn())
            .append("doi", getDoi())
            .append("abstractText", getAbstractText())
            .append("keywords", getKeywords())
            .append("pageStart", getPageStart())
            .append("pageEnd", getPageEnd())
            .append("yearNo", getYearNo())
            .append("volume", getVolume())
            .append("period", getPeriod())
            .append("status", getStatus())
            .append("publishTime", getPublishTime())
            .append("viewCount", getViewCount())
            .append("downloadCount", getDownloadCount())
            .append("htmlViewCount", getHtmlViewCount())
            .append("citedCount", getCitedCount())
            .append("cnkiCitedCount", getCnkiCitedCount())
            .append("pdfPath", getPdfPath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
