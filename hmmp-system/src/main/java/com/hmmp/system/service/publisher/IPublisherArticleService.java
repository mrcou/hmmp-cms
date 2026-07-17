package com.hmmp.system.service.publisher;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.hmmp.system.domain.publisher.PublisherArticle;
import com.hmmp.system.domain.publisher.PublisherArticleBatchImport;

public interface IPublisherArticleService {
    public List<PublisherArticle> selectArticleList(PublisherArticle article);
    public PublisherArticle selectArticleById(Long articleId);
    public PublisherArticle selectArticleByFileNo(String fileNo);
    public int insertArticle(PublisherArticle article);
    public int updateArticle(PublisherArticle article);
    public int batchPublishArticles(Long[] articleIds);
    public int cnkiCheck(PublisherArticle article);
    /** 将知网引用次数同步到被引次数 */
    public int syncCnkiCitedCount(Long[] articleIds);
    public int deleteArticleByIds(Long[] articleIds);

    /**
     * 过刊 Excel 一对一导入
     */
    public String importArticles(List<PublisherArticleBatchImport> rows, boolean updateSupport, String operName);

    /**
     * 过刊全文 ZIP 导入（PDF 文件名与稿件编号对应）
     */
    public String importPdfZip(MultipartFile file, String operName) throws Exception;
}
