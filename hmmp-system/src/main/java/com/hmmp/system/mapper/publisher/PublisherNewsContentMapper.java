package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherNewsContent;

public interface PublisherNewsContentMapper
{
    public List<PublisherNewsContent> selectNewsContentList(PublisherNewsContent content);

    public PublisherNewsContent selectNewsContentById(Long contentId);

    public int insertNewsContent(PublisherNewsContent content);

    public int updateNewsContent(PublisherNewsContent content);

    public int updateNewsContentSort(PublisherNewsContent content);

    public int deleteNewsContentByIds(Long[] contentIds);
}
