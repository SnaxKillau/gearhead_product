package ite.product.gearheadproduct.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReportTransformationsDTO {

    private Long transformationId;
    private String transformationName;
    private Integer unit;
    private BigDecimal totalAmount;


}
