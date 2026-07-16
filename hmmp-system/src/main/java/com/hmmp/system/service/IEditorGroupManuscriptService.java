package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorGroupManuscript;

/**
 * 组稿稿件关联 业务层
 * 
 * @author hmmp
 */
public interface IEditorGroupManuscriptService {

    /**
     * 查询组稿稿件列表
     */
    public List<EditorGroupManuscript> selectGroupManuscriptList(EditorGroupManuscript groupManuscript);

    /**
     * 根据组稿ID查询稿件列表
     */
    public List<EditorGroupManuscript> selectManuscriptsByGroupId(Long groupId);

    /**
     * 添加稿件到组稿
     */
    public int insertGroupManuscript(EditorGroupManuscript groupManuscript);

    /**
     * 修改组稿稿件
     */
    public int updateGroupManuscript(EditorGroupManuscript groupManuscript);

    /**
     * 从组稿移除稿件
     */
    public int deleteGroupManuscriptById(Long id);

    /**
     * 批量删除组稿稿件
     */
    public int deleteGroupManuscriptByIds(Long[] ids);
}