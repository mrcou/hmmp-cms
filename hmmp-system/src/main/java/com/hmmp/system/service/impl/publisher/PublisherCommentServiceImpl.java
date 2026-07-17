package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherComment;
import com.hmmp.system.mapper.publisher.PublisherCommentMapper;
import com.hmmp.system.service.publisher.IPublisherCommentService;

@Service
public class PublisherCommentServiceImpl implements IPublisherCommentService {

    @Autowired
    private PublisherCommentMapper commentMapper;

    @Override
    public List<PublisherComment> selectCommentList(PublisherComment comment) {
        return commentMapper.selectCommentList(comment);
    }

    @Override
    public PublisherComment selectCommentById(Long commentId) {
        return commentMapper.selectCommentById(commentId);
    }

    @Override
    public int insertComment(PublisherComment comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    public int auditComment(PublisherComment comment) {
        if (comment.getCommentId() == null
            && (comment.getCommentIds() == null || comment.getCommentIds().length == 0))
        {
            return 0;
        }
        comment.setAuditBy(SecurityUtils.getUsername());
        comment.setAuditTime(new Date());
        return commentMapper.auditComment(comment);
    }

    @Override
    public int deleteCommentByIds(Long[] commentIds) {
        return commentMapper.deleteCommentByIds(commentIds);
    }
}