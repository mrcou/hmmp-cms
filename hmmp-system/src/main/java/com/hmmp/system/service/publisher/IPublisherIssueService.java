package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherIssue;

public interface IPublisherIssueService {
    public List<PublisherIssue> selectIssueList(PublisherIssue issue);
    public PublisherIssue selectIssueById(Long issueId);
    public int insertIssue(PublisherIssue issue);
    public int updateIssue(PublisherIssue issue);
    public int publishIssue(PublisherIssue issue);
    public int deleteIssueByIds(Long[] issueIds);
}