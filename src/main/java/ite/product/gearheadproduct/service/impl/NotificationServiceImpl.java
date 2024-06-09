package ite.product.gearheadproduct.service.impl;


import ite.product.gearheadproduct.dto.UserResponse;
import ite.product.gearheadproduct.entity.Notification;
import ite.product.gearheadproduct.entity.Order;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.repository.NotificationRepository;
import ite.product.gearheadproduct.repository.OrderRepository;
import ite.product.gearheadproduct.request.*;
import ite.product.gearheadproduct.response.NotificationResponse;
import ite.product.gearheadproduct.service.NotificationService;
import ite.product.gearheadproduct.service.cilent.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public Notification posting(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        UserResponse senderResponse = userClient.getUserDetail(notificationRequest.getSenderId());
        Order order = orderRepository.findById(notificationRequest.getOrderId())
                .orElseThrow(() -> new ResourceNotFound(notificationRequest.getOrderId(), "Order"));
        if(notificationRequest.getNotificationId() != null){
            Notification parent = repository.findById(notificationRequest.getNotificationId())
                    .orElseThrow(() -> new ResourceNotFound(notificationRequest.getNotificationId(), "Notification"));
            notification.setParent(parent);
        }
        else {
            notification.setParent(null);
        }
        notification.setMessage(notificationRequest.getMessage());
        notification.setOrder(order);
        notification.setSenderId(notificationRequest.getSenderId());
        notification.setReceiverId(notificationRequest.getReceiverId());
        notification.setSenderEmail(senderResponse.getEmail());
        notification.setSenderUsername(senderResponse.getName());
        notification.setAccepted(notificationRequest.isAccepted());
        notification.setSeen(notificationRequest.isSeen());
        notification.setCreated(notificationRequest.getCreated());
        notification.setConfirmMessage(notificationRequest.isConfirmMessage());
        return repository.save(notification);
    }

    @Override
    public Notification postingWithoutOrder(NotificationWithoutOrderRequest notificationRequest) {
        Notification notification = new Notification();
        UserResponse senderResponse = userClient.getUserDetail(notificationRequest.getSenderId());
        if(notificationRequest.getNotificationId() != null){
            Notification parent = repository.findById(notificationRequest.getNotificationId())
                    .orElseThrow(() -> new ResourceNotFound(notificationRequest.getNotificationId(), "Notification"));
            notification.setParent(parent);
        }
        else {
            notification.setParent(null);
        }
        notification.setMessage(notificationRequest.getMessage());
        notification.setOrder(null);
        notification.setSenderId(notificationRequest.getSenderId());
        notification.setReceiverId(notificationRequest.getReceiverId());
        notification.setSenderEmail(senderResponse.getEmail());
        notification.setSenderUsername(senderResponse.getName());
        notification.setAccepted(notificationRequest.isAccepted());
        notification.setSeen(notificationRequest.isSeen());
        notification.setCreated(notificationRequest.getCreated());
        notification.setConfirmMessage(notificationRequest.isConfirmMessage());

        return repository.save(notification);
    }

    @Override
    public Notification detail(Long id) {
        Notification notification = repository.findById(id).orElseThrow(() -> new ResourceNotFound(id, "Notification"));
        return notification;
    }

    @Override
    public NotificationResponse getAllNotification() {
        List<Notification> allNoti = repository.findAll()
                .stream()
                .filter(data -> data.getParent() == null)
                .collect(Collectors.toList());
        NotificationResponse notificationResponse = new NotificationResponse();
        List<Notification> unSeen = filterUnSeenNotifications(allNoti);
        List<Notification> seen = filterSeenNotification(allNoti);
        List<Notification> sortUnseen = sortByCreated(unSeen);
        List<Notification> sortSeen = sortByCreated(seen);
        notificationResponse.setUnseen(sortUnseen);
        notificationResponse.setSeen(sortSeen);
        return notificationResponse;
    }

    @Override
    public List<Notification> get() {
        List<Notification> allNoti = repository.findAll()
                .stream()
                .filter(data -> data.getParent() == null)
                .collect(Collectors.toList());
        List<Notification> sortAll = sortByCreated(allNoti);
        return sortAll;
    }

    @Override
    public Notification getById(Long id) {
        Notification notification = repository.findById(id).orElseThrow(() -> new ResourceNotFound(id,"Notification"));
        return notification;
    }

    @Override
    public NotificationResponse getByUserId(String userId) {
        List<Notification> allNoti = repository.findAll()
                .stream()
                .filter(data -> data.getParent() == null && (data.getReceiverId().equals(userId) || data.getSenderId().equals(userId)))
                .collect(Collectors.toList());
        NotificationResponse notificationResponse = new NotificationResponse();
        List<Notification> unSeen = filterUnSeenNotifications(allNoti);
        List<Notification> seen = filterSeenNotification(allNoti);
        List<Notification> sortUnseen = sortByCreated(unSeen);
        List<Notification> sortSeen = sortByCreated(seen);
        notificationResponse.setUnseen(sortUnseen);
        notificationResponse.setSeen(sortSeen);
        return notificationResponse;
    }

    @Override
    public String deleteNotification(DeleteNotificationRequest request) {
            repository.deleteAllById(request.getNotificationId());
        return "Successfully";
    }

    @Override
    public boolean orderAccept(NotificationOrderRequest request) {
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFound(request.getId(), "Order"));

        NotificationRequest notificationRequest = createNotificationRequest(request, order);
        Notification notificationResponse = posting(notificationRequest);
        order.setPrice(request.getPrice());
        order.setAccept(true);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean orderDeny(NotificationDenyRequest request) {
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFound(request.getId(), "Order"));


        NotificationRequest notificationRequest = denyNotificationRequest(request, order);
        Notification notificationResponse = posting(notificationRequest);
        order.setPrice(0);
        order.setDeny(true);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean customerAccept(CustomerNotificationAcceptRequest request) {
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFound(request.getId(), "Order"));

        Notification notification = notificationRepository.findById(request.getNotificationId())
                .orElseThrow(() -> new ResourceNotFound(request.getNotificationId(),"Notification"));
        NotificationRequest notificationRequest = acceptNotificationRequest(request, order);
        Notification notificationResponse = posting(notificationRequest);
        notification.setConfirmMessage(false);
        notificationRepository.save(notification);
        notificationRepository.save(notification);
        order.setCustomerAccept(true);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean customerDeny(CustomerNotificationDenyRequest request) {
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFound(request.getId(), "Order"));
        Notification notification = notificationRepository.findById(request.getNotificationId())
                .orElseThrow(() -> new ResourceNotFound(request.getNotificationId(),"Notification"));
        NotificationRequest notificationRequest = denyCustomerNotificationRequest(request , order);
        Notification notificationResponse = posting(notificationRequest);
        notificationRepository.delete(notification);
        order.setCustomerDeny(true);
        order.setDeny(true);
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean seen(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id,"Notification"));
        if (!notification.getSeen()) {
            notification.setSeen(true);
            notificationRepository.save(notification);
        }

        if (notification.getChildren() != null) {
            List<Notification> notificationsToSave = notification.getChildren().stream()
                    .filter(e -> !e.getSeen())
                    .peek(e -> e.setSeen(true))
                    .collect(Collectors.toList());
            if (!notificationsToSave.isEmpty()) {
                notificationRepository.saveAll(notificationsToSave);
            }
        }
        return true;
    }

    private NotificationRequest denyNotificationRequest(NotificationDenyRequest request, Order order) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAccepted(false);
        notificationRequest.setNotificationId(null);
        notificationRequest.setOrderId(order.getId());
        notificationRequest.setConfirmMessage(true);
        notificationRequest.setSeen(false);
        notificationRequest.setSenderId(order.getUser_id());
        notificationRequest.setReceiverId(request.getSenderId());
        notificationRequest.setMessage("Sorry For Denied Order, Our source can't find your order's products");
        notificationRequest.setCreated(request.getCreated());

        return notificationRequest;
    }

    private NotificationRequest createNotificationRequest(NotificationOrderRequest request, Order order) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAccepted(false);
        notificationRequest.setNotificationId(null);
        notificationRequest.setOrderId(order.getId());
        notificationRequest.setConfirmMessage(true);
        notificationRequest.setSeen(false);
        notificationRequest.setSenderId(order.getUser_id());
        notificationRequest.setReceiverId(request.getSenderId());
        notificationRequest.setMessage("Order Accept, The price of this item is " + request.getPrice() + ". Please select accept to accept this price or deny to deny and delete the order.");
        notificationRequest.setCreated(request.getCreated());

        return notificationRequest;
    }
    private NotificationRequest acceptNotificationRequest(CustomerNotificationAcceptRequest request, Order order) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAccepted(false);
        notificationRequest.setNotificationId(null);
        notificationRequest.setOrderId(order.getId());
        notificationRequest.setConfirmMessage(false);
        notificationRequest.setSeen(false);
        notificationRequest.setReceiverId(order.getUser_id());
        notificationRequest.setSenderId(request.getSenderId());
        notificationRequest.setMessage("Order price accepted.");
        notificationRequest.setCreated(request.getCreated());

        return notificationRequest;
    }
    private NotificationRequest denyCustomerNotificationRequest(CustomerNotificationDenyRequest request, Order order) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAccepted(false);
        notificationRequest.setNotificationId(null);
        notificationRequest.setOrderId(order.getId());
        notificationRequest.setConfirmMessage(false);
        notificationRequest.setSeen(false);
        notificationRequest.setReceiverId(order.getUser_id());
        notificationRequest.setSenderId(request.getSenderId());
        notificationRequest.setMessage("Order price deny.");
        notificationRequest.setCreated(request.getCreated());

        return notificationRequest;
    }


    public List<Notification> filterUnSeenNotifications(List<Notification> notifications) {
        return notifications.stream()
                .filter(notification ->
                        !notification.getSeen() ||
                                (notification.getChildren() != null && notification.getChildren().stream().anyMatch(child -> !child.getSeen()))
                )
                .collect(Collectors.toList());
    }

    public List<Notification> filterSeenNotification(List<Notification> notifications) {
        return notifications.stream()
                .filter(notification ->
                        notification.getSeen() &&
                                (notification.getChildren() == null || notification.getChildren().stream().allMatch(Notification::getSeen))
                )
                .collect(Collectors.toList());
    }

    public List<Notification> sortByCreated(List<Notification> notifications) {
        List<Notification> sortedNotifications = notifications.stream()
                .sorted(Comparator.comparing(Notification::getCreated).reversed())
                .map(notification -> {
                    List<Notification> sortedChildren = notification.getChildren().stream()
                            .sorted(Comparator.comparing(Notification::getCreated).reversed())
                            .collect(Collectors.toList());
                    notification.setChildren(sortedChildren);
                    return notification;
                })
                .collect(Collectors.toList());
        return sortedNotifications;
    }


}
