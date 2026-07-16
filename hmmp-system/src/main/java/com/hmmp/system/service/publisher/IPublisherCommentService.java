package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherComment;

public interface IPublisherCommentService {
    public List<PublisherComment> selectCommentList(PublisherComment comment);
    public PublisherComment selectCommentById(Long commentId);
    public int insertComment(PublisherComment comment);
    public int auditComment(PublisherComment comment);
    public int deleteCommentByIds(Long[] commentIds);
}