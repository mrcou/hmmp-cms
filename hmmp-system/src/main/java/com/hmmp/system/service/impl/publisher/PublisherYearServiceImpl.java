package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.system.domain.publisher.PublisherYear;
import com.hmmp.system.mapper.publisher.PublisherYearMapper;
import com.hmmp.system.service.publisher.IPublisherYearService;

@Service
public class PublisherYearServiceImpl implements IPublisherYearService {

    @Autowired
    private PublisherYearMapper yearMapper;

    @Override
    public List<PublisherYear> selectYearList(PublisherYear year) {
        return yearMapper.selectYearList(year);
    }

    @Override
    public PublisherYear selectYearById(Long yearId) {
        return yearMapper.selectYearById(yearId);
    }

    @Override
    public boolean checkYearUnique(PublisherYear year) {
        Long yearId = year.getYearId() == null ? -1L : year.getYearId();
        PublisherYear info = yearMapper.checkYearUnique(year);
        if (info != null && info.getYearId().longValue() != yearId.longValue()) {
            return false;
        }
        return true;
    }

    @Override
    public int insertYear(PublisherYear year) {
        if (StringUtils.isEmpty(year.getJournalCode())) {
            year.setJournalCode("");
        }
        if (StringUtils.isEmpty(year.getIsFree())) {
            year.setIsFree("0");
        }
        if (StringUtils.isEmpty(year.getStatus())) {
            year.setStatus("0");
        }
        if (StringUtils.isEmpty(year.getNameCn()) && year.getYear() != null) {
            year.setNameCn(year.getYear() + "年");
        }
        year.setCreateBy(SecurityUtils.getUsername());
        return yearMapper.insertYear(year);
    }

    @Override
    public int updateYear(PublisherYear year) {
        year.setUpdateBy(SecurityUtils.getUsername());
        return yearMapper.updateYear(year);
    }

    @Override
    public int deleteYearByIds(Long[] yearIds) {
        return yearMapper.deleteYearByIds(yearIds);
    }
}
