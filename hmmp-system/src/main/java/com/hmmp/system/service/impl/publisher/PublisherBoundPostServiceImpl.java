package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherBoundPost;
import com.hmmp.system.mapper.publisher.PublisherBoundPostMapper;
import com.hmmp.system.service.publisher.IPublisherBoundPostService;

@Service
public class PublisherBoundPostServiceImpl implements IPublisherBoundPostService
{
    @Autowired
    private PublisherBoundPostMapper boundPostMapper;

    @Override
    public List<PublisherBoundPost> selectBoundPostList(PublisherBoundPost boundPost)
    {
        return boundPostMapper.selectBoundPostList(boundPost);
    }

    @Override
    public PublisherBoundPost selectBoundPostById(Long boundId)
    {
        return boundPostMapper.selectBoundPostById(boundId);
    }

    @Override
    public int saveBoundPost(PublisherBoundPost boundPost)
    {
        String username = SecurityUtils.getUsername();
        PublisherBoundPost existing = boundPostMapper.selectBoundPostByOrderId(boundPost.getOrderId());
        if (boundPost.getSendBookTime() == null && boundPost.getSendUser() != null && !boundPost.getSendUser().isEmpty())
        {
            boundPost.setSendBookTime(new Date());
        }
        if (existing != null && existing.getBoundId() != null)
        {
            boundPost.setBoundId(existing.getBoundId());
            boundPost.setUpdateBy(username);
            return boundPostMapper.updateBoundPost(boundPost);
        }
        boundPost.setCreateBy(username);
        boundPost.setUpdateBy(username);
        if (boundPost.getCanPost() == null)
        {
            boundPost.setCanPost("1");
        }
        return boundPostMapper.insertBoundPost(boundPost);
    }

    @Override
    public int clearInvalidAddresses()
    {
        return boundPostMapper.clearInvalidAddresses();
    }
}
