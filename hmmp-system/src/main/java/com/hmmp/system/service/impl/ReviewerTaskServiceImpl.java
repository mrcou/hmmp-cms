package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.ReviewerTask;
import com.hmmp.system.mapper.ReviewerTaskMapper;
import com.hmmp.system.service.IReviewerTaskService;

/**
 * 审稿任务 业务层处理
 * 
 * @author hmmp
 */
@Service
public class ReviewerTaskServiceImpl implements IReviewerTaskService
{
    @Autowired
    private ReviewerTaskMapper reviewerTaskMapper;

    /**
     * 查询审稿任务列表
     * 
     * @param reviewerTask 审稿任务
     * @return 审稿任务集合
     */
    @Override
    public List<ReviewerTask> selectReviewerTaskList(ReviewerTask reviewerTask)
    {
        return reviewerTaskMapper.selectReviewerTaskList(reviewerTask);
    }

    /**
     * 通过任务ID查询审稿任务
     * 
     * @param taskId 任务ID
     * @return 审稿任务
     */
    @Override
    public ReviewerTask selectReviewerTaskById(Long taskId)
    {
        return reviewerTaskMapper.selectReviewerTaskById(taskId);
    }

    /**
     * 通过稿件ID查询审稿任务
     * 
     * @param manuscriptId 稿件ID
     * @return 审稿任务
     */
    @Override
    public ReviewerTask selectReviewerTaskByManuscriptId(Long manuscriptId)
    {
        return reviewerTaskMapper.selectReviewerTaskByManuscriptId(manuscriptId);
    }

    /**
     * 新增审稿任务
     * 
     * @param reviewerTask 审稿任务
     * @return 结果
     */
    @Override
    public int insertReviewerTask(ReviewerTask reviewerTask)
    {
        return reviewerTaskMapper.insertReviewerTask(reviewerTask);
    }

    /**
     * 修改审稿任务
     * 
     * @param reviewerTask 审稿任务
     * @return 结果
     */
    @Override
    public int updateReviewerTask(ReviewerTask reviewerTask)
    {
        return reviewerTaskMapper.updateReviewerTask(reviewerTask);
    }

    /**
     * 批量删除审稿任务
     * 
     * @param taskIds 需要删除的任务ID
     * @return 结果
     */
    @Override
    public int deleteReviewerTaskByIds(Long[] taskIds)
    {
        return reviewerTaskMapper.deleteReviewerTaskByIds(taskIds);
    }

    /**
     * 删除审稿任务
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    @Override
    public int deleteReviewerTaskById(Long taskId)
    {
        return reviewerTaskMapper.deleteReviewerTaskById(taskId);
    }
}