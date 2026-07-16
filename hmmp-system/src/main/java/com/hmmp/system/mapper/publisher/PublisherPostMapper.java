package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherPost;

public interface PublisherPostMapper {
    public List<PublisherPost> selectPostList(PublisherPost post);
    public PublisherPost selectPostById(Long postId);
    public int insertPost(PublisherPost post);
    public int updatePost(PublisherPost post);
    public int updatePostStatus(PublisherPost post);
    public int deletePostById(Long postId);
    public int deletePostByIds(Long[] postIds);
}