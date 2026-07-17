package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherColumn;

public interface IPublisherColumnService {
    public List<PublisherColumn> selectColumnList(PublisherColumn column);
    public PublisherColumn selectColumnById(Long columnId);
    /**
     * 校验同一杂志下栏目ID是否唯一
     * @return true 唯一可用
     */
    public boolean checkColumnUnique(PublisherColumn column);
    public int insertColumn(PublisherColumn column);
    public int updateColumn(PublisherColumn column);
    public int deleteColumnByIds(Long[] columnIds);
}
