package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorGroup;

public interface EditorGroupMapper {
    public List<EditorGroup> selectGroupList(EditorGroup group);
    public EditorGroup selectGroupById(Long groupId);
    public int insertGroup(EditorGroup group);
    public int updateGroup(EditorGroup group);
    public int updateGroupStatus(EditorGroup group);
    public int deleteGroupById(Long groupId);
    public int deleteGroupByIds(Long[] groupIds);
}