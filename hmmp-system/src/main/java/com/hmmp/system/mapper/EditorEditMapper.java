package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorEdit;

public interface EditorEditMapper {
    public List<EditorEdit> selectEditList(EditorEdit edit);
    public EditorEdit selectEditById(Long editId);
    public int insertEdit(EditorEdit edit);
    public int updateEdit(EditorEdit edit);
    public int deleteEditById(Long editId);
}