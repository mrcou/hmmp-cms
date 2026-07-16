package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherReader;
import com.hmmp.system.mapper.publisher.PublisherReaderMapper;
import com.hmmp.system.service.publisher.IPublisherReaderService;

@Service
public class PublisherReaderServiceImpl implements IPublisherReaderService {

    @Autowired
    private PublisherReaderMapper readerMapper;

    @Override
    public List<PublisherReader> selectReaderList(PublisherReader reader) {
        return readerMapper.selectReaderList(reader);
    }

    @Override
    public PublisherReader selectReaderById(Long readerId) {
        return readerMapper.selectReaderById(readerId);
    }

    @Override
    public int insertReader(PublisherReader reader) {
        reader.setCreateBy(SecurityUtils.getUsername());
        return readerMapper.insertReader(reader);
    }

    @Override
    public int updateReader(PublisherReader reader) {
        reader.setUpdateBy(SecurityUtils.getUsername());
        return readerMapper.updateReader(reader);
    }

    @Override
    public int updateReaderStatus(PublisherReader reader) {
        reader.setUpdateBy(SecurityUtils.getUsername());
        return readerMapper.updateReaderStatus(reader);
    }

    @Override
    public int deleteReaderByIds(Long[] readerIds) {
        return readerMapper.deleteReaderByIds(readerIds);
    }
}