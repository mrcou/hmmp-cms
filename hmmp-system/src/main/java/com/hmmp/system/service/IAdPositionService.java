package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.AdPosition;

/**
 * 广告位 服务层
 * 
 * @author hmmp
 */
public interface IAdPositionService
{
    /**
     * 查询广告位信息
     * 
     * @param positionId 广告位ID
     * @return 广告位信息
     */
    public AdPosition selectPositionById(Long positionId);

    /**
     * 查询广告位列表
     * 
     * @param position 广告位信息
     * @return 广告位集合
     */
    public List<AdPosition> selectPositionList(AdPosition position);

    /**
     * 新增广告位
     * 
     * @param position 广告位信息
     * @return 结果
     */
    public int insertPosition(AdPosition position);

    /**
     * 修改广告位
     * 
     * @param position 广告位信息
     * @return 结果
     */
    public int updatePosition(AdPosition position);

    /**
     * 批量删除广告位
     * 
     * @param positionIds 需要删除的广告位ID
     */
    public void deletePositionByIds(Long[] positionIds);
}