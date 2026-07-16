package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorManuscript;
import com.hmmp.system.domain.EditorManuscriptLog;
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