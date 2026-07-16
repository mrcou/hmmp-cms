package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorAuthor;
import com.hmmp.system.mapper.EditorAuthorMapper;
import com.hmmp.system.service.IEditorAuthorService;

/**
 * 作者管理 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorAuthorServiceImpl implements IEditorAuthorService {

    @Autowired
    private EditorAuthorMapper authorMapper;

    @Override
    public List<EditorAuthor> selectAuthorList(EditorAuthor author) {
        return authorMapper.selectAuthorList(author);
    }

    @Override
    public EditorAuthor selectAuthorById(Long authorId) {
        return authorMapper.selectAuthorById(authorId);
    }

    @Override
    public EditorAuthor selectAuthorByUserId(Long userId) {
        return authorMapper.selectAuthorByUserId(userId);
    }

    @Override
    public int insertAuthor(EditorAuthor author) {
        author.setCreateBy(SecurityUtils.getUsername());
        return authorMapper.insertAuthor(author);
    }

    @Override
    public int updateAuthor(EditorAuthor author) {
        author.setUpdateBy(SecurityUtils.getUsername());
        return authorMapper.updateAuthor(author);
    }

    @Override
    public int deleteAuthorById(Long authorId) {
        return authorMapper.deleteAuthorById(authorId);
    }

    @Override
    public int deleteAuthorByIds(Long[] authorIds) {
        return authorMapper.deleteAuthorByIds(authorIds);
    }

    @Override
    public int updateAuthorStatus(EditorAuthor author) {
        author.setUpdateBy(SecurityUtils.getUsername());
        return authorMapper.updateAuthorStatus(author);
    }
}