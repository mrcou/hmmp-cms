package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherColumn;

public interface PublisherColumnMapper {
    public List<PublisherColumn> selectColumnList(PublisherColumn column);
    public PublisherColumn selectColumnById(Long columnId);
    public PublisherColumn checkColumnUnique(PublisherColumn column);
    public int insertColumn(PublisherColumn column);
    public int updateColumn(PublisherColumn column);
    public int deleteColumnById(Long columnId);
    public int deleteColumnByIds(Long[] columnIds);
}
