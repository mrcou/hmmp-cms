package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdPositionType;
import com.hmmp.system.mapper.AdPositionTypeMapper;
import com.hmmp.system.service.IAdPositionTypeService;

/**
 * 广告位类型 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdPositionTypeServiceImpl implements IAdPositionTypeService
{
    @Autowired
    private AdPositionTypeMapper positionTypeMapper;

    @Override
    public AdPositionType selectPositionTypeById(Long typeId)
    {
        return positionTypeMapper.selectPositionTypeById(typeId);
    }

    @Override
    public List<AdPositionType> selectPositionTypeList(AdPositionType positionType)
    {
        return positionTypeMapper.selectPositionTypeList(positionType);
    }

    @Override
    public int insertPositionType(AdPositionType positionType)
    {
        return positionTypeMapper.insertPositionType(positionType);
    }

    @Override
    public int updatePositionType(AdPositionType positionType)
    {
        return positionTypeMapper.updatePositionType(positionType);
    }

    @Override
    public void deletePositionTypeByIds(Long[] typeIds)
    {
        positionTypeMapper.deletePositionTypeByIds(typeIds);
    }
}