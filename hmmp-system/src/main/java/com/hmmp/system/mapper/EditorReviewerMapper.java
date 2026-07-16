package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorReviewer;

public interface EditorReviewerMapper {
    public List<EditorReviewer> selectReviewerList(EditorReviewer reviewer);
    public EditorReviewer selectReviewerById(Long reviewerId);
    public int insertReviewer(EditorReviewer reviewer);
    public int updateReviewer(EditorReviewer reviewer);
    public int deleteReviewerById(Long reviewerId);
    public int deleteReviewerByIds(Long[] reviewerIds);
}