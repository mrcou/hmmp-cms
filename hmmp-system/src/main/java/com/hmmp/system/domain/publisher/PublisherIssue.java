package com.hmmp.system.domain.publisher;

import java.math.BigDecimal;
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

    /** 杂志编号 */
    private String journalCode;

    /** 年份 */
    private Integer year;

    /** 卷 */
    private Integer volume;

    /** 期数/刊期（旧系统允许数字或字符，如增刊） */
    private String period;

    /** 刊期名称 */
    private String issueName;

    /** 中文名 */
    private String nameCn;

    /** 英文名 */
    private String nameEn;

    /** 顺序 */
    private Integer sequence;

    /** 专辑中文名 */
    private String specialName;

    /** 专辑英文名 */
    private String specialEnName;

    /** 重点(中文) */
    private String important;

    /** 重点(英文) */
    private String enImportant;

    /** 封面(小图) */
    private String imageUrl;

    /** 封面(大图) */
    private String largerCoverUrl;

    /** 目录文件 */
    private String contentsFile;

    /** 封面说明(中文) */
    private String cnImageExplain;

    /** 封面说明(英文) */
    private String enImageExplain;

    /** 是否收费(0否 1是) */
    private String requestMoney;

    /** 是否免费(0否 1是) */
    private String isFree;

    /** 是否最新一期文章(0否 1是) */
    private String isLatest;

    /** 会议ID */
    private Long meetingId;

    /** 订阅发送状态(0未发送 1已发送) */
    private String subscribeStatus;

    /** DOI注册状态(0未注册 1已注册) */
    private String doiStatus;

    /** CSTR注册状态(0未注册 1已注册) */
    private String cstrStatus;

    /** 百度发布状态(0未发送 1已发送) */
    private String baiduStatus;

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

    /** 印刷费 */
    private BigDecimal printMoney;

    /** 发行费 */
    private BigDecimal publishMoney;

    /** 邮费 */
    private BigDecimal postMoney;

    /** 起始页码 */
    private String startPage;

    /** 截止页码 */
    private String endPage;

    /** 栏目信息（JSON） */
    private String columnsInfo;

    /** 杂志名称（查询用，匹配中文名） */
    private String magazineName;

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

    public String getJournalCode()
    {
        return journalCode;
    }

    public void setJournalCode(String journalCode)
    {
        this.journalCode = journalCode;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getVolume()
    {
        return volume;
    }

    public void setVolume(Integer volume)
    {
        this.volume = volume;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
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

    public String getNameCn()
    {
        return nameCn;
    }

    public void setNameCn(String nameCn)
    {
        this.nameCn = nameCn;
    }

    public String getNameEn()
    {
        return nameEn;
    }

    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence(Integer sequence)
    {
        this.sequence = sequence;
    }

    public String getSpecialName()
    {
        return specialName;
    }

    public void setSpecialName(String specialName)
    {
        this.specialName = specialName;
    }

    public String getSpecialEnName()
    {
        return specialEnName;
    }

    public void setSpecialEnName(String specialEnName)
    {
        this.specialEnName = specialEnName;
    }

    public String getImportant()
    {
        return important;
    }

    public void setImportant(String important)
    {
        this.important = important;
    }

    public String getEnImportant()
    {
        return enImportant;
    }

    public void setEnImportant(String enImportant)
    {
        this.enImportant = enImportant;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getLargerCoverUrl()
    {
        return largerCoverUrl;
    }

    public void setLargerCoverUrl(String largerCoverUrl)
    {
        this.largerCoverUrl = largerCoverUrl;
    }

    public String getContentsFile()
    {
        return contentsFile;
    }

    public void setContentsFile(String contentsFile)
    {
        this.contentsFile = contentsFile;
    }

    public String getCnImageExplain()
    {
        return cnImageExplain;
    }

    public void setCnImageExplain(String cnImageExplain)
    {
        this.cnImageExplain = cnImageExplain;
    }

    public String getEnImageExplain()
    {
        return enImageExplain;
    }

    public void setEnImageExplain(String enImageExplain)
    {
        this.enImageExplain = enImageExplain;
    }

    public String getRequestMoney()
    {
        return requestMoney;
    }

    public void setRequestMoney(String requestMoney)
    {
        this.requestMoney = requestMoney;
    }

    public String getIsFree()
    {
        return isFree;
    }

    public void setIsFree(String isFree)
    {
        this.isFree = isFree;
    }

    public String getIsLatest()
    {
        return isLatest;
    }

    public void setIsLatest(String isLatest)
    {
        this.isLatest = isLatest;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public String getSubscribeStatus()
    {
        return subscribeStatus;
    }

    public void setSubscribeStatus(String subscribeStatus)
    {
        this.subscribeStatus = subscribeStatus;
    }

    public String getDoiStatus()
    {
        return doiStatus;
    }

    public void setDoiStatus(String doiStatus)
    {
        this.doiStatus = doiStatus;
    }

    public String getCstrStatus()
    {
        return cstrStatus;
    }

    public void setCstrStatus(String cstrStatus)
    {
        this.cstrStatus = cstrStatus;
    }

    public String getBaiduStatus()
    {
        return baiduStatus;
    }

    public void setBaiduStatus(String baiduStatus)
    {
        this.baiduStatus = baiduStatus;
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

    public BigDecimal getPrintMoney()
    {
        return printMoney;
    }

    public void setPrintMoney(BigDecimal printMoney)
    {
        this.printMoney = printMoney;
    }

    public BigDecimal getPublishMoney()
    {
        return publishMoney;
    }

    public void setPublishMoney(BigDecimal publishMoney)
    {
        this.publishMoney = publishMoney;
    }

    public BigDecimal getPostMoney()
    {
        return postMoney;
    }

    public void setPostMoney(BigDecimal postMoney)
    {
        this.postMoney = postMoney;
    }

    public String getStartPage()
    {
        return startPage;
    }

    public void setStartPage(String startPage)
    {
        this.startPage = startPage;
    }

    public String getEndPage()
    {
        return endPage;
    }

    public void setEndPage(String endPage)
    {
        this.endPage = endPage;
    }

    public String getColumnsInfo()
    {
        return columnsInfo;
    }

    public void setColumnsInfo(String columnsInfo)
    {
        this.columnsInfo = columnsInfo;
    }

    public String getMagazineName()
    {
        return magazineName;
    }

    public void setMagazineName(String magazineName)
    {
        this.magazineName = magazineName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("issueId", getIssueId())
            .append("yearId", getYearId())
            .append("journalCode", getJournalCode())
            .append("year", getYear())
            .append("volume", getVolume())
            .append("period", getPeriod())
            .append("issueName", getIssueName())
            .append("nameCn", getNameCn())
            .append("nameEn", getNameEn())
            .append("sequence", getSequence())
            .append("specialName", getSpecialName())
            .append("specialEnName", getSpecialEnName())
            .append("important", getImportant())
            .append("enImportant", getEnImportant())
            .append("imageUrl", getImageUrl())
            .append("largerCoverUrl", getLargerCoverUrl())
            .append("contentsFile", getContentsFile())
            .append("cnImageExplain", getCnImageExplain())
            .append("enImageExplain", getEnImageExplain())
            .append("requestMoney", getRequestMoney())
            .append("isFree", getIsFree())
            .append("isLatest", getIsLatest())
            .append("meetingId", getMeetingId())
            .append("subscribeStatus", getSubscribeStatus())
            .append("doiStatus", getDoiStatus())
            .append("cstrStatus", getCstrStatus())
            .append("baiduStatus", getBaiduStatus())
            .append("publishDate", getPublishDate())
            .append("status", getStatus())
            .append("publishTime", getPublishTime())
            .append("articleCount", getArticleCount())
            .append("pageCount", getPageCount())
            .append("coverImage", getCoverImage())
            .append("printMoney", getPrintMoney())
            .append("publishMoney", getPublishMoney())
            .append("postMoney", getPostMoney())
            .append("startPage", getStartPage())
            .append("endPage", getEndPage())
            .append("columnsInfo", getColumnsInfo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
