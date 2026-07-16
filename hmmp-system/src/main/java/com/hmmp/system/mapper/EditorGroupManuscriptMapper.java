package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorGroupManuscript;

public interface EditorGroupManuscriptMapper {
    public List<EditorGroupManuscript> selectByGroupId(Long groupId);
    public int insertGroupManuscript(EditorGroupManuscript gm);
    public int deleteById(Long id);
    public int deleteByGroupId(Long groupId);
}