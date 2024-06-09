package ite.product.gearheadproduct.response;

import ite.product.gearheadproduct.dto.DefaultTransformationDTO;
import lombok.Data;

import java.util.List;

@Data
public class TransformationWithPageResponse {
    private List<DefaultTransformationDTO> transformationDTOS;
    private Integer numberOfPage;

}
