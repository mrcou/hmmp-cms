package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherPushKeyword;

public interface IPublisherPushKeywordService {
    public List<PublisherPushKeyword> selectPushKeywordList(PublisherPushKeyword pushKeyword);
    public PublisherPushKeyword selectPushKeywordById(Long pushId);
    public int executePush(PublisherPushKeyword pushKeyword);
    public int deletePushKeywordByIds(Long[] pushIds);
}