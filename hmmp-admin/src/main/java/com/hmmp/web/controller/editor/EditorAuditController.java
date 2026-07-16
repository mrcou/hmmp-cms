package com.hmmp.web.controller.editor;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.hmmp.system.domain.EditorAudit;
import com.hmmp.system.service.IEditorAuditService;

/**
 * 审稿管理 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/editor/audit")
public class EditorAuditController extends BaseController {

    @Autowired
    private IEditorAuditService auditService;

    /**
     * 获取审稿列表
     */
    @PreAuthorize("@ss.hasPermi('editor:audit:list')")
    @GetMapping("/list")
    public TableDataInfo list(EditorAudit audit) {
        startPage();
        List<EditorAudit> list = auditService.selectAuditList(audit);
        return getDataTable(list);
    }

    /**
     * 导出审稿列表
     */
    @PreAuthorize("@ss.hasPermi('editor:audit:export')")
    @Log(title = "审稿管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EditorAudit audit) {
        List<EditorAudit> list = auditService.selectAuditList(audit);
        ExcelUtil<EditorAudit> util = new ExcelUtil<EditorAudit>(EditorAudit.class);
        util.exportExcel(response, list, "审稿数据");
    }

    /**
     * 获取审稿详情
     */
    @PreAuthorize("@ss.hasPermi('editor:audit:query')")
    @GetMapping("/{auditId}")
    public AjaxResult getInfo(@PathVariable Long auditId) {
        return success(auditService.selectAuditById(auditId));
    }

    /**
     * 邀请审稿人
     */
    @PreAuthorize("@ss.hasPermi('editor:audit:invite')")
    @Log(title = "审稿管理", businessType = BusinessType.INSERT)
    @PostMapping("/invite")
    public AjaxResult invite(@RequestBody EditorAudit audit) {
        return toAjax(auditService.inviteReviewer(audit));
    }

    /**
     * 撤审
     */
    @PreAuthorize("@ss.hasPermi('editor:audit:withdraw')")
    @Log(title = "审稿管理", businessType = BusinessType.UPDATE)
    @PutMapping("/withdraw")
    public AjaxResult withdraw(@RequestBody EditorAudit audit) {
        return toAjax(auditService.withdrawAudit(audit));
    }

    /**
     * 接受退审
     */
    @PreAuthorize("@ss.hasPermi('editor:audit:acceptWithdraw')")
    @Log(title = "审稿管理", businessType = BusinessType.UPDATE)
    @PutMapping("/acceptWithdraw")
    public AjaxResult acceptWithdraw(@RequestBody EditorAudit audit) {
        return toAjax(auditService.acceptWithdraw(audit));
    }

    /**
     * 审回处理
     */
    @PreAuthorize("@ss.hasPermi('editor:audit:return')")
    @Log(title = "审稿管理", businessType = BusinessType.UPDATE)
    @PutMapping("/return")
    public AjaxResult returnAudit(@RequestBody EditorAudit audit) {
        return toAjax(auditService.returnAudit(audit));
    }
}