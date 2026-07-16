package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherArticle;

public interface IPublisherArticleService {
    public List<PublisherArticle> selectArticleList(PublisherArticle article);
    public PublisherArticle selectArticleById(Long articleId);
    public int insertArticle(PublisherArticle article);
    public int updateArticle(PublisherArticle article);
    public int batchPublishArticles(Long[] articleIds);
    public int cnkiCheck(PublisherArticle article);
    public int deleteArticleByIds(Long[] articleIds);
}