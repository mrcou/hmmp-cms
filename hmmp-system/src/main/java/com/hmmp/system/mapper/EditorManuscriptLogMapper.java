package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorManuscriptLog;

public interface EditorManuscriptLogMapper {
    public List<EditorManuscriptLog> selectLogList(Long manuscriptId);
    public int insertLog(EditorManuscriptLog log);
}