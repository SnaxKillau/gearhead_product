package ite.product.gearheadproduct.specification;

import lombok.Data;

import java.util.List;
@Data
public class TransformationFilter {
    private String brand;
    private Integer minPrice;
    private Integer maxPrice;
}
