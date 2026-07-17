package com.hmmp.system.domain;

import com.hmmp.common.annotation.Excel;

/**
 * 责编加工工作量统计行（按责任编辑分组，按加工阶段统计篇数与字数）
 *
 * @author hmmp
 */
public class StatEditProduction
{
    /** 责任编辑ID */
    private Long editorId;

    /** 责任编辑姓名 */
    @Excel(name = "责任编辑")
    private String editorName;

    /** 编辑加工-未完成（篇） */
    @Excel(name = "编辑加工-未完成(篇)")
    private Long editUnfinishedCount;

    /** 编辑加工-已完成（篇） */
    @Excel(name = "编辑加工-已完成(篇)")
    private Long editFinishedCount;

    /** 编辑加工-未完成（字） */
    @Excel(name = "编辑加工-未完成(字)")
    private Long editUnfinishedWords;

    /** 编辑加工-已完成（字） */
    @Excel(name = "编辑加工-已完成(字)")
    private Long editFinishedWords;

    /** 排版-未完成（篇） */
    @Excel(name = "排版-未完成(篇)")
    private Long layoutUnfinishedCount;

    /** 排版-已完成（篇） */
    @Excel(name = "排版-已完成(篇)")
    private Long layoutFinishedCount;

    /** 排版-未完成（字） */
    @Excel(name = "排版-未完成(字)")
    private Long layoutUnfinishedWords;

    /** 排版-已完成（字） */
    @Excel(name = "排版-已完成(字)")
    private Long layoutFinishedWords;

    /** 校对-未完成（篇） */
    @Excel(name = "校对-未完成(篇)")
    private Long proofUnfinishedCount;

    /** 校对-已完成（篇） */
    @Excel(name = "校对-已完成(篇)")
    private Long proofFinishedCount;

    /** 校对-未完成（字） */
    @Excel(name = "校对-未完成(字)")
    private Long proofUnfinishedWords;

    /** 校对-已完成（字） */
    @Excel(name = "校对-已完成(字)")
    private Long proofFinishedWords;

    /** 定版确认-未完成（篇） */
    @Excel(name = "定版确认-未完成(篇)")
    private Long finalizeUnfinishedCount;

    /** 定版确认-已完成（篇） */
    @Excel(name = "定版确认-已完成(篇)")
    private Long finalizeFinishedCount;

    /** 定版确认-未完成（字） */
    @Excel(name = "定版确认-未完成(字)")
    private Long finalizeUnfinishedWords;

    /** 定版确认-已完成（字） */
    @Excel(name = "定版确认-已完成(字)")
    private Long finalizeFinishedWords;

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

    public Long getEditUnfinishedCount()
    {
        return editUnfinishedCount;
    }

    public void setEditUnfinishedCount(Long editUnfinishedCount)
    {
        this.editUnfinishedCount = editUnfinishedCount;
    }

    public Long getEditFinishedCount()
    {
        return editFinishedCount;
    }

    public void setEditFinishedCount(Long editFinishedCount)
    {
        this.editFinishedCount = editFinishedCount;
    }

    public Long getEditUnfinishedWords()
    {
        return editUnfinishedWords;
    }

    public void setEditUnfinishedWords(Long editUnfinishedWords)
    {
        this.editUnfinishedWords = editUnfinishedWords;
    }

    public Long getEditFinishedWords()
    {
        return editFinishedWords;
    }

    public void setEditFinishedWords(Long editFinishedWords)
    {
        this.editFinishedWords = editFinishedWords;
    }

    public Long getLayoutUnfinishedCount()
    {
        return layoutUnfinishedCount;
    }

    public void setLayoutUnfinishedCount(Long layoutUnfinishedCount)
    {
        this.layoutUnfinishedCount = layoutUnfinishedCount;
    }

    public Long getLayoutFinishedCount()
    {
        return layoutFinishedCount;
    }

    public void setLayoutFinishedCount(Long layoutFinishedCount)
    {
        this.layoutFinishedCount = layoutFinishedCount;
    }

    public Long getLayoutUnfinishedWords()
    {
        return layoutUnfinishedWords;
    }

    public void setLayoutUnfinishedWords(Long layoutUnfinishedWords)
    {
        this.layoutUnfinishedWords = layoutUnfinishedWords;
    }

    public Long getLayoutFinishedWords()
    {
        return layoutFinishedWords;
    }

    public void setLayoutFinishedWords(Long layoutFinishedWords)
    {
        this.layoutFinishedWords = layoutFinishedWords;
    }

    public Long getProofUnfinishedCount()
    {
        return proofUnfinishedCount;
    }

    public void setProofUnfinishedCount(Long proofUnfinishedCount)
    {
        this.proofUnfinishedCount = proofUnfinishedCount;
    }

    public Long getProofFinishedCount()
    {
        return proofFinishedCount;
    }

    public void setProofFinishedCount(Long proofFinishedCount)
    {
        this.proofFinishedCount = proofFinishedCount;
    }

    public Long getProofUnfinishedWords()
    {
        return proofUnfinishedWords;
    }

    public void setProofUnfinishedWords(Long proofUnfinishedWords)
    {
        this.proofUnfinishedWords = proofUnfinishedWords;
    }

    public Long getProofFinishedWords()
    {
        return proofFinishedWords;
    }

    public void setProofFinishedWords(Long proofFinishedWords)
    {
        this.proofFinishedWords = proofFinishedWords;
    }

    public Long getFinalizeUnfinishedCount()
    {
        return finalizeUnfinishedCount;
    }

    public void setFinalizeUnfinishedCount(Long finalizeUnfinishedCount)
    {
        this.finalizeUnfinishedCount = finalizeUnfinishedCount;
    }

    public Long getFinalizeFinishedCount()
    {
        return finalizeFinishedCount;
    }

    public void setFinalizeFinishedCount(Long finalizeFinishedCount)
    {
        this.finalizeFinishedCount = finalizeFinishedCount;
    }

    public Long getFinalizeUnfinishedWords()
    {
        return finalizeUnfinishedWords;
    }

    public void setFinalizeUnfinishedWords(Long finalizeUnfinishedWords)
    {
        this.finalizeUnfinishedWords = finalizeUnfinishedWords;
    }

    public Long getFinalizeFinishedWords()
    {
        return finalizeFinishedWords;
    }

    public void setFinalizeFinishedWords(Long finalizeFinishedWords)
    {
        this.finalizeFinishedWords = finalizeFinishedWords;
    }
}
