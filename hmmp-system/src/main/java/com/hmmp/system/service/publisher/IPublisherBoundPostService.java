package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherBoundPost;

public interface IPublisherBoundPostService
{
    public List<PublisherBoundPost> selectBoundPostList(PublisherBoundPost boundPost);

    public PublisherBoundPost selectBoundPostById(Long boundId);

    public int saveBoundPost(PublisherBoundPost boundPost);

    public int clearInvalidAddresses();
}
