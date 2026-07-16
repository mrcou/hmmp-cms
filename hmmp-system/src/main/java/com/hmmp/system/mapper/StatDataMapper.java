package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.StatData;

/**
 * 统计数据 数据层
 * 
 * @author hmmp
 */
public interface StatDataMapper
{
    /**
     * 查询统计数据列表
     * 
     * @param statData 统计数据
     * @return 统计数据集合
     */
    public List<StatData> selectStatDataList(StatData statData);

    /**
     * 通过统计ID查询统计数据
     * 
     * @param statId 统计ID
     * @return 统计数据
     */
    public StatData selectStatDataById(Long statId);

    /**
     * 新增统计数据
     * 
     * @param statData 统计数据
     * @return 结果
     */
    public int insertStatData(StatData statData);

    /**
     * 修改统计数据
     * 
     * @param statData 统计数据
     * @return 结果
     */
    public int updateStatData(StatData statData);

    /**
     * 删除统计数据
     * 
     * @param statId 统计ID
     * @return 结果
     */
    public int deleteStatDataById(Long statId);

    /**
     * 批量删除统计数据
     * 
     * @param statIds 需要删除的统计ID
     * @return 结果
     */
    public int deleteStatDataByIds(Long[] statIds);
}