package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherNewsColumn;

public interface PublisherNewsColumnMapper
{
    public List<PublisherNewsColumn> selectNewsColumnList(PublisherNewsColumn column);

    public PublisherNewsColumn selectNewsColumnById(Long columnId);

    public PublisherNewsColumn checkNewsColumnNameUnique(PublisherNewsColumn column);

    public int countContentByColumnId(Long columnId);

    public int insertNewsColumn(PublisherNewsColumn column);

    public int updateNewsColumn(PublisherNewsColumn column);

    public int deleteNewsColumnByIds(Long[] columnIds);
}
