package ite.product.gearheadproduct.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SaleDTO {
    private List<TransformationSoldDTO> transformation;
    private Integer total;
    private String phone;
    private String address;
    private String userId;
    private LocalDate soldDate;
}
