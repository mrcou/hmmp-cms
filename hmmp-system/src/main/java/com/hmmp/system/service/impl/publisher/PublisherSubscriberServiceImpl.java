package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherSubscriber;
import com.hmmp.system.mapper.publisher.PublisherSubscriberMapper;
import com.hmmp.system.service.publisher.IPublisherSubscriberService;

@Service
public class PublisherSubscriberServiceImpl implements IPublisherSubscriberService {

    @Autowired
    private PublisherSubscriberMapper subscriberMapper;

    @Override
    public List<PublisherSubscriber> selectSubscriberList(PublisherSubscriber subscriber) {
        return subscriberMapper.selectSubscriberList(subscriber);
    }

    @Override
    public PublisherSubscriber selectSubscriberById(Long subscriberId) {
        return subscriberMapper.selectSubscriberById(subscriberId);
    }

    @Override
    public int insertSubscriber(PublisherSubscriber subscriber) {
        subscriber.setCreateBy(SecurityUtils.getUsername());
        return subscriberMapper.insertSubscriber(subscriber);
    }

    @Override
    public int updateSubscriber(PublisherSubscriber subscriber) {
        subscriber.setUpdateBy(SecurityUtils.getUsername());
        return subscriberMapper.updateSubscriber(subscriber);
    }

    @Override
    public int updateSubscriberStatus(PublisherSubscriber subscriber) {
        subscriber.setUpdateBy(SecurityUtils.getUsername());
        return subscriberMapper.updateSubscriberStatus(subscriber);
    }

    @Override
    public int deleteSubscriberByIds(Long[] subscriberIds) {
        return subscriberMapper.deleteSubscriberByIds(subscriberIds);
    }
}