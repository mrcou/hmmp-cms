package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherColumn;

public interface IPublisherColumnService {
    public List<PublisherColumn> selectColumnList(PublisherColumn column);
    public PublisherColumn selectColumnById(Long columnId);
    public int insertColumn(PublisherColumn column);
    public int updateColumn(PublisherColumn column);
    public int deleteColumnByIds(Long[] columnIds);
}