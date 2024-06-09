package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.dto.UserResponse;
import ite.product.gearheadproduct.entity.Order;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.exception.ResourceNotFoundStringObject;
import ite.product.gearheadproduct.repository.OrderRepository;
import ite.product.gearheadproduct.request.OrderRequest;
import ite.product.gearheadproduct.service.OrderService;
import ite.product.gearheadproduct.service.cilent.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserClient userClient;
    @Override
    public List<Order> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<Order> orderList = orders.stream()
                .filter(e -> !e.getDeny())
                .collect(Collectors.toList());
        return orderList;
    }
    @Override
    public Order posting(OrderRequest orderRequest) {
        UserResponse userResponse = userClient.getUserDetail(orderRequest.getUser_id());
        Order order = new Order();
        order.setName(orderRequest.getName());
        order.setUserName(userResponse.getName());
        order.setUserEmail(userResponse.getEmail());
        order.setSource(orderRequest.getSource());
        order.setImagePath(orderRequest.getImagePath());
        order.setUser_id(orderRequest.getUser_id());
        order.setCreated(orderRequest.getCreated());
        order.setAddress(orderRequest.getAddress());
        order.setAccept(false);
        order.setDeny(false);
        order.setCustomerAccept(false);
        order.setCustomerDeny(false);
        order.setPrice(0);
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long Id) {
        Order order = orderRepository.findById(Id).orElseThrow(() -> new ResourceNotFound(Id , "Order"));
        return order;
    }

    @Override
    public String deleteById(Long Id , Long userId) {
        Order order = orderRepository.findById(Id).orElseThrow(() -> new ResourceNotFound(Id , "Order"));
        if(order.getUser_id().equals(userId)){
            orderRepository.delete(order);
            String message = "Delete Successfully";
            return message;
        }
        else {
            return "Don't delete other post";
        }
    }

    @Override
    public List<Order> orderByUserId(String id) {
        List<Order> orders = orderRepository.findByUserId(id);
        return orders;
    }

    @Override
    public String deleteAdmin(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id , "Order"));
        orderRepository.delete(order);
        String message = "Delete Successfully";
        return message;
    }
}
