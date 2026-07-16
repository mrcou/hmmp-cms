package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdType;
import com.hmmp.system.mapper.AdTypeMapper;
import com.hmmp.system.service.IAdTypeService;

/**
 * 广告类型 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdTypeServiceImpl implements IAdTypeService
{
    @Autowired
    private AdTypeMapper typeMapper;

    @Override
    public AdType selectTypeById(Long typeId)
    {
        return typeMapper.selectTypeById(typeId);
    }

    @Override
    public List<AdType> selectTypeList(AdType type)
    {
        return typeMapper.selectTypeList(type);
    }

    @Override
    public int insertType(AdType type)
    {
        return typeMapper.insertType(type);
    }

    @Override
    public int updateType(AdType type)
    {
        return typeMapper.updateType(type);
    }

    @Override
    public void deleteTypeByIds(Long[] typeIds)
    {
        typeMapper.deleteTypeByIds(typeIds);
    }
}