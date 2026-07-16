package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherPost;
import com.hmmp.system.mapper.publisher.PublisherPostMapper;
import com.hmmp.system.service.publisher.IPublisherPostService;

@Service
public class PublisherPostServiceImpl implements IPublisherPostService {

    @Autowired
    private PublisherPostMapper postMapper;

    @Override
    public List<PublisherPost> selectPostList(PublisherPost post) {
        return postMapper.selectPostList(post);
    }

    @Override
    public PublisherPost selectPostById(Long postId) {
        return postMapper.selectPostById(postId);
    }

    @Override
    public int insertPost(PublisherPost post) {
        post.setCreateBy(SecurityUtils.getUsername());
        return postMapper.insertPost(post);
    }

    @Override
    public int updatePost(PublisherPost post) {
        post.setUpdateBy(SecurityUtils.getUsername());
        return postMapper.updatePost(post);
    }

    @Override
    public int executePost(PublisherPost post) {
        post.setUpdateBy(SecurityUtils.getUsername());
        post.setPostTime(new Date());
        return postMapper.updatePostStatus(post);
    }

    @Override
    public int deletePostByIds(Long[] postIds) {
        return postMapper.deletePostByIds(postIds);
    }
}