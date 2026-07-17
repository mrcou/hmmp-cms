package com.hmmp.system.domain.publisher;

import java.util.Date;
import com.hmmp.common.annotation.Excel;
import com.hmmp.common.annotation.Excel.ColumnType;

/**
 * 过刊批量导入 Excel 行（表头与样表一一对应）
 *
 * @author hmmp
 */
public class PublisherArticleBatchImport
{
    @Excel(name = "杂志编号", sort = 1)
    private String journalCode;

    @Excel(name = "稿件编号", sort = 2)
    private String fileNo;

    @Excel(name = "中文标题", sort = 3)
    private String title;

    @Excel(name = "英文标题", sort = 4)
    private String titleEn;

    @Excel(name = "中文摘要", sort = 5)
    private String abstractText;

    @Excel(name = "英文摘要", sort = 6)
    private String abstractEn;

    @Excel(name = "中文关键词", sort = 7)
    private String keywords;

    @Excel(name = "英文关键词", sort = 8)
    private String keywordsEn;

    @Excel(name = "基金中文名", sort = 9)
    private String fundZh;

    @Excel(name = "基金英文名", sort = 10)
    private String fundEn;

    @Excel(name = "起始页码", sort = 11, cellType = ColumnType.NUMERIC)
    private Integer pageStart;

    @Excel(name = "截止页码", sort = 12, cellType = ColumnType.NUMERIC)
    private Integer pageEnd;

    @Excel(name = "投稿时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss", sort = 13)
    private Date submitTime;

    @Excel(name = "最后修改时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss", sort = 14)
    private Date lastModifyTime;

    @Excel(name = "发布时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss", sort = 15)
    private Date publishTime;

    @Excel(name = "年", sort = 16, cellType = ColumnType.NUMERIC)
    private Integer yearNo;

    @Excel(name = "卷", sort = 17, cellType = ColumnType.NUMERIC)
    private Integer volume;

    @Excel(name = "期", sort = 18, cellType = ColumnType.NUMERIC)
    private Integer period;

    @Excel(name = "栏目编号", sort = 19)
    private String columnCode;

    @Excel(name = "栏目名称", sort = 20)
    private String columnName;

    @Excel(name = "参考文献", sort = 21)
    private String referenceText;

    @Excel(name = "引用文本", sort = 22)
    private String citationText;

    @Excel(name = "所有作者中文名", sort = 23)
    private String authorNames;

    @Excel(name = "所有作者英文名", sort = 24)
    private String authorNamesEn;

    @Excel(name = "所有作者单位中文名", sort = 25)
    private String authorOrgZh;

    @Excel(name = "所有作者单位英文名", sort = 26)
    private String authorOrgEn;

    @Excel(name = "DOI", sort = 27)
    private String doi;

    @Excel(name = "浏览次数", sort = 28, cellType = ColumnType.NUMERIC)
    private Integer viewCount;

    @Excel(name = "下载次数", sort = 29, cellType = ColumnType.NUMERIC)
    private Integer downloadCount;

    @Excel(name = "HTML浏览次数", sort = 30, cellType = ColumnType.NUMERIC)
    private Integer htmlViewCount;

    @Excel(name = "被引次数", sort = 31, cellType = ColumnType.NUMERIC)
    private Integer citedCount;

    @Excel(name = "知网引用次数", sort = 32, cellType = ColumnType.NUMERIC)
    private Integer cnkiCitedCount;

    @Excel(name = "网址", sort = 33)
    private String articleUrl;

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

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Integer getYearNo()
    {
        return yearNo;
    }

    public void setYearNo(Integer yearNo)
    {
        this.yearNo = yearNo;
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

    public String getColumnCode()
    {
        return columnCode;
    }

    public void setColumnCode(String columnCode)
    {
        this.columnCode = columnCode;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
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

    public Integer getCnkiCitedCount()
    {
        return cnkiCitedCount;
    }

    public void setCnkiCitedCount(Integer cnkiCitedCount)
    {
        this.cnkiCitedCount = cnkiCitedCount;
    }

    public String getArticleUrl()
    {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl)
    {
        this.articleUrl = articleUrl;
    }
}
