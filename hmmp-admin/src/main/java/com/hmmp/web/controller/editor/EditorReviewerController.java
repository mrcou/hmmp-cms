package com.hmmp.web.controller.editor;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.system.domain.EditorReviewer;
import com.hmmp.system.domain.EditorReviewerApply;
import com.hmmp.system.service.IEditorReviewerApplyService;
import com.hmmp.system.service.IEditorReviewerService;

/**
 * 审稿人管理 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/editor/reviewer")
public class EditorReviewerController extends BaseController {

    @Autowired
    private IEditorReviewerService reviewerService;

    @Autowired
    private IEditorReviewerApplyService reviewerApplyService;

    /**
     * 获取审稿人列表
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:list')")
    @GetMapping("/list")
    public TableDataInfo list(EditorReviewer reviewer) {
        startPage();
        List<EditorReviewer> list = reviewerService.selectReviewerList(reviewer);
        return getDataTable(list);
    }

    /**
     * 获取审稿人详情
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:query')")
    @GetMapping("/{reviewerId}")
    public AjaxResult getInfo(@PathVariable Long reviewerId) {
        return success(reviewerService.selectReviewerById(reviewerId));
    }

    /**
     * 新增审稿人
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:add')")
    @Log(title = "审稿人管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EditorReviewer reviewer) {
        return toAjax(reviewerService.insertReviewer(reviewer));
    }

    /**
     * 修改审稿人
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:edit')")
    @Log(title = "审稿人管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EditorReviewer reviewer) {
        return toAjax(reviewerService.updateReviewer(reviewer));
    }

    /**
     * 删除审稿人
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:remove')")
    @Log(title = "审稿人管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{reviewerIds}")
    public AjaxResult remove(@PathVariable Long[] reviewerIds) {
        return toAjax(reviewerService.deleteReviewerByIds(reviewerIds));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:edit')")
    @Log(title = "审稿人管理", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult changeStatus(@RequestBody EditorReviewer reviewer) {
        return toAjax(reviewerService.updateReviewerStatus(reviewer));
    }

    // ========== 审稿人申请/推荐 ==========

    /**
     * 获取申请/推荐列表
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:apply')")
    @GetMapping("/apply/list")
    public TableDataInfo applyList(EditorReviewerApply apply) {
        startPage();
        List<EditorReviewerApply> list = reviewerApplyService.selectApplyList(apply);
        return getDataTable(list);
    }

    /**
     * 提交申请/推荐
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:apply')")
    @Log(title = "审稿人申请", businessType = BusinessType.INSERT)
    @PostMapping("/apply")
    public AjaxResult apply(@RequestBody EditorReviewerApply apply) {
        return toAjax(reviewerApplyService.insertApply(apply));
    }

    /**
     * 审核申请
     */
    @PreAuthorize("@ss.hasPermi('editor:reviewer:audit')")
    @Log(title = "审稿人申请", businessType = BusinessType.UPDATE)
    @PutMapping("/apply/audit")
    public AjaxResult auditApply(@RequestBody EditorReviewerApply apply) {
        return toAjax(reviewerApplyService.auditApply(apply));
    }
}