package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorManuscriptAuthor;

/**
 * 稿件作者关联 Mapper
 * 
 * @author hmmp
 */
public interface EditorManuscriptAuthorMapper
{
    public List<EditorManuscriptAuthor> selectAuthorListByManuscriptId(Long manuscriptId);

    public EditorManuscriptAuthor selectAuthorById(Long id);

    public int insertAuthor(EditorManuscriptAuthor author);

    public int batchInsertAuthor(List<EditorManuscriptAuthor> authorList);

    public int updateAuthor(EditorManuscriptAuthor author);

    public int deleteAuthorById(Long id);

    public int deleteAuthorByManuscriptId(Long manuscriptId);
}