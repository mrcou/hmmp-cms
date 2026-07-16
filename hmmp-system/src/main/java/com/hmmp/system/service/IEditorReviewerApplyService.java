package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorReviewerApply;

/**
 * 审稿人申请/推荐 业务层
 * 
 * @author hmmp
 */
public interface IEditorReviewerApplyService {

    /**
     * 查询申请/推荐列表
     */
    public List<EditorReviewerApply> selectApplyList(EditorReviewerApply apply);

    /**
     * 根据申请ID查询
     */
    public EditorReviewerApply selectApplyById(Long applyId);

    /**
     * 新增申请/推荐
     */
    public int insertApply(EditorReviewerApply apply);

    /**
     * 修改申请
     */
    public int updateApply(EditorReviewerApply apply);

    /**
     * 删除申请
     */
    public int deleteApplyById(Long applyId);

    /**
     * 批量删除申请
     */
    public int deleteApplyByIds(Long[] applyIds);

    /**
     * 审核申请
     */
    public int auditApply(EditorReviewerApply apply);
}