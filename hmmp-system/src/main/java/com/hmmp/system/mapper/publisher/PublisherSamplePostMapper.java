package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherSamplePost;

public interface PublisherSamplePostMapper
{
    public List<PublisherSamplePost> selectSamplePostList(PublisherSamplePost samplePost);

    public PublisherSamplePost selectSamplePostById(Long sampleId);

    public PublisherSamplePost selectSamplePostByArticleId(Long articleId);

    public int insertSamplePost(PublisherSamplePost samplePost);

    public int updateSamplePost(PublisherSamplePost samplePost);
}
