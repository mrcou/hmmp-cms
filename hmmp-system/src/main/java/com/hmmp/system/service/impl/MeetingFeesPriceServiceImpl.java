package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingFeesPrice;
import com.hmmp.system.mapper.meeting.MeetingFeesPriceMapper;
import com.hmmp.system.service.IMeetingFeesPriceService;

/**
 * 注册费价格 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingFeesPriceServiceImpl implements IMeetingFeesPriceService
{
    @Autowired
    private MeetingFeesPriceMapper priceMapper;

    @Override
    public List<MeetingFeesPrice> selectPriceList(MeetingFeesPrice price)
    {
        return priceMapper.selectPriceList(price);
    }

    @Override
    public MeetingFeesPrice selectPriceById(Long priceId)
    {
        return priceMapper.selectPriceById(priceId);
    }

    @Override
    public int insertPrice(MeetingFeesPrice price)
    {
        return priceMapper.insertPrice(price);
    }

    @Override
    public int updatePrice(MeetingFeesPrice price)
    {
        return priceMapper.updatePrice(price);
    }

    @Override
    public int deletePriceByIds(Long[] priceIds)
    {
        return priceMapper.deletePriceByIds(priceIds);
    }
}