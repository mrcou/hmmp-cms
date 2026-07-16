package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorReviewer;

/**
 * 审稿人管理 业务层
 * 
 * @author hmmp
 */
public interface IEditorReviewerService {

    /**
     * 查询审稿人列表
     */
    public List<EditorReviewer> selectReviewerList(EditorReviewer reviewer);

    /**
     * 根据审稿人ID查询
     */
    public EditorReviewer selectReviewerById(Long reviewerId);

    /**
     * 根据用户ID查询审稿人
     */
    public EditorReviewer selectReviewerByUserId(Long userId);

    /**
     * 新增审稿人
     */
    public int insertReviewer(EditorReviewer reviewer);

    /**
     * 修改审稿人
     */
    public int updateReviewer(EditorReviewer reviewer);

    /**
     * 删除审稿人（逻辑删除）
     */
    public int deleteReviewerById(Long reviewerId);

    /**
     * 批量删除审稿人
     */
    public int deleteReviewerByIds(Long[] reviewerIds);

    /**
     * 修改审稿人状态
     */
    public int updateReviewerStatus(EditorReviewer reviewer);
}