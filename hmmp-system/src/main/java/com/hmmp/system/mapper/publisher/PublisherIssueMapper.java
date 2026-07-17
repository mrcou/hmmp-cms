package com.hmmp.system.mapper.publisher;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hmmp.system.domain.publisher.PublisherIssue;

public interface PublisherIssueMapper {
    public List<PublisherIssue> selectIssueList(PublisherIssue issue);
    public PublisherIssue selectIssueById(Long issueId);
    public int insertIssue(PublisherIssue issue);
    public int updateIssue(PublisherIssue issue);
    public int updateIssueStatus(PublisherIssue issue);
    public int clearLatestByJournalCode(@Param("journalCode") String journalCode,
        @Param("excludeIssueId") Long excludeIssueId, @Param("updateBy") String updateBy);
    public int deleteIssueById(Long issueId);
    public int deleteIssueByIds(Long[] issueIds);
}
