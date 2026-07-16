package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.AdPositionType;

/**
 * 广告位类型 数据层
 * 
 * @author hmmp
 */
public interface AdPositionTypeMapper
{
    /**
     * 查询广告位类型信息
     * 
     * @param typeId 类型ID
     * @return 广告位类型信息
     */
    public AdPositionType selectPositionTypeById(Long typeId);

    /**
     * 查询广告位类型列表
     * 
     * @param positionType 广告位类型信息
     * @return 广告位类型集合
     */
    public List<AdPositionType> selectPositionTypeList(AdPositionType positionType);

    /**
     * 新增广告位类型
     * 
     * @param positionType 广告位类型信息
     * @return 结果
     */
    public int insertPositionType(AdPositionType positionType);

    /**
     * 修改广告位类型
     * 
     * @param positionType 广告位类型信息
     * @return 结果
     */
    public int updatePositionType(AdPositionType positionType);

    /**
     * 删除广告位类型
     * 
     * @param typeId 类型ID
     * @return 结果
     */
    public int deletePositionTypeById(Long typeId);

    /**
     * 批量删除广告位类型
     * 
     * @param typeIds 需要删除的类型ID
     * @return 结果
     */
    public int deletePositionTypeByIds(Long[] typeIds);
}