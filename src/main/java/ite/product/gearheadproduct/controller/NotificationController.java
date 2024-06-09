package ite.product.gearheadproduct.controller;


import ite.product.gearheadproduct.entity.Notification;
import ite.product.gearheadproduct.request.*;
import ite.product.gearheadproduct.response.NotificationResponse;
import ite.product.gearheadproduct.service.NotificationService;
import ite.product.gearheadproduct.service.cilent.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserClient userClient;

    @GetMapping
    private ResponseEntity<NotificationResponse> getAllNotification(){
        NotificationResponse notifications = notificationService.getAllNotification();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/all")
    private ResponseEntity<?> get(){
        List<Notification> notifications = notificationService.get();
        return ResponseEntity.ok(notifications);
    }
    @PostMapping
    private ResponseEntity<?> getNotification(@RequestBody NotificationRequest notification) {
        Notification postingNotification = notificationService.posting(notification);
        return ResponseEntity.ok(postingNotification);
    }

    @PostMapping("/seen/{id}")
    private ResponseEntity<Boolean> seen(@PathVariable Long id){
        Boolean seen = notificationService.seen(id);
        return ResponseEntity.ok(seen);
    }

    @PostMapping("/communicate")
    private ResponseEntity<Notification> communicateNotification(@RequestBody NotificationWithoutOrderRequest notificationWithoutOrderRequest){
        Notification notification = notificationService.postingWithoutOrder(notificationWithoutOrderRequest);
        return ResponseEntity.ok(notification);
    }

    @PostMapping("/admin/orderAccept")
    private ResponseEntity<Boolean> orderAccept(@RequestBody NotificationOrderRequest notificationOrderRequest){
        boolean accept = notificationService.orderAccept(notificationOrderRequest);
        return ResponseEntity.ok(accept);
    }
    @PostMapping("/admin/orderDeny")
    private ResponseEntity<Boolean> orderDeny(@RequestBody NotificationDenyRequest notificationOrderRequest){
        boolean accept = notificationService.orderDeny(notificationOrderRequest);
        return ResponseEntity.ok(accept);
    }

    @PostMapping("/orderAccept")
    private ResponseEntity<Boolean> customerAccept(@RequestBody CustomerNotificationAcceptRequest notificationDenyRequest){
        boolean accept = notificationService.customerAccept(notificationDenyRequest);
        return ResponseEntity.ok(accept);
    }

    @PostMapping("/orderDeny")
    private ResponseEntity<Boolean> customerDeny(@RequestBody CustomerNotificationDenyRequest notificationDenyRequest){
        boolean deny = notificationService.customerDeny(notificationDenyRequest);
        return ResponseEntity.ok(deny);
    }
    @GetMapping("{id}")
    private ResponseEntity<Notification> getDetail(@PathVariable Long id){
        Notification notification = notificationService.detail(id);
        return ResponseEntity.ok(notification);
    }
    @GetMapping("/user")
    private ResponseEntity<NotificationResponse> getByUser(@RequestParam String id){
        System.out.println(id);
        NotificationResponse notification = notificationService.getByUserId(id);
        return ResponseEntity.ok(notification);
    }
    @DeleteMapping
    private ResponseEntity<String>delete(@RequestBody DeleteNotificationRequest request){
        String message = notificationService.deleteNotification(request);
        return ResponseEntity.ok(message);
    }


}
