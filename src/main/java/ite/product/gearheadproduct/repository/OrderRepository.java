package ite.product.gearheadproduct.repository;

import ite.product.gearheadproduct.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order , Long> {
    @Query("SELECT o FROM Order o WHERE o.user_id = :userId")
    List<Order> findByUserId(@Param("userId") String userId);
}

