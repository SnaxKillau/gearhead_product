package ite.product.gearheadproduct.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationOrderRequest {
    Long id;
    LocalDate created;
    Integer price;
    String senderId;
}
