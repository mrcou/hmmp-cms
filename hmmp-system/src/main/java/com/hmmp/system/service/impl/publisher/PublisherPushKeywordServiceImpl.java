package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherPushKeyword;
import com.hmmp.system.mapper.publisher.PublisherPushKeywordMapper;
import com.hmmp.system.service.publisher.IPublisherPushKeywordService;

@Service
public class PublisherPushKeywordServiceImpl implements IPublisherPushKeywordService {

    @Autowired
    private PublisherPushKeywordMapper pushKeywordMapper;

    @Override
    public List<PublisherPushKeyword> selectPushKeywordList(PublisherPushKeyword pushKeyword) {
        return pushKeywordMapper.selectPushKeywordList(pushKeyword);
    }

    @Override
    public PublisherPushKeyword selectPushKeywordById(Long pushId) {
        return pushKeywordMapper.selectPushKeywordById(pushId);
    }

    @Override
    public int executePush(PublisherPushKeyword pushKeyword) {
        pushKeyword.setCreateBy(SecurityUtils.getUsername());
        pushKeyword.setPushTime(new Date());
        pushKeyword.setPushStatus("1");
        return pushKeywordMapper.insertPushKeyword(pushKeyword);
    }

    @Override
    public int deletePushKeywordByIds(Long[] pushIds) {
        return pushKeywordMapper.deletePushKeywordByIds(pushIds);
    }
}