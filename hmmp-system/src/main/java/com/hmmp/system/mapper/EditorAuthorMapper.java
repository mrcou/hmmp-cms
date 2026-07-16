package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.EditorAuthor;

public interface EditorAuthorMapper {
    public List<EditorAuthor> selectAuthorList(EditorAuthor author);
    public EditorAuthor selectAuthorById(Long authorId);
    public int insertAuthor(EditorAuthor author);
    public int updateAuthor(EditorAuthor author);
    public int deleteAuthorById(Long authorId);
    public int deleteAuthorByIds(Long[] authorIds);
    public EditorAuthor checkEmailUnique(String email);
}