package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorGroupManuscript;

public interface EditorGroupManuscriptMapper {
    public List<EditorGroupManuscript> selectGroupManuscriptList(EditorGroupManuscript gm);
    public EditorGroupManuscript selectGroupManuscriptById(Long id);
    public List<EditorGroupManuscript> selectManuscriptsByGroupId(Long groupId);
    public int insertGroupManuscript(EditorGroupManuscript gm);
    public int updateGroupManuscript(EditorGroupManuscript gm);
    public int deleteGroupManuscriptById(Long id);
    public int deleteGroupManuscriptByGroupId(Long groupId);
    public int deleteGroupManuscriptByIds(Long[] ids);
}
