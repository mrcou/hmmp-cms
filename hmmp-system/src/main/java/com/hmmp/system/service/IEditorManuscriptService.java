package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorManuscript;

/**
 * 稿件管理 业务层
 * 
 * @author hmmp
 */
public interface IEditorManuscriptService {

    /**
     * 查询稿件列表
     */
    public List<EditorManuscript> selectManuscriptList(EditorManuscript manuscript);

    /**
     * 根据稿件ID查询稿件
     */
    public EditorManuscript selectManuscriptById(Long manuscriptId);

    /**
     * 根据稿件编号查询稿件
     */
    public EditorManuscript selectManuscriptByFileNo(String fileNo);

    /**
     * 新增稿件（收稿登记）
     */
    public int insertManuscript(EditorManuscript manuscript);

    /**
     * 修改稿件
     */
    public int updateManuscript(EditorManuscript manuscript);

    /**
     * 删除稿件（逻辑删除）
     */
    public int deleteManuscriptById(Long manuscriptId);

    /**
     * 批量删除稿件
     */
    public int deleteManuscriptByIds(Long[] manuscriptIds);

    /**
     * 更新稿件状态
     */
    public int updateManuscriptStatus(EditorManuscript manuscript);

    /**
     * 分配责任编辑
     */
    public int assignEditor(EditorManuscript manuscript);

    /**
     * 送审
     */
    public int sendForReview(EditorManuscript manuscript);

    /**
     * 退修
     */
    public int revise(EditorManuscript manuscript);

    /**
     * 修回确认
     */
    public int confirmRevision(EditorManuscript manuscript);

    /**
     * 录用
     */
    public int adopt(EditorManuscript manuscript);

    /**
     * 退稿
     */
    public int reject(EditorManuscript manuscript);

    /**
     * 发表
     */
    public int publish(EditorManuscript manuscript);

    /**
     * 归档
     */
    public int archive(EditorManuscript manuscript);
}