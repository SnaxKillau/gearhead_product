package ite.product.gearheadproduct.repository;

import ite.product.gearheadproduct.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
