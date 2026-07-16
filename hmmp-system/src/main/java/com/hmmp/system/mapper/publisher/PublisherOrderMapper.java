package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherOrder;

public interface PublisherOrderMapper {
    public List<PublisherOrder> selectOrderList(PublisherOrder order);
    public PublisherOrder selectOrderById(Long orderId);
    public PublisherOrder selectOrderByOrderNo(String orderNo);
    public int insertOrder(PublisherOrder order);
    public int updateOrder(PublisherOrder order);
    public int updateOrderPayStatus(PublisherOrder order);
    public int updateOrderShipStatus(PublisherOrder order);
    public int deleteOrderById(Long orderId);
    public int deleteOrderByIds(Long[] orderIds);
}