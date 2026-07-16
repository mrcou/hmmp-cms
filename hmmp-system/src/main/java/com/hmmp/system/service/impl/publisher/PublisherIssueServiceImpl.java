package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherIssue;
import com.hmmp.system.mapper.publisher.PublisherIssueMapper;
import com.hmmp.system.service.publisher.IPublisherIssueService;

@Service
public class PublisherIssueServiceImpl implements IPublisherIssueService {

    @Autowired
    private PublisherIssueMapper issueMapper;

    @Override
    public List<PublisherIssue> selectIssueList(PublisherIssue issue) {
        return issueMapper.selectIssueList(issue);
    }

    @Override
    public PublisherIssue selectIssueById(Long issueId) {
        return issueMapper.selectIssueById(issueId);
    }

    @Override
    public int insertIssue(PublisherIssue issue) {
        issue.setCreateBy(SecurityUtils.getUsername());
        return issueMapper.insertIssue(issue);
    }

    @Override
    public int updateIssue(PublisherIssue issue) {
        issue.setUpdateBy(SecurityUtils.getUsername());
        return issueMapper.updateIssue(issue);
    }

    @Override
    public int publishIssue(PublisherIssue issue) {
        issue.setUpdateBy(SecurityUtils.getUsername());
        issue.setPublishTime(new Date());
        issue.setStatus("1");
        return issueMapper.updateIssueStatus(issue);
    }

    @Override
    public int deleteIssueByIds(Long[] issueIds) {
        return issueMapper.deleteIssueByIds(issueIds);
    }
}