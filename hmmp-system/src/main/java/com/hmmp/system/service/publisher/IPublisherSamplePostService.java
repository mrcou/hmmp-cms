package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherSamplePost;

public interface IPublisherSamplePostService
{
    public List<PublisherSamplePost> selectSamplePostList(PublisherSamplePost samplePost);

    public PublisherSamplePost selectSamplePostById(Long sampleId);

    public int saveSamplePost(PublisherSamplePost samplePost);
}
