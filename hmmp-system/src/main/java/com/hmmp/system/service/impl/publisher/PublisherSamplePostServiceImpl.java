package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherSamplePost;
import com.hmmp.system.mapper.publisher.PublisherSamplePostMapper;
import com.hmmp.system.service.publisher.IPublisherSamplePostService;

@Service
public class PublisherSamplePostServiceImpl implements IPublisherSamplePostService
{
    @Autowired
    private PublisherSamplePostMapper samplePostMapper;

    @Override
    public List<PublisherSamplePost> selectSamplePostList(PublisherSamplePost samplePost)
    {
        return samplePostMapper.selectSamplePostList(samplePost);
    }

    @Override
    public PublisherSamplePost selectSamplePostById(Long sampleId)
    {
        return samplePostMapper.selectSamplePostById(sampleId);
    }

    @Override
    public int saveSamplePost(PublisherSamplePost samplePost)
    {
        String username = SecurityUtils.getUsername();
        PublisherSamplePost existing = samplePostMapper.selectSamplePostByArticleId(samplePost.getArticleId());
        if (existing != null && existing.getSampleId() != null)
        {
            samplePost.setSampleId(existing.getSampleId());
            samplePost.setUpdateBy(username);
            return samplePostMapper.updateSamplePost(samplePost);
        }
        samplePost.setCreateBy(username);
        samplePost.setUpdateBy(username);
        if (samplePost.getSampleQty() == null)
        {
            samplePost.setSampleQty(1);
        }
        return samplePostMapper.insertSamplePost(samplePost);
    }
}
