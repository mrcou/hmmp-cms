package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherPost;

public interface IPublisherPostService {
    public List<PublisherPost> selectPostList(PublisherPost post);
    public PublisherPost selectPostById(Long postId);
    public int insertPost(PublisherPost post);
    public int updatePost(PublisherPost post);
    public int executePost(PublisherPost post);
    public int deletePostByIds(Long[] postIds);
}