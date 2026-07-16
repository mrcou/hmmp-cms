package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AuthorSubmission;
import com.hmmp.system.mapper.AuthorSubmissionMapper;
import com.hmmp.system.service.IAuthorSubmissionService;

/**
 * 作者投稿 业务层处理
 * 
 * @author hmmp
 */
@Service
public class AuthorSubmissionServiceImpl implements IAuthorSubmissionService
{
    @Autowired
    private AuthorSubmissionMapper authorSubmissionMapper;

    /**
     * 查询作者投稿列表
     * 
     * @param authorSubmission 作者投稿
     * @return 作者投稿集合
     */
    @Override
    public List<AuthorSubmission> selectAuthorSubmissionList(AuthorSubmission authorSubmission)
    {
        return authorSubmissionMapper.selectAuthorSubmissionList(authorSubmission);
    }

    /**
     * 通过投稿ID查询作者投稿
     * 
     * @param submissionId 投稿ID
     * @return 作者投稿
     */
    @Override
    public AuthorSubmission selectAuthorSubmissionById(Long submissionId)
    {
        return authorSubmissionMapper.selectAuthorSubmissionById(submissionId);
    }

    /**
     * 通过稿件ID查询作者投稿
     * 
     * @param manuscriptId 稿件ID
     * @return 作者投稿
     */
    @Override
    public AuthorSubmission selectAuthorSubmissionByManuscriptId(Long manuscriptId)
    {
        return authorSubmissionMapper.selectAuthorSubmissionByManuscriptId(manuscriptId);
    }

    /**
     * 新增作者投稿
     * 
     * @param authorSubmission 作者投稿
     * @return 结果
     */
    @Override
    public int insertAuthorSubmission(AuthorSubmission authorSubmission)
    {
        return authorSubmissionMapper.insertAuthorSubmission(authorSubmission);
    }

    /**
     * 修改作者投稿
     * 
     * @param authorSubmission 作者投稿
     * @return 结果
     */
    @Override
    public int updateAuthorSubmission(AuthorSubmission authorSubmission)
    {
        return authorSubmissionMapper.updateAuthorSubmission(authorSubmission);
    }

    /**
     * 批量删除作者投稿
     * 
     * @param submissionIds 需要删除的投稿ID
     * @return 结果
     */
    @Override
    public int deleteAuthorSubmissionByIds(Long[] submissionIds)
    {
        return authorSubmissionMapper.deleteAuthorSubmissionByIds(submissionIds);
    }

    /**
     * 删除作者投稿
     * 
     * @param submissionId 投稿ID
     * @return 结果
     */
    @Override
    public int deleteAuthorSubmissionById(Long submissionId)
    {
        return authorSubmissionMapper.deleteAuthorSubmissionById(submissionId);
    }
}