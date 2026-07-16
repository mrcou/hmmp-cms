package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorManuscriptAuthor;

/**
 * 稿件作者关联 服务层
 * 
 * @author hmmp
 */
public interface IEditorManuscriptAuthorService
{
    public List<EditorManuscriptAuthor> selectAuthorListByManuscriptId(Long manuscriptId);

    public int insertAuthor(EditorManuscriptAuthor author);

    public int batchInsertAuthor(List<EditorManuscriptAuthor> authorList);

    public int updateAuthor(EditorManuscriptAuthor author);

    public int deleteAuthorById(Long id);

    public int deleteAuthorByManuscriptId(Long manuscriptId);
}