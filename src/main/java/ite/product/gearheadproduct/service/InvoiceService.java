package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> invoice();
    String delete(Long id);

    List<Invoice> findByUser(String userId);

    Invoice getInvoiceById(String id);
}
