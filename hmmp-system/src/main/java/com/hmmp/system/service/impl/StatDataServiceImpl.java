package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.StatData;
import com.hmmp.system.mapper.StatDataMapper;
import com.hmmp.system.service.IStatDataService;

/**
 * 统计数据 业务层处理
 * 
 * @author hmmp
 */
@Service
public class StatDataServiceImpl implements IStatDataService
{
    @Autowired
    private StatDataMapper statDataMapper;

    /**
     * 查询统计数据列表
     * 
     * @param statData 统计数据
     * @return 统计数据集合
     */
    @Override
    public List<StatData> selectStatDataList(StatData statData)
    {
        return statDataMapper.selectStatDataList(statData);
    }

    /**
     * 通过统计ID查询统计数据
     * 
     * @param statId 统计ID
     * @return 统计数据
     */
    @Override
    public StatData selectStatDataById(Long statId)
    {
        return statDataMapper.selectStatDataById(statId);
    }

    /**
     * 新增统计数据
     * 
     * @param statData 统计数据
     * @return 结果
     */
    @Override
    public int insertStatData(StatData statData)
    {
        return statDataMapper.insertStatData(statData);
    }

    /**
     * 修改统计数据
     * 
     * @param statData 统计数据
     * @return 结果
     */
    @Override
    public int updateStatData(StatData statData)
    {
        return statDataMapper.updateStatData(statData);
    }

    /**
     * 批量删除统计数据
     * 
     * @param statIds 需要删除的统计ID
     * @return 结果
     */
    @Override
    public int deleteStatDataByIds(Long[] statIds)
    {
        return statDataMapper.deleteStatDataByIds(statIds);
    }

    /**
     * 删除统计数据
     * 
     * @param statId 统计ID
     * @return 结果
     */
    @Override
    public int deleteStatDataById(Long statId)
    {
        return statDataMapper.deleteStatDataById(statId);
    }
}