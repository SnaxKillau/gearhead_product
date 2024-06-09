package ite.product.gearheadproduct.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerNotificationAcceptRequest {
        Long id;
        Long notificationId;
        LocalDate created;
        String senderId;
}
