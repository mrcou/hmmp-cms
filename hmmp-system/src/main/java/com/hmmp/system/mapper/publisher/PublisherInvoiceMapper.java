package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherInvoice;

public interface PublisherInvoiceMapper {
    public List<PublisherInvoice> selectInvoiceList(PublisherInvoice invoice);
    public PublisherInvoice selectInvoiceById(Long invoiceId);
    public int insertInvoice(PublisherInvoice invoice);
    public int updateInvoice(PublisherInvoice invoice);
    public int auditInvoice(PublisherInvoice invoice);
    public int makeInvoice(PublisherInvoice invoice);
    public int postInvoice(PublisherInvoice invoice);
    public int deleteInvoiceById(Long invoiceId);
    public int deleteInvoiceByIds(Long[] invoiceIds);
}