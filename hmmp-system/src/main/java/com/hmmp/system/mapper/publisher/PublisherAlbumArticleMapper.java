package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherAlbumArticle;

public interface PublisherAlbumArticleMapper {
    public List<PublisherAlbumArticle> selectAlbumArticleList(PublisherAlbumArticle albumArticle);
    public int insertAlbumArticle(PublisherAlbumArticle albumArticle);
    public int deleteAlbumArticleById(Long id);
    public int deleteAlbumArticleByIds(Long[] ids);
    public int deleteByAlbumId(Long albumId);
}