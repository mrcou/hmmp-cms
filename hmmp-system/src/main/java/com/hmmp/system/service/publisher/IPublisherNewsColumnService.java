package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherNewsColumn;

public interface IPublisherNewsColumnService
{
    public List<PublisherNewsColumn> selectNewsColumnList(PublisherNewsColumn column);

    public PublisherNewsColumn selectNewsColumnById(Long columnId);

    public boolean checkNewsColumnNameUnique(PublisherNewsColumn column);

    public int insertNewsColumn(PublisherNewsColumn column);

    public int updateNewsColumn(PublisherNewsColumn column);

    public int deleteNewsColumnByIds(Long[] columnIds);
}
