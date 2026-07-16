package com.hmmp.web.controller.publisher;

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
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.system.domain.publisher.PublisherArticle;
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
        if (!yearService.checkYearUnique(year.getYear())) {
            return error("该年份已存在");
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
        return toAjax(columnService.insertColumn(column));
    }

    @PreAuthorize("@ss.hasPermi('publisher:column:edit')")
    @PutMapping("/column")
    public AjaxResult columnEdit(@RequestBody PublisherColumn column) {
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

    @PreAuthorize("@ss.hasPermi('publisher:article:cnkiCheck')")
    @PostMapping("/article/cnkiCheck")
    public AjaxResult articleCnkiCheck(@RequestBody PublisherArticle article) {
        return toAjax(articleService.cnkiCheck(article));
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
    @PutMapping("/comment/audit")
    public AjaxResult commentAudit(@RequestBody PublisherComment comment) {
        return toAjax(commentService.auditComment(comment));
    }

    // ========== 虚拟专辑管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:album:list')")
    @GetMapping("/album/list")
    public TableDataInfo albumList(PublisherVirtualAlbum album) {
        startPage();
        List<PublisherVirtualAlbum> list = albumService.selectAlbumList(album);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:album:add')")
    @PostMapping("/album")
    public AjaxResult albumAdd(@RequestBody PublisherVirtualAlbum album) {
        return toAjax(albumService.insertAlbum(album));
    }

    @PreAuthorize("@ss.hasPermi('publisher:album:edit')")
    @PutMapping("/album")
    public AjaxResult albumEdit(@RequestBody PublisherVirtualAlbum album) {
        return toAjax(albumService.updateAlbum(album));
    }

    @PreAuthorize("@ss.hasPermi('publisher:album:addArticle')")
    @PostMapping("/album/article")
    public AjaxResult albumAddArticle(@RequestBody PublisherAlbumArticle albumArticle) {
        return toAjax(albumArticleService.insertAlbumArticle(albumArticle));
    }
}