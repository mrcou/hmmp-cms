package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherOrder;
import com.hmmp.system.mapper.publisher.PublisherOrderMapper;
import com.hmmp.system.service.publisher.IPublisherOrderService;

@Service
public class PublisherOrderServiceImpl implements IPublisherOrderService {

    @Autowired
    private PublisherOrderMapper orderMapper;

    @Override
    public List<PublisherOrder> selectOrderList(PublisherOrder order) {
        return orderMapper.selectOrderList(order);
    }

    @Override
    public PublisherOrder selectOrderById(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public int insertOrder(PublisherOrder order) {
        order.setCreateBy(SecurityUtils.getUsername());
        return orderMapper.insertOrder(order);
    }

    @Override
    public int updateOrder(PublisherOrder order) {
        order.setUpdateBy(SecurityUtils.getUsername());
        return orderMapper.updateOrder(order);
    }

    @Override
    public int payOrder(PublisherOrder order) {
        order.setUpdateBy(SecurityUtils.getUsername());
        order.setPayTime(new Date());
        return orderMapper.updateOrderPayStatus(order);
    }

    @Override
    public int shipOrder(PublisherOrder order) {
        order.setUpdateBy(SecurityUtils.getUsername());
        order.setShippingTime(new Date());
        return orderMapper.updateOrderShipStatus(order);
    }

    @Override
    public int deleteOrderByIds(Long[] orderIds) {
        return orderMapper.deleteOrderByIds(orderIds);
    }
}