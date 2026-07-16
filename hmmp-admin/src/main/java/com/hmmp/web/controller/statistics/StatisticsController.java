package com.hmmp.web.controller.statistics;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.system.domain.StatData;
import com.hmmp.system.domain.StatSiteAccess;
import com.hmmp.system.service.IStatDataService;
import com.hmmp.system.service.IStatSiteAccessService;

/**
 * 统计中心
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController extends BaseController
{
    @Autowired
    private IStatDataService statDataService;

    @Autowired
    private IStatSiteAccessService statSiteAccessService;

    /**
     * 期刊统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:total')")
    @GetMapping("/integrative/total")
    public AjaxResult total()
    {
        StatData statData = new StatData();
        statData.setStatType("journal_total");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 审理周期统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:auditCycle')")
    @GetMapping("/integrative/auditCycle")
    public AjaxResult auditCycle()
    {
        StatData statData = new StatData();
        statData.setStatType("audit_cycle");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 出版周期统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:publishCycle')")
    @GetMapping("/integrative/publishCycle")
    public AjaxResult publishCycle()
    {
        StatData statData = new StatData();
        statData.setStatType("publish_cycle");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 责编处理周期统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editProcessCycle')")
    @GetMapping("/integrative/editProcessCycle")
    public AjaxResult editProcessCycle()
    {
        StatData statData = new StatData();
        statData.setStatType("edit_process_cycle");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 加工周期统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editArticleCycle')")
    @GetMapping("/integrative/editArticleCycle")
    public AjaxResult editArticleCycle()
    {
        StatData statData = new StatData();
        statData.setStatType("edit_article_cycle");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 责编工作量统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editWork')")
    @GetMapping("/integrative/editWork")
    public AjaxResult editWork()
    {
        StatData statData = new StatData();
        statData.setStatType("edit_work");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 责编加工工作量统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editProduction')")
    @GetMapping("/integrative/editProduction")
    public AjaxResult editProduction()
    {
        StatData statData = new StatData();
        statData.setStatType("edit_production");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 审稿统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:reviewerAudit')")
    @GetMapping("/integrative/reviewerAudit")
    public AjaxResult reviewerAudit()
    {
        StatData statData = new StatData();
        statData.setStatType("reviewer_audit");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 被引文章查询
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:referencedArticle')")
    @GetMapping("/integrative/referencedArticle")
    public AjaxResult referencedArticle()
    {
        StatData statData = new StatData();
        statData.setStatType("referenced_article");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 审稿周期统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:reviewCycle')")
    @GetMapping("/integrative/reviewCycle")
    public AjaxResult reviewCycle()
    {
        StatData statData = new StatData();
        statData.setStatType("review_cycle");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 用户统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:userStat')")
    @GetMapping("/integrative/userStat")
    public AjaxResult userStat()
    {
        StatData statData = new StatData();
        statData.setStatType("user_stat");
        List<StatData> list = statDataService.selectStatDataList(statData);
        return success(list);
    }

    /**
     * 网站访问量统计
     */
    @PreAuthorize("@ss.hasPermi('statistics:site:total')")
    @GetMapping("/site/total")
    public AjaxResult siteTotal(StatSiteAccess statSiteAccess)
    {
        List<StatSiteAccess> list = statSiteAccessService.selectStatSiteAccessList(statSiteAccess);
        return success(list);
    }
}