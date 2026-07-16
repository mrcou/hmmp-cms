package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingFeesPrice;

/**
 * 注册费价格表 数据层
 *
 * @author hmmp
 */
public interface MeetingFeesPriceMapper
{
    public List<MeetingFeesPrice> selectPriceList(MeetingFeesPrice price);

    public MeetingFeesPrice selectPriceById(Long priceId);

    public int insertPrice(MeetingFeesPrice price);

    public int updatePrice(MeetingFeesPrice price);

    public int deletePriceById(Long priceId);

    public int deletePriceByIds(Long[] priceIds);
}