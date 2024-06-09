package ite.product.gearheadproduct.repository;

import ite.product.gearheadproduct.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
