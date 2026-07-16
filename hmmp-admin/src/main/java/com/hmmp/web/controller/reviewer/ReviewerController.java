package com.hmmp.web.controller.reviewer;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.system.domain.ReviewerTask;
import com.hmmp.system.service.IReviewerTaskService;

/**
 * 审稿中心
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/reviewer")
public class ReviewerController extends BaseController
{
    @Autowired
    private IReviewerTaskService reviewerTaskService;

    /**
     * 获取新邀请审稿列表
     */
    @PreAuthorize("@ss.hasPermi('reviewer:manuscript:newInvite')")
    @GetMapping("/manuscript/newInvite")
    public AjaxResult newInvite()
    {
        Long userId = getUserId();
        ReviewerTask reviewerTask = new ReviewerTask();
        reviewerTask.setReviewerId(userId);
        reviewerTask.setTaskStatus("0");
        List<ReviewerTask> list = reviewerTaskService.selectReviewerTaskList(reviewerTask);
        return success(list);
    }

    /**
     * 接受审稿邀请
     */
    @PreAuthorize("@ss.hasPermi('reviewer:manuscript:accept')")
    @PutMapping("/manuscript/accept")
    public AjaxResult accept(@RequestBody ReviewerTask reviewerTask)
    {
        ReviewerTask existing = reviewerTaskService.selectReviewerTaskById(reviewerTask.getTaskId());
        if (existing == null)
        {
            return error("审稿任务不存在");
        }
        reviewerTask.setTaskStatus("1");
        reviewerTask.setAcceptTime(new Date());
        int rows = reviewerTaskService.updateReviewerTask(reviewerTask);
        return toAjax(rows);
    }

    /**
     * 拒绝审稿邀请
     */
    @PreAuthorize("@ss.hasPermi('reviewer:manuscript:reject')")
    @PutMapping("/manuscript/reject")
    public AjaxResult reject(@RequestBody ReviewerTask reviewerTask)
    {
        ReviewerTask existing = reviewerTaskService.selectReviewerTaskById(reviewerTask.getTaskId());
        if (existing == null)
        {
            return error("审稿任务不存在");
        }
        reviewerTask.setTaskStatus("4");
        int rows = reviewerTaskService.updateReviewerTask(reviewerTask);
        return toAjax(rows);
    }

    /**
     * 获取未审完稿件列表
     */
    @PreAuthorize("@ss.hasPermi('reviewer:manuscript:reviewing')")
    @GetMapping("/manuscript/reviewing")
    public AjaxResult reviewing()
    {
        Long userId = getUserId();
        ReviewerTask reviewerTask = new ReviewerTask();
        reviewerTask.setReviewerId(userId);
        reviewerTask.setTaskStatus("2");
        List<ReviewerTask> list = reviewerTaskService.selectReviewerTaskList(reviewerTask);
        return success(list);
    }

    /**
     * 获取稿件详情
     */
    @PreAuthorize("@ss.hasPermi('reviewer:manuscript:detail')")
    @GetMapping("/manuscript/detail/{manuscriptId}")
    public AjaxResult detail(@PathVariable("manuscriptId") Long manuscriptId)
    {
        ReviewerTask reviewerTask = reviewerTaskService.selectReviewerTaskByManuscriptId(manuscriptId);
        return success(reviewerTask);
    }

    /**
     * 提交审稿意见
     */
    @PreAuthorize("@ss.hasPermi('reviewer:manuscript:submit')")
    @PostMapping("/manuscript/submit")
    public AjaxResult submit(@RequestBody ReviewerTask reviewerTask)
    {
        ReviewerTask existing = reviewerTaskService.selectReviewerTaskById(reviewerTask.getTaskId());
        if (existing == null)
        {
            return error("审稿任务不存在");
        }
        reviewerTask.setTaskStatus("3");
        reviewerTask.setSubmitTime(new Date());
        int rows = reviewerTaskService.updateReviewerTask(reviewerTask);
        return toAjax(rows);
    }

    /**
     * 获取已审完稿件列表
     */
    @PreAuthorize("@ss.hasPermi('reviewer:manuscript:reviewed')")
    @GetMapping("/manuscript/reviewed")
    public AjaxResult reviewed()
    {
        Long userId = getUserId();
        ReviewerTask reviewerTask = new ReviewerTask();
        reviewerTask.setReviewerId(userId);
        reviewerTask.setTaskStatus("3");
        List<ReviewerTask> list = reviewerTaskService.selectReviewerTaskList(reviewerTask);
        return success(list);
    }
}