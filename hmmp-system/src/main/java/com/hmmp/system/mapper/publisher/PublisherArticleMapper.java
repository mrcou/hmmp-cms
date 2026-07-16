package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherArticle;

public interface PublisherArticleMapper {
    public List<PublisherArticle> selectArticleList(PublisherArticle article);
    public PublisherArticle selectArticleById(Long articleId);
    public int insertArticle(PublisherArticle article);
    public int updateArticle(PublisherArticle article);
    public int updateArticleStatus(PublisherArticle article);
    public int batchPublishArticles(Long[] articleIds);
    public int deleteArticleById(Long articleId);
    public int deleteArticleByIds(Long[] articleIds);
    public int updateCnkiStatus(PublisherArticle article);
}