package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.publisher.PublisherAlbumArticle;
import com.hmmp.system.mapper.publisher.PublisherAlbumArticleMapper;
import com.hmmp.system.service.publisher.IPublisherAlbumArticleService;

@Service
public class PublisherAlbumArticleServiceImpl implements IPublisherAlbumArticleService {

    @Autowired
    private PublisherAlbumArticleMapper albumArticleMapper;

    @Override
    public List<PublisherAlbumArticle> selectAlbumArticleList(PublisherAlbumArticle albumArticle) {
        return albumArticleMapper.selectAlbumArticleList(albumArticle);
    }

    @Override
    public int insertAlbumArticle(PublisherAlbumArticle albumArticle) {
        return albumArticleMapper.insertAlbumArticle(albumArticle);
    }

    @Override
    public int deleteAlbumArticleByIds(Long[] ids) {
        return albumArticleMapper.deleteAlbumArticleByIds(ids);
    }

    @Override
    public int deleteByAlbumId(Long albumId) {
        return albumArticleMapper.deleteByAlbumId(albumId);
    }
}