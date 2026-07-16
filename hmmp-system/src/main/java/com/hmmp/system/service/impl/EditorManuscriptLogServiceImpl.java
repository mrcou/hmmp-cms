package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorManuscriptLog;
import com.hmmp.system.mapper.EditorManuscriptLogMapper;
import com.hmmp.system.service.IEditorManuscriptLogService;

/**
 * 稿件操作日志 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorManuscriptLogServiceImpl implements IEditorManuscriptLogService {

    @Autowired
    private EditorManuscriptLogMapper logMapper;

    @Override
    public List<EditorManuscriptLog> selectLogList(EditorManuscriptLog log) {
        return logMapper.selectLogList(log);
    }

    @Override
    public EditorManuscriptLog selectLogById(Long logId) {
        return logMapper.selectLogById(logId);
    }

    @Override
    public List<EditorManuscriptLog> selectLogByManuscriptId(Long manuscriptId) {
        return logMapper.selectLogByManuscriptId(manuscriptId);
    }

    @Override
    public int insertLog(EditorManuscriptLog log) {
        log.setOperateTime(new Date());
        return logMapper.insertLog(log);
    }

    @Override
    public int deleteLogById(Long logId) {
        return logMapper.deleteLogById(logId);
    }
}