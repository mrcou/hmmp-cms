package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorSign;

public interface EditorSignMapper {
    public List<EditorSign> selectSignList(EditorSign sign);
    public EditorSign selectSignById(Long signId);
    public EditorSign selectSignByManuscriptId(Long manuscriptId);
    public int insertSign(EditorSign sign);
    public int updateSign(EditorSign sign);
}