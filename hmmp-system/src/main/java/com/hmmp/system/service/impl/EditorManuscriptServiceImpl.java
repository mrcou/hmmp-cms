package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorManuscript;
import com.hmmp.system.domain.EditorManuscriptLog;
import com.hmmp.system.domain.StatAuditCycle;
import com.hmmp.system.domain.StatEditArticleCycle;
import com.hmmp.system.domain.StatEditProcessCycle;
import com.hmmp.system.domain.StatPublishCycle;
import com.hmmp.system.domain.StatReviewCycle;
import com.hmmp.system.domain.StatEditProduction;
import com.hmmp.system.domain.StatEditProductionQuery;
import com.hmmp.system.domain.StatEditWork;
import com.hmmp.system.domain.StatEditWorkQuery;
import com.hmmp.system.domain.StatJournalQuery;
import com.hmmp.system.domain.StatJournalTotal;
import com.hmmp.system.domain.StatReviewerAudit;
import com.hmmp.system.domain.StatReviewerAuditQuery;
import com.hmmp.system.domain.StatReferencedArticle;
import com.hmmp.system.domain.StatUser;
import com.hmmp.system.domain.StatUserQuery;
import com.hmmp.system.mapper.EditorManuscriptMapper;
import com.hmmp.system.mapper.EditorManuscriptLogMapper;
import com.hmmp.system.service.IEditorManuscriptLogService;
import com.hmmp.system.service.IEditorManuscriptService;

/**
 * 稿件管理 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorManuscriptServiceImpl implements IEditorManuscriptService {

    @Autowired
    private EditorManuscriptMapper manuscriptMapper;

    @Autowired
    private EditorManuscriptLogMapper manuscriptLogMapper;

    @Override
    public List<EditorManuscript> selectManuscriptList(EditorManuscript manuscript) {
        return manuscriptMapper.selectManuscriptList(manuscript);
    }

    @Override
    public List<StatAuditCycle> selectAuditCycleList(StatAuditCycle query) {
        return manuscriptMapper.selectAuditCycleList(query);
    }

    @Override
    public List<StatPublishCycle> selectPublishCycleList(StatPublishCycle query) {
        return manuscriptMapper.selectPublishCycleList(query);
    }

    @Override
    public List<StatEditProcessCycle> selectEditProcessCycleList(StatEditProcessCycle query) {
        return manuscriptMapper.selectEditProcessCycleList(query);
    }

    @Override
    public List<StatEditArticleCycle> selectEditArticleCycleList(StatEditArticleCycle query) {
        return manuscriptMapper.selectEditArticleCycleList(query);
    }

    @Override
    public List<StatReviewCycle> selectReviewCycleList(StatReviewCycle query) {
        return manuscriptMapper.selectReviewCycleList(query);
    }

    @Override
    public StatJournalTotal selectJournalTotal(StatJournalQuery query) {
        StatJournalTotal total = manuscriptMapper.selectJournalTotal(query);
        if (total == null) {
            total = new StatJournalTotal();
        }
        fillJournalAverages(total, query);
        return total;
    }

    @Override
    public List<StatEditWork> selectEditWorkList(StatEditWorkQuery query) {
        return manuscriptMapper.selectEditWorkList(query);
    }

    @Override
    public List<StatEditProduction> selectEditProductionList(StatEditProductionQuery query) {
        return manuscriptMapper.selectEditProductionList(query);
    }

    @Override
    public List<StatUser> selectUserStatList(StatUserQuery query) {
        return manuscriptMapper.selectUserStatList(query);
    }

    @Override
    public List<StatReviewerAudit> selectReviewerAuditList(StatReviewerAuditQuery query) {
        return manuscriptMapper.selectReviewerAuditList(query);
    }

    @Override
    public List<StatReferencedArticle> selectReferencedArticleList(StatReferencedArticle query) {
        return manuscriptMapper.selectReferencedArticleList(query);
    }

    /**
     * 按年份跨度补齐年平均与比率字段
     */
    private void fillJournalAverages(StatJournalTotal total, StatJournalQuery query) {
        int yearSpan = resolveYearSpan(query);
        total.setYearSpan(yearSpan);
        long receive = nz(total.getReceiveCount());
        total.setReceiveCount(receive);
        total.setPublishCount(nz(total.getPublishCount()));
        total.setRejectCount(nz(total.getRejectCount()));
        total.setInitialRejectCount(nz(total.getInitialRejectCount()));
        total.setReviewRejectCount(nz(total.getReviewRejectCount()));
        total.setReReviewRejectCount(nz(total.getReReviewRejectCount()));
        total.setFinalRejectCount(nz(total.getFinalRejectCount()));
        total.setAdoptCount(nz(total.getAdoptCount()));
        total.setSendReviewCount(nz(total.getSendReviewCount()));
        total.setFundCount(nz(total.getFundCount()));
        total.setInitialReviewTimes(nz(total.getInitialReviewTimes()));
        total.setPeerReviewTimes(nz(total.getPeerReviewTimes()));
        total.setReReviewTimes(nz(total.getReReviewTimes()));
        total.setFinalReviewTimes(nz(total.getFinalReviewTimes()));

        total.setAvgReceiveCount(avg(receive, yearSpan));
        total.setAvgPublishCount(avg(total.getPublishCount(), yearSpan));
        total.setAvgRejectCount(avg(total.getRejectCount(), yearSpan));
        total.setAvgInitialRejectCount(avg(total.getInitialRejectCount(), yearSpan));
        total.setAvgReviewRejectCount(avg(total.getReviewRejectCount(), yearSpan));
        total.setAvgReReviewRejectCount(avg(total.getReReviewRejectCount(), yearSpan));
        total.setAvgFinalRejectCount(avg(total.getFinalRejectCount(), yearSpan));
        total.setAvgAdoptCount(avg(total.getAdoptCount(), yearSpan));
        total.setAvgSendReviewCount(avg(total.getSendReviewCount(), yearSpan));
        total.setAvgFundCount(avg(total.getFundCount(), yearSpan));

        total.setPublishRate(rate(total.getPublishCount(), receive));
        total.setRejectRate(rate(total.getRejectCount(), receive));
        total.setInitialRejectRate(rate(total.getInitialRejectCount(), receive));
        total.setReviewRejectRate(rate(total.getReviewRejectCount(), receive));
        total.setReReviewRejectRate(rate(total.getReReviewRejectCount(), receive));
        total.setFinalRejectRate(rate(total.getFinalRejectCount(), receive));
        total.setAdoptRate(rate(total.getAdoptCount(), receive));
        total.setSendReviewRate(rate(total.getSendReviewCount(), receive));
        total.setFundRate(rate(total.getFundCount(), receive));
    }

    private static long nz(Long value) {
        return value == null ? 0L : value;
    }

    private static double avg(long value, int yearSpan) {
        return Math.round(value * 100.0 / yearSpan) / 100.0;
    }

    private static double rate(long part, long total) {
        if (total <= 0) {
            return 0D;
        }
        return Math.round(part * 10000.0 / total) / 100.0;
    }

    private static int resolveYearSpan(StatJournalQuery query) {
        if (query == null || query.getBeginTime() == null || query.getEndTime() == null) {
            return 1;
        }
        try {
            String begin = query.getBeginTime().trim();
            String end = query.getEndTime().trim();
            int beginYear = Integer.parseInt(begin.substring(0, 4));
            int endYear = Integer.parseInt(end.substring(0, 4));
            return Math.max(1, endYear - beginYear + 1);
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public EditorManuscript selectManuscriptById(Long manuscriptId) {
        return manuscriptMapper.selectManuscriptById(manuscriptId);
    }

    @Override
    public EditorManuscript selectManuscriptByFileNo(String fileNo) {
        return manuscriptMapper.selectManuscriptByFileNo(fileNo);
    }

    @Override
    @Transactional
    public int insertManuscript(EditorManuscript manuscript) {
        manuscript.setCreateBy(SecurityUtils.getUsername());
        manuscript.setCreateTime(new Date());
        manuscript.setStatus("01"); // 新收稿
        int rows = manuscriptMapper.insertManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("01");
        log.setNewStatus("01");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("收稿登记");
        manuscriptLogMapper.insertLog(log);
        return rows;
    }

    @Override
    public int updateManuscript(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        return manuscriptMapper.updateManuscript(manuscript);
    }

    @Override
    public int deleteManuscriptById(Long manuscriptId) {
        return manuscriptMapper.deleteManuscriptById(manuscriptId);
    }

    @Override
    public int deleteManuscriptByIds(Long[] manuscriptIds) {
        return manuscriptMapper.deleteManuscriptByIds(manuscriptIds);
    }

    @Override
    public int updateManuscriptStatus(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        return manuscriptMapper.updateManuscriptStatus(manuscript);
    }

    @Override
    @Transactional
    public int assignEditor(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        manuscript.setAssignTime(new Date());
        manuscript.setStatus("02"); // 新分配
        manuscriptMapper.updateManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("02");
        log.setNewStatus("02");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("分配责任编辑：" + manuscript.getEditorName());
        manuscriptLogMapper.insertLog(log);
        return 1;
    }

    @Override
    @Transactional
    public int sendForReview(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        manuscript.setStatus("03"); // 审稿中
        manuscript.setReviewStartTime(new Date());
        manuscriptMapper.updateManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("03");
        log.setNewStatus("03");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("送审");
        manuscriptLogMapper.insertLog(log);
        return 1;
    }

    @Override
    @Transactional
    public int revise(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        manuscript.setStatus("04"); // 已退修
        manuscript.setRevisionCount(manuscript.getRevisionCount() != null ? manuscript.getRevisionCount() + 1 : 1);
        manuscriptMapper.updateManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("05");
        log.setNewStatus("04");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("退修");
        manuscriptLogMapper.insertLog(log);
        return 1;
    }

    @Override
    @Transactional
    public int confirmRevision(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        manuscript.setStatus("05"); // 已修回
        manuscriptMapper.updateManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("06");
        log.setNewStatus("05");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("修回确认");
        manuscriptLogMapper.insertLog(log);
        return 1;
    }

    @Override
    @Transactional
    public int adopt(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        manuscript.setStatus("06"); // 已采用
        manuscript.setConclusionTime(new Date());
        manuscriptMapper.updateManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("07");
        log.setNewStatus("06");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("录用");
        manuscriptLogMapper.insertLog(log);
        return 1;
    }

    @Override
    @Transactional
    public int reject(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        manuscript.setStatus("07"); // 已退稿
        manuscript.setConclusionTime(new Date());
        manuscriptMapper.updateManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("08");
        log.setNewStatus("07");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("退稿：" + (manuscript.getConclusion() != null ? manuscript.getConclusion() : ""));
        manuscriptLogMapper.insertLog(log);
        return 1;
    }

    @Override
    @Transactional
    public int publish(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        manuscript.setStatus("08"); // 已发表
        manuscript.setPublishTime(new Date());
        manuscriptMapper.updateManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("11");
        log.setNewStatus("08");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("发表");
        manuscriptLogMapper.insertLog(log);
        return 1;
    }

    @Override
    @Transactional
    public int archive(EditorManuscript manuscript) {
        manuscript.setUpdateBy(SecurityUtils.getUsername());
        manuscript.setStatus("09"); // 已归档
        manuscript.setArchiveTime(new Date());
        manuscriptMapper.updateManuscript(manuscript);
        // 记录操作日志
        EditorManuscriptLog log = new EditorManuscriptLog();
        log.setManuscriptId(manuscript.getManuscriptId());
        log.setLogType("12");
        log.setNewStatus("09");
        log.setOperatorId(SecurityUtils.getUserId());
        log.setOperatorName(SecurityUtils.getUsername());
        log.setOperateTime(new Date());
        log.setRemark("归档");
        manuscriptLogMapper.insertLog(log);
        return 1;
    }
}