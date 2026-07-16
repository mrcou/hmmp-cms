package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.AdPayment;

/**
 * 支付信息 服务层
 * 
 * @author hmmp
 */
public interface IAdPaymentService
{
    /**
     * 查询支付信息
     * 
     * @param paymentId 支付ID
     * @return 支付信息
     */
    public AdPayment selectPaymentById(Long paymentId);

    /**
     * 查询支付列表
     * 
     * @param payment 支付信息
     * @return 支付集合
     */
    public List<AdPayment> selectPaymentList(AdPayment payment);

    /**
     * 新增支付
     * 
     * @param payment 支付信息
     * @return 结果
     */
    public int insertPayment(AdPayment payment);

    /**
     * 修改支付
     * 
     * @param payment 支付信息
     * @return 结果
     */
    public int updatePayment(AdPayment payment);

    /**
     * 批量删除支付
     * 
     * @param paymentIds 需要删除的支付ID
     */
    public void deletePaymentByIds(Long[] paymentIds);
}