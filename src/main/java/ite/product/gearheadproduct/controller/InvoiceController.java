package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.entity.Invoice;
import ite.product.gearheadproduct.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @GetMapping
    public ResponseEntity<List<Invoice>> get(){
        List<Invoice> invoice = invoiceService.invoice();
        return ResponseEntity.ok(invoice);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String message = invoiceService.delete(id);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Invoice>> getByUser(@PathVariable String userId){
        List<Invoice> invoices = invoiceService.findByUser(userId);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getById(@PathVariable String id){
        Invoice invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }
}
