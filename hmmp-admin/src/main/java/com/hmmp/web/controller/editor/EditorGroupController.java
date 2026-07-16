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
import com.hmmp.system.domain.EditorGroup;
import com.hmmp.system.domain.EditorGroupManuscript;
import com.hmmp.system.domain.EditorSign;
import com.hmmp.system.service.IEditorGroupManuscriptService;
import com.hmmp.system.service.IEditorGroupService;
import com.hmmp.system.service.IEditorSignService;

/**
 * 排刊组稿 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/editor/group")
public class EditorGroupController extends BaseController {

    @Autowired
    private IEditorGroupService groupService;

    @Autowired
    private IEditorGroupManuscriptService groupManuscriptService;

    @Autowired
    private IEditorSignService signService;

    // ========== 组稿管理 ==========

    /**
     * 获取组稿列表
     */
    @PreAuthorize("@ss.hasPermi('editor:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(EditorGroup group) {
        startPage();
        List<EditorGroup> list = groupService.selectGroupList(group);
        return getDataTable(list);
    }

    /**
     * 获取组稿详情
     */
    @PreAuthorize("@ss.hasPermi('editor:group:query')")
    @GetMapping("/{groupId}")
    public AjaxResult getInfo(@PathVariable Long groupId) {
        return success(groupService.selectGroupById(groupId));
    }

    /**
     * 新建组稿
     */
    @PreAuthorize("@ss.hasPermi('editor:group:add')")
    @Log(title = "排刊组稿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EditorGroup group) {
        return toAjax(groupService.insertGroup(group));
    }

    /**
     * 修改组稿
     */
    @PreAuthorize("@ss.hasPermi('editor:group:edit')")
    @Log(title = "排刊组稿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EditorGroup group) {
        return toAjax(groupService.updateGroup(group));
    }

    /**
     * 删除组稿
     */
    @PreAuthorize("@ss.hasPermi('editor:group:remove')")
    @Log(title = "排刊组稿", businessType = BusinessType.DELETE)
    @DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds) {
        return toAjax(groupService.deleteGroupByIds(groupIds));
    }

    // ========== 组稿稿件管理 ==========

    /**
     * 获取组稿稿件的稿件列表
     */
    @PreAuthorize("@ss.hasPermi('editor:group:list')")
    @GetMapping("/manuscript/{groupId}")
    public TableDataInfo manuscriptList(@PathVariable Long groupId) {
        startPage();
        List<EditorGroupManuscript> list = groupManuscriptService.selectManuscriptsByGroupId(groupId);
        return getDataTable(list);
    }

    /**
     * 添加稿件到组稿
     */
    @PreAuthorize("@ss.hasPermi('editor:group:addManuscript')")
    @Log(title = "排刊组稿", businessType = BusinessType.INSERT)
    @PostMapping("/manuscript")
    public AjaxResult addManuscript(@RequestBody EditorGroupManuscript groupManuscript) {
        return toAjax(groupManuscriptService.insertGroupManuscript(groupManuscript));
    }

    /**
     * 从组稿移除稿件
     */
    @PreAuthorize("@ss.hasPermi('editor:group:removeManuscript')")
    @Log(title = "排刊组稿", businessType = BusinessType.DELETE)
    @DeleteMapping("/manuscript/{id}")
    public AjaxResult removeManuscript(@PathVariable Long id) {
        return toAjax(groupManuscriptService.deleteGroupManuscriptById(id));
    }

    // ========== 稿件签发 ==========

    /**
     * 稿件签发
     */
    @PreAuthorize("@ss.hasPermi('editor:sign:sign')")
    @Log(title = "稿件签发", businessType = BusinessType.INSERT)
    @PutMapping("/sign/{manuscriptId}")
    public AjaxResult sign(@PathVariable Long manuscriptId, @RequestBody EditorSign sign) {
        sign.setManuscriptId(manuscriptId);
        return toAjax(signService.signManuscript(sign));
    }
}