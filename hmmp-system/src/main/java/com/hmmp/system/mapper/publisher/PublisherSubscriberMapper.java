package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherSubscriber;

public interface PublisherSubscriberMapper {
    public List<PublisherSubscriber> selectSubscriberList(PublisherSubscriber subscriber);
    public PublisherSubscriber selectSubscriberById(Long subscriberId);
    public int insertSubscriber(PublisherSubscriber subscriber);
    public int updateSubscriber(PublisherSubscriber subscriber);
    public int updateSubscriberStatus(PublisherSubscriber subscriber);
    public int deleteSubscriberById(Long subscriberId);
    public int deleteSubscriberByIds(Long[] subscriberIds);
}