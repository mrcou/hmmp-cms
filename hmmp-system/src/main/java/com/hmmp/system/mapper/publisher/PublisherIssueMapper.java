package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherIssue;

public interface PublisherIssueMapper {
    public List<PublisherIssue> selectIssueList(PublisherIssue issue);
    public PublisherIssue selectIssueById(Long issueId);
    public int insertIssue(PublisherIssue issue);
    public int updateIssue(PublisherIssue issue);
    public int updateIssueStatus(PublisherIssue issue);
    public int deleteIssueById(Long issueId);
    public int deleteIssueByIds(Long[] issueIds);
}