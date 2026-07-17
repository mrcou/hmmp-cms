package com.hmmp.system.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.system.domain.StatSiteAccess;
import com.hmmp.system.domain.StatSiteVisit;
import com.hmmp.system.domain.StatSiteVisitQuery;
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
     * 按年汇总网站访问量（补齐区间内无数据年份，并计算占比）
     *
     * @param query 时间区间
     * @return 按年汇总列表
     */
    @Override
    public List<StatSiteVisit> selectSiteVisitByYear(StatSiteVisitQuery query)
    {
        List<StatSiteVisit> dbRows = statSiteAccessMapper.selectSiteVisitByYear(query);
        Map<Integer, StatSiteVisit> byYear = new HashMap<>();
        if (dbRows != null)
        {
            for (StatSiteVisit row : dbRows)
            {
                fillRates(row);
                if (row.getVisitYear() != null)
                {
                    byYear.put(row.getVisitYear(), row);
                }
            }
        }

        int beginYear = LocalDate.now().getYear();
        int endYear = beginYear;
        if (StringUtils.isNotEmpty(query.getBeginTime()) && query.getBeginTime().length() >= 4)
        {
            beginYear = Integer.parseInt(query.getBeginTime().substring(0, 4));
        }
        if (StringUtils.isNotEmpty(query.getEndTime()) && query.getEndTime().length() >= 4)
        {
            endYear = Integer.parseInt(query.getEndTime().substring(0, 4));
        }
        if (endYear < beginYear)
        {
            int tmp = beginYear;
            beginYear = endYear;
            endYear = tmp;
        }

        List<StatSiteVisit> result = new ArrayList<>();
        for (int year = beginYear; year <= endYear; year++)
        {
            StatSiteVisit row = byYear.get(year);
            if (row == null)
            {
                row = emptyYear(year);
            }
            result.add(row);
        }
        return result;
    }

    private StatSiteVisit emptyYear(int year)
    {
        StatSiteVisit row = new StatSiteVisit();
        row.setVisitYear(year);
        row.setPvCount(0L);
        row.setUvCount(0L);
        row.setLoginCount(0L);
        row.setLoginRate(0D);
        row.setAnonCount(0L);
        row.setAnonRate(0D);
        row.setPageCount(0L);
        row.setAvgStayTime(0D);
        row.setMinStayTime(0);
        row.setMaxStayTime(0);
        return row;
    }

    private void fillRates(StatSiteVisit row)
    {
        long pv = row.getPvCount() == null ? 0L : row.getPvCount();
        long login = row.getLoginCount() == null ? 0L : row.getLoginCount();
        long anon = row.getAnonCount() == null ? 0L : row.getAnonCount();
        if (pv <= 0)
        {
            row.setLoginRate(0D);
            row.setAnonRate(0D);
            return;
        }
        row.setLoginRate(round1(login * 100.0 / pv));
        row.setAnonRate(round1(anon * 100.0 / pv));
        if (row.getAvgStayTime() == null)
        {
            row.setAvgStayTime(0D);
        }
        if (row.getMinStayTime() == null)
        {
            row.setMinStayTime(0);
        }
        if (row.getMaxStayTime() == null)
        {
            row.setMaxStayTime(0);
        }
        if (row.getUvCount() == null)
        {
            row.setUvCount(0L);
        }
        if (row.getPageCount() == null)
        {
            row.setPageCount(0L);
        }
    }

    private double round1(double value)
    {
        return Math.round(value * 10.0) / 10.0;
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
