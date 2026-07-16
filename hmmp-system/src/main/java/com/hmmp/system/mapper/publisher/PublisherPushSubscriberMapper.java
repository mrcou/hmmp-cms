package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherPushSubscriber;

public interface PublisherPushSubscriberMapper {
    public List<PublisherPushSubscriber> selectPushSubscriberList(PublisherPushSubscriber pushSubscriber);
    public PublisherPushSubscriber selectPushSubscriberById(Long subId);
    public int insertPushSubscriber(PublisherPushSubscriber pushSubscriber);
    public int updatePushSubscriber(PublisherPushSubscriber pushSubscriber);
    public int deletePushSubscriberById(Long subId);
    public int deletePushSubscriberByIds(Long[] subIds);
}