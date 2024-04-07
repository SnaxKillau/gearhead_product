package ite.product.gearheadproduct.repository;

import ite.product.gearheadproduct.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order , Long> {
}
