package ite.product.gearheadproduct.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationWithoutOrderRequest {
    private String message;
    private String senderId;
    private String receiverId;
    private Long notificationId;
    private boolean confirmMessage;
    private boolean accepted;
    private boolean seen;
    private LocalDate created;
}
