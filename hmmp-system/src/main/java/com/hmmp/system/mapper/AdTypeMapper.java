package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.AdType;

/**
 * 广告类型 数据层
 * 
 * @author hmmp
 */
public interface AdTypeMapper
{
    /**
     * 查询广告类型信息
     * 
     * @param typeId 类型ID
     * @return 广告类型信息
     */
    public AdType selectTypeById(Long typeId);

    /**
     * 查询广告类型列表
     * 
     * @param type 广告类型信息
     * @return 广告类型集合
     */
    public List<AdType> selectTypeList(AdType type);

    /**
     * 新增广告类型
     * 
     * @param type 广告类型信息
     * @return 结果
     */
    public int insertType(AdType type);

    /**
     * 修改广告类型
     * 
     * @param type 广告类型信息
     * @return 结果
     */
    public int updateType(AdType type);

    /**
     * 删除广告类型
     * 
     * @param typeId 类型ID
     * @return 结果
     */
    public int deleteTypeById(Long typeId);

    /**
     * 批量删除广告类型
     * 
     * @param typeIds 需要删除的类型ID
     * @return 结果
     */
    public int deleteTypeByIds(Long[] typeIds);
}