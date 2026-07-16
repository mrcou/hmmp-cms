package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.publisher.PublisherPushSubscriber;
import com.hmmp.system.mapper.publisher.PublisherPushSubscriberMapper;
import com.hmmp.system.service.publisher.IPublisherPushSubscriberService;

@Service
public class PublisherPushSubscriberServiceImpl implements IPublisherPushSubscriberService {

    @Autowired
    private PublisherPushSubscriberMapper pushSubscriberMapper;

    @Override
    public List<PublisherPushSubscriber> selectPushSubscriberList(PublisherPushSubscriber pushSubscriber) {
        return pushSubscriberMapper.selectPushSubscriberList(pushSubscriber);
    }

    @Override
    public PublisherPushSubscriber selectPushSubscriberById(Long subId) {
        return pushSubscriberMapper.selectPushSubscriberById(subId);
    }

    @Override
    public int insertPushSubscriber(PublisherPushSubscriber pushSubscriber) {
        return pushSubscriberMapper.insertPushSubscriber(pushSubscriber);
    }

    @Override
    public int updatePushSubscriber(PublisherPushSubscriber pushSubscriber) {
        return pushSubscriberMapper.updatePushSubscriber(pushSubscriber);
    }

    @Override
    public int deletePushSubscriberByIds(Long[] subIds) {
        return pushSubscriberMapper.deletePushSubscriberByIds(subIds);
    }
}