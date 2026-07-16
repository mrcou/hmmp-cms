package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.ReviewerTask;

/**
 * 审稿任务 业务层
 * 
 * @author hmmp
 */
public interface IReviewerTaskService
{
    /**
     * 查询审稿任务列表
     * 
     * @param reviewerTask 审稿任务
     * @return 审稿任务集合
     */
    public List<ReviewerTask> selectReviewerTaskList(ReviewerTask reviewerTask);

    /**
     * 通过任务ID查询审稿任务
     * 
     * @param taskId 任务ID
     * @return 审稿任务
     */
    public ReviewerTask selectReviewerTaskById(Long taskId);

    /**
     * 通过稿件ID查询审稿任务
     * 
     * @param manuscriptId 稿件ID
     * @return 审稿任务
     */
    public ReviewerTask selectReviewerTaskByManuscriptId(Long manuscriptId);

    /**
     * 新增审稿任务
     * 
     * @param reviewerTask 审稿任务
     * @return 结果
     */
    public int insertReviewerTask(ReviewerTask reviewerTask);

    /**
     * 修改审稿任务
     * 
     * @param reviewerTask 审稿任务
     * @return 结果
     */
    public int updateReviewerTask(ReviewerTask reviewerTask);

    /**
     * 批量删除审稿任务
     * 
     * @param taskIds 需要删除的任务ID
     * @return 结果
     */
    public int deleteReviewerTaskByIds(Long[] taskIds);

    /**
     * 删除审稿任务
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    public int deleteReviewerTaskById(Long taskId);
}