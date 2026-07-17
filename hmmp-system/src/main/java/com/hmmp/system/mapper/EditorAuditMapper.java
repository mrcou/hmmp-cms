package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorAudit;

public interface EditorAuditMapper {
    public List<EditorAudit> selectAuditList(EditorAudit audit);
    public EditorAudit selectAuditById(Long auditId);
    public List<EditorAudit> selectAuditByManuscriptId(Long manuscriptId);
    public int insertAudit(EditorAudit audit);
    public int updateAudit(EditorAudit audit);
    public int updateAuditStatus(EditorAudit audit);
    public int deleteAuditById(Long auditId);
}