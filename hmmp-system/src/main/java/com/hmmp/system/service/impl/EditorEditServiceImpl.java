package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorEdit;
import com.hmmp.system.mapper.EditorEditMapper;
import com.hmmp.system.service.IEditorEditService;

/**
 * 编辑加工 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorEditServiceImpl implements IEditorEditService {

    @Autowired
    private EditorEditMapper editMapper;

    @Override
    public List<EditorEdit> selectEditList(EditorEdit edit) {
        return editMapper.selectEditList(edit);
    }

    @Override
    public EditorEdit selectEditById(Long editId) {
        return editMapper.selectEditById(editId);
    }

    @Override
    public int insertEdit(EditorEdit edit) {
        edit.setCreateBy(SecurityUtils.getUsername());
        edit.setCreateTime(new Date());
        edit.setEditStatus("0"); // 待编辑
        edit.setEditStartTime(new Date());
        return editMapper.insertEdit(edit);
    }

    @Override
    public int updateEdit(EditorEdit edit) {
        edit.setUpdateBy(SecurityUtils.getUsername());
        return editMapper.updateEdit(edit);
    }

    @Override
    public int deleteEditById(Long editId) {
        return editMapper.deleteEditById(editId);
    }

    @Override
    public int completeEdit(EditorEdit edit) {
        edit.setUpdateBy(SecurityUtils.getUsername());
        edit.setEditStatus("2"); // 已完成
        edit.setEditEndTime(new Date());
        return editMapper.updateEdit(edit);
    }
}