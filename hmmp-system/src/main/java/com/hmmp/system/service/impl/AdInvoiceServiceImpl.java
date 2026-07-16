package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.AdInvoice;
import com.hmmp.system.mapper.AdInvoiceMapper;
import com.hmmp.system.service.IAdInvoiceService;

/**
 * 发票信息 服务层实现
 * 
 * @author hmmp
 */
@Service
public class AdInvoiceServiceImpl implements IAdInvoiceService
{
    @Autowired
    private AdInvoiceMapper invoiceMapper;

    @Override
    public AdInvoice selectInvoiceById(Long invoiceId)
    {
        return invoiceMapper.selectInvoiceById(invoiceId);
    }

    @Override
    public List<AdInvoice> selectInvoiceList(AdInvoice invoice)
    {
        return invoiceMapper.selectInvoiceList(invoice);
    }

    @Override
    public int insertInvoice(AdInvoice invoice)
    {
        return invoiceMapper.insertInvoice(invoice);
    }

    @Override
    public int updateInvoice(AdInvoice invoice)
    {
        return invoiceMapper.updateInvoice(invoice);
    }

    @Override
    public void deleteInvoiceByIds(Long[] invoiceIds)
    {
        invoiceMapper.deleteInvoiceByIds(invoiceIds);
    }
}