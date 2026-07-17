package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorReviewerApply;

public interface EditorReviewerApplyMapper {
    public List<EditorReviewerApply> selectApplyList(EditorReviewerApply apply);
    public EditorReviewerApply selectApplyById(Long applyId);
    public int insertApply(EditorReviewerApply apply);
    public int updateApply(EditorReviewerApply apply);
    public int deleteApplyById(Long applyId);
    public int deleteApplyByIds(Long[] applyIds);
}