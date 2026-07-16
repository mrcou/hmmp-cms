package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdPosition;
import com.hmmp.system.mapper.AdPositionMapper;
import com.hmmp.system.service.IAdPositionService;

/**
 * 广告位 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdPositionServiceImpl implements IAdPositionService
{
    @Autowired
    private AdPositionMapper positionMapper;

    @Override
    public AdPosition selectPositionById(Long positionId)
    {
        return positionMapper.selectPositionById(positionId);
    }

    @Override
    public List<AdPosition> selectPositionList(AdPosition position)
    {
        return positionMapper.selectPositionList(position);
    }

    @Override
    public int insertPosition(AdPosition position)
    {
        return positionMapper.insertPosition(position);
    }

    @Override
    public int updatePosition(AdPosition position)
    {
        return positionMapper.updatePosition(position);
    }

    @Override
    public void deletePositionByIds(Long[] positionIds)
    {
        positionMapper.deletePositionByIds(positionIds);
    }
}