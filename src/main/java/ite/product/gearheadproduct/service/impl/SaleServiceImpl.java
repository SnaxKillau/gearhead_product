package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.dto.ReportTransformationsDTO;
import ite.product.gearheadproduct.dto.SaleDTO;
import ite.product.gearheadproduct.entity.Sale;
import ite.product.gearheadproduct.entity.SaleDetail;
import ite.product.gearheadproduct.entity.Transformation;
import ite.product.gearheadproduct.exception.ApiException;
import ite.product.gearheadproduct.repository.SaleDetailRepository;
import ite.product.gearheadproduct.repository.SaleRepository;
import ite.product.gearheadproduct.repository.TransformationRepository;
import ite.product.gearheadproduct.service.SaleService;
import ite.product.gearheadproduct.specification.SaleDetailFilter;
import ite.product.gearheadproduct.specification.SaleDetailSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    
    private final TransformationRepository transformationRepository;

    private final SaleDetailRepository saleDetailRepository;

    private final SaleRepository saleRepository;

    
    @Override
    public void sale(SaleDTO dto) {
        List<Long> transformationId = dto.getTransformation()
                .stream()
                .map(t -> t.getTransformation_id())
                .collect(Collectors.toList());
        List<Transformation> transformations = transformationRepository.findAllById(transformationId);
        Map<Long, Transformation> transformationMap = transformations.stream()
                .collect(Collectors.toMap(Transformation::getId, Function.identity()));
       Sale sale = new Sale();
       sale.setSoldDate(dto.getSoldDate());
       saleRepository.save(sale);
       dto.getTransformation().forEach(t->{
           Transformation transformation = transformationMap.get(t.getTransformation_id());
           if(transformation.getAvailableUnit() < t.getUnit()){
               throw new ApiException(HttpStatus.BAD_REQUEST , transformation.getModel() + "isn't enough , Sorry we alert when we restock");
           }
           transformation.setAvailableUnit(transformation.getAvailableUnit() - t.getUnit());
           transformationRepository.save(transformation);
           SaleDetail saleDetail = new SaleDetail();
           saleDetail.setUnit(t.getUnit());
           saleDetail.setAmount(transformation.getPrice() * t.getUnit());
           saleDetail.setTransformation(transformation);
           saleDetail.setSale(sale);
           SaleDetail saveSaleDetail = saleDetailRepository.save(saleDetail);
        }
       );
    }

    @Override
    public List<SaleDetail> get() {
        return saleDetailRepository.findAll();
    }

    @Override
    public List<ReportTransformationsDTO> report(LocalDate startDate, LocalDate endDate) {
        List<ReportTransformationsDTO> reportTransformationsDTOS = new ArrayList<>();
        SaleDetailFilter saleDetailFilter = new SaleDetailFilter();
        saleDetailFilter.setStartDate(startDate);
        saleDetailFilter.setEndDate(endDate);
        SaleDetailSpec saleDetailSpec = new SaleDetailSpec(saleDetailFilter);

        List<SaleDetail> saleDetailList = saleDetailRepository.findAll(saleDetailSpec);


        List<Long> transformation_id = saleDetailList.
                stream().map(t -> t.getTransformation().getId())
                .collect(Collectors.toList());
        Map<Long , Transformation> transformationMap = transformationRepository.findAllById(transformation_id)
                .stream().collect(Collectors.toMap(Transformation::getId , Function.identity()));
        Map<Transformation , List<SaleDetail>> saleDetailMap = saleDetailList.stream()
                .collect(Collectors.groupingBy(SaleDetail::getTransformation));


        for(var entity: saleDetailMap.entrySet()){
            Transformation transformation = transformationMap.get(entity.getKey().getId());
            Integer unit = entity.getValue().stream().map(SaleDetail::getUnit).reduce(0, (f,s) -> f + s);
            double total = entity.getValue().stream().mapToDouble(t -> t.getUnit() * t.getAmount().doubleValue()).sum();

            ReportTransformationsDTO reportTransformationsDTO = new ReportTransformationsDTO();
            reportTransformationsDTO.setUnit(unit);
            reportTransformationsDTO.setTransformationId(transformation.getId());
            reportTransformationsDTO.setTransformationName(transformation.getBrand() + transformation.getModel());
            reportTransformationsDTO.setTotalAmount(BigDecimal.valueOf(total));
            reportTransformationsDTOS.add(reportTransformationsDTO);
        }
        return reportTransformationsDTOS;
    }

    @Override
    public Map<Month, Integer> getByMonth() {
        List<SaleDetail> saleDetail = saleDetailRepository.findAll();
        Map<Month , Integer > monthlySale = saleDetail.stream()
                .collect(Collectors.groupingBy(
                        saleDetail1 -> saleDetail1.getSale().getSoldDate().getMonth(),
                        TreeMap::new,
                        Collectors.summingInt(SaleDetail::getUnit)
                ));


        return monthlySale;
    }
}
