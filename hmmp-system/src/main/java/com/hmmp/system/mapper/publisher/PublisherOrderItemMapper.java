package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherOrderItem;

public interface PublisherOrderItemMapper {
    public List<PublisherOrderItem> selectOrderItemList(PublisherOrderItem orderItem);
    public PublisherOrderItem selectOrderItemById(Long itemId);
    public List<PublisherOrderItem> selectOrderItemByOrderId(Long orderId);
    public int insertOrderItem(PublisherOrderItem orderItem);
    public int updateOrderItem(PublisherOrderItem orderItem);
    public int deleteOrderItemById(Long itemId);
    public int deleteOrderItemByIds(Long[] itemIds);
    public int deleteByOrderId(Long orderId);
}