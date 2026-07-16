package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherReader;

public interface IPublisherReaderService {
    public List<PublisherReader> selectReaderList(PublisherReader reader);
    public PublisherReader selectReaderById(Long readerId);
    public int insertReader(PublisherReader reader);
    public int updateReader(PublisherReader reader);
    public int updateReaderStatus(PublisherReader reader);
    public int deleteReaderByIds(Long[] readerIds);
}