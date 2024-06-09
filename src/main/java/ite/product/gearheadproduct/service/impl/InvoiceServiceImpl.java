package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.entity.Invoice;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.exception.ResourceNotFoundStringObject;
import ite.product.gearheadproduct.repository.InvoiceRepository;
import ite.product.gearheadproduct.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> invoice() {
        return invoiceRepository.findAll();
    }

    @Override
    public String delete(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id, "Invoice"));
        invoiceRepository.delete(invoice);
        return "Delete Successfully";
    }

    @Override
    public List<Invoice> findByUser(String userId) {
        List<Invoice> invoices = invoiceRepository.findByUserId(userId);
        return invoices;
    }

    @Override
    public Invoice getInvoiceById(String id) {
        Invoice invoice = invoiceRepository.findByInvoiceId(id).orElseThrow(() -> new ResourceNotFoundStringObject("Invoice not found"));
        return invoice;
    }


}
