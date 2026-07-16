package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherArticle;
import com.hmmp.system.mapper.publisher.PublisherArticleMapper;
import com.hmmp.system.service.publisher.IPublisherArticleService;

@Service
public class PublisherArticleServiceImpl implements IPublisherArticleService {

    @Autowired
    private PublisherArticleMapper articleMapper;

    @Override
    public List<PublisherArticle> selectArticleList(PublisherArticle article) {
        return articleMapper.selectArticleList(article);
    }

    @Override
    public PublisherArticle selectArticleById(Long articleId) {
        return articleMapper.selectArticleById(articleId);
    }

    @Override
    public int insertArticle(PublisherArticle article) {
        article.setCreateBy(SecurityUtils.getUsername());
        return articleMapper.insertArticle(article);
    }

    @Override
    public int updateArticle(PublisherArticle article) {
        article.setUpdateBy(SecurityUtils.getUsername());
        return articleMapper.updateArticle(article);
    }

    @Override
    public int batchPublishArticles(Long[] articleIds) {
        return articleMapper.batchPublishArticles(articleIds);
    }

    @Override
    public int cnkiCheck(PublisherArticle article) {
        return articleMapper.updateCnkiStatus(article);
    }

    @Override
    public int deleteArticleByIds(Long[] articleIds) {
        return articleMapper.deleteArticleByIds(articleIds);
    }
}