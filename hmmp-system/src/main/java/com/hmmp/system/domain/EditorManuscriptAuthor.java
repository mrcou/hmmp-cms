package com.hmmp.system.domain;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 稿件作者关联 editor_manuscript_author
 * 
 * @author hmmp
 */
public class EditorManuscriptAuthor implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 稿件ID */
    private Long manuscriptId;

    /** 作者用户ID */
    private Long authorId;

    /** 作者姓名 */
    private String authorName;

    /** 排序号 */
    private Integer authorSort;

    /** 是否通讯作者（0否 1是） */
    private String isCorresponding;

    /** 单位 */
    private String affiliation;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

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

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
    }

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public Integer getAuthorSort()
    {
        return authorSort;
    }

    public void setAuthorSort(Integer authorSort)
    {
        this.authorSort = authorSort;
    }

    public String getIsCorresponding()
    {
        return isCorresponding;
    }

    public void setIsCorresponding(String isCorresponding)
    {
        this.isCorresponding = isCorresponding;
    }

    public String getAffiliation()
    {
        return affiliation;
    }

    public void setAffiliation(String affiliation)
    {
        this.affiliation = affiliation;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
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
            .append("manuscriptId", getManuscriptId())
            .append("authorId", getAuthorId())
            .append("authorName", getAuthorName())
            .append("authorSort", getAuthorSort())
            .append("isCorresponding", getIsCorresponding())
            .append("affiliation", getAffiliation())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("createTime", getCreateTime())
            .toString();
    }
}