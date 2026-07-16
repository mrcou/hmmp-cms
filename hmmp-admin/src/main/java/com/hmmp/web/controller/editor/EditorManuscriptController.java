package com.hmmp.web.controller.editor;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
import com.hmmp.common.utils.poi.ExcelUtil;
import com.hmmp.system.domain.EditorManuscript;
import com.hmmp.system.domain.EditorManuscriptLog;
import com.hmmp.system.service.IEditorManuscriptLogService;
import com.hmmp.system.service.IEditorManuscriptService;

/**
 * 稿件处理 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/editor/manuscript")
public class EditorManuscriptController extends BaseController {

    @Autowired
    private IEditorManuscriptService manuscriptService;

    @Autowired
    private IEditorManuscriptLogService manuscriptLogService;

    /**
     * 获取稿件列表
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:list')")
    @GetMapping("/list")
    public TableDataInfo list(EditorManuscript manuscript) {
        startPage();
        List<EditorManuscript> list = manuscriptService.selectManuscriptList(manuscript);
        return getDataTable(list);
    }

    /**
     * 导出稿件列表
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:export')")
    @Log(title = "稿件管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EditorManuscript manuscript) {
        List<EditorManuscript> list = manuscriptService.selectManuscriptList(manuscript);
        ExcelUtil<EditorManuscript> util = new ExcelUtil<EditorManuscript>(EditorManuscript.class);
        util.exportExcel(response, list, "稿件数据");
    }

    /**
     * 获取稿件详情
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:query')")
    @GetMapping("/{manuscriptId}")
    public AjaxResult getInfo(@PathVariable Long manuscriptId) {
        return success(manuscriptService.selectManuscriptById(manuscriptId));
    }

    /**
     * 新增稿件（收稿登记）
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:add')")
    @Log(title = "稿件管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.insertManuscript(manuscript));
    }

    /**
     * 修改稿件
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:edit')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.updateManuscript(manuscript));
    }

    /**
     * 删除稿件
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:remove')")
    @Log(title = "稿件管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{manuscriptIds}")
    public AjaxResult remove(@PathVariable Long[] manuscriptIds) {
        return toAjax(manuscriptService.deleteManuscriptByIds(manuscriptIds));
    }

    /**
     * 分配责任编辑
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:assign')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/assign")
    public AjaxResult assign(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.assignEditor(manuscript));
    }

    /**
     * 送审
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:review')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/review")
    public AjaxResult review(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.sendForReview(manuscript));
    }

    /**
     * 退修
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:revise')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/revise")
    public AjaxResult revise(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.revise(manuscript));
    }

    /**
     * 修回确认
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:revised')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/revised")
    public AjaxResult revised(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.confirmRevision(manuscript));
    }

    /**
     * 录用
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:adopt')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/adopt")
    public AjaxResult adopt(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.adopt(manuscript));
    }

    /**
     * 退稿
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:reject')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/reject")
    public AjaxResult reject(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.reject(manuscript));
    }

    /**
     * 发表
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:publish')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/publish")
    public AjaxResult publish(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.publish(manuscript));
    }

    /**
     * 归档
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:archive')")
    @Log(title = "稿件管理", businessType = BusinessType.UPDATE)
    @PutMapping("/archive")
    public AjaxResult archive(@RequestBody EditorManuscript manuscript) {
        return toAjax(manuscriptService.archive(manuscript));
    }

    /**
     * 获取稿件操作日志
     */
    @PreAuthorize("@ss.hasPermi('editor:manuscript:query')")
    @GetMapping("/log/{manuscriptId}")
    public TableDataInfo log(@PathVariable Long manuscriptId) {
        startPage();
        List<EditorManuscriptLog> list = manuscriptLogService.selectLogByManuscriptId(manuscriptId);
        return getDataTable(list);
    }
}