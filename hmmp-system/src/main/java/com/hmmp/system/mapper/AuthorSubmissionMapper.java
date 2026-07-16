package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.AuthorSubmission;

/**
 * 作者投稿 数据层
 * 
 * @author hmmp
 */
public interface AuthorSubmissionMapper
{
    /**
     * 查询作者投稿列表
     * 
     * @param authorSubmission 作者投稿
     * @return 作者投稿集合
     */
    public List<AuthorSubmission> selectAuthorSubmissionList(AuthorSubmission authorSubmission);

    /**
     * 通过投稿ID查询作者投稿
     * 
     * @param submissionId 投稿ID
     * @return 作者投稿
     */
    public AuthorSubmission selectAuthorSubmissionById(Long submissionId);

    /**
     * 通过稿件ID查询作者投稿
     * 
     * @param manuscriptId 稿件ID
     * @return 作者投稿
     */
    public AuthorSubmission selectAuthorSubmissionByManuscriptId(Long manuscriptId);

    /**
     * 新增作者投稿
     * 
     * @param authorSubmission 作者投稿
     * @return 结果
     */
    public int insertAuthorSubmission(AuthorSubmission authorSubmission);

    /**
     * 修改作者投稿
     * 
     * @param authorSubmission 作者投稿
     * @return 结果
     */
    public int updateAuthorSubmission(AuthorSubmission authorSubmission);

    /**
     * 删除作者投稿
     * 
     * @param submissionId 投稿ID
     * @return 结果
     */
    public int deleteAuthorSubmissionById(Long submissionId);

    /**
     * 批量删除作者投稿
     * 
     * @param submissionIds 需要删除的投稿ID
     * @return 结果
     */
    public int deleteAuthorSubmissionByIds(Long[] submissionIds);
}