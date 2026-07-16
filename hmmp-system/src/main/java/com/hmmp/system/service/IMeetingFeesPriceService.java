package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingFeesPrice;

/**
 * 注册费价格 服务层
 *
 * @author hmmp
 */
public interface IMeetingFeesPriceService
{
    public List<MeetingFeesPrice> selectPriceList(MeetingFeesPrice price);

    public MeetingFeesPrice selectPriceById(Long priceId);

    public int insertPrice(MeetingFeesPrice price);

    public int updatePrice(MeetingFeesPrice price);

    public int deletePriceByIds(Long[] priceIds);
}