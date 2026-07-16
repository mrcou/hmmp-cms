package com.hmmp.web.controller.editor;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.hmmp.common.utils.poi.ExcelUtil;
import com.hmmp.system.domain.EditorAuthor;
import com.hmmp.system.service.IEditorAuthorService;

/**
 * 作者管理 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/editor/author")
public class EditorAuthorController extends BaseController {

    @Autowired
    private IEditorAuthorService authorService;

    /**
     * 获取作者列表
     */
    @PreAuthorize("@ss.hasPermi('editor:author:list')")
    @GetMapping("/list")
    public TableDataInfo list(EditorAuthor author) {
        startPage();
        List<EditorAuthor> list = authorService.selectAuthorList(author);
        return getDataTable(list);
    }

    /**
     * 导出作者列表
     */
    @PreAuthorize("@ss.hasPermi('editor:author:export')")
    @Log(title = "作者管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EditorAuthor author) {
        List<EditorAuthor> list = authorService.selectAuthorList(author);
        ExcelUtil<EditorAuthor> util = new ExcelUtil<EditorAuthor>(EditorAuthor.class);
        util.exportExcel(response, list, "作者数据");
    }

    /**
     * 获取作者详情
     */
    @PreAuthorize("@ss.hasPermi('editor:author:query')")
    @GetMapping("/{authorId}")
    public AjaxResult getInfo(@PathVariable Long authorId) {
        return success(authorService.selectAuthorById(authorId));
    }

    /**
     * 新增作者
     */
    @PreAuthorize("@ss.hasPermi('editor:author:add')")
    @Log(title = "作者管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EditorAuthor author) {
        return toAjax(authorService.insertAuthor(author));
    }

    /**
     * 修改作者
     */
    @PreAuthorize("@ss.hasPermi('editor:author:edit')")
    @Log(title = "作者管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EditorAuthor author) {
        return toAjax(authorService.updateAuthor(author));
    }

    /**
     * 删除作者
     */
    @PreAuthorize("@ss.hasPermi('editor:author:remove')")
    @Log(title = "作者管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{authorIds}")
    public AjaxResult remove(@PathVariable Long[] authorIds) {
        return toAjax(authorService.deleteAuthorByIds(authorIds));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('editor:author:edit')")
    @Log(title = "作者管理", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult changeStatus(@RequestBody EditorAuthor author) {
        return toAjax(authorService.updateAuthorStatus(author));
    }
}