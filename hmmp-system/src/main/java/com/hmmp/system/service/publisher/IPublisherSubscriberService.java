package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherSubscriber;

public interface IPublisherSubscriberService {
    public List<PublisherSubscriber> selectSubscriberList(PublisherSubscriber subscriber);
    public PublisherSubscriber selectSubscriberById(Long subscriberId);
    public int insertSubscriber(PublisherSubscriber subscriber);
    public int updateSubscriber(PublisherSubscriber subscriber);
    public int updateSubscriberStatus(PublisherSubscriber subscriber);
    public int deleteSubscriberByIds(Long[] subscriberIds);
}