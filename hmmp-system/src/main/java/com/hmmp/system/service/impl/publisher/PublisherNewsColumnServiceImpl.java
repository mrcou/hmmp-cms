package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hmmp.common.exception.ServiceException;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.system.domain.publisher.PublisherNewsColumn;
import com.hmmp.system.mapper.publisher.PublisherNewsColumnMapper;
import com.hmmp.system.service.publisher.IPublisherNewsColumnService;

@Service
public class PublisherNewsColumnServiceImpl implements IPublisherNewsColumnService
{
    @Autowired
    private PublisherNewsColumnMapper columnMapper;

    @Override
    public List<PublisherNewsColumn> selectNewsColumnList(PublisherNewsColumn column)
    {
        return columnMapper.selectNewsColumnList(column);
    }

    @Override
    public PublisherNewsColumn selectNewsColumnById(Long columnId)
    {
        return columnMapper.selectNewsColumnById(columnId);
    }

    @Override
    public boolean checkNewsColumnNameUnique(PublisherNewsColumn column)
    {
        if (StringUtils.isNotEmpty(column.getColumnName()))
        {
            column.setColumnName(column.getColumnName().trim());
        }
        Long columnId = StringUtils.isNull(column.getColumnId()) ? -1L : column.getColumnId();
        PublisherNewsColumn info = columnMapper.checkNewsColumnNameUnique(column);
        return StringUtils.isNull(info) || info.getColumnId().longValue() == columnId.longValue();
    }

    @Override
    public int insertNewsColumn(PublisherNewsColumn column)
    {
        normalize(column);
        column.setCreateBy(SecurityUtils.getUsername());
        return columnMapper.insertNewsColumn(column);
    }

    @Override
    public int updateNewsColumn(PublisherNewsColumn column)
    {
        normalize(column);
        column.setUpdateBy(SecurityUtils.getUsername());
        return columnMapper.updateNewsColumn(column);
    }

    @Override
    @Transactional
    public int deleteNewsColumnByIds(Long[] columnIds)
    {
        for (Long columnId : columnIds)
        {
            if (columnMapper.countContentByColumnId(columnId) > 0)
            {
                PublisherNewsColumn column = columnMapper.selectNewsColumnById(columnId);
                String name = column == null ? String.valueOf(columnId) : column.getColumnName();
                throw new ServiceException("栏目“" + name + "”下存在内容，无法删除");
            }
        }
        return columnMapper.deleteNewsColumnByIds(columnIds);
    }

    private void normalize(PublisherNewsColumn column)
    {
        if (StringUtils.isEmpty(column.getColumnName()))
        {
            throw new ServiceException("栏目名称不能为空");
        }
        column.setColumnName(column.getColumnName().trim());
        if (column.getSortOrder() == null)
        {
            column.setSortOrder(0);
        }
    }
}
