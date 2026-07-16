package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorGroup;

/**
 * 组稿管理 业务层
 * 
 * @author hmmp
 */
public interface IEditorGroupService {

    /**
     * 查询组稿列表
     */
    public List<EditorGroup> selectGroupList(EditorGroup group);

    /**
     * 根据组稿ID查询组稿
     */
    public EditorGroup selectGroupById(Long groupId);

    /**
     * 新增组稿
     */
    public int insertGroup(EditorGroup group);

    /**
     * 修改组稿
     */
    public int updateGroup(EditorGroup group);

    /**
     * 删除组稿
     */
    public int deleteGroupById(Long groupId);

    /**
     * 批量删除组稿
     */
    public int deleteGroupByIds(Long[] groupIds);

    /**
     * 更新组稿状态
     */
    public int updateGroupStatus(EditorGroup group);
}