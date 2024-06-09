package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.dto.ReportTransformationsDTO;
import ite.product.gearheadproduct.dto.SaleDTO;
import ite.product.gearheadproduct.entity.SaleDetail;
import ite.product.gearheadproduct.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity<List<SaleDetail>> get() {
        List<SaleDetail> saleDetails = saleService.get();
        return ResponseEntity.ok(saleDetails);
    }

    @PostMapping
    private ResponseEntity<String> post(@RequestBody SaleDTO dto){
        String id = saleService.sale(dto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{startDate}/{endDate}")
    private ResponseEntity<List<ReportTransformationsDTO>> report(@PathVariable("startDate") LocalDate startDate ,@PathVariable("endDate") LocalDate endDate){
        List<ReportTransformationsDTO> reportTransformationsDTOS = saleService.report(startDate,endDate);
        return ResponseEntity.ok(reportTransformationsDTOS);
    }

    @GetMapping("/month")
    private ResponseEntity<Map<Month , Integer>> getMonthlySale(){
        Map<Month , Integer> monthlySale = saleService.getByMonth();
        return ResponseEntity.ok(monthlySale);
    }
}
