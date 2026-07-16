package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorManuscriptLog;

/**
 * 稿件操作日志 业务层
 * 
 * @author hmmp
 */
public interface IEditorManuscriptLogService {

    /**
     * 查询日志列表
     */
    public List<EditorManuscriptLog> selectLogList(EditorManuscriptLog log);

    /**
     * 根据日志ID查询
     */
    public EditorManuscriptLog selectLogById(Long logId);

    /**
     * 根据稿件ID查询日志
     */
    public List<EditorManuscriptLog> selectLogByManuscriptId(Long manuscriptId);

    /**
     * 新增操作日志
     */
    public int insertLog(EditorManuscriptLog log);

    /**
     * 删除日志
     */
    public int deleteLogById(Long logId);
}