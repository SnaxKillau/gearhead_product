package ite.product.gearheadproduct.response;

import ite.product.gearheadproduct.dto.DefaultTransformationDTO;
import lombok.Data;

import java.util.List;

@Data
public class TopTransformationResponse {
    private List<DefaultTransformationDTO> topTransformation;
    private List<DefaultTransformationDTO> budgetTransformation;
}
