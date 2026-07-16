package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherPrice;

public interface PublisherPriceMapper {
    public List<PublisherPrice> selectPriceList(PublisherPrice price);
    public PublisherPrice selectPriceById(Long priceId);
    public int insertPrice(PublisherPrice price);
    public int updatePrice(PublisherPrice price);
    public int deletePriceById(Long priceId);
    public int deletePriceByIds(Long[] priceIds);
}