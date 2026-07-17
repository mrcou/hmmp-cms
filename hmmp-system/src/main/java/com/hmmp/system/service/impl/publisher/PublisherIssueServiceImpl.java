package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hmmp.common.exception.ServiceException;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.system.domain.publisher.PublisherIssue;
import com.hmmp.system.domain.publisher.PublisherYear;
import com.hmmp.system.mapper.publisher.PublisherIssueMapper;
import com.hmmp.system.mapper.publisher.PublisherYearMapper;
import com.hmmp.system.service.publisher.IPublisherIssueService;

@Service
public class PublisherIssueServiceImpl implements IPublisherIssueService {

    @Autowired
    private PublisherIssueMapper issueMapper;

    @Autowired
    private PublisherYearMapper yearMapper;

    @Override
    public List<PublisherIssue> selectIssueList(PublisherIssue issue) {
        return issueMapper.selectIssueList(issue);
    }

    @Override
    public PublisherIssue selectIssueById(Long issueId) {
        return issueMapper.selectIssueById(issueId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertIssue(PublisherIssue issue) {
        normalizeIssue(issue);
        resolveYearId(issue);
        applyDefaults(issue);
        issue.setCreateBy(SecurityUtils.getUsername());
        int rows = issueMapper.insertIssue(issue);
        if ("1".equals(issue.getIsLatest())) {
            clearOtherLatest(issue);
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateIssue(PublisherIssue issue) {
        normalizeIssue(issue);
        if (issue.getYearId() == null && issue.getYear() != null) {
            resolveYearId(issue);
        }
        issue.setUpdateBy(SecurityUtils.getUsername());
        int rows = issueMapper.updateIssue(issue);
        if ("1".equals(issue.getIsLatest())) {
            clearOtherLatest(issue);
        }
        return rows;
    }

    @Override
    public int publishIssue(PublisherIssue issue) {
        issue.setUpdateBy(SecurityUtils.getUsername());
        issue.setPublishTime(new Date());
        issue.setStatus("1");
        return issueMapper.updateIssueStatus(issue);
    }

    @Override
    public int executeIssueAction(Long issueId, String action) {
        if (issueId == null) {
            throw new ServiceException("刊期ID不能为空");
        }
        PublisherIssue existing = issueMapper.selectIssueById(issueId);
        if (existing == null) {
            throw new ServiceException("刊期不存在");
        }
        PublisherIssue patch = new PublisherIssue();
        patch.setIssueId(issueId);
        patch.setUpdateBy(SecurityUtils.getUsername());
        String act = StringUtils.trim(action);
        if ("subscribe".equalsIgnoreCase(act)) {
            patch.setSubscribeStatus("1");
        } else if ("doi".equalsIgnoreCase(act)) {
            patch.setDoiStatus("1");
        } else if ("cstr".equalsIgnoreCase(act)) {
            patch.setCstrStatus("1");
        } else if ("baidu".equalsIgnoreCase(act)) {
            patch.setBaiduStatus("1");
        } else {
            throw new ServiceException("不支持的操作类型: " + action);
        }
        return issueMapper.updateIssue(patch);
    }

    @Override
    public int deleteIssueByIds(Long[] issueIds) {
        return issueMapper.deleteIssueByIds(issueIds);
    }

    private void normalizeIssue(PublisherIssue issue) {
        if (StringUtils.isNotEmpty(issue.getNameCn()) && StringUtils.isEmpty(issue.getIssueName())) {
            issue.setIssueName(issue.getNameCn());
        } else if (StringUtils.isEmpty(issue.getNameCn()) && StringUtils.isNotEmpty(issue.getIssueName())) {
            issue.setNameCn(issue.getIssueName());
        }
        if (StringUtils.isEmpty(issue.getIsFree())) {
            issue.setIsFree("0");
        }
        if (StringUtils.isEmpty(issue.getIsLatest())) {
            issue.setIsLatest("0");
        }
        if (StringUtils.isEmpty(issue.getRequestMoney())) {
            issue.setRequestMoney("0");
        }
        if (StringUtils.isEmpty(issue.getImageUrl()) && StringUtils.isNotEmpty(issue.getCoverImage())) {
            issue.setImageUrl(issue.getCoverImage());
        } else if (StringUtils.isEmpty(issue.getCoverImage()) && StringUtils.isNotEmpty(issue.getImageUrl())) {
            issue.setCoverImage(issue.getImageUrl());
        }
    }

    private void applyDefaults(PublisherIssue issue) {
        if (StringUtils.isEmpty(issue.getStatus())) {
            issue.setStatus("0");
        }
        if (StringUtils.isEmpty(issue.getSubscribeStatus())) {
            issue.setSubscribeStatus("0");
        }
        if (StringUtils.isEmpty(issue.getDoiStatus())) {
            issue.setDoiStatus("0");
        }
        if (StringUtils.isEmpty(issue.getCstrStatus())) {
            issue.setCstrStatus("0");
        }
        if (StringUtils.isEmpty(issue.getBaiduStatus())) {
            issue.setBaiduStatus("0");
        }
        if (issue.getArticleCount() == null) {
            issue.setArticleCount(0);
        }
        if (issue.getPageCount() == null) {
            issue.setPageCount(0);
        }
    }

    private void resolveYearId(PublisherIssue issue) {
        if (issue.getYearId() != null && issue.getYearId() > 0) {
            return;
        }
        if (issue.getYear() == null) {
            throw new ServiceException("年份不能为空");
        }
        PublisherYear query = new PublisherYear();
        query.setYear(issue.getYear());
        query.setJournalCode(StringUtils.nvl(issue.getJournalCode(), ""));
        List<PublisherYear> years = yearMapper.selectYearList(query);
        if (years != null && !years.isEmpty()) {
            PublisherYear matched = years.get(0);
            issue.setYearId(matched.getYearId());
            if (StringUtils.isEmpty(issue.getJournalCode()) && StringUtils.isNotEmpty(matched.getJournalCode())) {
                issue.setJournalCode(matched.getJournalCode());
            }
            if (issue.getMeetingId() == null && matched.getMeetingId() != null) {
                issue.setMeetingId(matched.getMeetingId());
            }
            return;
        }
        PublisherYear create = new PublisherYear();
        create.setJournalCode(StringUtils.nvl(issue.getJournalCode(), ""));
        create.setYear(issue.getYear());
        create.setVolume(issue.getVolume());
        create.setNameCn(issue.getYear() + "年");
        create.setIsFree(StringUtils.nvl(issue.getIsFree(), "0"));
        create.setMeetingId(issue.getMeetingId());
        create.setStatus("0");
        create.setCreateBy(SecurityUtils.getUsername());
        yearMapper.insertYear(create);
        issue.setYearId(create.getYearId());
    }

    private void clearOtherLatest(PublisherIssue issue) {
        issueMapper.clearLatestByJournalCode(
            StringUtils.nvl(issue.getJournalCode(), ""),
            issue.getIssueId(),
            SecurityUtils.getUsername());
    }
}
