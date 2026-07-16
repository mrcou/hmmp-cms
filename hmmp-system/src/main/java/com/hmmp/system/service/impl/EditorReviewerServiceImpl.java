package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorReviewer;
import com.hmmp.system.mapper.EditorReviewerMapper;
import com.hmmp.system.service.IEditorReviewerService;

/**
 * 审稿人管理 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorReviewerServiceImpl implements IEditorReviewerService {

    @Autowired
    private EditorReviewerMapper reviewerMapper;

    @Override
    public List<EditorReviewer> selectReviewerList(EditorReviewer reviewer) {
        return reviewerMapper.selectReviewerList(reviewer);
    }

    @Override
    public EditorReviewer selectReviewerById(Long reviewerId) {
        return reviewerMapper.selectReviewerById(reviewerId);
    }

    @Override
    public EditorReviewer selectReviewerByUserId(Long userId) {
        return reviewerMapper.selectReviewerByUserId(userId);
    }

    @Override
    public int insertReviewer(EditorReviewer reviewer) {
        reviewer.setCreateBy(SecurityUtils.getUsername());
        return reviewerMapper.insertReviewer(reviewer);
    }

    @Override
    public int updateReviewer(EditorReviewer reviewer) {
        reviewer.setUpdateBy(SecurityUtils.getUsername());
        return reviewerMapper.updateReviewer(reviewer);
    }

    @Override
    public int deleteReviewerById(Long reviewerId) {
        return reviewerMapper.deleteReviewerById(reviewerId);
    }

    @Override
    public int deleteReviewerByIds(Long[] reviewerIds) {
        return reviewerMapper.deleteReviewerByIds(reviewerIds);
    }

    @Override
    public int updateReviewerStatus(EditorReviewer reviewer) {
        reviewer.setUpdateBy(SecurityUtils.getUsername());
        return reviewerMapper.updateReviewerStatus(reviewer);
    }
}