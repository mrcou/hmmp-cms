package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherPushKeyword;

public interface PublisherPushKeywordMapper {
    public List<PublisherPushKeyword> selectPushKeywordList(PublisherPushKeyword pushKeyword);
    public PublisherPushKeyword selectPushKeywordById(Long pushId);
    public int insertPushKeyword(PublisherPushKeyword pushKeyword);
    public int updatePushKeyword(PublisherPushKeyword pushKeyword);
    public int deletePushKeywordById(Long pushId);
    public int deletePushKeywordByIds(Long[] pushIds);
}