package ite.product.gearheadproduct.repository;

import ite.product.gearheadproduct.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUserId(String userId);

    Optional<Invoice> findByInvoiceId(String invoiceId);
}