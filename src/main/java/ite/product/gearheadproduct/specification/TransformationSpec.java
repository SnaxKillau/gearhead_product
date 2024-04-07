package ite.product.gearheadproduct.specification;

import ite.product.gearheadproduct.entity.Brand;
import ite.product.gearheadproduct.entity.Transformation;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TransformationSpec implements Specification {
    private TransformationFilter transformationFilter;

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
        Join<Transformation , Brand> brand = root.join("brand");
        Predicate predicate = builder.conjunction();
        if(transformationFilter.getBrand() == null || transformationFilter.getBrand().isBlank()){
            predicate = builder.and(
                    builder.greaterThanOrEqualTo(root.get("price"), transformationFilter.getMinPrice()),
                    builder.lessThanOrEqualTo(root.get("price"), transformationFilter.getMaxPrice())
            );
        }

        if(transformationFilter.getMinPrice() == null){
            predicate = builder.and(
                    builder.like(brand.get("description"), transformationFilter.getBrand()),
                    builder.lessThanOrEqualTo(root.get("price"), transformationFilter.getMaxPrice())
            );
        }
        return predicate;

    }
}
