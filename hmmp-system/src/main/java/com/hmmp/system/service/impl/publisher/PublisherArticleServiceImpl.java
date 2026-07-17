package com.hmmp.system.service.impl.publisher;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.hmmp.common.config.RuoYiConfig;
import com.hmmp.common.exception.ServiceException;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.common.utils.file.FileUploadUtils;
import com.hmmp.system.domain.publisher.PublisherArticle;
import com.hmmp.system.domain.publisher.PublisherArticleBatchImport;
import com.hmmp.system.domain.publisher.PublisherColumn;
import com.hmmp.system.domain.publisher.PublisherIssue;
import com.hmmp.system.domain.publisher.PublisherYear;
import com.hmmp.system.mapper.publisher.PublisherArticleMapper;
import com.hmmp.system.mapper.publisher.PublisherColumnMapper;
import com.hmmp.system.mapper.publisher.PublisherIssueMapper;
import com.hmmp.system.mapper.publisher.PublisherYearMapper;
import com.hmmp.system.service.publisher.IPublisherArticleService;

@Service
public class PublisherArticleServiceImpl implements IPublisherArticleService {

    @Autowired
    private PublisherArticleMapper articleMapper;

    @Autowired
    private PublisherYearMapper yearMapper;

    @Autowired
    private PublisherIssueMapper issueMapper;

    @Autowired
    private PublisherColumnMapper columnMapper;

    @Override
    public List<PublisherArticle> selectArticleList(PublisherArticle article) {
        return articleMapper.selectArticleList(article);
    }

    @Override
    public PublisherArticle selectArticleById(Long articleId) {
        return articleMapper.selectArticleById(articleId);
    }

    @Override
    public PublisherArticle selectArticleByFileNo(String fileNo) {
        return articleMapper.selectArticleByFileNo(fileNo);
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
    public int syncCnkiCitedCount(Long[] articleIds) {
        if (articleIds == null || articleIds.length == 0) {
            return 0;
        }
        return articleMapper.syncCnkiCitedCount(articleIds);
    }

    @Override
    public int deleteArticleByIds(Long[] articleIds) {
        return articleMapper.deleteArticleByIds(articleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importArticles(List<PublisherArticleBatchImport> rows, boolean updateSupport, String operName) {
        if (rows == null || rows.isEmpty()) {
            throw new ServiceException("导入数据不能为空");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        Map<String, Long> yearCache = new HashMap<>();
        Map<String, Long> issueCache = new HashMap<>();
        Map<String, Long> columnCache = new HashMap<>();

        for (int i = 0; i < rows.size(); i++) {
            PublisherArticleBatchImport row = rows.get(i);
            int rowNo = i + 2;
            try {
                if (StringUtils.isEmpty(row.getTitle())) {
                    throw new ServiceException("中文标题不能为空");
                }
                if (row.getYearNo() == null || row.getPeriod() == null) {
                    throw new ServiceException("年、期不能为空");
                }

                Long yearId = resolveYearId(row.getJournalCode(), row.getYearNo(), yearCache, operName);
                Long issueId = resolveIssueId(yearId, row.getYearNo(), row.getVolume(), row.getPeriod(), issueCache, operName);
                Long columnId = resolveColumnId(row.getColumnCode(), row.getColumnName(), columnCache, operName);

                PublisherArticle article = mapImportRow(row, issueId, columnId);
                article.setStatus("1");
                if (article.getPublishTime() == null) {
                    article.setPublishTime(new Date());
                }

                PublisherArticle existing = StringUtils.isNotEmpty(row.getFileNo())
                    ? articleMapper.selectArticleByFileNo(row.getFileNo())
                    : null;
                if (existing != null) {
                    if (!updateSupport) {
                        throw new ServiceException("稿件编号 " + row.getFileNo() + " 已存在");
                    }
                    article.setArticleId(existing.getArticleId());
                    article.setUpdateBy(operName);
                    articleMapper.updateArticle(article);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、第").append(rowNo)
                        .append("行 稿件编号 ").append(row.getFileNo()).append(" 更新成功");
                } else {
                    article.setCreateBy(operName);
                    article.setUpdateBy(operName);
                    articleMapper.insertArticle(article);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、第").append(rowNo)
                        .append("行 《").append(row.getTitle()).append("》 导入成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、第" + rowNo + "行导入失败：" + e.getMessage();
                failureMsg.append(msg);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        return successMsg.toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importPdfZip(MultipartFile file, String operName) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new ServiceException("请选择 ZIP 文件");
        }
        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.toLowerCase().endsWith(".zip")) {
            throw new ServiceException("仅支持 zip 格式");
        }

        File tempZip = File.createTempFile("batch-pdf-", ".zip");
        file.transferTo(tempZip);

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        try (ZipFile zipFile = openZipFile(tempZip)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                String entryName = FilenameUtils.getName(entry.getName());
                if (StringUtils.isEmpty(entryName) || !entryName.toLowerCase().endsWith(".pdf")) {
                    continue;
                }
                String fileNo = FilenameUtils.getBaseName(entryName);
                try {
                    PublisherArticle article = articleMapper.selectArticleByFileNo(fileNo);
                    if (article == null) {
                        throw new ServiceException("未找到稿件编号 " + fileNo + " 对应的文章，请先导入 Excel");
                    }
                    String relativeName = "publisher/pdf/" + fileNo + ".pdf";
                    File dest = FileUploadUtils.getAbsoluteFile(RuoYiConfig.getProfile(), relativeName);
                    try (InputStream in = zipFile.getInputStream(entry);
                         java.io.FileOutputStream out = new java.io.FileOutputStream(dest)) {
                        org.apache.commons.io.IOUtils.copy(in, out);
                    }
                    PublisherArticle update = new PublisherArticle();
                    update.setArticleId(article.getArticleId());
                    update.setPdfPath("/" + relativeName);
                    update.setUpdateBy(operName);
                    articleMapper.updateArticle(update);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、").append(entryName).append(" 绑定成功");
                } catch (Exception e) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、").append(entryName)
                        .append(" 失败：").append(e.getMessage());
                }
            }
        } finally {
            if (tempZip.exists()) {
                tempZip.delete();
            }
        }

        if (successNum == 0 && failureNum == 0) {
            throw new ServiceException("ZIP 中未找到 PDF 文件");
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "部分全文导入失败！成功 " + successNum + " 个，失败 " + failureNum + " 个，明细如下：");
            if (successNum == 0) {
                throw new ServiceException(failureMsg.toString());
            }
            return failureMsg.toString() + "<br/>成功明细：" + successMsg;
        }
        successMsg.insert(0, "全文导入成功！共 " + successNum + " 个 PDF，明细如下：");
        return successMsg.toString();
    }

    private ZipFile openZipFile(File tempZip) throws Exception {
        try {
            return new ZipFile(tempZip, Charset.forName("GBK"));
        } catch (Exception e) {
            return new ZipFile(tempZip, Charset.forName("UTF-8"));
        }
    }

    private Long resolveYearId(String journalCode, Integer year, Map<String, Long> cache, String operName) {
        String code = StringUtils.isEmpty(journalCode) ? "" : journalCode.trim();
        String cacheKey = code + "_" + year;
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }
        PublisherYear query = new PublisherYear();
        query.setJournalCode(code);
        query.setYear(year);
        PublisherYear existing = yearMapper.checkYearUnique(query);
        if (existing != null) {
            cache.put(cacheKey, existing.getYearId());
            return existing.getYearId();
        }
        PublisherYear create = new PublisherYear();
        create.setJournalCode(code);
        create.setYear(year);
        create.setNameCn(year + "年");
        create.setIsFree("0");
        create.setStatus("0");
        create.setCreateBy(operName);
        yearMapper.insertYear(create);
        cache.put(cacheKey, create.getYearId());
        return create.getYearId();
    }

    private Long resolveIssueId(Long yearId, Integer year, Integer volume, Integer period,
        Map<String, Long> cache, String operName) {
        String key = year + "_" + (volume == null ? "" : volume) + "_" + period;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        PublisherIssue query = new PublisherIssue();
        query.setYear(year);
        query.setPeriod(String.valueOf(period));
        if (volume != null) {
            query.setVolume(volume);
        }
        List<PublisherIssue> list = issueMapper.selectIssueList(query);
        PublisherIssue matched = null;
        if (list != null) {
            for (PublisherIssue issue : list) {
                if (volume == null || volume.equals(issue.getVolume())
                    || (issue.getVolume() == null && volume == null)) {
                    matched = issue;
                    break;
                }
            }
            if (matched == null && !list.isEmpty() && volume != null) {
                for (PublisherIssue issue : list) {
                    if (issue.getVolume() == null) {
                        matched = issue;
                        break;
                    }
                }
            }
        }
        if (matched != null) {
            if (matched.getVolume() == null && volume != null) {
                PublisherIssue patch = new PublisherIssue();
                patch.setIssueId(matched.getIssueId());
                patch.setVolume(volume);
                patch.setUpdateBy(operName);
                issueMapper.updateIssue(patch);
                matched.setVolume(volume);
            }
            cache.put(key, matched.getIssueId());
            return matched.getIssueId();
        }
        PublisherIssue create = new PublisherIssue();
        create.setYearId(yearId);
        create.setYear(year);
        create.setVolume(volume);
        create.setPeriod(String.valueOf(period));
        String issueName = year + "年第" + (volume != null ? volume + "卷第" : "") + period + "期";
        create.setIssueName(issueName);
        create.setNameCn("第" + period + "期");
        create.setNameEn("No." + period);
        create.setIsFree("0");
        create.setIsLatest("0");
        create.setSubscribeStatus("0");
        create.setDoiStatus("0");
        create.setCstrStatus("0");
        create.setBaiduStatus("0");
        create.setStatus("1");
        create.setPublishTime(new Date());
        create.setCreateBy(operName);
        issueMapper.insertIssue(create);
        cache.put(key, create.getIssueId());
        return create.getIssueId();
    }

    private Long resolveColumnId(String columnCode, String columnName, Map<String, Long> cache, String operName) {
        String code = StringUtils.trim(columnCode);
        String name = StringUtils.trim(columnName);
        String key = (code == null ? "" : code) + "|" + (name == null ? "" : name);
        if (StringUtils.isEmpty(code) && StringUtils.isEmpty(name)) {
            return null;
        }
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (StringUtils.isNotEmpty(code)) {
            PublisherColumn byCode = new PublisherColumn();
            byCode.setColumnCode(code);
            List<PublisherColumn> codeList = columnMapper.selectColumnList(byCode);
            if (codeList != null && !codeList.isEmpty()) {
                Long id = codeList.get(0).getColumnId();
                cache.put(key, id);
                return id;
            }
        }
        if (StringUtils.isNotEmpty(name)) {
            PublisherColumn byName = new PublisherColumn();
            byName.setColumnName(name);
            List<PublisherColumn> nameList = columnMapper.selectColumnList(byName);
            if (nameList != null) {
                for (PublisherColumn col : nameList) {
                    if (name.equals(col.getColumnName())) {
                        cache.put(key, col.getColumnId());
                        return col.getColumnId();
                    }
                }
            }
        }
        PublisherColumn create = new PublisherColumn();
        create.setColumnName(StringUtils.isNotEmpty(name) ? name : ("栏目" + code));
        create.setColumnCode(StringUtils.isNotEmpty(code) ? code : "");
        create.setParentId(0L);
        create.setOrderNum(0);
        create.setNeedRecommender("0");
        create.setUsageScope("网刊发布");
        create.setStatus("0");
        create.setCreateBy(operName);
        columnMapper.insertColumn(create);
        cache.put(key, create.getColumnId());
        return create.getColumnId();
    }

    private PublisherArticle mapImportRow(PublisherArticleBatchImport row, Long issueId, Long columnId) {
        PublisherArticle article = new PublisherArticle();
        article.setIssueId(issueId);
        article.setColumnId(columnId);
        article.setJournalCode(row.getJournalCode());
        article.setFileNo(row.getFileNo());
        article.setTitle(row.getTitle());
        article.setTitleEn(row.getTitleEn());
        article.setAbstractText(row.getAbstractText());
        article.setAbstractEn(row.getAbstractEn());
        article.setKeywords(row.getKeywords());
        article.setKeywordsEn(row.getKeywordsEn());
        article.setFundZh(row.getFundZh());
        article.setFundEn(row.getFundEn());
        article.setPageStart(row.getPageStart());
        article.setPageEnd(row.getPageEnd());
        article.setSubmitTime(row.getSubmitTime());
        article.setLastModifyTime(row.getLastModifyTime());
        article.setPublishTime(row.getPublishTime());
        article.setYearNo(row.getYearNo());
        article.setVolume(row.getVolume());
        article.setPeriod(row.getPeriod());
        article.setReferenceText(row.getReferenceText());
        article.setCitationText(row.getCitationText());
        article.setAuthorNames(row.getAuthorNames());
        article.setAuthorNamesEn(row.getAuthorNamesEn());
        article.setAuthorOrgZh(row.getAuthorOrgZh());
        article.setAuthorOrgEn(row.getAuthorOrgEn());
        article.setDoi(row.getDoi());
        article.setViewCount(defaultZero(row.getViewCount()));
        article.setDownloadCount(defaultZero(row.getDownloadCount()));
        article.setHtmlViewCount(defaultZero(row.getHtmlViewCount()));
        article.setCitedCount(defaultZero(row.getCitedCount()));
        article.setCnkiCitedCount(defaultZero(row.getCnkiCitedCount()));
        article.setArticleUrl(row.getArticleUrl());
        return article;
    }

    private Integer defaultZero(Integer value) {
        return value == null ? 0 : value;
    }
}
