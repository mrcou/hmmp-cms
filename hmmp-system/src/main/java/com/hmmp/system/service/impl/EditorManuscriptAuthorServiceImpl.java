package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.EditorManuscriptAuthor;
import com.hmmp.system.mapper.EditorManuscriptAuthorMapper;
import com.hmmp.system.service.IEditorManuscriptAuthorService;

/**
 * 稿件作者关联 服务层处理
 * 
 * @author hmmp
 */
@Service
public class EditorManuscriptAuthorServiceImpl implements IEditorManuscriptAuthorService
{
    @Autowired
    private EditorManuscriptAuthorMapper authorMapper;

    @Override
    public List<EditorManuscriptAuthor> selectAuthorListByManuscriptId(Long manuscriptId)
    {
        return authorMapper.selectAuthorListByManuscriptId(manuscriptId);
    }

    @Override
    public int insertAuthor(EditorManuscriptAuthor author)
    {
        author.setCreateTime(new Date());
        return authorMapper.insertAuthor(author);
    }

    @Override
    public int batchInsertAuthor(List<EditorManuscriptAuthor> authorList)
    {
        for (EditorManuscriptAuthor author : authorList)
        {
            author.setCreateTime(new Date());
        }
        return authorMapper.batchInsertAuthor(authorList);
    }

    @Override
    public int updateAuthor(EditorManuscriptAuthor author)
    {
        return authorMapper.updateAuthor(author);
    }

    @Override
    public int deleteAuthorById(Long id)
    {
        return authorMapper.deleteAuthorById(id);
    }

    @Override
    public int deleteAuthorByManuscriptId(Long manuscriptId)
    {
        return authorMapper.deleteAuthorByManuscriptId(manuscriptId);
    }
}