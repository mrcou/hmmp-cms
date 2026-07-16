package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherReader;

public interface PublisherReaderMapper {
    public List<PublisherReader> selectReaderList(PublisherReader reader);
    public PublisherReader selectReaderById(Long readerId);
    public PublisherReader selectReaderByUserId(Long userId);
    public int insertReader(PublisherReader reader);
    public int updateReader(PublisherReader reader);
    public int updateReaderStatus(PublisherReader reader);
    public int deleteReaderById(Long readerId);
    public int deleteReaderByIds(Long[] readerIds);
}