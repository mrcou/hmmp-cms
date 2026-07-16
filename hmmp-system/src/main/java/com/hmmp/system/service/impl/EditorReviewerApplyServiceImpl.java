package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorReviewerApply;
import com.hmmp.system.mapper.EditorReviewerApplyMapper;
import com.hmmp.system.service.IEditorReviewerApplyService;

/**
 * 审稿人申请/推荐 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorReviewerApplyServiceImpl implements IEditorReviewerApplyService {

    @Autowired
    private EditorReviewerApplyMapper applyMapper;

    @Override
    public List<EditorReviewerApply> selectApplyList(EditorReviewerApply apply) {
        return applyMapper.selectApplyList(apply);
    }

    @Override
    public EditorReviewerApply selectApplyById(Long applyId) {
        return applyMapper.selectApplyById(applyId);
    }

    @Override
    public int insertApply(EditorReviewerApply apply) {
        apply.setCreateTime(new Date());
        apply.setAuditStatus("0"); // 待审核
        return applyMapper.insertApply(apply);
    }

    @Override
    public int updateApply(EditorReviewerApply apply) {
        return applyMapper.updateApply(apply);
    }

    @Override
    public int deleteApplyById(Long applyId) {
        return applyMapper.deleteApplyById(applyId);
    }

    @Override
    public int deleteApplyByIds(Long[] applyIds) {
        return applyMapper.deleteApplyByIds(applyIds);
    }

    @Override
    public int auditApply(EditorReviewerApply apply) {
        apply.setAuditTime(new Date());
        apply.setAuditBy(SecurityUtils.getUsername());
        return applyMapper.updateApply(apply);
    }
}