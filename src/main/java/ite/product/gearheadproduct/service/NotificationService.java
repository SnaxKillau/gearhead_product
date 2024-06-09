package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.entity.Notification;
import ite.product.gearheadproduct.request.*;
import ite.product.gearheadproduct.response.NotificationResponse;

import java.util.List;


public interface NotificationService {
    Notification posting(NotificationRequest notification);

    Notification postingWithoutOrder(NotificationWithoutOrderRequest notificationWithoutOrderRequest);
    Notification detail(Long id);
    NotificationResponse getAllNotification();

    List<Notification> get();

    Notification getById(Long id);
    NotificationResponse getByUserId(String userId);
    String deleteNotification(DeleteNotificationRequest request);

    boolean orderAccept(NotificationOrderRequest request);

    boolean orderDeny(NotificationDenyRequest request);

    boolean customerAccept(CustomerNotificationAcceptRequest request);

    boolean customerDeny(CustomerNotificationDenyRequest request);

    boolean seen(Long id);
}
