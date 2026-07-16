package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherComment;

public interface PublisherCommentMapper {
    public List<PublisherComment> selectCommentList(PublisherComment comment);
    public PublisherComment selectCommentById(Long commentId);
    public int insertComment(PublisherComment comment);
    public int updateComment(PublisherComment comment);
    public int auditComment(PublisherComment comment);
    public int deleteCommentById(Long commentId);
    public int deleteCommentByIds(Long[] commentIds);
}