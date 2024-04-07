package ite.product.gearheadproduct.specification;


import ite.product.gearheadproduct.entity.Sale;
import ite.product.gearheadproduct.entity.SaleDetail;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class SaleDetailSpec implements Specification<SaleDetail> {

    private SaleDetailFilter saleDetailFilter;
    @Override
    public Predicate toPredicate(Root<SaleDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<>();
        Join<SaleDetail , Sale> sale = root.join("sale");
        if(Objects.nonNull(saleDetailFilter.getStartDate())){
            Predicate predicate = cb.greaterThanOrEqualTo(sale.get("soldDate"),saleDetailFilter.getStartDate());
            predicateList.add(predicate);
        }
        if(Objects.nonNull(saleDetailFilter.getEndDate())){
            Predicate predicate = cb.lessThanOrEqualTo(sale.get("soldDate"), saleDetailFilter.getEndDate());
            predicateList.add(predicate);
        }
        Predicate predicate = cb.and(predicateList.toArray(Predicate[]::new));


        return predicate;
    }
}
