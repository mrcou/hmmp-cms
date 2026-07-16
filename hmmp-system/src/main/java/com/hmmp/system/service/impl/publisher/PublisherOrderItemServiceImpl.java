package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.publisher.PublisherOrderItem;
import com.hmmp.system.mapper.publisher.PublisherOrderItemMapper;
import com.hmmp.system.service.publisher.IPublisherOrderItemService;

@Service
public class PublisherOrderItemServiceImpl implements IPublisherOrderItemService {

    @Autowired
    private PublisherOrderItemMapper orderItemMapper;

    @Override
    public List<PublisherOrderItem> selectOrderItemList(PublisherOrderItem orderItem) {
        return orderItemMapper.selectOrderItemList(orderItem);
    }

    @Override
    public PublisherOrderItem selectOrderItemById(Long itemId) {
        return orderItemMapper.selectOrderItemById(itemId);
    }

    @Override
    public List<PublisherOrderItem> selectOrderItemByOrderId(Long orderId) {
        return orderItemMapper.selectOrderItemByOrderId(orderId);
    }

    @Override
    public int insertOrderItem(PublisherOrderItem orderItem) {
        return orderItemMapper.insertOrderItem(orderItem);
    }

    @Override
    public int updateOrderItem(PublisherOrderItem orderItem) {
        return orderItemMapper.updateOrderItem(orderItem);
    }

    @Override
    public int deleteOrderItemByIds(Long[] itemIds) {
        return orderItemMapper.deleteOrderItemByIds(itemIds);
    }
}