package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.dto.ReportTransformationsDTO;
import ite.product.gearheadproduct.dto.SaleDTO;
import ite.product.gearheadproduct.dto.UserResponse;
import ite.product.gearheadproduct.entity.*;
import ite.product.gearheadproduct.exception.ApiException;
import ite.product.gearheadproduct.repository.*;
import ite.product.gearheadproduct.service.SaleService;
import ite.product.gearheadproduct.service.cilent.UserClient;
import ite.product.gearheadproduct.specification.SaleDetailFilter;
import ite.product.gearheadproduct.specification.SaleDetailSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final TransformationSaleRepository transformationSaleRepository;

    private final InvoiceRepository invoiceRepository;

    @Autowired
    private UserClient userClient;

    
    @Override
    public String sale(SaleDTO dto) {
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
        UserResponse userResponse = userClient.getUserDetail(dto.getUserId());
        Invoice invoice = new Invoice();
        invoice.setUserId(dto.getUserId());
        invoice.setName(userResponse.getName());
        invoice.setEmail(userResponse.getEmail());
        invoice.setInvoiceId(getSaltString());
        invoice.setCreated(dto.getSoldDate());
        invoice.setPhoneNumber(dto.getPhone());
        invoice.setAddress(dto.getAddress());
        invoice.setTotal(dto.getTotal());
        Invoice saveInvoice = invoiceRepository.save(invoice);
       dto.getTransformation().forEach(t->{
           Transformation transformation = transformationMap.get(t.getTransformation_id());
           if(transformation.getAvailableUnit() < t.getUnit()){
               throw new ApiException(HttpStatus.BAD_REQUEST , transformation.getModel() + "isn't enough , Sorry we alert when we restock");
           }
           transformation.setAvailableUnit(transformation.getAvailableUnit() - t.getUnit());
           transformationRepository.save(transformation);
           SaleDetail saleDetail = new SaleDetail();
           saleDetail.setUnit(t.getUnit());
           saleDetail.setUserId(dto.getUserId());
           saleDetail.setAmount(transformation.getPrice() * t.getUnit());
           saleDetail.setTransformation(transformation);
           saleDetail.setSale(sale);
           SaleDetail saveSaleDetail = saleDetailRepository.save(saleDetail);
           TransformationSale transformationSale = new TransformationSale();
           transformationSale.setUnit(t.getUnit());
           transformationSale.setName(transformation.getModel());
           transformationSale.setPrice(transformation.getPrice());
           transformationSale.setTotal(t.getUnit() * transformation.getPrice());
           transformationSale.setInvoice(saveInvoice);
           transformationSaleRepository.save(transformationSale);
        }
       );
       return invoice.getInvoiceId();
    }
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

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
