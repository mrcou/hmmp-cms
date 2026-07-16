package com.hmmp.web.controller.editor;

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
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.system.domain.EditorEdit;
import com.hmmp.system.service.IEditorEditService;

/**
 * 编辑加工 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/editor/edit")
public class EditorEditController extends BaseController {

    @Autowired
    private IEditorEditService editService;

    /**
     * 获取编辑加工列表
     */
    @PreAuthorize("@ss.hasPermi('editor:edit:list')")
    @GetMapping("/list")
    public TableDataInfo list(EditorEdit edit) {
        startPage();
        List<EditorEdit> list = editService.selectEditList(edit);
        return getDataTable(list);
    }

    /**
     * 获取编辑详情
     */
    @PreAuthorize("@ss.hasPermi('editor:edit:query')")
    @GetMapping("/{editId}")
    public AjaxResult getInfo(@PathVariable Long editId) {
        return success(editService.selectEditById(editId));
    }

    /**
     * 新建编辑任务
     */
    @PreAuthorize("@ss.hasPermi('editor:edit:add')")
    @Log(title = "编辑加工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EditorEdit edit) {
        return toAjax(editService.insertEdit(edit));
    }

    /**
     * 更新编辑任务
     */
    @PreAuthorize("@ss.hasPermi('editor:edit:edit')")
    @Log(title = "编辑加工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EditorEdit edit) {
        return toAjax(editService.updateEdit(edit));
    }

    /**
     * 完成编辑
     */
    @PreAuthorize("@ss.hasPermi('editor:edit:complete')")
    @Log(title = "编辑加工", businessType = BusinessType.UPDATE)
    @PutMapping("/complete")
    public AjaxResult complete(@RequestBody EditorEdit edit) {
        return toAjax(editService.completeEdit(edit));
    }
}