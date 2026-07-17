package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherArticle;

public interface PublisherArticleMapper {
    public List<PublisherArticle> selectArticleList(PublisherArticle article);
    public PublisherArticle selectArticleById(Long articleId);
    public PublisherArticle selectArticleByFileNo(String fileNo);
    public int insertArticle(PublisherArticle article);
    public int updateArticle(PublisherArticle article);
    public int updateArticleStatus(PublisherArticle article);
    public int batchPublishArticles(Long[] articleIds);
    public int deleteArticleById(Long articleId);
    public int deleteArticleByIds(Long[] articleIds);
    public int updateCnkiStatus(PublisherArticle article);
    /** 将知网引用次数同步到被引次数 */
    public int syncCnkiCitedCount(Long[] articleIds);
}