package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.AdBargain;

/**
 * 合同信息 数据层
 * 
 * @author hmmp
 */
public interface AdBargainMapper
{
    /**
     * 查询合同信息
     * 
     * @param bargainId 合同ID
     * @return 合同信息
     */
    public AdBargain selectBargainById(Long bargainId);

    /**
     * 查询合同列表
     * 
     * @param bargain 合同信息
     * @return 合同集合
     */
    public List<AdBargain> selectBargainList(AdBargain bargain);

    /**
     * 新增合同
     * 
     * @param bargain 合同信息
     * @return 结果
     */
    public int insertBargain(AdBargain bargain);

    /**
     * 修改合同
     * 
     * @param bargain 合同信息
     * @return 结果
     */
    public int updateBargain(AdBargain bargain);

    /**
     * 删除合同
     * 
     * @param bargainId 合同ID
     * @return 结果
     */
    public int deleteBargainById(Long bargainId);

    /**
     * 批量删除合同
     * 
     * @param bargainIds 需要删除的合同ID
     * @return 结果
     */
    public int deleteBargainByIds(Long[] bargainIds);
}