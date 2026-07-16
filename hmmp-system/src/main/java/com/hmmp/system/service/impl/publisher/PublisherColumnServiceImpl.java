package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
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
    public int insertColumn(PublisherColumn column) {
        column.setCreateBy(SecurityUtils.getUsername());
        return columnMapper.insertColumn(column);
    }

    @Override
    public int updateColumn(PublisherColumn column) {
        column.setUpdateBy(SecurityUtils.getUsername());
        return columnMapper.updateColumn(column);
    }

    @Override
    public int deleteColumnByIds(Long[] columnIds) {
        return columnMapper.deleteColumnByIds(columnIds);
    }
}