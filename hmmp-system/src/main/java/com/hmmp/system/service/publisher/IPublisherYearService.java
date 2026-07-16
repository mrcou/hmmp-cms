package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherYear;

public interface IPublisherYearService {
    public List<PublisherYear> selectYearList(PublisherYear year);
    public PublisherYear selectYearById(Long yearId);
    public boolean checkYearUnique(Integer year);
    public int insertYear(PublisherYear year);
    public int updateYear(PublisherYear year);
    public int deleteYearByIds(Long[] yearIds);
}