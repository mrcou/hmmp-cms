package com.hmmp.web.controller.publisher;

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
import org.springframework.web.multipart.MultipartFile;
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.common.utils.poi.ExcelUtil;
import com.hmmp.system.domain.publisher.PublisherArticle;
import com.hmmp.system.domain.publisher.PublisherArticleBatchImport;
import com.hmmp.system.domain.publisher.PublisherColumn;
import com.hmmp.system.domain.publisher.PublisherComment;
import com.hmmp.system.domain.publisher.PublisherIssue;
import com.hmmp.system.domain.publisher.PublisherVirtualAlbum;
import com.hmmp.system.domain.publisher.PublisherYear;
import com.hmmp.system.domain.publisher.PublisherAlbumArticle;
import com.hmmp.system.service.publisher.IPublisherArticleService;
import com.hmmp.system.service.publisher.IPublisherColumnService;
import com.hmmp.system.service.publisher.IPublisherCommentService;
import com.hmmp.system.service.publisher.IPublisherIssueService;
import com.hmmp.system.service.publisher.IPublisherVirtualAlbumService;
import com.hmmp.system.service.publisher.IPublisherYearService;
import com.hmmp.system.service.publisher.IPublisherAlbumArticleService;

/**
 * 网刊数据管理 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/publisher")
public class PublisherOnlineController extends BaseController {

    @Autowired
    private IPublisherYearService yearService;

    @Autowired
    private IPublisherIssueService issueService;

    @Autowired
    private IPublisherColumnService columnService;

    @Autowired
    private IPublisherArticleService articleService;

    @Autowired
    private IPublisherCommentService commentService;

    @Autowired
    private IPublisherVirtualAlbumService albumService;

    @Autowired
    private IPublisherAlbumArticleService albumArticleService;

    // ========== 年份管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:year:list')")
    @GetMapping("/year/list")
    public TableDataInfo yearList(PublisherYear year) {
        startPage();
        List<PublisherYear> list = yearService.selectYearList(year);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:year:add')")
    @PostMapping("/year")
    public AjaxResult yearAdd(@RequestBody PublisherYear year) {
        if (!yearService.checkYearUnique(year)) {
            return error("该杂志下年份已存在");
        }
        return toAjax(yearService.insertYear(year));
    }

    @PreAuthorize("@ss.hasPermi('publisher:year:query')")
    @GetMapping("/year/{yearId}")
    public AjaxResult yearGetInfo(@PathVariable Long yearId) {
        return success(yearService.selectYearById(yearId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:year:edit')")
    @PutMapping("/year")
    public AjaxResult yearEdit(@RequestBody PublisherYear year) {
        if (!yearService.checkYearUnique(year)) {
            return error("该杂志下年份已存在");
        }
        return toAjax(yearService.updateYear(year));
    }

    @PreAuthorize("@ss.hasPermi('publisher:year:remove')")
    @DeleteMapping("/year/{yearIds}")
    public AjaxResult yearRemove(@PathVariable Long[] yearIds) {
        return toAjax(yearService.deleteYearByIds(yearIds));
    }

    // ========== 刊期管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:issue:list')")
    @GetMapping("/issue/list")
    public TableDataInfo issueList(PublisherIssue issue) {
        startPage();
        List<PublisherIssue> list = issueService.selectIssueList(issue);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:issue:query')")
    @GetMapping("/issue/{issueId}")
    public AjaxResult issueGetInfo(@PathVariable Long issueId) {
        return success(issueService.selectIssueById(issueId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:issue:add')")
    @PostMapping("/issue")
    public AjaxResult issueAdd(@RequestBody PublisherIssue issue) {
        return toAjax(issueService.insertIssue(issue));
    }

    @PreAuthorize("@ss.hasPermi('publisher:issue:edit')")
    @PutMapping("/issue")
    public AjaxResult issueEdit(@RequestBody PublisherIssue issue) {
        return toAjax(issueService.updateIssue(issue));
    }

    @PreAuthorize("@ss.hasPermi('publisher:issue:remove')")
    @DeleteMapping("/issue/{issueIds}")
    public AjaxResult issueRemove(@PathVariable Long[] issueIds) {
        return toAjax(issueService.deleteIssueByIds(issueIds));
    }

    @PreAuthorize("@ss.hasPermi('publisher:issue:publish')")
    @PutMapping("/issue/publish")
    public AjaxResult issuePublish(@RequestBody PublisherIssue issue) {
        return toAjax(issueService.publishIssue(issue));
    }

    /** 刊期业务动作：subscribe=订阅发送 doi=DOI注册 cstr=CSTR注册 baidu=百度发布 */
    @PreAuthorize("@ss.hasPermi('publisher:issue:edit')")
    @PutMapping("/issue/action/{action}/{issueId}")
    public AjaxResult issueAction(@PathVariable String action, @PathVariable Long issueId) {
        return toAjax(issueService.executeIssueAction(issueId, action));
    }

    // ========== 栏目管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:column:list')")
    @GetMapping("/column/list")
    public TableDataInfo columnList(PublisherColumn column) {
        startPage();
        List<PublisherColumn> list = columnService.selectColumnList(column);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:column:query')")
    @GetMapping("/column/{columnId}")
    public AjaxResult columnGetInfo(@PathVariable Long columnId) {
        return success(columnService.selectColumnById(columnId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:column:add')")
    @PostMapping("/column")
    public AjaxResult columnAdd(@RequestBody PublisherColumn column) {
        if (!columnService.checkColumnUnique(column)) {
            return error("该杂志下栏目ID已存在");
        }
        return toAjax(columnService.insertColumn(column));
    }

    @PreAuthorize("@ss.hasPermi('publisher:column:edit')")
    @PutMapping("/column")
    public AjaxResult columnEdit(@RequestBody PublisherColumn column) {
        if (!columnService.checkColumnUnique(column)) {
            return error("该杂志下栏目ID已存在");
        }
        return toAjax(columnService.updateColumn(column));
    }

    @PreAuthorize("@ss.hasPermi('publisher:column:remove')")
    @DeleteMapping("/column/{columnIds}")
    public AjaxResult columnRemove(@PathVariable Long[] columnIds) {
        return toAjax(columnService.deleteColumnByIds(columnIds));
    }

    // ========== 文章管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:article:list')")
    @GetMapping("/article/list")
    public TableDataInfo articleList(PublisherArticle article) {
        startPage();
        List<PublisherArticle> list = articleService.selectArticleList(article);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:article:query')")
    @GetMapping("/article/{articleId}")
    public AjaxResult articleGetInfo(@PathVariable Long articleId) {
        return success(articleService.selectArticleById(articleId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:article:add')")
    @PostMapping("/article")
    public AjaxResult articleAdd(@RequestBody PublisherArticle article) {
        return toAjax(articleService.insertArticle(article));
    }

    @PreAuthorize("@ss.hasPermi('publisher:article:edit')")
    @PutMapping("/article")
    public AjaxResult articleEdit(@RequestBody PublisherArticle article) {
        return toAjax(articleService.updateArticle(article));
    }

    @PreAuthorize("@ss.hasPermi('publisher:article:remove')")
    @DeleteMapping("/article/{articleIds}")
    public AjaxResult articleRemove(@PathVariable Long[] articleIds) {
        return toAjax(articleService.deleteArticleByIds(articleIds));
    }

    @PreAuthorize("@ss.hasPermi('publisher:article:batchPublish')")
    @PutMapping("/article/batchPublish")
    public AjaxResult articleBatchPublish(@RequestBody Long[] articleIds) {
        return toAjax(articleService.batchPublishArticles(articleIds));
    }

    /**
     * 批量发布：导入过刊 Excel 元数据，或 ZIP 全文 PDF（文件名=稿件编号）
     */
    @Log(title = "批量发布文章", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('publisher:article:batchPublish')")
    @PostMapping("/article/batchImport")
    public AjaxResult articleBatchImport(MultipartFile file, boolean updateSupport) throws Exception {
        if (file == null || file.isEmpty()) {
            return error("请选择要导入的文件");
        }
        String filename = file.getOriginalFilename();
        if (filename != null && filename.toLowerCase().endsWith(".zip")) {
            String message = articleService.importPdfZip(file, getUsername());
            return success(message);
        }
        ExcelUtil<PublisherArticleBatchImport> util = new ExcelUtil<PublisherArticleBatchImport>(PublisherArticleBatchImport.class);
        List<PublisherArticleBatchImport> rows = util.importExcel(file.getInputStream());
        String message = articleService.importArticles(rows, updateSupport, getUsername());
        return success(message);
    }

    /**
     * 下载过刊导入模板（表头与样表字段一一对应）
     */
    @PreAuthorize("@ss.hasPermi('publisher:article:batchPublish')")
    @PostMapping("/article/importTemplate")
    public void articleImportTemplate(HttpServletResponse response) {
        ExcelUtil<PublisherArticleBatchImport> util = new ExcelUtil<PublisherArticleBatchImport>(PublisherArticleBatchImport.class);
        util.importTemplateExcel(response, "过刊稿件明细");
    }

    @PreAuthorize("@ss.hasPermi('publisher:article:cnkiCheck')")
    @Log(title = "知网标题核对", businessType = BusinessType.UPDATE)
    @PostMapping("/article/cnkiCheck")
    public AjaxResult articleCnkiCheck(@RequestBody PublisherArticle article) {
        return toAjax(articleService.cnkiCheck(article));
    }

    /**
     * 引用次数同步：将知网引用次数写入被引次数
     */
    @PreAuthorize("@ss.hasPermi('publisher:article:cnkiCheck')")
    @Log(title = "知网引用次数同步", businessType = BusinessType.UPDATE)
    @PutMapping("/article/syncCnkiCited/{articleIds}")
    public AjaxResult articleSyncCnkiCited(@PathVariable Long[] articleIds) {
        return toAjax(articleService.syncCnkiCitedCount(articleIds));
    }

    // ========== 评论管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:comment:list')")
    @GetMapping("/comment/list")
    public TableDataInfo commentList(PublisherComment comment) {
        startPage();
        List<PublisherComment> list = commentService.selectCommentList(comment);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:comment:audit')")
    @Log(title = "文章评论", businessType = BusinessType.UPDATE)
    @PutMapping("/comment/audit")
    public AjaxResult commentAudit(@RequestBody PublisherComment comment) {
        return toAjax(commentService.auditComment(comment));
    }

    @PreAuthorize("@ss.hasPermi('publisher:comment:remove')")
    @Log(title = "文章评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/comment/{commentIds}")
    public AjaxResult commentRemove(@PathVariable Long[] commentIds) {
        return toAjax(commentService.deleteCommentByIds(commentIds));
    }

    // ========== 虚拟专辑管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:album:list')")
    @GetMapping("/album/list")
    public TableDataInfo albumList(PublisherVirtualAlbum album) {
        startPage();
        List<PublisherVirtualAlbum> list = albumService.selectAlbumList(album);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:album:query')")
    @GetMapping("/album/{albumId}")
    public AjaxResult albumGetInfo(@PathVariable Long albumId) {
        return success(albumService.selectAlbumById(albumId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:album:add')")
    @Log(title = "虚拟专辑", businessType = BusinessType.INSERT)
    @PostMapping("/album")
    public AjaxResult albumAdd(@RequestBody PublisherVirtualAlbum album) {
        return toAjax(albumService.insertAlbum(album));
    }

    @PreAuthorize("@ss.hasPermi('publisher:album:edit')")
    @Log(title = "虚拟专辑", businessType = BusinessType.UPDATE)
    @PutMapping("/album")
    public AjaxResult albumEdit(@RequestBody PublisherVirtualAlbum album) {
        return toAjax(albumService.updateAlbum(album));
    }

    @PreAuthorize("@ss.hasPermi('publisher:album:remove')")
    @Log(title = "虚拟专辑", businessType = BusinessType.DELETE)
    @DeleteMapping("/album/{albumIds}")
    public AjaxResult albumRemove(@PathVariable Long[] albumIds) {
        return toAjax(albumService.deleteAlbumByIds(albumIds));
    }

    @PreAuthorize("@ss.hasPermi('publisher:album:addArticle')")
    @PostMapping("/album/article")
    public AjaxResult albumAddArticle(@RequestBody PublisherAlbumArticle albumArticle) {
        return toAjax(albumArticleService.insertAlbumArticle(albumArticle));
    }
}