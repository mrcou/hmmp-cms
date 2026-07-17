package com.hmmp.system.domain.publisher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.annotation.Excel;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 邮寄样刊登记 publisher_sample_post（联查 publisher_article / editor_manuscript）
 *
 * @author hmmp
 */
public class PublisherSamplePost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 样刊登记ID */
    private Long sampleId;

    /** 关联文章ID */
    private Long articleId;

    /** 关联稿件ID（非表字段） */
    private Long manuscriptId;

    /** 稿件编号（非表字段） */
    @Excel(name = "稿件编号")
    private String fileNo;

    /** 年份（非表字段） */
    @Excel(name = "年份")
    private Integer yearNo;

    /** 刊期（非表字段） */
    @Excel(name = "刊期")
    private Integer period;

    /** 投稿作者（非表字段） */
    @Excel(name = "投稿作者")
    private String submitAuthor;

    /** 第一作者（非表字段） */
    @Excel(name = "第一作者")
    private String firstAuthor;

    /** 通讯作者（非表字段） */
    private String correspondingAuthor;

    /** 第一作者地址（非表字段） */
    private String firstAuthorAddress;

    /** 第一作者单位（非表字段） */
    private String firstAuthorOrg;

    /** 第一作者邮编（非表字段） */
    private String firstAuthorZip;

    /** 第一作者电话（非表字段） */
    private String firstAuthorPhone;

    /** 通讯作者地址（非表字段） */
    private String correspondingAddress;

    /** 通讯作者单位（非表字段） */
    private String correspondingOrg;

    /** 通讯作者邮编（非表字段） */
    private String correspondingZip;

    /** 通讯作者电话（非表字段） */
    private String correspondingPhone;

    /** 投稿作者地址（非表字段） */
    private String submitAuthorAddress;

    /** 投稿作者单位（非表字段） */
    private String submitAuthorOrg;

    /** 投稿作者邮编（非表字段） */
    private String submitAuthorZip;

    /** 投稿作者电话（非表字段） */
    private String submitAuthorPhone;

    /** 收款人姓名 */
    @Excel(name = "收款人姓名")
    private String payeeName;

    /** 收款人地址 */
    @Excel(name = "收款人地址")
    private String payeeAddress;

    /** 收款人单位 */
    @Excel(name = "收款人单位")
    private String payeeOrg;

    /** 邮编 */
    @Excel(name = "邮编")
    private String payeeZip;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String payeePhone;

    /** 样刊册数 */
    @Excel(name = "样刊册数")
    private Integer sampleQty;

    /** 杂志编号（筛选，非表字段） */
    private String journalCode;

    /** 会议ID（筛选，非表字段） */
    private Long meetingId;

    /** 批量操作文章ID（非表字段） */
    private Long[] articleIds;

    public Long getSampleId()
    {
        return sampleId;
    }

    public void setSampleId(Long sampleId)
    {
        this.sampleId = sampleId;
    }

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

    public String getFileNo()
    {
        return fileNo;
    }

    public void setFileNo(String fileNo)
    {
        this.fileNo = fileNo;
    }

    public Integer getYearNo()
    {
        return yearNo;
    }

    public void setYearNo(Integer yearNo)
    {
        this.yearNo = yearNo;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public String getSubmitAuthor()
    {
        return submitAuthor;
    }

    public void setSubmitAuthor(String submitAuthor)
    {
        this.submitAuthor = submitAuthor;
    }

    public String getFirstAuthor()
    {
        return firstAuthor;
    }

    public void setFirstAuthor(String firstAuthor)
    {
        this.firstAuthor = firstAuthor;
    }

    public String getCorrespondingAuthor()
    {
        return correspondingAuthor;
    }

    public void setCorrespondingAuthor(String correspondingAuthor)
    {
        this.correspondingAuthor = correspondingAuthor;
    }

    public String getFirstAuthorAddress()
    {
        return firstAuthorAddress;
    }

    public void setFirstAuthorAddress(String firstAuthorAddress)
    {
        this.firstAuthorAddress = firstAuthorAddress;
    }

    public String getFirstAuthorOrg()
    {
        return firstAuthorOrg;
    }

    public void setFirstAuthorOrg(String firstAuthorOrg)
    {
        this.firstAuthorOrg = firstAuthorOrg;
    }

    public String getFirstAuthorZip()
    {
        return firstAuthorZip;
    }

    public void setFirstAuthorZip(String firstAuthorZip)
    {
        this.firstAuthorZip = firstAuthorZip;
    }

    public String getFirstAuthorPhone()
    {
        return firstAuthorPhone;
    }

    public void setFirstAuthorPhone(String firstAuthorPhone)
    {
        this.firstAuthorPhone = firstAuthorPhone;
    }

    public String getCorrespondingAddress()
    {
        return correspondingAddress;
    }

    public void setCorrespondingAddress(String correspondingAddress)
    {
        this.correspondingAddress = correspondingAddress;
    }

    public String getCorrespondingOrg()
    {
        return correspondingOrg;
    }

    public void setCorrespondingOrg(String correspondingOrg)
    {
        this.correspondingOrg = correspondingOrg;
    }

    public String getCorrespondingZip()
    {
        return correspondingZip;
    }

    public void setCorrespondingZip(String correspondingZip)
    {
        this.correspondingZip = correspondingZip;
    }

    public String getCorrespondingPhone()
    {
        return correspondingPhone;
    }

    public void setCorrespondingPhone(String correspondingPhone)
    {
        this.correspondingPhone = correspondingPhone;
    }

    public String getSubmitAuthorAddress()
    {
        return submitAuthorAddress;
    }

    public void setSubmitAuthorAddress(String submitAuthorAddress)
    {
        this.submitAuthorAddress = submitAuthorAddress;
    }

    public String getSubmitAuthorOrg()
    {
        return submitAuthorOrg;
    }

    public void setSubmitAuthorOrg(String submitAuthorOrg)
    {
        this.submitAuthorOrg = submitAuthorOrg;
    }

    public String getSubmitAuthorZip()
    {
        return submitAuthorZip;
    }

    public void setSubmitAuthorZip(String submitAuthorZip)
    {
        this.submitAuthorZip = submitAuthorZip;
    }

    public String getSubmitAuthorPhone()
    {
        return submitAuthorPhone;
    }

    public void setSubmitAuthorPhone(String submitAuthorPhone)
    {
        this.submitAuthorPhone = submitAuthorPhone;
    }

    public String getPayeeName()
    {
        return payeeName;
    }

    public void setPayeeName(String payeeName)
    {
        this.payeeName = payeeName;
    }

    public String getPayeeAddress()
    {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress)
    {
        this.payeeAddress = payeeAddress;
    }

    public String getPayeeOrg()
    {
        return payeeOrg;
    }

    public void setPayeeOrg(String payeeOrg)
    {
        this.payeeOrg = payeeOrg;
    }

    public String getPayeeZip()
    {
        return payeeZip;
    }

    public void setPayeeZip(String payeeZip)
    {
        this.payeeZip = payeeZip;
    }

    public String getPayeePhone()
    {
        return payeePhone;
    }

    public void setPayeePhone(String payeePhone)
    {
        this.payeePhone = payeePhone;
    }

    public Integer getSampleQty()
    {
        return sampleQty;
    }

    public void setSampleQty(Integer sampleQty)
    {
        this.sampleQty = sampleQty;
    }

    public String getJournalCode()
    {
        return journalCode;
    }

    public void setJournalCode(String journalCode)
    {
        this.journalCode = journalCode;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
    }

    public Long[] getArticleIds()
    {
        return articleIds;
    }

    public void setArticleIds(Long[] articleIds)
    {
        this.articleIds = articleIds;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("sampleId", getSampleId())
            .append("articleId", getArticleId())
            .append("fileNo", getFileNo())
            .append("yearNo", getYearNo())
            .append("period", getPeriod())
            .append("submitAuthor", getSubmitAuthor())
            .append("firstAuthor", getFirstAuthor())
            .append("payeeName", getPayeeName())
            .append("payeeAddress", getPayeeAddress())
            .append("payeeOrg", getPayeeOrg())
            .append("payeeZip", getPayeeZip())
            .append("payeePhone", getPayeePhone())
            .append("sampleQty", getSampleQty())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
