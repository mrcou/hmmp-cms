package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherNewsContent;

public interface IPublisherNewsContentService
{
    public List<PublisherNewsContent> selectNewsContentList(PublisherNewsContent content);

    public PublisherNewsContent selectNewsContentById(Long contentId);

    public int insertNewsContent(PublisherNewsContent content);

    public int updateNewsContent(PublisherNewsContent content);

    public int updateNewsContentSort(List<PublisherNewsContent> contents);

    public int deleteNewsContentByIds(Long[] contentIds);
}
