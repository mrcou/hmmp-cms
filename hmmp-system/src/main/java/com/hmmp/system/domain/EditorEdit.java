package com.hmmp.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 编辑加工实体 editor_edit
 * 
 * @author hmmp
 */
public class EditorEdit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编辑加工ID */
    private Long editId;

    /** 稿件ID */
    private Long manuscriptId;

    /** 编辑ID */
    private Long editorId;

    /** 编辑姓名 */
    private String editorName;

    /** 编辑状态 */
    private String editStatus;

    /** 编辑开始时间 */
    private Date editStartTime;

    /** 编辑结束时间 */
    private Date editEndTime;

    /** 编辑内容 */
    private String editContent;

    /** 编辑建议 */
    private String editSuggestion;

    /** 编辑前字数 */
    private Integer wordCountBefore;

    /** 编辑后字数 */
    private Integer wordCountAfter;

    /** 版本号 */
    private Integer version;

    public Long getEditId()
    {
        return editId;
    }

    public void setEditId(Long editId)
    {
        this.editId = editId;
    }

    public Long getManuscriptId()
    {
        return manuscriptId;
    }

    public void setManuscriptId(Long manuscriptId)
    {
        this.manuscriptId = manuscriptId;
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

    public String getEditStatus()
    {
        return editStatus;
    }

    public void setEditStatus(String editStatus)
    {
        this.editStatus = editStatus;
    }

    public Date getEditStartTime()
    {
        return editStartTime;
    }

    public void setEditStartTime(Date editStartTime)
    {
        this.editStartTime = editStartTime;
    }

    public Date getEditEndTime()
    {
        return editEndTime;
    }

    public void setEditEndTime(Date editEndTime)
    {
        this.editEndTime = editEndTime;
    }

    public String getEditContent()
    {
        return editContent;
    }

    public void setEditContent(String editContent)
    {
        this.editContent = editContent;
    }

    public String getEditSuggestion()
    {
        return editSuggestion;
    }

    public void setEditSuggestion(String editSuggestion)
    {
        this.editSuggestion = editSuggestion;
    }

    public Integer getWordCountBefore()
    {
        return wordCountBefore;
    }

    public void setWordCountBefore(Integer wordCountBefore)
    {
        this.wordCountBefore = wordCountBefore;
    }

    public Integer getWordCountAfter()
    {
        return wordCountAfter;
    }

    public void setWordCountAfter(Integer wordCountAfter)
    {
        this.wordCountAfter = wordCountAfter;
    }

    public Integer getVersion()
    {
        return version;
    }

    public void setVersion(Integer version)
    {
        this.version = version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("editId", getEditId())
            .append("manuscriptId", getManuscriptId())
            .append("editorId", getEditorId())
            .append("editorName", getEditorName())
            .append("editStatus", getEditStatus())
            .append("editStartTime", getEditStartTime())
            .append("editEndTime", getEditEndTime())
            .append("editContent", getEditContent())
            .append("editSuggestion", getEditSuggestion())
            .append("wordCountBefore", getWordCountBefore())
            .append("wordCountAfter", getWordCountAfter())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}