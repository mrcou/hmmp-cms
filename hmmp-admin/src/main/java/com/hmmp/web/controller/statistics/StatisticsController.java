package com.hmmp.web.controller.statistics;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.common.utils.poi.ExcelUtil;
import com.hmmp.system.domain.StatAuditCycle;
import com.hmmp.system.domain.StatData;
import com.hmmp.system.domain.StatEditArticleCycle;
import com.hmmp.system.domain.StatEditProcessCycle;
import com.hmmp.system.domain.StatEditProduction;
import com.hmmp.system.domain.StatEditProductionQuery;
import com.hmmp.system.domain.StatEditWork;
import com.hmmp.system.domain.StatEditWorkQuery;
import com.hmmp.system.domain.StatJournalQuery;
import com.hmmp.system.domain.StatJournalTotal;
import com.hmmp.system.domain.StatPublishCycle;
import com.hmmp.system.domain.StatReviewCycle;
import com.hmmp.system.domain.StatSiteAccess;
import com.hmmp.system.domain.StatSiteVisit;
import com.hmmp.system.domain.StatSiteVisitQuery;
import com.hmmp.system.domain.StatReferencedArticle;
import com.hmmp.system.domain.StatReviewerAudit;
import com.hmmp.system.domain.StatReviewerAuditQuery;
import com.hmmp.system.domain.StatUser;
import com.hmmp.system.domain.StatUserQuery;
import com.hmmp.system.service.IEditorManuscriptService;
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

    @Autowired
    private IEditorManuscriptService manuscriptService;

    /**
     * 期刊统计（按收稿时间聚合；时间支持年 / 年-月 / 年-月-日）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:total')")
    @GetMapping("/integrative/total")
    public AjaxResult total(
            @RequestParam(value = "beginTime", required = false) String beginTime,
            @RequestParam(value = "endTime", required = false) String endTime)
    {
        StatJournalQuery query = new StatJournalQuery();
        query.setBeginTime(normalizeBeginTime(beginTime));
        query.setEndTime(normalizeEndTime(endTime));
        StatJournalTotal total = manuscriptService.selectJournalTotal(query);
        return success(total);
    }

    /**
     * 将「年 / 年-月 / 年-月-日」规范为区间起始日 yyyy-MM-dd；空则取当年 01-01
     */
    private String normalizeBeginTime(String raw)
    {
        String value = StringUtils.trim(raw);
        if (StringUtils.isEmpty(value))
        {
            return LocalDate.now().getYear() + "-01-01";
        }
        if (value.matches("^\\d{4}$"))
        {
            return value + "-01-01";
        }
        if (value.matches("^\\d{4}-\\d{1,2}$"))
        {
            YearMonth ym = YearMonth.parse(padYearMonth(value));
            return ym.atDay(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$"))
        {
            return LocalDate.parse(padYearMonthDay(value)).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return value;
    }

    /**
     * 将「年 / 年-月 / 年-月-日」规范为区间截止日 yyyy-MM-dd；空则取当年 12-31
     */
    private String normalizeEndTime(String raw)
    {
        String value = StringUtils.trim(raw);
        if (StringUtils.isEmpty(value))
        {
            return LocalDate.now().getYear() + "-12-31";
        }
        if (value.matches("^\\d{4}$"))
        {
            return value + "-12-31";
        }
        if (value.matches("^\\d{4}-\\d{1,2}$"))
        {
            YearMonth ym = YearMonth.parse(padYearMonth(value));
            return ym.atEndOfMonth().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$"))
        {
            return LocalDate.parse(padYearMonthDay(value)).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return value;
    }

    private String padYearMonth(String value)
    {
        String[] parts = value.split("-");
        return parts[0] + "-" + (parts[1].length() == 1 ? "0" + parts[1] : parts[1]);
    }

    private String padYearMonthDay(String value)
    {
        String[] parts = value.split("-");
        String month = parts[1].length() == 1 ? "0" + parts[1] : parts[1];
        String day = parts[2].length() == 1 ? "0" + parts[2] : parts[2];
        return parts[0] + "-" + month + "-" + day;
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
     * 审理周期统计明细列表（按稿件）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:auditCycle')")
    @GetMapping("/integrative/auditCycle/list")
    public TableDataInfo auditCycleList(StatAuditCycle query)
    {
        startPage();
        List<StatAuditCycle> list = manuscriptService.selectAuditCycleList(query);
        return getDataTable(list);
    }

    /**
     * 审理周期统计明细导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:auditCycle')")
    @Log(title = "审理周期统计", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/auditCycle/export")
    public void auditCycleExport(HttpServletResponse response, StatAuditCycle query)
    {
        List<StatAuditCycle> list = manuscriptService.selectAuditCycleList(query);
        ExcelUtil<StatAuditCycle> util = new ExcelUtil<StatAuditCycle>(StatAuditCycle.class);
        util.exportExcel(response, list, "审理周期统计");
    }

    /**
     * 出版周期统计（预聚合壳，兼容旧调用）
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
     * 出版周期统计明细列表（按稿件）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:publishCycle')")
    @GetMapping("/integrative/publishCycle/list")
    public TableDataInfo publishCycleList(StatPublishCycle query)
    {
        startPage();
        List<StatPublishCycle> list = manuscriptService.selectPublishCycleList(query);
        return getDataTable(list);
    }

    /**
     * 出版周期统计明细导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:publishCycle')")
    @Log(title = "出版周期统计", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/publishCycle/export")
    public void publishCycleExport(HttpServletResponse response, StatPublishCycle query)
    {
        List<StatPublishCycle> list = manuscriptService.selectPublishCycleList(query);
        ExcelUtil<StatPublishCycle> util = new ExcelUtil<StatPublishCycle>(StatPublishCycle.class);
        util.exportExcel(response, list, "出版周期统计");
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
     * 责编处理周期统计明细列表（按稿件）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editProcessCycle')")
    @GetMapping("/integrative/editProcessCycle/list")
    public TableDataInfo editProcessCycleList(StatEditProcessCycle query)
    {
        startPage();
        List<StatEditProcessCycle> list = manuscriptService.selectEditProcessCycleList(query);
        return getDataTable(list);
    }

    /**
     * 责编处理周期统计明细导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editProcessCycle')")
    @Log(title = "责编处理周期统计", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/editProcessCycle/export")
    public void editProcessCycleExport(HttpServletResponse response, StatEditProcessCycle query)
    {
        List<StatEditProcessCycle> list = manuscriptService.selectEditProcessCycleList(query);
        ExcelUtil<StatEditProcessCycle> util = new ExcelUtil<StatEditProcessCycle>(StatEditProcessCycle.class);
        util.exportExcel(response, list, "责编处理周期统计");
    }

    /**
     * 加工周期统计（预聚合壳，兼容旧调用）
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
     * 加工周期统计明细列表（按编辑加工记录）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editArticleCycle')")
    @GetMapping("/integrative/editArticleCycle/list")
    public TableDataInfo editArticleCycleList(StatEditArticleCycle query)
    {
        startPage();
        List<StatEditArticleCycle> list = manuscriptService.selectEditArticleCycleList(query);
        return getDataTable(list);
    }

    /**
     * 加工周期统计明细导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editArticleCycle')")
    @Log(title = "加工周期统计", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/editArticleCycle/export")
    public void editArticleCycleExport(HttpServletResponse response, StatEditArticleCycle query)
    {
        List<StatEditArticleCycle> list = manuscriptService.selectEditArticleCycleList(query);
        ExcelUtil<StatEditArticleCycle> util = new ExcelUtil<StatEditArticleCycle>(StatEditArticleCycle.class);
        util.exportExcel(response, list, "加工周期统计");
    }

    /**
     * 责编工作量统计（按责任编辑分组；时间支持年 / 年-月 / 年-月-日）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editWork')")
    @GetMapping("/integrative/editWork")
    public AjaxResult editWork(
            @RequestParam(value = "beginTime", required = false) String beginTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "dateField", required = false) String dateField)
    {
        StatEditWorkQuery query = new StatEditWorkQuery();
        query.setBeginTime(normalizeBeginTime(beginTime));
        query.setEndTime(normalizeEndTime(endTime));
        query.setDateField(normalizeEditWorkDateField(dateField));
        List<StatEditWork> list = manuscriptService.selectEditWorkList(query);
        return success(list);
    }

    /**
     * 责编工作量统计导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editWork')")
    @Log(title = "责编工作量统计", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/editWork/export")
    public void editWorkExport(
            HttpServletResponse response,
            @RequestParam(value = "beginTime", required = false) String beginTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "dateField", required = false) String dateField)
    {
        StatEditWorkQuery query = new StatEditWorkQuery();
        query.setBeginTime(normalizeBeginTime(beginTime));
        query.setEndTime(normalizeEndTime(endTime));
        query.setDateField(normalizeEditWorkDateField(dateField));
        List<StatEditWork> list = manuscriptService.selectEditWorkList(query);
        ExcelUtil<StatEditWork> util = new ExcelUtil<StatEditWork>(StatEditWork.class);
        util.exportExcel(response, list, "责编工作量统计");
    }

    /**
     * 白名单校验时间字段，非法值回落到投稿日期
     */
    private String normalizeEditWorkDateField(String raw)
    {
        String value = StringUtils.trim(raw);
        if ("assignTime".equals(value)
                || "reviewStartTime".equals(value)
                || "conclusionTime".equals(value)
                || "publishTime".equals(value)
                || "receiveTime".equals(value))
        {
            return value;
        }
        return "receiveTime";
    }

    /**
     * 责编加工工作量统计（按责任编辑分组；时间支持年 / 年-月 / 年-月-日）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editProduction')")
    @GetMapping("/integrative/editProduction")
    public AjaxResult editProduction(
            @RequestParam(value = "beginTime", required = false) String beginTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "dateField", required = false) String dateField)
    {
        StatEditProductionQuery query = new StatEditProductionQuery();
        query.setBeginTime(normalizeBeginTime(beginTime));
        query.setEndTime(normalizeEndTime(endTime));
        query.setDateField(normalizeEditProductionDateField(dateField));
        List<StatEditProduction> list = manuscriptService.selectEditProductionList(query);
        return success(list);
    }

    /**
     * 责编加工工作量统计导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:editProduction')")
    @Log(title = "责编加工工作量", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/editProduction/export")
    public void editProductionExport(
            HttpServletResponse response,
            @RequestParam(value = "beginTime", required = false) String beginTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "dateField", required = false) String dateField)
    {
        StatEditProductionQuery query = new StatEditProductionQuery();
        query.setBeginTime(normalizeBeginTime(beginTime));
        query.setEndTime(normalizeEndTime(endTime));
        query.setDateField(normalizeEditProductionDateField(dateField));
        List<StatEditProduction> list = manuscriptService.selectEditProductionList(query);
        ExcelUtil<StatEditProduction> util = new ExcelUtil<StatEditProduction>(StatEditProduction.class);
        util.exportExcel(response, list, "责编加工工作量");
    }

    /**
     * 白名单校验加工工作量时间字段，非法值回落到完成日期
     */
    private String normalizeEditProductionDateField(String raw)
    {
        String value = StringUtils.trim(raw);
        if ("editStartTime".equals(value)
                || "createTime".equals(value)
                || "editEndTime".equals(value))
        {
            return value;
        }
        return "editEndTime";
    }

    /**
     * 审稿统计（按审稿人聚合，分页）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:reviewerAudit')")
    @GetMapping("/integrative/reviewerAudit/list")
    public TableDataInfo reviewerAuditList(StatReviewerAuditQuery query)
    {
        startPage();
        List<StatReviewerAudit> list = manuscriptService.selectReviewerAuditList(query);
        return getDataTable(list);
    }

    /**
     * 审稿统计导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:reviewerAudit')")
    @Log(title = "审稿统计", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/reviewerAudit/export")
    public void reviewerAuditExport(HttpServletResponse response, StatReviewerAuditQuery query)
    {
        List<StatReviewerAudit> list = manuscriptService.selectReviewerAuditList(query);
        ExcelUtil<StatReviewerAudit> util = new ExcelUtil<StatReviewerAudit>(StatReviewerAudit.class);
        util.exportExcel(response, list, "审稿统计");
    }

    /**
     * 审稿统计（旧汇总接口，保留兼容）
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
     * 被引文章查询（预聚合壳，兼容旧调用）
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
     * 被引文章查询明细列表
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:referencedArticle')")
    @GetMapping("/integrative/referencedArticle/list")
    public TableDataInfo referencedArticleList(StatReferencedArticle query)
    {
        startPage();
        List<StatReferencedArticle> list = manuscriptService.selectReferencedArticleList(query);
        return getDataTable(list);
    }

    /**
     * 被引文章查询导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:referencedArticle')")
    @Log(title = "被引文章查询", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/referencedArticle/export")
    public void referencedArticleExport(HttpServletResponse response, StatReferencedArticle query)
    {
        List<StatReferencedArticle> list = manuscriptService.selectReferencedArticleList(query);
        ExcelUtil<StatReferencedArticle> util = new ExcelUtil<StatReferencedArticle>(StatReferencedArticle.class);
        util.exportExcel(response, list, "被引文章查询");
    }

    /**
     * 审稿周期统计（预聚合壳，兼容旧调用）
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
     * 审稿周期统计明细列表（按审稿记录）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:reviewCycle')")
    @GetMapping("/integrative/reviewCycle/list")
    public TableDataInfo reviewCycleList(StatReviewCycle query)
    {
        startPage();
        List<StatReviewCycle> list = manuscriptService.selectReviewCycleList(query);
        return getDataTable(list);
    }

    /**
     * 审稿周期统计明细导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:reviewCycle')")
    @Log(title = "审稿周期统计", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/reviewCycle/export")
    public void reviewCycleExport(HttpServletResponse response, StatReviewCycle query)
    {
        List<StatReviewCycle> list = manuscriptService.selectReviewCycleList(query);
        ExcelUtil<StatReviewCycle> util = new ExcelUtil<StatReviewCycle>(StatReviewCycle.class);
        util.exportExcel(response, list, "审稿周期统计");
    }

    /**
     * 用户统计（按系统用户聚合投稿与审稿数据，分页）
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:userStat')")
    @GetMapping("/integrative/userStat/list")
    public TableDataInfo userStatList(StatUserQuery query)
    {
        startPage();
        List<StatUser> list = manuscriptService.selectUserStatList(query);
        return getDataTable(list);
    }

    /**
     * 用户统计导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:integrative:userStat')")
    @Log(title = "用户统计", businessType = BusinessType.EXPORT)
    @PostMapping("/integrative/userStat/export")
    public void userStatExport(HttpServletResponse response, StatUserQuery query)
    {
        List<StatUser> list = manuscriptService.selectUserStatList(query);
        ExcelUtil<StatUser> util = new ExcelUtil<StatUser>(StatUser.class);
        util.exportExcel(response, list, "用户统计");
    }

    /**
     * 用户统计（旧汇总接口，保留兼容）
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
     * 网站访问量统计（按年汇总；时间支持年 / 年-月 / 年-月-日）
     */
    @PreAuthorize("@ss.hasPermi('statistics:site:total')")
    @GetMapping("/site/total")
    public AjaxResult siteTotal(
            @RequestParam(value = "beginTime", required = false) String beginTime,
            @RequestParam(value = "endTime", required = false) String endTime)
    {
        StatSiteVisitQuery query = new StatSiteVisitQuery();
        query.setBeginTime(normalizeBeginTime(beginTime));
        query.setEndTime(normalizeEndTime(endTime));
        List<StatSiteVisit> list = statSiteAccessService.selectSiteVisitByYear(query);
        return success(list);
    }

    /**
     * 网站访问量统计导出
     */
    @PreAuthorize("@ss.hasPermi('statistics:site:total')")
    @Log(title = "网站访问量统计", businessType = BusinessType.EXPORT)
    @PostMapping("/site/total/export")
    public void siteTotalExport(
            HttpServletResponse response,
            @RequestParam(value = "beginTime", required = false) String beginTime,
            @RequestParam(value = "endTime", required = false) String endTime)
    {
        StatSiteVisitQuery query = new StatSiteVisitQuery();
        query.setBeginTime(normalizeBeginTime(beginTime));
        query.setEndTime(normalizeEndTime(endTime));
        List<StatSiteVisit> list = statSiteAccessService.selectSiteVisitByYear(query);
        ExcelUtil<StatSiteVisit> util = new ExcelUtil<StatSiteVisit>(StatSiteVisit.class);
        util.exportExcel(response, list, "网站访问量统计");
    }

    /**
     * 网站访问日志明细（分页）
     */
    @PreAuthorize("@ss.hasPermi('statistics:site:total')")
    @GetMapping("/site/total/list")
    public TableDataInfo siteTotalList(StatSiteAccess statSiteAccess)
    {
        startPage();
        List<StatSiteAccess> list = statSiteAccessService.selectStatSiteAccessList(statSiteAccess);
        return getDataTable(list);
    }
}