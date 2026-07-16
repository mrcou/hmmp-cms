package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorAuthor;

/**
 * 作者管理 业务层
 * 
 * @author hmmp
 */
public interface IEditorAuthorService {

    /**
     * 查询作者列表
     */
    public List<EditorAuthor> selectAuthorList(EditorAuthor author);

    /**
     * 根据作者ID查询
     */
    public EditorAuthor selectAuthorById(Long authorId);

    /**
     * 根据用户ID查询作者
     */
    public EditorAuthor selectAuthorByUserId(Long userId);

    /**
     * 新增作者
     */
    public int insertAuthor(EditorAuthor author);

    /**
     * 修改作者
     */
    public int updateAuthor(EditorAuthor author);

    /**
     * 删除作者（逻辑删除）
     */
    public int deleteAuthorById(Long authorId);

    /**
     * 批量删除作者
     */
    public int deleteAuthorByIds(Long[] authorIds);

    /**
     * 修改作者状态
     */
    public int updateAuthorStatus(EditorAuthor author);
}