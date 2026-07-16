package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorGroup;
import com.hmmp.system.mapper.EditorGroupMapper;
import com.hmmp.system.service.IEditorGroupService;

/**
 * 组稿管理 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorGroupServiceImpl implements IEditorGroupService {

    @Autowired
    private EditorGroupMapper groupMapper;

    @Override
    public List<EditorGroup> selectGroupList(EditorGroup group) {
        return groupMapper.selectGroupList(group);
    }

    @Override
    public EditorGroup selectGroupById(Long groupId) {
        return groupMapper.selectGroupById(groupId);
    }

    @Override
    public int insertGroup(EditorGroup group) {
        group.setCreateBy(SecurityUtils.getUsername());
        return groupMapper.insertGroup(group);
    }

    @Override
    public int updateGroup(EditorGroup group) {
        group.setUpdateBy(SecurityUtils.getUsername());
        return groupMapper.updateGroup(group);
    }

    @Override
    public int deleteGroupById(Long groupId) {
        return groupMapper.deleteGroupById(groupId);
    }

    @Override
    public int deleteGroupByIds(Long[] groupIds) {
        return groupMapper.deleteGroupByIds(groupIds);
    }

    @Override
    public int updateGroupStatus(EditorGroup group) {
        group.setUpdateBy(SecurityUtils.getUsername());
        return groupMapper.updateGroupStatus(group);
    }
}