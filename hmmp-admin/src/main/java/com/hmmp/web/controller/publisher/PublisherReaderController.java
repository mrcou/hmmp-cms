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
import com.hmmp.system.domain.publisher.PublisherReader;
import com.hmmp.system.domain.publisher.PublisherSubscriber;
import com.hmmp.system.service.publisher.IPublisherReaderService;
import com.hmmp.system.service.publisher.IPublisherSubscriberService;

/**
 * 读者用户管理 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/publisher")
public class PublisherReaderController extends BaseController {

    @Autowired
    private IPublisherReaderService readerService;

    @Autowired
    private IPublisherSubscriberService subscriberService;

    // ========== 读者管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:reader:list')")
    @GetMapping("/reader/list")
    public TableDataInfo readerList(PublisherReader reader) {
        startPage();
        List<PublisherReader> list = readerService.selectReaderList(reader);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:reader:query')")
    @GetMapping("/reader/{readerId}")
    public AjaxResult readerGetInfo(@PathVariable Long readerId) {
        return success(readerService.selectReaderById(readerId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:reader:add')")
    @PostMapping("/reader")
    public AjaxResult readerAdd(@RequestBody PublisherReader reader) {
        return toAjax(readerService.insertReader(reader));
    }

    @PreAuthorize("@ss.hasPermi('publisher:reader:edit')")
    @PutMapping("/reader")
    public AjaxResult readerEdit(@RequestBody PublisherReader reader) {
        return toAjax(readerService.updateReader(reader));
    }

    @PreAuthorize("@ss.hasPermi('publisher:reader:remove')")
    @DeleteMapping("/reader/{readerIds}")
    public AjaxResult readerRemove(@PathVariable Long[] readerIds) {
        return toAjax(readerService.deleteReaderByIds(readerIds));
    }

    // ========== 订户管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:subscriber:list')")
    @GetMapping("/subscriber/list")
    public TableDataInfo subscriberList(PublisherSubscriber subscriber) {
        startPage();
        List<PublisherSubscriber> list = subscriberService.selectSubscriberList(subscriber);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:subscriber:query')")
    @GetMapping("/subscriber/{subscriberId}")
    public AjaxResult subscriberGetInfo(@PathVariable Long subscriberId) {
        return success(subscriberService.selectSubscriberById(subscriberId));
    }

    @PreAuthorize("@ss.hasPermi('publisher:subscriber:add')")
    @PostMapping("/subscriber")
    public AjaxResult subscriberAdd(@RequestBody PublisherSubscriber subscriber) {
        return toAjax(subscriberService.insertSubscriber(subscriber));
    }

    @PreAuthorize("@ss.hasPermi('publisher:subscriber:edit')")
    @PutMapping("/subscriber")
    public AjaxResult subscriberEdit(@RequestBody PublisherSubscriber subscriber) {
        return toAjax(subscriberService.updateSubscriber(subscriber));
    }

    @PreAuthorize("@ss.hasPermi('publisher:subscriber:remove')")
    @DeleteMapping("/subscriber/{subscriberIds}")
    public AjaxResult subscriberRemove(@PathVariable Long[] subscriberIds) {
        return toAjax(subscriberService.deleteSubscriberByIds(subscriberIds));
    }
}