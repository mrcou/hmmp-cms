package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherPushSubscriber;

public interface IPublisherPushSubscriberService {
    public List<PublisherPushSubscriber> selectPushSubscriberList(PublisherPushSubscriber pushSubscriber);
    public PublisherPushSubscriber selectPushSubscriberById(Long subId);
    public int insertPushSubscriber(PublisherPushSubscriber pushSubscriber);
    public int updatePushSubscriber(PublisherPushSubscriber pushSubscriber);
    public int deletePushSubscriberByIds(Long[] subIds);
}