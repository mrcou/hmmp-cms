package com.hmmp.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 组稿稿件关联 editor_group_manuscript
 * 
 * @author hmmp
 */
public class EditorGroupManuscript implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 组稿ID */
    private Long groupId;

    /** 稿件ID */
    private Long manuscriptId;

    /** 排序号 */
    private Integer sortNo;

    /** 页面位置 */
    private String pagePosition;

    /** 字数 */
    private Integer wordCount;

    /** 页数 */
    private BigDecimal pageCount;

    /** 创建时间 */
    private Date createTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
    }

    public Integer getSortNo()
    {
        return sortNo;
    }

    public void setSortNo(Integer sortNo)
    {
        this.sortNo = sortNo;
    }

    public String getPagePosition()
    {
        return pagePosition;
    }

    public void setPagePosition(String pagePosition)
    {
        this.pagePosition = pagePosition;
    }

    public Integer getWordCount()
    {
        return wordCount;
    }

    public void setWordCount(Integer wordCount)
    {
        this.wordCount = wordCount;
    }

    public BigDecimal getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(BigDecimal pageCount)
    {
        this.pageCount = pageCount;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupId", getGroupId())
            .append("manuscriptId", getManuscriptId())
            .append("sortNo", getSortNo())
            .append("pagePosition", getPagePosition())
            .append("wordCount", getWordCount())
            .append("pageCount", getPageCount())
            .append("createTime", getCreateTime())
            .toString();
    }
}