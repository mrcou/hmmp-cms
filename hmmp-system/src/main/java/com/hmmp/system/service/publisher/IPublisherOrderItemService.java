package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherOrderItem;

public interface IPublisherOrderItemService {
    public List<PublisherOrderItem> selectOrderItemList(PublisherOrderItem orderItem);
    public PublisherOrderItem selectOrderItemById(Long itemId);
    public List<PublisherOrderItem> selectOrderItemByOrderId(Long orderId);
    public int insertOrderItem(PublisherOrderItem orderItem);
    public int updateOrderItem(PublisherOrderItem orderItem);
    public int deleteOrderItemByIds(Long[] itemIds);
}