package ite.product.gearheadproduct.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransformationImportHistoryDTO {

    @NotNull(message = "Date can't be null")
    private LocalDate dataImport;
    @Min(value = 1 ,message = "importUnit should greater than 0 (>0)")
    private Integer importUnit;
    @Min(value = 1 , message = "The price should greater than 1$")
    private Integer pricePerUnit;

    private Long transformation_id;

}
