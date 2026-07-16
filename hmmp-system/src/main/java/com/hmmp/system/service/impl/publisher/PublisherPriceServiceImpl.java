package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherPrice;
import com.hmmp.system.mapper.publisher.PublisherPriceMapper;
import com.hmmp.system.service.publisher.IPublisherPriceService;

@Service
public class PublisherPriceServiceImpl implements IPublisherPriceService {

    @Autowired
    private PublisherPriceMapper priceMapper;

    @Override
    public List<PublisherPrice> selectPriceList(PublisherPrice price) {
        return priceMapper.selectPriceList(price);
    }

    @Override
    public PublisherPrice selectPriceById(Long priceId) {
        return priceMapper.selectPriceById(priceId);
    }

    @Override
    public int insertPrice(PublisherPrice price) {
        price.setCreateBy(SecurityUtils.getUsername());
        return priceMapper.insertPrice(price);
    }

    @Override
    public int updatePrice(PublisherPrice price) {
        price.setUpdateBy(SecurityUtils.getUsername());
        return priceMapper.updatePrice(price);
    }

    @Override
    public int deletePriceByIds(Long[] priceIds) {
        return priceMapper.deletePriceByIds(priceIds);
    }
}