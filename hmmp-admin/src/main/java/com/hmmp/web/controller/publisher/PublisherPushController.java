package com.hmmp.web.controller.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.system.domain.publisher.PublisherPushKeyword;
import com.hmmp.system.domain.publisher.PublisherPushSubscriber;
import com.hmmp.system.service.publisher.IPublisherPushKeywordService;
import com.hmmp.system.service.publisher.IPublisherPushSubscriberService;

/**
 * 精准推送管理 Controller
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/publisher/push")
public class PublisherPushController extends BaseController {

    @Autowired
    private IPublisherPushSubscriberService pushSubscriberService;

    @Autowired
    private IPublisherPushKeywordService pushKeywordService;

    // ========== 订阅推送用户管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:push:subscriber:list')")
    @GetMapping("/subscriber/list")
    public TableDataInfo pushSubscriberList(PublisherPushSubscriber pushSubscriber) {
        startPage();
        List<PublisherPushSubscriber> list = pushSubscriberService.selectPushSubscriberList(pushSubscriber);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:push:subscriber:add')")
    @PostMapping("/subscriber")
    public AjaxResult pushSubscriberAdd(@RequestBody PublisherPushSubscriber pushSubscriber) {
        return toAjax(pushSubscriberService.insertPushSubscriber(pushSubscriber));
    }

    @PreAuthorize("@ss.hasPermi('publisher:push:subscriber:remove')")
    @DeleteMapping("/subscriber/{subIds}")
    public AjaxResult pushSubscriberRemove(@PathVariable Long[] subIds) {
        return toAjax(pushSubscriberService.deletePushSubscriberByIds(subIds));
    }

    // ========== 关键词推送管理 ==========

    @PreAuthorize("@ss.hasPermi('publisher:push:keyword:list')")
    @GetMapping("/keyword/list")
    public TableDataInfo pushKeywordList(PublisherPushKeyword pushKeyword) {
        startPage();
        List<PublisherPushKeyword> list = pushKeywordService.selectPushKeywordList(pushKeyword);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('publisher:push:keyword:push')")
    @PostMapping("/keyword")
    public AjaxResult pushKeywordExecute(@RequestBody PublisherPushKeyword pushKeyword) {
        return toAjax(pushKeywordService.executePush(pushKeyword));
    }
}