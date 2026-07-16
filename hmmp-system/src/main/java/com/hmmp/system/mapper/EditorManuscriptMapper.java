package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorManuscript;

public interface EditorManuscriptMapper {
    public List<EditorManuscript> selectManuscriptList(EditorManuscript manuscript);
    public EditorManuscript selectManuscriptById(Long manuscriptId);
    public EditorManuscript selectManuscriptByFileNo(String fileNo);
    public int insertManuscript(EditorManuscript manuscript);
    public int updateManuscript(EditorManuscript manuscript);
    public int deleteManuscriptById(Long manuscriptId);
    public int deleteManuscriptByIds(Long[] manuscriptIds);
    public int updateManuscriptStatus(EditorManuscript manuscript);
}