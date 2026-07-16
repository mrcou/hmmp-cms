package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.StatSiteAccess;

/**
 * 网站访问日志 业务层
 * 
 * @author hmmp
 */
public interface IStatSiteAccessService
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
     * 批量删除网站访问日志
     * 
     * @param accessIds 需要删除的访问ID
     * @return 结果
     */
    public int deleteStatSiteAccessByIds(Long[] accessIds);

    /**
     * 删除网站访问日志
     * 
     * @param accessId 访问ID
     * @return 结果
     */
    public int deleteStatSiteAccessById(Long accessId);
}