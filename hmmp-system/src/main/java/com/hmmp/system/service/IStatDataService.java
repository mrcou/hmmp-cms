package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.StatData;

/**
 * 统计数据 业务层
 * 
 * @author hmmp
 */
public interface IStatDataService
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
     * 批量删除统计数据
     * 
     * @param statIds 需要删除的统计ID
     * @return 结果
     */
    public int deleteStatDataByIds(Long[] statIds);

    /**
     * 删除统计数据
     * 
     * @param statId 统计ID
     * @return 结果
     */
    public int deleteStatDataById(Long statId);
}