package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorAudit;

/**
 * 审稿管理 业务层
 * 
 * @author hmmp
 */
public interface IEditorAuditService {

    /**
     * 查询审稿列表
     */
    public List<EditorAudit> selectAuditList(EditorAudit audit);

    /**
     * 根据审稿ID查询审稿
     */
    public EditorAudit selectAuditById(Long auditId);

    /**
     * 根据稿件ID查询审稿记录
     */
    public List<EditorAudit> selectAuditByManuscriptId(Long manuscriptId);

    /**
     * 新增审稿记录（邀请审稿人）
     */
    public int insertAudit(EditorAudit audit);

    /**
     * 修改审稿记录
     */
    public int updateAudit(EditorAudit audit);

    /**
     * 删除审稿记录
     */
    public int deleteAuditById(Long auditId);

    /**
     * 邀请审稿人审稿
     */
    public int inviteReviewer(EditorAudit audit);

    /**
     * 撤审
     */
    public int withdrawAudit(EditorAudit audit);

    /**
     * 接受退审
     */
    public int acceptWithdraw(EditorAudit audit);

    /**
     * 审回处理
     */
    public int returnAudit(EditorAudit audit);
}