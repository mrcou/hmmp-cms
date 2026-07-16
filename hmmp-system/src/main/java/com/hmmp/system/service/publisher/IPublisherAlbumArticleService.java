package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherAlbumArticle;

public interface IPublisherAlbumArticleService {
    public List<PublisherAlbumArticle> selectAlbumArticleList(PublisherAlbumArticle albumArticle);
    public int insertAlbumArticle(PublisherAlbumArticle albumArticle);
    public int deleteAlbumArticleByIds(Long[] ids);
    public int deleteByAlbumId(Long albumId);
}