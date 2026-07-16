package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorAudit;
import com.hmmp.system.mapper.EditorAuditMapper;
import com.hmmp.system.service.IEditorAuditService;

/**
 * 审稿管理 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorAuditServiceImpl implements IEditorAuditService {

    @Autowired
    private EditorAuditMapper auditMapper;

    @Override
    public List<EditorAudit> selectAuditList(EditorAudit audit) {
        return auditMapper.selectAuditList(audit);
    }

    @Override
    public EditorAudit selectAuditById(Long auditId) {
        return auditMapper.selectAuditById(auditId);
    }

    @Override
    public List<EditorAudit> selectAuditByManuscriptId(Long manuscriptId) {
        return auditMapper.selectAuditByManuscriptId(manuscriptId);
    }

    @Override
    public int insertAudit(EditorAudit audit) {
        audit.setCreateBy(SecurityUtils.getUsername());
        audit.setCreateTime(new Date());
        return auditMapper.insertAudit(audit);
    }

    @Override
    public int updateAudit(EditorAudit audit) {
        audit.setUpdateBy(SecurityUtils.getUsername());
        return auditMapper.updateAudit(audit);
    }

    @Override
    public int deleteAuditById(Long auditId) {
        return auditMapper.deleteAuditById(auditId);
    }

    @Override
    public int inviteReviewer(EditorAudit audit) {
        audit.setCreateBy(SecurityUtils.getUsername());
        audit.setCreateTime(new Date());
        audit.setAuditStatus("0"); // 待审
        audit.setInviteTime(new Date());
        return auditMapper.insertAudit(audit);
    }

    @Override
    public int withdrawAudit(EditorAudit audit) {
        audit.setUpdateBy(SecurityUtils.getUsername());
        audit.setAuditStatus("2"); // 已撤审
        audit.setWithdrawTime(new Date());
        return auditMapper.updateAuditStatus(audit);
    }

    @Override
    public int acceptWithdraw(EditorAudit audit) {
        audit.setUpdateBy(SecurityUtils.getUsername());
        audit.setAuditStatus("3"); // 已接受退审
        audit.setAcceptWithdrawTime(new Date());
        return auditMapper.updateAuditStatus(audit);
    }

    @Override
    public int returnAudit(EditorAudit audit) {
        audit.setUpdateBy(SecurityUtils.getUsername());
        audit.setAuditStatus("1"); // 已审回
        audit.setAuditTime(new Date());
        return auditMapper.updateAudit(audit);
    }
}