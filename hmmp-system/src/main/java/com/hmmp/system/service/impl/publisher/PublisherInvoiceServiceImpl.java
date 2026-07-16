package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherInvoice;
import com.hmmp.system.mapper.publisher.PublisherInvoiceMapper;
import com.hmmp.system.service.publisher.IPublisherInvoiceService;

@Service
public class PublisherInvoiceServiceImpl implements IPublisherInvoiceService {

    @Autowired
    private PublisherInvoiceMapper invoiceMapper;

    @Override
    public List<PublisherInvoice> selectInvoiceList(PublisherInvoice invoice) {
        return invoiceMapper.selectInvoiceList(invoice);
    }

    @Override
    public PublisherInvoice selectInvoiceById(Long invoiceId) {
        return invoiceMapper.selectInvoiceById(invoiceId);
    }

    @Override
    public int applyInvoice(PublisherInvoice invoice) {
        invoice.setCreateBy(SecurityUtils.getUsername());
        invoice.setApplyStatus("0");
        return invoiceMapper.insertInvoice(invoice);
    }

    @Override
    public int auditInvoice(PublisherInvoice invoice) {
        invoice.setUpdateBy(SecurityUtils.getUsername());
        invoice.setAuditBy(SecurityUtils.getUsername());
        invoice.setAuditTime(new Date());
        return invoiceMapper.auditInvoice(invoice);
    }

    @Override
    public int makeInvoice(PublisherInvoice invoice) {
        invoice.setUpdateBy(SecurityUtils.getUsername());
        invoice.setMakeTime(new Date());
        return invoiceMapper.makeInvoice(invoice);
    }

    @Override
    public int postInvoice(PublisherInvoice invoice) {
        invoice.setUpdateBy(SecurityUtils.getUsername());
        invoice.setPostTime(new Date());
        return invoiceMapper.postInvoice(invoice);
    }

    @Override
    public int deleteInvoiceByIds(Long[] invoiceIds) {
        return invoiceMapper.deleteInvoiceByIds(invoiceIds);
    }
}