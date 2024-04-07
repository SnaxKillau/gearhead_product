package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.dto.ReportTransformationsDTO;
import ite.product.gearheadproduct.dto.SaleDTO;
import ite.product.gearheadproduct.entity.SaleDetail;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;


public interface SaleService {
    public void sale(SaleDTO dto);

    public List<SaleDetail> get();
    public List<ReportTransformationsDTO> report(LocalDate startDate , LocalDate endDate);

    public  Map<Month, Integer> getByMonth();
}
