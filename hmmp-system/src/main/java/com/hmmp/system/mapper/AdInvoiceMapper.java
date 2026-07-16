package com.hmmp.system.mapper;

import java.util.List;
import com.hmmp.system.domain.AdInvoice;

/**
 * 发票信息 数据层
 * 
 * @author hmmp
 */
public interface AdInvoiceMapper
{
    /**
     * 查询发票信息
     * 
     * @param invoiceId 发票ID
     * @return 发票信息
     */
    public AdInvoice selectInvoiceById(Long invoiceId);

    /**
     * 查询发票列表
     * 
     * @param invoice 发票信息
     * @return 发票集合
     */
    public List<AdInvoice> selectInvoiceList(AdInvoice invoice);

    /**
     * 新增发票
     * 
     * @param invoice 发票信息
     * @return 结果
     */
    public int insertInvoice(AdInvoice invoice);

    /**
     * 修改发票
     * 
     * @param invoice 发票信息
     * @return 结果
     */
    public int updateInvoice(AdInvoice invoice);

    /**
     * 删除发票
     * 
     * @param invoiceId 发票ID
     * @return 结果
     */
    public int deleteInvoiceById(Long invoiceId);

    /**
     * 批量删除发票
     * 
     * @param invoiceIds 需要删除的发票ID
     * @return 结果
     */
    public int deleteInvoiceByIds(Long[] invoiceIds);
}