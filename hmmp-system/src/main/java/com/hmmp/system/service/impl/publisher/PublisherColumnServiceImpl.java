package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.system.domain.publisher.PublisherColumn;
import com.hmmp.system.mapper.publisher.PublisherColumnMapper;
import com.hmmp.system.service.publisher.IPublisherColumnService;

@Service
public class PublisherColumnServiceImpl implements IPublisherColumnService {

    @Autowired
    private PublisherColumnMapper columnMapper;

    @Override
    public List<PublisherColumn> selectColumnList(PublisherColumn column) {
        return columnMapper.selectColumnList(column);
    }

    @Override
    public PublisherColumn selectColumnById(Long columnId) {
        return columnMapper.selectColumnById(columnId);
    }

    @Override
    public boolean checkColumnUnique(PublisherColumn column) {
        Long columnId = StringUtils.isNull(column.getColumnId()) ? -1L : column.getColumnId();
        PublisherColumn info = columnMapper.checkColumnUnique(column);
        if (StringUtils.isNotNull(info) && info.getColumnId().longValue() != columnId.longValue()) {
            return false;
        }
        return true;
    }

    @Override
    public int insertColumn(PublisherColumn column) {
        normalizeColumnFields(column);
        if (column.getParentId() == null) {
            column.setParentId(0L);
        }
        if (StringUtils.isEmpty(column.getNeedRecommender())) {
            column.setNeedRecommender("0");
        }
        if (StringUtils.isEmpty(column.getStatus())) {
            column.setStatus("0");
        }
        if (column.getOrderNum() == null) {
            column.setOrderNum(0);
        }
        column.setCreateBy(SecurityUtils.getUsername());
        return columnMapper.insertColumn(column);
    }

    @Override
    public int updateColumn(PublisherColumn column) {
        normalizeColumnFields(column);
        column.setUpdateBy(SecurityUtils.getUsername());
        return columnMapper.updateColumn(column);
    }

    @Override
    public int deleteColumnByIds(Long[] columnIds) {
        return columnMapper.deleteColumnByIds(columnIds);
    }

    private void normalizeColumnFields(PublisherColumn column) {
        if (StringUtils.isEmpty(column.getNameEn()) && !StringUtils.isEmpty(column.getColumnEnName())) {
            column.setNameEn(column.getColumnEnName());
        }
        if (StringUtils.isEmpty(column.getColumnEnName()) && !StringUtils.isEmpty(column.getNameEn())) {
            column.setColumnEnName(column.getNameEn());
        }
        if (column.getOrderNum() == null && column.getSequence() != null) {
            column.setOrderNum(column.getSequence());
        }
        if (column.getSequence() == null && column.getOrderNum() != null) {
            column.setSequence(column.getOrderNum());
        }
        if (StringUtils.isEmpty(column.getIsPostColumn())) {
            column.setIsPostColumn(resolveIsPostColumn(column.getUsageScope()));
        }
        if (StringUtils.isEmpty(column.getUsageScope())) {
            column.setUsageScope(resolveUsageScope(column.getIsPostColumn()));
        }
    }

    private String resolveIsPostColumn(String usageScope) {
        if (StringUtils.isEmpty(usageScope)) {
            return "1";
        }
        if ("0".equals(usageScope) || "1".equals(usageScope) || "2".equals(usageScope)) {
            return usageScope;
        }
        if (usageScope.contains("投稿")) {
            return "1";
        }
        if (usageScope.contains("内部")) {
            return "2";
        }
        return "0";
    }

    private String resolveUsageScope(String isPostColumn) {
        if ("0".equals(isPostColumn)) {
            return "网刊发布";
        }
        if ("2".equals(isPostColumn)) {
            return "内部查询、网刊发布";
        }
        return "投稿、内部查询、网刊发布";
    }
}
