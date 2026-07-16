package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.AdInfo;

/**
 * 广告信息 数据层
 * 
 * @author hmmp
 */
public interface AdInfoMapper
{
    /**
     * 查询广告信息
     * 
     * @param adId 广告ID
     * @return 广告信息
     */
    public AdInfo selectAdById(Long adId);

    /**
     * 查询广告列表
     * 
     * @param ad 广告信息
     * @return 广告集合
     */
    public List<AdInfo> selectAdList(AdInfo ad);

    /**
     * 新增广告
     * 
     * @param ad 广告信息
     * @return 结果
     */
    public int insertAd(AdInfo ad);

    /**
     * 修改广告
     * 
     * @param ad 广告信息
     * @return 结果
     */
    public int updateAd(AdInfo ad);

    /**
     * 删除广告
     * 
     * @param adId 广告ID
     * @return 结果
     */
    public int deleteAdById(Long adId);

    /**
     * 批量删除广告
     * 
     * @param adIds 需要删除的广告ID
     * @return 结果
     */
    public int deleteAdByIds(Long[] adIds);
}