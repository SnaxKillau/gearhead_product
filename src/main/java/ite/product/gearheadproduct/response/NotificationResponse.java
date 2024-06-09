package ite.product.gearheadproduct.response;

import ite.product.gearheadproduct.entity.Notification;
import lombok.Data;

import java.util.List;

@Data
public class NotificationResponse {
    private List<Notification> seen;
    private List<Notification> unseen;

}
