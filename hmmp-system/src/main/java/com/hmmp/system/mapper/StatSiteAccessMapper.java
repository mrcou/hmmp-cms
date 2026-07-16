package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.StatSiteAccess;

/**
 * 网站访问日志 数据层
 * 
 * @author hmmp
 */
public interface StatSiteAccessMapper
{
    /**
     * 查询网站访问日志列表
     * 
     * @param statSiteAccess 网站访问日志
     * @return 网站访问日志集合
     */
    public List<StatSiteAccess> selectStatSiteAccessList(StatSiteAccess statSiteAccess);

    /**
     * 通过访问ID查询网站访问日志
     * 
     * @param accessId 访问ID
     * @return 网站访问日志
     */
    public StatSiteAccess selectStatSiteAccessById(Long accessId);

    /**
     * 新增网站访问日志
     * 
     * @param statSiteAccess 网站访问日志
     * @return 结果
     */
    public int insertStatSiteAccess(StatSiteAccess statSiteAccess);

    /**
     * 修改网站访问日志
     * 
     * @param statSiteAccess 网站访问日志
     * @return 结果
     */
    public int updateStatSiteAccess(StatSiteAccess statSiteAccess);

    /**
     * 删除网站访问日志
     * 
     * @param accessId 访问ID
     * @return 结果
     */
    public int deleteStatSiteAccessById(Long accessId);

    /**
     * 批量删除网站访问日志
     * 
     * @param accessIds 需要删除的访问ID
     * @return 结果
     */
    public int deleteStatSiteAccessByIds(Long[] accessIds);
}