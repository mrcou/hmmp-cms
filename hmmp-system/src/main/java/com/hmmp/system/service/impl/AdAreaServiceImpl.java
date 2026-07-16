package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdArea;
import com.hmmp.system.mapper.AdAreaMapper;
import com.hmmp.system.service.IAdAreaService;

/**
 * 区域信息 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdAreaServiceImpl implements IAdAreaService
{
    @Autowired
    private AdAreaMapper areaMapper;

    @Override
    public AdArea selectAreaById(Long areaId)
    {
        return areaMapper.selectAreaById(areaId);
    }

    @Override
    public List<AdArea> selectAreaList(AdArea area)
    {
        return areaMapper.selectAreaList(area);
    }

    @Override
    public int insertArea(AdArea area)
    {
        return areaMapper.insertArea(area);
    }

    @Override
    public int updateArea(AdArea area)
    {
        return areaMapper.updateArea(area);
    }

    @Override
    public void deleteAreaByIds(Long[] areaIds)
    {
        areaMapper.deleteAreaByIds(areaIds);
    }
}