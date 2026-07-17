package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorManuscript;
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

/**
 * 稿件管理 业务层
 * 
 * @author hmmp
 */
public interface IEditorManuscriptService {

    /**
     * 查询稿件列表
     */
    public List<EditorManuscript> selectManuscriptList(EditorManuscript manuscript);

    /**
     * 审理周期统计明细
     */
    public List<StatAuditCycle> selectAuditCycleList(StatAuditCycle query);

    /**
     * 出版周期统计明细
     */
    public List<StatPublishCycle> selectPublishCycleList(StatPublishCycle query);

    /**
     * 责编处理周期统计明细
     */
    public List<StatEditProcessCycle> selectEditProcessCycleList(StatEditProcessCycle query);

    /**
     * 加工周期统计明细
     */
    public List<StatEditArticleCycle> selectEditArticleCycleList(StatEditArticleCycle query);

    /**
     * 审稿周期统计明细（按审稿记录）
     */
    public List<StatReviewCycle> selectReviewCycleList(StatReviewCycle query);

    /**
     * 期刊统计聚合
     */
    public StatJournalTotal selectJournalTotal(StatJournalQuery query);

    /**
     * 责编工作量统计（按责任编辑分组）
     */
    public List<StatEditWork> selectEditWorkList(StatEditWorkQuery query);

    /**
     * 责编加工工作量统计（按责任编辑分组，按加工阶段汇总）
     */
    public List<StatEditProduction> selectEditProductionList(StatEditProductionQuery query);

    /**
     * 用户统计（按系统用户聚合投稿与审稿数据）
     */
    public List<StatUser> selectUserStatList(StatUserQuery query);

    /**
     * 审稿统计（按审稿人聚合审稿数据）
     */
    public List<StatReviewerAudit> selectReviewerAuditList(StatReviewerAuditQuery query);

    /**
     * 被引文章查询
     */
    public List<StatReferencedArticle> selectReferencedArticleList(StatReferencedArticle query);

    /**
     * 根据稿件ID查询稿件
     */
    public EditorManuscript selectManuscriptById(Long manuscriptId);

    /**
     * 根据稿件编号查询稿件
     */
    public EditorManuscript selectManuscriptByFileNo(String fileNo);

    /**
     * 新增稿件（收稿登记）
     */
    public int insertManuscript(EditorManuscript manuscript);

    /**
     * 修改稿件
     */
    public int updateManuscript(EditorManuscript manuscript);

    /**
     * 删除稿件（逻辑删除）
     */
    public int deleteManuscriptById(Long manuscriptId);

    /**
     * 批量删除稿件
     */
    public int deleteManuscriptByIds(Long[] manuscriptIds);

    /**
     * 更新稿件状态
     */
    public int updateManuscriptStatus(EditorManuscript manuscript);

    /**
     * 分配责任编辑
     */
    public int assignEditor(EditorManuscript manuscript);

    /**
     * 送审
     */
    public int sendForReview(EditorManuscript manuscript);

    /**
     * 退修
     */
    public int revise(EditorManuscript manuscript);

    /**
     * 修回确认
     */
    public int confirmRevision(EditorManuscript manuscript);

    /**
     * 录用
     */
    public int adopt(EditorManuscript manuscript);

    /**
     * 退稿
     */
    public int reject(EditorManuscript manuscript);

    /**
     * 发表
     */
    public int publish(EditorManuscript manuscript);

    /**
     * 归档
     */
    public int archive(EditorManuscript manuscript);
}