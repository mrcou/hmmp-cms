package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherPrice;

public interface IPublisherPriceService {
    public List<PublisherPrice> selectPriceList(PublisherPrice price);
    public PublisherPrice selectPriceById(Long priceId);
    public int insertPrice(PublisherPrice price);
    public int updatePrice(PublisherPrice price);
    public int deletePriceByIds(Long[] priceIds);
}