package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherBoundPost;

public interface PublisherBoundPostMapper
{
    public List<PublisherBoundPost> selectBoundPostList(PublisherBoundPost boundPost);

    public PublisherBoundPost selectBoundPostById(Long boundId);

    public PublisherBoundPost selectBoundPostByOrderId(Long orderId);

    public int insertBoundPost(PublisherBoundPost boundPost);

    public int updateBoundPost(PublisherBoundPost boundPost);

    public int clearInvalidAddresses();
}
