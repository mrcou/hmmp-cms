package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherYear;

public interface PublisherYearMapper {
    public List<PublisherYear> selectYearList(PublisherYear year);
    public PublisherYear selectYearById(Long yearId);
    public PublisherYear checkYearUnique(Integer year);
    public int insertYear(PublisherYear year);
    public int updateYear(PublisherYear year);
    public int deleteYearById(Long yearId);
    public int deleteYearByIds(Long[] yearIds);
}