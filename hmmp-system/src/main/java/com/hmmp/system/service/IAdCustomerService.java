package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.AdCustomer;

/**
 * 客户信息 服务层
 * 
 * @author hmmp
 */
public interface IAdCustomerService
{
    /**
     * 查询客户信息
     * 
     * @param customerId 客户ID
     * @return 客户信息
     */
    public AdCustomer selectCustomerById(Long customerId);

    /**
     * 查询客户列表
     * 
     * @param customer 客户信息
     * @return 客户集合
     */
    public List<AdCustomer> selectCustomerList(AdCustomer customer);

    /**
     * 新增客户
     * 
     * @param customer 客户信息
     * @return 结果
     */
    public int insertCustomer(AdCustomer customer);

    /**
     * 修改客户
     * 
     * @param customer 客户信息
     * @return 结果
     */
    public int updateCustomer(AdCustomer customer);

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的客户ID
     */
    public void deleteCustomerByIds(Long[] customerIds);
}