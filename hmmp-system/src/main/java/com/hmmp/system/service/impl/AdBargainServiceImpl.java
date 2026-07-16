package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdBargain;
import com.hmmp.system.mapper.AdBargainMapper;
import com.hmmp.system.service.IAdBargainService;

/**
 * 合同信息 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdBargainServiceImpl implements IAdBargainService
{
    @Autowired
    private AdBargainMapper bargainMapper;

    @Override
    public AdBargain selectBargainById(Long bargainId)
    {
        return bargainMapper.selectBargainById(bargainId);
    }

    @Override
    public List<AdBargain> selectBargainList(AdBargain bargain)
    {
        return bargainMapper.selectBargainList(bargain);
    }

    @Override
    public int insertBargain(AdBargain bargain)
    {
        return bargainMapper.insertBargain(bargain);
    }

    @Override
    public int updateBargain(AdBargain bargain)
    {
        return bargainMapper.updateBargain(bargain);
    }

    @Override
    public void deleteBargainByIds(Long[] bargainIds)
    {
        bargainMapper.deleteBargainByIds(bargainIds);
    }
}