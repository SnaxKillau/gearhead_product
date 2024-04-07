package ite.product.gearheadproduct.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SaleDTO {
    private List<TransformationSoldDTO> transformation;
    private LocalDate soldDate;
}
