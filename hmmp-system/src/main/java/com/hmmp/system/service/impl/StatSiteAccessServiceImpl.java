package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.StatSiteAccess;
import com.hmmp.system.mapper.StatSiteAccessMapper;
import com.hmmp.system.service.IStatSiteAccessService;

/**
 * 网站访问日志 业务层处理
 * 
 * @author hmmp
 */
@Service
public class StatSiteAccessServiceImpl implements IStatSiteAccessService
{
    @Autowired
    private StatSiteAccessMapper statSiteAccessMapper;

    /**
     * 查询网站访问日志列表
     * 
     * @param statSiteAccess 网站访问日志
     * @return 网站访问日志集合
     */
    @Override
    public List<StatSiteAccess> selectStatSiteAccessList(StatSiteAccess statSiteAccess)
    {
        return statSiteAccessMapper.selectStatSiteAccessList(statSiteAccess);
    }

    /**
     * 通过访问ID查询网站访问日志
     * 
     * @param accessId 访问ID
     * @return 网站访问日志
     */
    @Override
    public StatSiteAccess selectStatSiteAccessById(Long accessId)
    {
        return statSiteAccessMapper.selectStatSiteAccessById(accessId);
    }

    /**
     * 新增网站访问日志
     * 
     * @param statSiteAccess 网站访问日志
     * @return 结果
     */
    @Override
    public int insertStatSiteAccess(StatSiteAccess statSiteAccess)
    {
        return statSiteAccessMapper.insertStatSiteAccess(statSiteAccess);
    }

    /**
     * 修改网站访问日志
     * 
     * @param statSiteAccess 网站访问日志
     * @return 结果
     */
    @Override
    public int updateStatSiteAccess(StatSiteAccess statSiteAccess)
    {
        return statSiteAccessMapper.updateStatSiteAccess(statSiteAccess);
    }

    /**
     * 批量删除网站访问日志
     * 
     * @param accessIds 需要删除的访问ID
     * @return 结果
     */
    @Override
    public int deleteStatSiteAccessByIds(Long[] accessIds)
    {
        return statSiteAccessMapper.deleteStatSiteAccessByIds(accessIds);
    }

    /**
     * 删除网站访问日志
     * 
     * @param accessId 访问ID
     * @return 结果
     */
    @Override
    public int deleteStatSiteAccessById(Long accessId)
    {
        return statSiteAccessMapper.deleteStatSiteAccessById(accessId);
    }
}