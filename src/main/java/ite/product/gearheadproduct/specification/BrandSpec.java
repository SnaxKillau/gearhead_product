package ite.product.gearheadproduct.specification;

import ite.product.gearheadproduct.entity.Brand;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class BrandSpec implements Specification<Brand> {

    private BrandFilter brandFilter;
    @Override
    public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<>();
        if(Objects.nonNull(brandFilter.getDescription())){
            Predicate predicate = cb.like(cb.lower(root.get("description")) ,"%" +  brandFilter.getDescription().toLowerCase() + "%");
            predicateList.add(predicate);
        }
        Predicate predicate = cb.and(predicateList.toArray(Predicate[]::new));
        return predicate;
    }
}
