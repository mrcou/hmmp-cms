package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdCustomer;
import com.hmmp.system.mapper.AdCustomerMapper;
import com.hmmp.system.service.IAdCustomerService;

/**
 * 客户信息 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdCustomerServiceImpl implements IAdCustomerService
{
    @Autowired
    private AdCustomerMapper customerMapper;

    @Override
    public AdCustomer selectCustomerById(Long customerId)
    {
        return customerMapper.selectCustomerById(customerId);
    }

    @Override
    public List<AdCustomer> selectCustomerList(AdCustomer customer)
    {
        return customerMapper.selectCustomerList(customer);
    }

    @Override
    public int insertCustomer(AdCustomer customer)
    {
        return customerMapper.insertCustomer(customer);
    }

    @Override
    public int updateCustomer(AdCustomer customer)
    {
        return customerMapper.updateCustomer(customer);
    }

    @Override
    public void deleteCustomerByIds(Long[] customerIds)
    {
        customerMapper.deleteCustomerByIds(customerIds);
    }
}