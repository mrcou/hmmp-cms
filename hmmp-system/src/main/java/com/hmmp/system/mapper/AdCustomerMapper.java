package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.AdCustomer;

/**
 * 客户信息 数据层
 * 
 * @author hmmp
 */
public interface AdCustomerMapper
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
     * 删除客户
     * 
     * @param customerId 客户ID
     * @return 结果
     */
    public int deleteCustomerById(Long customerId);

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的客户ID
     * @return 结果
     */
    public int deleteCustomerByIds(Long[] customerIds);
}