package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.AdArea;

/**
 * 区域信息 服务层
 * 
 * @author hmmp
 */
public interface IAdAreaService
{
    /**
     * 查询区域信息
     * 
     * @param areaId 区域ID
     * @return 区域信息
     */
    public AdArea selectAreaById(Long areaId);

    /**
     * 查询区域列表
     * 
     * @param area 区域信息
     * @return 区域集合
     */
    public List<AdArea> selectAreaList(AdArea area);

    /**
     * 新增区域
     * 
     * @param area 区域信息
     * @return 结果
     */
    public int insertArea(AdArea area);

    /**
     * 修改区域
     * 
     * @param area 区域信息
     * @return 结果
     */
    public int updateArea(AdArea area);

    /**
     * 批量删除区域
     * 
     * @param areaIds 需要删除的区域ID
     */
    public void deleteAreaByIds(Long[] areaIds);
}