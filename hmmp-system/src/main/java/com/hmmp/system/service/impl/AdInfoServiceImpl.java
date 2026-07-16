package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdInfo;
import com.hmmp.system.mapper.AdInfoMapper;
import com.hmmp.system.service.IAdInfoService;

/**
 * 广告信息 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdInfoServiceImpl implements IAdInfoService
{
    @Autowired
    private AdInfoMapper adMapper;

    @Override
    public AdInfo selectAdById(Long adId)
    {
        return adMapper.selectAdById(adId);
    }

    @Override
    public List<AdInfo> selectAdList(AdInfo ad)
    {
        return adMapper.selectAdList(ad);
    }

    @Override
    public int insertAd(AdInfo ad)
    {
        return adMapper.insertAd(ad);
    }

    @Override
    public int updateAd(AdInfo ad)
    {
        return adMapper.updateAd(ad);
    }

    @Override
    public void deleteAdByIds(Long[] adIds)
    {
        adMapper.deleteAdByIds(adIds);
    }
}