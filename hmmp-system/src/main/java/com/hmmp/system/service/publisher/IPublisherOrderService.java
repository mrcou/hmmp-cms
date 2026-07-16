package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherOrder;

public interface IPublisherOrderService {
    public List<PublisherOrder> selectOrderList(PublisherOrder order);
    public PublisherOrder selectOrderById(Long orderId);
    public int insertOrder(PublisherOrder order);
    public int updateOrder(PublisherOrder order);
    public int payOrder(PublisherOrder order);
    public int shipOrder(PublisherOrder order);
    public int deleteOrderByIds(Long[] orderIds);
}