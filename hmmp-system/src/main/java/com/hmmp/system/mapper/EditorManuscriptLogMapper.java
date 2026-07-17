package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorManuscriptLog;

public interface EditorManuscriptLogMapper {
    public List<EditorManuscriptLog> selectLogList(EditorManuscriptLog log);
    public EditorManuscriptLog selectLogById(Long logId);
    public List<EditorManuscriptLog> selectLogByManuscriptId(Long manuscriptId);
    public int insertLog(EditorManuscriptLog log);
    public int deleteLogById(Long logId);
    public int deleteLogByManuscriptId(Long manuscriptId);
}
