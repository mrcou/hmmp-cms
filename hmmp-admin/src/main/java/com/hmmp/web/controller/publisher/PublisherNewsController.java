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
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.system.domain.publisher.PublisherNewsColumn;
import com.hmmp.system.domain.publisher.PublisherNewsContent;
import com.hmmp.system.service.publisher.IPublisherNewsColumnService;
import com.hmmp.system.service.publisher.IPublisherNewsContentService;

/**
 * 发行中心 - 新闻管理
 */
@RestController
@RequestMapping("/publisher/news")
public class PublisherNewsController extends BaseController
{
    @Autowired
    private IPublisherNewsColumnService columnService;

    @Autowired
    private IPublisherNewsContentService contentService;

    @PreAuthorize("@ss.hasPermi('publisher:newsColumn:list')")
    @GetMapping("/column/list")
    public TableDataInfo columnList(PublisherNewsColumn column)
    {
        startPage();
        return getDataTable(columnService.selectNewsColumnList(column));
    }

    @PreAuthorize("@ss.hasPermi('publisher:newsColumn:query')")
    @GetMapping("/column/{columnId}")
    public AjaxResult columnGetInfo(@PathVariable Long columnId)
    {
        return success(columnService.selectNewsColumnById(columnId));
    }

    @Log(title = "新闻栏目", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('publisher:newsColumn:add')")
    @PostMapping("/column")
    public AjaxResult columnAdd(@RequestBody PublisherNewsColumn column)
    {
        if (!columnService.checkNewsColumnNameUnique(column))
        {
            return error("该栏目已存在");
        }
        return toAjax(columnService.insertNewsColumn(column));
    }

    @Log(title = "新闻栏目", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('publisher:newsColumn:edit')")
    @PutMapping("/column")
    public AjaxResult columnEdit(@RequestBody PublisherNewsColumn column)
    {
        if (!columnService.checkNewsColumnNameUnique(column))
        {
            return error("该栏目已存在");
        }
        return toAjax(columnService.updateNewsColumn(column));
    }

    @Log(title = "新闻栏目", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('publisher:newsColumn:remove')")
    @DeleteMapping("/column/{columnIds}")
    public AjaxResult columnRemove(@PathVariable Long[] columnIds)
    {
        return toAjax(columnService.deleteNewsColumnByIds(columnIds));
    }

    @PreAuthorize("@ss.hasPermi('publisher:newsContent:list')")
    @GetMapping("/content/list")
    public TableDataInfo contentList(PublisherNewsContent content)
    {
        startPage();
        return getDataTable(contentService.selectNewsContentList(content));
    }

    @PreAuthorize("@ss.hasPermi('publisher:newsContent:query')")
    @GetMapping("/content/{contentId}")
    public AjaxResult contentGetInfo(@PathVariable Long contentId)
    {
        return success(contentService.selectNewsContentById(contentId));
    }

    @Log(title = "新闻内容", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('publisher:newsContent:add')")
    @PostMapping("/content")
    public AjaxResult contentAdd(@RequestBody PublisherNewsContent content)
    {
        return toAjax(contentService.insertNewsContent(content));
    }

    @Log(title = "新闻内容", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('publisher:newsContent:edit')")
    @PutMapping("/content")
    public AjaxResult contentEdit(@RequestBody PublisherNewsContent content)
    {
        return toAjax(contentService.updateNewsContent(content));
    }

    @Log(title = "新闻内容排序", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('publisher:newsContent:edit')")
    @PutMapping("/content/sort")
    public AjaxResult contentSort(@RequestBody List<PublisherNewsContent> contents)
    {
        return toAjax(contentService.updateNewsContentSort(contents));
    }

    @Log(title = "新闻内容", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('publisher:newsContent:remove')")
    @DeleteMapping("/content/{contentIds}")
    public AjaxResult contentRemove(@PathVariable Long[] contentIds)
    {
        return toAjax(contentService.deleteNewsContentByIds(contentIds));
    }
}
