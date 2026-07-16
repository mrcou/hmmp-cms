package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.EditorGroupManuscript;
import com.hmmp.system.mapper.EditorGroupManuscriptMapper;
import com.hmmp.system.service.IEditorGroupManuscriptService;

/**
 * 组稿稿件关联 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorGroupManuscriptServiceImpl implements IEditorGroupManuscriptService {

    @Autowired
    private EditorGroupManuscriptMapper groupManuscriptMapper;

    @Override
    public List<EditorGroupManuscript> selectGroupManuscriptList(EditorGroupManuscript groupManuscript) {
        return groupManuscriptMapper.selectGroupManuscriptList(groupManuscript);
    }

    @Override
    public List<EditorGroupManuscript> selectManuscriptsByGroupId(Long groupId) {
        return groupManuscriptMapper.selectManuscriptsByGroupId(groupId);
    }

    @Override
    public int insertGroupManuscript(EditorGroupManuscript groupManuscript) {
        return groupManuscriptMapper.insertGroupManuscript(groupManuscript);
    }

    @Override
    public int updateGroupManuscript(EditorGroupManuscript groupManuscript) {
        return groupManuscriptMapper.updateGroupManuscript(groupManuscript);
    }

    @Override
    public int deleteGroupManuscriptById(Long id) {
        return groupManuscriptMapper.deleteGroupManuscriptById(id);
    }

    @Override
    public int deleteGroupManuscriptByIds(Long[] ids) {
        return groupManuscriptMapper.deleteGroupManuscriptByIds(ids);
    }
}