package com.hmmp.web.controller.author;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.system.domain.AuthorSubmission;
import com.hmmp.system.service.IAuthorSubmissionService;

/**
 * 作者中心
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/author")
public class AuthorController extends BaseController
{
    @Autowired
    private IAuthorSubmissionService authorSubmissionService;

    /**
     * 获取作者中心消息
     */
    @PreAuthorize("@ss.hasPermi('author:center:list')")
    @GetMapping("/center")
    public AjaxResult center()
    {
        Long userId = getUserId();
        AuthorSubmission authorSubmission = new AuthorSubmission();
        authorSubmission.setUserId(userId);
        authorSubmission.setIsDeleted("0");
        List<AuthorSubmission> list = authorSubmissionService.selectAuthorSubmissionList(authorSubmission);
        return success(list);
    }

    /**
     * 提交新稿
     */
    @PreAuthorize("@ss.hasPermi('author:manuscript:contribution')")
    @PostMapping("/manuscript/contribution")
    public AjaxResult contribution(@RequestBody AuthorSubmission authorSubmission)
    {
        authorSubmission.setUserId(getUserId());
        authorSubmission.setCreateBy(getUsername());
        authorSubmission.setCreateTime(new Date());
        authorSubmission.setSubmissionTime(new Date());
        authorSubmission.setStatus("01");
        authorSubmission.setIsDeleted("0");
        int rows = authorSubmissionService.insertAuthorSubmission(authorSubmission);
        return toAjax(rows);
    }

    /**
     * 获取待修改稿列表
     */
    @PreAuthorize("@ss.hasPermi('author:manuscript:revision')")
    @GetMapping("/manuscript/revision")
    public AjaxResult revision()
    {
        Long userId = getUserId();
        AuthorSubmission authorSubmission = new AuthorSubmission();
        authorSubmission.setUserId(userId);
        authorSubmission.setStatus("02");
        authorSubmission.setIsDeleted("0");
        List<AuthorSubmission> list = authorSubmissionService.selectAuthorSubmissionList(authorSubmission);
        return success(list);
    }

    /**
     * 提交修改稿
     */
    @PreAuthorize("@ss.hasPermi('author:manuscript:revision')")
    @PutMapping("/manuscript/revision")
    public AjaxResult submitRevision(@RequestBody AuthorSubmission authorSubmission)
    {
        AuthorSubmission existing = authorSubmissionService.selectAuthorSubmissionById(authorSubmission.getSubmissionId());
        if (existing == null)
        {
            return error("投稿记录不存在");
        }
        authorSubmission.setUpdateBy(getUsername());
        authorSubmission.setUpdateTime(new Date());
        authorSubmission.setStatus("03");
        int rows = authorSubmissionService.updateAuthorSubmission(authorSubmission);
        return toAjax(rows);
    }

    /**
     * 查询稿件状态
     */
    @PreAuthorize("@ss.hasPermi('author:manuscript:search')")
    @GetMapping("/manuscript/search")
    public AjaxResult search(AuthorSubmission authorSubmission)
    {
        authorSubmission.setUserId(getUserId());
        authorSubmission.setIsDeleted("0");
        List<AuthorSubmission> list = authorSubmissionService.selectAuthorSubmissionList(authorSubmission);
        return success(list);
    }
}