package ite.product.gearheadproduct.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDenyRequest {
        Long id;
        LocalDate created;
        String senderId;
}
