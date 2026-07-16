package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdPayment;
import com.hmmp.system.mapper.AdPaymentMapper;
import com.hmmp.system.service.IAdPaymentService;

/**
 * 支付信息 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdPaymentServiceImpl implements IAdPaymentService
{
    @Autowired
    private AdPaymentMapper paymentMapper;

    @Override
    public AdPayment selectPaymentById(Long paymentId)
    {
        return paymentMapper.selectPaymentById(paymentId);
    }

    @Override
    public List<AdPayment> selectPaymentList(AdPayment payment)
    {
        return paymentMapper.selectPaymentList(payment);
    }

    @Override
    public int insertPayment(AdPayment payment)
    {
        return paymentMapper.insertPayment(payment);
    }

    @Override
    public int updatePayment(AdPayment payment)
    {
        return paymentMapper.updatePayment(payment);
    }

    @Override
    public void deletePaymentByIds(Long[] paymentIds)
    {
        paymentMapper.deletePaymentByIds(paymentIds);
    }
}