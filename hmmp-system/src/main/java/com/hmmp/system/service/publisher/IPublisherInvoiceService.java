package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherInvoice;

public interface IPublisherInvoiceService {
    public List<PublisherInvoice> selectInvoiceList(PublisherInvoice invoice);
    public PublisherInvoice selectInvoiceById(Long invoiceId);
    public int applyInvoice(PublisherInvoice invoice);
    public int auditInvoice(PublisherInvoice invoice);
    public int makeInvoice(PublisherInvoice invoice);
    public int postInvoice(PublisherInvoice invoice);
    public int deleteInvoiceByIds(Long[] invoiceIds);
}