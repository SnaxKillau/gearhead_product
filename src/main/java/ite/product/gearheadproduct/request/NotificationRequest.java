package ite.product.gearheadproduct.request;

import ite.product.gearheadproduct.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String message;
    private Long orderId;
    private String senderId;
    private String receiverId;
    private Long notificationId;
    private boolean confirmMessage;
    private boolean accepted;
    private boolean seen;
    private LocalDate created;
}
