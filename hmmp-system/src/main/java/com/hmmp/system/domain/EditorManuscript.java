package com.hmmp.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 稿件实体 editor_manuscript
 * 
 * @author hmmp
 */
public class EditorManuscript extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 稿件ID */
    private Long manuscriptId;

    /** 稿件编号 */
    private String fileNo;

    /** 标题 */
    private String title;

    /** 作者ID列表 */
    private String authorIds;

    /** 作者姓名列表 */
    private String authorNames;

    /** 通讯作者 */
    private String correspondingAuthor;

    /** 通讯作者邮箱 */
    private String correspondingEmail;

    /** 摘要 */
    private String abstractText;

    /** 关键词 */
    private String keywords;

    /** 稿件类型 */
    private String manuscriptType;

    /** 字数统计 */
    private Integer wordCount;

    /** 基金项目 */
    private String fundProject;

    /** 基金编号 */
    private String fundNo;

    /** 状态 */
    private String status;

    /** 收稿时间 */
    private Date receiveTime;

    /** 分配时间 */
    private Date assignTime;

    /** 审稿开始时间 */
    private Date reviewStartTime;

    /** 审稿结束时间 */
    private Date reviewEndTime;

    /** 审稿结论 */
    private String conclusion;

    /** 结论时间 */
    private Date conclusionTime;

    /** 编辑ID */
    private Long editorId;

    /** 编辑姓名 */
    private String editorName;

    /** 审核次数 */
    private Integer auditCount;

    /** 修订次数 */
    private Integer revisionCount;

    /** 是否删除 */
    private String isDeleted;

    /** 删除时间 */
    private Date deleteTime;

    /** 删除人 */
    private String deleteBy;

    /** 归档时间 */
    private Date archiveTime;

    /** 发表时间 */
    private Date publishTime;

    /** 期号ID */
    private Long issueId;

    /** 起始页码 */
    private Integer pageStart;

    /** 结束页码 */
    private Integer pageEnd;

    /** 文件路径 */
    private String filePath;

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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthorIds()
    {
        return authorIds;
    }

    public void setAuthorIds(String authorIds)
    {
        this.authorIds = authorIds;
    }

    public String getAuthorNames()
    {
        return authorNames;
    }

    public void setAuthorNames(String authorNames)
    {
        this.authorNames = authorNames;
    }

    public String getCorrespondingAuthor()
    {
        return correspondingAuthor;
    }

    public void setCorrespondingAuthor(String correspondingAuthor)
    {
        this.correspondingAuthor = correspondingAuthor;
    }

    public String getCorrespondingEmail()
    {
        return correspondingEmail;
    }

    public void setCorrespondingEmail(String correspondingEmail)
    {
        this.correspondingEmail = correspondingEmail;
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

    public String getManuscriptType()
    {
        return manuscriptType;
    }

    public void setManuscriptType(String manuscriptType)
    {
        this.manuscriptType = manuscriptType;
    }

    public Integer getWordCount()
    {
        return wordCount;
    }

    public void setWordCount(Integer wordCount)
    {
        this.wordCount = wordCount;
    }

    public String getFundProject()
    {
        return fundProject;
    }

    public void setFundProject(String fundProject)
    {
        this.fundProject = fundProject;
    }

    public String getFundNo()
    {
        return fundNo;
    }

    public void setFundNo(String fundNo)
    {
        this.fundNo = fundNo;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getReceiveTime()
    {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }

    public Date getAssignTime()
    {
        return assignTime;
    }

    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }

    public Date getReviewStartTime()
    {
        return reviewStartTime;
    }

    public void setReviewStartTime(Date reviewStartTime)
    {
        this.reviewStartTime = reviewStartTime;
    }

    public Date getReviewEndTime()
    {
        return reviewEndTime;
    }

    public void setReviewEndTime(Date reviewEndTime)
    {
        this.reviewEndTime = reviewEndTime;
    }

    public String getConclusion()
    {
        return conclusion;
    }

    public void setConclusion(String conclusion)
    {
        this.conclusion = conclusion;
    }

    public Date getConclusionTime()
    {
        return conclusionTime;
    }

    public void setConclusionTime(Date conclusionTime)
    {
        this.conclusionTime = conclusionTime;
    }

    public Long getEditorId()
    {
        return editorId;
    }

    public void setEditorId(Long editorId)
    {
        this.editorId = editorId;
    }

    public String getEditorName()
    {
        return editorName;
    }

    public void setEditorName(String editorName)
    {
        this.editorName = editorName;
    }

    public Integer getAuditCount()
    {
        return auditCount;
    }

    public void setAuditCount(Integer auditCount)
    {
        this.auditCount = auditCount;
    }

    public Integer getRevisionCount()
    {
        return revisionCount;
    }

    public void setRevisionCount(Integer revisionCount)
    {
        this.revisionCount = revisionCount;
    }

    public String getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getDeleteBy()
    {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy)
    {
        this.deleteBy = deleteBy;
    }

    public Date getArchiveTime()
    {
        return archiveTime;
    }

    public void setArchiveTime(Date archiveTime)
    {
        this.archiveTime = archiveTime;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Long getIssueId()
    {
        return issueId;
    }

    public void setIssueId(Long issueId)
    {
        this.issueId = issueId;
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

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("manuscriptId", getManuscriptId())
            .append("fileNo", getFileNo())
            .append("title", getTitle())
            .append("authorIds", getAuthorIds())
            .append("authorNames", getAuthorNames())
            .append("correspondingAuthor", getCorrespondingAuthor())
            .append("correspondingEmail", getCorrespondingEmail())
            .append("abstractText", getAbstractText())
            .append("keywords", getKeywords())
            .append("manuscriptType", getManuscriptType())
            .append("wordCount", getWordCount())
            .append("fundProject", getFundProject())
            .append("fundNo", getFundNo())
            .append("status", getStatus())
            .append("receiveTime", getReceiveTime())
            .append("assignTime", getAssignTime())
            .append("reviewStartTime", getReviewStartTime())
            .append("reviewEndTime", getReviewEndTime())
            .append("conclusion", getConclusion())
            .append("conclusionTime", getConclusionTime())
            .append("editorId", getEditorId())
            .append("editorName", getEditorName())
            .append("auditCount", getAuditCount())
            .append("revisionCount", getRevisionCount())
            .append("isDeleted", getIsDeleted())
            .append("deleteTime", getDeleteTime())
            .append("deleteBy", getDeleteBy())
            .append("archiveTime", getArchiveTime())
            .append("publishTime", getPublishTime())
            .append("issueId", getIssueId())
            .append("pageStart", getPageStart())
            .append("pageEnd", getPageEnd())
            .append("filePath", getFilePath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}